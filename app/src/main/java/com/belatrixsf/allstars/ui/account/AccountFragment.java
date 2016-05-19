/* The MIT License (MIT)
* Copyright (c) 2016 BELATRIX
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:

* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.

* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/
package com.belatrixsf.allstars.ui.account;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.adapters.AccountSubCategoriesAdapter;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.entities.SubCategory;
import com.belatrixsf.allstars.ui.account.edit.EditAccountActivity;
import com.belatrixsf.allstars.ui.common.AllStarsFragment;
import com.belatrixsf.allstars.ui.common.RecyclerOnItemClickListener;
import com.belatrixsf.allstars.ui.common.views.DividerItemDecoration;
import com.belatrixsf.allstars.ui.stars.GiveStarActivity;
import com.belatrixsf.allstars.ui.stars.GiveStarFragment;
import com.belatrixsf.allstars.ui.stars.StarsListActivity;
import com.belatrixsf.allstars.utils.AllStarsApplication;
import com.belatrixsf.allstars.utils.DialogUtils;
import com.belatrixsf.allstars.utils.di.modules.presenters.AccountPresenterModule;
import com.belatrixsf.allstars.utils.media.ImageFactory;
import com.belatrixsf.allstars.utils.media.loaders.ImageLoader;

import java.util.List;

import butterknife.Bind;

import static com.belatrixsf.allstars.ui.account.AccountActivity.USER_ID_KEY;
import static com.belatrixsf.allstars.ui.stars.GiveStarFragment.SELECTED_USER_KEY;
import static com.belatrixsf.allstars.ui.account.edit.EditAccountFragment.RQ_EDIT_ACCOUNT;

/**
 * Created by pedrocarrillo on 4/9/16.
 */
public class AccountFragment extends AllStarsFragment implements AccountView, RecyclerOnItemClickListener {

    public static final int RQ_GIVE_STAR = 99;

    private AccountPresenter accountPresenter;
    private AccountSubCategoriesAdapter accountCategoriesAdapter;

    @Bind(R.id.account_recommendations) RecyclerView recommendationRecyclerView;
    @Bind(R.id.skype_id) TextView skypeIdTextView;
    @Bind(R.id.current_month_score) TextView currentMonthScoreTextView;
    @Bind(R.id.level) TextView levelTextView;
    @Bind(R.id.score) TextView scoreTextView;
    @Bind(R.id.profile_name) TextView nameTextView;
    @Bind(R.id.profile_email) TextView emailTextView;
    @Bind(R.id.profile_picture) ImageView pictureImageView;
    @Bind(R.id.profile_location_logo) ImageView profileLocationImageView;
    @Bind(R.id.account_swipe_refresh) SwipeRefreshLayout accountSwipeRefresh;
    @Bind(R.id.subcategories_progress_bar) ProgressBar subCategoriesProgressBar;
    @Bind(R.id.no_data_textview) TextView noDataTextView;

    private MenuItem recommendMenuItem;
    private MenuItem editProfileMenuItem;

    public static AccountFragment newInstance(Integer userId) {
        Bundle bundle = new Bundle();
        if (userId != null) {
            bundle.putInt(USER_ID_KEY, userId);
        }
        AccountFragment accountFragment = new AccountFragment();
        accountFragment.setArguments(bundle);
        return accountFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onClick(View v) {
        accountPresenter.onSubCategoryClicked(v.getTag());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        Integer userId = null;
        if (getArguments() != null) {
            if (getArguments().containsKey(USER_ID_KEY)) {
                userId = getArguments().getInt(USER_ID_KEY);
            }
        }
        accountPresenter.setUserId(userId);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_account, menu);
        recommendMenuItem = menu.findItem(R.id.action_recommend);
        editProfileMenuItem = menu.findItem(R.id.action_edit_profile);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_recommend:
                accountPresenter.startRecommendation();
                return true;
            case R.id.action_edit_profile:
                accountPresenter.startEditProfile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupViews() {
        accountCategoriesAdapter = new AccountSubCategoriesAdapter(this);
        recommendationRecyclerView.setAdapter(accountCategoriesAdapter);
        recommendationRecyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setAutoMeasureEnabled(true);
        recommendationRecyclerView.setNestedScrollingEnabled(false);
        recommendationRecyclerView.setLayoutManager(linearLayoutManager);

        accountSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    @Override
    protected void initDependencies(AllStarsApplication allStarsApplication) {
        accountPresenter = allStarsApplication.getApplicationComponent()
                .accountComponent(new AccountPresenterModule(this))
                .accountPresenter();
    }

    @Override
    public void goSubCategoryDetail(Integer categoryId, Integer employeeId) {
        Intent intent = new Intent(getActivity(), StarsListActivity.class);
        intent.putExtra(StarsListActivity.USER_ID, employeeId);
        intent.putExtra(StarsListActivity.SUBCATEGORY_ID, categoryId);
        startActivity(intent);
    }

    @Override
    public void showCurrentMonthScore(String currentMonthScore) {
        currentMonthScoreTextView.setText(String.valueOf(currentMonthScore));
    }

    @Override
    public void showScore(String score) {
        scoreTextView.setText(String.valueOf(score));
    }

    @Override
    public void showSubCategories(List<SubCategory> subCategories) {
        accountCategoriesAdapter.updateData(subCategories);
    }

    @Override
    public void showLevel(String level) {
        levelTextView.setText(String.valueOf(level));
    }

    @Override
    public void showEmployeeName(String employeName) {
        nameTextView.setText(employeName);
    }

    @Override
    public void showEmail(String role) {
        emailTextView.setText(role);
    }

    @Override
    public void showSkypeId(String skypeID) {
        skypeIdTextView.setText(getResources().getString(R.string.skype_id_content, skypeID));
    }

    @Override
    public void showProfilePicture(final String profilePicture) {
        ImageFactory.getLoader().loadFromUrl(
                profilePicture,
                pictureImageView,
                ImageLoader.ImageTransformation.BORDERED_CIRCLE,
                new ImageLoader.Callback() {
                    @Override
                    public void onSuccess() {
                        startPostponedEnterTransition();
                    }

                    @Override
                    public void onFailure() {
                        startPostponedEnterTransition();
                    }
                }
        );
    }

    @Override
    public void showLocationFlag(String locationIcon) {
        ImageFactory.getLoader().loadFromUrl(
                locationIcon,
                profileLocationImageView
        );
    }

    private void startPostponedEnterTransition() {
        pictureImageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                pictureImageView.getViewTreeObserver().removeOnPreDrawListener(this);
                ActivityCompat.startPostponedEnterTransition(getActivity());
                return false;
            }
        });
    }

    @Override
    public void showRecommendMenu(boolean show) {
        recommendMenuItem.setVisible(show);
    }

    @Override
    public void showEditProfileButton(boolean show) {
        if (editProfileMenuItem != null) {
            editProfileMenuItem.setVisible(show);
        }
    }

    @Override
    public void goToEditProfile(Employee employee) {
        Intent intent = new Intent(getActivity(), EditAccountActivity.class);
        intent.putExtra(EditAccountActivity.EMPLOYEE_KEY, employee);
        ViewCompat.setTransitionName(pictureImageView, getActivity().getString(R.string.transition_photo));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pictureImageView, getActivity().getString(R.string.transition_photo));
        getActivity().startActivityForResult(intent, RQ_EDIT_ACCOUNT, options.toBundle());
    }

    @Override
    public void goToGiveStar(Employee employee) {
        Intent intent = new Intent(getActivity(), GiveStarActivity.class);
        intent.putExtra(SELECTED_USER_KEY, employee);
        startActivityForResult(intent, RQ_GIVE_STAR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RQ_GIVE_STAR && resultCode == Activity.RESULT_OK && data != null) {
            DialogUtils.createInformationDialog(getActivity(), data.getStringExtra(GiveStarFragment.MESSAGE_KEY), getString(R.string.app_name), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Do Nothing
                }
            }).show();
        } else if (requestCode == RQ_EDIT_ACCOUNT && resultCode == Activity.RESULT_OK) {
            accountPresenter.refreshEmployee();
        }
    }

    @Override
    public void showProgressDialog() {
        accountSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void showProgressIndicator() {
        setProgressViewVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgressDialog() {
        accountSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void hideProgressIndicator() {
        setProgressViewVisibility(View.GONE);
    }

    @Override
    public void showNoDataView() {
        noDataTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoDataView() {
        noDataTextView.setVisibility(View.GONE);
    }

    public void setProgressViewVisibility(int visibility) {
        subCategoriesProgressBar.setVisibility(visibility);
    }

    public void loadData() {
        accountCategoriesAdapter.clear();
        accountPresenter.loadEmployeeAccount();
    }

}
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
package com.belatrixsf.connect.ui.account;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
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

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.adapters.AccountSubCategoriesAdapter;
import com.belatrixsf.connect.entities.Employee;
import com.belatrixsf.connect.entities.SubCategory;
import com.belatrixsf.connect.ui.account.edit.EditAccountActivity;
import com.belatrixsf.connect.ui.account.edit.EditAccountFragment;
import com.belatrixsf.connect.ui.account.expanded.ExpandPictureActivity;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.ui.common.RecyclerOnItemClickListener;
import com.belatrixsf.connect.ui.common.views.DividerItemDecoration;
import com.belatrixsf.connect.ui.login.LoginActivity;
import com.belatrixsf.connect.ui.skills.SkillsListActivity;
import com.belatrixsf.connect.ui.stars.GiveStarActivity;
import com.belatrixsf.connect.ui.stars.GiveStarFragment;
import com.belatrixsf.connect.ui.stars.StarsListActivity;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.DialogUtils;
import com.belatrixsf.connect.utils.SnackbarUtils;
import com.belatrixsf.connect.utils.di.modules.presenters.AccountPresenterModule;
import com.belatrixsf.connect.utils.media.ImageFactory;
import com.belatrixsf.connect.utils.media.loaders.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pedrocarrillo on 4/9/16.
 */
public class AccountFragment extends BelatrixConnectFragment implements AccountView, RecyclerOnItemClickListener {

    public static final int RQ_GIVE_STAR = 99;

    private AccountPresenter accountPresenter;
    private AccountSubCategoriesAdapter accountCategoriesAdapter;
    private AccountFragmentListener accountFragmentListener;

    private MenuItem recommendMenuItem;
    private MenuItem editProfileMenuItem;
    private MenuItem editSkillsMenuItem;

    @Bind(R.id.account_recommendations) RecyclerView recommendationRecyclerView;
    @Bind(R.id.skype_id) TextView skypeIdTextView;
    @Bind(R.id.current_month_score) TextView currentMonthScoreTextView;
    @Bind(R.id.level) TextView levelTextView;
    @Bind(R.id.score) TextView scoreTextView;
    @Bind(R.id.profile_name) TextView nameTextView;
    @Bind(R.id.profile_email) TextView emailTextView;
    @Bind(R.id.profile_picture) ImageView pictureImageView;
    @Bind(R.id.location_name) TextView profileLocationImageView;
    @Bind(R.id.account_swipe_refresh) SwipeRefreshLayout accountSwipeRefresh;
    @Bind(R.id.subcategories_progress_bar) ProgressBar subCategoriesProgressBar;
    @Bind(R.id.no_data_textview) TextView noDataTextView;
    @Bind(R.id.main_coordinator) CoordinatorLayout coordinatorLayout;

    public static AccountFragment newInstance(Integer userId, byte[] imgBitmap) {
        Bundle bundle = new Bundle();
        if (userId != null) {
            bundle.putInt(AccountActivity.USER_ID_KEY, userId);
        }
        if(imgBitmap != null){
            bundle.putByteArray(AccountActivity.USER_IMG_PROFILE_KEY,imgBitmap);
        }
        AccountFragment accountFragment = new AccountFragment();
        accountFragment.setArguments(bundle);
        return accountFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        castOrThrowException(activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        castOrThrowException(context);
    }

    private void castOrThrowException(Context context) {
        try {
            accountFragmentListener = (AccountFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement AccountFragmentListener");
        }
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
        byte [] userImg = null;
        if (getArguments() != null) {
            if (getArguments().containsKey(AccountActivity.USER_ID_KEY)) {
                userId = getArguments().getInt(AccountActivity.USER_ID_KEY);
            }
            if(getArguments().containsKey(AccountActivity.USER_IMG_PROFILE_KEY)){
                userImg = getArguments().getByteArray(AccountActivity.USER_IMG_PROFILE_KEY);
            }
        }
        accountPresenter.setUserInfo(userId, userImg);
        loadData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_account, menu);
        recommendMenuItem = menu.findItem(R.id.action_recommend);
        editProfileMenuItem = menu.findItem(R.id.action_edit_profile);
        editSkillsMenuItem = menu.findItem(R.id.action_edit_skills);
        accountPresenter.checkRecommendationEnabled();
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
            case R.id.action_edit_skills:
                accountPresenter.startEditSkills();
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
                accountPresenter.refreshEmployee();
                loadData();
            }
        });
        accountSwipeRefresh.setColorSchemeResources(R.color.swipe_refresh);
    }

    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        accountPresenter = belatrixConnectApplication.getApplicationComponent()
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
        if (currentMonthScoreTextView != null) {
            currentMonthScoreTextView.setText(String.valueOf(currentMonthScore));
        }
    }

    @Override
    public void showScore(String score) {
        if (scoreTextView != null) {
            scoreTextView.setText(String.valueOf(score));
        }
    }

    @Override
    public void showSubCategories(List<SubCategory> subCategories) {
        if (accountCategoriesAdapter != null) {
            accountCategoriesAdapter.update(subCategories);
        }
    }

    @Override
    public void showLevel(String level) {
        if (levelTextView != null) {
            levelTextView.setText(String.valueOf(level));
        }
    }

    @Override
    public void showEmployeeName(String employeName) {
        if (nameTextView != null) {
            nameTextView.setText(employeName);
        }
    }

    @Override
    public void showEmail(String role) {
        if (emailTextView != null) {
            emailTextView.setText(role);
        }
    }

    @Override
    public void showSkypeId(String skypeID) {
        if (skypeIdTextView != null) {
            skypeIdTextView.setText(skypeID);
        }
    }

    @Override
    public void showProfilePicture(final String profilePicture) {
        if (pictureImageView != null) {
            if (accountPresenter.getEmployeeImg() == null) {
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
                        },
                        getResources().getDrawable(R.drawable.contact_placeholder),
                        ImageLoader.ScaleType.CENTER_CROP
                );
            } else {
                ImageFactory.getLoader().loadFromBitmap(
                        accountPresenter.getEmployeeImg(),
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
                        },
                        getResources().getDrawable(R.drawable.contact_placeholder),
                        ImageLoader.ScaleType.CENTER_CROP
                );
            }
        }
    }

    @Override
    public void showLocation(String location) {
        profileLocationImageView.setText(location);
    }

    private void startPostponedEnterTransition() {
        if (pictureImageView == null) {
            return;
        }
        pictureImageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                pictureImageView.getViewTreeObserver().removeOnPreDrawListener(this);
                ActivityCompat.startPostponedEnterTransition(getActivity());
                return false;
            }
        });
    }

    @OnClick(R.id.profile_picture)
    public void profilePictureClicked() {
        accountPresenter.profilePictureClicked();
    }

    @Override
    public void goToExpandPhoto(String url) {
        ExpandPictureActivity.startActivityAnimatingProfilePic(getActivity(), pictureImageView, url);
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
    public void showEditSkillsButton(boolean show) {
        if (editSkillsMenuItem != null) {
            editSkillsMenuItem.setVisible(show);
        }
    }

    @Override
    public void goToEditProfile(Employee employee) {
        Intent intent = new Intent(getActivity(), EditAccountActivity.class);
        intent.putExtra(EditAccountFragment.IS_NEW_USER, false);
        ViewCompat.setTransitionName(pictureImageView, getActivity().getString(R.string.transition_photo));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pictureImageView, getActivity().getString(R.string.transition_photo));
        getActivity().startActivityForResult(intent, EditAccountFragment.RQ_EDIT_ACCOUNT, options.toBundle());
    }

    @Override
    public void goToEditSkills() {
        startActivity(SkillsListActivity.makeIntent(getActivity()));
    }

    @Override
    public void goToGiveStar(Employee employee) {
        Intent intent = new Intent(getActivity(), GiveStarActivity.class);
        intent.putExtra(GiveStarFragment.SELECTED_USER_KEY, employee);
        startActivityForResult(intent, RQ_GIVE_STAR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RQ_GIVE_STAR && resultCode == Activity.RESULT_OK && data != null) {
            SnackbarUtils.createInformationSnackBar(data.getStringExtra(GiveStarFragment.MESSAGE_KEY), getString(R.string.dialog_option_confirm), coordinatorLayout,new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //nothing
                }
            }).show();
        } else if (requestCode == EditAccountFragment.RQ_EDIT_ACCOUNT) {
            accountPresenter.refreshEmployee();
        }
    }

    @Override
    public void showProgressDialog() {
        if (accountSwipeRefresh != null) {
            accountSwipeRefresh.setRefreshing(true);
        }
    }

    @Override
    public void showProgressIndicator() {
        setProgressViewVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgressDialog() {
        if (accountSwipeRefresh != null) {
            accountSwipeRefresh.setRefreshing(false);
        }
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

    @Override
    public void notifyNavigationRefresh() {
        accountFragmentListener.refreshNavigationDrawer();
    }

    @Override
    public void showInformativeDialog(String information) {
        DialogUtils.createInformationDialog(getActivity(), information, getString(R.string.app_name), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                accountPresenter.confirmEndSession();
            }
        }).show();
    }

    @Override
    public void goBackToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        fragmentListener.closeActivity();
    }

    public void setProgressViewVisibility(int visibility) {
        if (subCategoriesProgressBar != null) {
            subCategoriesProgressBar.setVisibility(visibility);
        }
    }

    public void loadData() {
        accountCategoriesAdapter.clear();
        accountPresenter.loadEmployeeAccount();
    }

    @Override
    public void onDestroyView() {
        accountPresenter.cancelRequests();
        super.onDestroyView();
    }

}
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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.TextView;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.adapters.AccountSubCategoriesAdapter;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.entities.SubCategory;
import com.belatrixsf.allstars.ui.common.AllStarsFragment;
import com.belatrixsf.allstars.ui.common.RecyclerOnItemClickListener;
import com.belatrixsf.allstars.ui.common.views.BorderedCircleTransformation;
import com.belatrixsf.allstars.ui.common.views.DividerItemDecoration;
import com.belatrixsf.allstars.ui.givestar.GiveStarActivity;
import com.belatrixsf.allstars.ui.recommendation.RecommendationActivity;
import com.belatrixsf.allstars.utils.AllStarsApplication;
import com.belatrixsf.allstars.utils.di.modules.presenters.AccountPresenterModule;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import static com.belatrixsf.allstars.ui.account.AccountActivity.USER_ID_KEY;
import static com.belatrixsf.allstars.ui.givestar.GiveStarFragment.SELECTED_USER_KEY;

import java.util.List;

import butterknife.Bind;

/**
 * Created by pedrocarrillo on 4/9/16.
 */
public class AccountFragment extends AllStarsFragment implements AccountView, RecyclerOnItemClickListener {

    private AccountPresenter accountPresenter;
    private AccountSubCategoriesAdapter accountCategoriesAdapter;

    @Bind(R.id.account_recommendations) RecyclerView recommendationRecyclerView;
    @Bind(R.id.skype_id) TextView skypeIdTextView;
    @Bind(R.id.current_month_score) TextView currentMonthScoreTextView;
    @Bind(R.id.level) TextView levelTextView;
    @Bind(R.id.score) TextView scoreTextView;
    @Bind(R.id.profile_name) TextView nameTextView;
    @Bind(R.id.profile_role) TextView roleTextView;
    @Bind(R.id.profile_picture) ImageView pictureImageView;

    private MenuItem recommendMenuItem;

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
        // Inflate the layout for this fragment
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
        accountCategoriesAdapter.clear();
        accountPresenter.loadEmployeeAccount();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_account, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        recommendMenuItem = menu.findItem(R.id.action_recommend);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_recommend:
                accountPresenter.startRecommendation();
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
    }

    @Override
    protected void initDependencies(AllStarsApplication allStarsApplication) {
        accountPresenter = allStarsApplication.getApplicationComponent()
                .accountComponent(new AccountPresenterModule(this))
                .accountPresenter();
    }

    @Override
    public void goSubCategoryDetail(Integer categoryId, Integer employeeId) {
        Intent intent = new Intent(getActivity(), RecommendationActivity.class);
        intent.putExtra(RecommendationActivity.USER_ID, employeeId);
        intent.putExtra(RecommendationActivity.SUBCATEGORY_ID, categoryId);
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
    public void showRole(String role) {
        roleTextView.setText(role);
    }

    @Override
    public void showSkypeId(String skypeID) {
        skypeIdTextView.setText(getResources().getString(R.string.skype_id_content, skypeID));
    }

    @Override
    public void showProfilePicture(final String profilePicture) {
        Glide.with(getActivity())
                .load(profilePicture)
                .fitCenter()
                .transform(new BorderedCircleTransformation(getActivity()))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        startPosponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        startPosponedEnterTransition();
                        return false;
                    }
                })
                .into(pictureImageView);
    }

    private void startPosponedEnterTransition() {
        recommendationRecyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                recommendationRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
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
    public void goToGiveStar(Employee employee) {
        Intent intent = new Intent(getActivity(), GiveStarActivity.class);
        intent.putExtra(SELECTED_USER_KEY, employee);
        startActivity(intent);
    }

}

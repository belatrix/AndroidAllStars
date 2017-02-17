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
package com.belatrixsf.connect.ui.account.recommendations;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.adapters.AccountSubCategoriesAdapter;
import com.belatrixsf.connect.entities.Category;
import com.belatrixsf.connect.entities.Employee;
import com.belatrixsf.connect.entities.SubCategory;
import com.belatrixsf.connect.ui.account.AccountActivity;
import com.belatrixsf.connect.ui.account.AccountFragmentListener;
import com.belatrixsf.connect.ui.account.AccountPresenter;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.ui.common.RecyclerOnItemClickListener;
import com.belatrixsf.connect.ui.common.views.DividerItemDecoration;
import com.belatrixsf.connect.ui.recommendations.RecommendationsActivity;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.LoggerUtil;
import com.belatrixsf.connect.utils.di.modules.presenters.AccountPresenterModule;
import com.belatrixsf.connect.utils.di.modules.presenters.AccountRecommendationsPresenterModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by dvelasquez on 16/2/17.
 */
public class AccountRecommendationsFragment extends BelatrixConnectFragment implements AccountRecommendationsView, RecyclerOnItemClickListener {

    public static final String _CATEGORY_LIST_KEY = "_category_list_key";

    private AccountRecommendationsPresenter accountRecommendationsPresenter;
    private AccountSubCategoriesAdapter accountCategoriesAdapter;
    private AccountFragmentListener accountFragmentListener;

    @Bind(R.id.account_recommendations) RecyclerView recommendationRecyclerView;
    @Bind(R.id.categories_progress_bar) ProgressBar categoriesProgressBar;
    @Bind(R.id.no_data_textview) TextView noDataTextView;

    public static AccountRecommendationsFragment newInstance(Integer userId) {
        LoggerUtil.log("AccountRecommendationsFragment","userId on AccountRecommendationsFragment: " + userId);
        Bundle bundle = new Bundle();
        if (userId != null) {
            bundle.putInt(AccountActivity.USER_ID_KEY, userId);
        }
        AccountRecommendationsFragment accountFragment = new AccountRecommendationsFragment();
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
        return inflater.inflate(R.layout.fragment_account_recommendations, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        }else if (getArguments() != null) {
            Integer userId = null;
            if (getArguments().containsKey(AccountActivity.USER_ID_KEY)) {
                userId = getArguments().getInt(AccountActivity.USER_ID_KEY);
            }

            accountRecommendationsPresenter.setUserInfo(userId);
        }
       accountRecommendationsPresenter.loadCategoriesByEmployee(true);
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
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        accountRecommendationsPresenter = belatrixConnectApplication.getApplicationComponent()
                .accountRecommendationsComponent(new AccountRecommendationsPresenterModule(this))
                .accountRecommendationsPresenter();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        saveState(outState);
        super.onSaveInstanceState(outState);
    }

    private void restoreState(Bundle savedInstanceState) {
        List<SubCategory> categoryList = savedInstanceState.getParcelableArrayList(_CATEGORY_LIST_KEY);
        accountRecommendationsPresenter.loadPresenterState(categoryList);
    }

    private void saveState(Bundle outState) {
    }


    @Override
    public void showProgressIndicator() {
        recommendationRecyclerView.setVisibility(View.GONE);
        setProgressViewVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressIndicator() {
        setProgressViewVisibility(View.GONE);
    }

    public void setProgressViewVisibility(int visibility) {
        if (categoriesProgressBar != null) {
            categoriesProgressBar.setVisibility(visibility);
        }
    }


    @Override
    public void showCategories(List<SubCategory> subCategories) {
        recommendationRecyclerView.setVisibility(View.VISIBLE);
        accountCategoriesAdapter.setData(subCategories);
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
    public void onClick(View v) {
        accountRecommendationsPresenter.onCategoryClicked(v.getTag());
    }

    @Override
    public void goCategoryDetail(int categoryId, int employeeId) {
        RecommendationsActivity.startActivity(getActivity(), employeeId, categoryId);
    }

    @Override
    public void onDestroyView() {
        accountRecommendationsPresenter.cancelRequests();
        super.onDestroyView();
    }
}
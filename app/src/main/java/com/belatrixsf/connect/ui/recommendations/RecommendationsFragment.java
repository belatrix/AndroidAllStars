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
package com.belatrixsf.connect.ui.recommendations;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.adapters.RecommendationsAdapter;
import com.belatrixsf.connect.entities.Star;
import com.belatrixsf.connect.ui.account.AccountFragmentListener;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.ui.common.views.DividerItemDecoration;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.di.modules.presenters.RecommendationsPresenterModule;

import java.util.List;

import butterknife.Bind;

/**
 * Created by dvelasquez on 17/2/17.
 */
public class RecommendationsFragment extends BelatrixConnectFragment implements RecommendationsView ,RecommendationsAdapter.KeywordClickListener {

    public static final String _RECOMMENDATION_LIST_KEY = "_recommendation_list_key";

    private RecommendationsPresenter recommendationsPresenter;
    private RecommendationsAdapter recommendationsAdapter;
    private AccountFragmentListener accountFragmentListener;

    @Bind(R.id.recommendations_swipe_refresh) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recommendations) RecyclerView recommendationRecyclerView;
    @Bind(R.id.recommendations_progress_bar) ProgressBar categoriesProgressBar;
    @Bind(R.id.no_data_textview) TextView noDataTextView;

    public static RecommendationsFragment newInstance(Integer userId, Integer categoryId) {
        Bundle bundle = new Bundle();
        if (userId != null) {
            bundle.putInt(RecommendationsActivity.USER_ID_KEY, userId);
        }
        if (categoryId != null) {
            bundle.putInt(RecommendationsActivity.CATEGORY_ID_KEY, categoryId);
        }
        RecommendationsFragment accountFragment = new RecommendationsFragment();
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
        return inflater.inflate(R.layout.fragment_recommendations, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        }else if (getArguments() != null) {
            Integer userId = null;
            Integer categoryId = null;
            if (getArguments().containsKey(RecommendationsActivity.USER_ID_KEY)) {
                userId = getArguments().getInt(RecommendationsActivity.USER_ID_KEY);
            }

            if (getArguments().containsKey(RecommendationsActivity.CATEGORY_ID_KEY)) {
                categoryId = getArguments().getInt(RecommendationsActivity.CATEGORY_ID_KEY);
            }

            recommendationsPresenter.setUserInfo(userId,categoryId);
        }
        recommendationsPresenter.loadCategoriesByEmployee(true);
    }


    private void setupViews() {
        swipeRefreshLayout.setColorSchemeResources(R.color.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recommendationsPresenter.loadCategoriesByEmployee(true);
            }
        });
        recommendationsAdapter = new RecommendationsAdapter(getActivity(),this);
        recommendationRecyclerView.setAdapter(recommendationsAdapter);
        recommendationRecyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setAutoMeasureEnabled(true);
        recommendationRecyclerView.setNestedScrollingEnabled(false);
        recommendationRecyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        recommendationsPresenter = belatrixConnectApplication.getApplicationComponent()
                .recommendationsComponent(new RecommendationsPresenterModule(this))
                .recommendationsPresenter();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        saveState(outState);
        super.onSaveInstanceState(outState);
    }

    private void restoreState(Bundle savedInstanceState) {
        List<Star> list = savedInstanceState.getParcelableArrayList(_RECOMMENDATION_LIST_KEY);
        recommendationsPresenter.loadPresenterState(list);
    }

    private void saveState(Bundle outState) {
        //outState.putParcelable(EMPLOYEE_KEY, accountRecommendationsPresenter.getEmployee());
    }


    @Override
    public void showProgressIndicator() {
        recommendationRecyclerView.setVisibility(View.GONE);
        setProgressViewVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressIndicator() {
        swipeRefreshLayout.setRefreshing(false);
        setProgressViewVisibility(View.GONE);
    }

    public void setProgressViewVisibility(int visibility) {
        if (categoriesProgressBar != null) {
            categoriesProgressBar.setVisibility(visibility);
        }
    }


    @Override
    public void showRecommendations(List<Star> data) {
        recommendationRecyclerView.setVisibility(View.VISIBLE);
        recommendationsAdapter.setData(data);
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
    public void onDestroyView() {
        recommendationsPresenter.cancelRequests();
        super.onDestroyView();
    }

    @Override
    public void onKeywordSelected(int position) {

    }
}
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
package com.belatrixsf.connect.ui.ranking;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.adapters.RankingListAdapter;
import com.belatrixsf.connect.entities.Employee;
import com.belatrixsf.connect.ui.account.AccountActivity;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.ui.common.views.DividerItemDecoration;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.Constants;
import com.belatrixsf.connect.utils.di.modules.presenters.RankingPresenterModule;

import java.util.List;

import butterknife.Bind;

/**
 * Created by icerrate on 28/04/2016.
 */
public class RankingFragment extends BelatrixConnectFragment implements RankingView, RankingListAdapter.RankingListClickListener {

    public static final String RANKING_KIND_KEY = "_ranking_kind_key";

    private RankingPresenter rankingPresenter;
    private RankingListAdapter rankingListAdapter;

    private ImageView photoImageView;
    @Bind(R.id.ranking) RecyclerView rankingRecyclerView;
    @Bind(R.id.progressBar) ProgressBar loadingProgressBar;
    @Bind(R.id.ranking_swipe_refresh) SwipeRefreshLayout rankingSwipeRefresh;
    @Bind(R.id.no_data_textview) TextView noDataTextView;

    public static RankingFragment newInstance(String kind) {
        Bundle bundle = new Bundle();
        bundle.putString(RANKING_KIND_KEY, kind);
        RankingFragment rankingFragment = new RankingFragment();
        rankingFragment.setArguments(bundle);
        return rankingFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ranking, container, false);
    }

    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        rankingPresenter = belatrixConnectApplication.getApplicationComponent()
                .rankingComponent(new RankingPresenterModule(this))
                .rankingPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(RANKING_KIND_KEY)) {
            initViews();
            String kind = getArguments().getString(RANKING_KIND_KEY);
            rankingPresenter.getRankingList(kind, Constants.DEFAULT_QUANTITY, false);
        }
    }

    private void initViews() {
        rankingListAdapter = new RankingListAdapter(this);
        rankingRecyclerView.setAdapter(rankingListAdapter);
        rankingRecyclerView.setNestedScrollingEnabled(false);
        rankingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rankingRecyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));
        rankingSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rankingListAdapter.clear();
                rankingPresenter.getRankingList(getArguments().getString(RANKING_KIND_KEY), Constants.DEFAULT_QUANTITY, true);
            }
        });
        rankingSwipeRefresh.setColorSchemeResources(R.color.swipe_refresh);
    }

    @Override
    public void showRankingList(List<Employee> rankingList) {
        rankingListAdapter.updateData(rankingList);
    }

    @Override
    public void onEmployeeClicked(int position, View view) {
        photoImageView = (ImageView) view.findViewById(R.id.contact_photo);
        rankingPresenter.employeeSelected(position);
    }

    @Override
    public void goToEmployeeProfile(Integer employeeId) {
        AccountActivity.startActivityAnimatingProfilePic(getActivity(), photoImageView, employeeId);
    }

    @Override
    public void showProgressIndicator() {
        if (loadingProgressBar != null) {
            loadingProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgressIndicator() {
        if (loadingProgressBar != null) {
            loadingProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideRefreshData() {
        if (rankingSwipeRefresh != null) {
            rankingSwipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onDestroyView() {
        rankingPresenter.cancelRequests();
        super.onDestroyView();
    }

    @Override
    public void showNoDataView() {
        noDataTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoDataView() {
        noDataTextView.setVisibility(View.GONE);
    }
}
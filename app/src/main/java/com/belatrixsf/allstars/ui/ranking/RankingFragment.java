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
package com.belatrixsf.allstars.ui.ranking;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.adapters.RankingListAdapter;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.ui.common.AllStarsFragment;
import com.belatrixsf.allstars.utils.AllStarsApplication;
import com.belatrixsf.allstars.utils.Constants;
import com.belatrixsf.allstars.utils.di.modules.presenters.RankingPresenterModule;

import java.util.List;

import butterknife.Bind;

/**
 * Created by icerrate on 28/04/2016.
 */
public class RankingFragment extends AllStarsFragment implements RankingView {

    @Bind(R.id.ranking) RecyclerView rankingRecyclerView;

    private RankingPresenter rankingPresenter;
    private RankingListAdapter rankingListAdapter;

    public static RankingFragment newInstance() {
        return new RankingFragment();
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
    protected void initDependencies(AllStarsApplication allStarsApplication) {
        rankingPresenter = allStarsApplication.getApplicationComponent()
                .rankingComponent(new RankingPresenterModule(this))
                .rankingPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        rankingPresenter.getRankingList(Constants.KIND_SCORE, Constants.DEFAULT_QUANTITY);
    }

    private void initViews() {
        rankingListAdapter = new RankingListAdapter();
        rankingRecyclerView.setAdapter(rankingListAdapter);
        rankingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void showRankingList(List<Employee> rankingList) {
        rankingListAdapter.updateData(rankingList);
    }
}
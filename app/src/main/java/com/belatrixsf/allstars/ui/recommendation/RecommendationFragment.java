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
package com.belatrixsf.allstars.ui.recommendation;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.adapters.RecommendationListAdapter;
import com.belatrixsf.allstars.entities.Recommendation;
import com.belatrixsf.allstars.ui.common.AllStarsFragment;
import com.belatrixsf.allstars.utils.AllStarsApplication;
import com.belatrixsf.allstars.utils.di.modules.presenters.RecommendationPresenterModule;

import java.util.List;

import butterknife.Bind;

/**
 * Created by icerrate on 25/04/2016.
 */
public class RecommendationFragment extends AllStarsFragment implements RecommendationView {

    @Bind(R.id.recommendations) RecyclerView recommendationRecyclerView;

    private RecommendationPresenter recommendationPresenter;
    private RecommendationListAdapter recommendationListAdapter;

    public static RecommendationFragment newInstance(Integer userId, Integer categoryId) {
        Bundle bundle = new Bundle();
        bundle.putInt(RecommendationActivity.USER_ID, userId);
        bundle.putInt(RecommendationActivity.SUBCATEGORY_ID, categoryId);
        RecommendationFragment recommendationFragment = new RecommendationFragment();
        recommendationFragment.setArguments(bundle);
        return recommendationFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recommendation, container, false);
    }

    @Override
    protected void initDependencies(AllStarsApplication allStarsApplication) {
        recommendationPresenter = allStarsApplication.getApplicationComponent()
                .recommendationComponent(new RecommendationPresenterModule(this))
                .recommendationPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        if (getArguments() != null && getArguments().containsKey(RecommendationActivity.USER_ID) && getArguments().containsKey(RecommendationActivity.SUBCATEGORY_ID)) {
            Integer userId = getArguments().getInt(RecommendationActivity.USER_ID);
            Integer categoryId = getArguments().getInt(RecommendationActivity.SUBCATEGORY_ID);
            recommendationPresenter.getRecommendationList(userId, categoryId);
        }
    }

    private void initViews() {
        recommendationListAdapter = new RecommendationListAdapter();
        recommendationRecyclerView.setAdapter(recommendationListAdapter);
        recommendationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void showRecommendations(List<Recommendation> recommendations) {
        recommendationListAdapter.updateData(recommendations);
    }
}

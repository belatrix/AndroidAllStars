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
package com.belatrixsf.allstars.ui.stars;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.adapters.StarsListAdapter;
import com.belatrixsf.allstars.entities.Star;
import com.belatrixsf.allstars.ui.common.AllStarsFragment;
import com.belatrixsf.allstars.utils.AllStarsApplication;
import com.belatrixsf.allstars.utils.di.modules.presenters.StarsListPresenterModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by icerrate on 25/04/2016.
 */
public class StarsListFragment extends AllStarsFragment implements StarsListView {

    public static final String STARS_KEY = "_stars_key";

    private StarsListPresenter starsListPresenter;
    private StarsListAdapter starsListAdapter;

    @Bind(R.id.stars) RecyclerView starsRecyclerView;

    public static StarsListFragment newInstance(Integer userId, Integer categoryId) {
        Bundle bundle = new Bundle();
        bundle.putInt(StarsListActivity.USER_ID, userId);
        bundle.putInt(StarsListActivity.SUBCATEGORY_ID, categoryId);
        StarsListFragment employeeStarsListFragment = new StarsListFragment();
        employeeStarsListFragment.setArguments(bundle);
        return employeeStarsListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stars_list, container, false);
    }

    @Override
    protected void initDependencies(AllStarsApplication allStarsApplication) {
        starsListPresenter = allStarsApplication.getApplicationComponent()
                .starsListComponent(new StarsListPresenterModule(this))
                .starsListPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        boolean hasArguments = (getArguments() != null && getArguments().containsKey(StarsListActivity.USER_ID) && getArguments().containsKey(StarsListActivity.SUBCATEGORY_ID));
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        }
        if (hasArguments) {
            Integer userId = getArguments().getInt(StarsListActivity.USER_ID);
            Integer categoryId = getArguments().getInt(StarsListActivity.SUBCATEGORY_ID);
            starsListPresenter.getStars(userId, categoryId);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        saveState(outState);
        super.onSaveInstanceState(outState);
    }

    private void restoreState(Bundle savedInstanceState) {
        List<Star> savedStars = savedInstanceState.getParcelableArrayList(STARS_KEY);
        starsListPresenter.setLoadedStars(savedStars);
    }

    private void saveState(Bundle outState) {
        List<Star> forSavingStars = starsListPresenter.getLoadedStars();
        if (forSavingStars != null && forSavingStars instanceof ArrayList) {
            outState.putParcelableArrayList(STARS_KEY, (ArrayList<Star>) forSavingStars);
        }
    }

    private void initViews() {
        starsListAdapter = new StarsListAdapter(getActivity());
        starsRecyclerView.setAdapter(starsListAdapter);
        starsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void showStars(List<Star> stars) {
        starsListAdapter.updateData(stars);
    }

}

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
package com.belatrixsf.connect.ui.stars;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.adapters.StarsListAdapter;
import com.belatrixsf.connect.entities.Keyword;
import com.belatrixsf.connect.entities.Star;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.ui.common.EndlessRecyclerOnScrollListener;
import com.belatrixsf.connect.ui.contacts.keyword.ContactsKeywordListActivity;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.di.modules.presenters.StarsListPresenterModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by icerrate on 25/04/2016.
 */
public class StarsListFragment extends BelatrixConnectFragment implements StarsListView, StarsListAdapter.KeywordClickListener {

    public static final String STARS_KEY = "_stars_key";
    public static final String EMPLOYEE_ID_KEY = "_employee_id_key";
    public static final String SUBCATEGORY_ID_KEY = "_sub_category_id_key";
    public static final String PAGINATION_RESPONSE_KEY = "_pagination_response_key";

    private StarsListPresenter starsListPresenter;
    private StarsListAdapter starsListAdapter;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

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
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        starsListPresenter = belatrixConnectApplication.getApplicationComponent()
                .starsListComponent(new StarsListPresenterModule(this))
                .starsListPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        boolean hasArguments = (getArguments() != null && getArguments().containsKey(StarsListActivity.USER_ID) && getArguments().containsKey(StarsListActivity.SUBCATEGORY_ID));
        if (savedInstanceState != null) {
            restorePresenterState(savedInstanceState);
        } else if (hasArguments) {
            Integer userId = getArguments().getInt(StarsListActivity.USER_ID);
            Integer categoryId = getArguments().getInt(StarsListActivity.SUBCATEGORY_ID);
            starsListPresenter.getStars(userId, categoryId);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        savePresenterState(outState);
        super.onSaveInstanceState(outState);
    }

    private void restorePresenterState(Bundle savedInstanceState) {
        List<Star> savedStars = savedInstanceState.getParcelableArrayList(STARS_KEY);
        Integer employeeId = savedInstanceState.getInt(EMPLOYEE_ID_KEY);
        Integer subCategoryId = savedInstanceState.getInt(SUBCATEGORY_ID_KEY);
        PaginatedResponse paginatedResponse = savedInstanceState.getParcelable(PAGINATION_RESPONSE_KEY);
        starsListPresenter.loadPresenterState(employeeId, subCategoryId, savedStars, paginatedResponse);
    }

    private void savePresenterState(Bundle outState) {
        List<Star> forSavingStars = starsListPresenter.getLoadedStars();
        if (forSavingStars != null && forSavingStars instanceof ArrayList) {
            outState.putParcelableArrayList(STARS_KEY, (ArrayList<Star>) forSavingStars);
        }
        outState.putInt(EMPLOYEE_ID_KEY, starsListPresenter.getEmployeeId());
        outState.putInt(SUBCATEGORY_ID_KEY, starsListPresenter.getSubCategoryId());
        outState.putParcelable(PAGINATION_RESPONSE_KEY, starsListPresenter.getStarPaginatedResponse());
    }

    private void initViews() {
        starsListAdapter = new StarsListAdapter(getActivity(), this);
        starsRecyclerView.setAdapter(starsListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        starsRecyclerView.setLayoutManager(linearLayoutManager);
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                starsListPresenter.callNextPage();
            }
        };
        starsRecyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
    }

    @Override
    public void showStars(List<Star> stars) {
        starsListAdapter.update(stars);
    }

    @Override
    public void showProgressIndicator() {
        if (starsListAdapter != null) {
            starsListAdapter.setLoading(true);
        }
        if (endlessRecyclerOnScrollListener != null) {
            endlessRecyclerOnScrollListener.setLoading(true);
        }
    }

    @Override
    public void hideProgressIndicator() {
        if (starsListAdapter != null) {
            starsListAdapter.setLoading(false);
        }
        if (endlessRecyclerOnScrollListener != null) {
            endlessRecyclerOnScrollListener.setLoading(false);
        }
    }

    @Override
    public void showCurrentPage(int currentPage) {
        endlessRecyclerOnScrollListener.setCurrentPage(currentPage);
    }

    @Override
    public void onKeywordSelected(int position) {
        starsListPresenter.onKeywordSelected(position);
    }

    @Override
    public void goToKeywordContacts(Keyword keyword) {
        Intent intent = new Intent(getActivity(), ContactsKeywordListActivity.class);
        intent.putExtra(ContactsKeywordListActivity.KEYWORD_KEY, keyword);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        starsListPresenter.cancelRequests();
        super.onDestroyView();
    }

}


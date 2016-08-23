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
package com.belatrixsf.connect.ui.event;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.adapters.EventListAdapter;
import com.belatrixsf.connect.entities.Event;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.ui.common.EndlessRecyclerOnScrollListener;
import com.belatrixsf.connect.ui.common.RecyclerOnItemClickListener;
import com.belatrixsf.connect.ui.common.views.searchingview.SearchingView;
import com.belatrixsf.connect.ui.event.detail.EventDetailActivity;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.KeyboardUtils;
import com.belatrixsf.connect.utils.di.modules.presenters.EventListPresenterModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by icerrate on 13/06/2016.
 */
public class EventListFragment extends BelatrixConnectFragment implements EventListView, RecyclerOnItemClickListener {

    public static final String EVENTS_KEY = "_events_key";
    public static final String SEARCH_TEXT_KEY = "_search_text_key";
    public static final String PAGING_KEY = "_paging_key";
    public static final String SEARCHING_KEY = "_searching_key";

    private EventListPresenter eventListPresenter;
    private EventListAdapter eventListAdapter;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

    private ImageView photoImageView;

    @Bind(R.id.events) RecyclerView eventsRecyclerView;
    @Bind(R.id.no_data_textview) TextView noDataTextView;

    public static EventListFragment newInstance() {
        return new EventListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_event_list, container, false);
    }

    @Override
    protected void initDependencies(BelatrixConnectApplication allStarsApplication) {
        eventListPresenter = allStarsApplication.getApplicationComponent()
                .eventListComponent(new EventListPresenterModule(this))
                .eventListPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        if (savedInstanceState != null) {
            restorePresenterState(savedInstanceState);
        }
        eventListPresenter.getEvents();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                eventListPresenter.searchEvents();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        savePresenterState(outState);
        super.onSaveInstanceState(outState);
    }

    private void restorePresenterState(Bundle savedInstanceState) {
        List<Event> events = savedInstanceState.getParcelableArrayList(EVENTS_KEY);
        boolean searching = savedInstanceState.getBoolean(SEARCHING_KEY, false);
        PaginatedResponse paging = savedInstanceState.getParcelable(PAGING_KEY);
        String searchText = savedInstanceState.getString(SEARCH_TEXT_KEY, null);
        eventListPresenter.loadPresenterState(events, paging, searchText, searching);
    }

    private void savePresenterState(Bundle outState) {
        outState.putBoolean(SEARCHING_KEY, eventListPresenter.isSearching());
        outState.putParcelable(PAGING_KEY, eventListPresenter.getEventsPaging());
        outState.putString(SEARCH_TEXT_KEY, eventListPresenter.getSearchText());
        List<Event> events = eventListPresenter.getEventsSync();
        if (events != null && events instanceof ArrayList) {
            outState.putParcelableArrayList(EVENTS_KEY, (ArrayList<Event>) events);
        }
    }

    private void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                eventListPresenter.callNextPage();
            }
        };
        eventListAdapter = new EventListAdapter(this);
        eventsRecyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
        eventsRecyclerView.setAdapter(eventListAdapter);
        eventsRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void showProgressIndicator() {
        if (eventListAdapter != null) {
            eventListAdapter.setLoading(true);
        }
        if (endlessRecyclerOnScrollListener != null) {
            endlessRecyclerOnScrollListener.setLoading(true);
        }
    }

    @Override
    public void hideProgressIndicator() {
        if (eventListAdapter != null) {
            eventListAdapter.setLoading(false);
        }
        if (endlessRecyclerOnScrollListener != null) {
            endlessRecyclerOnScrollListener.setLoading(false);
        }
    }

    @Override
    public void addEvents(List<Event> events) {
        eventListAdapter.add(events);
    }

    @Override
    public void showSearchActionMode() {
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
        }
    }

    @Override
    public void resetList() {
        eventListAdapter.reset();
    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(final ActionMode mode, Menu menu) {
            SearchingView searchingView = new SearchingView(getActivity());
            searchingView.setSearchingListener(new SearchingView.SearchingListener() {
                @Override
                public void onSearchingTextTyped(String searchText) {
                    eventListPresenter.getEvents(searchText);
                }
            });
            mode.setCustomView(searchingView);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            eventListPresenter.stopSearchingEvents();
            KeyboardUtils.hideKeyboard(getActivity(), getView());
        }
    };

    @Override
    public void onClick(View v) {
        photoImageView = ButterKnife.findById(v, R.id.event_picture);
        eventListPresenter.onEventClicked(v.getTag());
    }

    @Override
    public void goEventDetail(Integer id) {
        EventDetailActivity.startActivityAnimatingPic(getActivity(), photoImageView, id);
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
        eventListPresenter.cancelRequests();
        super.onDestroyView();
    }

}
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
package com.belatrixsf.connect.ui.event.notification;

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
import com.belatrixsf.connect.adapters.NotificationListAdapter;
import com.belatrixsf.connect.entities.Notification;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.ui.common.EndlessRecyclerOnScrollListener;
import com.belatrixsf.connect.ui.common.views.DividerItemDecoration;
import com.belatrixsf.connect.ui.event.detail.EventDetailActivity;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.di.modules.presenters.EventNewsPresenterModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by dvelasquez on 21/02/2017.
 */
public class EventNewsFragment extends BelatrixConnectFragment implements EventNewsView  {

    public static final String EVENT_NEWS_KEY = "_event_news_key";
    public static final String PAGING_KEY = "_paging_key";

    private EventNewsPresenter eventNewsPresenter;
    private NotificationListAdapter eventNewsListAdapter;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

    @Bind(R.id.news) RecyclerView newsRecyclerView;
    @Bind(R.id.no_data_textview) TextView noDataTextView;

    public static EventNewsFragment newInstance(Integer eventId) {
        Bundle bundle = new Bundle();
        if (eventId != null) {
            bundle.putInt(EventDetailActivity.EVENT_ID_KEY, eventId);
        }
        EventNewsFragment accountFragment = new EventNewsFragment();
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
        return inflater.inflate(R.layout.fragment_event_news, container, false);
    }

    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        eventNewsPresenter = belatrixConnectApplication.getApplicationComponent()
                .eventNewsComponent(new EventNewsPresenterModule(this))
                .eventNewsPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState == null) {
            initViews();
            int eventId = getArguments().getInt(EventDetailActivity.EVENT_ID_KEY);
            eventNewsPresenter.setEventId(eventId);
        } else {
            restoreState(savedInstanceState);
        }
        eventNewsPresenter.getEventNews();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        saveState(outState);
        super.onSaveInstanceState(outState);
    }

    private void initViews() {
        eventNewsListAdapter = new NotificationListAdapter();
        newsRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        newsRecyclerView.setLayoutManager(layoutManager);
        newsRecyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));


        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                eventNewsPresenter.callNextPage();
            }
        };
        newsRecyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);

        newsRecyclerView.setAdapter(eventNewsListAdapter);
    }

    private void restoreState(Bundle savedInstanceState) {
        List<Notification> events = savedInstanceState.getParcelableArrayList(EVENT_NEWS_KEY);
        PaginatedResponse paging = savedInstanceState.getParcelable(PAGING_KEY);
        eventNewsPresenter.loadPresenterState(events,paging);
    }

    private void saveState(Bundle outState) {
        outState.putParcelable(PAGING_KEY, eventNewsPresenter.getEventsPaging());
        List<Notification> events = eventNewsPresenter.getEventNewsList();
        if (events != null && events instanceof ArrayList) {
            outState.putParcelableArrayList(EVENT_NEWS_KEY, (ArrayList<Notification>) events);
        }
    }



    @Override
    public void resetList() {
        eventNewsListAdapter.reset();
    }

    @Override
    public void showNotificationList(List<Notification> list) {
        eventNewsListAdapter.update(list);
    }

    @Override
    public void showProgressIndicator() {
        if (eventNewsListAdapter != null) {
            eventNewsListAdapter.setLoading(true);
        }
        if (endlessRecyclerOnScrollListener != null) {
            endlessRecyclerOnScrollListener.setLoading(true);
        }
    }

    @Override
    public void hideProgressIndicator() {
        if (eventNewsListAdapter != null) {
            eventNewsListAdapter.setLoading(false);
        }
        if (endlessRecyclerOnScrollListener != null) {
            endlessRecyclerOnScrollListener.setLoading(false);
        }
    }


    @Override
    public void onDestroyView() {
        eventNewsPresenter.cancelRequests();
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
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
package com.belatrixsf.connect.ui.notifications;


import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.adapters.NotificationListAdapter;
import com.belatrixsf.connect.entities.Notification;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.ui.common.EndlessRecyclerOnScrollListener;
import com.belatrixsf.connect.ui.common.views.DividerItemDecoration;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.di.modules.presenters.NotificationListPresenterModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by icerrate on 20/06/2016.
 */
public class NotificationListFragment extends BelatrixConnectFragment implements NotificationListView {

    public static final String NOTIFICATIONS_KEY = "_notifications_key";
    public static final String PAGING_KEY = "_paging_key";

    private NotificationListPresenter notificationListPresenter;
    private NotificationListAdapter notificationListAdapter;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

    @Bind(R.id.notifications)
    RecyclerView notificationsRecyclerView;
    @Bind(R.id.refresh_notifications)
    SwipeRefreshLayout notificationsRefreshLayout;

    public static NotificationListFragment newInstance() {
        return new NotificationListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_notification_list, container, false);
    }

    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        notificationListPresenter = belatrixConnectApplication.getApplicationComponent()
                .notificationListComponent(new NotificationListPresenterModule(this))
                .notificationListPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        if (savedInstanceState != null) {
            restorePresenterState(savedInstanceState);
        }
        notificationListPresenter.getNotifications();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        savePresenterState(outState);
        super.onSaveInstanceState(outState);
    }

    private void restorePresenterState(Bundle savedInstanceState) {
        List<Notification> notifications = savedInstanceState.getParcelableArrayList(NOTIFICATIONS_KEY);
        PaginatedResponse paging = savedInstanceState.getParcelable(PAGING_KEY);
        notificationListPresenter.load(notifications, paging);
    }

    private void savePresenterState(Bundle outState) {
        outState.putParcelable(PAGING_KEY, notificationListPresenter.getNotificationsPaging());
        List<Notification> notifications = notificationListPresenter.getNotificationsSync();
        if (notifications != null && notifications instanceof ArrayList) {
            outState.putParcelableArrayList(NOTIFICATIONS_KEY, (ArrayList<Notification>) notifications);
        }
    }

    private void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                notificationListPresenter.callNextPage();
            }
        };
        notificationListAdapter = new NotificationListAdapter();
        notificationsRecyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
        notificationsRecyclerView.setAdapter(notificationListAdapter);
        notificationsRecyclerView.setLayoutManager(linearLayoutManager);
        notificationsRecyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));
        notificationsRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                notificationListAdapter.clear();
                notificationListPresenter.refreshNotifications();
            }
        });
        notificationsRefreshLayout.setColorSchemeResources(R.color.swipe_refresh);
    }

    @Override
    public void showProgressIndicator() {
        notificationListAdapter.setLoading(true);
        endlessRecyclerOnScrollListener.setLoading(true);
    }

    @Override
    public void hideProgressIndicator() {
        notificationListAdapter.setLoading(false);
        endlessRecyclerOnScrollListener.setLoading(false);
        notificationsRefreshLayout.setRefreshing(false);
    }

    @Override
    public void addNotifications(List<Notification> notifications) {
        notificationListAdapter.add(notifications);
    }

    @Override
    public void resetList() {
        notificationListAdapter.reset();
    }

    @Override
    public void onDestroyView() {
        notificationListPresenter.cancelRequests();
        super.onDestroyView();
    }
}
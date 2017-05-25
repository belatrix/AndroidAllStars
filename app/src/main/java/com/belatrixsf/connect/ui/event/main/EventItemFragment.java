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
package com.belatrixsf.connect.ui.event.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.adapters.EventListAdapter;
import com.belatrixsf.connect.entities.Event;
import com.belatrixsf.connect.managers.PreferencesManager;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.ui.common.RecyclerOnItemClickListener;
import com.belatrixsf.connect.ui.event.EventListActivity;
import com.belatrixsf.connect.ui.event.detail.EventDetailActivity;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.di.modules.presenters.EventItemPresenterModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dvelasquez on 20/02/2017.
 */
public class EventItemFragment extends BelatrixConnectFragment implements EventItemView, RecyclerOnItemClickListener{

    public static final String EVENTS_KEY = "_event_list";
    public static final String EVENTS_TYPE = "_event_type";
    public static final String EVENTS_TITLE = "_event_title";
    private String kindEvent;
    private String title;
    private ImageView photoImageView;

    private EventItemPresenter eventItemPresenter;
    private EventListAdapter eventListAdapter;

    @Bind(R.id.progressBar) ProgressBar progressBar;
    @Bind(R.id.events_recycler_view) RecyclerView eventsRecyclerView;
    @Bind(R.id.event_title_textview) TextView eventTitleTextView;
    @Bind(R.id.no_data_textview) TextView noDataTextView;
    @Bind(R.id.event_more_button) Button eventMoreButton;

    public static EventItemFragment newInstance(String _kindEvent, String _title) {
        EventItemFragment fragment = new EventItemFragment();
        fragment.kindEvent = _kindEvent;
        fragment.title = _title;
        return fragment;
    }

    @OnClick(R.id.event_more_button)
    public void onClickMore(){
        EventListActivity.startActivity(getActivity(),kindEvent,title);
    }


    @Override
    public void showProgressIndicator() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressIndicator() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_event_item, container, false);
    }

    @Override
    protected void initDependencies(BelatrixConnectApplication allStarsApplication) {
        eventItemPresenter = allStarsApplication.getApplicationComponent()
                .eventContainerComponent(new EventItemPresenterModule(this)).eventContainerPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        }
        eventItemPresenter.setEmployeeId(PreferencesManager.get().getEmployeeId());
        eventItemPresenter.getEvents(kindEvent);
    }

    @Override
    public void onResume() {
        super.onResume();
        eventItemPresenter.setEmployeeId(PreferencesManager.get().getEmployeeId());
        eventItemPresenter.getEvents(kindEvent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        saveState(outState);
        super.onSaveInstanceState(outState);
    }

    private void restoreState(Bundle savedInstanceState) {
        List<Event> events = savedInstanceState.getParcelableArrayList(EVENTS_KEY);
        eventItemPresenter.loadPresenterState(events);
        title = savedInstanceState.getString(EVENTS_TITLE);
        kindEvent = savedInstanceState.getString(EVENTS_TYPE);
        eventItemPresenter.setEmployeeId(PreferencesManager.get().getEmployeeId());
    }

    private void saveState(Bundle outState) {
        List<Event> events = eventItemPresenter.getEventsListSync();
        if (events != null && events instanceof ArrayList) {
            outState.putParcelableArrayList(EVENTS_KEY, (ArrayList<Event>) events);
        }
        outState.putString(EVENTS_TITLE,title);
        outState.putString(EVENTS_TYPE,kindEvent);
    }

    private void initViews() {
        eventTitleTextView.setText(title);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        eventsRecyclerView.setLayoutManager(layoutManager);
        eventListAdapter = new EventListAdapter(this);
        eventsRecyclerView.setAdapter(eventListAdapter);
    }



    @Override
    public void onDestroyView() {
        eventItemPresenter.cancelRequests();
        super.onDestroyView();
    }

    @Override
    public void goEventDetail(Integer id) {
        EventDetailActivity.startActivityAnimatingPic(getActivity(), photoImageView, id);
    }

    @Override
    public void showEventList(List<Event> eventList) {
        eventListAdapter.update(eventList);
    }

    @Override
    public void hideRefreshData() {

    }

    @Override
    public void showNoDataView() {
        noDataTextView.setVisibility(View.VISIBLE);
        eventListAdapter.update(new ArrayList<Event>());
    }

    @Override
    public void hideNoDataView() {
        noDataTextView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        photoImageView = ButterKnife.findById(v, R.id.event_picture);
        eventItemPresenter.onEventClicked(v.getTag());
    }
}
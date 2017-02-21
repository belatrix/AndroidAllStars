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

import com.belatrixsf.connect.entities.Event;
import com.belatrixsf.connect.managers.PreferencesManager;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.services.ServiceRequest;
import com.belatrixsf.connect.services.contracts.EventService;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by icerrate on 13/06/2016.
 */
public class EventListPresenter extends BelatrixConnectPresenter<EventListView> {

    private EventService eventService;
    private List<Event> eventsList = new ArrayList<>();
    private PaginatedResponse eventsPaging = new PaginatedResponse();
    private ServiceRequest searchingServiceRequest;
    private String eventType;
    private String eventTitle;

    public String getEventTitle() {
        return eventTitle;
    }

    public String getEventType() {
        return eventType;
    }

    @Inject
    public EventListPresenter(EventListView view, EventService eventsService) {
        super(view);
        this.eventService = eventsService;
    }

    public void onEventClicked(Object object) {
        if (object != null && object instanceof Event) {
            Event event = (Event) object;
            view.goEventDetail(event.getId());
        }
    }



    public void callNextPage() {
        if (eventsPaging.getNext() != null) {
            getEventsInternal();
        }
    }


    /*
    public void getEvents() {
        view.resetList();
        if (eventsList.isEmpty()) {
            getEventsInternal();
        } else {
            view.addEvents(eventsList);
        }
    }
*/
    public void getEvents() {
        if (searchingServiceRequest != null) {
            searchingServiceRequest.cancel();
        }
        reset();
        getEventsInternal();
    }

    private void getEventsInternal() {
        view.showProgressIndicator();
        view.hideNoDataView();
        int storedEmployeeId = PreferencesManager.get().getEmployeeId();
        searchingServiceRequest = eventService.getEventList(
                eventType,
                storedEmployeeId,
                eventsPaging.getNextPage(),
                new PresenterCallback<PaginatedResponse<Event>>() {
                    @Override
                    public void onSuccess(PaginatedResponse<Event> eventListResponse) {
                        eventsPaging.setCount(eventListResponse.getCount());
                        eventsPaging.setNext(eventListResponse.getNext());
                        eventsList.addAll(eventListResponse.getResults());
                        if (!eventListResponse.getResults().isEmpty()) {
                            view.addEvents(eventListResponse.getResults());
                        } else {
                            view.showNoDataView();
                        }
                        view.hideProgressIndicator();
                    }
                });
    }

    @Override
    public void cancelRequests() {
        eventService.cancelAll();
    }


    public void setEventInfo(String eventType, String eventTitle){
        this.eventType = eventType;
        this.eventTitle = eventTitle;
        view.showEventTitle(eventTitle);
    }

    private void reset() {
        eventsList.clear();
        view.resetList();
        eventsPaging.reset();
    }

    // saving state stuff

    public void loadPresenterState(List<Event> events, PaginatedResponse contactsPaging) {
        if (events != null) {
            this.eventsList.addAll(events);
        }
        this.eventsPaging = contactsPaging;
    }


    public PaginatedResponse getEventsPaging() {
        return eventsPaging;
    }

    public List<Event> getEventsSync() {
        return eventsList;
    }


}
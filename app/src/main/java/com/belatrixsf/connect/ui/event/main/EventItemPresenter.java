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

import com.belatrixsf.connect.entities.Event;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.services.contracts.EventService;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by icerrate on 13/06/2016.
 * modified by dvelasquez on 20/02/2017
 */
public class EventItemPresenter extends BelatrixConnectPresenter<EventItemView> {

    private EventService eventService;
    private List<Event> eventList = new ArrayList<>();
    private int employeeId;

    @Inject
    public EventItemPresenter(EventItemView view, EventService eventsService) {
        super(view);
        this.eventService = eventsService;
    }

    public List<Event> getEventsListSync() {
        return eventList;
    }

    public void onEventClicked(Object object) {
        if (object != null && object instanceof Event) {
            Event event = (Event) object;
            view.goEventDetail(event.getId());
        }
    }


    public void getEvents(String eventType) {
        if (eventList.isEmpty()) {
            getEventsInternal(eventType);
        } else {
            view.showEventList(eventList);
        }
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    private void getEventsInternal(String eventType) {
        view.showProgressIndicator();
        view.hideNoDataView();
        eventService.getEventList(
                eventType,employeeId,1,
                new PresenterCallback<PaginatedResponse<Event>>() {
                    @Override
                    public void onSuccess(PaginatedResponse<Event> eventListResponse) {
                        if (!eventListResponse.getResults().isEmpty()) {
                            view.showEventList(eventListResponse.getResults());
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

    private void reset() {
        eventList.clear();
    }

    // saving state stuff

    public void loadPresenterState(List<Event> eventList) {
        if (eventList != null) {
            this.eventList.addAll(eventList);
        }

    }

}
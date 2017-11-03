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
package com.belatrixsf.connect.ui.event.detail;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.entities.Event;
import com.belatrixsf.connect.networking.retrofit.responses.EventParticipantDetailResponse;
import com.belatrixsf.connect.services.contracts.EventService;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;
import com.belatrixsf.connect.utils.BelatrixConnectCallback;
import com.belatrixsf.connect.utils.DateUtils;
import com.belatrixsf.connect.utils.ServiceError;

import javax.inject.Inject;

/**
 * Created by icerrate on 27/06/2016.
 * Modified by dvelasquez on 21/02/2017
 */
public class EventDetailPresenter extends BelatrixConnectPresenter<EventDetailView> {

    protected EventService eventService;
    protected Event event;
    protected Integer eventId;

    private Integer employeeId;

    @Inject
    public EventDetailPresenter(EventDetailView view, EventService eventService) {
        super(view);
        this.eventService = eventService;
    }


    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public void loadPresenterState(Event event) {
        this.event = event;
        showEventDetail();
    }


    public void loadEventDetail(){
        view.showProgressDialog();
        eventService.getEventDetail(eventId, employeeId, new PresenterCallback<Event>() {
            @Override
            public void onSuccess(Event eventResponse) {
                event = eventResponse;
                showEventDetail();
                view.dismissProgressDialog();
            }


            @Override
            public void onFailure(ServiceError serviceError) {
                view.dismissProgressDialog();
                super.onFailure(serviceError);
            }
        });
    }

    /*
    public void loadEventDetail() {
        view.showProgressDialog();
        if (guestId != null) {
            eventService.getEventParticipantDetail(eventId, guestId, participantDetailResponseBelatrixConnectCallback);
        } else {
            eventService.getEventCollaboratorDetail(eventId, employeeId, eventDetailCallback);
        }
    }*/

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    private void showEventDetail() {
        String date = event.getDatetime();
        String formattedDate = date != null && !date.isEmpty() ? DateUtils.formatDate(date, DateUtils.DATE_FORMAT_3, DateUtils.DATE_FORMAT_4) : getString(R.string.no_data_option);
        String title = event.getName() != null && !event.getName().isEmpty() ? event.getName() : getString(R.string.no_data_option);
        String description = event.getDescription() != null && !event.getDescription().isEmpty() ? event.getDescription() : getString(R.string.no_data_option);
        view.showDateTime(formattedDate);
        view.showTitle(title);
        view.showDescription(description);
        view.showPicture(event.getPicture());
        refreshRegisterEvent();
    }

    private void refreshRegisterEvent() {
        /*
        if (event.isRegistered()) {
                view.enableUnregister();
            } else {
                view.enableRegister();
        }*/
        view.enableRegister();
    }


    @Override
    public void cancelRequests() {
        eventService.cancelAll();
    }

    public Event getEvent() {
        return event;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void requestRegister(final String action) {
        view.showProgressDialog();
        eventService.registerActionEvent(eventId, employeeId, action, new PresenterCallback<Event>() {
            @Override
            public void onSuccess(Event event) {
                view.dismissProgressDialog();
                EventDetailPresenter.this.event = event;
                refreshRegisterEvent();
                if (event.isRegistered()){
                     view.showRegisterResult();
                } else {
                    view.showUnregisterResult();
                }
            }

            @Override
            public void onFailure(ServiceError serviceError) {
                view.dismissProgressDialog();
                super.onFailure(serviceError);
            }
        });
    }


    public Integer getEmployeeId() {
        return employeeId;
    }


}
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
import com.belatrixsf.connect.services.contracts.GuestService;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;
import com.belatrixsf.connect.utils.BelatrixConnectCallback;
import com.belatrixsf.connect.utils.DateUtils;

import javax.inject.Inject;

/**
 * Created by icerrate on 27/06/2016.
 */
public class EventDetailPresenter extends BelatrixConnectPresenter<EventDetailView> {

    protected EventService eventService;
    protected Event event;
    protected Integer eventId;

    private Integer employeeId;
    private Integer guestId;

    @Inject
    public EventDetailPresenter(EventDetailView view, EventService eventService) {
        super(view);
        this.eventService = eventService;
    }

    public void setGuestId(Integer guestId) {
        this.guestId = guestId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public void loadPresenterState(Event event) {
        this.event = event;
        showEventDetail();
    }

    public void loadEventDetail() {
        view.showProgressDialog();
        if (guestId != null) {
            eventService.getEventParticipantDetail(eventId, guestId, participantDetailResponseBelatrixConnectCallback);
        } else {
            eventService.getEventCollaboratorDetail(eventId, employeeId, eventDetailCallback);
        }
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    private void showEventDetail() {
        String date = event.getDatetime();
        String formattedDate = date != null && !date.isEmpty() ? DateUtils.formatDate(date, DateUtils.DATE_FORMAT_3, DateUtils.DATE_FORMAT_4) : getString(R.string.no_data_option);
        String location = event.getLocation() != null && !event.getLocation().isEmpty() ? event.getLocation() : getString(R.string.no_data_option);
        String title = event.getTitle() != null && !event.getTitle().isEmpty() ? event.getTitle() : getString(R.string.no_data_option);
        String description = event.getDescription() != null && !event.getDescription().isEmpty() ? event.getDescription() : getString(R.string.no_data_option);
        String collaboratorsCount = event.getCollaborators() != null ? String.format(getString(R.string.event_collaborators), String.valueOf(event.getCollaborators())) : getString(R.string.event_collaborators_no_data);
        String participantsCount = event.getParticipants() != null ? String.format(getString(R.string.event_participants), String.valueOf(event.getParticipants())) : getString(R.string.event_participants_no_data);

        view.showDateTime(formattedDate);
        view.showLocation(location);
        view.showTitle(title);
        view.showDescription(description);
        view.showCollaboratorsCount(collaboratorsCount);
        view.showParticipantsCount(participantsCount);
        view.showPicture(event.getPicture());


        //TODO: future implementation

        /*if (event.isRegistrationAvailable()) {
            view.showRegister();
            if (event.isRegistered()) {
                view.enableUnregister();
            } else {
                view.enableRegister();
            }
        } else {
            view.hideRegister();
        }*/
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

    public void requestRegister() {
        view.showProgressDialog();
        if (employeeId != null) {
            eventService.registerCollaborator(eventId, employeeId, eventDetailCallback);
        } else {
            eventService.registerParticipant(eventId, guestId, participantDetailResponseBelatrixConnectCallback);
        }
    }

    private PresenterCallback<Event> eventDetailCallback = new PresenterCallback<Event>() {
        @Override
        public void onSuccess(Event event) {
            view.dismissProgressDialog();
            EventDetailPresenter.this.event = event;
            showEventDetail();
        }
    };

    private BelatrixConnectCallback<EventParticipantDetailResponse> participantDetailResponseBelatrixConnectCallback =  new PresenterCallback<EventParticipantDetailResponse>() {
        @Override
        public void onSuccess(EventParticipantDetailResponse eventParticipantDetailResponse) {
            view.dismissProgressDialog();
            EventDetailPresenter.this.event = eventParticipantDetailResponse.getEvent();
            EventDetailPresenter.this.event.setIsRegistered(eventParticipantDetailResponse.isRegistered());
            showEventDetail();
        }
    };

    public Integer getEmployeeId() {
        return employeeId;
    }

    public Integer getGuestId() {
        return guestId;
    }

    public void requestUnregister() {
        view.showProgressDialog();
        if (employeeId != null) {
            eventService.unregisterCollaborator(eventId, employeeId, eventDetailCallback);
        } else {
            eventService.unregisterParticipant(eventId, guestId, eventDetailCallback);
        }
    }

}
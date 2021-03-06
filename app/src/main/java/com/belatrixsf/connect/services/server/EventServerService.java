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
package com.belatrixsf.connect.services.server;

import com.belatrixsf.connect.entities.Event;
import com.belatrixsf.connect.entities.Notification;
import com.belatrixsf.connect.networking.retrofit.api.EventAPI;
import com.belatrixsf.connect.networking.retrofit.responses.EventParticipantDetailResponse;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.services.BelatrixConnectBaseService;
import com.belatrixsf.connect.services.ServerServiceRequest;
import com.belatrixsf.connect.services.ServiceRequest;
import com.belatrixsf.connect.services.contracts.EventService;
import com.belatrixsf.connect.utils.BelatrixConnectCallback;

import retrofit2.Call;

/**
 * Created by icerrate on 13/06/2016.
 */
public class EventServerService extends BelatrixConnectBaseService implements EventService {

    private EventAPI eventAPI;

    public EventServerService(EventAPI eventAPI) {
        this.eventAPI = eventAPI;
    }

    @Override
    public ServiceRequest getEventSearchList(String searchTerm, Integer page, BelatrixConnectCallback<PaginatedResponse<Event>> callback) {
        Call<PaginatedResponse<Event>> call = eventAPI.getEventSearchList(searchTerm, page);
        ServiceRequest<PaginatedResponse<Event>> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest getEventDetail(Integer eventId, BelatrixConnectCallback<Event> callback) {
        Call<Event> call = eventAPI.getEventDetail(eventId);
        ServiceRequest<Event> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest getEventParticipantDetail(Integer eventId, Integer guestId, BelatrixConnectCallback<EventParticipantDetailResponse> callback) {
        Call<EventParticipantDetailResponse> call = eventAPI.getEventParticipantDetail(eventId, guestId);
        ServiceRequest<EventParticipantDetailResponse> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest getEventCollaboratorDetail(Integer eventId, Integer employeeId, BelatrixConnectCallback<Event> callback) {
        Call<Event> call = eventAPI.getEventCollaboratorDetail(eventId, employeeId);
        ServiceRequest<Event> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest registerCollaborator(int eventId, int employeeId, BelatrixConnectCallback<Event> callback) {
        Call<Event> call = eventAPI.registerCollaborator(eventId, employeeId);
        ServiceRequest<Event> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest registerParticipant(int eventId, int employeeId, BelatrixConnectCallback<EventParticipantDetailResponse> callback) {
        Call<EventParticipantDetailResponse> call = eventAPI.registerParticipant(eventId, employeeId);
        ServiceRequest<EventParticipantDetailResponse> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest unregisterCollaborator(int eventId, int employeeId, BelatrixConnectCallback<Event> callback) {
        Call<Event> call = eventAPI.unregisterCollaborator(eventId, employeeId);
        ServiceRequest<Event> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest unregisterParticipant(int eventId, int employeeId, BelatrixConnectCallback<Event> callback) {
        Call<Event> call = eventAPI.unregisterParticipant(eventId, employeeId);
        ServiceRequest<Event> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }


    @Override
    public ServiceRequest getEventList(String eventType, int employeeId, Integer page, BelatrixConnectCallback<PaginatedResponse<Event>> callback) {
        Call<PaginatedResponse<Event>> call = eventAPI.getEventList(eventType,employeeId, page);
        ServiceRequest<PaginatedResponse<Event>> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest getEventDetail(int eventId, int employeeId, BelatrixConnectCallback<Event> callback) {
        Call<Event> call = eventAPI.getEventDetail(eventId, employeeId);
        ServiceRequest<Event> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }


    @Override
    public ServiceRequest registerActionEvent(int eventId, int employeeId, String action, BelatrixConnectCallback<Event> callback) {
        Call<Event> call = eventAPI.registerActionEvent(eventId,employeeId,action);
        ServiceRequest<Event> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }


    @Override
    public ServiceRequest getEventNews(int eventId, Integer page, BelatrixConnectCallback<PaginatedResponse<Notification>> callback) {
        Call<PaginatedResponse<Notification>> call = eventAPI.getEventNews(eventId,page);
        ServiceRequest<PaginatedResponse<Notification>> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

}
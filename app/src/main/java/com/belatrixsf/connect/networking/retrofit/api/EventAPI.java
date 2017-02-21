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
package com.belatrixsf.connect.networking.retrofit.api;

import com.belatrixsf.connect.entities.Event;
import com.belatrixsf.connect.networking.retrofit.responses.EventParticipantDetailResponse;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by icerrate on 13/06/2016.
 */
public interface EventAPI {

    @GET(ServerPaths.EVENT_LIST)
    Call<PaginatedResponse<Event>> getEventSearchList(@Query(ServerPaths.SEARCH_TERM) String searchTerm, @Query(ServerPaths.QUERY_PAGE) Integer page);

    @GET(ServerPaths.EVENT_DETAIL)
    Call<Event> getEventDetail(@Path(ServerPaths.EVENT_ID) Integer eventId);

    @GET(ServerPaths.EVENT_PARTICIPANT_DETAIL)
    Call<EventParticipantDetailResponse> getEventParticipantDetail(@Path(ServerPaths.EVENT_ID) Integer eventId, @Path(ServerPaths.GUEST_ID) Integer guestId);

    @GET(ServerPaths.EVENT_COLLABORATOR_DETAIL)
    Call<Event> getEventCollaboratorDetail(@Path(ServerPaths.EVENT_ID) Integer eventId, @Path(ServerPaths.EMPLOYEE_ID) Integer employeeId);

    @PUT(ServerPaths.EVENT_REGISTER_COLLABORATOR)
    Call<Event> registerCollaborator(@Path(ServerPaths.EVENT_ID) int eventId, @Path(ServerPaths.EMPLOYEE_ID) int employeeId);

    @PUT(ServerPaths.EVENT_REGISTER_PARTICIPANT)
    Call<EventParticipantDetailResponse> registerParticipant(@Path(ServerPaths.EVENT_ID) int eventId, @Path(ServerPaths.GUEST_ID) int guestId);

    @PUT(ServerPaths.EVENT_UNREGISTER_COLLABORATOR)
    Call<Event> unregisterCollaborator(@Path(ServerPaths.EVENT_ID) int eventId, @Path(ServerPaths.EMPLOYEE_ID) int employeeId);

    @PUT(ServerPaths.EVENT_UNREGISTER_PARTICIPANT)
    Call<Event> unregisterParticipant(@Path(ServerPaths.EVENT_ID) int eventId, @Path(ServerPaths.GUEST_ID) int guestId);

    //new API
    @GET(ServerPaths.EVENT_LIST_BY_TYPE)
    Call<PaginatedResponse<Event>> getEventList(@Path(ServerPaths.EVENT_TYPE) String eventType, @Path(ServerPaths.EMPLOYEE_ID) int employeeId, @Query(ServerPaths.QUERY_PAGE) Integer page);

    @GET(ServerPaths.EVENT_LIST_DETAIL)
    Call<Event> getEventDetail(@Path(ServerPaths.EVENT_ID) int eventId, @Path(ServerPaths.EMPLOYEE_ID) int employeeId);


}

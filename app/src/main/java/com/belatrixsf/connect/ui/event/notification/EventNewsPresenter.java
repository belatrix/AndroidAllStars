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

import com.belatrixsf.connect.entities.Employee;
import com.belatrixsf.connect.entities.Notification;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.services.ServiceRequest;
import com.belatrixsf.connect.services.contracts.EmployeeService;
import com.belatrixsf.connect.services.contracts.EventService;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;
import com.belatrixsf.connect.ui.ranking.RankingView;
import com.belatrixsf.connect.utils.ServiceError;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by icerrate on 28/04/2016.
 */
public class EventNewsPresenter extends BelatrixConnectPresenter<EventNewsView> {

    private EventService eventService;
    private List<Notification> eventNewsList = new ArrayList<>();
    private PaginatedResponse eventsPaging = new PaginatedResponse();
    private int eventId;

    public void callNextPage() {
        if (eventsPaging.getNext() != null) {
            getEventNewsInternal();
        }
    }

    @Inject
    public EventNewsPresenter(EventNewsView view, EventService eventService) {
        super(view);
        this.eventService = eventService;
    }


    public void setEventId(int eventId){
        this.eventId = eventId;
    }

    public PaginatedResponse getEventsPaging() {
        return eventsPaging;
    }

    public List<Notification> getEventNewsList() {
        return eventNewsList;
    }

    public void getEventNews() {
        reset();
        getEventNewsInternal();
    }

    private void reset() {
        eventNewsList.clear();
        view.resetList();
        eventsPaging.reset();
    }


    private void getEventNewsInternal() {
        view.showProgressIndicator();
        view.hideNoDataView();

        eventService.getEventNews(
                eventId,
                 eventsPaging.getNextPage(),
                new PresenterCallback<PaginatedResponse<Notification>>() {
                    @Override
                    public void onSuccess(PaginatedResponse<Notification> notificationPaginatedResponse) {
                        eventsPaging.setCount(notificationPaginatedResponse.getCount());
                        eventsPaging.setNext(notificationPaginatedResponse.getNext());
                        eventNewsList.addAll(notificationPaginatedResponse.getResults());
                        if (!notificationPaginatedResponse.getResults().isEmpty()) {
                            view.showNotificationList(eventNewsList);
                        } else {
                            view.showNoDataView();
                        }
                        view.hideProgressIndicator();
                    }

                });
    }


    public void loadPresenterState(List<Notification> list,PaginatedResponse eventsPaging){
       this.eventNewsList = list;
        this.eventsPaging = eventsPaging;
    }

    @Override
    public void cancelRequests() {
        eventService.cancelAll();
    }

}

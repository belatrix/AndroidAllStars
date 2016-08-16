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

import com.belatrixsf.connect.entities.Notification;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.services.ServiceRequest;
import com.belatrixsf.connect.services.contracts.EmployeeService;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;
import com.belatrixsf.connect.utils.ServiceError;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by icerrate on 20/06/2016.
 */
public class NotificationListPresenter extends BelatrixConnectPresenter<NotificationListView> {

    private EmployeeService employeeService;
    private List<Notification> notifications = new ArrayList<>();
    private PaginatedResponse notificationsPaging = new PaginatedResponse();
    private ServiceRequest searchingServiceRequest;

    @Inject
    public NotificationListPresenter(NotificationListView view, EmployeeService employeeService) {
        super(view);
        this.employeeService = employeeService;
    }

    public void refreshNotifications() {
        reset();
        getNotificationsInternal();
    }

    public void callNextPage() {
        if (notificationsPaging.getNext() != null) {
            getNotificationsInternal();
        }
    }

    public void getNotifications() {
        view.resetList();
        if (notifications.isEmpty()) {
            getNotificationsInternal();
        } else {
            view.addNotifications(notifications);
        }
    }

    private void getNotificationsInternal() {
        view.showProgressIndicator();
        view.hideNoDataView();
        searchingServiceRequest = employeeService.getEmployeeNotifications(
            notificationsPaging.getNextPage(),
            new PresenterCallback<PaginatedResponse<Notification>>() {
                @Override
                public void onSuccess(PaginatedResponse<Notification> notificationsResponse) {
                    notificationsPaging.setCount(notificationsResponse.getCount());
                    notificationsPaging.setNext(notificationsResponse.getNext());
                    notifications.addAll(notificationsResponse.getResults());
                    if(!notificationsResponse.getResults().isEmpty()) {
                        view.addNotifications(notificationsResponse.getResults());
                    } else {
                        view.showNoDataView();
                    }
                    view.hideProgressIndicator();
                }
            });
    }

    @Override
    public void cancelRequests() {

    }

    private void reset() {
        notifications.clear();
        view.resetList();
        notificationsPaging.reset();
    }

    // saving state stuff

    public void load(List<Notification> notifications, PaginatedResponse notificationsPaging) {
        if (notifications != null) {
            this.notifications.addAll(notifications);
        }
        this.notificationsPaging = notificationsPaging;
    }

    public PaginatedResponse getNotificationsPaging() {
        return notificationsPaging;
    }

    public List<Notification> getNotificationsSync() {
        return notifications;
    }

}
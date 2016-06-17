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
package com.belatrixsf.connect.services;


import com.belatrixsf.connect.services.contracts.BelatrixConnectService;
import com.belatrixsf.connect.utils.BelatrixConnectCallback;
import com.belatrixsf.connect.utils.ServiceError;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Carlos Piñan
 */
public abstract class BelatrixConnectBaseService implements BelatrixConnectService {

    private List<ServiceRequest> serviceRequestList;

    protected <T> void enqueue(final ServiceRequest<T> serviceRequest, final BelatrixConnectCallback<T> belatrixConnectCallback) {
        if (serviceRequestList == null) {
            serviceRequestList = new ArrayList<>();
        }
        serviceRequestList.add(serviceRequest);
        serviceRequest.enqueue(new BelatrixConnectCallback<T>() {
            @Override
            public void onSuccess(T t) {
                dequeue(serviceRequest);
                belatrixConnectCallback.onSuccess(t);
            }

            @Override
            public void onFailure(ServiceError serviceError) {
                dequeue(serviceRequest);
                belatrixConnectCallback.onFailure(serviceError);
            }
        });
    }

    protected void dequeue(ServiceRequest serviceRequest) {
        serviceRequestList.remove(serviceRequest);
    }

    @Override
    public void cancelAll() {
        if (serviceRequestList != null && !serviceRequestList.isEmpty()) {
            for (ServiceRequest serviceRequest : serviceRequestList) {
                serviceRequest.cancel();
            }
            serviceRequestList.clear();
        }
    }

}
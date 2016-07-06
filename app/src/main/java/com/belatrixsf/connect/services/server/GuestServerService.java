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

import com.belatrixsf.connect.entities.Guest;
import com.belatrixsf.connect.networking.retrofit.api.GuestAPI;
import com.belatrixsf.connect.services.BelatrixConnectBaseService;
import com.belatrixsf.connect.services.ServerServiceRequest;
import com.belatrixsf.connect.services.ServiceRequest;
import com.belatrixsf.connect.services.contracts.GuestService;
import com.belatrixsf.connect.utils.BelatrixConnectCallback;

import retrofit2.Call;


/**
 * Created by icerrate on 22/06/2016.
 */
public class GuestServerService extends BelatrixConnectBaseService implements GuestService {

    private GuestAPI guestAPI;

    public GuestServerService(GuestAPI guestAPI) {
        this.guestAPI = guestAPI;
    }

    @Override
    public ServiceRequest authenticateGuest(Guest guest, BelatrixConnectCallback<Guest> callback) {
        Call<Guest> call = guestAPI.authenticateGuest(guest);
        ServiceRequest<Guest> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest getGuest(int guestId, BelatrixConnectCallback<Guest> callback) {
        Call<Guest> call = guestAPI.getGuest(guestId);
        ServiceRequest<Guest> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

}
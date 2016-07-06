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
package com.belatrixsf.connect.managers;

import com.belatrixsf.connect.entities.Guest;
import com.belatrixsf.connect.services.contracts.GuestService;
import com.belatrixsf.connect.utils.BelatrixConnectCallback;
import com.belatrixsf.connect.utils.ServiceError;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by icerrate on 22/06/2016.
 */
@Singleton
public class GuestManager {

    private GuestService guestService;
    private Guest guest;

    @Inject
    public GuestManager(GuestService guestService) {
        this.guestService = guestService;
    }

    public void loginAsGuest(Guest guest, final BelatrixConnectCallback<Guest> callback) {
        guestService.authenticateGuest(guest, new BelatrixConnectCallback<Guest>() {
            @Override
            public void onSuccess(final Guest guestAuthenticationResponse) {
                PreferencesManager.get().saveGuestId(guestAuthenticationResponse.getId());
                guestService.getGuest(guestAuthenticationResponse.getId(), new BelatrixConnectCallback<Guest>() {
                    @Override
                    public void onSuccess(Guest guestResponse) {
                        GuestManager.this.guest = guestResponse;
                        callback.onSuccess(guestResponse);
                    }

                    @Override
                    public void onFailure(ServiceError serviceError) {
                        callback.onFailure(serviceError);
                    }
                });
            }

            @Override
            public void onFailure(ServiceError serviceError) {
                callback.onFailure(serviceError);
            }
        });
    }

    public void getLoggedInGuest(final BelatrixConnectCallback<Guest> callback) {
        if (guest == null) {
            int storedGuestId = PreferencesManager.get().getGuestId();
            guestService.getGuest(storedGuestId, new BelatrixConnectCallback<Guest>() {
                @Override
                public void onSuccess(Guest guest) {
                    GuestManager.this.guest = guest;
                    callback.onSuccess(guest);
                }

                @Override
                public void onFailure(ServiceError serviceError) {
                    callback.onFailure(serviceError);
                }
            });
        } else {
            callback.onSuccess(guest);
        }
    }

    public void refreshGuest() {
        this.guest = null;
    }

    public void logout() {
        refreshGuest();
        PreferencesManager.get().clearGuestId();
    }

}
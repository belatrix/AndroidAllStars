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
package com.belatrixsf.connect.ui.home;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.entities.Guest;
import com.belatrixsf.connect.managers.GuestManager;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;

import javax.inject.Inject;

/**
 * Created by gyosida on 7/4/16.
 */
public class GuestHomePresenter extends BelatrixConnectPresenter<HomeView> implements HomePresenter {

    private GuestManager guestManager;

    @Inject
    public GuestHomePresenter(HomeView homeView, GuestManager guestManager) {
        super(homeView);
        this.guestManager = guestManager;
    }

    @Override
    public void cancelRequests() {

    }

    @Override
    public void wantToLogout() {
        view.showLogoutConfirmationDialog(getString(R.string.dialog_confirmation_logout));
    }

    @Override
    public void confirmLogout() {
        guestManager.logout();
        view.endSession();
    }

    @Override
    public void loadEmployeeData() {
        guestManager.getLoggedInGuest(new PresenterCallback<Guest>() {
            @Override
            public void onSuccess(Guest guest) {
                view.setNavigationDrawerData(guest.getAvatarLink(), guest.getFullName(), guest.getEmail());
            }
        });
    }

}

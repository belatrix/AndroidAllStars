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
package com.belatrixsf.connect.ui.login.guest;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.entities.Guest;
import com.belatrixsf.connect.managers.GuestManager;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;
import com.belatrixsf.connect.utils.ServiceError;

import javax.inject.Inject;

/**
 * Created by icerrate on 27/05/16.
 */
public class LoginAsGuestPresenter extends BelatrixConnectPresenter<LoginAsGuestView> {

    private GuestManager guestManager;

    private Guest guest;

    @Inject
    public LoginAsGuestPresenter(LoginAsGuestView view, GuestManager guestManager) {
        super(view);
        this.guestManager = guestManager;
    }

    public void setGuestData(Guest guest) {
        this.guest = guest;
    }

    public void loginWithFacebook(String id, String email, String fullName){
        if (validEmail(email)) {
            guest = new Guest(fullName, email, id, null, null);
            loginAsGuest();
        } else {
            view.goRequestGuestEmail();
        }
    }

    public void twitterSessionSuccess(String id, String userName){
        guest = new Guest(null, null, null, id, userName);
        view.requestTwitterUserData();
    }

    public void twitterUserDataSuccess(String fullName, String userImage){
        guest.setFullName(fullName);
        guest.setAvatarLink(userImage);
        view.requestTwitterEmail();
    }

    public void twitterEmailSuccess(String email){
        if (validEmail(email)) {
            guest.setEmail(email);
            loginAsGuest();
        } else {
            view.goRequestGuestEmail();
        }
    }

    public void continueSocialLoginProcess(String email) {
        if (validEmail(email)) {
            guest.setEmail(email);
            loginAsGuest();
        }
    }

    public void facebookFailure(){
        view.showError(getString(R.string.error_facebook));
    }

    public void twitterFailure(){
        view.showError(getString(R.string.error_twitter));
    }

    public void onGettingEmailError() {
        view.goRequestGuestEmail();
    }

    public void resetData() {
        guest = null;
    }

    private boolean validEmail(String email){
        return  email != null && !email.isEmpty();
    }

    private void loginAsGuest() {
        if (guest != null) {
            view.showProgressDialog();
            guestManager.loginAsGuest(guest, new PresenterCallback<Guest>() {
                @Override
                public void onSuccess(Guest guestResponse) {
                    view.dismissProgressDialog();
                    view.goHome();
                }

                @Override
                public void onFailure(ServiceError serviceError) {
                    super.onFailure(serviceError);
                }
            });
        }
    }

    @Override
    public void cancelRequests() {
    }

    //Saving State

    public Guest getGuestData() {
        return guest;
    }

}

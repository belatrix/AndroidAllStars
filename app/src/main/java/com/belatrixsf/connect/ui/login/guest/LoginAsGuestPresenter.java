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

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Created by icerrate on 27/05/16.
 */
public class LoginAsGuestPresenter extends BelatrixConnectPresenter<LoginAsGuestView> {

    private GuestManager guestManager;

    private String fullName;
    private String email;
    private String id;

    @Inject
    public LoginAsGuestPresenter(LoginAsGuestView view, GuestManager guestManager) {
        super(view);
        this.guestManager = guestManager;
    }

    public void setGuestData(String id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    public void loginWithFacebook(JSONObject json){
        try {
            id = json.getString("id");
            email = json.getString("email");
            fullName = json.getString("first_name") +" "+ json.getString("last_name");
            if (activeSession()) {
                Guest guest = new Guest(fullName, email, id, null);
                loginAsGuest(guest);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void twitterSessionSuccess(){
        view.requestTwitterUserData();
    }

    public void twitterUserDataSuccess(String name){
        this.fullName = name;
        view.requestTwitterEmail();
    }

    public void twitterEmailSuccess(String email){
        this.email = email;
        if (activeSession()) {
            Guest guest = new Guest(fullName, email, null, id);
            loginAsGuest(guest);
        }
    }

    public void continueTwitterProcess(String email) {
        this.email = email;
        if (activeSession()) {
            Guest guest = new Guest(fullName, email, null, id);
            loginAsGuest(guest);
        }
    }

    public void facebookFailure(){
        view.showError(getString(R.string.error_facebook));
    }

    public void twitterFailure(){
        view.showError(getString(R.string.error_twitter));
    }

    public void resetData() {
        fullName = null;
        email = null;
        id = null;
    }

    private boolean activeSession(){
        return  email != null && id != null && fullName != null;
    }

    private void loginAsGuest(Guest guestRequest) {
        view.showProgressDialog();
        guestManager.loginAsGuest(guestRequest, new PresenterCallback<Guest>() {
            @Override
            public void onSuccess(Guest guestResponse) {
                view.dismissProgressDialog();
                view.goHome();
            }
        });
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    @Override
    public void cancelRequests() {
    }
}

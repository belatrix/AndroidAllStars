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
package com.belatrixsf.allstars.ui.login.guest;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.services.contracts.EmployeeService;
import com.belatrixsf.allstars.ui.common.AllStarsPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Created by icerrate on 27/05/16.
 */
public class LoginAsGuestPresenter extends AllStarsPresenter<LoginAsGuestView> {

    private EmployeeService employeeService;
    private String email;
    private String pictureUrl;
    private String name;

    @Inject
    public LoginAsGuestPresenter(LoginAsGuestView view, EmployeeService employeeService) {
        super(view);
        this.employeeService = employeeService;
    }

    public void init() {

    }

    public void loginWithFacebook(JSONObject json){
        try {
            String id = json.getString("id");
            pictureUrl = "https://graph.facebook.com/" + id + "/picture?type=large";
            email = json.getString("email");
            name = json.getString("first_name") + json.getString("last_name");
            if (activeSession()) {
                //view.loginComplete();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void twitterSessionSuccess(){
        view.requestEmail();
    }

    public void twitterEmailSucces(String email){
        this.email = email;
        view.requestUserData();
    }

    public void twitterUserDataSuccess(String pictureUrl, String name){
        this.pictureUrl = pictureUrl;
        this.name = name;
        if (activeSession()) {
            //view.loginComplete();
        }
    }

    public void facebookFailure(){
        view.showError(getString(R.string.error_facebook));
    }

    public void twitterFailure(){
        view.showError(getString(R.string.error_twitter));
    }

    private boolean activeSession(){
        return  email != null && pictureUrl != null && name != null;
    }

    @Override
    public void cancelRequests() {
        employeeService.cancelAll();
    }
}

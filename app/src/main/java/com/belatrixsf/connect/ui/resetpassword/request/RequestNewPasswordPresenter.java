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
package com.belatrixsf.connect.ui.resetpassword.request;

import android.os.SystemClock;

import com.belatrixsf.connect.networking.retrofit.responses.RequestNewPasswordResponse;
import com.belatrixsf.connect.services.contracts.EmployeeService;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;
import com.belatrixsf.connect.utils.ServiceError;

import javax.inject.Inject;

/**
 * Created by icerrate on 15/07/2016.
 */
public class RequestNewPasswordPresenter extends BelatrixConnectPresenter<RequestNewPasswordView> {

    private long lastClickTime = 0; // to handle fast double click
    private EmployeeService employeeService;

    private Runnable inRunnable = new Runnable() {
        @Override
        public void run() {
            view.slideInAnimation();
        }
    };

    @Inject
    public RequestNewPasswordPresenter(RequestNewPasswordView view, EmployeeService employeeService) {
        super(view);
        this.employeeService = employeeService;
    }

    public void onBackClicked() {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
            return;
        }
        lastClickTime = SystemClock.elapsedRealtime();
        if (view.getEmailFocus()) {
            view.removeEmailFocus();
        }
        view.slideOutAnimation();
    }

    public void startAnimations() {
        view.startAnimations(inRunnable);
    }

    public void checkIfInputsIsValid(String email) {
        view.enableRequestButton(isFieldFilled(email));
    }

    public void init() {
        view.enableRequestButton(false);
    }

    public void requestNewPassword(String email) {
        view.showProgressDialog();
        employeeService.requestNewPassword(email, new PresenterCallback<RequestNewPasswordResponse>() {
            @Override
            public void onSuccess(RequestNewPasswordResponse requestNewPassordResponse) {
                view.dismissProgressDialog();
                view.showMessage(requestNewPassordResponse.getDetail());
            }

            @Override
            public void onFailure(ServiceError serviceError) {
                view.dismissProgressDialog();
                super.onFailure(serviceError);
            }
        });
    }

    private boolean isFieldFilled(String email) {
        return email != null && !email.isEmpty();
    }

    public void endFlow(){
        view.goBacktoLogin();
    }

    @Override
    public void cancelRequests() {

    }

}

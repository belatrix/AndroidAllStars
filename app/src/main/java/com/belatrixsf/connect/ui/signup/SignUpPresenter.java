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
package com.belatrixsf.connect.ui.signup;

import android.text.TextUtils;

import com.belatrixsf.connect.networking.retrofit.responses.CreateEmployeeResponse;
import com.belatrixsf.connect.services.contracts.EmployeeService;
import com.belatrixsf.connect.ui.common.AllStarsPresenter;

import javax.inject.Inject;

/**
 * Created by icerrate on 16/05/16.
 */
public class SignUpPresenter extends AllStarsPresenter<SignUpView> {

    private EmployeeService employeeService;

    @Inject
    public SignUpPresenter(SignUpView view, EmployeeService employeeService) {
        super(view);
        this.employeeService = employeeService;
    }

    public void checkIfEmailIsValid(String email) {
        view.enableSend(!TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public void init() {
        view.enableSend(false);
    }

    public void signUp(String email) {
        view.showProgressDialog();
        employeeService.createEmployee(email, new PresenterCallback<CreateEmployeeResponse>() {
            @Override
            public void onSuccess(CreateEmployeeResponse response) {
                view.dismissProgressDialog();
                view.showMessage(response.getDetail());
            }
        });
    }

    public void confirmMessage(){
        view.backToLogin();
    }

    @Override
    public void cancelRequests() {
        employeeService.cancelAll();
    }
}

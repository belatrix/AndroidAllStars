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
package com.belatrixsf.allstars.ui.login;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.managers.EmployeeManager;
import com.belatrixsf.allstars.ui.common.AllStarsPresenter;
import com.belatrixsf.allstars.utils.AllStarsCallback;
import com.belatrixsf.allstars.utils.ServiceError;

import javax.inject.Inject;

/**
 * Created by gyosida on 4/12/16.
 */
public class LogiinPresenter extends AllStarsPresenter<LogiinView> {

    public static final int DEST_HOME = 0;
    public static final int DEST_RESET_PASSWORD = 1;
    public static final int DEST_EDIT_PROFILE = 2;


    private EmployeeManager employeeManager;

    @Inject
    public LogiinPresenter(LogiinView view, EmployeeManager employeeManager) {
        super(view);
        this.employeeManager = employeeManager;
    }

    public void checkIfInputsAreValid(String username, String password) {
        view.enableLogin(areFieldsFilled(username, password));
    }

    public void init() {
        view.enableLogin(false);
    }

    public void login(String username, String password) {
        if (areFieldsFilled(username, password)) {
            view.showProgressDialog();
            employeeManager.login(username, password, new AllStarsCallback<Integer>() {
                @Override
                public void onSuccess(Integer destination) {
                    view.dismissProgressDialog();
                    switch (destination) {
                        case DEST_HOME:
                            view.goHome();
                            break;
                        case DEST_RESET_PASSWORD:
                            view.goResetPassword();
                            break;
                        case DEST_EDIT_PROFILE:
                            view.goEditProfile();
                            break;
                    }
                }

                @Override
                public void onFailure(ServiceError serviceError) {
                    showError(serviceError.getDetail());
                }
            });
        } else {
            showError(R.string.error_incorrect_fields);
        }
    }

    private boolean areFieldsFilled(String username, String password) {
        return username != null && password != null && !username.isEmpty() && !password.isEmpty();
    }

    @Override
    public void cancelRequests() {
    }

}

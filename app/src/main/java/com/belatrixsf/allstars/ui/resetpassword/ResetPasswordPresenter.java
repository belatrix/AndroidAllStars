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
package com.belatrixsf.allstars.ui.resetpassword;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.managers.EmployeeManager;
import com.belatrixsf.allstars.managers.PreferencesManager;
import com.belatrixsf.allstars.ui.common.AllStarsPresenter;
import com.belatrixsf.allstars.utils.AllStarsCallback;
import com.belatrixsf.allstars.utils.ServiceError;

import javax.inject.Inject;

/**
 * Created by icerrate on 19/05/16.
 */
public class ResetPasswordPresenter extends AllStarsPresenter<ResetPasswordView> {

    static final int MIN_PASSWORD_LENGHT = 4;

    private EmployeeManager employeeManager;

    @Inject
    public ResetPasswordPresenter(ResetPasswordView view, EmployeeManager employeeManager) {
        super(view);
        this.employeeManager = employeeManager;
    }

    public void checkIfInputsAreValid(String oldPassword, String newPassword, String repeatNewPassword) {
        boolean valid = false;
        if (!oldPassword.isEmpty() && !newPassword.isEmpty() && !repeatNewPassword.isEmpty()){
            if (newPassword.equals(repeatNewPassword)){
                if (newPassword.length() >= MIN_PASSWORD_LENGHT) {
                    view.cleanNewPasswordError();
                    valid = true;
                }else{
                    view.newPasswordError(String.format(getString(R.string.new_password_length_error), MIN_PASSWORD_LENGHT));
                }
            }else{
                view.newPasswordError(getString(R.string.new_password_repeat_error));
            }
        }
        view.enableReset(valid);
    }

    public void init() {
        view.enableReset(false);
    }

    public void reset(String oldPassword, String newPassword) {
        view.showProgressDialog();
        employeeManager.resetPassword(oldPassword, newPassword, new AllStarsCallback<Employee>() {
            @Override
            public void onSuccess(Employee employee) {
                view.dismissProgressDialog();
                view.goEditProfile(employee);
            }

            @Override
            public void onFailure(ServiceError serviceError) {
                showError(serviceError.getDetail());
            }
        });
    }

    @Override
    public void cancelRequests() {

    }
}

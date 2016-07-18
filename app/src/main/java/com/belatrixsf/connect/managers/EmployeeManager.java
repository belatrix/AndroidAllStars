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

import com.belatrixsf.connect.entities.Employee;
import com.belatrixsf.connect.networking.retrofit.responses.AuthenticationResponse;
import com.belatrixsf.connect.networking.retrofit.responses.RequestNewPasswordResponse;
import com.belatrixsf.connect.services.contracts.EmployeeService;
import com.belatrixsf.connect.utils.BelatrixConnectCallback;
import com.belatrixsf.connect.utils.ServiceError;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by gyosida on 4/12/16.
 */
@Singleton
public class EmployeeManager {

    public enum AccountState {
        PROFILE_COMPLETE,
        PROFILE_INCOMPLETE,
        PASSWORD_RESET_INCOMPLETE
    }

    private EmployeeService employeeService;
    private Employee employee;

    @Inject
    public EmployeeManager(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void login(String username, String password, final BelatrixConnectCallback<AccountState> callback) {
        employeeService.authenticate(username, password, new BelatrixConnectCallback<AuthenticationResponse>() {
            @Override
            public void onSuccess(final AuthenticationResponse authenticationResponse) {
                PreferencesManager.get().saveToken(authenticationResponse.getToken());
                PreferencesManager.get().saveEmployeeId(authenticationResponse.getEmployeeId());
                if (authenticationResponse.getResetPasswordCode() == null){
                    PreferencesManager.get().setResetPassword(true);
                    employeeService.getEmployee(authenticationResponse.getEmployeeId(), new BelatrixConnectCallback<Employee>() {
                        @Override
                        public void onSuccess(Employee employee) {
                            EmployeeManager.this.employee = employee;
                            if (authenticationResponse.isBaseProfileComplete()){
                                PreferencesManager.get().setEditProfile(true);
                                callback.onSuccess(AccountState.PROFILE_COMPLETE);
                            } else {
                                PreferencesManager.get().setEditProfile(false);
                                callback.onSuccess(AccountState.PROFILE_INCOMPLETE);
                            }
                        }

                        @Override
                        public void onFailure(ServiceError serviceError) {
                            callback.onFailure(serviceError);
                        }
                    });
                } else {
                    PreferencesManager.get().setResetPassword(false);
                    callback.onSuccess(AccountState.PASSWORD_RESET_INCOMPLETE);
                }
            }

            @Override
            public void onFailure(ServiceError serviceError) {
                callback.onFailure(serviceError);
            }
        });
    }

    public void resetPassword(String oldPassword, String newPassword, final BelatrixConnectCallback<Employee> callback) {
        if (employee == null) {
            int storedEmployeeId = PreferencesManager.get().getEmployeeId();
            employeeService.resetPassword(storedEmployeeId, oldPassword, newPassword, new BelatrixConnectCallback<Employee>() {
                @Override
                public void onSuccess(Employee employee) {
                    PreferencesManager.get().setResetPassword(true);
                    callback.onSuccess(employee);
                }

                @Override
                public void onFailure(ServiceError serviceError) {
                    callback.onFailure(serviceError);
                }
            });
        }
    }

    public void requestNewPassword(String employeeEmail, final BelatrixConnectCallback<RequestNewPasswordResponse> callback) {
        employeeService.requestNewPassword(employeeEmail, new BelatrixConnectCallback<RequestNewPasswordResponse>() {
            @Override
            public void onSuccess(RequestNewPasswordResponse requestNewPasswordResponse) {
                callback.onSuccess(requestNewPasswordResponse);
            }

            @Override
            public void onFailure(ServiceError serviceError) {
                callback.onFailure(serviceError);
            }
        });
    }

    public void getLoggedInEmployee(final BelatrixConnectCallback<Employee> callback) {
        if (employee == null) {
            int storedEmployeeId = PreferencesManager.get().getEmployeeId();
            employeeService.getEmployee(storedEmployeeId, new BelatrixConnectCallback<Employee>() {
                @Override
                public void onSuccess(Employee employee) {
                    EmployeeManager.this.employee = employee;
                    callback.onSuccess(employee);
                }

                @Override
                public void onFailure(ServiceError serviceError) {
                    callback.onFailure(serviceError);
                }
            });
        } else {
            callback.onSuccess(employee);
        }
    }

    public void updateEmployeeImage(File selectedFile, final BelatrixConnectCallback<Employee> callback) {
        employeeService.updateEmployeeImage(employee.getPk(), selectedFile, new BelatrixConnectCallback<Employee>() {
            @Override
            public void onSuccess(Employee employee) {
                PreferencesManager.get().setEditProfile(true);
                EmployeeManager.this.employee = employee;
                callback.onSuccess(employee);
            }

            @Override
            public void onFailure(ServiceError serviceError) {
                callback.onFailure(serviceError);
            }
        });
    }

    public void refreshEmployee() {
        this.employee = null;
    }

    public void logout() {
        refreshEmployee();
        PreferencesManager.get().clearEmployeeId();
        PreferencesManager.get().clearToken();
        PreferencesManager.get().clearResetPassword();
        PreferencesManager.get().clearEditProfile();
    }

}
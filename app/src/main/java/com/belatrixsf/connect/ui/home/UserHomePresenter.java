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
import com.belatrixsf.connect.entities.Employee;
import com.belatrixsf.connect.managers.EmployeeManager;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;

import javax.inject.Inject;

/**
 * Created by gyosida on 4/28/16.
 */
public class UserHomePresenter extends BelatrixConnectPresenter<HomeView> implements HomePresenter {

    private EmployeeManager employeeManager;

    @Inject
    public UserHomePresenter(HomeView view, EmployeeManager employeeManager) {
        super(view);
        this.employeeManager = employeeManager;
    }

    @Override
    public void wantToLogout() {
        view.showLogoutConfirmationDialog(getString(R.string.dialog_confirmation_logout));
    }

    @Override
    public void confirmLogout() {
        employeeManager.logout();
        view.goToLogin();
    }

    @Override
    public void loadEmployeeData(){
        employeeManager.getLoggedInEmployee(new PresenterCallback<Employee>() {
            @Override
            public void onSuccess(Employee employee) {
                view.setNavigationDrawerData(employee.getAvatar(), employee.getFullName(), employee.getEmail());
            }
        });
    }

    @Override
    public void cancelRequests() {
    }

    public void refreshEmployee() {
        employeeManager.refreshEmployee();
    }

}
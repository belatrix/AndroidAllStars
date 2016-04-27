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
package com.belatrixsf.allstars.ui.account;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.managers.EmployeeManager;
import com.belatrixsf.allstars.networking.retrofit.responses.StarSubCategoryResponse;
import com.belatrixsf.allstars.services.EmployeeService;
import com.belatrixsf.allstars.services.StarService;
import com.belatrixsf.allstars.ui.common.AllStarsPresenter;
import com.belatrixsf.allstars.utils.AllStarsCallback;
import com.belatrixsf.allstars.utils.ServiceError;

import javax.inject.Inject;

/**
 * Created by PedroCarrillo on 4/13/16.
 */
public class AccountPresenter extends AllStarsPresenter<AccountView> {

    protected EmployeeManager employeeManager;
    protected Employee employee;
    protected StarService starService;
    protected EmployeeService employeeService;

    @Inject
    public AccountPresenter(AccountView view, EmployeeManager employeeManager, EmployeeService employeeService, StarService starService) {
        super(view);
        this.employeeManager = employeeManager;
        this.starService = starService;
        this.employeeService = employeeService;
    }

    public void loadEmployeeAccount(Integer employeeId) {
        AllStarsCallback<Employee> employeeAllStarsCallback = new AllStarsCallback<Employee>() {
            @Override
            public void onSuccess(Employee employee) {
                AccountPresenter.this.employee = employee;
                loadSubCategoriesStar();
                showEmployeeData();
            }

            @Override
            public void onFailure(ServiceError serviceError) {
                showError(serviceError.getErrorMessage());
            }
        };
        if (employeeId == null) {
            employeeManager.getLoggedInEmployee(employeeAllStarsCallback);
        } else{
            employeeService.getEmployee(employeeId, employeeAllStarsCallback);
        }
    }

    private void loadSubCategoriesStar() {
        starService.getEmployeeSubCategoriesStars(employee.getPk(), new AllStarsCallback<StarSubCategoryResponse>() {
            @Override
            public void onSuccess(StarSubCategoryResponse starSubCategoryResponse) {
                view.showSubCategories(starSubCategoryResponse.getSubCategories());
            }

            @Override
            public void onFailure(ServiceError serviceError) {
                showError(serviceError.getErrorMessage());
            }
        });
    }

    private void showEmployeeData() {
        if (employee.getLevel() != null) {
            view.showLevel(String.valueOf(employee.getLevel()));
        } else {
            view.showCurrentMonthScore(getString(R.string.no_data_option));
        }
        if (employee.getScore() != null) {
            view.showScore(String.valueOf(employee.getScore()));
        } else {
            view.showCurrentMonthScore(getString(R.string.no_data_option));
        }
        if (employee.getCurrentMonthScore() != null) {
            view.showCurrentMonthScore(String.valueOf(employee.getCurrentMonthScore()));
        } else {
            view.showCurrentMonthScore(getString(R.string.no_data_option));
        }
        if (employee.getFirstName() != null || employee.getLastName() != null || !employee.getFullName().isEmpty()) {
            view.showEmployeeName(employee.getFullName());
        } else {
            view.showEmployeeName(getString(R.string.no_data));
        }
        if (employee.getSkypeId() != null && !employee.getSkypeId().isEmpty()) {
            view.showSkypeId(employee.getSkypeId());
        }
        if (employee.getRole() != null) {
            view.showRole(employee.getRole().getName());
        } else {
            view.showRole(getString(R.string.no_data));
        }
        if (employee.getAvatar() != null) {
            view.showProfilePicture(employee.getAvatar());
        }
    }

    public void onCategoryClicked(int position) {
        view.goCategoryDetail(employee.getCategories().get(position));
    }

}

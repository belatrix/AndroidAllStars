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
package com.belatrixsf.connect.ui.account;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.entities.Employee;
import com.belatrixsf.connect.entities.SubCategory;
import com.belatrixsf.connect.managers.EmployeeManager;
import com.belatrixsf.connect.managers.PreferencesManager;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.services.contracts.EmployeeService;
import com.belatrixsf.connect.services.contracts.StarService;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;
import com.belatrixsf.connect.utils.BelatrixConnectCallback;
import com.belatrixsf.connect.utils.ServiceError;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by PedroCarrillo on 4/13/16.
 */
public class AccountPresenter extends BelatrixConnectPresenter<AccountView> {

    private EmployeeManager employeeManager;
    private Integer employeeId;
    private Employee employee;
    private List<SubCategory> subCategoriesList;
    private byte[] employeeImg;

    private StarService starService;
    private EmployeeService employeeService;

    @Inject
    public AccountPresenter(AccountView view, EmployeeManager employeeManager, EmployeeService employeeService, StarService starService) {
        super(view);
        this.employeeManager = employeeManager;
        this.starService = starService;
        this.employeeService = employeeService;
    }

    public void loadEmployeeAccount(final boolean force) {
        if (employeeId == null || force) {
            view.showProgressIndicator();
            BelatrixConnectCallback<Employee> employeeBelatrixConnectCallback = new PresenterCallback<Employee>() {
                @Override
                public void onSuccess(Employee employee) {
                    AccountPresenter.this.employeeId = employee.getPk();
                    AccountPresenter.this.employee = employee;
                    loadSubCategoriesStar(true);
                    showEmployeeData();
                    view.notifyNavigationRefresh();
                    view.hideProgressIndicator();
                }

                @Override
                public void onFailure(ServiceError serviceError) {
                    view.hideProgressIndicator();
                    if (serviceError.getResponseCode() == ServiceError.INVALID_TOKEN) {
                        view.showInformativeDialog(serviceError.getDetail());
                    }
                }
            };
            if (employeeId == null) {
                employeeManager.getLoggedInEmployee(employeeBelatrixConnectCallback);
            } else {
                employeeService.getEmployee(employeeId, employeeBelatrixConnectCallback);
            }
        } else {
            loadSubCategoriesStar(false);
            showEmployeeData();
        }
    }

    private void loadSubCategoriesStar(boolean force) {
        view.hideNoDataView();
        if (subCategoriesList == null || force) {
            view.showProgressIndicator();
            starService.getEmployeeSubCategoriesStars(
                employee.getPk(),
                new PresenterCallback<PaginatedResponse<SubCategory>>() {
                    @Override
                    public void onSuccess(PaginatedResponse<SubCategory> starSubCategoryResponse) {
                        view.hideProgressIndicator();
                        if(!starSubCategoryResponse.getResults().isEmpty()) {
                            view.showSubCategories(starSubCategoryResponse.getResults());
                        } else {
                            view.showNoDataView();
                        }
                    }

                    @Override
                    public void onFailure(ServiceError serviceError) {
                        view.hideProgressIndicator();
                        super.onFailure(serviceError);
                    }
                });
        } else {
            if(!subCategoriesList.isEmpty()) {
                view.showSubCategories(subCategoriesList);
            } else {
                view.showNoDataView();
            }
        }
    }

    public void setUserInfo(Integer employeeId, byte[] employeeByteImg) {
        this.employeeId = employeeId;
        this.employeeImg = employeeByteImg;
    }

    private void showEmployeeData() {
        if (employee.getLocation() != null) {
            view.showLocation(employee.getLocation().getName());
        }
        if (employee.getLevel() != null) {
            view.showLevel(String.valueOf(employee.getLevel()));
        }
        if (employee.getTotalScore() != null) {
            view.showScore(String.valueOf(employee.getTotalScore()));
        }
        if (employee.getFirstName() != null || employee.getLastName() != null || !employee.getFullName().isEmpty()) {
            view.showEmployeeName(employee.getFullName());
        } else {
            view.showEmployeeName(getString(R.string.no_data));
        }
        if (employee.getSkypeId() != null && !employee.getSkypeId().isEmpty()) {
            view.showSkypeId(employee.getSkypeId());
        }
        if (employee.getEmail() != null && !employee.getEmail().isEmpty()) {
            view.showEmail(employee.getEmail());
        } else {
            view.showEmail(getString(R.string.no_data));
        }
        view.showProfilePicture(employee.getAvatar());
        checkRecommendationEnabled();
    }

    public void onSubCategoryClicked(Object object) {
        if (object != null && object instanceof SubCategory) {
            SubCategory subCategory = (SubCategory) object;
            view.goSubCategoryDetail(subCategory.getId(), employee.getPk());
        }
    }

    public void checkRecommendationEnabled() {
        if (employee != null) {
            if (PreferencesManager.get().getEmployeeId() != employee.getPk()) {
                view.showRecommendMenu(true);
            } else {
                view.showEditProfileButton(true);
                view.showEditSkillsButton(false);
            }
        }
    }

    public void startRecommendation() {
        view.goToGiveStar(employee);
    }

    public void startEditProfile() {
        view.goToEditProfile(employee);
    }

    public void startEditSkills(){
        view.goToEditSkills();
    }

    public void refreshEmployee() {
        employeeManager.refreshEmployee();
    }

    public void profilePictureClicked() {
        if (employee != null && employee.getAvatar() != null) {
            view.goToExpandPhoto(employee.getAvatar());
        }
    }

    public List<SubCategory> getSubCategoriesListSync() {
        return subCategoriesList;
    }

    public Employee getEmployee() {
        return employee;
    }

    public byte[] getEmployeeImg(){
        return this.employeeImg;
    }

    public void loadPresenterState(List<SubCategory> subCategoriesList, Employee employee, byte[] employeeImg) {
        this.subCategoriesList = subCategoriesList;
        this.employee = employee;
        this.employeeImg = employeeImg;
    }

    public void confirmEndSession() {
        endSession();
        view.goBackToLogin();
    }

    @Override
    public void cancelRequests() {
        employeeService.cancelAll();
        starService.cancelAll();
    }

    private void endSession() {
        PreferencesManager.get().clearUserSession();
    }

}
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
package com.belatrixsf.connect.ui.account.recommendations;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.entities.Category;
import com.belatrixsf.connect.entities.Employee;
import com.belatrixsf.connect.entities.SubCategory;
import com.belatrixsf.connect.managers.EmployeeManager;
import com.belatrixsf.connect.managers.PreferencesManager;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.services.contracts.CategoryService;
import com.belatrixsf.connect.services.contracts.EmployeeService;
import com.belatrixsf.connect.services.contracts.StarService;
import com.belatrixsf.connect.ui.account.AccountView;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;
import com.belatrixsf.connect.utils.BelatrixConnectCallback;
import com.belatrixsf.connect.utils.ServiceError;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by PedroCarrillo on 4/13/16.
 */
public class AccountRecommendationsPresenter extends BelatrixConnectPresenter<AccountRecommendationsView> {

    private Integer employeeId;
    private List<SubCategory> subCategoryList;

    private CategoryService categoryService;

    @Inject
    public AccountRecommendationsPresenter(AccountRecommendationsView view, EmployeeManager employeeManager, CategoryService categoryService) {
        super(view);
        this.categoryService = categoryService;
    }


    public void loadCategoriesByEmployee(boolean force) {
        view.hideNoDataView();
        if (subCategoryList == null || force) {
            view.showProgressIndicator();
            categoryService.getCategoriesByEmployee(
                employeeId,
                new PresenterCallback<PaginatedResponse<SubCategory>>() {
                    @Override
                    public void onSuccess(PaginatedResponse<SubCategory> starSubCategoryResponse) {
                        view.hideProgressIndicator();
                        if(!starSubCategoryResponse.getResults().isEmpty()) {
                            view.showCategories(starSubCategoryResponse.getResults());
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
            if(!subCategoryList.isEmpty()) {
                view.showCategories(subCategoryList);
            } else {
                view.showNoDataView();
            }
        }
    }

    public void setUserInfo(Integer employeeId) {
        this.employeeId = employeeId;
    }



    public void onCategoryClicked(Object object) {
        if (object != null && object instanceof SubCategory) {
            SubCategory subCategory = (SubCategory) object;
            view.goCategoryDetail(subCategory.getId(), this.employeeId );
        }
    }





    public void loadPresenterState(List<SubCategory> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }



    @Override
    public void cancelRequests() {
        categoryService.cancelAll();
    }

}
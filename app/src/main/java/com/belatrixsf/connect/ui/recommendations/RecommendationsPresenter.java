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
package com.belatrixsf.connect.ui.recommendations;

import com.belatrixsf.connect.entities.Star;
import com.belatrixsf.connect.managers.EmployeeManager;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.services.contracts.EmployeeService;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;
import com.belatrixsf.connect.utils.ServiceError;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by dvelasquez on 17/2/17.
 */
public class RecommendationsPresenter extends BelatrixConnectPresenter<RecommendationsView> {

    private Integer employeeId;
    private Integer categoryId;
    private List<Star> list;

    private EmployeeService employeeService;

    @Inject
    public RecommendationsPresenter(RecommendationsView view, EmployeeManager employeeManager, EmployeeService employeeService) {
        super(view);
        this.employeeService = employeeService;
    }


    public void loadCategoriesByEmployee(boolean force) {
        view.hideNoDataView();
        if (list == null || force) {
            view.showProgressIndicator();
            employeeService.getEmployeeRecommendationList(
                employeeId, categoryId,
                new PresenterCallback<PaginatedResponse<Star>>() {
                    @Override
                    public void onSuccess(PaginatedResponse<Star> starSubCategoryResponse) {
                        view.hideProgressIndicator();
                        if(!starSubCategoryResponse.getResults().isEmpty()) {
                            view.showRecommendations(starSubCategoryResponse.getResults());
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
            if(!list.isEmpty()) {
                view.showRecommendations(list);
            } else {
                view.showNoDataView();
            }
        }
    }

    public void setUserInfo(Integer employeeId, Integer categoryId) {
        this.employeeId = employeeId;
        this.categoryId = categoryId;
    }




    public List<Star> getListSync() {
        return list;
    }



    public void loadPresenterState(List<Star> list) {
        this.list = list;
    }



    @Override
    public void cancelRequests() {
        employeeService.cancelAll();
    }

}
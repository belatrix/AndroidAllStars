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
package com.belatrixsf.allstars.ui.ranking;

import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.services.contracts.EmployeeService;
import com.belatrixsf.allstars.ui.common.AllStarsPresenter;
import com.belatrixsf.allstars.utils.AllStarsCallback;
import com.belatrixsf.allstars.utils.ServiceError;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by icerrate on 28/04/2016.
 */
public class RankingPresenter extends AllStarsPresenter<RankingView> {

    private EmployeeService employeeService;
    private List<Employee> rankingEmployees;

    @Inject
    public RankingPresenter(RankingView view, EmployeeService employeeService) {
        super(view);
        this.employeeService = employeeService;
    }

    public void getRankingList(String kind, int quantity, final boolean isRefresh) {
        if (!isRefresh) {
            view.showProgressIndicator();
        }
        employeeService.getRankingList(
                kind,
                quantity,
                new PresenterCallback<List<Employee>>() {
                    @Override
                    public void onSuccess(List<Employee> rankingResponse) {
                        rankingEmployees = rankingResponse;
                        view.showRankingList(rankingResponse);
                        if (!isRefresh) {
                            view.hideProgressIndicator();
                        } else {
                            view.hideRefreshData();
                        }
                    }

                    @Override
                    public void onFailure(ServiceError serviceError) {
                        if (!isRefresh) {
                            view.hideProgressIndicator();
                        } else {
                            view.hideRefreshData();
                        }
                        super.onFailure(serviceError);
                    }
                });
    }

    public void employeeSelected(int position) {
        view.goToEmployeeProfile(rankingEmployees.get(position).getPk());
    }

    @Override
    public void cancelRequests() {
        employeeService.cancelAll();
    }
}

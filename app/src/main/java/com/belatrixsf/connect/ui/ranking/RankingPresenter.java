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
package com.belatrixsf.connect.ui.ranking;

import com.belatrixsf.connect.entities.Employee;
import com.belatrixsf.connect.services.contracts.EmployeeService;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by icerrate on 28/04/2016.
 */
public class RankingPresenter extends BelatrixConnectPresenter<RankingView> {

    private EmployeeService employeeService;
    private List<Employee> employeeList;

    @Inject
    public RankingPresenter(RankingView view, EmployeeService employeeService) {
        super(view);
        this.employeeService = employeeService;
    }

    public void getRankingList(String kind, int quantity, final boolean isRefresh) {
        if (!isRefresh) {
            view.showProgressIndicator();
        }
        view.hideNoDataView();
        employeeService.getRankingList(
                kind,
                quantity,
                new PresenterCallback<List<Employee>>() {
                    @Override
                    public void onSuccess(List<Employee> rankingResponse) {
                        employeeList = rankingResponse;
                        if(!rankingResponse.isEmpty()) {
                            view.showRankingList(rankingResponse);
                        } else {
                            view.showNoDataView();
                        }
                        if (!isRefresh) {
                            view.hideProgressIndicator();
                        } else {
                            view.hideRefreshData();
                        }
                    }
                });
    }

    public void employeeSelected(int position) {
        view.goToEmployeeProfile(employeeList.get(position).getPk());
    }

    @Override
    public void cancelRequests() {
        employeeService.cancelAll();
    }

}

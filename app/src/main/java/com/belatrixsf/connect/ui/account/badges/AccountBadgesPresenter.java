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
package com.belatrixsf.connect.ui.account.badges;

import com.belatrixsf.connect.entities.Badge;
import com.belatrixsf.connect.entities.EmployeeBadge;
import com.belatrixsf.connect.managers.EmployeeManager;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.services.contracts.BadgeService;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;
import com.belatrixsf.connect.utils.ServiceError;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by PedroCarrillo on 4/13/16.
 */
public class AccountBadgesPresenter extends BelatrixConnectPresenter<AccountBadgesView> {

    private EmployeeManager employeeManager;
    private Integer employeeId;
    private List<EmployeeBadge> list;

    private BadgeService badgeService;

    @Inject
    public AccountBadgesPresenter(AccountBadgesView view, EmployeeManager employeeManager, BadgeService badgeService) {
        super(view);
        this.employeeManager = employeeManager;
        this.badgeService = badgeService;
    }


    public void loadBadgesByEmployee(boolean force) {
        view.hideNoDataView();
        if (badgeService == null || force) {
            view.showProgressIndicator();
            badgeService.getBadgesByEmployee(
                employeeId,
                new PresenterCallback<PaginatedResponse<EmployeeBadge>>() {
                    @Override
                    public void onSuccess(PaginatedResponse<EmployeeBadge> starSubCategoryResponse) {
                        view.hideProgressIndicator();
                        if(!starSubCategoryResponse.getResults().isEmpty()) {
                            view.showBadges(starSubCategoryResponse.getResults());
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
                view.showBadges(list);
            } else {
                view.showNoDataView();
            }
        }
    }

    public void setUserInfo(Integer employeeId) {
        this.employeeId = employeeId;
    }



    public void onBadgeClicked(Object object) {
        if (object != null && object instanceof Badge) {
            Badge badge = (Badge) object;
            view.goBadgeDetail(badge);
        }
    }





    public List<EmployeeBadge> getBadges() {
        return list;
    }



    public void loadPresenterState(List<EmployeeBadge> list) {
        this.list = list;
    }



    @Override
    public void cancelRequests() {
        badgeService.cancelAll();
    }

}
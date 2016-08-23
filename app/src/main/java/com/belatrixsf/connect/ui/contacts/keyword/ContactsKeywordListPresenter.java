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
package com.belatrixsf.connect.ui.contacts.keyword;

import com.belatrixsf.connect.entities.Employee;
import com.belatrixsf.connect.entities.Keyword;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.services.contracts.StarService;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;
import com.belatrixsf.connect.utils.BelatrixConnectCallback;
import com.belatrixsf.connect.utils.ServiceError;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by PedroCarrillo on 5/12/16.
 */
public class ContactsKeywordListPresenter extends BelatrixConnectPresenter<ContactsKeywordListView> {

    private StarService starService;
    private Keyword keyword;
    private PaginatedResponse starPaginatedResponse = new PaginatedResponse();
    private List<Employee> employeeList = new ArrayList<>();

    @Inject
    protected ContactsKeywordListPresenter(ContactsKeywordListView view, StarService starService) {
        super(view);
        this.starService = starService;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public PaginatedResponse getStarPaginatedResponse() {
        return starPaginatedResponse;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }

    public void init(Keyword keyword) {
        this.keyword = keyword;
        getEmployeesByStarKeywords();
    }

    public void loadPresenterState(List<Employee> employeeList, PaginatedResponse starPaginatedResponse) {
        if (employeeList != null) {
            this.employeeList = employeeList;
        }
        this.starPaginatedResponse = starPaginatedResponse;
        view.showCurrentPage(starPaginatedResponse.getNextPage() == null ? 1 : starPaginatedResponse.getNextPage());
        view.showEmployees(employeeList);
    }

    public void callNextPage() {
        if (starPaginatedResponse.getNext() != null) {
            getEmployeesByStarKeywords();
        }
    }

    public void getEmployeesByStarKeywords() {
        view.showProgressIndicator();
        starService.getStarsKeywordTopList(
                keyword.getId(),
                starPaginatedResponse.getNextPage(),
                new BelatrixConnectCallback<PaginatedResponse<Employee>>() {
                    @Override
                    public void onSuccess(PaginatedResponse<Employee> starKeywordTopListResponse) {
                        employeeList.addAll(starKeywordTopListResponse.getResults());
                        starPaginatedResponse.setNext(starKeywordTopListResponse.getNext());
                        view.hideProgressIndicator();
                        view.showEmployees(employeeList);
                    }

                    @Override
                    public void onFailure(ServiceError serviceError) {
                        view.hideProgressIndicator();
                        showError(serviceError.getDetail());
                    }
                });
    }

    public void onContactClicked(Object contact) {
        if (contact != null && contact instanceof Employee) {
            Employee employee = (Employee) contact;
            view.goContactProfile(employee.getPk());
        }
    }

    @Override
    public void cancelRequests() {
        starService.cancelAll();
    }

}

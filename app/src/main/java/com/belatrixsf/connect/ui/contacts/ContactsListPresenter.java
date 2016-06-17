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
package com.belatrixsf.connect.ui.contacts;

import com.belatrixsf.connect.entities.Employee;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.networking.retrofit.responses.SearchEmployeeResponse;
import com.belatrixsf.connect.services.ServiceRequest;
import com.belatrixsf.connect.services.contracts.EmployeeService;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by icerrate on 15/04/2016.
 */
public class ContactsListPresenter extends BelatrixConnectPresenter<ContactsListView> {

    private EmployeeService employeeService;
    private List<Employee> contacts = new ArrayList<>();
    private PaginatedResponse contactsPaging = new PaginatedResponse();
    private ServiceRequest searchingServiceRequest;
    private String searchText;
    private boolean searching = false;
    private boolean profileEnabled = true;

    @Inject
    public ContactsListPresenter(ContactsListView view, EmployeeService employeeService) {
        super(view);
        this.employeeService = employeeService;
    }

    public void onContactClicked(Object object) {
        if (object != null && object instanceof Employee) {
            Employee employee = (Employee) object;
            if (profileEnabled) {
                view.goContactProfile(employee.getPk());
            } else {
                view.selectContact(employee);
            }
        }
    }

    public void searchContacts() {
        view.showSearchActionMode();
        searching = true;
    }

    public void stopSearchingContacts() {
        searchText = null;
        searching = false;
        reset();
        getContactsInternal();
    }

    public void callNextPage() {
        if (contactsPaging.getNext() != null) {
            getContactsInternal();
        }
    }

    public void getContacts() {
        view.resetList();
        if (contacts.isEmpty()) {
            getContactsInternal();
        } else {
            view.addContacts(contacts);
        }
    }

    public void getContacts(String searchText) {
        if (searchingServiceRequest != null) {
            searchingServiceRequest.cancel();
        }
        this.searchText = searchText;
        reset();
        getContactsInternal();
    }

    private void getContactsInternal() {
        view.showProgressIndicator();
        searchingServiceRequest = employeeService.getEmployeeSearchList(
                searchText,
                contactsPaging.getNextPage(),
                new PresenterCallback<SearchEmployeeResponse>() {
                    @Override
                    public void onSuccess(SearchEmployeeResponse starsByKeywordsResponse) {
                        contactsPaging.setCount(starsByKeywordsResponse.getCount());
                        contactsPaging.setNext(starsByKeywordsResponse.getNext());
                        contacts.addAll(starsByKeywordsResponse.getEmployeeList());
                        view.addContacts(starsByKeywordsResponse.getEmployeeList());
                        view.hideProgressIndicator();
                    }
                });
    }

    @Override
    public void cancelRequests() {
        employeeService.cancelAll();
    }

    private void reset() {
        contacts.clear();
        view.resetList();
        contactsPaging.reset();
    }

    // saving state stuff

    public void load(List<Employee> contacts, PaginatedResponse contactsPaging, String searchText, boolean searching) {
        if (contacts != null) {
            this.contacts.addAll(contacts);
        }
        this.contactsPaging = contactsPaging;
        this.searchText = searchText;
        this.searching = searching;
        if (searching) {
            searchContacts();
        }
    }

    public void setProfileEnabled(boolean profileEnabled) {
        this.profileEnabled = profileEnabled;
    }

    public boolean getProfileEnabled() {
        return profileEnabled;
    }

    public String getSearchText() {
        return searchText;
    }

    public PaginatedResponse getContactsPaging() {
        return contactsPaging;
    }

    public List<Employee> getContactsSync() {
        return contacts;
    }

    public boolean isSearching() {
        return searching;
    }

}
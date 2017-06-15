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
import com.belatrixsf.connect.services.ServiceRequest;
import com.belatrixsf.connect.services.contracts.EmployeeService;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

/**
 * Created by icerrate on 15/04/2016.
 */
public class ContactsListPresenter extends BelatrixConnectPresenter<ContactsListView> {

    private EmployeeService employeeService;
    private Set<Employee> contactsList = new HashSet<>();
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

        getContactsInternal();

        /*
        if (contactsList.isEmpty()) {
            getContactsInternal();
        } else {
            view.addContacts(contactsList);
            view.hideProgressIndicator();
        }*/
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
        view.hideNoDataView();
        searchingServiceRequest = employeeService.getEmployeeSearchList(
                searchText,
                contactsPaging.getNextPage(),
                new PresenterCallback<PaginatedResponse<Employee>>() {
                    @Override
                    public void onSuccess(PaginatedResponse<Employee> contactsKeywordsResponse) {
                        contactsPaging.setCount(contactsKeywordsResponse.getCount());
                        contactsPaging.setNext(contactsKeywordsResponse.getNext());
                        contactsList.addAll(contactsKeywordsResponse.getResults());
                        if(!contactsKeywordsResponse.getResults().isEmpty()) {
                            view.resetList();
                            List<Employee> list = new ArrayList<Employee>(contactsList);
                            Collections.sort(list,getEmployeeComparator());
                            view.addContacts(list);
                        } else {
                            view.showNoDataView();
                        }
                        view.hideProgressIndicator();
                    }
                });
    }

    @Override
    public void cancelRequests() {
        employeeService.cancelAll();
    }

    private void reset() {
        contactsList.clear();
        view.resetList();
        contactsPaging.reset();
    }

    // saving state stuff

    public void loadPresenterState(List<Employee> contacts, PaginatedResponse contactsPaging, String searchText, boolean searching) {
        if (contacts != null) {
            this.contactsList.addAll(contacts);
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
        return new ArrayList<>(contactsList);
    }

    public boolean isSearching() {
        return searching;
    }

    private Comparator<Employee> getEmployeeComparator (){
        return new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return o1.getFirstName().compareToIgnoreCase(o2.getFirstName());
            }
        };
    }

}
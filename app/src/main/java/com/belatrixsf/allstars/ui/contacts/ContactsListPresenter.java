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
package com.belatrixsf.allstars.ui.contacts;

import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.SearchEmployeeResponse;
import com.belatrixsf.allstars.services.EmployeeService;
import com.belatrixsf.allstars.ui.common.AllStarsPresenter;
import com.belatrixsf.allstars.utils.AllStarsCallback;
import com.belatrixsf.allstars.utils.ServiceError;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by icerrate on 15/04/2016.
 */
public class ContactsListPresenter extends AllStarsPresenter<ContactsListView> {

    private EmployeeService employeeService;
    private List<Employee> contacts = new ArrayList<>();
    private boolean inActionMode = false;
    private boolean profileEnabled = true;
    private PaginatedResponse contactPaginatedResponse = new PaginatedResponse();
    private String searchTerm = "";

    @Inject
    public ContactsListPresenter(ContactsListView view, EmployeeService employeeService) {
        super(view);
        this.employeeService = employeeService;
    }

    public void setProfileEnabled(boolean profileEnabled) {
        this.profileEnabled = profileEnabled;
    }

    public PaginatedResponse getContactPaginatedResponse() {
        return contactPaginatedResponse;
    }

    public List<Employee> getLoadedContacts(){
        return contacts;
    }

    public boolean isInActionMode(){
        return inActionMode;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setLoadedContacts(boolean inActionMode, List<Employee> contacts, PaginatedResponse contactPaginatedResponse, String searchTerm){
        this.contacts = contacts;
        this.contactPaginatedResponse = contactPaginatedResponse;
        this.searchTerm = searchTerm;
        this.inActionMode = inActionMode;
        if (inActionMode){
            searchContacts();
        }
        view.showCurrentPage(contactPaginatedResponse.getNextPage() == null ? 1 : contactPaginatedResponse.getNextPage());
        view.showContacts(contacts);
    }

    public void searchContacts() {
        view.showSearchActionMode();
    }

    public void startActionMode() {
        if (!inActionMode){
            inActionMode = true;
        }
    }

    public void stopSearchingContacts(){
        inActionMode = false;
        contactPaginatedResponse = new PaginatedResponse();
        getContacts();
    }

    public void getContacts() {
        searchTerm = "";
        getContactsInternal();
    }

    public void getContacts(String searchTerm) {
        this.searchTerm = searchTerm;
        contactPaginatedResponse = new PaginatedResponse();
        view.showCurrentPage(1);
        getContactsInternal();
    }

    public void callNextPage() {
        if (contactPaginatedResponse.getNextPage() != null) {
            getContactsInternal();
        }
    }

    public void getContactsInternal(){
        view.showProgressIndicator();
        employeeService.getEmployeeSearchList(searchTerm, contactPaginatedResponse.getNextPage(), new AllStarsCallback<SearchEmployeeResponse>() {
            @Override
            public void onSuccess(SearchEmployeeResponse searchEmployeeResponse) {
                if (contactPaginatedResponse.getNextPage() == null){
                    contacts.clear();
                }
                contacts.addAll(searchEmployeeResponse.getEmployeeList());
                contactPaginatedResponse.setNext(searchEmployeeResponse.getNext());
                view.hideProgressIndicator();
                view.showContacts(contacts);
            }

            @Override
            public void onFailure(ServiceError serviceError) {
                view.hideProgressIndicator();
                showError(serviceError.getErrorMessage());
            }
        });
    }

    public void onContactSelected(Object object) {
        if (object != null && object instanceof Employee) {
            Employee employee = (Employee) object;
            if (profileEnabled) {
                view.goToContactProfile(employee.getPk());
            } else {
                view.selectContact(employee);
            }
        }
    }

    public boolean getProfileEnabled() {
        return profileEnabled;
    }
}

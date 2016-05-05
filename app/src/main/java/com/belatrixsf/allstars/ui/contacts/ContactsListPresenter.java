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

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.networking.retrofit.responses.SearchEmployeeResponse;
import com.belatrixsf.allstars.services.EmployeeService;
import com.belatrixsf.allstars.ui.common.AllStarsPresenter;
import com.belatrixsf.allstars.utils.AllStarsCallback;
import com.belatrixsf.allstars.utils.ServiceError;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by icerrate on 15/04/2016.
 */
public class ContactsListPresenter extends AllStarsPresenter<ContactsListView> {

    private EmployeeService employeeService;
    private List<Employee> employees;
    private boolean inActionMode = false;
    private boolean profileEnabled = true;

    @Inject
    public ContactsListPresenter(ContactsListView view, EmployeeService employeeService) {
        super(view);
        this.employeeService = employeeService;
    }

    public void setProfileEnabled(boolean profileEnabled) {
        this.profileEnabled = profileEnabled;
    }

    public void setInActionMode(boolean inActionMode) {
        this.inActionMode = inActionMode;
    }

    public void shouldShowActionMode() {
        if (inActionMode){
            view.startActionMode();
        }
    }

    public List<Employee> getContacts(){
        return employees;
    }

    public boolean isInActionMode(){
        return inActionMode;
    }

    public void loadSavedContacts(List<Employee> employees){
        this.employees = employees;
    }

    public void getContacts(boolean force) {
        if (force || employees == null || employees.isEmpty()) {
            view.showProgressIndicator();
            employeeService.getEmployees(new AllStarsCallback<SearchEmployeeResponse>() {
                @Override
                public void onSuccess(SearchEmployeeResponse searchEmployeeResponse) {
                    employees = searchEmployeeResponse.getEmployeeList();
                    view.showContacts(searchEmployeeResponse.getEmployeeList());
                }

                @Override
                public void onFailure(ServiceError serviceError) {
                    showError(serviceError.getErrorMessage());
                }
            });
        }else{
            view.showContacts(employees);
        }
    }

    public void onSearchTermChange(String newSearchTerm){
        if (newSearchTerm.length()>0){
            view.showCleanButton();
        }else{
            view.hideCleanButton();
        }
    }

    public void submitSearchTerm(String searchTerm){
        if (!searchTerm.isEmpty()) {
            //view.showProgressIndicator();
            employeeService.getEmployeeSearchList(searchTerm, new AllStarsCallback<SearchEmployeeResponse>() {
                @Override
                public void onSuccess(SearchEmployeeResponse searchEmployeeResponse) {
                    employees = searchEmployeeResponse.getEmployeeList();
                    //view.hideProgressIndicator();
                    view.showContacts(searchEmployeeResponse.getEmployeeList());
                }

                @Override
                public void onFailure(ServiceError serviceError) {
                    showError(serviceError.getErrorMessage());
                }
            });
        }else{
            showError(getString(R.string.empty_search_term));
        }
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

}

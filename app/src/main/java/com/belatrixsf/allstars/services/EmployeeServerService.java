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
package com.belatrixsf.allstars.services;

import com.belatrixsf.allstars.entities.Category;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.networking.retrofit.RetrofitCallback;
import com.belatrixsf.allstars.networking.retrofit.api.EmployeeAPI;
import com.belatrixsf.allstars.networking.retrofit.requests.AuthenticationRequest;
import com.belatrixsf.allstars.networking.retrofit.responses.AuthenticationResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.SearchEmployeeResponse;
import com.belatrixsf.allstars.utils.AllStarsCallback;

import java.util.List;


/**
 * Created by gyosida on 4/12/16.
 */
public class EmployeeServerService extends AllStarsServerService implements EmployeeService {

    private EmployeeAPI employeeAPI;

    public EmployeeServerService(EmployeeAPI employeeAPI) {
        this.employeeAPI = employeeAPI;
    }

    @Override
    public void authenticate(String username, String password, AllStarsCallback<AuthenticationResponse> callback) {
        AuthenticationRequest request = new AuthenticationRequest(username, password);
        enqueue(employeeAPI.authenticate(request), new RetrofitCallback<AuthenticationResponse>(callback));
    }

    @Override
    public void getEmployee(int employeeId, AllStarsCallback<Employee> callback) {
        enqueue(employeeAPI.getEmployee(employeeId), new RetrofitCallback<Employee>(callback));
    }

    @Override
    public void getEmployeeSearchList(String searchTerm, Integer page, AllStarsCallback<SearchEmployeeResponse> callback) {
        enqueue(employeeAPI.getEmployeeSearchList(searchTerm, page), new RetrofitCallback<SearchEmployeeResponse>(callback));
    }

    @Override
    public void getRankingList(String kind, int quantity, AllStarsCallback<List<Employee>> callback) {
        enqueue(employeeAPI.getRankingList(kind, quantity), new RetrofitCallback<List<Employee>>(callback));
    }

    public void getEmployeeCategories(int employeeId, AllStarsCallback<List<Category>> callback) {
        enqueue(employeeAPI.getEmployeeCategories(employeeId), new RetrofitCallback<List<Category>>(callback));
    }

}
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
package com.belatrixsf.allstars.services.mock;

import com.belatrixsf.allstars.data.EmployeeMockDataSource;
import com.belatrixsf.allstars.entities.Category;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.entities.Location;
import com.belatrixsf.allstars.networking.retrofit.responses.AuthenticationResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.CreateEmployeeResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.allstars.services.AllStarsBaseService;
import com.belatrixsf.allstars.services.MockServiceRequest;
import com.belatrixsf.allstars.utils.exceptions.NotFoundException;
import com.belatrixsf.allstars.services.ServiceRequest;
import com.belatrixsf.allstars.services.contracts.EmployeeService;
import com.belatrixsf.allstars.utils.AllStarsCallback;
import com.belatrixsf.allstars.utils.ServiceError;
import com.belatrixsf.allstars.utils.exceptions.InvalidPageException;

import java.io.File;
import java.util.List;

/**
 * Created by gyosida on 6/7/16.
 */
public class EmployeeMockService extends AllStarsBaseService implements EmployeeService {

    private EmployeeMockDataSource employeeMockDataSource;

    public EmployeeMockService(EmployeeMockDataSource employeeMockDataSource) {
        this.employeeMockDataSource = employeeMockDataSource;
    }

    @Override
    public ServiceRequest authenticate(String username, String password, AllStarsCallback<AuthenticationResponse> callback) {
        try {
            AuthenticationResponse authenticationResponse = employeeMockDataSource.authenticate(username, password);
            ServiceRequest<AuthenticationResponse> serviceRequest = new MockServiceRequest<>(authenticationResponse);
            enqueue(serviceRequest, callback);
            return serviceRequest;
        } catch (NotFoundException e) {
            ServiceError serviceError = new ServiceError(401, "Invalid credentials");
            callback.onFailure(serviceError);
            return null;
        }
    }

    @Override
    public ServiceRequest createEmployee(String email, AllStarsCallback<CreateEmployeeResponse> callback) {
        return null;
    }

    @Override
    public ServiceRequest getEmployee(int employeeId, AllStarsCallback<Employee> callback) {
        try {
            Employee employee = employeeMockDataSource.getEmployee(employeeId);
            ServiceRequest<Employee> serviceRequest = new MockServiceRequest<>(employee);
            enqueue(serviceRequest, callback);
            return serviceRequest;
        } catch (NotFoundException e) {
            ServiceError serviceError = new ServiceError(404, "Employee not found");
            callback.onFailure(serviceError);
            return null;
        }
    }

    @Override
    public ServiceRequest getEmployeeSearchList(String searchTerm, Integer page, AllStarsCallback<PaginatedResponse<Employee>> callback) {
        try {
            PaginatedResponse<Employee> response = employeeMockDataSource.search(searchTerm, page == null? 0 : page);
            ServiceRequest<PaginatedResponse<Employee>> serviceRequest = new MockServiceRequest<>(response);
            enqueue(serviceRequest, callback);
            return serviceRequest;
        } catch (InvalidPageException e) {
            ServiceError serviceError = new ServiceError(500, "An invalid page was sent, make sure page >= 0");
            callback.onFailure(serviceError);
            return null;
        }
    }

    @Override
    public ServiceRequest getRankingList(String kind, int quantity, AllStarsCallback<List<Employee>> callback) {
        return null;
    }

    @Override
    public ServiceRequest getEmployeeCategories(int employeeId, AllStarsCallback<List<Category>> callback) {
        return null;
    }

    @Override
    public ServiceRequest updateEmployee(int employeeId, String firstName, String lastName, String skypeId, int locationId, AllStarsCallback<Employee> callback) {
        return null;
    }

    @Override
    public ServiceRequest getEmployeeLocations(AllStarsCallback<List<Location>> callback) {
        return null;
    }

    @Override
    public ServiceRequest updateEmployeeImage(int employeeId, File file, AllStarsCallback<Employee> callback) {
        return null;
    }

    @Override
    public ServiceRequest resetPassword(int employeeId, String oldPassword, String newPassword, AllStarsCallback<Employee> callback) {
        return null;
    }

    @Override
    public void cancelAll() {

    }
}

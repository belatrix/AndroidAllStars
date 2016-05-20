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
package com.belatrixsf.allstars.services.server;

import com.belatrixsf.allstars.entities.Category;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.entities.Location;
import com.belatrixsf.allstars.networking.retrofit.api.EmployeeAPI;
import com.belatrixsf.allstars.networking.retrofit.requests.AuthenticationRequest;
import com.belatrixsf.allstars.networking.retrofit.requests.CreateEmployeeRequest;
import com.belatrixsf.allstars.networking.retrofit.requests.UpdateEmployeeRequest;
import com.belatrixsf.allstars.networking.retrofit.responses.AuthenticationResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.CreateEmployeeResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.SearchEmployeeResponse;
import com.belatrixsf.allstars.services.AllStarsBaseService;
import com.belatrixsf.allstars.services.ServerServiceRequest;
import com.belatrixsf.allstars.services.ServiceRequest;
import com.belatrixsf.allstars.services.contracts.EmployeeService;
import com.belatrixsf.allstars.utils.AllStarsCallback;

import java.util.List;

import retrofit2.Call;


/**
 * Created by gyosida on 4/12/16.
 */
public class EmployeeServerService extends AllStarsBaseService implements EmployeeService {

    private EmployeeAPI employeeAPI;

    public EmployeeServerService(EmployeeAPI employeeAPI) {
        this.employeeAPI = employeeAPI;
    }

    @Override
    public ServiceRequest authenticate(String username, String password, AllStarsCallback<AuthenticationResponse> callback) {
        AuthenticationRequest request = new AuthenticationRequest(username, password);
        Call<AuthenticationResponse> call = employeeAPI.authenticate(request);
        ServiceRequest<AuthenticationResponse> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest createEmployee(String email, AllStarsCallback<CreateEmployeeResponse> callback) {
        CreateEmployeeRequest request = new CreateEmployeeRequest(email);
        Call<CreateEmployeeResponse> call = employeeAPI.createEmployee(request);
        ServiceRequest<CreateEmployeeResponse> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest getEmployee(int employeeId, AllStarsCallback<Employee> callback) {
        Call<Employee> call = employeeAPI.getEmployee(employeeId);
        ServiceRequest<Employee> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest getEmployeeSearchList(String searchTerm, Integer page, AllStarsCallback<SearchEmployeeResponse> callback) {
        Call<SearchEmployeeResponse> call = employeeAPI.getEmployeeSearchList(searchTerm, page);
        ServiceRequest<SearchEmployeeResponse> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest getRankingList(String kind, int quantity, AllStarsCallback<List<Employee>> callback) {
        Call<List<Employee>> call = employeeAPI.getRankingList(kind, quantity);
        ServiceRequest<List<Employee>> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    public ServiceRequest getEmployeeCategories(int employeeId, AllStarsCallback<List<Category>> callback) {
        Call<List<Category>> call = employeeAPI.getEmployeeCategories(employeeId);
        ServiceRequest<List<Category>> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }


    @Override
    public ServiceRequest updateEmployee(int employeeId, String firstName, String lastName, String skypeId, int locationId, AllStarsCallback<Employee> callback) {
        UpdateEmployeeRequest updateEmployeeRequest = new UpdateEmployeeRequest(firstName, lastName, skypeId, locationId);
        Call<Employee> call = employeeAPI.updateEmployee(employeeId, updateEmployeeRequest);
        ServiceRequest<Employee> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest getEmployeeLocations(AllStarsCallback<List<Location>> callback) {
        Call<List<Location>> call = employeeAPI.getEmployeeLocations();
        ServiceRequest<List<Location>> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

}
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
package com.belatrixsf.connect.services.server;

import com.belatrixsf.connect.entities.Category;
import com.belatrixsf.connect.entities.Employee;
import com.belatrixsf.connect.entities.Location;
import com.belatrixsf.connect.networking.retrofit.api.EmployeeAPI;
import com.belatrixsf.connect.networking.retrofit.requests.AuthenticationRequest;
import com.belatrixsf.connect.networking.retrofit.requests.CreateEmployeeRequest;
import com.belatrixsf.connect.networking.retrofit.requests.RegisterDeviceRequest;
import com.belatrixsf.connect.networking.retrofit.requests.ResetPasswordRequest;
import com.belatrixsf.connect.networking.retrofit.requests.UpdateEmployeeRequest;
import com.belatrixsf.connect.networking.retrofit.responses.AuthenticationResponse;
import com.belatrixsf.connect.networking.retrofit.responses.CreateEmployeeResponse;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.services.BelatrixConnectBaseService;
import com.belatrixsf.connect.services.ServerServiceRequest;
import com.belatrixsf.connect.services.ServiceRequest;
import com.belatrixsf.connect.services.contracts.EmployeeService;
import com.belatrixsf.connect.utils.BelatrixConnectCallback;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;


/**
 * Created by gyosida on 4/12/16.
 */
public class EmployeeServerService extends BelatrixConnectBaseService implements EmployeeService {

    private EmployeeAPI employeeAPI;

    public EmployeeServerService(EmployeeAPI employeeAPI) {
        this.employeeAPI = employeeAPI;
    }

    @Override
    public ServiceRequest authenticate(String username, String password, BelatrixConnectCallback<AuthenticationResponse> callback) {
        AuthenticationRequest request = new AuthenticationRequest(username, password);
        Call<AuthenticationResponse> call = employeeAPI.authenticate(request);
        ServiceRequest<AuthenticationResponse> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest createEmployee(String email, BelatrixConnectCallback<CreateEmployeeResponse> callback) {
        CreateEmployeeRequest request = new CreateEmployeeRequest(email);
        Call<CreateEmployeeResponse> call = employeeAPI.createEmployee(request);
        ServiceRequest<CreateEmployeeResponse> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest getEmployee(int employeeId, BelatrixConnectCallback<Employee> callback) {
        Call<Employee> call = employeeAPI.getEmployee(employeeId);
        ServiceRequest<Employee> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest getEmployeeSearchList(String searchTerm, Integer page, BelatrixConnectCallback<PaginatedResponse<Employee>> callback) {
        Call<PaginatedResponse<Employee>> call = employeeAPI.getEmployeeSearchList(searchTerm, page);
        ServiceRequest<PaginatedResponse<Employee>> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest getRankingList(String kind, int quantity, BelatrixConnectCallback<List<Employee>> callback) {
        Call<List<Employee>> call = employeeAPI.getRankingList(kind, quantity);
        ServiceRequest<List<Employee>> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest getEmployeeCategories(int employeeId, BelatrixConnectCallback<List<Category>> callback) {
        Call<List<Category>> call = employeeAPI.getEmployeeCategories(employeeId);
        ServiceRequest<List<Category>> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest updateEmployee(int employeeId, String firstName, String lastName, String skypeId, int locationId, BelatrixConnectCallback<Employee> callback) {
        UpdateEmployeeRequest updateEmployeeRequest = new UpdateEmployeeRequest(firstName, lastName, skypeId, locationId);
        Call<Employee> call = employeeAPI.updateEmployee(employeeId, updateEmployeeRequest);
        ServiceRequest<Employee> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest getEmployeeLocations(BelatrixConnectCallback<List<Location>> callback) {
        Call<List<Location>> call = employeeAPI.getEmployeeLocations();
        ServiceRequest<List<Location>> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest updateEmployeeImage(int employeeId, File file, BelatrixConnectCallback<Employee> callback) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        Call<Employee> call = employeeAPI.updateEmployeeImage(employeeId, body);
        ServiceRequest<Employee> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest resetPassword(int employeeId, String oldPassword, String newPassword, BelatrixConnectCallback<Employee> callback) {
        ResetPasswordRequest request = new ResetPasswordRequest(oldPassword, newPassword);
        Call<Employee> call = employeeAPI.resetPassword(employeeId, request);
        ServiceRequest<Employee> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest registerDevice(int employeeId, String deviceToken, BelatrixConnectCallback<Void> callback) {
        RegisterDeviceRequest request = new RegisterDeviceRequest(deviceToken);
        Call<Void> call = employeeAPI.registerDevice(employeeId, request);
        ServiceRequest<Void> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }
}
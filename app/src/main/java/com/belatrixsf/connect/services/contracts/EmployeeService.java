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
package com.belatrixsf.connect.services.contracts;

import com.belatrixsf.connect.entities.Category;
import com.belatrixsf.connect.entities.Employee;
import com.belatrixsf.connect.entities.Location;
import com.belatrixsf.connect.entities.Notification;
import com.belatrixsf.connect.entities.SiteInfo;
import com.belatrixsf.connect.entities.Star;
import com.belatrixsf.connect.networking.retrofit.responses.AuthenticationResponse;
import com.belatrixsf.connect.networking.retrofit.responses.CreateEmployeeResponse;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.networking.retrofit.responses.RequestNewPasswordResponse;
import com.belatrixsf.connect.services.ServiceRequest;
import com.belatrixsf.connect.utils.BelatrixConnectCallback;

import java.io.File;
import java.util.List;

/**
 * Created by gyosida on 4/12/16.
 */
public interface EmployeeService extends BelatrixConnectService {

    ServiceRequest authenticate(String username, String password, BelatrixConnectCallback<AuthenticationResponse> callback);

    ServiceRequest createEmployee(String email, BelatrixConnectCallback<CreateEmployeeResponse> callback);

    ServiceRequest getEmployee(int employeeId, BelatrixConnectCallback<Employee> callback);

    ServiceRequest getEmployeeSearchList(String searchTerm, Integer page, BelatrixConnectCallback<PaginatedResponse<Employee>> callback);

    ServiceRequest getRankingList(String kind, BelatrixConnectCallback<List<Employee>> callback);

    ServiceRequest getEmployeeCategories(int employeeId, BelatrixConnectCallback<List<Category>> callback);

    ServiceRequest updateEmployee(int employeeId, String firstName, String lastName, String skypeId, int locationId, BelatrixConnectCallback<Employee> callback);

    ServiceRequest getEmployeeLocations(BelatrixConnectCallback<List<Location>> callback);

    ServiceRequest getEmployeeNotifications(Integer page, BelatrixConnectCallback<PaginatedResponse<Notification>> callback);

    ServiceRequest updateEmployeeImage(int employeeId, File file, BelatrixConnectCallback<Employee> callback);

    ServiceRequest resetPassword(int employeeId, String oldPassword, String newPassword, BelatrixConnectCallback<Employee> callback);

    ServiceRequest requestNewPassword(String employeeEmail, BelatrixConnectCallback<RequestNewPasswordResponse> callback);

    ServiceRequest registerDevice(int employeeId, String deviceToken, BelatrixConnectCallback<Void> callback);

    ServiceRequest logout(BelatrixConnectCallback<Void> callback);

    ServiceRequest getSiteInfo(BelatrixConnectCallback<SiteInfo> callback);

    ServiceRequest getEmployeeRecommendationList(int employeeId, int categoryId, BelatrixConnectCallback<PaginatedResponse<Star>> callback);
}

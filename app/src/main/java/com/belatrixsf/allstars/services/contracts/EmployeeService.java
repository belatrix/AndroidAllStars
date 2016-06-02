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
package com.belatrixsf.allstars.services.contracts;

import com.belatrixsf.allstars.entities.Category;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.entities.Location;
import com.belatrixsf.allstars.networking.retrofit.responses.AuthenticationResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.CreateEmployeeResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.SearchEmployeeResponse;
import com.belatrixsf.allstars.services.ServiceRequest;
import com.belatrixsf.allstars.utils.AllStarsCallback;

import java.io.File;
import java.util.List;

/**
 * Created by gyosida on 4/12/16.
 */
public interface EmployeeService extends AllStarsService {

    ServiceRequest authenticate(String username, String password, AllStarsCallback<AuthenticationResponse> callback);

    ServiceRequest createEmployee(String email, AllStarsCallback<CreateEmployeeResponse> callback);

    ServiceRequest getEmployee(int employeeId, AllStarsCallback<Employee> callback);

    ServiceRequest getEmployeeSearchList(String searchTerm, Integer page, AllStarsCallback<SearchEmployeeResponse> callback);

    ServiceRequest getRankingList(String kind, int quantity, AllStarsCallback<List<Employee>> callback);

    ServiceRequest getEmployeeCategories(int employeeId, AllStarsCallback<List<Category>> callback);

    ServiceRequest updateEmployee(int employeeId, String firstName, String lastName, String skypeId, int locationId, AllStarsCallback<Employee> callback);

    ServiceRequest getEmployeeLocations(AllStarsCallback<List<Location>> callback);

    ServiceRequest updateEmployeeImage(int employeeId, File file, AllStarsCallback<Employee> callback);

    ServiceRequest resetPassword(int employeeId, String oldPassword, String newPassword, AllStarsCallback<Employee> callback);

}

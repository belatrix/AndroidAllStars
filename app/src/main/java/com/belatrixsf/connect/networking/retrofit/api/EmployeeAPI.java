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
package com.belatrixsf.connect.networking.retrofit.api;

import com.belatrixsf.connect.entities.Category;
import com.belatrixsf.connect.entities.Employee;
import com.belatrixsf.connect.entities.Location;
import com.belatrixsf.connect.networking.retrofit.requests.AuthenticationRequest;
import com.belatrixsf.connect.networking.retrofit.requests.CreateEmployeeRequest;
import com.belatrixsf.connect.networking.retrofit.requests.ResetPasswordRequest;
import com.belatrixsf.connect.networking.retrofit.requests.UpdateEmployeeRequest;
import com.belatrixsf.connect.networking.retrofit.responses.AuthenticationResponse;
import com.belatrixsf.connect.networking.retrofit.responses.CreateEmployeeResponse;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by gyosida on 4/11/16.
 */
public interface EmployeeAPI {

    @POST(ServerPaths.EMPLOYEE_AUTHENTICATE)
    Call<AuthenticationResponse> authenticate(@Body AuthenticationRequest request);

    @POST(ServerPaths.EMPLOYEE_CREATE)
    Call<CreateEmployeeResponse> createEmployee(@Body CreateEmployeeRequest request);

    @POST(ServerPaths.EMPLOYEE_RESET_PASSWORD)
    Call<Employee> resetPassword(@Path(ServerPaths.EMPLOYEE_ID) int employeeId, @Body ResetPasswordRequest request);

    @GET(ServerPaths.EMPLOYEE_BY_ID)
    Call<Employee> getEmployee(@Path(ServerPaths.EMPLOYEE_ID) int employeeId);

    @GET(ServerPaths.EMPLOYEE_LIST)
    Call<PaginatedResponse<Employee>> getEmployeeSearchList(@Query(ServerPaths.SEARCH_TERM) String searchTerm, @Query(ServerPaths.QUERY_PAGE) Integer page);

    @GET(ServerPaths.RANKING_LIST)
    Call<List<Employee>> getRankingList(@Path(ServerPaths.KIND) String kind, @Path(ServerPaths.QUANTITY) int quantity);

    @GET(ServerPaths.EMPLOYEE_CATEGORIES)
    Call<List<Category>> getEmployeeCategories(@Path(ServerPaths.EMPLOYEE_ID) int employeeId);

    @PATCH(ServerPaths.EMPLOYEE_UPDATE)
    Call<Employee> updateEmployee(@Path(ServerPaths.EMPLOYEE_ID) int employeeId, @Body UpdateEmployeeRequest updateEmployeeRequest);

    @GET(ServerPaths.EMPLOYEE_LOCATION_LIST)
    Call<List<Location>> getEmployeeLocations();

    @Multipart
    @POST(ServerPaths.EMPLOYEE_AVATAR)
    Call<Employee> updateEmployeeImage(@Path(ServerPaths.EMPLOYEE_ID) int employeeId, @Part MultipartBody.Part file);

}

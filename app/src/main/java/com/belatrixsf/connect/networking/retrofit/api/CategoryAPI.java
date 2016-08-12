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

import com.belatrixsf.connect.entities.SubCategory;
import com.belatrixsf.connect.entities.Keyword;
import com.belatrixsf.connect.networking.retrofit.requests.EmployeeKeywordRequest;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

/**
 * Created by gyosida on 4/26/16.
 */
public interface CategoryAPI {

    @GET(ServerPaths.SUBCATEGORIES_BY_CATEGORY_ID)
    Call<List<SubCategory>> getSubcategories(@Path(ServerPaths.CATEGORY_ID) int categoryId);

    @GET(ServerPaths.CATEGORY_KEYWORD_LIST)
    Call<List<Keyword>> getKeywords();

    @GET(ServerPaths.EMPLOYEE_SKILLS)
    Call<PaginatedResponse<Keyword>> getKeywordsByEmployee(@Path(ServerPaths.EMPLOYEE_ID) int employeeId);

    @PATCH(ServerPaths.EMPLOYEE_ADD_SKILL)
    Call<Keyword> saveEmployeeSkill(@Path(ServerPaths.EMPLOYEE_ID) int employeeId, @Body EmployeeKeywordRequest employeeKeywordRequest);

    @PATCH(ServerPaths.EMPLOYEE_REMOVE_SKILL)
    Call<Keyword> removeEmployeeSkill(@Path(ServerPaths.EMPLOYEE_ID) int employeeId, @Body EmployeeKeywordRequest employeeKeywordRequest);
}
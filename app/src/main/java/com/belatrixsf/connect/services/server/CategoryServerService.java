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
import com.belatrixsf.connect.entities.Keyword;
import com.belatrixsf.connect.entities.SubCategory;
import com.belatrixsf.connect.networking.retrofit.api.CategoryAPI;
import com.belatrixsf.connect.networking.retrofit.requests.EmployeeKeywordRequest;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.services.BelatrixConnectBaseService;
import com.belatrixsf.connect.services.ServerServiceRequest;
import com.belatrixsf.connect.services.ServiceRequest;
import com.belatrixsf.connect.services.contracts.CategoryService;
import com.belatrixsf.connect.utils.BelatrixConnectCallback;
import com.belatrixsf.connect.utils.ServiceError;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by gyosida on 4/27/16.
 */
public class CategoryServerService extends BelatrixConnectBaseService implements CategoryService {

    private CategoryAPI categoryAPI;

    public CategoryServerService(CategoryAPI categoryAPI) {
        this.categoryAPI = categoryAPI;
    }

    @Override
    public ServiceRequest getSubcategories(int categoryId, final BelatrixConnectCallback<List<Category>> callback) {
        Call<List<SubCategory>> call = categoryAPI.getSubcategories(categoryId);
        ServiceRequest<List<SubCategory>> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, new BelatrixConnectCallback<List<SubCategory>>() {
            @Override
            public void onSuccess(List<SubCategory> subCategories) {
                callback.onSuccess(new ArrayList<Category>(subCategories));
            }

            @Override
            public void onFailure(ServiceError serviceError) {
                callback.onFailure(serviceError);
            }
        });
        return serviceRequest;
    }

    @Override
    public ServiceRequest getKeywords(BelatrixConnectCallback<List<Keyword>> callback) {
        Call<List<Keyword>> call = categoryAPI.getKeywords();
        ServiceRequest<List<Keyword>> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest getKeywordsByEmployee(int employeeId, BelatrixConnectCallback<PaginatedResponse<Keyword>> callback) {
        Call<PaginatedResponse<Keyword>> call = categoryAPI.getKeywordsByEmployee(employeeId);
        ServiceRequest<PaginatedResponse<Keyword>> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest saveKeywordToEmployee(int employeeId, String keywordName, BelatrixConnectCallback<Keyword> callback) {
        EmployeeKeywordRequest employeeKeywordRequest = new EmployeeKeywordRequest(keywordName);
        Call<Keyword> keywordCall = categoryAPI.saveEmployeeSkill(employeeId, employeeKeywordRequest);
        ServiceRequest<Keyword> serviceRequest = new ServerServiceRequest<>(keywordCall);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest removeEmployeeKeyword(int employeeId, String keywordName, BelatrixConnectCallback<Keyword> callback) {
        EmployeeKeywordRequest employeeKeywordRequest = new EmployeeKeywordRequest(keywordName);
        Call<Keyword> keywordCall = categoryAPI.removeEmployeeSkill(employeeId, employeeKeywordRequest);
        ServiceRequest<Keyword> serviceRequest = new ServerServiceRequest<>(keywordCall);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }
}
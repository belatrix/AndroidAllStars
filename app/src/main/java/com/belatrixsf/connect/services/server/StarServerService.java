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
import com.belatrixsf.connect.entities.Keyword;
import com.belatrixsf.connect.entities.Star;
import com.belatrixsf.connect.entities.SubCategory;
import com.belatrixsf.connect.networking.retrofit.api.StarAPI;
import com.belatrixsf.connect.networking.retrofit.requests.StarRequest;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.networking.retrofit.responses.StarResponse;
import com.belatrixsf.connect.services.BelatrixConnectBaseService;
import com.belatrixsf.connect.services.ServerServiceRequest;
import com.belatrixsf.connect.services.ServiceRequest;
import com.belatrixsf.connect.services.contracts.StarService;
import com.belatrixsf.connect.utils.BelatrixConnectCallback;

import retrofit2.Call;

/**
 * Created by PedroCarrillo on 4/26/16.
 */
public class StarServerService extends BelatrixConnectBaseService implements StarService {

    private StarAPI starAPI;

    public StarServerService(StarAPI starAPI) {
        this.starAPI = starAPI;
    }

    @Override
    public ServiceRequest getEmployeeSubCategoriesStars(final int employeeId, BelatrixConnectCallback<PaginatedResponse<SubCategory>> callback) {
        Call<PaginatedResponse<SubCategory>> call = starAPI.getEmployeeSubCategoriesStars(employeeId);
        ServiceRequest<PaginatedResponse<SubCategory>> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }


    @Override
    public ServiceRequest star(int fromEmployeeId, int toEmployeeId, StarRequest starRequest, BelatrixConnectCallback<StarResponse> callback) {
        Call<StarResponse> call = starAPI.star(fromEmployeeId, toEmployeeId, starRequest);
        ServiceRequest<StarResponse> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest getStars(int employeeId, int subcategory, Integer page, BelatrixConnectCallback<PaginatedResponse<Star>> callback) {
        Call<PaginatedResponse<Star>> call = starAPI.getStars(employeeId, subcategory, page);
        ServiceRequest<PaginatedResponse<Star>> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest getStarsKeywordTopList(int keywordId, Integer page, BelatrixConnectCallback<PaginatedResponse<Employee>> callback) {
        Call<PaginatedResponse<Employee>> call = starAPI.getStarsKeywordTop(keywordId, page);
        ServiceRequest<PaginatedResponse<Employee>> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest getStarsByKeywords(String search, Integer next, BelatrixConnectCallback<PaginatedResponse<Keyword>> callback) {
        Call<PaginatedResponse<Keyword>> call = starAPI.getStarsByKeywords(search, next);
        ServiceRequest<PaginatedResponse<Keyword>> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

}
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

import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.entities.Keyword;
import com.belatrixsf.allstars.entities.Star;
import com.belatrixsf.allstars.networking.retrofit.api.StarAPI;
import com.belatrixsf.allstars.networking.retrofit.requests.StarRequest;
import com.belatrixsf.allstars.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.StarResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.StarSubCategoryResponse;
import com.belatrixsf.allstars.services.AllStarsBaseService;
import com.belatrixsf.allstars.services.ServiceRequest;
import com.belatrixsf.allstars.services.contracts.StarService;
import com.belatrixsf.allstars.services.ServerServiceRequest;
import com.belatrixsf.allstars.utils.AllStarsCallback;

import retrofit2.Call;

/**
 * Created by PedroCarrillo on 4/26/16.
 */
public class StarServerService extends AllStarsBaseService implements StarService {

    private StarAPI starAPI;

    public StarServerService(StarAPI starAPI) {
        this.starAPI = starAPI;
    }

    @Override
    public ServiceRequest getEmployeeSubCategoriesStars(final int employeeId, AllStarsCallback<StarSubCategoryResponse> callback) {
        Call<StarSubCategoryResponse> call = starAPI.getEmployeeSubCategoriesStars(employeeId);
        ServiceRequest<StarSubCategoryResponse> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest star(int fromEmployeeId, int toEmployeeId, StarRequest starRequest, AllStarsCallback<StarResponse> callback) {
        Call<StarResponse> call = starAPI.star(fromEmployeeId, toEmployeeId, starRequest);
        ServiceRequest<StarResponse> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest getStars(int employeeId, int subcategory, Integer page, AllStarsCallback<PaginatedResponse<Star>> callback) {
        Call<PaginatedResponse<Star>> call = starAPI.getStars(employeeId, subcategory, page);
        ServiceRequest<PaginatedResponse<Star>> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest getStarsKeywordTopList(int keywordId, Integer page, AllStarsCallback<PaginatedResponse<Employee>> callback) {
        Call<PaginatedResponse<Employee>> call = starAPI.getStarsKeywordTop(keywordId, page);
        ServiceRequest<PaginatedResponse<Employee>> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }

    @Override
    public ServiceRequest getStarsByKeywords(String search, Integer next, AllStarsCallback<PaginatedResponse<Keyword>> callback) {
        Call<PaginatedResponse<Keyword>> call = starAPI.getStarsByKeywords(search, next);
        ServiceRequest<PaginatedResponse<Keyword>> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }
}
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

import com.belatrixsf.allstars.data.StarMockDataSource;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.entities.Keyword;
import com.belatrixsf.allstars.entities.Star;
import com.belatrixsf.allstars.entities.SubCategory;
import com.belatrixsf.allstars.networking.retrofit.requests.StarRequest;
import com.belatrixsf.allstars.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.StarResponse;
import com.belatrixsf.allstars.services.AllStarsBaseService;
import com.belatrixsf.allstars.services.MockServiceRequest;
import com.belatrixsf.allstars.services.ServiceRequest;
import com.belatrixsf.allstars.services.contracts.StarService;
import com.belatrixsf.allstars.utils.AllStarsCallback;
import com.belatrixsf.allstars.utils.ServiceErrorUtils;
import com.belatrixsf.allstars.utils.exceptions.InvalidPageException;


/**
 * Created by gyosida on 6/13/16.
 */
public class StarMockService extends AllStarsBaseService implements StarService  {

    private StarMockDataSource starMockDataSource;

    public StarMockService(StarMockDataSource starMockDataSource) {
        this.starMockDataSource = starMockDataSource;
    }

    @Override
    public ServiceRequest getEmployeeSubCategoriesStars(int employeeId, AllStarsCallback<PaginatedResponse<SubCategory>> callback) {
        try {
            PaginatedResponse<SubCategory> paginatedResponse = starMockDataSource.getEmployeeSubcategoriesStars(employeeId, null);
            ServiceRequest<PaginatedResponse<SubCategory>> serviceRequest = new MockServiceRequest<>(paginatedResponse);
            enqueue(serviceRequest, callback);
            return serviceRequest;
        } catch (InvalidPageException e) {
            callback.onFailure(ServiceErrorUtils.createBadRequestError(e.getMessage()));
        }
        return null;
    }

    @Override
    public ServiceRequest star(int fromEmployeeId, int toEmployeeId, StarRequest starRequest, AllStarsCallback<StarResponse> callback) {
        return null;
    }

    @Override
    public ServiceRequest getStarsByKeywords(String search, Integer next, AllStarsCallback<PaginatedResponse<Keyword>> callback) {
        return null;
    }

    @Override
    public ServiceRequest getStars(int employeeId, int subcategory, Integer page, AllStarsCallback<PaginatedResponse<Star>> callback) {
        return null;
    }

    @Override
    public ServiceRequest getStarsKeywordTopList(int keywordId, Integer page, AllStarsCallback<PaginatedResponse<Employee>> callback) {
        return null;
    }

    @Override
    public void cancelAll() {

    }
}

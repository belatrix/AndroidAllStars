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
import com.belatrixsf.allstars.entities.Keyword;
import com.belatrixsf.allstars.entities.SubCategory;
import com.belatrixsf.allstars.networking.retrofit.api.CategoryAPI;
import com.belatrixsf.allstars.services.AllStarsBaseService;
import com.belatrixsf.allstars.services.ServerServiceRequest;
import com.belatrixsf.allstars.services.ServiceRequest;
import com.belatrixsf.allstars.services.contracts.CategoryService;
import com.belatrixsf.allstars.utils.AllStarsCallback;
import com.belatrixsf.allstars.utils.ServiceError;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by gyosida on 4/27/16.
 */
public class CategoryServerService extends AllStarsBaseService implements CategoryService {

    private CategoryAPI categoryAPI;

    public CategoryServerService(CategoryAPI categoryAPI) {
        this.categoryAPI = categoryAPI;
    }

    @Override
    public ServiceRequest getSubcategories(int categoryId, final AllStarsCallback<List<Category>> callback) {
        Call<List<SubCategory>> call = categoryAPI.getSubcategories(categoryId);
        ServiceRequest<List<SubCategory>> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, new AllStarsCallback<List<SubCategory>>() {
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
    public ServiceRequest getKeywords(AllStarsCallback<List<Keyword>> callback) {
        Call<List<Keyword>> call = categoryAPI.getKeywords();
        ServiceRequest<List<Keyword>> serviceRequest = new ServerServiceRequest<>(call);
        enqueue(serviceRequest, callback);
        return serviceRequest;
    }
}
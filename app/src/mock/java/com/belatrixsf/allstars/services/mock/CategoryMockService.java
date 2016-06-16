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

import com.belatrixsf.allstars.data.CategoryMockDataSource;
import com.belatrixsf.allstars.entities.Category;
import com.belatrixsf.allstars.entities.Keyword;
import com.belatrixsf.allstars.services.ServiceRequest;
import com.belatrixsf.allstars.services.contracts.CategoryService;
import com.belatrixsf.allstars.utils.AllStarsCallback;

import java.util.List;

/**
 * Created by gyosida on 6/13/16.
 */
public class CategoryMockService implements CategoryService {

    private CategoryMockDataSource categoryMockDataSource;

    public CategoryMockService(CategoryMockDataSource categoryMockDataSource) {
        this.categoryMockDataSource = categoryMockDataSource;
    }

    @Override
    public ServiceRequest getSubcategories(int categoryId, AllStarsCallback<List<Category>> callback) {
        return null;
    }

    @Override
    public ServiceRequest getKeywords(AllStarsCallback<List<Keyword>> callback) {
        return null;
    }

    @Override
    public void cancelAll() {

    }
}

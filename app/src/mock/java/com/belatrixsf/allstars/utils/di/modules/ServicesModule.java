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
package com.belatrixsf.allstars.utils.di.modules;

import com.belatrixsf.allstars.data.CategoryMockDataSource;
import com.belatrixsf.allstars.data.EmployeeMockDataSource;
import com.belatrixsf.allstars.data.StarMockDataSource;
import com.belatrixsf.allstars.services.contracts.CategoryService;
import com.belatrixsf.allstars.services.contracts.EmployeeService;
import com.belatrixsf.allstars.services.contracts.StarService;
import com.belatrixsf.allstars.services.mock.CategoryMockService;
import com.belatrixsf.allstars.services.mock.EmployeeMockService;
import com.belatrixsf.allstars.services.mock.StarMockService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gyosida on 6/13/16.
 */
@Module
public class ServicesModule {

    @Provides
    public EmployeeService providesEmployeeService(EmployeeMockDataSource employeeMockDataSource) {
        return new EmployeeMockService(employeeMockDataSource);
    }

    @Provides
    public StarService providesStarService(StarMockDataSource starMockDataSource) {
        return new StarMockService(starMockDataSource);
    }

    @Provides
    public CategoryService provideCategoryService(CategoryMockDataSource categoryMockDataSource) {
        return new CategoryMockService(categoryMockDataSource);
    }

}

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
package com.belatrixsf.allstars.data;

import com.belatrixsf.allstars.data.mappers.EmployeeSubcategoriesStarsMapper;
import com.belatrixsf.allstars.entities.SubCategory;
import com.belatrixsf.allstars.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.allstars.utils.exceptions.InvalidPageException;
import com.belatrixsf.allstars.utils.search.SearchingEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gyosida on 6/13/16.
 */
public class StarMockDataSource {

    private List<EmployeeSubcategoriesStarsMapper> employeeSubcategoriesStarsMappers = new ArrayList<>();

    public StarMockDataSource(List<EmployeeSubcategoriesStarsMapper> employeeSubcategoriesStarsMappers) {
        if (employeeSubcategoriesStarsMappers != null) {
            this.employeeSubcategoriesStarsMappers.addAll(employeeSubcategoriesStarsMappers);
        }
    }

    public PaginatedResponse<SubCategory> getEmployeeSubcategoriesStars(int employeeId, Integer page) throws InvalidPageException {
        EmployeeSubcategoriesStarsMapper mapper = matchEmployeeSubcategoriesStarsMapper(employeeId);
        List<SubCategory> subcategories = new ArrayList<>();
        if (mapper != null) {
            subcategories.addAll(mapper.getSubcategories());
        }
        SearchingEngine<SubCategory> searchingEngine = new SearchingEngine<>(subcategories);
        return searchingEngine.search(page);
    }

    private EmployeeSubcategoriesStarsMapper matchEmployeeSubcategoriesStarsMapper(int employeeId) {
        for(EmployeeSubcategoriesStarsMapper mapper : employeeSubcategoriesStarsMappers) {
            if (mapper.getEmployeeId() == employeeId) {
                return mapper;
            }
        }
        return null;
    }

}

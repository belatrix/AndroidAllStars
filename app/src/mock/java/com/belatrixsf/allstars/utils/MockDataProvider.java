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
package com.belatrixsf.allstars.utils;

import com.belatrixsf.allstars.data.mappers.EmployeeSubcategoriesStarsMapper;
import com.belatrixsf.allstars.entities.Employee;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by gyosida on 6/16/16.
 */
public class MockDataProvider {

    private static final String MOCKS_FOLDER = "mocks/";

    public static List<Employee> getEmployees() {
        String json = IOUtils.readFileFromAssets(MOCKS_FOLDER + "employees.json");
        try {
            return new Gson().fromJson(json, new TypeToken<List<Employee>>(){}.getType());
        } catch (JsonSyntaxException e) {
             return null;
        }
    }

    public static List<EmployeeSubcategoriesStarsMapper> getEmployeesSubcategoriesStarsMapper() {
        String json = IOUtils.readFileFromAssets(MOCKS_FOLDER + "employee_subcategories_stars.json");
        try {
            return new Gson().fromJson(json, new TypeToken<List<EmployeeSubcategoriesStarsMapper>>(){}.getType());
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

}

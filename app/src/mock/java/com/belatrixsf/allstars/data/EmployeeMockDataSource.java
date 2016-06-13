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

import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.networking.retrofit.responses.AuthenticationResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.allstars.utils.IOUtils;
import com.belatrixsf.allstars.utils.exceptions.InvalidPageException;
import com.belatrixsf.allstars.utils.exceptions.NotFoundException;
import com.belatrixsf.allstars.utils.search.SearchingEngine;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by gyosida on 6/13/16.
 */
public class EmployeeMockDataSource {

    private SearchingEngine<Employee> searchingEngine;
    private List<Employee> employees;

    public EmployeeMockDataSource(List<Employee> employees) {
        this(employees, 10);
    }

    public EmployeeMockDataSource(List<Employee> employees, int itemsPerPage) {
        this.employees = employees;
        searchingEngine = new SearchingEngine<>(employees);
        searchingEngine.setItemsPerPage(itemsPerPage);
    }

    public EmployeeMockDataSource(int itemsPerPage) {
        String employeesJSON = IOUtils.readFileFromAssets("mocks/employees.json");
        List<Employee> parsedEmployees = new Gson().fromJson(employeesJSON, new TypeToken<List<Employee>>(){}.getType());
        employees = parsedEmployees;
        searchingEngine = new SearchingEngine<>(parsedEmployees);
        searchingEngine.setItemsPerPage(itemsPerPage);
    }

    public PaginatedResponse<Employee> search(String searchTerm, Integer page) throws InvalidPageException {
        return searchingEngine.search(searchTerm, page == null? 0 : page);
    }

    public AuthenticationResponse authenticate(String username, String password) throws NotFoundException {
        for (Employee employee : employees) {
            if (employee.getUsername() != null && employee.getPassword() != null && employee.getUsername().equals(username) && employee.getPassword().equals(password)) {
                return new AuthenticationResponse(employee.getPk(), "0090920302312", null, true);
            }
        }
        throw new NotFoundException("employee");
    }

    public Employee getEmployee(int employeeId) throws NotFoundException {
        for (Employee employee : employees) {
            if (employee.getPk() != null && employee.getPk() == employeeId) {
                return employee;
            }
        }
        throw new NotFoundException("employee");
    }

}

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
package com.belatrixsf.allstars;

import com.belatrixsf.allstars.data.EmployeeMockDataSource;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.allstars.services.contracts.EmployeeService;
import com.belatrixsf.allstars.services.mock.EmployeeMockService;
import com.belatrixsf.allstars.util.TestConstants;
import com.belatrixsf.allstars.util.TestingAllStarsCallback;
import com.belatrixsf.allstars.utils.ServiceError;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by gyosida on 6/9/16.
 */
public class EmployeeMockServiceTest {

    private static final int STARTING_EMPLOYEES_COUNT = 48;
    private static final long ASYNC_AWAIT_MILLIS = 500;
    private static final String[] SEARCHABLE_EMPLOYEES_NAMES = new String[] {
            "Test0","Test1", "Test2", "Test3", "Test4", "Test5", "Test6",
            "Test7", "Test8", "Test9", "Test10", "Test11", "Test12", "Test13"
    };

    @Test
    public void GetEmployeeSearchList_NullSearchStringAndNullPage_FirstEmployeesPage() throws Exception {
        List<Employee> employees = constructEmployees();
        EmployeeMockDataSource employeeMockDataSource = new EmployeeMockDataSource(employees);
        // inject service with mocked employees
        EmployeeService employeeService = new EmployeeMockService(employeeMockDataSource);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        employeeService.getEmployeeSearchList(null, null, new TestingAllStarsCallback<PaginatedResponse<Employee>>(countDownLatch) {
            @Override
            public void onSuccess(PaginatedResponse<Employee> employeePaginatedResponse) {
                assertNotNull(employeePaginatedResponse);
                assertNotNull(employeePaginatedResponse.getNext());
                assertEquals(employeePaginatedResponse.getCount(), 10);
                super.onSuccess(employeePaginatedResponse);
            }
        });
        assertTrue(countDownLatch.await(ASYNC_AWAIT_MILLIS, TimeUnit.MILLISECONDS));
    }

    @Test
    public void GetEmployeeSearchList_SearchStringAndNullPage_UniqueEmployeesPage() throws Exception {
        List<Employee> employees = constructEmployees();
        // create target employees
        fillInEmployeesWithSearchableText(employees, 3);
        EmployeeMockDataSource employeeMockDataSource = new EmployeeMockDataSource(employees);
        // inject service and do the search
        EmployeeService employeeService = new EmployeeMockService(employeeMockDataSource);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        employeeService.getEmployeeSearchList("Test", null, new TestingAllStarsCallback<PaginatedResponse<Employee>>(countDownLatch) {
            @Override
            public void onSuccess(PaginatedResponse<Employee> employeePaginatedResponse) {
                assertNotNull(employeePaginatedResponse);
                assertNull(employeePaginatedResponse.getNext());
                assertEquals(employeePaginatedResponse.getCount(), 3);
                super.onSuccess(employeePaginatedResponse);
            }
        });
        assertTrue(countDownLatch.await(ASYNC_AWAIT_MILLIS, TimeUnit.MILLISECONDS));
    }

    @Test
    public void GetEmployeeSearchList_SearchStringAndValidPage_EmployeesPage() throws Exception {
        List<Employee> employees = constructEmployees();
        fillInEmployeesWithSearchableText(employees, TestConstants.PAGINATION_ITEMS_PER_PAGE + 3);
        EmployeeMockDataSource employeeMockDataSource = new EmployeeMockDataSource(employees, TestConstants.PAGINATION_ITEMS_PER_PAGE);
        // inject service with 13 employees (2 pages, 1 full of employees and the other with only 3)
        EmployeeService employeeService = new EmployeeMockService(employeeMockDataSource);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        employeeService.getEmployeeSearchList("Test", 1, new TestingAllStarsCallback<PaginatedResponse<Employee>>(countDownLatch) {
            @Override
            public void onSuccess(PaginatedResponse<Employee> employeePaginatedResponse) {
                assertNotNull(employeePaginatedResponse);
                assertNull(employeePaginatedResponse.getNext());
                assertEquals(employeePaginatedResponse.getCount(), 3);
                super.onSuccess(employeePaginatedResponse);
            }
        });
        assertTrue(countDownLatch.await(ASYNC_AWAIT_MILLIS, TimeUnit.MILLISECONDS));
    }

    @Test
    public void GetEmployeesSearchList_NotExistingSearchTextNullPage_EmptyEmployeesList() throws Exception {
        List<Employee> employees = constructEmployees();
        EmployeeMockDataSource employeeMockDataSource = new EmployeeMockDataSource(employees);
        // inject service
        EmployeeService employeeService = new EmployeeMockService(employeeMockDataSource);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        employeeService.getEmployeeSearchList("NotExisting", null, new TestingAllStarsCallback<PaginatedResponse<Employee>>(countDownLatch) {
            @Override
            public void onSuccess(PaginatedResponse<Employee> employeePaginatedResponse) {
                assertNotNull(employeePaginatedResponse);
                assertNull(employeePaginatedResponse.getNext());
                assertEquals(employeePaginatedResponse.getCount(), 0);
                super.onSuccess(employeePaginatedResponse);
            }
        });
        assertTrue(countDownLatch.await(ASYNC_AWAIT_MILLIS, TimeUnit.MILLISECONDS));
    }

    @Test
    public void GetEmployeeSearchList_NullSearchStringInvalidPage_OnFailureCalled() throws Exception {
        List<Employee> employees = constructEmployees();
        EmployeeMockDataSource employeeMockDataSource = new EmployeeMockDataSource(employees);
        // inject service with employees
        EmployeeService employeeService = new EmployeeMockService(employeeMockDataSource);
        // create count down and send the request
        CountDownLatch countDownLatch = new CountDownLatch(1);
        employeeService.getEmployeeSearchList(null, -3, new TestingAllStarsCallback<PaginatedResponse<Employee>>(countDownLatch) {
            @Override
            public void onFailure(ServiceError serviceError) {
                assertNotNull(serviceError);
                assertNotNull(serviceError.getDetail());
                super.onFailure(serviceError);
            }
        });
        assertTrue(countDownLatch.await(ASYNC_AWAIT_MILLIS, TimeUnit.MILLISECONDS));
    }

    private void fillInEmployeesWithSearchableText(List<Employee> employees, int employeesCount) {
        if (employeesCount > SEARCHABLE_EMPLOYEES_NAMES.length) {
            employeesCount = SEARCHABLE_EMPLOYEES_NAMES.length;
        }
        for (int i = 0; i < employeesCount; i++) {
            Employee employee = mock(Employee.class);
            when(employee.searchableText()).thenReturn(SEARCHABLE_EMPLOYEES_NAMES[i]);
            employees.add(employee);
        }
    }

    private List<Employee> constructEmployees() {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < STARTING_EMPLOYEES_COUNT; i++) {
            Employee employee = mock(Employee.class);
            employees.add(employee);
        }
        return employees;
    }

}

package com.belatrixsf.allstars.networking.retrofit.responses;

import com.belatrixsf.allstars.entities.Employee;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by PedroCarrillo on 5/12/16.
 */
public class StarKeywordListResponse extends PaginatedResponse {

    @SerializedName("results")
    List<Employee> employeeList;

    public List<Employee> getEmployees() {
        return employeeList;
    }

}

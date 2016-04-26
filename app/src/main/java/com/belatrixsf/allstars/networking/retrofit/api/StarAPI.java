package com.belatrixsf.allstars.networking.retrofit.api;

import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.networking.retrofit.responses.StarSubCategoryResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by PedroCarrillo on 4/26/16.
 */
public interface StarAPI {

    @GET(ServerPaths.EMPLOYEE_SUBCATEGORY_LIST)
    Call<StarSubCategoryResponse> getEmployeeSubCategoriesStars(@Path(ServerPaths.EMPLOYEE_ID) int employeeId);

}

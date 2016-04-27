package com.belatrixsf.allstars.networking.retrofit.api;

import com.belatrixsf.allstars.networking.retrofit.requests.StarRequest;
import com.belatrixsf.allstars.networking.retrofit.responses.StarResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.StarSubCategoryResponse;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by PedroCarrillo on 4/26/16.
 */
public interface StarAPI {

    @GET(ServerPaths.EMPLOYEE_SUBCATEGORY_LIST)
    Call<StarSubCategoryResponse> getEmployeeSubCategoriesStars(@Path(ServerPaths.EMPLOYEE_ID) int employeeId);

    @POST(ServerPaths.STAR_EMPLOYEE)
    Call<StarResponse> star(@Path(ServerPaths.FROM_EMPLOYEE) int fromEmployeeId, @Path(ServerPaths.TO_EMPLOYEE) int toEmployeeId, @Body StarRequest request);

}

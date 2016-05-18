package com.belatrixsf.allstars.networking.retrofit.api;

import com.belatrixsf.allstars.networking.retrofit.requests.StarRequest;
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> develop
import com.belatrixsf.allstars.networking.retrofit.responses.StarKeywordTopListResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.StarsByKeywordsResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.StarsResponse;
>>>>>>> develop
import com.belatrixsf.allstars.networking.retrofit.responses.StarResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.StarSubCategoryResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.StarsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by PedroCarrillo on 4/26/16.
 */
public interface StarAPI {

    @GET(ServerPaths.EMPLOYEE_SUBCATEGORY_LIST)
    Call<StarSubCategoryResponse> getEmployeeSubCategoriesStars(@Path(ServerPaths.EMPLOYEE_ID) int employeeId);

    @POST(ServerPaths.STAR_EMPLOYEE)
    Call<StarResponse> star(@Path(ServerPaths.FROM_EMPLOYEE) int fromEmployeeId, @Path(ServerPaths.TO_EMPLOYEE) int toEmployeeId, @Body StarRequest request);

    @GET(ServerPaths.STARS_BY_EMPLOYEE_AND_SUBCATEGORY)
    Call<StarsResponse> getStars(@Path(ServerPaths.EMPLOYEE_ID) int employeeId, @Path(ServerPaths.SUBCATEGORY_ID) int subcategoryId, @Query(ServerPaths.QUERY_PAGE) Integer page);

    @GET(ServerPaths.STARS_BY_KEYWORD)
    Call<StarsByKeywordsResponse> getStarsByKeywords(@Query(ServerPaths.QUERY_SEARCH) String search, @Query(ServerPaths.QUERY_PAGE) Integer nextPage);

    @GET(ServerPaths.STARS_KEYWORD_TOP)
    Call<StarKeywordTopListResponse> getStarsKeywordTop(@Path(ServerPaths.KEYWORD_ID) int keywordId, @Query(ServerPaths.QUERY_PAGE) Integer page);

}

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
package com.belatrixsf.connect.networking.retrofit.api;

import com.belatrixsf.connect.networking.retrofit.requests.StarRequest;
import com.belatrixsf.connect.networking.retrofit.responses.StarKeywordTopListResponse;
import com.belatrixsf.connect.networking.retrofit.responses.StarsByKeywordsResponse;
import com.belatrixsf.connect.networking.retrofit.responses.StarsResponse;
import com.belatrixsf.connect.networking.retrofit.responses.StarResponse;
import com.belatrixsf.connect.networking.retrofit.responses.StarSubCategoryResponse;

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

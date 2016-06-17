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
package com.belatrixsf.connect.services.contracts;

import com.belatrixsf.connect.networking.retrofit.requests.StarRequest;
import com.belatrixsf.connect.networking.retrofit.responses.StarKeywordTopListResponse;
import com.belatrixsf.connect.networking.retrofit.responses.StarResponse;
import com.belatrixsf.connect.networking.retrofit.responses.StarSubCategoryResponse;
import com.belatrixsf.connect.networking.retrofit.responses.StarsByKeywordsResponse;
import com.belatrixsf.connect.networking.retrofit.responses.StarsResponse;
import com.belatrixsf.connect.services.ServiceRequest;
import com.belatrixsf.connect.utils.BelatrixConnectCallback;

/**
 * Created by PedroCarrillo on 4/26/16.
 */
public interface StarService extends BelatrixConnectService {


    ServiceRequest getEmployeeSubCategoriesStars(int employeeId, BelatrixConnectCallback<StarSubCategoryResponse> callback);

    ServiceRequest star(int fromEmployeeId, int toEmployeeId, StarRequest starRequest, BelatrixConnectCallback<StarResponse> callback);

    ServiceRequest getStarsByKeywords(String search, Integer next, BelatrixConnectCallback<StarsByKeywordsResponse> callback);

    ServiceRequest getStars(int employeeId, int subcategory, Integer page, BelatrixConnectCallback<StarsResponse> callback);

    ServiceRequest getStarsKeywordTopList(int keywordId, Integer page, BelatrixConnectCallback<StarKeywordTopListResponse> callback);

}

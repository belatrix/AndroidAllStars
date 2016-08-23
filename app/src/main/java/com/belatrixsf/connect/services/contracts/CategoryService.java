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

import com.belatrixsf.connect.entities.Category;
import com.belatrixsf.connect.entities.Keyword;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.services.ServiceRequest;
import com.belatrixsf.connect.utils.BelatrixConnectCallback;

import java.util.List;

/**
 * Created by gyosida on 4/27/16.
 */
public interface CategoryService extends BelatrixConnectService {

    ServiceRequest getSubcategories(int categoryId, BelatrixConnectCallback<List<Category>> callback);

    ServiceRequest getKeywords(BelatrixConnectCallback<List<Keyword>> callback);

    ServiceRequest getKeywordsByEmployee(int employeeId, BelatrixConnectCallback<PaginatedResponse<Keyword>> callback);

    ServiceRequest saveKeywordToEmployee(int employeeId, String keywordName, BelatrixConnectCallback<Keyword> callback);

    ServiceRequest removeEmployeeKeyword(int employeeId, String keywordName, BelatrixConnectCallback<Keyword> callback);

}

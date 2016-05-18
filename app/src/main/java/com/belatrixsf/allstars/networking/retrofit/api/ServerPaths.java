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
package com.belatrixsf.allstars.networking.retrofit.api;

/**
 * Created by gyosida on 4/11/16.
 */
public interface ServerPaths {

    String EMPLOYEE = "employee";
    String CATEGORY = "category";
    String STAR = "star";

    String EMPLOYEE_ID = "employee_id";
    String FROM_EMPLOYEE = "from_employee_id";
    String TO_EMPLOYEE = "to_employee_id";
    String SUBCATEGORY_ID = "subcategory_id";
    String SEARCH_TERM = "search_term";
    String KIND = "kind";
    String QUANTITY = "quantity";
    String CATEGORY_ID = "category_id";

    String QUERY_PAGE = "page";

    String EMPLOYEE_AUTHENTICATE = EMPLOYEE + "/authenticate/";
    String EMPLOYEE_LIST = EMPLOYEE + "/list";
    String EMPLOYEE_SEARCH_TERM = EMPLOYEE + "/search/{" + SEARCH_TERM + "}";
    String EMPLOYEE_BY_ID = EMPLOYEE + "/{" + EMPLOYEE_ID + "}";
    String EMPLOYEE_CATEGORIES = EMPLOYEE + "/{" + EMPLOYEE_ID + "}/category/list";
    String EMPLOYEE_SUBCATEGORY_LIST = STAR + "/{" + EMPLOYEE_ID + "}" + "/subcategory/list";

    String STAR_EMPLOYEE = STAR + "/{" + FROM_EMPLOYEE + "}" + "/give/star/to/{" + TO_EMPLOYEE + "}/" ;
    String STARS_BY_EMPLOYEE_AND_SUBCATEGORY = STAR + "/{" + EMPLOYEE_ID + "}/subcategory" + "/{" + SUBCATEGORY_ID + "}/list";
    String RANKING_LIST = EMPLOYEE + "/list/top/{" + KIND + "}/{" + QUANTITY + "}/";

    String SUBCATEGORIES_BY_CATEGORY_ID = CATEGORY + "/{" + CATEGORY_ID + "}/subcategory/list";

}

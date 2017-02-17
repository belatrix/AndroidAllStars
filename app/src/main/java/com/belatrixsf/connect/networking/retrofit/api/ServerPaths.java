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

/**
 * Created by gyosida on 4/11/16.
 */
public interface ServerPaths {

    String EMPLOYEE = "employee";
    String CATEGORY = "category";
    String STAR = "star";
    String EVENT = "event";
    String NOTIFICATION = "notification";
    String SKILLS = "skills";

    String EMPLOYEE_ID = "employee_id";
    String EMPLOYEE_EMAIL = "employee_email";
    String KEYWORD_ID = "keyword_id";
    String FROM_EMPLOYEE = "from_employee_id";
    String TO_EMPLOYEE = "to_employee_id";
    String SUBCATEGORY_ID = "subcategory_id";
    String SEARCH_TERM = "search";
    String KIND = "kind";
    String QUANTITY = "quantity";
    String CATEGORY_ID = "category_id";
    String EVENT_ID = "event_id";
    String GUEST_ID = "participant_id";

    String QUERY_PAGE = "page";
    String QUERY_SEARCH = "search";

    String EMPLOYEE_AUTHENTICATE = EMPLOYEE + "/authenticate/";
    String EMPLOYEE_LOGOUT = EMPLOYEE + "/logout/";
    String EMPLOYEE_CREATE = EMPLOYEE + "/create/";
    String EMPLOYEE_UPDATE_PASSWORD = EMPLOYEE + "/{" + EMPLOYEE_ID + "}" + "/update/password/";
    String EMPLOYEE_REQUEST_NEW_PASSWORD = EMPLOYEE + "/reset/password/{" + EMPLOYEE_EMAIL + "}/";
    String EMPLOYEE_LIST = EMPLOYEE + "/list/";
    String EMPLOYEE_BY_ID = EMPLOYEE + "/{" + EMPLOYEE_ID + "}";
    String EMPLOYEE_CATEGORIES = EMPLOYEE + "/{" + EMPLOYEE_ID + "}/category/list";
    String EMPLOYEE_SUBCATEGORY_LIST = STAR + "/{" + EMPLOYEE_ID + "}" + "/subcategory/list";
    String EMPLOYEE_UPDATE = EMPLOYEE + "/{" + EMPLOYEE_ID + "}/update/";
    String EMPLOYEE_LOCATION_LIST = EMPLOYEE + "/location/list";
    String EMPLOYEE_NOTIFICATION_LIST = "activity/get/" + NOTIFICATION + "/employee/{" + EMPLOYEE_ID + "}/all/";
    String EMPLOYEE_AVATAR = EMPLOYEE + "/{" + EMPLOYEE_ID + "}/avatar/";
    String EMPLOYEE_REGISTER_DEVICE = EMPLOYEE + "/{" + EMPLOYEE_ID + "}/register/device/";

    String STAR_EMPLOYEE = STAR + "/{" + FROM_EMPLOYEE + "}" + "/give/star/to/{" + TO_EMPLOYEE + "}/" ;
    String STARS_BY_EMPLOYEE_AND_SUBCATEGORY = STAR + "/{" + EMPLOYEE_ID + "}/subcategory" + "/{" + SUBCATEGORY_ID + "}/list";
    String STARS_BY_KEYWORD = STAR + "/keyword/list";
    String STARS_KEYWORD_TOP = STAR + "/keyword/{" + KEYWORD_ID + "}/list/";
    String RANKING_LIST = EMPLOYEE + "/list/top/10/{" + KIND + "}/";
    String CATEGORY_LIST = CATEGORY + "/list/";
    String EMPLOYEE_CATEGORY_LIST = STAR + "/{" + EMPLOYEE_ID + "}/list/group/category";
    String EMPLOYEE_BADGE_LIST = STAR + "/{" + EMPLOYEE_ID + "}/badge/list";
    String EMPLOYEE_RECOMMENDATIONS_BY_CATEGORY_LIST = STAR + "/{" + EMPLOYEE_ID + "}/list/group/category/{" + CATEGORY_ID + "}/";

    String CATEGORY_KEYWORD_LIST = CATEGORY + "/keyword/list";
    String SUBCATEGORIES_BY_CATEGORY_ID = CATEGORY + "/{" + CATEGORY_ID + "}/subcategory/list";

    String EMPLOYEE_SKILLS = EMPLOYEE + "/{" + EMPLOYEE_ID + "}/" + SKILLS + "/list";
    String EMPLOYEE_ADD_SKILL = EMPLOYEE + "/{" + EMPLOYEE_ID + "}/" + SKILLS + "/add/";
    String EMPLOYEE_REMOVE_SKILL = EMPLOYEE + "/{" + EMPLOYEE_ID + "}/" + SKILLS + "/remove/";

    String EVENT_LIST = EVENT + "/list/";
    String EVENT_DETAIL = EVENT + "/{" + EVENT_ID + "}/";
    String EVENT_PARTICIPANT_DETAIL = EVENT_DETAIL + "participant/{" + GUEST_ID + "}/detail/";
    String EVENT_COLLABORATOR_DETAIL = EVENT_DETAIL + "collaborator/{" + EMPLOYEE_ID + "}/detail/";
    String EVENT_REGISTER_COLLABORATOR = EVENT_DETAIL + "register/collaborator/{" + EMPLOYEE_ID + "}/";
    String EVENT_REGISTER_PARTICIPANT = EVENT_DETAIL + "register/participant/{" + GUEST_ID + "}/";
    String EVENT_UNREGISTER_COLLABORATOR = EVENT_DETAIL + "unregister/collaborator/{" + EMPLOYEE_ID + "}/";
    String EVENT_UNREGISTER_PARTICIPANT = EVENT_DETAIL + "unregister/participant/{" + GUEST_ID + "}/";

    String GUEST_AUTHENTICATE = EVENT + "/participant/";
    String GUEST_BY_ID = EVENT + "/participant/{" + GUEST_ID + "}";
    String NOTIFICATION_LIST = NOTIFICATION + "/list/";

    String EMPLOYEE_SITE_INFO = "admin/site/info/";
}

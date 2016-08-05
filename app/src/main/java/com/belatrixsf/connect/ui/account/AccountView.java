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
package com.belatrixsf.connect.ui.account;

import com.belatrixsf.connect.entities.Employee;
import com.belatrixsf.connect.entities.SubCategory;
import com.belatrixsf.connect.ui.common.BelatrixConnectView;

import java.util.List;

/**
 * Created by PedroCarrillo on 4/13/16.
 */
public interface AccountView extends BelatrixConnectView {

    void goSubCategoryDetail(Integer categoryId, Integer employeeId);
    void showCurrentMonthScore(String skypeId);
    void showScore(String score);
    void showLocation(String locationIcon);
    void showSubCategories(List<SubCategory> subCategories);
    void showLevel(String level);
    void showSkypeId(String skypeID);
    void showEmployeeName(String employeName);
    void showEmail(String role);
    void showProfilePicture(String profilePicture);
    void showRecommendMenu(boolean show);
    void showEditProfileButton(boolean show);
    void goToEditProfile(Employee employee);
    void goToGiveStar(Employee employee);
    void showNoDataView(String message);
    void hideNoDataView();
    void goToExpandPhoto(String url);
    void notifyNavigationRefresh();
    void showInformativeDialog(String information);
    void goBackToLogin();

}

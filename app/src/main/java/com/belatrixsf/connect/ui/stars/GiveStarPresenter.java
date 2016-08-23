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
package com.belatrixsf.connect.ui.stars;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.entities.Category;
import com.belatrixsf.connect.entities.Employee;
import com.belatrixsf.connect.entities.Keyword;
import com.belatrixsf.connect.entities.SubCategory;
import com.belatrixsf.connect.managers.EmployeeManager;
import com.belatrixsf.connect.networking.retrofit.requests.StarRequest;
import com.belatrixsf.connect.networking.retrofit.responses.StarResponse;
import com.belatrixsf.connect.services.contracts.StarService;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;

import javax.inject.Inject;

/**
 * Created by pedrocarrillo on 4/25/16.
 */
public class GiveStarPresenter extends BelatrixConnectPresenter<GiveStarView> {

    private StarService starService;
    private EmployeeManager employeeManager;
    private Employee selectedEmployee;
    private SubCategory selectedSubCategory;
    private Keyword selectedKeyword;
    private String selectedComment;
    private boolean initWithUser = false;

    @Inject
    public GiveStarPresenter(GiveStarView view, EmployeeManager employeeManager, StarService starService) {
        super(view);
        this.starService = starService;
        this.employeeManager = employeeManager;
    }

    public void initWithUser(Employee employee) {
        loadSelectedUser(employee);
        initWithUser = true;
        view.blockWithUserSelected();
    }

    public void userSelectionClicked() {
        if (!initWithUser) {
            view.goSearchUser();
        }
    }

    public void categorySelectionClicked() {
        view.goSelectSubcategory();
    }

    public void commentSelectionClicked() {
        view.goWriteComment(selectedComment);
    }

    public Employee getSelectedEmployee() {
        return selectedEmployee;
    }

    public Category getSelectedSubCategory() {
        return selectedSubCategory;
    }

    public String getSelectedComment() {
        return selectedComment;
    }

    public Keyword getSelectedKeyword() {
        return selectedKeyword;
    }

    public void keywordSelectionClicked() {
        view.goSelectKeyword();
    }

    public void loadSelectedUser(Employee employee) {
        if (employee != null) {
            if (employee.getFullName() != null && !employee.getFullName().isEmpty()) {
                view.showUserFullName(employee.getFullName());
            }
            if (employee.getLevel() != null && !employee.getLevel().toString().isEmpty()) {
                view.showUserLevel(String.valueOf(employee.getLevel()));
            }
            view.showUserProfileImage(employee.getAvatar());
            this.selectedEmployee = employee;
            view.showUser();
            checkRecommendationEnabled();
        }
    }

    public void loadSelectedComment(String comment) {
        if (comment != null) {
            this.selectedComment = comment;
            if (selectedComment.isEmpty()) {
                view.showCommentHint();
            } else {
                view.showComment(comment);
            }
            checkRecommendationEnabled();
        }
    }

    public void loadSelectedSubCategory(SubCategory subCategory) {
        if (subCategory != null && subCategory.getName() != null && !subCategory.getName().isEmpty()) {
            selectedSubCategory = subCategory;
            view.showCategory(subCategory.getName());
            checkRecommendationEnabled();
        }
    }

    public void loadSelectedKeyword(Keyword keyword) {
        if (keyword != null) {
            selectedKeyword = keyword;
            view.showKeywordSelected(keyword.getName());
            checkRecommendationEnabled();
        }
    }

    public void load(Employee savedEmployee, String savedComment, SubCategory savedCategory, Keyword savedKeyword){
        loadSelectedUser(savedEmployee);
        loadSelectedComment(savedComment);
        loadSelectedSubCategory(savedCategory);
        loadSelectedKeyword(savedKeyword);
    }

    public void makeRecommendation() {
        view.showProgressDialog(getString(R.string.making_recommendation));
        employeeManager.getLoggedInEmployee(
                new PresenterCallback<Employee>() {
                    @Override
                    public void onSuccess(Employee fromEmployee) {
                        StarRequest starRequest = new StarRequest(selectedSubCategory.getParentCategory().getId(), selectedSubCategory.getId(), selectedComment, selectedKeyword.getId());
                        starService.star(
                                fromEmployee.getPk(),
                                selectedEmployee.getPk(),
                                starRequest,
                                new PresenterCallback<StarResponse>() {
                                    @Override
                                    public void onSuccess(StarResponse starResponse) {
                                        view.dismissProgressDialog();
                                        view.finishRecommendation();
                                    }
                                });
                    }
                });
    }

    public void checkRecommendationEnabled() {
        view.showDoneMenu(validateFormComplete());
    }

    private boolean validateFormComplete() {
        return selectedEmployee != null && selectedSubCategory != null && selectedKeyword != null && validateCategoryCommentRequired();
    }

    private boolean validateCategoryCommentRequired() {
        if (selectedSubCategory != null) {
            boolean commentRequired = selectedSubCategory.getParentCategory().isCommentRequired();
            if ((selectedComment == null || selectedComment.isEmpty()) && commentRequired) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void cancelRequests() {
        starService.cancelAll();
    }

}
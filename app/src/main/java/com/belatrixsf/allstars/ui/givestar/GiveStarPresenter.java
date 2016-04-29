package com.belatrixsf.allstars.ui.givestar;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Category;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.entities.SubCategory;
import com.belatrixsf.allstars.managers.EmployeeManager;
import com.belatrixsf.allstars.networking.retrofit.requests.StarRequest;
import com.belatrixsf.allstars.networking.retrofit.responses.StarResponse;
import com.belatrixsf.allstars.services.StarService;
import com.belatrixsf.allstars.ui.common.AllStarsPresenter;
import com.belatrixsf.allstars.utils.AllStarsCallback;
import com.belatrixsf.allstars.utils.ServiceError;

import javax.inject.Inject;

/**
 * Created by pedrocarrillo on 4/25/16.
 */
public class GiveStarPresenter extends AllStarsPresenter<GiveStarView> {

    private StarService starService;
    private EmployeeManager employeeManager;
    private Employee toEmployee;
    private Category selectedSubCategory;
    private String comment;
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
        view.goWriteComment(comment);
    }

    public void loadSelectedUser(Employee employee) {
        checkRecommendationEnabled();
        if (employee.getFullName() != null && !employee.getFullName().isEmpty()) {
            view.showUserFullName(employee.getFullName());
        }
        if (employee.getLevel() != null) {
            view.showUserLevel(String.valueOf(employee.getLevel()));
        }
        if (employee.getAvatar() != null) {
            view.showUserProfileImage(employee.getAvatar());
        }
        this.toEmployee = employee;
        view.showUser();
    }

    public void loadSelectedComment(String comment) {
        checkRecommendationEnabled();
        if (!comment.isEmpty()) {
            this.comment = comment;
            view.showComment(comment);
        }
    }

    public void loadSelectedSubCategory(Category subCategory) {
        selectedSubCategory = subCategory;
        view.showCategory(subCategory.getName());
        checkRecommendationEnabled();
    }

    public void makeRecommendation() {
        view.showProgressDialog(getString(R.string.making_recommendation));
        employeeManager.getLoggedInEmployee(new AllStarsCallback<Employee>() {
            @Override
            public void onSuccess(Employee fromEmployee) {
                StarRequest starRequest = new StarRequest(selectedSubCategory.getParentId(), selectedSubCategory.getId(), comment);
                starService.star(fromEmployee.getPk(), toEmployee.getPk(), starRequest, new AllStarsCallback<StarResponse>() {
                    @Override
                    public void onSuccess(StarResponse starResponse) {
                        view.dismissProgressDialog();
                        view.finishRecommendation();
                    }

                    @Override
                    public void onFailure(ServiceError serviceError) {
                        view.showError(serviceError.getErrorMessage());
                    }
                });
            }

            @Override
            public void onFailure(ServiceError serviceError) {
                view.showError(serviceError.getErrorMessage());
            }
        });
    }

    public void checkRecommendationEnabled() {
        view.showDoneMenu(validateFormComplete());
    }

    private boolean validateFormComplete() {
        return toEmployee != null && selectedSubCategory != null;
    }

}

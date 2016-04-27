package com.belatrixsf.allstars.ui.givestar;

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
    private SubCategory selectedSubCategory;
    private String comment;

    @Inject
    public GiveStarPresenter(GiveStarView view, EmployeeManager employeeManager, StarService starService) {
        super(view);
        this.starService = starService;
        this.employeeManager = employeeManager;
    }

    public void userSelectionClicked() {
        view.goSearchUser();
    }

    public void categorySelectionClicked() {
        // TODO: REMOVE THIS
        selectedSubCategory = new SubCategory();
        selectedSubCategory.setFakeData(1);
        loadSelectedSubCategory(selectedSubCategory);
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

    public void loadSelectedSubCategory(SubCategory subCategory) {
        checkRecommendationEnabled();
    }

    public void makeRecommendation() {
        employeeManager.getLoggedInEmployee(new AllStarsCallback<Employee>() {
            @Override
            public void onSuccess(Employee fromEmployee) {
                //TODO: change to categoryId
                StarRequest starRequest = new StarRequest(selectedSubCategory.getPk(), selectedSubCategory.getPk(), comment);
                starService.star(fromEmployee.getPk(), toEmployee.getPk(), starRequest, new AllStarsCallback<StarResponse>() {
                    @Override
                    public void onSuccess(StarResponse starResponse) {
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

    public boolean checkRecommendationEnabled() {
        view.enableContinueButton();
        return validateFormComplete();
    }

    private boolean validateFormComplete() {
        return toEmployee != null && selectedSubCategory != null && comment != null && !comment.isEmpty();
    }

}

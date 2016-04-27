package com.belatrixsf.allstars.ui.recommendation;

import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.entities.SubCategory;
import com.belatrixsf.allstars.services.StarService;
import com.belatrixsf.allstars.ui.common.AllStarsPresenter;

import javax.inject.Inject;

/**
 * Created by pedrocarrillo on 4/25/16.
 */
public class RecommendationPresenter extends AllStarsPresenter<RecommendationView> {

    private StarService starService;
    private Employee recipientEmployee;
    private SubCategory subCategory;
    private String comment;

    @Inject
    public RecommendationPresenter(RecommendationView view, StarService starService) {
        super(view);
        this.starService = starService;
    }

    public void userSelectionClicked() {
        view.goSearchUser();
    }

    public void categorySelectionClicked() {
        view.goSelectSubcategory();
    }

    public void commentSelectionClicked() {
        view.goWriteComment();
    }

    public void loadSelectedUser(Employee employee) {
        checkRecommendationEnabled();
        if (employee.getFullName() != null && !employee.getFullName().isEmpty()) {
            view.showUserFullName(employee.getFullName());
        }
        if (employee.getLevel() != null) {
            view.showUserLevel(String.valueOf(employee.getLevel()));
        }
        view.showUser();
    }

    public void loadSelectedComment(String comment) {
        checkRecommendationEnabled();

    }

    public void loadSelectedSubCategory(SubCategory subCategory) {
        checkRecommendationEnabled();

    }

    public void makeRecommendation() {

    }

    private void checkRecommendationEnabled() {
        if (validateFormComplete()) {
            view.enableContinueButton();
        }
    }

    private boolean validateFormComplete() {
        return recipientEmployee != null && subCategory != null && comment != null && !comment.isEmpty();
//        return true;
    }

}

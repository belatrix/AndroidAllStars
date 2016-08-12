package com.belatrixsf.connect.ui.skills;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.entities.Keyword;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.services.contracts.CategoryService;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by echuquilin on 4/08/16.
 */
public class SkillsListPresenter extends BelatrixConnectPresenter<SkillsListView> {

    private CategoryService skillService;
    private List<Keyword> skills = new ArrayList<Keyword>();
    private PaginatedResponse skillsPaging = new PaginatedResponse();
    private int employeeId;

    @Inject
    public SkillsListPresenter(SkillsListView view, CategoryService categoryService) {
        super(view);
        this.skillService = categoryService;
    }

    public void getSkills(int employeeId, boolean isRefreshing) {
        this.employeeId = employeeId;
        view.resetList();
        if (skills.isEmpty()) {
            getSkillsInternal(isRefreshing);
        } else {
            view.addSkills(skills);
        }
    }

    public void callNextPage() {
        if (skillsPaging.getNext() != null) {
            getSkillsInternal(false);
        }
    }

    private void getSkillsInternal(final boolean isRefreshing) {
        if (!isRefreshing) {
            view.showProgressIndicator();
        }
        view.hideNoDataView();
        skillService.getKeywordsByEmployee(
                employeeId,
                new PresenterCallback<PaginatedResponse<Keyword>>() {
                    @Override
                    public void onSuccess(PaginatedResponse<Keyword> employeeKeywordsResponse) {
                        skillsPaging.setCount(employeeKeywordsResponse.getCount());
                        skillsPaging.setNext(employeeKeywordsResponse.getNext());
                        skills.addAll(employeeKeywordsResponse.getResults());
                        view.addSkills(employeeKeywordsResponse.getResults());
                        if (!isRefreshing) {
                            view.hideProgressIndicator();
                        } else {
                            view.hideRefreshData();
                        }
                    }
                });
    }

    public void refreshSkills(boolean isRefreshing) {
        reset();
        getSkillsInternal(isRefreshing);
    }

    private void reset() {
        skills.clear();
        view.resetList();
        skillsPaging.reset();
    }

    public void onSkillSelected(int position) {
        if (position >= 0 && position < skills.size()) {
            Keyword keyword = skills.get(position);
            String message = getString(R.string.dialog_confirmation_remove_skill_first)
                    + " " + keyword.getName() + " " + getString(R.string.dialog_confirmation_remove_skill_sec);
            view.showDeleteConfirmationDialog(message, keyword);
        }
    }

    public void confirmDelete(final String skillName){
        view.showProgressIndicator();
        view.hideNoDataView();
        skillService.removeEmployeeKeyword(
                employeeId,
                skillName,
                new PresenterCallback<Keyword>() {
                    @Override
                    public void onSuccess(Keyword keyword) {
                        view.hideProgressIndicator();
                        view.showDeletedInformation(skillName);
                        refreshSkills(false);
                    }
                });
    }

    @Override
    public void cancelRequests() {
        skillService.cancelAll();
    }
}

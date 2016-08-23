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
    private List<Keyword> skillsList = new ArrayList<Keyword>();
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
        if (skillsList.isEmpty()) {
            getSkillsInternal(isRefreshing);
        } else {
            view.addSkills(skillsList);
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
                        skillsList.addAll(employeeKeywordsResponse.getResults());
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
        skillsList.clear();
        view.resetList();
        skillsPaging.reset();
    }

    public void onSkillSelected(int position) {
        if (position >= 0 && position < skillsList.size()) {
            Keyword keyword = skillsList.get(position);
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

    public PaginatedResponse getSkillsPaging() {
        return skillsPaging;
    }

    public List<Keyword> getSkillsSync() {
        return skillsList;
    }

    public void load(List<Keyword> skills, PaginatedResponse keywordsPaging) {
        if (skills != null) {
            this.skillsList.addAll(skills);
        }
        this.skillsPaging = keywordsPaging;
    }

}

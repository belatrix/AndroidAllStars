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
package com.belatrixsf.connect.ui.skills.Add;

import com.belatrixsf.connect.entities.Keyword;
import com.belatrixsf.connect.managers.PreferencesManager;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.services.contracts.CategoryService;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;
import com.belatrixsf.connect.utils.BelatrixConnectCallback;
import com.belatrixsf.connect.utils.ServiceError;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by echuquilin on 10/08/16.
 */
public class AddSkillPresenter extends BelatrixConnectPresenter<AddSkillView> {

    private CategoryService categoryService;
    private List<Keyword> keywordsList = new ArrayList<Keyword>();
    private List<Keyword> skillsList = new ArrayList<Keyword>();
    private PaginatedResponse keywordsPaging = new PaginatedResponse();
    private String searchText;
    private boolean searching = false;

    @Inject
    public AddSkillPresenter(AddSkillView addSkillView, CategoryService categoryService) {
        super(addSkillView);
        this.categoryService = categoryService;
    }

    public void onKeywordSelected(int position) {
        if (position >= 0 && position < keywordsList.size()) {
            Keyword keyword = keywordsList.get(position);
            addKeywordToEmployee(keyword.getName());
        }
    }

    public void searchKeywords() {
        searching = true;
        view.showSearchActionMode();
    }

    public void stopSearchingKeywords() {
        searchText = null;
        searching = false;
        reset();
        getKeywordsInternal(false);
    }

    public void callNextPage() {
        if (keywordsPaging.getNext() != null) {
            getKeywordsInternal(false);
        }
    }

    public void getKeywords(boolean isRefreshing) {
        view.resetList();
        getKeywordsInternal(isRefreshing);
    }

    public void getKeywords(String searchText, boolean isRefreshing) {
        this.searchText = searchText;
        view.resetList();
        getKeywordsInternal(isRefreshing);
    }

    public void refreshKeywords(boolean isRefreshing) {
        reset();
        getKeywordsInternal(isRefreshing);
    }

    public void updateSkillsList(){
        categoryService.getKeywordsByEmployee(
                PreferencesManager.get().getEmployeeId(),
                new PresenterCallback<PaginatedResponse<Keyword>>() {
                    @Override
                    public void onSuccess(PaginatedResponse<Keyword> employeeKeywordsResponse) {
                        skillsList.clear();
                        skillsList.addAll(employeeKeywordsResponse.getResults());
                    }
                });
    }

    private void getKeywordsInternal(final boolean isRefreshing) {
        view.hideNoDataView();
        if(keywordsList.isEmpty()) {
            if (!isRefreshing) {
                view.showProgressIndicator();
            }
            categoryService.getKeywords(new PresenterCallback<List<Keyword>>() {
                @Override
                public void onSuccess(List<Keyword> keywords) {
                    keywordsList.addAll(keywords);
                    if (!isRefreshing) {
                        view.hideProgressIndicator();
                    } else {
                        view.hideRefreshData();
                    }
                    addViewKeywords();
                }
            });
        } else {
            addViewKeywords();
        }
    }

    private void addViewKeywords() {
        if (searching) {
            List<Keyword> filterList = getKeywordsBySearch(searchText);
            if (filterList.isEmpty()) {
                view.showAddNewKeywordButton();
            } else {
                view.addKeywords(filterList);
                view.hideAddNewKeywordButton();
            }
            view.hideNoDataView();
        } else {
            if(!keywordsList.isEmpty()) {
                view.addKeywords(keywordsList);
                view.hideNoDataView();
            }
            else{
                view.showNoDataView();
            }
            view.hideAddNewKeywordButton();
        }
    }

    private boolean exists(String keywordName, List<Keyword> list){
        for(Keyword k:list){
            if(k.getName().equalsIgnoreCase(keywordName)) {
                return true;
            }
        }
        return false;
    }

    private List<Keyword> getKeywordsBySearch(String text) {
        List<Keyword> newList = new ArrayList<Keyword>();
        if (!keywordsList.isEmpty()) {
            String textUpper = text.toUpperCase();
            for (Keyword keyword : keywordsList) {
                if (keyword.getName().contains(textUpper) && !exists(keyword.getName(),newList)) {
                    newList.add(keyword);
                }
            }
        }
        return newList;
    }

    public void addKeywordToEmployee(final String skillName) {
        view.showProgressIndicator();
        if(!exists(skillName, skillsList)) {
            categoryService.saveKeywordToEmployee(
                    PreferencesManager.get().getEmployeeId(),
                    skillName,
                    new BelatrixConnectCallback<Keyword>() {
                        @Override
                        public void onSuccess(Keyword keyword) {
                            view.showAddedConfirmation(skillName);
                            updateSkillsList();
                            view.hideProgressIndicator();
                        }

                        @Override
                        public void onFailure(ServiceError serviceError) {
                            //nothing
                        }
                    });
        } else {
            view.showAlreadyExistsConfirmation(skillName);
            view.hideProgressIndicator();
        }
    }

    @Override
    public void cancelRequests() {
        categoryService.cancelAll();
    }

    private void reset() {
        keywordsList.clear();
        view.resetList();
        keywordsPaging.reset();
    }

    public List<Keyword> getKeywordsSync() {
        return keywordsList;
    }

    public String getSearchText() {
        return searchText;
    }

    public PaginatedResponse getKeywordsPaging() {
        return keywordsPaging;
    }

    public boolean isSearching() {
        return searching;
    }

    public void loadPresenterState(List<Keyword> keywords, PaginatedResponse keywordsPaging, String searchText, boolean searching) {
        if (keywords != null) {
            this.keywordsList.addAll(keywords);
        }
        this.keywordsPaging = keywordsPaging;
        this.searchText = searchText;
        this.searching = searching;
        if (searching) {
            searchKeywords();
        }
    }

}

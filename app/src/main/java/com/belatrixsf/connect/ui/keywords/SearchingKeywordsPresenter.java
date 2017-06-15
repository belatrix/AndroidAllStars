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
package com.belatrixsf.connect.ui.keywords;

import com.belatrixsf.connect.entities.Keyword;
import com.belatrixsf.connect.services.contracts.CategoryService;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;
import com.belatrixsf.connect.utils.BelatrixConnectCallback;
import com.belatrixsf.connect.utils.ServiceError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by gyosida on 5/9/16.
 */
public class SearchingKeywordsPresenter extends BelatrixConnectPresenter<SearchingKeywordsView> {

    private CategoryService categoryService;
    private List<Keyword> keywordsList = new ArrayList<>();
    private String searchText;
    private boolean searching;

    public boolean isSearching() {
        return searching;
    }

    @Inject
    public SearchingKeywordsPresenter(SearchingKeywordsView searchingKeywordsView, CategoryService categoryService) {
        super(searchingKeywordsView);
        this.categoryService = categoryService;
    }

    public void onKeywordSelected(Keyword keyword) {
            view.showKeywordDetail(keyword);
    }

    public void addNewKeyword(final String keywordName){
        view.showProgressIndicator();
        if(!exists(keywordName, keywordsList)) {
            categoryService.saveNewKeyword(
                    keywordName,
                    new BelatrixConnectCallback<Keyword>() {
                        @Override
                        public void onSuccess(Keyword keyword) {
                            keywordsList.add(keyword);
                            orderKeywordList();
                            view.showKeywords(keywordsList);
                            view.showAddedConfirmation(keyword);
                            view.hideProgressIndicator();
                        }

                        @Override
                        public void onFailure(ServiceError serviceError) {
                            view.showErrorConfirmation(keywordName);
                            view.hideProgressIndicator();
                        }
                    });
        } else {
            view.showAlreadyExistsConfirmation(keywordName);
            view.hideProgressIndicator();
        }
    }

    private void orderKeywordList(){
        Collections.sort(keywordsList, new Comparator<Keyword>() {
            @Override
            public int compare(Keyword keyword2, Keyword keyword1) {
                return  keyword2.getName().toLowerCase().compareTo(keyword1.getName().toLowerCase());
            }
        });
    }

    private boolean exists(String keywordName, List<Keyword> list){
        for(Keyword k:list){
            if(k.getName().equalsIgnoreCase(keywordName)) {
                return true;
            }
        }
        return false;
    }

    public void searchKeywords() {
        view.showSearchActionMode();
    }

    public void getKeywords() {
        view.showProgressIndicator();
        view.hideNoDataView();
        categoryService.getKeywords(new PresenterCallback<List<Keyword>>() {
            @Override
            public void onSuccess(List<Keyword> keywords) {
                keywordsList.clear();
                keywordsList.addAll(keywords);
                if(keywordsList.isEmpty()) {
                    view.showNoDataView();
                } else {
                    view.showKeywords(keywords);
                }
                view.hideProgressIndicator();
            }
        });
    }

    @Override
    public void cancelRequests() {
        categoryService.cancelAll();
    }

    // saving state stuff
    public void loadPresenterState(List<Keyword> keywords, String searchText, boolean searching) {
        if (keywords != null) {
            this.keywordsList.addAll(keywords);
        }
        this.searchText = searchText;
        if (searching) {
            searchKeywords();
        }
    }

    public String getSearchText() {
        return searchText;
    }

    public List<Keyword> getKeywordsSync() {
        return keywordsList;
    }

    public void stopSearchingKeywords() {
        view.showKeywords(keywordsList);
    }
}

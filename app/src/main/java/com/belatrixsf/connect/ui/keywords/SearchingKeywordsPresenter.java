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
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.services.ServiceRequest;
import com.belatrixsf.connect.services.contracts.StarService;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by gyosida on 5/9/16.
 */
public class SearchingKeywordsPresenter extends BelatrixConnectPresenter<SearchingKeywordsView> {

    private StarService starService;
    private List<Keyword> keywords = new ArrayList<>();
    private PaginatedResponse keywordsPaging = new PaginatedResponse();
    private ServiceRequest searchingServiceRequest;
    private String searchText;
    private boolean searching = false;

    @Inject
    public SearchingKeywordsPresenter(SearchingKeywordsView searchingKeywordsView, StarService starService) {
        super(searchingKeywordsView);
        this.starService = starService;
    }

    public void onKeywordSelected(int position) {
        if (position >= 0 && position < keywords.size()) {
            Keyword keyword = keywords.get(position);
            view.showKeywordDetail(keyword);
        }
    }

    public void searchKeywords() {
        view.showSearchActionMode();
        searching = true;
    }

    public void stopSearchingKeywords() {
        searchText = null;
        searching = false;
        reset();
        getKeywordsInternal();
    }

    public void callNextPage() {
        if (keywordsPaging.getNext() != null) {
            getKeywordsInternal();
        }
    }

    public void getKeywords() {
        view.resetList();
        if (keywords.isEmpty()) {
            getKeywordsInternal();
        } else {
            view.addKeywords(keywords);
        }
    }

    public void getKeywords(String searchText) {
        if (searchingServiceRequest != null) {
            searchingServiceRequest.cancel();
        }
        this.searchText = searchText;
        refreshKeywords();
    }

    public void refreshKeywords() {
        reset();
        getKeywordsInternal();
    }

    private void getKeywordsInternal() {
        view.showProgressIndicator();
        view.hideNoDataView();
        searchingServiceRequest = starService.getStarsByKeywords(
                searchText,
                keywordsPaging.getNextPage(),
                new PresenterCallback<PaginatedResponse<Keyword>>() {
                    @Override
                    public void onSuccess(PaginatedResponse<Keyword> starsByKeywordsResponse) {
                        keywordsPaging.setCount(starsByKeywordsResponse.getCount());
                        keywordsPaging.setNext(starsByKeywordsResponse.getNext());
                        if(!starsByKeywordsResponse.getResults().isEmpty()) {
                            keywords.addAll(starsByKeywordsResponse.getResults());
                            view.addKeywords(starsByKeywordsResponse.getResults());
                        } else {
                            view.showNoDataView();
                        }
                        view.hideProgressIndicator();
                    }
                });
    }

    @Override
    public void cancelRequests() {
        starService.cancelAll();
    }

    private void reset() {
        keywords.clear();
        view.resetList();
        keywordsPaging.reset();
    }

    // saving state stuff

    public void load(List<Keyword> keywords, PaginatedResponse keywordsPaging, String searchText, boolean searching) {
        if (keywords != null) {
            this.keywords.addAll(keywords);
        }
        this.keywordsPaging = keywordsPaging;
        this.searchText = searchText;
        this.searching = searching;
        if (searching) {
            searchKeywords();
        }
    }

    public String getSearchText() {
        return searchText;
    }

    public PaginatedResponse getKeywordsPaging() {
        return keywordsPaging;
    }

    public List<Keyword> getKeywordsSync() {
        return keywords;
    }

    public boolean isSearching() {
        return searching;
    }

}

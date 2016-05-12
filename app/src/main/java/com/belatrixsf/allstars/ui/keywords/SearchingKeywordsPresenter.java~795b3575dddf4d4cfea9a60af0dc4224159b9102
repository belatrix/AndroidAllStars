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
package com.belatrixsf.allstars.ui.keywords;

import com.belatrixsf.allstars.entities.Keyword;
import com.belatrixsf.allstars.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.StarsByKeywordsResponse;
import com.belatrixsf.allstars.services.StarService;
import com.belatrixsf.allstars.ui.common.AllStarsPresenter;
import com.belatrixsf.allstars.utils.AllStarsCallback;
import com.belatrixsf.allstars.utils.ServiceError;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by gyosida on 5/9/16.
 */
public class SearchingKeywordsPresenter extends AllStarsPresenter<SearchingKeywordsView> {

    private StarService starService;
    private List<Keyword> keywords = new ArrayList<>();
    private List<Keyword> filteredKeywords = new ArrayList<>();
    private PaginatedResponse keywordsPaging = new PaginatedResponse();
    private PaginatedResponse filteredKeywordsPaging = new PaginatedResponse();

    @Inject
    public SearchingKeywordsPresenter(SearchingKeywordsView searchingKeywordsView, StarService starService) {
        super(searchingKeywordsView);
        this.starService = starService;
    }

    public void getKeywords() {
        getKeywords(keywords, null, keywordsPaging);
    }

    public void getKeywords(String searchText) {
        getKeywords(filteredKeywords, searchText, filteredKeywordsPaging);
    }

    public void onKeywordSelected(int position) {
        // if it is empty means is not from the searching list
        matchAndDispatchKeyword(filteredKeywords.isEmpty()? keywords : filteredKeywords, position);
    }

    private void matchAndDispatchKeyword(List<Keyword> keywords, int position) {
        if (position >= 0 && position < keywords.size()) {
            Keyword keyword = keywords.get(position);
            view.showKeywordDetail(keyword);
        }
    }

    public void searchKeywords() {
        view.showSearchActionMode();
    }

    public void stopSearchingKeywords() {
        filteredKeywords.clear();
        filteredKeywordsPaging.reset();
        view.showSearchActionMode();
    }

    private void getKeywords(final List<Keyword> keywords, String searchText, final PaginatedResponse paginatedResponse) {
        view.showProgressIndicator();
        starService.getStarsByKeywords(searchText, 1, new AllStarsCallback<StarsByKeywordsResponse>() {
            @Override
            public void onSuccess(StarsByKeywordsResponse starsByKeywordsResponse) {
                paginatedResponse.setCount(starsByKeywordsResponse.getCount());
                paginatedResponse.setNext(starsByKeywordsResponse.getNext());
                paginatedResponse.setPrevious(starsByKeywordsResponse.getPrevious());
                keywords.addAll(starsByKeywordsResponse.getKeywords());
                view.addKeywords(starsByKeywordsResponse.getKeywords());
                view.hideProgressIndicator();
            }

            @Override
            public void onFailure(ServiceError serviceError) {
                showError(serviceError.getErrorMessage());
            }
        });
    }

}

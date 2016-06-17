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
package com.belatrixsf.connect.ui.stars.keyword;

import com.belatrixsf.connect.entities.Keyword;
import com.belatrixsf.connect.services.contracts.CategoryService;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by gyosida on 5/11/16.
 */
public class KeywordsListPresenter extends BelatrixConnectPresenter<KeywordsListView> {

    private List<Keyword> keywords = new ArrayList<>();
    private CategoryService categoryService;

    @Inject
    public KeywordsListPresenter(KeywordsListView keywordsListView, CategoryService categoryService) {
        super(keywordsListView);
        this.categoryService = categoryService;
    }

    public void getKeywords() {
        view.resetList();
        if (keywords.isEmpty()) {
            getKeywordsInternal();
        } else {
            view.showKeywords(keywords);
        }
    }

    public void refreshKeywords() {
        reset();
        getKeywordsInternal();
    }

    public void getKeywordsInternal() {
        if (keywords.isEmpty()) {
            view.showProgressIndicator();
            categoryService.getKeywords(new PresenterCallback<List<Keyword>>() {
                @Override
                public void onSuccess(List<Keyword> keywords) {
                    KeywordsListPresenter.this.keywords.addAll(keywords);
                    view.showKeywords(keywords);
                    view.hideProgressIndicator();
                }
            });
        } else {
            view.showKeywords(keywords);
        }
    }

    private void reset() {
        keywords.clear();
        view.resetList();
    }

    public void onKeywordSelected(int position) {
        if (position >= 0 && position < keywords.size()) {
            Keyword keyword = keywords.get(position);
            view.deliverKeywordAsResult(keyword);
        }
    }

    public void load(List<Keyword> keywords) {
        if (keywords != null) {
            this.keywords.addAll(keywords);
        }
    }

    public List<Keyword> getKeywordsSync() {
        return keywords;
    }

    @Override
    public void cancelRequests() {
        categoryService.cancelAll();
    }
}
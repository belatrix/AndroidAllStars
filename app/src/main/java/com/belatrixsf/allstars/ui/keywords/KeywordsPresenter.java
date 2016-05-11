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
import com.belatrixsf.allstars.ui.common.AllStarsPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gyosida on 5/9/16.
 */
public abstract class KeywordsPresenter extends AllStarsPresenter<KeywordsListView> {

    protected List<Keyword> keywords = new ArrayList<>();
    protected KeywordsMode keywordsMode;


    protected KeywordsPresenter(KeywordsListView keywordsListView, KeywordsMode keywordsMode) {
        super(keywordsListView);
        this.keywordsMode = keywordsMode;
    }

    public void onKeywordSelected(int position) {
        matchAndDispatchKeyword(keywords, position);
    }

    protected void matchAndDispatchKeyword(List<Keyword> keywords, int position) {
        if (position >= 0 && position < keywords.size()) {
            Keyword keyword = keywords.get(position);
            if (keywordsMode != null) {
                switch (keywordsMode) {
                    case SEARCH:
                        view.showKeywordDetail(keyword);
                        break;
                    default:
                        view.deliverKeywordAsResult(keyword);
                }
            } else {
                throw new RuntimeException("Need to pass a non-null keyword");
            }
        }
    }

    public abstract void getKeywords();

}

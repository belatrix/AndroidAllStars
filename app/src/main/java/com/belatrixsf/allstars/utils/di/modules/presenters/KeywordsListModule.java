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
package com.belatrixsf.allstars.utils.di.modules.presenters;

import com.belatrixsf.allstars.services.CategoryService;
import com.belatrixsf.allstars.services.StarService;
import com.belatrixsf.allstars.ui.keywords.KeywordsListPresenter;
import com.belatrixsf.allstars.ui.keywords.KeywordsPresenter;
import com.belatrixsf.allstars.ui.keywords.KeywordsListView;
import com.belatrixsf.allstars.ui.keywords.KeywordsMode;
import com.belatrixsf.allstars.ui.keywords.SearchingKeywordsPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gyosida on 5/10/16.
 */
@Module
public class KeywordsListModule {

    private KeywordsListView keywordsListView;
    private KeywordsMode mode;

    public KeywordsListModule(KeywordsListView keywordsListView, KeywordsMode mode) {
        this.keywordsListView = keywordsListView;
        this.mode = mode;
    }

    @Provides
    public KeywordsListView provideView() {
        return keywordsListView;
    }

    @Provides
    public KeywordsPresenter providePresenter(StarService starService, CategoryService categoryService) {
        switch (mode) {
            case SEARCH:
                return new SearchingKeywordsPresenter(keywordsListView, starService);
            default:
                return new KeywordsListPresenter(keywordsListView, categoryService);
        }
    }

}

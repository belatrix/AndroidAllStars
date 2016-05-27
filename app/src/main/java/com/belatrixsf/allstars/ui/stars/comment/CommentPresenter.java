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
package com.belatrixsf.allstars.ui.stars.comment;

import com.belatrixsf.allstars.ui.common.AllStarsPresenter;

import javax.inject.Inject;

/**
 * Created by PedroCarrillo on 4/27/16.
 */
public class CommentPresenter extends AllStarsPresenter<CommentView> {

    @Inject
    public CommentPresenter(CommentView view) {
        super(view);
    }

    public void validateComment(String comment) {
//        if (comment.isEmpty()) {
//            view.showError(getString(R.string.comment_no_empty_error));
//        } else {
        view.selectComment(comment);
//        }
    }

    public void init(String comment) {
        if (comment != null) {
            view.showComment(comment);
        }
    }


    @Override
    public void cancelRequests() {
        // TODO Implement cancel request for the required services.
    }
}

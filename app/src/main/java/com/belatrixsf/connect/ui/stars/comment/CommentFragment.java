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
package com.belatrixsf.connect.ui.stars.comment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.common.AllStarsFragment;
import com.belatrixsf.connect.ui.stars.GiveStarFragment;
import com.belatrixsf.connect.utils.AllStarsApplication;
import com.belatrixsf.connect.utils.di.components.DaggerCommentComponent;
import com.belatrixsf.connect.utils.di.modules.presenters.CommentPresenterModule;

import butterknife.Bind;

/**
 * Created by PedroCarrillo on 4/27/16.
 */
public class CommentFragment extends AllStarsFragment implements CommentView {

    @Bind(R.id.comment) EditText commentEditText;

    private CommentPresenter commentPresenter;

    public static CommentFragment newInstance(String comment) {
        Bundle bundle = new Bundle();
        if (comment != null) {
            bundle.putString(GiveStarFragment.COMMENT_KEY, comment);
        }
        CommentFragment commentFragment = new CommentFragment();
        commentFragment.setArguments(bundle);
        return commentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null && getArguments().containsKey(GiveStarFragment.COMMENT_KEY)) {
            commentPresenter.init(getArguments().getString(GiveStarFragment.COMMENT_KEY));
        }
    }

    @Override
    protected void initDependencies(AllStarsApplication allStarsApplication) {
        commentPresenter = DaggerCommentComponent.builder().
                commentPresenterModule(new CommentPresenterModule(this))
                .build().commentPresenter();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_done, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                commentPresenter.validateComment(commentEditText.getText().toString());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        commentEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    commentPresenter.validateComment(v.getText().toString());
                }
                return true;
            }
        });
    }

    @Override
    public void selectComment(String comment) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(GiveStarFragment.COMMENT_KEY, comment);
        fragmentListener.setActivityResult(Activity.RESULT_OK, resultIntent);
        fragmentListener.closeActivity();
    }

    @Override
    public void showComment(String comment) {
        commentEditText.setText(comment);
        commentEditText.setSelection(comment.length());
    }

}

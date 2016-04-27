package com.belatrixsf.allstars.ui.comment;

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

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.ui.common.AllStarsFragment;
import com.belatrixsf.allstars.utils.AllStarsApplication;
import com.belatrixsf.allstars.utils.di.components.DaggerCommentComponent;
import com.belatrixsf.allstars.utils.di.modules.presenters.CommentPresenterModule;

import static com.belatrixsf.allstars.ui.recommendation.RecommendationFragment.COMMENT_KEY;

import butterknife.Bind;

/**
 * Created by PedroCarrillo on 4/27/16.
 */
public class CommentFragment extends AllStarsFragment implements CommentView {

    @Bind(R.id.comment) EditText commentEditText;

    private CommentPresenter commentPresenter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    protected void initDependencies(AllStarsApplication allStarsApplication) {
        commentPresenter = DaggerCommentComponent.builder().
                commentPresenterModule(new CommentPresenterModule(this))
                .build().commentPresenter();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.comment_menu, menu);
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
                return false;
            }
        });
    }

    @Override
    public void selectComment(String comment) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(COMMENT_KEY, comment);
        fragmentListener.setActivityResult(Activity.RESULT_OK, resultIntent);
        fragmentListener.closeActivity();
    }
}

package com.belatrixsf.allstars.ui.comment;

import com.belatrixsf.allstars.R;
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
        if (comment.isEmpty()) {
            view.showError(getString(R.string.comment_no_empty_error));
        } else {
            view.selectComment(comment);
        }
    }

    public void init(String comment) {
        if (comment != null) {
            view.showComment(comment);
        }
    }

}

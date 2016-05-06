package com.belatrixsf.allstars.ui.stars.comment;

import com.belatrixsf.allstars.ui.common.AllStarsView;

/**
 * Created by PedroCarrillo on 4/27/16.
 */
public interface CommentView extends AllStarsView {

    void selectComment(String comment);
    void showComment(String comment);

}

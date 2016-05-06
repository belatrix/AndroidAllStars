package com.belatrixsf.allstars.utils.di.components;

import com.belatrixsf.allstars.ui.stars.comment.CommentPresenter;
import com.belatrixsf.allstars.utils.di.modules.presenters.CommentPresenterModule;
import com.belatrixsf.allstars.utils.di.scopes.UIScope;

import dagger.Component;

/**
 * Created by PedroCarrillo on 4/27/16.
 */
@UIScope
@Component(
        modules = CommentPresenterModule.class
)
public interface CommentComponent {

    CommentPresenter commentPresenter();

}

package com.belatrixsf.allstars.utils.di.modules.presenters;

import com.belatrixsf.allstars.ui.comment.CommentView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by PedroCarrillo on 4/27/16.
 */
@Module
public class CommentPresenterModule {

    private CommentView view;

    public CommentPresenterModule(CommentView view) {
        this.view = view;
    }

    @Provides
    public CommentView providesView() {
        return view;
    }

}

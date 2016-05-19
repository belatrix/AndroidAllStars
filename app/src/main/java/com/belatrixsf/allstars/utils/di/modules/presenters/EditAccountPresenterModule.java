package com.belatrixsf.allstars.utils.di.modules.presenters;

import com.belatrixsf.allstars.ui.account.edit.EditAccountView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by PedroCarrillo on 5/17/16.
 */
@Module
public class EditAccountPresenterModule {

    private EditAccountView view;

    public EditAccountPresenterModule(EditAccountView view) {
        this.view = view;
    }

    @Provides
    public EditAccountView providesView() {
        return view;
    }

}

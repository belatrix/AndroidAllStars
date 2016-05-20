package com.belatrixsf.allstars.utils.di.components;

import com.belatrixsf.allstars.ui.account.edit.EditAccountPresenter;
import com.belatrixsf.allstars.utils.di.modules.presenters.EditAccountPresenterModule;
import com.belatrixsf.allstars.utils.di.scopes.UIScope;

import dagger.Subcomponent;

/**
 * Created by PedroCarrillo on 5/17/16.
 */
@UIScope
@Subcomponent(
        modules = EditAccountPresenterModule.class
)
public interface EditAccountComponent {

    EditAccountPresenter editAccountPresenter();

}

package com.belatrixsf.allstars.utils.di.components;

import com.belatrixsf.allstars.ui.account.AccountPresenter;
import com.belatrixsf.allstars.utils.di.modules.presenters.AccountPresenterModule;
import com.belatrixsf.allstars.utils.di.scopes.UIScope;

import dagger.Component;

/**
 * Created by PedroCarrillo on 4/13/16.
 */
@UIScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = AccountPresenterModule.class
)
public interface AccountComponent {

    AccountPresenter accountPresenter();

}

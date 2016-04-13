package com.belatrixsf.allstars.utils.di.modules.presenters;

import com.belatrixsf.allstars.ui.account.AccountView;
import com.belatrixsf.allstars.ui.login.LoginView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by PedroCarrillo on 4/13/16.
 */
@Module
public class AccountPresenterModule {

    private AccountView view;

    public AccountPresenterModule(AccountView view) {
        this.view = view;
    }

    @Provides
    public AccountView providesView() {
        return view;
    }

}

package com.belatrixsf.allstars.utils.di.components;

import com.belatrixsf.allstars.ui.contacts.keyword.ContactsKeywordListPresenter;
import com.belatrixsf.allstars.utils.di.modules.presenters.ContactsKeywordPresenterModule;
import com.belatrixsf.allstars.utils.di.scopes.UIScope;

import dagger.Subcomponent;

/**
 * Created by PedroCarrillo on 5/12/16.
 */
@UIScope
@Subcomponent(
        modules = ContactsKeywordPresenterModule.class
)
public interface ContactsKeywordListComponent {

    ContactsKeywordListPresenter contactsKeywordPresenter();

}
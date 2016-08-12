package com.belatrixsf.connect.utils.di.components;

import com.belatrixsf.connect.ui.skills.Add.AddSkillPresenterNew;
import com.belatrixsf.connect.utils.di.modules.presenters.AddSkillPresenterModule;
import com.belatrixsf.connect.utils.di.scopes.UIScope;

import dagger.Subcomponent;

/**
 * Created by echuquilin on 10/08/16.
 */
@UIScope
@Subcomponent(
        modules = AddSkillPresenterModule.class
)
public interface AddSkillComponent {
    AddSkillPresenterNew addSkillPresenter();
}

package com.belatrixsf.connect.utils.di.components;

import com.belatrixsf.connect.ui.skills.SkillsListPresenter;
import com.belatrixsf.connect.utils.di.modules.presenters.SkillsListPresenterModule;
import com.belatrixsf.connect.utils.di.scopes.UIScope;

import dagger.Subcomponent;

/**
 * Created by echuquilin on 5/08/16.
 */

@UIScope
@Subcomponent(
        modules = SkillsListPresenterModule.class
)

public interface SkillsListComponent {

    SkillsListPresenter skillsListPresenter();
}

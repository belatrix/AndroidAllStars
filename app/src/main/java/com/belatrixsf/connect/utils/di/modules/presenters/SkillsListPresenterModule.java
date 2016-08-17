package com.belatrixsf.connect.utils.di.modules.presenters;

import com.belatrixsf.connect.ui.skills.SkillsListView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by echuquilin on 5/08/16.
 */
@Module
public class SkillsListPresenterModule {

    private SkillsListView view;

    public SkillsListPresenterModule(SkillsListView view){
        this.view = view;
    }

    @Provides
    public SkillsListView providesView(){
        return view;
    }
}

package com.belatrixsf.connect.utils.di.modules.presenters;

import com.belatrixsf.connect.ui.skills.Add.AddSkillViewNew;

import dagger.Module;
import dagger.Provides;

/**
 * Created by echuquilin on 10/08/16.
 */
@Module
public class AddSkillPresenterModule {

    private AddSkillViewNew view;

    public AddSkillPresenterModule(AddSkillViewNew view){
        this.view = view;
    }

    @Provides
    public AddSkillViewNew providesView(){
        return view;
    }
}

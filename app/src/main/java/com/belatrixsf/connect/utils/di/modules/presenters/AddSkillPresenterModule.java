package com.belatrixsf.connect.utils.di.modules.presenters;

import com.belatrixsf.connect.ui.skills.Add.AddSkillView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by echuquilin on 10/08/16.
 */
@Module
public class AddSkillPresenterModule {

    private AddSkillView view;

    public AddSkillPresenterModule(AddSkillView view){
        this.view = view;
    }

    @Provides
    public AddSkillView providesView(){
        return view;
    }
}

package com.belatrixsf.allstars.utils.di.modules.presenters;

import com.belatrixsf.allstars.ui.startrecommendation.StartRecommendationView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by pedrocarrillo on 4/26/16.
 */
@Module
public class RecommendationPresenterModule {

    private StartRecommendationView view;

    public RecommendationPresenterModule(StartRecommendationView view) {
        this.view = view;
    }

    @Provides
    public StartRecommendationView providesView() {
        return view;
    }

}

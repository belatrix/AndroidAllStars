package com.belatrixsf.allstars.utils.di.modules.presenters;

import com.belatrixsf.allstars.ui.recommendation.RecommendationView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by pedrocarrillo on 4/26/16.
 */
@Module
public class RecommendationPresenterModule {

    private RecommendationView view;

    public RecommendationPresenterModule(RecommendationView view) {
        this.view = view;
    }

    @Provides
    public RecommendationView providesView() {
        return view;
    }

}

package com.belatrixsf.allstars.utils.di.components;

import com.belatrixsf.allstars.ui.recommendation.RecommendationPresenter;
import com.belatrixsf.allstars.utils.di.modules.presenters.RecommendationPresenterModule;
import com.belatrixsf.allstars.utils.di.scopes.UIScope;

import dagger.Subcomponent;

/**
 * Created by pedrocarrillo on 4/26/16.
 */
@UIScope
@Subcomponent(
        modules = RecommendationPresenterModule.class
)
public interface RecommendationComponent {

    RecommendationPresenter recommendationPresenter();

}

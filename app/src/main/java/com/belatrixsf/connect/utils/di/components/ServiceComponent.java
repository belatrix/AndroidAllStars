package com.belatrixsf.connect.utils.di.components;

import android.app.Service;

import com.belatrixsf.connect.services.fcm.AllStarsFirebaseInstanceIDService;
import com.belatrixsf.connect.utils.di.scopes.UIScope;

import dagger.Component;

/**
 * Created by PedroCarrillo on 7/13/16.
 */

@UIScope
@Component(
        dependencies = ApplicationComponent.class
)
public interface ServiceComponent {

    void inject(AllStarsFirebaseInstanceIDService service);

}

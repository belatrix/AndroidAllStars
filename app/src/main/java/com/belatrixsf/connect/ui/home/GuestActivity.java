package com.belatrixsf.connect.ui.home;

import android.os.Bundle;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.di.components.DaggerGuestHomeComponent;
import com.belatrixsf.connect.utils.di.components.DaggerUserHomeComponent;
import com.belatrixsf.connect.utils.di.modules.presenters.GuestHomePresenterModule;

/**
 * Created by PedroCarrillo on 7/4/16.
 */

public class GuestActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initDependencies() {
        BelatrixConnectApplication belatrixConnectApplication = (BelatrixConnectApplication) getApplicationContext();
        DaggerGuestHomeComponent.builder()
                .applicationComponent(belatrixConnectApplication.getApplicationComponent())
                .guestHomePresenterModule(new GuestHomePresenterModule(this))
                .build().inject(this);
    }
}

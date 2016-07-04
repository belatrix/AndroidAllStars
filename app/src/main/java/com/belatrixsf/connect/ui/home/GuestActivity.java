package com.belatrixsf.connect.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.adapters.GuestNavigationViewPagerAdapter;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.di.components.DaggerGuestHomeComponent;
import com.belatrixsf.connect.utils.di.modules.presenters.GuestHomePresenterModule;

import butterknife.Bind;

/**
 * Created by PedroCarrillo on 7/4/16.
 */

public class GuestActivity extends MainActivity {

    @Bind(R.id.main_view_pager) ViewPager mainViewPager;
    @Nullable @Bind(R.id.tab_layout) TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        setupViews();
    }

    @Override
    protected void initDependencies() {
        BelatrixConnectApplication belatrixConnectApplication = (BelatrixConnectApplication) getApplicationContext();
        DaggerGuestHomeComponent.builder()
                .applicationComponent(belatrixConnectApplication.getApplicationComponent())
                .guestHomePresenterModule(new GuestHomePresenterModule(this))
                .build().inject(this);
    }

    protected void setupViews() {
        super.setupViews();
        setupTabs();
    }

    private void setupTabs() {
        GuestNavigationViewPagerAdapter guestNavigationViewPagerAdapter = new GuestNavigationViewPagerAdapter(this, getSupportFragmentManager());
        mainViewPager.setAdapter(guestNavigationViewPagerAdapter);
        tabLayout.setupWithViewPager(mainViewPager);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, GuestActivity.class);
    }
}

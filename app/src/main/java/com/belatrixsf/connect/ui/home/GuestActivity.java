package com.belatrixsf.connect.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.adapters.GuestNavigationViewPagerAdapter;
import com.belatrixsf.connect.ui.about.AboutActivity;
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
        setupNavigationDrawerMenu();
        setupNavigationDrawerListener();
        setupTabs();
        homePresenter.loadEmployeeData();
    }

    private void setupTabs() {
        GuestNavigationViewPagerAdapter guestNavigationViewPagerAdapter = new GuestNavigationViewPagerAdapter(this, getSupportFragmentManager());
        mainViewPager.setAdapter(guestNavigationViewPagerAdapter);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(mainViewPager);
            tabLayout.setVisibility(View.GONE);
        }
    }

    protected void setupNavigationDrawerMenu() {
        super.setNavigationDrawerMenu(R.menu.menu_guest_nav_view);
    }

    protected void setupNavigationDrawerListener() {
        super.setNavigationDrawerListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.menu_event:
                        break;
                    case R.id.menu_about:
                        Intent intent = new Intent(GuestActivity.this, AboutActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, GuestActivity.class);
    }
}

package com.belatrixsf.connect.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.about.AboutActivity;
import com.belatrixsf.connect.ui.event.EventListFragment;
import com.belatrixsf.connect.ui.settings.SettingsActivity;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.di.components.DaggerGuestHomeComponent;
import com.belatrixsf.connect.utils.di.modules.presenters.GuestHomePresenterModule;

import butterknife.Bind;

/**
 * Created by PedroCarrillo on 7/4/16.
 */

public class GuestActivity extends MainActivity {

    @Bind(R.id.main_coordinator) CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
        if (savedInstanceState == null) {
            replaceFragment(EventListFragment.newInstance(), false);
        }
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
        homePresenter.loadEmployeeData();
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
                    case R.id.menu_settings:
                        Intent settingsIntent = new Intent(GuestActivity.this, SettingsActivity.class);
                        startActivity(settingsIntent);
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

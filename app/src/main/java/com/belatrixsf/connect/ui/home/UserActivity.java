/* The MIT License (MIT)
* Copyright (c) 2016 BELATRIX
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:

* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.

* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/
package com.belatrixsf.connect.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.view.ActionMode;
import android.view.MenuItem;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.belatrixsf.connect.R;
import com.belatrixsf.connect.adapters.UserNavigationViewPagerAdapter;
import com.belatrixsf.connect.services.fcm.ConnectFirebaseMessagingService;
import com.belatrixsf.connect.ui.about.AboutActivity;
import com.belatrixsf.connect.ui.account.AccountActivity;
import com.belatrixsf.connect.ui.account.AccountFragmentListener;
import com.belatrixsf.connect.ui.account.edit.EditAccountFragment;
import com.belatrixsf.connect.ui.contacts.ContactsListActivity;
import com.belatrixsf.connect.ui.event.EventListActivity;
import com.belatrixsf.connect.ui.notifications.NotificationListActivity;
import com.belatrixsf.connect.ui.ranking.RankingFragmentListener;
import com.belatrixsf.connect.ui.settings.SettingsActivity;
import com.belatrixsf.connect.ui.stars.GiveStarActivity;
import com.belatrixsf.connect.ui.stars.GiveStarFragment;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.SnackbarUtils;
import com.belatrixsf.connect.utils.di.components.DaggerUserHomeComponent;
import com.belatrixsf.connect.utils.di.modules.presenters.UserHomePresenterModule;

import butterknife.Bind;

/**
 * Created by PedroCarrillo on 7/4/16.
 */
public class UserActivity extends MainActivity implements RankingFragmentListener, AccountFragmentListener {

    public static final int RANKING_TAB = 3;
    public static final int RQ_GIVE_STAR = 99;
    public static final int PARENT_INDEX = 3;
    private ConnectFirebaseMessagingService.TargetTab tabSelected;

    @Bind(R.id.main_view_pager) ViewPager mainViewPager;
    @Bind(R.id.start_recommendation) FloatingActionButton startRecommendationButton;
    @Bind(R.id.main_coordinator) CoordinatorLayout coordinatorLayout;
    @Nullable @Bind(R.id.tab_layout) TabLayout tabLayout;
    @Bind(R.id.bottom_navigation) AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Bundle extras = getIntent().getExtras();
        if(extras != null && extras.containsKey(ConnectFirebaseMessagingService.ACTIVITY_TAB_BUNDLE_KEY)){
            tabSelected = (ConnectFirebaseMessagingService.TargetTab)
                    extras.get(ConnectFirebaseMessagingService.ACTIVITY_TAB_BUNDLE_KEY);
        } else {
            tabSelected = ConnectFirebaseMessagingService.TargetTab.ACCOUNT_TAB;
        }
        setToolbar();
        setupViews();
    }

    @Override
    protected void initDependencies() {
        BelatrixConnectApplication belatrixConnectApplication = (BelatrixConnectApplication) getApplicationContext();
        DaggerUserHomeComponent.builder()
                .applicationComponent(belatrixConnectApplication.getApplicationComponent())
                .userHomePresenterModule(new UserHomePresenterModule(this))
                .build().inject(this);
    }

    protected void setupViews() {
        super.setupViews();
        setupNavigationDrawerMenu();
        setupNavigationDrawerListener();
        setupActionButton();
        setupTabs();
    }

    private void setupActionButton() {
        startRecommendationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, GiveStarActivity.class);
                startActivityForResult(intent, RQ_GIVE_STAR);
            }
        });
    }

    private void setupTabs() {
        UserNavigationViewPagerAdapter userNavigationViewPagerAdapter = new UserNavigationViewPagerAdapter(this, getSupportFragmentManager());
        mainViewPager.setAdapter(userNavigationViewPagerAdapter);
        mainViewPager.setOffscreenPageLimit(3);
        mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == RANKING_TAB) {
                    startRecommendationButton.hide();
                    bottomNavigation.restoreBottomNavigation();
                    bottomNavigation.setBehaviorTranslationEnabled(true);
                    bottomNavigation.setCurrentItem(1);
                } else {
                    startRecommendationButton.show();
                    bottomNavigation.hideBottomNavigation();
                    bottomNavigation.setBehaviorTranslationEnabled(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (tabLayout != null) {
            tabLayout.setupWithViewPager(mainViewPager);
        }

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_last_month, R.drawable.ic_event, R.color.colorAccent);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_current_month, R.drawable.ic_whatshot, R.color.colorAccent);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_all_time, R.drawable.ic_star, R.color.colorAccent);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        bottomNavigation.hideBottomNavigation();

        switch (tabSelected){
            case ACCOUNT_TAB:
                mainViewPager.setCurrentItem(0);
                break;
            case EVENTS_TAB:
                mainViewPager.setCurrentItem(1);
                break;
            case CONTACTS_TAB:
                mainViewPager.setCurrentItem(2);
                break;
            case RANKING_TAB:
                mainViewPager.setCurrentItem(3);
                break;
        }
    }

    protected void setupNavigationDrawerMenu() {
        super.setNavigationDrawerMenu(R.menu.menu_user_nav_view);
    }

    protected void setupNavigationDrawerListener() {
        super.setNavigationDrawerListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        break;
                    case R.id.menu_activities:
                        Intent intent = new Intent(UserActivity.this, NotificationListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_settings:
                        intent = new Intent(UserActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_about:
                        intent = new Intent(UserActivity.this, AboutActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_help:
                        //TODO: HelpActivity
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void setBottomTabListener(AHBottomNavigation.OnTabSelectedListener onTabSelectedListener) {
        bottomNavigation.setOnTabSelectedListener(onTabSelectedListener);
    }

    @Override
    public void refreshNavigationDrawer() {
        homePresenter.loadEmployeeData();
    }

    @Override
    public void onSupportActionModeStarted(@NonNull ActionMode mode) {
        drawerLayout.closeDrawers();
        appBarLayout.setExpanded(false, true);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if (toolbar != null && toolbar.getLayoutParams() != null){
                    AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
                    params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
                    toolbar.setVisibility(View.GONE);
                }
            }
        }, 300);
        super.onSupportActionModeStarted(mode);
    }

    @Override
    public void onSupportActionModeFinished(@NonNull ActionMode mode) {
        if (toolbar != null && toolbar.getLayoutParams() != null){
            AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
            appBarLayout.setExpanded(true, true);
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    toolbar.setVisibility(View.VISIBLE);
                }
            }, 300);
        }
        super.onSupportActionModeFinished(mode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RQ_GIVE_STAR && resultCode == Activity.RESULT_OK && data != null) {
            SnackbarUtils.createInformationSnackBar(data.getStringExtra(GiveStarFragment.MESSAGE_KEY), getString(R.string.dialog_option_confirm), coordinatorLayout,new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //nothing
                }
            }).show();
        } else if (requestCode == EditAccountFragment.RQ_EDIT_ACCOUNT && resultCode == Activity.RESULT_OK && data != null) {
            ((UserHomePresenter) homePresenter).refreshEmployee();
        }
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, UserActivity.class);
    }

}

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
import android.content.DialogInterface;
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
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.view.ActionMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.belatrixsf.connect.R;
import com.belatrixsf.connect.adapters.MainNavigationViewPagerAdapter;
import com.belatrixsf.connect.entities.Employee;
import com.belatrixsf.connect.ui.account.AccountFragmentListener;
import com.belatrixsf.connect.ui.account.edit.EditAccountFragment;
import com.belatrixsf.connect.ui.common.BelatrixConnectActivity;
import com.belatrixsf.connect.ui.contacts.ContactsListActivity;
import com.belatrixsf.connect.ui.login.LoginActivity;
import com.belatrixsf.connect.ui.ranking.RankingFragmentListener;
import com.belatrixsf.connect.ui.stars.GiveStarActivity;
import com.belatrixsf.connect.ui.stars.GiveStarFragment;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.DialogUtils;
import com.belatrixsf.connect.utils.di.components.DaggerHomeComponent;
import com.belatrixsf.connect.utils.di.modules.presenters.HomePresenterModule;
import com.belatrixsf.connect.utils.media.ImageFactory;
import com.belatrixsf.connect.utils.media.loaders.ImageLoader;

import javax.inject.Inject;

import butterknife.Bind;

public class MainActivity extends BelatrixConnectActivity implements HomeView, RankingFragmentListener, AccountFragmentListener {

    public static final int RQ_GIVE_STAR = 99;
    public static final int RANKING_TAB = 1;

    @Inject HomePresenter homePresenter;

    @Bind(R.id.drawer) DrawerLayout drawerLayout;
    @Bind(R.id.navigation) NavigationView navigationView;
    @Bind(R.id.menu_logout) TextView menuLogoutTextView;
    @Bind(R.id.app_bar_layout) AppBarLayout appBarLayout;
    @Nullable @Bind(R.id.tab_layout) TabLayout tabLayout;
    @Bind(R.id.main_view_pager) ViewPager mainViewPager;
    @Bind(R.id.start_recommendation) FloatingActionButton startRecommendationButton;
    @Bind(R.id.main_coordinator) CoordinatorLayout coordinatorLayout;
    @Bind(R.id.bottom_navigation) AHBottomNavigation bottomNavigation;

    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        setupDependencies();
        setupViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupViews() {
        setupActionButton();
        setupTabs();
        setupNavigationDrawer();
    }

    private void setupActionButton() {
        startRecommendationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GiveStarActivity.class);
                startActivityForResult(intent, RQ_GIVE_STAR);
            }
        });
    }

    private void setupTabs() {
        MainNavigationViewPagerAdapter mainNavigationViewPagerAdapter = new MainNavigationViewPagerAdapter(this, getSupportFragmentManager());
        mainViewPager.setAdapter(mainNavigationViewPagerAdapter);
        mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == RANKING_TAB) {
                    bottomNavigation.setVisibility(View.VISIBLE);
                    bottomNavigation.setCurrentItem(1);
                    startRecommendationButton.hide();

                } else {
                    bottomNavigation.setVisibility(View.GONE);
                    startRecommendationButton.show();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(mainViewPager);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_last_month, R.drawable.ic_event, R.color.colorAccent);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_current_month, R.drawable.ic_whatshot, R.color.colorActiveSmall);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_all_time, R.drawable.ic_star, R.color.colorPrimary);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        bottomNavigation.setBehaviorTranslationEnabled(false);

    }

    private void setupNavigationDrawer(){
        menuLogoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                homePresenter.wantToLogout();
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override public boolean onNavigationItemSelected(MenuItem item) {
                        drawerLayout.closeDrawers();
                        switch (item.getItemId()){
                            case R.id.menu_home:
                                break;
                            case R.id.menu_contacts:
                                Intent intent = new Intent(MainActivity.this, ContactsListActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return true;
                    }
                });
    }

    private void setupDependencies() {
        BelatrixConnectApplication belatrixConnectApplication = (BelatrixConnectApplication) getApplicationContext();
        DaggerHomeComponent.builder()
                .applicationComponent(belatrixConnectApplication.getApplicationComponent())
                .homePresenterModule(new HomePresenterModule(this))
                .build().inject(this);
    }

    // HomeView

    @Override
    public void setNavigationDrawerData(final Employee employee) {
        if (navigationView != null) {
            navigationView.post(new Runnable() {
                @Override
                public void run() {
                    ImageView pictureImageView = (ImageView) navigationView.findViewById(R.id.picture);
                    TextView fullNameTextView = (TextView) navigationView.findViewById(R.id.full_name);
                    TextView emailTextView = (TextView) navigationView.findViewById(R.id.email);
                    if (pictureImageView != null && fullNameTextView != null && emailTextView != null) {
                        ImageFactory.getLoader().loadFromUrl(employee.getAvatar(), pictureImageView, ImageLoader.ImageTransformation.BORDERED_CIRCLE);
                        fullNameTextView.setText(employee.getFullName());
                        emailTextView.setText(employee.getEmail());
                    }
                }
            });

        }
    }

    @Override
    public void goToLogin() {
        startActivity(LoginActivity.makeIntent(this));
        finish();
    }

    @Override
    public void showLogoutConfirmationDialog(String message) {
        DialogUtils.createConfirmationDialog(this, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                homePresenter.confirmLogout();
            }
        }, null).show();
    }

    @Override
    public Context getContext() {
        return this;
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
            DialogUtils.createInformationDialog(this, data.getStringExtra(GiveStarFragment.MESSAGE_KEY), getString(R.string.app_name), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Do Nothing
                }
            }).show();
        } else if (requestCode == EditAccountFragment.RQ_EDIT_ACCOUNT && resultCode == Activity.RESULT_OK && data != null) {
            homePresenter.refreshEmployee();
        }
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public void setBottomTabListener(AHBottomNavigation.OnTabSelectedListener onTabSelectedListener) {
        bottomNavigation.setOnTabSelectedListener(onTabSelectedListener);
    }

    @Override
    public void showProgressIndicator() {

    }

    @Override
    public void hideProgressIndicator() {

    }

    @Override
    public void refreshNavigationDrawer() {
        homePresenter.loadEmployeeData();
    }
}
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
package com.belatrixsf.allstars.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.adapters.MainNavigationViewPagerAdapter;
import com.belatrixsf.allstars.ui.common.AllStarsActivity;
import com.belatrixsf.allstars.ui.contacts.ContactFragmentListener;
import com.belatrixsf.allstars.ui.givestar.GiveStarActivity;
import com.belatrixsf.allstars.ui.givestar.GiveStarFragment;
import com.belatrixsf.allstars.ui.login.LoginActivity;
import com.belatrixsf.allstars.ui.ranking.RankingFragmentListener;
import com.belatrixsf.allstars.utils.AllStarsApplication;
import com.belatrixsf.allstars.utils.DialogUtils;
import com.belatrixsf.allstars.utils.di.components.DaggerHomeComponent;
import com.belatrixsf.allstars.utils.di.modules.presenters.HomePresenterModule;

import javax.inject.Inject;

import butterknife.Bind;

public class MainActivity extends AllStarsActivity implements ContactFragmentListener, HomeView, RankingFragmentListener {

    public static final int RQ_GIVE_STAR = 99;
    public static final int RANKING_TAB = 1;

    @Inject HomePresenter homePresenter;

    @Bind(R.id.app_bar_layout) AppBarLayout appBarLayout;
    @Bind(R.id.tab_layout) TabLayout tabLayout;
    @Bind(R.id.main_view_pager) ViewPager mainViewPager;
    @Bind(R.id.start_recommendation)
    FloatingActionButton startRecommendationButton;
    @Bind(R.id.main_coordinator) CoordinatorLayout coordinatorLayout;
    @Bind(R.id.bottom_navigation) AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        setupViews();
        setupDependencies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                homePresenter.wantToLogout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViews() {
        setupActionButton();
        setupTabs();
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
        MainNavigationViewPagerAdapter mainNavigationViewPagerAdapter = new MainNavigationViewPagerAdapter(getSupportFragmentManager());
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

    private void setupDependencies() {
        AllStarsApplication allStarsApplication = (AllStarsApplication) getApplicationContext();
        DaggerHomeComponent.builder()
                .applicationComponent(allStarsApplication.getApplicationComponent())
                .homePresenterModule(new HomePresenterModule(this))
                .build().inject(this);
    }

    // HomeView

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

    // ContactFragmentListener

    @Override
    public void setActionMode(ActionMode.Callback callback) {
        startSupportActionMode(callback);
    }

    @Override
    public void onSupportActionModeStarted(@NonNull ActionMode mode) {
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
        if (resultCode == Activity.RESULT_OK && data != null) {
            DialogUtils.createInformationDialog(this, data.getStringExtra(GiveStarFragment.MESSAGE_KEY), getString(R.string.app_name), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Do Nothing
                }
            }).show();
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
}
package com.belatrixsf.connect.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.belatrixsf.connect.R;
import com.belatrixsf.connect.adapters.MainNavigationViewPagerAdapter;
import com.belatrixsf.connect.ui.account.AccountFragmentListener;
import com.belatrixsf.connect.ui.account.edit.EditAccountFragment;
import com.belatrixsf.connect.ui.ranking.RankingFragmentListener;
import com.belatrixsf.connect.ui.stars.GiveStarActivity;
import com.belatrixsf.connect.ui.stars.GiveStarFragment;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.DialogUtils;
import com.belatrixsf.connect.utils.di.components.DaggerUserHomeComponent;
import com.belatrixsf.connect.utils.di.modules.presenters.GuestHomePresenterModule;
import com.belatrixsf.connect.utils.di.modules.presenters.UserHomePresenterModule;

import butterknife.Bind;

/**
 * Created by PedroCarrillo on 7/4/16.
 */

public class UserActivity extends MainActivity implements RankingFragmentListener, AccountFragmentListener {

    public static final int RANKING_TAB = 1;
    public static final int RQ_GIVE_STAR = 99;

    @Bind(R.id.main_view_pager) ViewPager mainViewPager;
    @Bind(R.id.start_recommendation) FloatingActionButton startRecommendationButton;
    @Bind(R.id.main_coordinator) CoordinatorLayout coordinatorLayout;
    @Nullable @Bind(R.id.tab_layout) TabLayout tabLayout;
    @Bind(R.id.bottom_navigation) AHBottomNavigation bottomNavigation;

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
        DaggerUserHomeComponent.builder()
                .applicationComponent(belatrixConnectApplication.getApplicationComponent())
                .userHomePresenterModule(new UserHomePresenterModule(this))
                .build().inject(this);
    }

    protected void setupViews() {
        super.setupViews();
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

    @Override
    public void setBottomTabListener(AHBottomNavigation.OnTabSelectedListener onTabSelectedListener) {
        bottomNavigation.setOnTabSelectedListener(onTabSelectedListener);
    }

    @Override
    public void refreshNavigationDrawer() {
        homePresenter.loadEmployeeData();
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
            ((UserHomePresenter) homePresenter).refreshEmployee();
        }
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, UserActivity.class);
    }


}

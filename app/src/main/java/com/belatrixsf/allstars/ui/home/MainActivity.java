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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.adapters.MainNavigationViewPagerAdapter;
import com.belatrixsf.allstars.ui.common.AllStarsActivity;
import com.belatrixsf.allstars.ui.contacts.ContactFragmentListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AllStarsActivity implements ContactFragmentListener {

    @Bind(R.id.app_bar_layout) AppBarLayout appBarLayout;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.tab_layout) TabLayout tabLayout;
    @Bind(R.id.main_view_pager) ViewPager mainViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupViews();
    }

    private void setupViews() {
        setupTabs();
    }

    private void setupTabs() {
        MainNavigationViewPagerAdapter mainNavigationViewPagerAdapter = new MainNavigationViewPagerAdapter(getFragmentManager());
        mainViewPager.setAdapter(mainNavigationViewPagerAdapter);
        tabLayout.setupWithViewPager(mainViewPager);
    }

    @Override
    public void setActionMode(ActionMode.Callback callback) {
        startSupportActionMode(callback);
    }

    @Override
    public void onSupportActionModeStarted(@NonNull ActionMode mode) {
        Log.e("MainActivity", "Start ActionMode");
        appBarLayout.setExpanded(false, true);
        /*AppBarLayout.LayoutParams params;
        params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);*/
        appBarLayout.postOnAnimation(new Runnable() {
            @Override
            public void run() {
                tabLayout.setVisibility(View.GONE);
            }
        });

        super.onSupportActionModeStarted(mode);
    }

    @Override
    public void onSupportActionModeFinished(@NonNull ActionMode mode) {
        Log.e("MainActivity", "Finish ActionMode");
        appBarLayout.setExpanded(true, true);

        appBarLayout.postOnAnimation(new Runnable() {
            @Override
            public void run() {
                tabLayout.setVisibility(View.VISIBLE);
            }
        });


        super.onSupportActionModeFinished(mode);
    }
}
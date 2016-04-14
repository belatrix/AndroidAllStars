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
package com.belatrixsf.allstars.ui;

import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.adapters.MainNavigationViewPagerAdapter;
import com.belatrixsf.allstars.ui.common.AllStarsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AllStarsActivity {

    @Bind(R.id.tab_layout) TabLayout tabLayout;
    @Bind(R.id.main_view_pager) ViewPager mainViewPager;
    @Bind(R.id.tv_name) TextView nameTextView;
    @Bind(R.id.tv_role) TextView roleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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


}

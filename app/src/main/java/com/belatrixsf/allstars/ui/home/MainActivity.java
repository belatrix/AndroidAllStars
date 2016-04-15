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

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.adapters.MainNavigationViewPagerAdapter;
import com.belatrixsf.allstars.ui.common.AllStarsActivity;
import com.belatrixsf.allstars.utils.AllStarsApplication;
import com.belatrixsf.allstars.utils.di.components.DaggerEmployeeComponent;
import com.belatrixsf.allstars.utils.di.modules.presenters.EmployeePresenterModule;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AllStarsActivity implements EmployeeView {

    private EmployeePresenter employeePresenter;

    @Bind(R.id.tab_layout) TabLayout tabLayout;
    @Bind(R.id.main_view_pager) ViewPager mainViewPager;
    @Bind(R.id.profile_name) TextView nameTextView;
    @Bind(R.id.profile_role) TextView roleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        employeePresenter = DaggerEmployeeComponent.builder()
                .employeePresenterModule(new EmployeePresenterModule(this))
                .applicationComponent(((AllStarsApplication)getApplication()).getApplicationComponent())
                .build()
                .employeePresenter();
        employeePresenter.loadEmployeeAccount();
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
    public void showEmployeeName(String employeName) {
        nameTextView.setText(employeName);
    }

    @Override
    public void showRole(String role) {
        roleTextView.setText(role);
    }

    @Override
    public void showProfilePicture(String profilePicture) {

    }

    @Override
    public Context getContext() {
        return this;
    }

}

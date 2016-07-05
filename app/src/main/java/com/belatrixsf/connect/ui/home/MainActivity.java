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

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.view.ActionMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.common.BelatrixConnectActivity;
import com.belatrixsf.connect.ui.login.LoginActivity;
import com.belatrixsf.connect.utils.DialogUtils;
import com.belatrixsf.connect.utils.media.ImageFactory;
import com.belatrixsf.connect.utils.media.loaders.ImageLoader;

import javax.inject.Inject;

import butterknife.Bind;

public abstract class MainActivity extends BelatrixConnectActivity implements HomeView {

    @Inject HomePresenter homePresenter;

    @Bind(R.id.drawer) DrawerLayout drawerLayout;
    @Bind(R.id.navigation) NavigationView navigationView;
    @Bind(R.id.menu_logout) TextView menuLogoutTextView;
    @Bind(R.id.app_bar_layout) AppBarLayout appBarLayout;

    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDependencies();
    }

    protected void setupViews() {
        setupNavigationDrawer();
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

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    protected abstract void initDependencies();

    protected void setNavigationDrawerMenu(Integer menuResource) {
        navigationView.inflateMenu(menuResource);
    }

    protected void setNavigationDrawerListener(NavigationView.OnNavigationItemSelectedListener listener) {
        navigationView.setNavigationItemSelectedListener(listener);
    }

    // HomeView

    @Override
    public void setNavigationDrawerData(final String photoUrl, final String fullName, final String email) {
        if (navigationView != null) {
            navigationView.post(new Runnable() {
                @Override
                public void run() {
                    ImageView pictureImageView = (ImageView) navigationView.findViewById(R.id.picture);
                    TextView fullNameTextView = (TextView) navigationView.findViewById(R.id.full_name);
                    TextView emailTextView = (TextView) navigationView.findViewById(R.id.email);
                    if (pictureImageView != null && fullNameTextView != null && emailTextView != null) {
                        ImageFactory.getLoader().loadFromUrl(photoUrl,
                                pictureImageView,
                                ImageLoader.ImageTransformation.BORDERED_CIRCLE,
                                pictureImageView.getResources().getDrawable(R.drawable.contact_placeholder)
                        );
                        fullNameTextView.setText(fullName);
                        emailTextView.setText(email);
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
    public void showProgressIndicator() {

    }

    @Override
    public void hideProgressIndicator() {

    }

}
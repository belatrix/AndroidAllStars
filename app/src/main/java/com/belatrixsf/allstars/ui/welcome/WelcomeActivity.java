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
package com.belatrixsf.allstars.ui.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.ui.common.AllStarsActivity;
import com.belatrixsf.allstars.ui.login.LoginFragment;
import com.belatrixsf.allstars.ui.signup.SignUpFragment;
import com.belatrixsf.allstars.utils.AllStarsApplication;
import com.belatrixsf.allstars.utils.di.components.DaggerWelcomeComponent;
import com.belatrixsf.allstars.utils.di.modules.presenters.WelcomePresenterModule;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by icerrate on 17/05/16.
 */
public class WelcomeActivity extends AllStarsActivity implements WelcomeView {

    @Inject WelcomePresenter welcomePresenter;

    @Bind(R.id.main_frame) FrameLayout mainFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setViews();
        setupDependencies();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, WelcomeActivity.class);
    }

    public void setViews(){
        setFragment();
    }

    public void setFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, new LoginFragment())
                .commit();
    }

    private void setupDependencies() {
        AllStarsApplication allStarsApplication = (AllStarsApplication) getApplicationContext();
        DaggerWelcomeComponent.builder()
                .applicationComponent(allStarsApplication.getApplicationComponent())
                .welcomePresenterModule(new WelcomePresenterModule(this))
                .build().inject(this);
    }

    @Override
    public void showLogin() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.main_frame, new LoginFragment())
                .commit();
    }

    @Override
    public void showSignUp() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.main_frame, new SignUpFragment())
                .commit();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showProgressIndicator() {

    }

    @Override
    public void hideProgressIndicator() {

    }
}
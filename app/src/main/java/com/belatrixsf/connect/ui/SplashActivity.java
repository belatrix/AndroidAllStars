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
package com.belatrixsf.connect.ui;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.managers.PreferencesManager;
import com.belatrixsf.connect.ui.home.GuestActivity;
import com.belatrixsf.connect.ui.home.UserActivity;
import com.belatrixsf.connect.ui.login.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by echuquilin on 11/08/16.
 */
public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.logoImageView) ImageView logo;
    @Bind(R.id.splashBackground) RelativeLayout background;

    public static final int WAIT_DURATION = 800;
    public static final int ANIMATION_DURATION = 350;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        startAnimation();
    }

    private void startAnimation() {
        this.setTheme(R.style.SplashThemeFinished);
        Handler waitHandler = new Handler();
        final Handler animationHandler = new Handler();
        final TransitionDrawable bgTransition = (TransitionDrawable) background.getBackground();
        final TransitionDrawable logoTransition = (TransitionDrawable) logo.getBackground();
        waitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bgTransition.startTransition(ANIMATION_DURATION);
                logoTransition.startTransition(ANIMATION_DURATION);
                animationHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        continueFlow();
                    }
                }, ANIMATION_DURATION);
            }
        }, WAIT_DURATION);
    }

    private void continueFlow() {
        boolean userHasPermission = PreferencesManager.get().getEmployeeId() != 0 &&
                PreferencesManager.get().getToken() != null &&
                PreferencesManager.get().isResetPassword() &&
                PreferencesManager.get().isEditProfile();
        if (userHasPermission) {
            //Restore Employee session
            startActivity(UserActivity.makeIntent(this));
        } else {
            PreferencesManager.get().clearUserSession();

            boolean guestHasPermission = PreferencesManager.get().getGuestId() != 0;
            if (guestHasPermission) {
                //Restore Guest session
                startActivity(GuestActivity.makeIntent(this));
            } else {
                PreferencesManager.get().clearGuestSession();
                startActivity(LoginActivity.makeIntent(this));
                //startActivity(WizardMainActivity.makeIntent(this));
            }
        }
        overridePendingTransition(0, 0);
        finish();
    }

}
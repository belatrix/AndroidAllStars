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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.managers.PreferencesManager;
import com.belatrixsf.connect.ui.common.BelatrixConnectActivity;
import com.belatrixsf.connect.ui.home.GuestActivity;
import com.belatrixsf.connect.ui.home.UserActivity;
import com.belatrixsf.connect.ui.login.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by echuquilin on 11/08/16.
 */
public class SplashActivity extends BelatrixConnectActivity {

    @Bind(R.id.launcherImageView) ImageView launcher;
    @Bind(R.id.logoImageView) ImageView logo;
    @Bind(R.id.splashBackground) RelativeLayout background;

    public static final int WAIT_DURATION = 800;
    public static final int ANIMATION_DURATION = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        startAnimation();
    }

    private void startAnimation() {
        Handler waitHandler = new Handler();
        final Handler animationHandler = new Handler();
        final TransitionDrawable generalBgTransition = (TransitionDrawable) background.getBackground();
        final TransitionDrawable launcherBgTransition = (TransitionDrawable) launcher.getBackground();
        final TransitionDrawable logoBgTransition = (TransitionDrawable) logo.getBackground();
        waitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                generalBgTransition.startTransition(ANIMATION_DURATION);
                launcherBgTransition.startTransition(ANIMATION_DURATION);
                logoBgTransition.startTransition(ANIMATION_DURATION);
                animateLogo();
                animationHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        continueFlow();
                    }
                }, ANIMATION_DURATION);
            }
        }, WAIT_DURATION);
    }

    private void animateLogo() {
        AlphaAnimation launcherAnim = new AlphaAnimation(1f, 0f);
        launcherAnim.setDuration(ANIMATION_DURATION / 2);
        launcherAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                launcher.setAlpha(0f);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        launcher.startAnimation(launcherAnim);

        AlphaAnimation logoAnim = new AlphaAnimation(0f, 1f);
        logoAnim.setDuration(ANIMATION_DURATION / 2);
        logoAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                logo.setAlpha(1f);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        logo.startAnimation(logoAnim);
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
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

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.Window;
import android.widget.ImageView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.home.UserActivity;
import com.belatrixsf.connect.ui.login.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by echuquilin on 9/22/17.
 */
public class IntermediaryLogoActivity extends AppCompatActivity {

    public static Intent nextActivity;
    public static Activity previousActivity;

    private final String INTERMEDIARY_EXTRA_KEY = "intermediary_key";
    private final int WAIT_DURATION = 1000;

    private boolean toLogin;

    @Bind(R.id.logoImageView) ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermediary);
        ButterKnife.bind(this);
        toLogin = getIntent().getBooleanExtra(INTERMEDIARY_EXTRA_KEY, true);
        setupEnterSharedAnimation();
        continueFlow(toLogin ? WAIT_DURATION : (WAIT_DURATION / 2));
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, IntermediaryLogoActivity.class);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupEnterSharedAnimation() {
        Window window = getWindow();
        TransitionSet set = new TransitionSet();
        set.addTransition(new ChangeImageTransform());
        set.addTransition(new ChangeBounds());
        set.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {}

            @Override
            public void onTransitionEnd(Transition transition) {
                if (previousActivity != null) {
                    previousActivity.finish();
                }
            }

            @Override
            public void onTransitionCancel(Transition transition) {}

            @Override
            public void onTransitionPause(Transition transition) {}

            @Override
            public void onTransitionResume(Transition transition) {}
        });
        window.setSharedElementEnterTransition(set);
    }

    private void continueFlow(int duration) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (toLogin) {
                    startActivity(LoginActivity.makeIntent(IntermediaryLogoActivity.this));
                    overridePendingTransition(0, 0);
                    finish();
                } else {
                    startAnimatedActivity();
                    overridePendingTransition(0, 0);
                }
            }
        }, duration);
    }

    public void startAnimatedActivity() {
        UserActivity.previousActivity = this;
        String transitionName = getString(R.string.transition_splash_logo);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, logo, transitionName);
        ActivityCompat.startActivity(this, nextActivity, options.toBundle());
    }

}
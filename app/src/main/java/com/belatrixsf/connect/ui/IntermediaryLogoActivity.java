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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.login.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.belatrixsf.connect.ui.home.UserActivity.ANIMATION_KEY;

/**
 * Created by echuquilin on 9/22/17.
 */
public class IntermediaryLogoActivity extends AppCompatActivity {

    public static Intent nextActivity;

    public static final String INTERMEDIARY_EXTRA_KEY = "intermediary_key";
    private final int REVEAL_DURATION = 600;
    private final int WAIT_DURATION = 100;

    private boolean toLogin;

    @Bind(R.id.logoImageView) ImageView logo;

    private Runnable enterRunnable = new Runnable() {
        @Override
        public void run() {
            Animator animator = showViewCircleRevealAnimator();
            animator.setDuration(REVEAL_DURATION);
            animator.start();
        }
    };

    private Runnable exitRunnable = new Runnable() {
        @Override
        public void run() {
            Animator animator = hideViewCircleRevealAnimator();
            animator.setDuration(REVEAL_DURATION);
            animator.start();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermediary);
        ButterKnife.bind(this);
        toLogin = getIntent().getBooleanExtra(INTERMEDIARY_EXTRA_KEY, true);
        if (toLogin) {
            logo.setVisibility(View.INVISIBLE);
            startAnimation(enterRunnable);
        } else {
            logo.setVisibility(View.VISIBLE);
            startAnimation(exitRunnable);
        }
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, IntermediaryLogoActivity.class);
    }

    private void startAnimation(Runnable runnable) {
        new Handler().postDelayed(runnable, WAIT_DURATION);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Animator showViewCircleRevealAnimator() {
        int cx = logo.getWidth() / 2;
        int cy = logo.getHeight() / 2;

        float finalRadius = (float) Math.hypot(cx, cy);
        Animator anim = ViewAnimationUtils.createCircularReveal(logo, cx, cy, 0, finalRadius);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                continueFlow();
            }
        });
        logo.setVisibility(View.VISIBLE);

        return anim;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Animator hideViewCircleRevealAnimator() {
        int cx = logo.getWidth() / 2;
        int cy = logo.getHeight() / 2;

        float initialRadius = (float) Math.hypot(cx, cy);
        Animator anim = ViewAnimationUtils.createCircularReveal(logo, cx, cy, initialRadius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                logo.setVisibility(View.INVISIBLE);
                continueFlow();
            }
        });

        return anim;
    }

    private void continueFlow() {
        Intent intent;
        if (toLogin) {
            intent = LoginActivity.makeIntent(IntermediaryLogoActivity.this);
        } else {
            intent = nextActivity;
            intent.putExtra(ANIMATION_KEY, true);
        }
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

}

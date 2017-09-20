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
package com.belatrixsf.connect.ui.common;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.belatrixsf.connect.R;

/**
 * Created by educhuks on 9/14/17.
 */

public abstract class BaseAnimationsFragment extends BelatrixConnectFragment {

    public enum OutInAnimDirection {
        OUT_RIGHT,
        OUT_LEFT,
        OUT_UP,
        OUT_DOWN,
        IN_RIGHT,
        IN_LEFT,
        IN_UP,
        IN_DOWN
    }

    public enum StraightAnimDirection {
        RIGHT,
        LEFT,
        UP,
        DOWN
    }

    public static final int ANIMATIONS_DURATION = 450;
    public static final int WAIT_DURATION = 100;

    protected Animation scaleAnimation(float scale, AnimationListener listener) {
        return scaleAnimation(1f, scale, listener, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
    }

    protected Animation scaleAnimation(float fromScale, float toScale, AnimationListener listener,
                                       int pivotXType, float pivotX, int pivotYType, float pivotY) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(fromScale, toScale, fromScale, toScale,
                                                            pivotXType, pivotX, pivotYType, pivotY);
        scaleAnimation.setDuration(ANIMATIONS_DURATION);
        scaleAnimation.setFillAfter(true);
        if (listener != null) {
            scaleAnimation.setAnimationListener(listener);
        }
        return scaleAnimation;
    }

    protected Animation verticalTranslationAnimation(float initialY, AnimationListener listener) {
        return verticalTranslationAnimation(initialY, 0, listener); // moves to original position
    }

    protected Animation verticalTranslationAnimation(float initialY, float finalY, AnimationListener listener) {
        TranslateAnimation translation = new TranslateAnimation(0, 0, initialY, finalY);
        translation.setDuration(ANIMATIONS_DURATION);
        translation.setFillAfter(true);
        if (listener != null) {
            translation.setAnimationListener(listener);
        }
        return translation;
    }

    // Initial Logo Animation in Login Screen from Splash Screen section

    protected Animation logoScaleAnimation(float scale, AnimationListener listener) {
        return scaleAnimation(1f, scale, listener, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f);
    }

    protected Animation initialLogoAnimation(float newY, float newScale) {
        AnimationSet animations = new AnimationSet(true);
        animations.setInterpolator(getActivity(), android.R.interpolator.accelerate_decelerate);
        animations.addAnimation(logoScaleAnimation(newScale, null));
        animations.addAnimation(verticalTranslationAnimation(0, newY, null));
        animations.setFillAfter(true);
        return animations;
    }

    protected Animation scaleFromCenterAnimation(Animation.AnimationListener listener) {
        Animation scaleAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_from_center);
        scaleAnimation.setDuration(ANIMATIONS_DURATION / 2);
        scaleAnimation.setFillAfter(true);
        if (listener != null) {
            scaleAnimation.setAnimationListener(listener);
        }
        return scaleAnimation;
    }

    // Animation for slide-in slide-out effects of initial screens

    protected Animation customTranslateAnimation(View view, OutInAnimDirection direction) {
        TranslateAnimation translateAnimation = getInOutTranslateAnimation(view, direction);
        translateAnimation.setInterpolator(getActivity(), android.R.interpolator.accelerate_decelerate);
        translateAnimation.setDuration(ANIMATIONS_DURATION);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }


    protected Animation customTranslateAnimation(StraightAnimDirection direction, int distance) {
        TranslateAnimation translateAnimation = getStraightTranslateAnimation(direction, distance);
        translateAnimation.setInterpolator(getActivity(), android.R.interpolator.accelerate_decelerate);
        translateAnimation.setDuration(ANIMATIONS_DURATION);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }

    // Helper functions

    protected int getScreenHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    protected int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
    
    private TranslateAnimation getStraightTranslateAnimation(StraightAnimDirection direction, int distance) {
        float xf = 0, yf = 0;

        switch (direction) {
            case RIGHT:
                xf = distance;
                break;
            case LEFT:
                xf -= distance;
                break;
            case UP:
                yf -= distance;
                break;
            case DOWN:
                yf = distance ;
                break;
            default: break;
        }
        return new TranslateAnimation(0, xf, 0, yf);
    }

    private TranslateAnimation getInOutTranslateAnimation(View view, OutInAnimDirection direction) {
        float xi = 0, xf = 0, yi = 0, yf = 0;
        int screenHeight = getScreenHeight();
        int screenWidth = getScreenWidth();

        switch (direction) {
            case IN_RIGHT:
                xi -= (view.getX() + view.getWidth());
                break;
            case IN_LEFT:
                xi = (screenWidth - view.getX()) + view.getWidth();
                break;
            case OUT_RIGHT:
                xf = (screenWidth - view.getX()) + view.getWidth();
                break;
            case OUT_LEFT:
                xf -= (view.getX() + view.getWidth());
                break;
            case IN_UP:
                yi = (screenHeight - view.getY()) + view.getHeight();
                break;
            case IN_DOWN:
                yi -= (view.getY() + view.getHeight()) ;
                break;
            case OUT_UP:
                yf -= (view.getY() + view.getHeight());
                break;
            case OUT_DOWN:
                yf = (screenHeight - view.getY()) + view.getHeight();
                break;
            default: break;
        }
        return new TranslateAnimation(xi, xf, yi, yf);
    }

}

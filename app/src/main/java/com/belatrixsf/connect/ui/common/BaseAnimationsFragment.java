package com.belatrixsf.connect.ui.common;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.belatrixsf.connect.R;

/**
 * Created by educhuks on 9/14/17.
 */

public abstract class BaseAnimationsFragment extends BelatrixConnectFragment {

    public enum AnimationDirection {
        SHOW_DOWN,
        SHOW_UP,
        HIDE_UP,
        HIDE_DOWN
    }

    public static final int ANIMATIONS_DURATION = 500;
    public static final int WAIT_DURATION = 100;

    protected int getScreenHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    private TranslateAnimation getTranslateAnimationByDirection(AnimationDirection direction, View view) {
        float yi = 0, yf = 0;
        int screenHeight = getScreenHeight();

        switch (direction) {
            case SHOW_DOWN:
                yi -= (view.getY() + view.getHeight()) ;
                break;
            case SHOW_UP:
                yi = (screenHeight - view.getY()) + view.getHeight();
                break;
            case HIDE_UP:
                yf -= (view.getY() + view.getHeight());
                break;
            case HIDE_DOWN:
                yf = (screenHeight - view.getY()) + view.getHeight();
                break;
            default: break;
        }

        return new TranslateAnimation(0, 0, yi, yf);
    }

    protected Animation scaleFromCenterAnimation(Animation.AnimationListener listener) {
        Animation scaleAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_from_center);
        scaleAnimation.setDuration(ANIMATIONS_DURATION / 2);
        if (listener != null) {
            scaleAnimation.setAnimationListener(listener);
        }
        return scaleAnimation;
    }

    protected Animation squareScaleAnimation(float scale, Animation.AnimationListener listener) {
        return squareScaleAnimation(scale, 1f, listener);
    }

    protected Animation squareScaleAnimation(float fromScale, float toScale, Animation.AnimationListener listener) {
        Animation scaleAnimation = new ScaleAnimation(fromScale, toScale, fromScale, toScale, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        scaleAnimation.setDuration(ANIMATIONS_DURATION);
        if (listener != null) {
            scaleAnimation.setAnimationListener(listener);
        }
        return scaleAnimation;
    }

    protected Animation verticalTranslationAnimation(float initialY, Animation.AnimationListener listener) {
        return verticalTranslationAnimation(initialY, 0, listener); // moves to original position
    }

    protected Animation verticalTranslationAnimation(float initialY, float finalY, Animation.AnimationListener listener) {
        TranslateAnimation translation = new TranslateAnimation(0, 0, initialY, finalY);
        translation.setDuration(ANIMATIONS_DURATION);
        translation.setFillAfter(true);
        if (listener != null) {
            translation.setAnimationListener(listener);
        }
        return translation;
    }

    protected void animateViewVerticalOutInTranslation(View view, AnimationDirection direction, AnimationListener listener) {
        TranslateAnimation translation = getTranslateAnimationByDirection(direction, view);
        translation.setDuration(ANIMATIONS_DURATION);
        translation.setInterpolator(getActivity(), android.R.interpolator.accelerate_decelerate);
        translation.setFillAfter(true);
        if (listener != null) {
            translation.setAnimationListener(listener);
        }
        view.startAnimation(translation);
    }

}

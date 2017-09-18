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

    private int getScreenHeight() {
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

    protected void animateViewScaleFromCenter(View view, AnimationListener listener) {
        view.setVisibility(View.VISIBLE);
        Animation scaleAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_from_center);
        scaleAnimation.setDuration(ANIMATIONS_DURATION / 2);
        if (listener != null) {
            scaleAnimation.setAnimationListener(listener);
        }
        view.startAnimation(scaleAnimation);
    }

    protected void animateViewSquareScale(View view, float scale, AnimationListener listener) {
        animateViewSquareScale(view, 1f, scale, listener);
    }

    protected void animateViewSquareScale(View view, float fromScale, float toScale, AnimationListener listener) {
        Animation scaleAnimation = new ScaleAnimation(fromScale, toScale, fromScale, toScale);
        scaleAnimation.setDuration(ANIMATIONS_DURATION);
        if (listener != null) {
            scaleAnimation.setAnimationListener(listener);
        }
        view.startAnimation(scaleAnimation);
    }

    protected void animateViewVerticalTranslation(View view, float initialY, AnimationListener listener) {
        animateViewVerticalTranslation(view, initialY, 0, listener); // moves to original position
    }

    protected void animateViewVerticalTranslation(View view, float initialY, float finalY, AnimationListener listener) {
        TranslateAnimation translation = new TranslateAnimation(0, 0, initialY, finalY);
        translation.setDuration(ANIMATIONS_DURATION);
        translation.setInterpolator(getActivity(), android.R.interpolator.accelerate_decelerate);
        translation.setFillAfter(true);
        if (listener != null) {
            translation.setAnimationListener(listener);
        }
        view.startAnimation(translation);
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

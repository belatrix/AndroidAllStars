package com.belatrixsf.connect.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import com.belatrixsf.connect.R;

/**
 * Created by echuquilin on 9/21/17.
 */
public class AnimationsUtils {

    public static final int ANIMATIONS_DURATION = 400;
    public static final int OUT_IN_DURATION = 350;
    public static final int BOUNCE_DURATION = 200;
    public static final int BOUNCE_DISTANCE = 15;
    public static final int WAIT_DURATION = 100;

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

    private static int getScreenHeight(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    private static int getScreenWidth(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static Animation scaleAnimation(float scale, Animation.AnimationListener listener) {
        return scaleAnimation(1f, scale, listener, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
    }

    public static Animation scaleAnimation(float fromScale, float toScale, Animation.AnimationListener listener,
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

    public static Animation verticalTranslationAnimation(float initialY, Animation.AnimationListener listener) {
        return verticalTranslationAnimation(initialY, 0, listener); // moves to original position
    }

    public static Animation verticalTranslationAnimation(float initialY, float finalY, Animation.AnimationListener listener) {
        TranslateAnimation translation = new TranslateAnimation(0, 0, initialY, finalY);
        translation.setDuration(ANIMATIONS_DURATION);
        translation.setFillAfter(true);
        if (listener != null) {
            translation.setAnimationListener(listener);
        }
        return translation;
    }

    // Initial Logo Animation in Login Screen from Splash Screen section

    public static Animation logoScaleAnimation(float scale, Animation.AnimationListener listener) {
        return scaleAnimation(1f, scale, listener, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f);
    }

    public static Animation moveLogoAnimation(Context context, StraightAnimDirection direction, float distance, float newScale) {
        AnimationSet animations = new AnimationSet(true);
        animations.setInterpolator(context, android.R.interpolator.accelerate_decelerate);
        animations.addAnimation(logoScaleAnimation(newScale, null));
        animations.addAnimation(customTranslateAnimation(context, direction, (int) distance));
        animations.setFillAfter(true);
        return animations;
    }

    public static Animation scaleCenterAnimation(Context context, Animation.AnimationListener listener, boolean fromCenter) {
        Animation scaleAnimation = fromCenter ? AnimationUtils.loadAnimation(context, R.anim.scale_from_center) :
                                                AnimationUtils.loadAnimation(context, R.anim.scale_to_center);
        scaleAnimation.setDuration(ANIMATIONS_DURATION / 2);
        scaleAnimation.setFillAfter(true);
        if (listener != null) {
            scaleAnimation.setAnimationListener(listener);
        }
        return scaleAnimation;
    }

    // Animation for slide-in slide-out effects of initial screens

    public static Animation customTranslateAnimation(Context context, View view, OutInAnimDirection direction) {
        Animation translateAnimation = getInOutTranslateAnimation(context, view, direction);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }


    public static Animation customTranslateAnimation(Context context, StraightAnimDirection direction, int distance) {
        Animation translateAnimation = getStraightTranslateAnimation(direction, distance);
        translateAnimation.setInterpolator(context, android.R.interpolator.accelerate_decelerate);
        translateAnimation.setDuration(ANIMATIONS_DURATION);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }

    private static Animation getStraightTranslateAnimation(StraightAnimDirection direction, int distance) {
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

    private static Animation getInOutTranslateAnimation(Context context, View view, OutInAnimDirection direction) {
        float xi = 0, xf = 0, yi = 0, yf = 0;
        float xbi = 0, xbf = 0, ybi = 0, ybf = 0;
        int screenHeight = getScreenHeight((Activity) context);
        int screenWidth = getScreenWidth((Activity) context);
        boolean requireBounce = false;

        switch (direction) {
            case IN_RIGHT:
                requireBounce = true;
                xi -= (view.getX() + view.getWidth());
                xf = BOUNCE_DISTANCE;
                xbi = BOUNCE_DISTANCE;
                xbf -= BOUNCE_DISTANCE;
                break;
            case IN_LEFT:
                requireBounce = true;
                xi = (screenWidth - view.getX()) + view.getWidth();
                xf -= BOUNCE_DISTANCE;
                xbi -= BOUNCE_DISTANCE;
                xbf = BOUNCE_DISTANCE;
                break;
            case OUT_RIGHT:
                xf = (screenWidth - view.getX()) + view.getWidth();
                break;
            case OUT_LEFT:
                xf -= (view.getX() + view.getWidth());
                break;
            case IN_UP:
                requireBounce = true;
                yi = (screenHeight - view.getY()) + view.getHeight();
                yf -= BOUNCE_DISTANCE;
                ybi -= BOUNCE_DISTANCE;
                ybf = BOUNCE_DISTANCE;
                break;
            case IN_DOWN:
                requireBounce = true;
                yi -= (view.getY() + view.getHeight()) ;
                yf = BOUNCE_DISTANCE;
                ybi = BOUNCE_DISTANCE;
                ybf -= BOUNCE_DISTANCE;
                break;
            case OUT_UP:
                yf -= (view.getY() + view.getHeight());
                break;
            case OUT_DOWN:
                yf = (screenHeight - view.getY()) + view.getHeight();
                break;
            default: break;
        }

        AnimationSet animationSet = new AnimationSet(false);

        TranslateAnimation translateAnimation = new TranslateAnimation(xi, xf, yi, yf);
        translateAnimation.setDuration(OUT_IN_DURATION);
        animationSet.addAnimation(translateAnimation);

        if (requireBounce) {
            TranslateAnimation bounceAnimation = new TranslateAnimation(xbi, xbf, ybi, ybf);
            bounceAnimation.setDuration(BOUNCE_DURATION);
            bounceAnimation.setStartOffset(OUT_IN_DURATION);
            bounceAnimation.setInterpolator(new OvershootInterpolator(1.0f));
            animationSet.addAnimation(bounceAnimation);
        }

        return animationSet;
    }

}

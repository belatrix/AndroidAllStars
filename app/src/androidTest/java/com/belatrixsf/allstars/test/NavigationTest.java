package com.belatrixsf.allstars.test;

import android.support.test.rule.ActivityTestRule;

import com.belatrixsf.allstars.ui.login.LoginActivity;

import org.junit.Rule;
import org.junit.Test;

/**
 * Created by joyep on 5/25/16.
 */

public class NavigationTest extends LoginTest{
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);
    @Test
    public void navigation(){

    }
}

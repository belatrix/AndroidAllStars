package com.belatrixsf.allstars.test;

import android.support.test.rule.ActivityTestRule;

import com.belatrixsf.allstars.*;
import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.testFunction.TestFunction;
import com.belatrixsf.allstars.ui.login.LoginActivity;
import com.belatrixsf.allstars.util.Constants;

import org.junit.Rule;
import org.junit.Test;

/**
 * Created by joyep on 5/25/16.
 */

public class NewUserTest extends TestFunction{
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);
    @Test
    public void NewUser(){
        findIdAndTap(R.id.sign_up);
        findIdAndWrite(R.id.email, Constants.EMAIL);
        findIdAndTap(R.id.send);
    }
}

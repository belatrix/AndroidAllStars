package com.belatrixsf.allstars.test;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.ui.login.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by gcuzcano on 5/25/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecommendTest extends LoginTest{

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void RecommedActionTest(){
        loginTest();
        findIdAndTap(R.id.start_recommendation);
        checkIfElementIsDisplayedWithText("Give a recommendation");
        findTextAndTap("Select user");
        checkIfElementIsDisplayedWithText("Select an user");
        findTextAndTap("amy noe");
        checkIfMatches(R.id.contact_full_name,R.id.contact_full_name);
    }


}

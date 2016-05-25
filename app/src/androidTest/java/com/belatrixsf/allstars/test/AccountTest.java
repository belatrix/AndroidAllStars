package com.belatrixsf.allstars.test;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.ui.login.LoginActivity;
import com.belatrixsf.allstars.util.Constants;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by joyep on 5/24/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest

public class AccountTest extends LoginTest{
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);
    @Test
    public void accountViewTest() {
        loginTest();
        checktWithIdAndText(R.id.profile_name,Constants.FULLNAME);
        checkIfElementIsDisplayed(R.id.profile_email);
        checkIfElementIsDisplayed(R.id.skype_id);
        checkIfElementIsDisplayed(R.id.location_name);
        checkIfElementIsDisplayed(R.id.current_month_score);
        checkIfElementIsDisplayed(R.id.start_recommendation);
        checkIfElementIsDisplayed(R.id.score);
        checkIfElementIsDisplayed(R.id.level);
        findIdAndTap(R.id.start_recommendation);
    }
}

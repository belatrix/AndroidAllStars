package com.belatrixsf.allstars.test;

/**
 * Created by joyep on 5/23/16.
 */


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import com.belatrixsf.allstars.*;
import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.TestFunction.TestFunction;
import com.belatrixsf.allstars.ui.home.MainActivity;
import com.belatrixsf.allstars.ui.login.LoginActivity;
import com.belatrixsf.allstars.util.Constants;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest extends TestFunction{

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    @Test
    public void loginTest() {

        findIdAndWrite(R.id.username,Constants.USERNAME);
        findIdAndWrite(R.id.password,Constants.PASSWORD);
        findIdAndTap(R.id.log_in);

        onView(ViewMatchers.withId(R.id.profile_name)).check(matches(withText(Constants.FULLNAME)));

        onView(withText(Constants.RANKING)).perform(click());
        onView(withText(Constants.CONTACTS)).perform(click());
        onView(withText(Constants.KEYWORDS)).perform(click());
        onView(withText(Constants.ACCOUNT)).perform(click());
    }

    @Test
    public void logoutTest(){
        loginTest();
        tapOnOverFlowMenu();
        findNameAndTap(R.string.menu_item_logout);
        findNameAndTap(R.string.dialog_option_positive);
        onView(ViewMatchers.withId(R.id.login)).check(matches(isDisplayed()));
    }

}

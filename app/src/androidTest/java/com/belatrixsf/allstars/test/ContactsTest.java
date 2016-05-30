package com.belatrixsf.allstars.test;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.belatrixsf.allstars.testFunction.TestFunction;
import com.belatrixsf.allstars.ui.login.LoginActivity;
import com.belatrixsf.allstars.util.Constants;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.belatrixsf.allstars.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by lbarzola on 5/24/16.
 */


public class ContactsTest extends LoginTest{
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    @Test
    public void getContactTest() {
        loginTest();

        onView(withText(Constants.CONTACTS)).perform(click());
        onView(withId(R.id.employees)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }
}

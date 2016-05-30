package com.belatrixsf.allstars.testFunction;

/**
 * Created by joyep on 5/24/16.
 */

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.util.Constants;
import com.belatrixsf.allstars.util.ViewActionUtils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;



public class TestFunction extends ViewActionUtils {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    public void findIdAndWrite(int id, String keyName) {
        onView(isRoot()).perform(waitId(id, Constants.MILISECONDS));
        onView(ViewMatchers.withId(id))
                .perform(typeText(keyName), closeSoftKeyboard());
    }

    public void findIdAndWriteDoneEvent(int id, String keyName) {
        onView(isRoot()).perform(waitId(id, Constants.MILISECONDS));
        onView(ViewMatchers.withId(id))
                .perform(typeText(keyName), pressImeActionButton());
    }

    public void findIdAndTap(int id) {
        onView(isRoot()).perform(waitId(id, Constants.MILISECONDS));
        onView(ViewMatchers.withId(id)).perform(click());
    }

    public void tapOnOverFlowMenu() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
    }

    public void findNameAndTap(int id) {
        onView(isRoot()).perform(waitId(id, Constants.MILISECONDS));
        onView(ViewMatchers.withText(id)).perform(click());
    }

    public void findTextAndTap(String keyName) {
        onView(isRoot()).perform(waitString(keyName, Constants.MILISECONDS));
        onView(ViewMatchers.withText(keyName)).perform(click());
    }

    public void checktWithIdAndText(int id, String keyName) {
        onView(isRoot()).perform(waitId(id, Constants.MILISECONDS));
        onView(ViewMatchers.withId(id)).check(matches(withText(keyName)));
    }

    public void checkIfElementIsDisplayed(int id) {
        onView(isRoot()).perform(waitId(id, Constants.MILISECONDS));
        onView(ViewMatchers.withId(id)).check(matches(isDisplayed()));
    }

    public void checkIfElementIsDisplayedWithText(String keyName) {
        onView(ViewMatchers.withText(keyName)).check(matches(isDisplayed()));
    }

    public void checkIfElementIsEnabled(int id) {
        onView(isRoot()).perform(waitId(id, Constants.MILISECONDS));
        onView(ViewMatchers.withId(id)).check(matches(isEnabled()));
    }

    public void checkIfElementIsChecked(int id) {
        onView(isRoot()).perform(waitId(id, Constants.MILISECONDS));
        onView(ViewMatchers.withId(id)).check(matches(isChecked()));
    }

    public void checkIfElementIsClickable(int id) {
        onView(isRoot()).perform(waitId(id, Constants.MILISECONDS));
        onView(ViewMatchers.withId(id)).check(matches(isClickable()));
    }

    public void checkIfMatches(int idFirst, int idSecond) {
        onView(isRoot()).perform(waitId(idSecond, Constants.MILISECONDS));
        onView(ViewMatchers.withId(idFirst)).check(matches(ViewMatchers.withId(idSecond)));
    }
    public void findItemOnRecyclerViewAndTap(int id, int position) {
        onView(isRoot()).perform(waitId(id, Constants.MILISECONDS));
        onView(withId(id)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }
}


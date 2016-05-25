package com.belatrixsf.allstars.TestFunction;

/**
 * Created by joyep on 5/24/16.
 */

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;

import com.belatrixsf.allstars.util.ViewActionUtils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


public class TestFunction extends ViewActionUtils{
    public void findIdAndWrite(int id, String keyName){
        onView(ViewMatchers.withId(id))
         .perform(typeText(keyName), closeSoftKeyboard());
    }
    public void findIdAndTap(int id){
        onView(ViewMatchers.withId(id)).perform(click());
    }
    public void tapOnOverFlowMenu(){
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
    }
    public void findNameAndTap(int id){
        onView(ViewMatchers.withText(id)).perform(click());
    }
    public void findTextAndTap(String keyName){
        onView(ViewMatchers.withText(keyName)).perform(click());
    }
    public void checktWithIdAndText(int id, String keyName){
        onView(isRoot()).perform(waitId(id, 6000));
        onView(ViewMatchers.withId(id)).check(matches(withText(keyName)));
    }
    public void checkIfElementIsDisplayed(int id){
        onView(isRoot()).perform(waitId(id, 6000));
        onView(ViewMatchers.withId(id)).check(matches(isDisplayed()));
    }
    public void checkIfElementIsDisplayedWithText(String keyName){
        onView(ViewMatchers.withText(keyName)).check(matches(isDisplayed()));
    }
    public void checkIfElementIsEnabled(int id){
        onView(ViewMatchers.withId(id)).check(matches(isEnabled()));
    }
    public void checkIfElementIsChecked(int id){
        onView(ViewMatchers.withId(id)).check(matches(isChecked()));
    }
    public void checkIfElementIsClickable(int id) {
        onView(ViewMatchers.withId(id)).check(matches(isClickable()));
    }
}

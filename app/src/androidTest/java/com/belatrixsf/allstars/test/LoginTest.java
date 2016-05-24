package com.belatrixsf.allstars.test;

/**
 * Created by joyep on 5/23/16.
 */


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.TestFunction.TestFunction;
import com.belatrixsf.allstars.ui.LauncherActivity;
import com.belatrixsf.allstars.util.Constants;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest extends TestFunction{

    @Rule
    public ActivityTestRule<LauncherActivity> mActivityRule = new ActivityTestRule<>(
            LauncherActivity.class);

    @Test
    public void loginTest() {
        findIdAndWrite(R.id.username,Constants.USERNAME);
        findIdAndWrite(R.id.password,Constants.PASSWORD);
        findIdAndTap(R.id.log_in);
        checktWithIdAndText(R.id.profile_name,Constants.FULLNAME);
    }

    @Test
    public void logoutTest(){
        loginTest();
        tapOnOverFlowMenu();
        findNameAndTap(R.string.menu_item_logout);
        findNameAndTap(R.string.dialog_option_positive);
        checkIfElementIsDisplayed(R.id.login);
    }

}

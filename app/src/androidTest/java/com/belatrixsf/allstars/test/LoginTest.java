package com.belatrixsf.allstars.test;

/**
 * Created by joyep on 5/23/16.
 */


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.testFunction.TestFunction;
import com.belatrixsf.allstars.ui.LauncherActivity;
import com.belatrixsf.allstars.ui.home.MainActivity;
import com.belatrixsf.allstars.ui.login.LoginActivity;
import com.belatrixsf.allstars.util.Constants;


@RunWith(AndroidJUnit4.class)
public class LoginTest extends TestFunction{

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    @Test
    public void loginTest() {
        findIdAndWrite(R.id.username,Constants.USERNAME);
        findIdAndWrite(R.id.password,Constants.PASSWORD);
        findIdAndTap(R.id.log_in);
        checktWithIdAndText(R.id.profile_name,Constants.FULLNAME);
    }

    @Test
    public void loginTestEnterFromKeyboard() {
        findIdAndWrite(R.id.username,Constants.USERNAME);
        findIdAndWriteDoneEvent(R.id.password,Constants.PASSWORD);
    }

    @Test
    public void logoutTest(){
        loginTest();
        tapOnOverFlowMenu();
        findTextAndTap("Logout");
        findTextAndTap("Yes");
        checkIfElementIsDisplayed(R.id.login);
    }

}

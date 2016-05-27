/* The MIT License (MIT)
* Copyright (c) 2016 BELATRIX
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:

* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.

* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/

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

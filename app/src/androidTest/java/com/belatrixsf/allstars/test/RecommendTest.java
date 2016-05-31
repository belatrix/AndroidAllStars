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

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.testFunction.TestFunction;
import com.belatrixsf.allstars.ui.login.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by gcuzcano on 5/25/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecommendTest extends TestFunction{

    LoginTest loginTest = new LoginTest();

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void RecommendUiTest(){
        loginTest.loginTest();
        findIdAndTap(R.id.start_recommendation);
        Context activityContext = mActivityRule.getActivity();
        checkIfElementIsDisplayedWithText(activityContext.getResources().getString(R.string.title_give_star_activity)); //Give a recommendation
        checkIfElementIsDisplayedWithText(activityContext.getResources().getString(R.string.select_user)); //Select user
        checkIfElementIsDisplayedWithText(activityContext.getResources().getString(R.string.hint_write_comment)); //Write a comment
        checkIfElementIsDisplayedWithText(activityContext.getResources().getString(R.string.select_category)); //Select category
        checkIfElementIsDisplayedWithText(activityContext.getResources().getString(R.string.hint_keyword)); //Select a Tag
    }

    @Test
    public void RecommendActionTest(){
        loginTest.loginTest();
        findIdAndTap(R.id.start_recommendation);
        Context activityContext = mActivityRule.getActivity();
        findTextAndTap(activityContext.getResources().getString(R.string.select_user));
        findItemOnRecyclerViewAndTap(R.id.employees,3);
        findTextAndTap(activityContext.getResources().getString(R.string.select_category));
        findItemOnRecyclerViewAndTap(R.id.categories,0);
        findItemOnRecyclerViewAndTap(R.id.categories,0);
        findTextAndTap(activityContext.getResources().getString(R.string.hint_keyword));
        findItemOnRecyclerViewAndTap(R.id.keywords,0);
        findIdAndTap(R.id.action_done);
        checkIfElementIsDisplayedWithText(activityContext.getResources().getString(R.string.success_recommendation));
        findTextAndTap(activityContext.getResources().getString(R.string.dialog_option_confirm));
    }

}
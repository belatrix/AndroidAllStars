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
package com.belatrixsf.connect.ui.contacts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.account.AccountActivity;
import com.belatrixsf.connect.ui.common.BelatrixConnectActivity;
import com.belatrixsf.connect.ui.home.UserActivity;

/**
 * Created by pedrocarrillo on 4/26/16.
 */
public class ContactsListActivity extends BelatrixConnectActivity {

    public static final String PROFILE_ENABLED_KEY = "_is_search";
    public static final int PARENT_INDEX = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setNavigationToolbar();
        if (savedInstanceState == null) {
            boolean profileEnabled = getIntent().getBooleanExtra(PROFILE_ENABLED_KEY, true);
            replaceFragment(ContactsListFragment.newInstance(profileEnabled), false);
        }
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, ContactsListActivity.class);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AccountActivity.PARENT_ACTIVITY_INDEX = UserActivity.PARENT_INDEX;
    }

}
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
package com.belatrixsf.connect.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.belatrixsf.connect.managers.PreferencesManager;
import com.belatrixsf.connect.ui.home.GuestActivity;
import com.belatrixsf.connect.ui.home.UserActivity;
import com.belatrixsf.connect.ui.login.LoginActivity;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean userHasPermission = PreferencesManager.get().getEmployeeId() != 0 && PreferencesManager.get().getToken() != null && PreferencesManager.get().isResetPassword() && PreferencesManager.get().isEditProfile();
        if (userHasPermission) {
            //Restore Employee session
            startActivity(UserActivity.makeIntent(this));
        } else {
            PreferencesManager.get().clearUserSession();

            boolean guestHasPermission = PreferencesManager.get().getGuestId() != 0;
            if (guestHasPermission) {
                //Restore Guest session
                startActivity(GuestActivity.makeIntent(this));
            } else {
                PreferencesManager.get().clearGuestSession();
                startActivity(LoginActivity.makeIntent(this));
                //startActivity(WizardMainActivity.makeIntent(this));
            }
        }
        overridePendingTransition(0, 0);
        finish();
    }

}

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
            PreferencesManager.get().clearEmployeeId();
            PreferencesManager.get().clearToken();
            PreferencesManager.get().clearResetPassword();
            PreferencesManager.get().clearEditProfile();

            boolean guestHasPermission = PreferencesManager.get().getGuestId() != 0;
            if (guestHasPermission) {
                //Restore Guest session
                startActivity(GuestActivity.makeIntent(this));
            } else {
                startActivity(LoginActivity.makeIntent(this));
            }
        }
        finish();
    }

}

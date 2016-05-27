package com.belatrixsf.allstars.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.belatrixsf.allstars.managers.PreferencesManager;
import com.belatrixsf.allstars.ui.home.MainActivity;
import com.belatrixsf.allstars.ui.login.LogInActivity;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean userHasPermission = PreferencesManager.get().getEmployeeId() != 0 && PreferencesManager.get().getToken() != null && PreferencesManager.get().isResetPassword() && PreferencesManager.get().isEditProfile();
        if (userHasPermission) {
            startActivity(MainActivity.makeIntent(this));
        } else {
            PreferencesManager.get().clearEmployeeId();
            PreferencesManager.get().clearToken();
            PreferencesManager.get().clearResetPassword();
            PreferencesManager.get().clearEditProfile();
            startActivity(LogInActivity.makeIntent(this));
        }
        finish();
    }

}

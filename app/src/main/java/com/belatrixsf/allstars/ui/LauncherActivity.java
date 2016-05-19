package com.belatrixsf.allstars.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.belatrixsf.allstars.managers.PreferencesManager;
import com.belatrixsf.allstars.ui.home.MainActivity;
import com.belatrixsf.allstars.ui.login.LoginActivity;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean userHasPermission = PreferencesManager.get().getEmployeeId() != 0 && PreferencesManager.get().getToken() != null;
        startActivity(userHasPermission? MainActivity.makeIntent(this) : LoginActivity.makeIntent(this));
        finish();
    }

}

package com.belatrixsf.connect.ui.settings;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.common.BelatrixConnectActivity;

/**
 * Created by echuquilin on 6/07/16.
 */
public class SettingsActivity extends BelatrixConnectActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //set default settings
        PreferenceManager.setDefaultValues(this, R.xml.fragment_settings, false);

        //display the fragment of settings
        getFragmentManager().beginTransaction()
                .replace(R.id.main_content, new SettingsFragment())
                .commit();

        setNavigationToolbar();
    }

}

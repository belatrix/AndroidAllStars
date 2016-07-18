package com.belatrixsf.connect.ui.settings;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.MenuItem;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

package com.belatrixsf.connect.ui.settings;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.belatrixsf.connect.R;

/**
 * Created by echuquilin on 6/07/16.
 */
public class SettingsActivity extends AppCompatActivity {

    Toolbar settingsToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsToolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        settingsToolbar.setTitle(R.string.settings_main_title);
        setSupportActionBar(settingsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set default settings
        PreferenceManager.setDefaultValues(this, R.xml.fragment_settings, false);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(R.id.settings_content, new SettingsFragment())
                .commit();
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

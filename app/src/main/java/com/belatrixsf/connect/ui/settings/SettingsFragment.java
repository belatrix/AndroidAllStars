package com.belatrixsf.connect.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.managers.PreferencesManager;

/**
 * Created by echuquilin on 6/07/16.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String NOTIFICATIONS_ENABLED_KEY = "settings_key_notifications_switch";

    private static SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.fragment_settings);

        sharedPreferences = PreferencesManager.get().getSharedPreferences();
        restorePreviusSettings();
    }

    private void restorePreviusSettings() {
        if (sharedPreferences.contains(NOTIFICATIONS_ENABLED_KEY)) {
            Preference notificationEnabledPref = findPreference(NOTIFICATIONS_ENABLED_KEY);
            notificationEnabledPref.setDefaultValue(sharedPreferences.getBoolean(NOTIFICATIONS_ENABLED_KEY, true));
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(NOTIFICATIONS_ENABLED_KEY)) {
            sharedPreferences.edit().putBoolean(NOTIFICATIONS_ENABLED_KEY,sharedPreferences.getBoolean(NOTIFICATIONS_ENABLED_KEY,true));
        }
    }
}

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
package com.belatrixsf.connect.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.belatrixsf.connect.utils.BelatrixConnectApplication;

/**
 * Created by gyosida on 4/12/16.
 */
public class PreferencesManager {

    private static final String TOKEN_KEY = "_token_key";
    private static final String EMPLOYEE_ID_KEY = "_employee_id_key";
    private static final String RESET_PASSWORD_KEY = "_reset_password_key";
    private static final String EDIT_PROFILE_KEY = "_edit_profile_key";
    private static final String GUEST_ID_KEY = "_guest_id_key";
    private static final String DEVICE_TOKEN_ID = "_device_token_id";
    private static final String NOTIFICATIONS_ENABLED_KEY = "_settings_notifications_switch_key";
    private static PreferencesManager preferencesManager;

    private SharedPreferences sharedPreferences;

    private PreferencesManager() {
        sharedPreferences = BelatrixConnectApplication.getContext().getSharedPreferences("all_stars_pref", Context.MODE_PRIVATE);
    }

    public static synchronized PreferencesManager get() {
        if (preferencesManager == null) {
            preferencesManager = new PreferencesManager();
        }
        return preferencesManager;
    }

    public void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

    public void clearToken() {
        sharedPreferences.edit().remove(TOKEN_KEY).apply();
    }

    public String getToken() {
        return sharedPreferences.getString(TOKEN_KEY, null);
    }

    public void saveEmployeeId(int employeeId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(EMPLOYEE_ID_KEY, employeeId);
        editor.apply();
    }

    private void clearEmployeeId() {
        sharedPreferences.edit().remove(EMPLOYEE_ID_KEY).apply();
    }

    public int getEmployeeId() {
        return sharedPreferences.getInt(EMPLOYEE_ID_KEY, 0);
    }

    public void setResetPassword(boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(RESET_PASSWORD_KEY, value);
        editor.apply();
    }

    private void clearResetPassword() {
        sharedPreferences.edit().remove(RESET_PASSWORD_KEY).apply();
    }

    public boolean isResetPassword() {
        return sharedPreferences.getBoolean(RESET_PASSWORD_KEY, false);
    }

    public void setEditProfile(boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(EDIT_PROFILE_KEY, value);
        editor.apply();
    }

    private void clearEditProfile() {
        sharedPreferences.edit().remove(EDIT_PROFILE_KEY).apply();
    }

    public boolean isEditProfile() {
        return sharedPreferences.getBoolean(EDIT_PROFILE_KEY, false);
    }

    public void saveGuestId(int guestId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(GUEST_ID_KEY, guestId);
        editor.apply();
    }

    private void clearGuestId() {
        sharedPreferences.edit().remove(GUEST_ID_KEY).apply();
    }

    public int getGuestId() {
        return sharedPreferences.getInt(GUEST_ID_KEY, 0);
    }

    private void clearDeviceToken() {
        sharedPreferences.edit().remove(DEVICE_TOKEN_ID).apply();
    }

    public String getDeviceToken() {
        return sharedPreferences.getString(DEVICE_TOKEN_ID, null);
    }

    public void saveDeviceToken(String deviceToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DEVICE_TOKEN_ID, deviceToken);
        editor.apply();
    }

    public boolean isNotificationEnabled() {
        return sharedPreferences.getBoolean(NOTIFICATIONS_ENABLED_KEY, true);
    }

    public String getNotificationKey(){
        return  NOTIFICATIONS_ENABLED_KEY;
    }

    public void setNotificationsChanged(boolean value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(NOTIFICATIONS_ENABLED_KEY, value);
        editor.apply();
    }

    private void clearNotificationEnabled() {
        sharedPreferences.edit().remove(NOTIFICATIONS_ENABLED_KEY);
    }

    public void clearUserSession() {
        clearEmployeeId();
        clearDeviceToken();
        clearToken();
        clearEditProfile();
        clearResetPassword();
        clearNotificationEnabled();
    }

    public void clearGuestSession() {
        clearGuestId();
    }

}

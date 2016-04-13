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
package com.belatrixsf.allstars.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.belatrixsf.allstars.utils.AllStarsApplication;

/**
 * Created by gyosida on 4/12/16.
 */
public class PreferencesManager {

    private static final String TOKEN_KEY = "token_key";
    private static final String EMPLOYEE_ID_KEY = "employee_id_key";
    private static PreferencesManager preferencesManager;

    private SharedPreferences sharedPreferences;

    private PreferencesManager() {
        sharedPreferences = AllStarsApplication.getContext().getSharedPreferences("all_stars_pref", Context.MODE_PRIVATE);
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

    public String getToken() {
        return sharedPreferences.getString(TOKEN_KEY, null);
    }

    public void saveEmployeeId(int employeeId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(EMPLOYEE_ID_KEY, employeeId);
        editor.apply();
    }

    public int getEmployeeId() {
        return sharedPreferences.getInt(EMPLOYEE_ID_KEY, 0);
    }

}

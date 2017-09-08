package com.belatrixsf.connect.utils;

import android.text.TextUtils;

/**
 * Created by dvelasquez on 6/27/17.
 */

public class Utils {

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}

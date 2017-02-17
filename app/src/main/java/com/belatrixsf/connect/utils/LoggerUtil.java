package com.belatrixsf.connect.utils;

import android.util.Log;

import com.belatrixsf.connect.BuildConfig;

/**
 * Created by dvelasquez on 2/16/17.
 */

public class LoggerUtil {

    public static void log(String tag, Object text){
        if (BuildConfig.DEBUG){
            Log.d(tag, ""+text);
        }
    }
}

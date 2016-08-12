package com.belatrixsf.connect.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by echuquilin on 11/08/16.
 */
public class SnackbarUtils {

    public static Snackbar createInformationSnackBar(String message, String actionLabel, View view, View.OnClickListener positiveListener) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG)
                .setAction(actionLabel, positiveListener);

        return snackbar;
    }
}

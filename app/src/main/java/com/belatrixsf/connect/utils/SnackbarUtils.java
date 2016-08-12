package com.belatrixsf.connect.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.belatrixsf.connect.R;

/**
 * Created by echuquilin on 11/08/16.
 */
public class SnackbarUtils {

    public static Snackbar createInformationSnackBar(String message, View view, View.OnClickListener positiveListener) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG)
                .setAction(R.string.dialog_option_confirm, positiveListener);

        return snackbar;
    }
}

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
package com.belatrixsf.connect.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;

import com.belatrixsf.connect.R;

import static android.content.DialogInterface.OnClickListener;

/**
 * Created by gyosida on 4/14/16.
 */
public class DialogUtils {

    public static AlertDialog createErrorDialog(Activity activity, String message) {
        return createSimpleDialog(activity, BelatrixConnectApplication.getContext().getString(R.string.dialog_title_error), message);
    }

    public static AlertDialog createSimpleDialog(Activity activity, String title, String message) {
        return new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .create();
    }

    public static AlertDialog createInformationDialog(Activity activity, String message, String title, OnClickListener positiveListener) {
        return new AlertDialog.Builder(activity)
                .setTitle(title)
                .setPositiveButton(R.string.dialog_option_confirm, positiveListener)
                .setMessage(message)
                .setCancelable(false)
                .create();
    }

    public static AlertDialog createConfirmationDialog(Activity activity, String message, OnClickListener positiveListener, OnClickListener negativeListener) {
        return createConfirmationDialogWithTitle(activity,message,null,positiveListener,negativeListener);
    }

    public static AlertDialog createConfirmationDialogWithTitle(Activity activity,String title, String message, OnClickListener positiveListener, OnClickListener negativeListener) {
        return new AlertDialog.Builder(activity)
                .setPositiveButton(R.string.dialog_option_positive, positiveListener)
                .setNegativeButton(R.string.dialog_option_negative, negativeListener)
                .setMessage(message)
                .setTitle(title)
                .create();
    }

    public static ProgressDialog createProgressDialog(Activity activity, String message) {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

}

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
package com.belatrixsf.connect.ui.common;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.utils.DialogUtils;
import com.belatrixsf.connect.utils.SnackbarUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author PedroCarrillo
 */
public class BelatrixConnectActivity extends AppCompatActivity implements FragmentListener {

    private AlertDialog errorAlertDialog;
    private ProgressDialog progressDialog;
    @Nullable @Bind(R.id.toolbar) protected Toolbar toolbar;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        replaceFragment(R.id.main_content, fragment, addToBackStack);
    }

    @Override
    public void replaceFragment(int containerId, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        String tag = fragment.getClass().getSimpleName();
        transaction.replace(containerId, fragment, tag);
        if (addToBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void showError(String message) {
        if (errorAlertDialog == null) {
            errorAlertDialog = DialogUtils.createErrorDialog(this, null);
        }
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        if (!errorAlertDialog.isShowing()) {
            errorAlertDialog.setMessage(message);
            errorAlertDialog.show();
        }
    }

    @Override
    public void setTitle(String title) {
        if (!activityHandleTitle() && getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void showProgressDialog() {
        showProgressDialog(getString(R.string.dialog_message_loading));
    }

    @Override
    public void showProgressDialog(String message) {
        if (progressDialog == null) {
            progressDialog = DialogUtils.createProgressDialog(this, null);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }

    @Override
    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showSnackBar(String message) {
        if (toolbar != null) {
            SnackbarUtils.createInformationSnackBar(toolbar, message, null, Snackbar.LENGTH_LONG, null).show();
        }
    }

    @Override
    public void showSnackBar(View view, String message) {
        if (toolbar != null) {
            SnackbarUtils.createInformationSnackBar(view, message, null, Snackbar.LENGTH_LONG, null).show();
        }
    }

    @Override
    public void showSnackBar(View view, String message, String action, View.OnClickListener onClickListener) {
        if (toolbar != null) {
            SnackbarUtils.createInformationSnackBar(toolbar, message, action, Snackbar.LENGTH_LONG, onClickListener).show();
        }
    }

    @Override
    public void closeActivity() {
        finish();
    }

    @Override
    public void setActivityResult(int resultCode) {
        setResult(resultCode);
    }

    @Override
    public void setActivityResult(int resultCode, Intent resultIntent) {
        setResult(resultCode, resultIntent);
    }

    protected boolean activityHandleTitle() {
        return false;
    }

    protected void setToolbar() {
        setSupportActionBar(toolbar);
    }

    protected void setNavigationToolbar() {
        setToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateBack();
                }
            });
        }
    }

    @Override
    public void setToolbar(Toolbar customToolbar) {
        setSupportActionBar(customToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (customToolbar != null) {
            customToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    protected void navigateBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (isTaskRoot()){
            new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.app_name))
                    .setMessage(getResources().getString(R.string.dialog_confirmation_exit))
                    .setPositiveButton(getResources().getString(R.string.dialog_option_positive), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BelatrixConnectActivity.super.onBackPressed();
                        }
                    }).setNegativeButton(getResources().getString(R.string.dialog_option_negative), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).create().show();
        }else{
            super.onBackPressed();
        }
    }
}

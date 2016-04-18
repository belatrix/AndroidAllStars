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
package com.belatrixsf.allstars.ui.common;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.utils.Dialogs;

/**
 * @author PedroCarrillo
 */
public class AllStarsActivity extends AppCompatActivity implements FragmentListener {

    private AlertDialog errorAlertDialog;
    private ProgressDialog progressDialog;
    private SearchView searchView;
    private SearchView.OnQueryTextListener searchViewListener;

    @Override
    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        replaceFragment(R.id.main_content, fragment, addToBackStack);
    }

    @Override
    public void replaceFragment(int containerId, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        String tag = fragment.getClass().getSimpleName();
        transaction.replace(containerId, fragment, tag);
        if(addToBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void showError(String message) {
        if (errorAlertDialog == null) {
            errorAlertDialog = Dialogs.createErrorDialog(this, null);
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
    public void showProgressIndicator() {

    }

    @Override
    public void hideProgressIndicator() {

    }

    @Override
    public void showProgressDialog() {
        showProgressDialog(getString(R.string.dialog_message_loading));
    }

    @Override
    public void showProgressDialog(String message) {
        if (progressDialog == null) {
            progressDialog = Dialogs.createProgressDialog(this, null);
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
    public void closeActivity() {
        finish();
    }

    @Override
    public void setTitle() {

    }

    @Override
    public void setSearchView(SearchView searchView) {
        this.searchView = searchView;
    }

    @Override
    public SearchView getSearchView() {
        return this.searchView;
    }

    @Override
    public void setSearchViewListener(SearchView.OnQueryTextListener queryTextListener) {
        this.searchViewListener = queryTextListener;
    }

    @Override
    public SearchView.OnQueryTextListener getSearchViewListener() {
        return this.searchViewListener;
    }
}

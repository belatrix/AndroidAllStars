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

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.belatrixsf.connect.utils.BelatrixConnectApplication;

import butterknife.ButterKnife;

/**
 * @author PedroCarrillo
 * @author gyosida
 *         <p/>
 *         BelatrixConnectFragment will implement the BelatrixConnectView interface and manage
 *         common fragment stuff to avoid boilerplate code
 */
public abstract class BelatrixConnectFragment extends Fragment implements BelatrixConnectView {

    protected FragmentListener fragmentListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentListener = (FragmentListener) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        fragmentListener = (FragmentListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDependencies((BelatrixConnectApplication) getActivity().getApplication());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentListener = null;
    }

    public void showProgressIndicator() {

    }

    public void hideProgressIndicator() {
    }

    @Override
    public void showProgressDialog() {
        if (fragmentListener != null) {
            fragmentListener.showProgressDialog();
        }
    }

    @Override
    public void showProgressDialog(String message) {
        if (fragmentListener != null) {
            fragmentListener.showProgressDialog(message);
        }
    }

    @Override
    public void dismissProgressDialog() {
        if (fragmentListener != null) {
            fragmentListener.dismissProgressDialog();
        }
    }

    @Override
    public void showSnackBar(String message) {
        if (fragmentListener != null) {
            fragmentListener.showSnackBar(message);
        }
    }

    @Override
    public void showSnackBar(View view, String message) {
        if (fragmentListener != null) {
            fragmentListener.showSnackBar(view, message);
        }
    }

    @Override
    public void showSnackBar(View view, String message, String action, View.OnClickListener onClickListener) {
        if (fragmentListener != null) {
            fragmentListener.showSnackBar(view, message, action, onClickListener);
        }
    }

    @Override
    public void showError(String message) {
        if (fragmentListener != null) {
            fragmentListener.showError(message);
        }
    }

    @Override
    public void setTitle(String title) {
        if (fragmentListener != null) {
            fragmentListener.setTitle(title);
        }
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    protected abstract void initDependencies(BelatrixConnectApplication belatrixConnectApplication);


    protected void replaceChildFragment(Fragment fragment, int fragmentReplacedId) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        String tag = fragment.getClass().getSimpleName();
        transaction.replace(fragmentReplacedId, fragment, tag);
        transaction.commit();
    }

}

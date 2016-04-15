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

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.belatrixsf.allstars.utils.AllStarsApplication;

import butterknife.ButterKnife;

/**
 * @author PedroCarrillo
 * @author gyosida
 *
 * AllStarsFragment will implement the AllStarsView interface and manage
 * common fragment stuff to avoid boilerplate code
 */
public abstract class AllStarsFragment extends Fragment implements AllStarsView {

    protected static final String TAG = AllStarsFragment.class.getName();
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initDependencies((AllStarsApplication) getActivity().getApplication());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentListener = null;
    }

    @Override
    public void showProgressIndicator() {
        fragmentListener.showProgressIndicator();
    }

    @Override
    public void hideProgressIndicator() {
        fragmentListener.hideProgressIndicator();
    }

    @Override
    public void showProgressDialog() {
        fragmentListener.showProgressDialog();
    }

    @Override
    public void showProgressDialog(String message) {
        fragmentListener.showProgressDialog(message);
    }

    @Override
    public void dismissProgressDialog() {

    }

    @Override
    public void showError(String message) {
        fragmentListener.showError(message);
    }

    protected abstract void initDependencies(AllStarsApplication allStarsApplication);

}

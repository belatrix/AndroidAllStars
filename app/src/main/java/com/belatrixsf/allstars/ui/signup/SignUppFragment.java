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
package com.belatrixsf.allstars.ui.signup;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.ui.common.AllStarsFragment;
import com.belatrixsf.allstars.utils.AllStarsApplication;
import com.belatrixsf.allstars.utils.DialogUtils;
import com.belatrixsf.allstars.utils.di.modules.presenters.SignUpPresenterModule;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by icerrate on 16/05/16.
 */
public class SignUppFragment extends AllStarsFragment implements SignUppView {

    @Bind(R.id.email) EditText emailEditText;
    @Bind(R.id.send) Button sendButton;

    private SignUppPresenter signUppPresenter;
    private SignUpFragmentListener signUpFragmentListener;

    public SignUppFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        castOrThrowException(activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        castOrThrowException(context);
    }

    private void castOrThrowException(Context context) {
        try {
            signUpFragmentListener = (SignUpFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SignUpFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        if (savedInstanceState == null) {
            signUppPresenter.init();
        }
    }

    private void initViews() {
        emailEditText.addTextChangedListener(formFieldWatcher);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        signUppPresenter = null;
    }

    @Override
    protected void initDependencies(AllStarsApplication allStarsApplication) {
        signUppPresenter = allStarsApplication.getApplicationComponent()
                .signUpComponent(new SignUpPresenterModule(this))
                .signUpPresenter();
    }

    @Override
    public void backToLogin() {
        fragmentListener.closeActivity();
    }

    @Override
    public void enableSend(boolean enable) {
        sendButton.setEnabled(enable);
    }

    @OnClick(R.id.send)
    public void sendClicked() {
        String email = emailEditText.getText().toString();
        signUppPresenter.signUp(email);
    }

    @Override
    public void showMessage(String message){
        DialogUtils.createInformationDialog(getActivity(), message, getString(R.string.app_name), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                signUppPresenter.confirmMessage();
            }
        }).show();
    }

    @OnClick(R.id.log_in)
    public void logInClicked() {
        signUpFragmentListener.loginButtonClicked();
    }

    private TextWatcher formFieldWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String email = emailEditText.getText().toString();
            signUppPresenter.checkIfEmailIsValid(email);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}

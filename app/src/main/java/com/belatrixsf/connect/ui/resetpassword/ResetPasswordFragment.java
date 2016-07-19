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
package com.belatrixsf.connect.ui.resetpassword;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.IntentCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.account.edit.EditAccountActivity;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.ui.home.UserActivity;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.di.components.DaggerResetPasswordComponent;
import com.belatrixsf.connect.utils.di.modules.presenters.ResetPasswordPresenterModule;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by icerrate on 19/05/16.
 */
public class ResetPasswordFragment extends BelatrixConnectFragment implements ResetPasswordView {

    @Bind(R.id.new_password_input) TextInputLayout newPasswordInputLayout;
    @Bind(R.id.repeat_new_password_input) TextInputLayout repeatNewPasswordInputLayout;
    @Bind(R.id.old_password) EditText oldPasswordEditText;
    @Bind(R.id.new_password) EditText newPasswordEditText;
    @Bind(R.id.repeat_new_password) EditText repeatNewPasswordEditText;
    @Bind(R.id.reset) Button resetButton;
    @Bind(R.id.toolbar) Toolbar toolbar;

    private ResetPasswordPresenter resetPasswordPresenter;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset_password, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        if (savedInstanceState == null) {
            resetPasswordPresenter.init();
        }
    }

    private void initViews() {
        fragmentListener.setToolbar(toolbar);
        fragmentListener.setTitle("");
        oldPasswordEditText.setTransformationMethod(new PasswordTransformationMethod());
        newPasswordEditText.setTransformationMethod(new PasswordTransformationMethod());
        repeatNewPasswordEditText.setTransformationMethod(new PasswordTransformationMethod());
        oldPasswordEditText.addTextChangedListener(formFieldWatcher);
        newPasswordEditText.addTextChangedListener(formFieldWatcher);
        repeatNewPasswordEditText.addTextChangedListener(formFieldWatcher);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        resetPasswordPresenter = null;
    }

    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        resetPasswordPresenter = DaggerResetPasswordComponent.builder()
                .applicationComponent(belatrixConnectApplication.getApplicationComponent())
                .resetPasswordPresenterModule(new ResetPasswordPresenterModule(this))
                .build()
                .resetPasswordPresenter();
    }

    @Override
    public void goHome() {
        Intent intent = new Intent(getActivity(), UserActivity.class);
        intent.addFlags(IntentCompat.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        fragmentListener.closeActivity();
    }

    @Override
    public void goEditProfile() {
        Intent intent = new Intent(getActivity(), EditAccountActivity.class);
        startActivity(intent);
        fragmentListener.closeActivity();
    }

    @Override
    public void enableReset(boolean enable) {
        resetButton.setEnabled(enable);
    }

    @Override
    public void newPasswordError(String message) {
        newPasswordInputLayout.setErrorEnabled(true);
        repeatNewPasswordInputLayout.setErrorEnabled(true);
        newPasswordInputLayout.setError(message);
        repeatNewPasswordInputLayout.setError(message);
    }

    @Override
    public void cleanNewPasswordError() {
        newPasswordInputLayout.setErrorEnabled(false);
        repeatNewPasswordInputLayout.setErrorEnabled(false);
    }

    @OnClick(R.id.reset)
    public void resetClicked() {
        String oldPassword = oldPasswordEditText.getText().toString();
        String newPassword = newPasswordEditText.getText().toString();
        resetPasswordPresenter.reset(oldPassword, newPassword);
    }

    private TextWatcher formFieldWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String oldPassword = oldPasswordEditText.getText().toString();
            String newPassword = newPasswordEditText.getText().toString();
            String repeatNewPassword = repeatNewPasswordEditText.getText().toString();
            resetPasswordPresenter.checkIfInputsAreValid(oldPassword, newPassword, repeatNewPassword);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };

}

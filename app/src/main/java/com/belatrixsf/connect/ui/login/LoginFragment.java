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
package com.belatrixsf.connect.ui.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.account.edit.EditAccountActivity;
import com.belatrixsf.connect.ui.account.edit.EditAccountFragment;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.ui.home.UserActivity;
import com.belatrixsf.connect.ui.login.guest.LoginAsGuestActivity;
import com.belatrixsf.connect.ui.resetpassword.ResetPasswordActivity;
import com.belatrixsf.connect.ui.resetpassword.request.RequestNewPasswordActivity;
import com.belatrixsf.connect.ui.signup.SignUpActivity;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.CustomDomainEditText;
import com.belatrixsf.connect.utils.di.components.DaggerLoginComponent;
import com.belatrixsf.connect.utils.di.modules.presenters.LoginPresenterModule;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.OnClick;

import static com.belatrixsf.connect.utils.Constants.DEFAULT_DOMAIN_KEY;

public class LoginFragment extends BelatrixConnectFragment implements LoginView {

    private LoginPresenter loginPresenter;

    private String defaultDomain;
    private final String DEFAULT_DOMAIN_ID = "default_domain_id";

    @Bind(R.id.username) CustomDomainEditText usernameEditText;
    @Bind(R.id.password) EditText passwordEditText;
    @Bind(R.id.log_in) Button logInButton;
    @Bind(R.id.log_in_as_guest) Button logInAsGuestButton;
    @Bind(R.id.forgot_password) TextView forgotPasswordButton;
    @Bind(R.id.sign_up) Button signUpButton;
    @BindString(R.string.privacy_policy_url) String privacyPolicyURL;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        usernameEditText.initEditText(getString(R.string.hint_username));
        if (savedInstanceState == null) {
            loginPresenter.init();
            loginPresenter.getDefaultDomain();
        }
        if(savedInstanceState != null){
            usernameEditText.setDefaultDomain(savedInstanceState.getString(DEFAULT_DOMAIN_KEY));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(DEFAULT_DOMAIN_KEY,usernameEditText.getDefaultDomain());
        super.onSaveInstanceState(outState);
    }

    private void initViews() {
        passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
        usernameEditText.addTextChangedListener(formFieldWatcher);
        passwordEditText.addTextChangedListener(formFieldWatcher);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    logInClicked();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        loginPresenter.cancelRequests();
        super.onDestroyView();
    }

    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        loginPresenter = DaggerLoginComponent.builder()
                .applicationComponent(belatrixConnectApplication.getApplicationComponent())
                .loginPresenterModule(new LoginPresenterModule(this))
                .build()
                .loginPresenter();
    }

    @Override
    public void goHome() {
        Intent intent = new Intent(getActivity(), UserActivity.class);
        startActivity(intent);
        fragmentListener.closeActivity();
    }

    @Override
    public void goResetPassword() {
        Intent intent = new Intent(getActivity(), ResetPasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public void goEditProfile() {
        Intent intent = new Intent(getActivity(), EditAccountActivity.class);
        intent.putExtra(EditAccountFragment.IS_NEW_USER, true);
        startActivity(intent);
        fragmentListener.closeActivity();
    }

    @Override
    public void enableLogin(boolean enable) {
        logInButton.setEnabled(enable);
    }

    @OnClick(R.id.log_in)
    public void logInClicked() {
        String username = usernameEditText.getUserName().trim();
        String password = passwordEditText.getText().toString().trim();
        loginPresenter.login(username, password);
    }

    @OnClick(R.id.log_in_as_guest)
    public void logInAsGuestClicked() {
        Intent intent = new Intent(getActivity(), LoginAsGuestActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.sign_up)
    public void signUpClicked() {
        Intent intent = new Intent(getActivity(), SignUpActivity.class);
        intent.putExtra(DEFAULT_DOMAIN_ID, defaultDomain);
        startActivity(intent);
    }

    @OnClick(R.id.forgot_password)
    public void forgotPasswordClicked() {
        Intent intent = new Intent(getActivity(), RequestNewPasswordActivity.class);
        intent.putExtra(DEFAULT_DOMAIN_ID, defaultDomain);
        startActivity(intent);
    }

    @OnClick(R.id.privacy_policy)
    public void privacyPolicyClicked() {
        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(privacyPolicyURL));
        startActivity(intent);
    }

    @Override
    public void setDefaultDomain(String domain) {
        defaultDomain = domain;
        usernameEditText.setDefaultDomain(domain);
        usernameEditText.setDefaultUsername(getString(R.string.hint_username));
    }

    private TextWatcher formFieldWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String username = usernameEditText.getUserName().trim();
            String password = passwordEditText.getText().toString().trim();
            loginPresenter.checkIfInputsAreValid(username, password);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };

}

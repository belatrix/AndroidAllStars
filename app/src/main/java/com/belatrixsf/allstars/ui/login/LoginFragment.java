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
package com.belatrixsf.allstars.ui.login;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.belatrixsf.allstars.BuildConfig;
import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.ui.common.AllStarsFragment;
import com.belatrixsf.allstars.ui.home.MainActivity;
import com.belatrixsf.allstars.ui.resetpassword.ResetPasswordActivity;
import com.belatrixsf.allstars.ui.signup.SignUpActivity;
import com.belatrixsf.allstars.utils.AllStarsApplication;
import com.belatrixsf.allstars.utils.di.components.DaggerLoginComponent;
import com.belatrixsf.allstars.utils.di.modules.presenters.LoginPresenterModule;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginFragment extends AllStarsFragment implements LoginView {

    @Bind(R.id.username) EditText usernameEditText;
    @Bind(R.id.password) EditText passwordEditText;
    @Bind(R.id.log_in) Button logInButton;
    @Bind(R.id.sign_up) TextView signUpButton;
    @Bind(R.id.facebook_log_in)
    LoginButton facebookLogInButton;

    private LoginPresenter loginPresenter;
    private CallbackManager callbackManager;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FacebookSdk.setApplicationId(BuildConfig.FB_ID);
        FacebookSdk.sdkInitialize(getActivity());
        callbackManager = CallbackManager.Factory.create();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        if (savedInstanceState == null) {
            loginPresenter.init();
        }
    }

    private void initViews() {
        passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
        usernameEditText.addTextChangedListener(formFieldWatcher);
        passwordEditText.addTextChangedListener(formFieldWatcher);

        facebookLogInButton.setReadPermissions(Arrays.asList("email", "public_profile "));
        facebookLogInButton.setFragment(this);
        facebookLogInButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("FACEBOOK","On success");
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject json, GraphResponse response) {
                                loginPresenter.loginWithFacebook(json);
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name,last_name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d("FACEBOOK","On cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("FACEBOOK",exception.toString());
            }
        });
    }

    @Override
    public void onDestroyView() {
        loginPresenter.cancelRequests();
        super.onDestroyView();
    }

    @Override
    protected void initDependencies(AllStarsApplication allStarsApplication) {
        loginPresenter = DaggerLoginComponent.builder()
                .applicationComponent(allStarsApplication.getApplicationComponent())
                .loginPresenterModule(new LoginPresenterModule(this))
                .build()
                .loginPresenter();
    }

    @Override
    public void goHome() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        fragmentListener.closeActivity();
    }

    @Override
    public void goResetPassword() {
        Intent intent = new Intent(getActivity(), ResetPasswordActivity.class);
        startActivity(intent);
        fragmentListener.closeActivity();
    }

    @Override
    public void enableLogin(boolean enable) {
        logInButton.setEnabled(enable);
    }

    @OnClick(R.id.log_in)
    public void loginClicked() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        loginPresenter.login(username, password);
    }

    @OnClick(R.id.sign_up)
    public void signUpClicked() {
        Intent intent = new Intent(getActivity(), SignUpActivity.class);
        startActivity(intent);
    }

    private TextWatcher formFieldWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            loginPresenter.checkIfInputsAreValid(username, password);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}

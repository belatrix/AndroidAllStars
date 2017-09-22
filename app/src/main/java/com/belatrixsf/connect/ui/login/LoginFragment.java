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
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.IntermediaryLogoActivity;
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

import static com.belatrixsf.connect.utils.AnimationsUtils.ANIMATIONS_DURATION;
import static com.belatrixsf.connect.utils.AnimationsUtils.OutInAnimDirection;
import static com.belatrixsf.connect.utils.AnimationsUtils.StraightAnimDirection;
import static com.belatrixsf.connect.utils.AnimationsUtils.WAIT_DURATION;
import static com.belatrixsf.connect.utils.AnimationsUtils.customTranslateAnimation;
import static com.belatrixsf.connect.utils.AnimationsUtils.moveLogoAnimation;
import static com.belatrixsf.connect.utils.AnimationsUtils.scaleCenterAnimation;
import static com.belatrixsf.connect.utils.Constants.DEFAULT_DOMAIN_KEY;

public class LoginFragment extends BelatrixConnectFragment implements LoginView {

    private LoginPresenter loginPresenter;

    private String defaultDomain;
    private final String DEFAULT_DOMAIN_ID = "default_domain_id";
    private final String INTERMEDIARY_EXTRA_KEY = "intermediary_key";
    private final int LOGGED_ANIMATION_DURATION = 800;

    @Bind(R.id.username) CustomDomainEditText usernameEditText;
    @Bind(R.id.password) EditText passwordEditText;
    @Bind(R.id.log_in) Button logInButton;
    @Bind(R.id.log_in_as_guest) Button logInAsGuestButton;
    @Bind(R.id.forgot_password) TextView forgotPasswordButton;
    @Bind(R.id.sign_up) Button signUpButton;
    @Bind(R.id.fields_container) LinearLayout fieldsContainer;
    @Bind(R.id.logo_container) RelativeLayout logoContainer;
    @Bind(R.id.tempLogo) ImageView tempLogo;
    @Bind(R.id.tempTitle) TextView tempTitle;
    @Bind(R.id.bx_logo) ImageView bxLogo;
    @Bind(R.id.bx_title) TextView bxTitle;
    @BindString(R.string.privacy_policy_url) String privacyPolicyURL;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
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
        if (savedInstanceState == null) {
            initViews();
            usernameEditText.setDefaultUsername(getString(R.string.hint_username));
            loginPresenter.setCallNeeded(true);
            loginPresenter.init();
            loginPresenter.startAnimations();
        }
        if(savedInstanceState != null){
            loginPresenter.setCallNeeded(false);
            usernameEditText.setDefaultDomain(savedInstanceState.getString(DEFAULT_DOMAIN_KEY));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(DEFAULT_DOMAIN_KEY, usernameEditText.getDefaultDomain());
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
    public Intent homeIntent() {
        return UserActivity.makeIntent(getActivity());
    }

    @Override
    public Intent editProfileIntent() {
        Intent intent = new Intent(getActivity(), EditAccountActivity.class);
        intent.putExtra(EditAccountFragment.IS_NEW_USER, true);
        return intent;
    }

    @Override
    public Intent resetPassIntent() {
        return new Intent(getActivity(), ResetPasswordActivity.class);
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
        loginPresenter.onSignUpClicked();
    }

    @OnClick(R.id.forgot_password)
    public void forgotPasswordClicked() {
        loginPresenter.onForgotPasswordClicked();
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

    @Override
    public void initialAnimations(float scale) {
        startInitialLogoAnimation(scale);

        Animation appearFieldsAnim = customTranslateAnimation(getActivity(), fieldsContainer, OutInAnimDirection.IN_UP);
        appearFieldsAnim.setAnimationListener(initialAnimationListener);
        fieldsContainer.startAnimation(appearFieldsAnim);
    }

    @Override
    public void loggedAnimations(float scale) {
        loggedLogoAnimation(scale);

        Animation hideFieldsAnim = customTranslateAnimation(getActivity(), fieldsContainer, OutInAnimDirection.OUT_DOWN);
        hideFieldsAnim.setAnimationListener(loggedAnimationListener);
        hideFieldsAnim.setDuration(LOGGED_ANIMATION_DURATION);
        fieldsContainer.startAnimation(hideFieldsAnim);
    }

    private void startInitialLogoAnimation(float scale) {
        tempLogo.startAnimation(moveLogoAnimation(getActivity(), StraightAnimDirection.UP, tempLogo.getY() - bxLogo.getY(), scale));
        tempTitle.animate().y(bxTitle.getY()).setDuration(ANIMATIONS_DURATION);
        tempTitle.startAnimation(scaleCenterAnimation(getActivity(), null, true));
    }

    private void loggedLogoAnimation(float scale) {
        Animation bxLogoReverseAnim = moveLogoAnimation(getActivity(), StraightAnimDirection.DOWN, tempLogo.getY() - bxLogo.getY(), scale);
        Animation bxTitleReverseAnim = scaleCenterAnimation(getActivity(), null, false);
        bxLogoReverseAnim.setDuration(LOGGED_ANIMATION_DURATION);
        bxTitleReverseAnim.setDuration(LOGGED_ANIMATION_DURATION);

        bxLogo.startAnimation(bxLogoReverseAnim);
        bxTitle.startAnimation(bxTitleReverseAnim);
    }

    @Override
    public void slideOutAnimation() {
        logoContainer.startAnimation(customTranslateAnimation(getActivity(), logoContainer, OutInAnimDirection.OUT_UP));

        Animation slideOutAnim = customTranslateAnimation(getActivity(), fieldsContainer, OutInAnimDirection.OUT_DOWN);
        slideOutAnim.setAnimationListener(slideOutAnimationListener);
        fieldsContainer.startAnimation(slideOutAnim);
    }

    @Override
    public void slideInAnimation() {
        fieldsContainer.startAnimation(customTranslateAnimation(getActivity(), fieldsContainer, OutInAnimDirection.IN_UP));
        logoContainer.startAnimation(customTranslateAnimation(getActivity(), logoContainer, OutInAnimDirection.IN_DOWN));
    }

    @Override
    public void startAnimations(Runnable runnable) {
        new Handler().postDelayed(runnable, WAIT_DURATION);
    }

    @Override
    public void startLoggedHandler(Runnable runnable, int duration) {
        new Handler().postDelayed(runnable, duration);
    }

    @Override
    public void enableFields(boolean enable) {
        if (usernameEditText != null && passwordEditText != null) {
            usernameEditText.setEnabled(enable);
            passwordEditText.setEnabled(enable);
        }
    }

    @Override
    public void replaceLogo() {
        bxLogo.setVisibility(View.VISIBLE);
        bxTitle.setVisibility(View.VISIBLE);
        tempLogo.setVisibility(View.INVISIBLE);
        tempTitle.setVisibility(View.INVISIBLE);
        tempLogo.clearAnimation();
        tempTitle.clearAnimation();
    }

    private AnimationListener initialAnimationListener = new AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {}

        @Override
        public void onAnimationEnd(Animation animation) {
            loginPresenter.checkForCallNeeded();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {}
    };

    private AnimationListener slideOutAnimationListener = new AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {}

        @Override
        public void onAnimationEnd(Animation animation) {
            Class toActivityClass = (loginPresenter.isGoingToSignUp()) ? SignUpActivity.class :
                                                                    RequestNewPasswordActivity.class;
            Intent intent = new Intent(getActivity(), toActivityClass);
            intent.putExtra(DEFAULT_DOMAIN_ID, defaultDomain);
            startActivity(intent);
            getActivity().overridePendingTransition(0, 0);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {}
    };

    private AnimationListener loggedAnimationListener = new AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {}

        @Override
        public void onAnimationEnd(Animation animation) {
            loginPresenter.continueFlow();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {}
    };

    private TextWatcher formFieldWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String username = usernameEditText.getUserName().trim();
            String password = passwordEditText.getText().toString().trim();
            loginPresenter.checkIfInputsAreValid(username, password);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    @Override
    public void goToNextActivity(Intent intent) {
        IntermediaryLogoActivity.nextActivity = intent;
        Intent intermediaryIntent = IntermediaryLogoActivity.makeIntent(getActivity());
        intermediaryIntent.putExtra(INTERMEDIARY_EXTRA_KEY, false);
        startActivity(intermediaryIntent);
        getActivity().overridePendingTransition(0, 0);
        fragmentListener.closeActivity();
    }

}

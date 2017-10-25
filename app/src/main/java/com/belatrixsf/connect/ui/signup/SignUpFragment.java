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
package com.belatrixsf.connect.ui.signup;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.ui.login.LoginActivity;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.CustomDomainEditText;
import com.belatrixsf.connect.utils.DialogUtils;
import com.belatrixsf.connect.utils.KeyboardUtils;
import com.belatrixsf.connect.utils.di.modules.presenters.SignUpPresenterModule;

import butterknife.Bind;
import butterknife.OnClick;

import static com.belatrixsf.connect.utils.AnimationsUtils.OutInAnimDirection;
import static com.belatrixsf.connect.utils.AnimationsUtils.WAIT_DURATION;
import static com.belatrixsf.connect.utils.AnimationsUtils.customTranslateAnimation;

/**
 * Created by icerrate on 16/05/16.
 */
public class SignUpFragment extends BelatrixConnectFragment implements SignUpView {

    private SignUpPresenter signUpPresenter;

    private final String DEFAULT_DOMAIN_ID = "default_domain_id";

    @Bind(R.id.email) CustomDomainEditText emailEditText;
    @Bind(R.id.send) Button sendButton;
    @Bind(R.id.back_button) ImageView backButton;
    @Bind(R.id.fields_container) View fieldsContainer;
    @Bind(R.id.title_container) View titleContainer;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    public SignUpFragment() {
        // Required empty public constructor
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
        if (savedInstanceState == null) {
            initViews();
            signUpPresenter.startAnimations();
            signUpPresenter.init();
        }
    }

    private void initViews() {
        emailEditText.setFilters(new InputFilter[] { filter });
        emailEditText.setDefaultDomain(getActivity().getIntent().getExtras().getString(DEFAULT_DOMAIN_ID));
        emailEditText.setDefaultUsername(getString(R.string.hint_username));
        enableSend(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        signUpPresenter = null;
    }

    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        signUpPresenter = belatrixConnectApplication.getApplicationComponent()
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
        String email = emailEditText.getText().toString().trim();
        signUpPresenter.signUp(email);
    }

    @OnClick(R.id.back_button)
    public void onBackClicked() {
        signUpPresenter.onBackClicked();
    }

    @Override
    public void startAnimations(Runnable runnable) {
        new Handler().postDelayed(runnable, WAIT_DURATION);
    }

    @Override
    public void slideInAnimation() {
        Animation fieldsAnimation = customTranslateAnimation(getActivity(), fieldsContainer, OutInAnimDirection.IN_UP);
        fieldsAnimation.setAnimationListener(initialAnimationListener);
        fieldsContainer.startAnimation(fieldsAnimation);

        titleContainer.startAnimation(customTranslateAnimation(getActivity(), titleContainer, OutInAnimDirection.IN_DOWN));
    }

    @Override
    public void slideOutAnimation() {
        Animation fieldsAnimation = customTranslateAnimation(getActivity(), fieldsContainer, OutInAnimDirection.OUT_DOWN);
        fieldsAnimation.setAnimationListener(slideOutAnimationListener);
        fieldsContainer.startAnimation(fieldsAnimation);

        titleContainer.startAnimation(customTranslateAnimation(getActivity(), titleContainer, OutInAnimDirection.OUT_UP));
    }

    @Override
    public boolean getEmailFocus() {
        return (emailEditText != null) && emailEditText.hasFocus();
    }

    @Override
    public void removeEmailFocus() {
        KeyboardUtils.hideKeyboard(getActivity(), emailEditText);
        emailEditText.clearFocus();
    }

    @Override
    public void showMessage(String message){
        DialogUtils.createInformationDialog(getActivity(), message, getString(R.string.app_name), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //signUpPresenter.confirmMessage();
                onBackClicked();
            }
        }).show();
    }

    @Override
    public void showErrorMessage(String message){
        DialogUtils.createSimpleDialog(getActivity(),getString(R.string.app_name),message).show();
    }

    private Animation.AnimationListener initialAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {}

        @Override
        public void onAnimationEnd(Animation animation) {
            emailEditText.setEnabled(true);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {}
    };

    private Animation.AnimationListener slideOutAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {}

        @Override
        public void onAnimationEnd(Animation animation) {
            signUpPresenter.endFlow();
            getActivity().overridePendingTransition(0, 0);
            LoginActivity.fragment.slideInAnimation();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {}
    };

    private String blockCharacterSet = "~#^|$%&*!@";

    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };


}

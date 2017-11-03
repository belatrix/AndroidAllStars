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

import android.os.SystemClock;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.entities.SiteInfo;
import com.belatrixsf.connect.managers.EmployeeManager;
import com.belatrixsf.connect.managers.EmployeeManager.AccountState;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;
import com.belatrixsf.connect.utils.ServiceError;

import javax.inject.Inject;

/**
 * Created by gyosida on 4/12/16.
 */
public class LoginPresenter extends BelatrixConnectPresenter<LoginView> {

    private long lastClickTime = 0; // to handle fast double click

    private EmployeeManager employeeManager;
    private boolean callNeeded;
    private boolean toSignUp;

    public static final float LOGO_SCALE = 1.5f;
    public static final float INITIAL_SCALE = 0.66f;

    private Runnable logoRunnable = new Runnable() {
        @Override
        public void run() {
            view.initialAnimations(LOGO_SCALE);
        }
    };

    private PresenterCallback<AccountState> loginCallBack = new PresenterCallback<AccountState>() {
        @Override
        public void onSuccess(AccountState accountState) {
            view.hideProgressIndicator();
            continueFlow(accountState);
        }

        @Override
        public void onFailure(ServiceError serviceError) {
            view.hideProgressIndicator();
            view.showError(serviceError.getDetail());
            view.recreateFragment(); // reset
        }
    };

    @Inject
    public LoginPresenter(LoginView view, EmployeeManager employeeManager) {
        super(view);
        this.employeeManager = employeeManager;
        this.callNeeded = false;
    }

    public void setCallNeeded(boolean callNeeded) {
        this.callNeeded = callNeeded;
    }



    public void checkForCallNeeded() {
        if (callNeeded) {
            getDefaultDomain();
        }
    }

    public void checkIfInputsAreValid(String username, String password) {
        view.enableLogin(areFieldsFilled(username, password));
    }

    public void startAnimations() {;
        view.startAnimations(logoRunnable);
    }

    public boolean isGoingToSignUp() {
        return toSignUp;
    }

    public void onForgotPasswordClicked() {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
            return;
        }
        lastClickTime = SystemClock.elapsedRealtime();
        toSignUp = false;
        view.slideOutAnimation();
    }

    public void onSignUpClicked() {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
            return;
        }
        lastClickTime = SystemClock.elapsedRealtime();
        toSignUp = true;
        view.slideOutAnimation();
    }

    public void init() {
        view.enableLogin(false);
        view.enableFields(false);
    }

    public void onLoginButtonClicked(String username, String password) {
        if (areFieldsFilled(username, password)) {
            view.loggedAnimations(INITIAL_SCALE);
        } else {
            showError(R.string.error_incorrect_fields);
        }
    }

    public void login(String username, String password) {
        view.showProgressIndicator();
        employeeManager.login(username, password, loginCallBack);
    }

    public void getDefaultDomain() {
        employeeManager.getSiteInfo(new PresenterCallback<SiteInfo>() {
            @Override
            public void onSuccess(SiteInfo siteInfo) {
                view.setDefaultDomain("@" + siteInfo.getEmail_domain());
                view.enableFields(true);
                view.replaceLogo();
            }

            @Override
            public void onFailure(ServiceError serviceError) {
                view.replaceLogo();
            }
        });
    }

    public void continueFlow(AccountState userState) {
        switch (userState) {
            case PROFILE_COMPLETE:
                view.goToNextActivity(view.homeIntent());
                break;
            case PROFILE_INCOMPLETE:
                view.goToNextActivity(view.editProfileIntent());
                break;
            case PASSWORD_RESET_INCOMPLETE:
                view.goToNextActivity(view.resetPassIntent());
                break;
        }
    }

    public boolean areFieldsFilled(String username, String password) {
        return username != null && password != null && !username.isEmpty() && !password.isEmpty();
    }

    @Override
    public void cancelRequests() {

    }

}

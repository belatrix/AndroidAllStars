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

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.entities.SiteInfo;
import com.belatrixsf.connect.managers.EmployeeManager;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;

import javax.inject.Inject;

/**
 * Created by gyosida on 4/12/16.
 */
public class LoginPresenter extends BelatrixConnectPresenter<LoginView> {

    private EmployeeManager employeeManager;
    private boolean callNeeded;
    private boolean initialAnimationsEnded;

    public static final float LOGO_SCALE = 1.5f;

    private Runnable logoRunnable = new Runnable() {
        @Override
        public void run() {
            float logoY = view.getLogoY();
            float titleY = view.getTitleY();
            view.initialAnimations(logoY, titleY, LOGO_SCALE);
        }
    };

    private Runnable outRunnable = new Runnable() {
        @Override
        public void run() {
            view.slideOutAnimation();
        }
    };

    @Inject
    public LoginPresenter(LoginView view, EmployeeManager employeeManager) {
        super(view);
        this.employeeManager = employeeManager;
        this.callNeeded = false;
        this.initialAnimationsEnded = false;
    }

    public void onFragmentResume() {
        if (initialAnimationsEnded) {
            view.slideInAnimation();
        }
    }

    public void endInitialAnimations() {
        initialAnimationsEnded = true;
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

    public void startAnimations() {
        view.startAnimations(logoRunnable);
    }

    public void onForgotPasswordClicked() {
        view.startAnimations(outRunnable);
    }

    public void init() {
        view.enableLogin(false);
        view.enableFields(false);
    }

    public void login(String username, String password) {
        if (areFieldsFilled(username, password)) {
            view.showProgressDialog();
            employeeManager.login(username, password, new PresenterCallback<EmployeeManager.AccountState>() {
                @Override
                public void onSuccess(EmployeeManager.AccountState accountState) {
                    view.dismissProgressDialog();
                    switch (accountState) {
                        case PROFILE_COMPLETE:
                            view.goHome();
                            break;
                        case PROFILE_INCOMPLETE:
                            view.goEditProfile();
                            break;
                        case PASSWORD_RESET_INCOMPLETE:
                            view.goResetPassword();
                            break;
                    }
                }
            });
        } else {
            showError(R.string.error_incorrect_fields);
        }
    }

    public void getDefaultDomain() {
        //view.showProgressDialog();
        employeeManager.getSiteInfo(new PresenterCallback<SiteInfo>() {
            @Override
            public void onSuccess(SiteInfo siteInfo) {
                view.setDefaultDomain("@" + siteInfo.getEmail_domain());
                //view.dismissProgressDialog();
                view.enableFields(true);
            }
        });
    }

    private boolean areFieldsFilled(String username, String password) {
        return username != null && password != null && !username.isEmpty() && !password.isEmpty();
    }

    @Override
    public void cancelRequests() {
    }

}

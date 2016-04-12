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


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.ui.common.AllStarsFragment;
import com.belatrixsf.allstars.utils.AllStarsApplication;
import com.belatrixsf.allstars.utils.di.components.DaggerLoginComponent;
import com.belatrixsf.allstars.utils.di.modules.presenters.LoginPresenterModule;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginFragment extends AllStarsFragment implements LoginView {

    @Bind(R.id.username) EditText usernameEditText;
    @Bind(R.id.password) EditText passwordEditText;

    private LoginPresenter loginPresenter;

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
    protected void initDependencies(AllStarsApplication allStarsApplication) {
        loginPresenter = DaggerLoginComponent.builder()
                .applicationComponent(allStarsApplication.getApplicationComponent())
                .loginPresenterModule(new LoginPresenterModule(this))
                .build()
                .loginPresenter();
    }

    @Override
    public void goHome() {
        Log.d(TAG, "goHome: ");
    }

    @OnClick(R.id.submit)
    public void submitClicked() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        loginPresenter.login(username, password);
    }
}

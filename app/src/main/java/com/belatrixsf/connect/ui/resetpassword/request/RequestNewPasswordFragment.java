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
package com.belatrixsf.connect.ui.resetpassword.request;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.CustomDomainEditText;
import com.belatrixsf.connect.utils.DialogUtils;
import com.belatrixsf.connect.utils.di.modules.presenters.RequestNewPasswordPresenterModule;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by icerrate on 15/07/2016.
 */
public class RequestNewPasswordFragment extends BelatrixConnectFragment implements RequestNewPasswordView {

    private RequestNewPasswordPresenter requestNewPasswordPresenter;

    private final String DEFAULT_DOMAIN_ID = "default_domain_id";

    @Bind(R.id.email) CustomDomainEditText emailEditText;
    @Bind(R.id.new_password) Button requestButton;
    @Bind(R.id.toolbar) Toolbar toolbar;

    public RequestNewPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request_new_password, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        if (savedInstanceState == null) {
            requestNewPasswordPresenter.init();
        }
    }

    private void initViews() {
        fragmentListener.setToolbar(toolbar);
        fragmentListener.setTitle("");
        emailEditText.setDefaultDomain(getActivity().getIntent().getExtras().getString(DEFAULT_DOMAIN_ID));
        emailEditText.setDefaultUsername(getString(R.string.hint_username));
        emailEditText.addTextChangedListener(formFieldWatcher);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requestNewPasswordPresenter = null;
    }

    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        requestNewPasswordPresenter = belatrixConnectApplication
                .getApplicationComponent()
                .requestNewPasswordComponent(new RequestNewPasswordPresenterModule(this))
                .requestNewPasswordPresenter();
    }

    @Override
    public void enableRequestButton(boolean enable) {
        requestButton.setEnabled(enable);
    }

    @Override
    public void showMessage(String message){
        DialogUtils.createInformationDialog(getActivity(), message, getString(R.string.app_name), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                requestNewPasswordPresenter.confirmMessage();
            }
        }).show();
    }

    @Override
    public void goBacktoLogin() {
        fragmentListener.closeActivity();
    }

    @OnClick(R.id.new_password)
    public void requestClicked() {
        String email = emailEditText.getText().toString().trim();
        requestNewPasswordPresenter.requestNewPassword(email);
    }

    private TextWatcher formFieldWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String email = emailEditText.getUserName().trim();
            if (email.contains(emailEditText.getDefaultUsername())) {
                email = email.replace(emailEditText.getDefaultUsername(), "");
            }
            requestNewPasswordPresenter.checkIfInputsIsValid(email);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };

}

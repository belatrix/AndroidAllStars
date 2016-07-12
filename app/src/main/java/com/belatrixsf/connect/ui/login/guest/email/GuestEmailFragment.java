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
package com.belatrixsf.connect.ui.login.guest.email;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.di.components.DaggerGuestEmailComponent;
import com.belatrixsf.connect.utils.di.modules.presenters.GuestEmailPresenterModule;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by icerrate on 24/06/2016.
 */
public class GuestEmailFragment extends BelatrixConnectFragment implements GuestEmailView {


    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.email) EditText emailEditText;
    @Bind(R.id.send_email) Button sendEmailButton;

    private GuestEmailPresenter guestEmailPresenter;

    public GuestEmailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guest_email, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        fragmentListener.setToolbar(toolbar);
        fragmentListener.setTitle("");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goBackWithoutEmail();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        guestEmailPresenter = null;
    }

    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        guestEmailPresenter = DaggerGuestEmailComponent.builder()
                .applicationComponent(belatrixConnectApplication.getApplicationComponent())
                .guestEmailPresenterModule(new GuestEmailPresenterModule(this))
                .build()
                .guestEmailPresenter();
    }

    @OnClick(R.id.send_email)
    public void sendEmailClicked() {
        String email = emailEditText.getText().toString();
        guestEmailPresenter.getEmailProcessCompleted(email);
    }

    @Override
    public void goBackWithEmail(String email) {
        Intent intent = getActivity().getIntent();
        intent.putExtra(GuestEmailActivity.GUEST_EMAIL_KEY, email);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    private void goBackWithoutEmail() {
        Intent intent = getActivity().getIntent();
        getActivity().setResult(Activity.RESULT_CANCELED, intent);
        getActivity().finish();
    }

}

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
package com.belatrixsf.allstars.ui.login.guest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.belatrixsf.allstars.BuildConfig;
import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.ui.common.AllStarsFragment;
import com.belatrixsf.allstars.utils.AllStarsApplication;
import com.belatrixsf.allstars.utils.di.modules.presenters.LogInAsGuestPresenterModule;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
i

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.Bind;
import io.fabric.sdk.android.Fabric;

/**
 * Created by icerrate on 27/05/16.
 */
public class LogInAsGuestFragment extends AllStarsFragment implements LogInAsGuestView {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.facebook_log_in) LoginButton facebookLogInButton;
    @Bind(R.id.twitter_log_in) TwitterLoginButton twitterLogInButton;

    private LogInAsGuestPresenter logInAsGuestPresenter;
    private CallbackManager callbackManager;

    public LogInAsGuestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        prepareSocialLogins();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_in_as_guest, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        if (savedInstanceState == null) {
            logInAsGuestPresenter.init();
        }
    }

    private void prepareSocialLogins(){
        //Facebook
        FacebookSdk.setApplicationId(BuildConfig.FB_ID);
        FacebookSdk.sdkInitialize(getActivity());
        callbackManager = CallbackManager.Factory.create();
        //Twitter
        TwitterAuthConfig authConfig = new TwitterAuthConfig(BuildConfig.TWITTER_ID, BuildConfig.TWITTER_SECRET);
        Fabric.with(getActivity(), new Twitter(authConfig));

    }

    private void initViews() {
        fragmentListener.setToolbar(toolbar);
        fragmentListener.setTitle("");

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
                                logInAsGuestPresenter.loginWithFacebook(json);
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

        twitterLogInButton.setCallback(new Callback() {
            @Override
            public void success(Result result) {
                Toast.makeText(getActivity().getApplicationContext(),
                        getResources().getString(R.string.app_name),
                        Toast.LENGTH_SHORT).show();
                TweetComposer.Builder builder = new TweetComposer.Builder(getActivity())
                        .text("Just setting up my Fabric!");
                builder.show();
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(getActivity().getApplicationContext(),
                        getResources().getString(R.string.app_name),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        logInAsGuestPresenter = null;
    }

    @Override
    protected void initDependencies(AllStarsApplication allStarsApplication) {
        logInAsGuestPresenter = allStarsApplication.getApplicationComponent()
                .logInAsGuestComponent(new LogInAsGuestPresenterModule(this))
                .logInAsGuestPresenter();
    }

    @Override
    public void backToLogin() {
        fragmentListener.closeActivity();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
        twitterLogInButton.onActivityResult(requestCode, resultCode, data);

    }

}

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
package com.belatrixsf.connect.ui.login.guest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.entities.Guest;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.ui.home.GuestActivity;
import com.belatrixsf.connect.ui.login.guest.email.GuestEmailActivity;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.di.components.DaggerLoginAsGuestComponent;
import com.belatrixsf.connect.utils.di.modules.presenters.LoginAsGuestPresenterModule;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by icerrate on 27/05/16.
 */
public class LoginAsGuestFragment extends BelatrixConnectFragment implements LoginAsGuestView {

    public static final int FACEBOOK_RQ = 64206;
    public static final int TWITTER_RQ = 140;
    public static final int TWITTER_NAME_RQ = 75535;
    public static final int GUEST_EMAIL_RQ = 9999;

    public static final String GUEST_KEY = "_guest_key";

    @Bind(R.id.toolbar) Toolbar toolbar;

    private LoginAsGuestPresenter loginAsGuestPresenter;
    private CallbackManager callbackManager;
    private TwitterAuthClient twitterAuthClient;

    public LoginAsGuestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_in_as_guest, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSocialLogin();
        initViews();
        if (savedInstanceState != null) {
            restorePresenterState(savedInstanceState);
        }
    }

    private void initSocialLogin() {
        callbackManager = CallbackManager.Factory.create();
        twitterAuthClient = new TwitterAuthClient();
    }

    private void initViews() {
        fragmentListener.setToolbar(toolbar);
        fragmentListener.setTitle("");
    }

    @OnClick(R.id.facebook_log_in)
    public void onFacebookLogin() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile "));

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject json, GraphResponse response) {
                                try {
                                    String id = json.getString("id");
                                    String email = json.getString("email");
                                    String fullName = json.getString("first_name") + " " + json.getString("last_name");
                                    loginAsGuestPresenter.loginWithFacebook(id, email, fullName);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name,last_name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
                loginAsGuestPresenter.facebookFailure();
            }
        });
    }

    @OnClick(R.id.twitter_log_in)
    public void onTwitterLogin() {
        twitterAuthClient.authorize(getActivity(), new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                String id = String.valueOf(result.data.getUserId());
                String userName = result.data.getUserName();
                loginAsGuestPresenter.twitterSessionSuccess(id, userName);
            }

            @Override
            public void failure(TwitterException e) {
                loginAsGuestPresenter.twitterFailure();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        savePresenterState(outState);
        super.onSaveInstanceState(outState);
    }

    private void restorePresenterState(Bundle savedInstanceState) {
        Guest guest = savedInstanceState.getParcelable(GUEST_KEY);
        loginAsGuestPresenter.setGuestData(guest);
    }

    private void savePresenterState(Bundle outState) {
        outState.putParcelable(GUEST_KEY, loginAsGuestPresenter.getGuestData());
    }

    @Override
    public void requestTwitterUserData() {
        TwitterSession session = Twitter.getSessionManager().getActiveSession();
        Twitter.getApiClient(session).getAccountService().verifyCredentials(true, false, new Callback<User>() {
            @Override
            public void success(Result<User> userResult) {
                User user = userResult.data;
                final String fullName = user.name;
                final String userImage = user.profileImageUrl;
                loginAsGuestPresenter.twitterUserDataSuccess(fullName, userImage);
            }

            @Override
            public void failure(TwitterException e) {
                loginAsGuestPresenter.twitterFailure();
            }
        });
    }

    @Override
    public void requestTwitterEmail() {
        TwitterSession session = Twitter.getSessionManager().getActiveSession();
        twitterAuthClient.requestEmail(session, new Callback<String>() {
            @Override
            public void success(Result<String> result) {
                String email = result.data;
                loginAsGuestPresenter.twitterEmailSuccess(email);
            }

            @Override
            public void failure(TwitterException exception) {
                loginAsGuestPresenter.onGettingEmailError();
            }
        });
    }

    @Override
    public void goRequestGuestEmail() {
        Intent intent = new Intent(getActivity(), GuestEmailActivity.class);
        startActivityForResult(intent, GUEST_EMAIL_RQ);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loginAsGuestPresenter = null;
    }

    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        loginAsGuestPresenter = DaggerLoginAsGuestComponent.builder()
                .applicationComponent(belatrixConnectApplication.getApplicationComponent())
                .loginAsGuestPresenterModule(new LoginAsGuestPresenterModule(this))
                .build()
                .loginAsGuestPresenter();
    }

    @Override
    public void goHome() {
        Intent intent = new Intent(getActivity(), GuestActivity.class);
        startActivity(intent);
        fragmentListener.closeActivity();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FACEBOOK_RQ){
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == TWITTER_RQ || requestCode == TWITTER_NAME_RQ) {
            twitterAuthClient.onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == GUEST_EMAIL_RQ) {
            if (resultCode == Activity.RESULT_OK) {
                String email = data.getStringExtra(GuestEmailActivity.GUEST_EMAIL_KEY);
                loginAsGuestPresenter.continueSocialLoginProcess(email);
            } else {
                loginAsGuestPresenter.resetData();
            }
        }

    }

}

package com.belatrixsf.connect.services.fcm;

import android.util.Log;

import com.belatrixsf.connect.services.contracts.EmployeeService;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.di.modules.presenters.AboutPresenterModule;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import javax.inject.Inject;

/**
 * Created by PedroCarrillo on 6/2/16.
 */

public class AllStarsFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
//    @Inject
//    EmployeeService employeeService;

    @Override
    public void onCreate() {
        BelatrixConnectApplication belatrixConnectApplication = (BelatrixConnectApplication) getApplication();
//        belatrixConnectApplication.getApplicationComponent()
//                .aboutPresenter();
        super.onCreate();
    }

    @Override
    public void onTokenRefresh() {
        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //Displaying token on logcat

        Log.d(TAG, "Refreshed token: " + refreshedToken);
        super.onTokenRefresh();
    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
    }

}
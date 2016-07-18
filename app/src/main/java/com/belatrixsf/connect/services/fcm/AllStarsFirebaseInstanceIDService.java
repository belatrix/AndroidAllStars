package com.belatrixsf.connect.services.fcm;

import android.util.Log;

import com.belatrixsf.connect.managers.PreferencesManager;
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

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, refreshedToken);
        PreferencesManager.get().saveDeviceToken(refreshedToken);
        super.onTokenRefresh();
    }

}
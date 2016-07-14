package com.belatrixsf.connect.networking.retrofit.requests;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PedroCarrillo on 7/13/16.
 */

public class RegisterDeviceRequest {

    @SerializedName("android_device")
    private String androidToken;

    public RegisterDeviceRequest(String androidToken) {
        this.androidToken = androidToken;
    }

}

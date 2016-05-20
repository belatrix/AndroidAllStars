package com.belatrixsf.allstars.networking.retrofit.requests;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PedroCarrillo on 5/17/16.
 */
public class UpdateEmployeeRequest {

    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("skype_id")
    private String skypeId;
    @SerializedName("location")
    private int locationId;

    public UpdateEmployeeRequest(String firstName, String lastName, String skypeId, int locationId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.skypeId = skypeId;
        this.locationId = locationId;
    }

}

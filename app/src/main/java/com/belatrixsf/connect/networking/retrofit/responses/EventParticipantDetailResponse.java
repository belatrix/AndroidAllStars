package com.belatrixsf.connect.networking.retrofit.responses;

import com.belatrixsf.connect.entities.Event;
import com.belatrixsf.connect.entities.Guest;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PedroCarrillo on 7/11/16.
 */

public class EventParticipantDetailResponse {

    private Integer pk;
    @SerializedName("datetime_register")
    private String dateTimeRegister;
    @SerializedName("is_registered")
    private boolean isRegistered;
    @SerializedName("event_details")
    private Event event;
    private Guest participant;

    public Integer getPk() {
        return pk;
    }

    public String getDateTimeRegister() {
        return dateTimeRegister;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public Event getEvent() {
        return event;
    }

    public Guest getParticipant() {
        return participant;
    }

}

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

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
package com.belatrixsf.connect.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by icerrate on 13/06/2016.
 * Modified by dvelasquez on 20/02/2017
 */
public class Event implements Parcelable {

    @SerializedName("pk")
    private Integer id;
    private String title;
    private String name;
    private String datetime;
    private Location location;
    private Integer collaborators;
    private Integer participants;
    private String address;
    private String description;
    @SerializedName("is_active")
    private boolean isActive;
    @SerializedName("is_upcoming")
    private boolean isUpcoming;
    @SerializedName("image")
    private String picture;
    @SerializedName("is_registration_open")
    private boolean isRegistrationAvailable;
    @SerializedName("is_registered")
    private boolean isRegistered;
    @SerializedName("registration_url")
    private String registrationURL;


    public Event() {
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isUpcoming() {
        return isUpcoming;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDatetime() {
        return datetime;
    }

    public Location getLocation() {
        return location;
    }

    public Integer getCollaborators() {
        return collaborators;
    }

    public Integer getParticipants() {
        return participants;
    }

    public String getPicture() {
        return picture;
    }

    public boolean isRegistrationAvailable() {
        return isRegistrationAvailable;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

    public String getRegistrationURL() {
        return registrationURL;
    }

    public void setRegistrationURL(String registrationURL) {
        this.registrationURL = registrationURL;
    }

    protected Event(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        title = in.readString();
        name = in.readString();
        datetime = in.readString();
        location = (Location) in.readValue(Location.class.getClassLoader());
        collaborators = in.readByte() == 0x00 ? null : in.readInt();
        participants = in.readByte() == 0x00 ? null : in.readInt();
        address = in.readString();
        description = in.readString();
        isActive = in.readByte() != 0x00;
        isUpcoming = in.readByte() != 0x00;
        picture = in.readString();
        isRegistrationAvailable = in.readByte() != 0x00;
        isRegistered = in.readByte() != 0x00;
        registrationURL = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }

        dest.writeString(title);
        dest.writeString(name);
        dest.writeString(datetime);
        dest.writeValue(location);
        if (collaborators == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(collaborators);
        }
        if (participants == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(participants);
        }
        dest.writeString(address);
        dest.writeString(description);
        dest.writeByte((byte) (isActive ? 0x01 : 0x00));
        dest.writeByte((byte) (isUpcoming ? 0x01 : 0x00));
        dest.writeString(picture);
        dest.writeByte((byte) (isRegistrationAvailable ? 0x01 : 0x00));
        dest.writeByte((byte) (isRegistered ? 0x01 : 0x00));
        dest.writeString(registrationURL);
    }

    @SuppressWarnings("unused")
    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

}
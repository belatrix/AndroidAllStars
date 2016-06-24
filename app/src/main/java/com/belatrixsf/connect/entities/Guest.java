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
 * Created by gyosida on 4/12/16.
 */
public class Guest implements Parcelable {

    private Integer id;
    private String fullname;
    private String email;
    @SerializedName("birth_date")
    private String birthDate;
    private String carreer ;
    @SerializedName("educational_center ")
    private String educationalCenter ;
    @SerializedName("english_level ")
    private String englishLevel ;
    @SerializedName("facebook_id ")
    private String facebookId ;
    @SerializedName("facebook_link")
    private String facebookLink;
    @SerializedName("twitter_id ")
    private String twitterId ;
    @SerializedName("twitter_link ")
    private String twitterLink;

    public Guest(String fullname, String email, String facebookId, String twitterId) {
        this.fullname = fullname;
        this.email = email;
        this.facebookId = facebookId;
        this.twitterId = twitterId;
        setFacebookLink();
        setTwitterLink();
    }

    public Integer getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getCarreer() {
        return carreer;
    }

    public String getEducationalCenter() {
        return educationalCenter;
    }

    public String getEnglishLevel() {
        return englishLevel;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public String getFacebookLink() {
        facebookLink = facebookId != null ? "https://facebook.com/" + facebookId : null;
        return facebookLink;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public String getTwitterLink() {
        twitterLink = twitterId != null ? "https://twitter.com/" + twitterId : null;
        return twitterLink;
    }

    private void setFacebookLink() {
        facebookLink = facebookId != null ? "https://facebook.com/" + facebookId : null;
    }

    private void setTwitterLink() {
        twitterLink = twitterId != null ? "https://twitter.com/" + twitterId : null;
    }

    protected Guest(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        fullname = in.readString();
        email = in.readString();
        birthDate = in.readString();
        carreer = in.readString();
        educationalCenter  = in.readString();
        englishLevel  = in.readString();
        facebookId  = in.readString();
        facebookLink  = in.readString();
        twitterId  = in.readString();
        twitterLink  = in.readString();
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
        dest.writeString(fullname);
        dest.writeString(email);
        dest.writeString(birthDate);
        dest.writeString(carreer);
        dest.writeString(educationalCenter);
        dest.writeString(englishLevel);
        dest.writeString(facebookId);
        dest.writeString(facebookLink);
        dest.writeString(twitterId);
        dest.writeString(twitterLink);
    }

    @SuppressWarnings("unused")
    public static final Creator<Guest> CREATOR = new Creator<Guest>() {
        @Override
        public Guest createFromParcel(Parcel in) {
            return new Guest(in);
        }

        @Override
        public Guest[] newArray(int size) {
            return new Guest[size];
        }
    };

}

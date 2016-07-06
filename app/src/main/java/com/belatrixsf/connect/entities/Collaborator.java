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
 * Created by icerrate on 12/06/2016.
 */
public class Collaborator implements Parcelable {

    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private Integer drawable;

    public Collaborator(String firstName, String lastName, int drawable) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.drawable = drawable;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getDrawable() {
        return drawable;
    }

    public String getFullName() {
        StringBuilder stringBuilder = new StringBuilder(firstName);
        if (lastName != null && !lastName.isEmpty()){
            stringBuilder.append(" ").append(lastName);
        }
        return stringBuilder.toString();
    }

    protected Collaborator(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        drawable = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        if (drawable == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(drawable);
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<Collaborator> CREATOR = new Creator<Collaborator>() {
        @Override
        public Collaborator createFromParcel(Parcel in) {
            return new Collaborator(in);
        }

        @Override
        public Collaborator[] newArray(int size) {
            return new Collaborator[size];
        }
    };

}

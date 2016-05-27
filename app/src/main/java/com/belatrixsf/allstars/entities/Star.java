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
package com.belatrixsf.allstars.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by icerrate on 25/04/2016.
 */
public class Star implements Parcelable {

    @SerializedName(value = "pk", alternate = {"id"})
    private Integer id;
    private String date;
    @SerializedName("text")
    private String message;
    @SerializedName("from_user")
    private Employee fromUser;
    private Category category;
    private Keyword keyword;

    public Integer getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public Employee getFromUser() {
        return fromUser;
    }

    public Category getCategory() {
        return category;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    protected Star (Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        date = in.readString();
        message = in.readString();
        fromUser = (Employee) in.readValue(Employee.class.getClassLoader());
        category = (Category) in.readValue(Category.class.getClassLoader());
        keyword = (Keyword) in.readValue(Keyword.class.getClassLoader());
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
        dest.writeString(date);
        dest.writeString(message);
        dest.writeValue(fromUser);
        dest.writeValue(category);
        dest.writeValue(keyword);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Star> CREATOR = new Parcelable.Creator<Star>() {
        @Override
        public Star createFromParcel(Parcel in) {
            return new Star(in);
        }

        @Override
        public Star[] newArray(int size) {
            return new Star[size];
        }
    };
}
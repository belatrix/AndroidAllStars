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

/**
 * Created by echuquilin on 25/01/17.
 */
public class SiteInfo implements Parcelable{

    private String site;
    private String email_domain;
    private String backend_version;

    public String getSite() {
        return site;
    }

    public String getEmail_domain() {
        return email_domain;
    }

    public String getBackend_version() {
        return backend_version;
    }

    protected SiteInfo(Parcel in) {
        site = in.readString();
        email_domain = in.readString();
        backend_version = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(site);
        parcel.writeString(email_domain);
        parcel.writeString(backend_version);
    }

    @SuppressWarnings("unused")
    public static final Creator<SiteInfo> CREATOR = new Creator<SiteInfo>() {
        @Override
        public SiteInfo createFromParcel(Parcel in) {
            return new SiteInfo(in);
        }

        @Override
        public SiteInfo[] newArray(int size) {
            return new SiteInfo[size];
        }
    };
}

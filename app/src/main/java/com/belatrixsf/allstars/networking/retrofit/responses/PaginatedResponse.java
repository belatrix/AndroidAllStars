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
package com.belatrixsf.allstars.networking.retrofit.responses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gyosida on 5/9/16.
 */
public class PaginatedResponse implements Parcelable {

    private int count;
    private String next;
    private String previous;

    public PaginatedResponse() {}

    protected PaginatedResponse(Parcel in) {
        count = in.readInt();
        next = in.readString();
        previous = in.readString();
    }

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void reset() {
        count = 0;
        next = null;
        previous = null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(count);
        dest.writeString(next);
        dest.writeString(previous);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PaginatedResponse> CREATOR = new Parcelable.Creator<PaginatedResponse>() {
        @Override
        public PaginatedResponse createFromParcel(Parcel in) {
            return new PaginatedResponse(in);
        }

        @Override
        public PaginatedResponse[] newArray(int size) {
            return new PaginatedResponse[size];
        }
    };

}
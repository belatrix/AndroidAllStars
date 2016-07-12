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

import android.os.Parcel;
import android.os.Parcelable;

import com.belatrixsf.connect.utils.NumericUtils;

import java.util.List;

/**
 * Created by gyosida on 5/9/16.
 */
public class PaginatedResponse<T> implements Parcelable {

    private static final String PARAM_PAGE = "page";

    private int count;
    private String next;
    private String previous;
    private Integer nextPage;
    private List<T> results;

    public PaginatedResponse() {}

    protected PaginatedResponse(Parcel in) {
        count = in.readInt();
        next = in.readString();
        previous = in.readString();
        results = in.readArrayList(null);
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
        parseNextPage();
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private void parseNextPage() {
        if (next != null) {
            String[] queryParams = extractQueryParams(next);
            if (queryParams != null) {
                String queryParam = extractQueryParam(queryParams, PARAM_PAGE);
                if (queryParam != null) {
                    String value = getQueryParamValue(queryParam);
                    if (NumericUtils.isNumeric(value)) {
                        nextPage = Integer.parseInt(value);
                    }
                }
            }
        } else {
            nextPage = null;
        }
    }

    private String[] extractQueryParams(String url) {
        String[] splitResult = url.split("\\?");
        if (splitResult.length > 1) {
            String queryParams = splitResult[1];
            return queryParams.split("&");
        }
        return null;
    }

    private String extractQueryParam(String[] queryParams, String key) {
        for (String queryParam : queryParams) {
            if (queryParam.contains(key)) {
                return queryParam;
            }
        }
        return null;
    }

    private String getQueryParamValue(String queryParam) {
        int equalIndex = queryParam.indexOf("=");
        if (equalIndex != -1) {
            return queryParam.substring(equalIndex + 1, queryParam.length());
        }
        return null;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public void reset() {
        count = 0;
        next = null;
        previous = null;
        nextPage = null;
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
        dest.writeList(results);
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
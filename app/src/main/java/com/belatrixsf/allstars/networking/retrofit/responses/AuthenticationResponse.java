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

import android.support.annotation.VisibleForTesting;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gyosida on 4/11/16.
 */
public class AuthenticationResponse {

    @SerializedName("user_id")
    private int employeeId;
    private String token;
    @SerializedName("reset_password_code")
    private String resetPasswordCode;
    @SerializedName("base_profile_complete")
    private boolean baseProfileComplete;

    public int getEmployeeId() {
        return employeeId;
    }

    public String getToken() {
        return token;
    }

    public String getResetPasswordCode() {
        return resetPasswordCode;
    }

    public boolean isBaseProfileComplete() {
        return baseProfileComplete;
    }
}

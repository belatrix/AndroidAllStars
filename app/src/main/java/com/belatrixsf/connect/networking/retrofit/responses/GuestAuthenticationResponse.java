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

import com.google.gson.annotations.SerializedName;

/**
 * Created by icerrate on 22/06/2016.
 */
public class GuestAuthenticationResponse {

    private Integer id;
    private String fullname;
    private String email;
    @SerializedName("birth_date")
    private String birthDate;
    private String carreer ;
    @SerializedName("educational_center")
    private String educationalCenter ;
    @SerializedName("english_level")
    private String englishLevel ;
    @SerializedName("facebook_id")
    private String facebookId ;
    @SerializedName("facebook_link")
    private String facebookLink;
    @SerializedName("twitter_id")
    private String twitterId ;
    @SerializedName("twitter_link")
    private String twitterLink;

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
        return facebookLink;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public String getTwitterLink() {
        return twitterLink;
    }
}

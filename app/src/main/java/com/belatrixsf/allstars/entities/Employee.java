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

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gyosida on 4/12/16.
 */
public class Employee {

    private Integer pk;
    private String username;
    private String email;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private String avatar;
    private Integer role;
    @SerializedName("skype_id")
    private String skypeId;
    @SerializedName("last_month_score")
    private Integer lastMonthScore;
    @SerializedName("current_month_score")
    private Integer currentMonthScore;
    private Integer level;
    private Integer score;
    @SerializedName("is_active")
    private boolean active;
    @SerializedName("last_login")
    private String lastLogin;

    private List<Category> categories;

    public Integer getPk() {
        return pk;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAvatar() {
        if (avatar == null) {
            avatar = "https://pbs.twimg.com/profile_images/616076655547682816/6gMRtQyY.jpg";
        }
        return avatar;
    }

    public Integer getRole() {
        return role;
    }

    public String getSkypeId() {
        return skypeId;
    }

    public Integer getLastMonthScore() {
        return lastMonthScore;
    }

    public Integer getCurrentMonthScore() {
        return currentMonthScore;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getScore() {
        return score;
    }

    public boolean isActive() {
        return active;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public String getFullName() {
        StringBuilder stringBuilder = new StringBuilder(firstName);
        if (lastName != null && !lastName.isEmpty()){
            stringBuilder.append(" ").append(lastName);
        }
        return stringBuilder.toString();
    }

}

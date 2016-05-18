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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gyosida on 4/12/16.
 */
public class Employee implements Parcelable {

    private Integer pk;
    private String username;
    private String email;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private Role role;
    @SerializedName("skype_id")
    private String skypeId;
    @SerializedName("total_score")
    private Integer totalScore;
    @SerializedName("last_month_score")
    private Integer lastMonthScore;
    @SerializedName("current_month_score")
    private Integer currentMonthScore;
    @SerializedName("last_year_score")
    private Integer lastYearScore;
    @SerializedName("current_year_score")
    private Integer currentYearScore;
    private Integer value;
    private Integer level;
    private List<Category> categories;
    @SerializedName("is_active")
    private boolean active;
    @SerializedName("last_login")
    private String lastLogin;
    private String avatar;

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

    public Role getRole() {
        return role;
    }

    public String getSkypeId() {
        return skypeId;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public Integer getLastMonthScore() {
        return lastMonthScore;
    }

    public Integer getCurrentMonthScore() {
        return currentMonthScore;
    }

    public Integer getLastYearScore() {
        return lastYearScore;
    }

    public Integer getCurrentYearScore() {
        return currentYearScore;
    }

    public Integer getValue() {
        return value;
    }

    public Integer getLevel() {
        return level;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public boolean isActive() {
        return active;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public String getAvatar() {
        if (avatar == null) {
            avatar = "https://pbs.twimg.com/profile_images/616076655547682816/6gMRtQyY.jpg";
        }
        return avatar;
    }

    public String getFullName() {
        StringBuilder stringBuilder = new StringBuilder(firstName);
        if (lastName != null && !lastName.isEmpty()){
            stringBuilder.append(" ").append(lastName);
        }
        return stringBuilder.toString();
    }


    protected Employee(Parcel in) {
        pk = in.readByte() == 0x00 ? null : in.readInt();
        username = in.readString();
        email = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        role = (Role) in.readValue(Role.class.getClassLoader());
        skypeId = in.readString();
        totalScore = in.readByte() == 0x00 ? null : in.readInt();
        lastMonthScore = in.readByte() == 0x00 ? null : in.readInt();
        currentMonthScore = in.readByte() == 0x00 ? null : in.readInt();
        lastYearScore = in.readByte() == 0x00 ? null : in.readInt();
        currentYearScore = in.readByte() == 0x00 ? null : in.readInt();
        value = in.readByte() == 0x00 ? null : in.readInt();
        level = in.readByte() == 0x00 ? null : in.readInt();
        if (in.readByte() == 0x01) {
            categories = new ArrayList<Category>();
            in.readList(categories, Category.class.getClassLoader());
        } else {
            categories = null;
        }
        active = in.readByte() != 0x00;
        lastLogin = in.readString();
        avatar = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (pk == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(pk);
        }
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeValue(role);
        dest.writeString(skypeId);
        if (totalScore == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(totalScore);
        }
        if (lastMonthScore == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(lastMonthScore);
        }
        if (currentMonthScore == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(currentMonthScore);
        }
        if (lastYearScore == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(lastYearScore);
        }
        if (currentYearScore == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(currentYearScore);
        }
        if (value == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(value);
        }
        if (level == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(level);
        }
        if (categories == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(categories);
        }
        dest.writeByte((byte) (active ? 0x01 : 0x00));
        dest.writeString(lastLogin);
        dest.writeString(avatar);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Employee> CREATOR = new Parcelable.Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

}

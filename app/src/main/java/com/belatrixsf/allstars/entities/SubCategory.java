package com.belatrixsf.allstars.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PedroCarrillo on 4/26/16.
 */
public class SubCategory implements Parcelable {

    @SerializedName("subcategory__pk")
    private Integer pk;
    @SerializedName("subcategory__name")
    private String name;
    @SerializedName("num_stars")
    private Integer numStars;

    public Integer getPk() {
        return pk;
    }

    public String getName() {
        return name;
    }

    public Integer getNumStars() {
        return numStars;
    }

    protected SubCategory(Parcel in) {
        pk = in.readByte() == 0x00 ? null : in.readInt();
        name = in.readString();
        numStars = in.readByte() == 0x00 ? null : in.readInt();
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
        dest.writeString(name);
        if (numStars == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(numStars);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SubCategory> CREATOR = new Parcelable.Creator<SubCategory>() {
        @Override
        public SubCategory createFromParcel(Parcel in) {
            return new SubCategory(in);
        }

        @Override
        public SubCategory[] newArray(int size) {
            return new SubCategory[size];
        }
    };

    //TODO: REMOVE THIS
    public void setFakeData(int pk) {
        this.pk = pk;
        this.name = "test";
    }

    public SubCategory(){

    }

}
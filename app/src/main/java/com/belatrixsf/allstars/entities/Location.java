package com.belatrixsf.allstars.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PedroCarrillo on 5/17/16.
 */
public class Location implements Parcelable {

    private Integer pk;
    private String name;
    private String icon;

    public Integer getPk() {
        return pk;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }


    protected Location(Parcel in) {
        pk = in.readByte() == 0x00 ? null : in.readInt();
        name = in.readString();
        icon = in.readString();
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
        dest.writeString(icon);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

}

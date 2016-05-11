package com.belatrixsf.allstars.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PedroCarrillo on 4/26/16.
 */
public class SubCategory extends Category implements Parcelable {

    @SerializedName("num_stars")
    private Integer numStars;
    private Category parentCategory;

    protected SubCategory(Parcel in) {
        super(in);
        numStars = in.readByte() == 0x00 ? null : in.readInt();
        parentCategory = (Category) in.readValue(Category.class.getClassLoader());
    }

    public SubCategory(Category category) {
        setId(category.getId());
        setName(category.getName());
    }

    public Integer getNumStars() {
        return numStars;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        if (numStars == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(numStars);
        }
        dest.writeValue(parentCategory);
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

}
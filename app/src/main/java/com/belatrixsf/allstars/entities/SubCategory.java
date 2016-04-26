package com.belatrixsf.allstars.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PedroCarrillo on 4/26/16.
 */
public class SubCategory {

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

}

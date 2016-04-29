package com.belatrixsf.allstars.networking.retrofit.responses;

import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.entities.SubCategory;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by PedroCarrillo on 4/26/16.
 */
public class StarSubCategoryResponse {

    private int count;
    private String next;
    private String previous;
    @SerializedName("results")
    private List<SubCategory> subCategories;

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

}

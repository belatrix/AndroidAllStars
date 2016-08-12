package com.belatrixsf.connect.networking.retrofit.requests;

import com.google.gson.annotations.SerializedName;

/**
 * Created by echuquilin on 9/08/16.
 */
public class SaveEmployeeKeywordRequest {

    @SerializedName("skill")
    private String name;

    public SaveEmployeeKeywordRequest(String keywordName){
        this.name = keywordName;
    }
}

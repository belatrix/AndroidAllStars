package com.belatrixsf.allstars.networking.retrofit.requests;

/**
 * Created by PedroCarrillo on 4/27/16.
 */
public class StarRequest {

    private int category;
    private int subcategory;
    private String text;
    private int keyword;

    public StarRequest(int category, int subcategory, String text, int keyword) {
        this.category = category;
        this.subcategory = subcategory;
        this.text = text;
        this.keyword = keyword;
    }

}

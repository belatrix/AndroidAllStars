package com.belatrixsf.allstars.networking.retrofit.requests;

/**
 * Created by PedroCarrillo on 4/27/16.
 */
public class StarRequest {

    private int category;
    private int subcategory;
    private String text;

    public StarRequest(int category, int subcategory, String text) {
        this.category = category;
        this.subcategory = subcategory;
        this.text = text;
    }

}

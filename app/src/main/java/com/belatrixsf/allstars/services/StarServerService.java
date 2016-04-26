package com.belatrixsf.allstars.services;

import com.belatrixsf.allstars.networking.retrofit.RetrofitCallback;
import com.belatrixsf.allstars.networking.retrofit.api.StarAPI;
import com.belatrixsf.allstars.networking.retrofit.responses.StarSubCategoryResponse;
import com.belatrixsf.allstars.utils.AllStarsCallback;

/**
 * Created by PedroCarrillo on 4/26/16.
 */
public class StarServerService implements StarService {

    private StarAPI starAPI;

    public StarServerService(StarAPI starAPI) {
        this.starAPI = starAPI;
    }

    @Override
    public void getEmployeeSubCategoriesStars(int employeeId, AllStarsCallback<StarSubCategoryResponse> callback) {
        starAPI.getEmployeeSubCategoriesStars(employeeId).enqueue(new RetrofitCallback<StarSubCategoryResponse>(callback));
    }

}

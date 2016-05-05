package com.belatrixsf.allstars.services;

import com.belatrixsf.allstars.networking.retrofit.RetrofitCallback;
import com.belatrixsf.allstars.networking.retrofit.api.StarAPI;
import com.belatrixsf.allstars.networking.retrofit.requests.StarRequest;
import com.belatrixsf.allstars.networking.retrofit.responses.StarsResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.StarResponse;
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

    @Override
    public void star(int fromEmployeeId, int toEmployeeId, StarRequest starRequest, AllStarsCallback<StarResponse> callback) {
        starAPI.star(fromEmployeeId, toEmployeeId, starRequest).enqueue(new RetrofitCallback<StarResponse>(callback));
    }

    @Override
    public void getStars(int employeeId, int subcategory, AllStarsCallback<StarsResponse> callback) {
        starAPI.getStars(employeeId, subcategory).enqueue(new RetrofitCallback<StarsResponse>(callback));
    }
}

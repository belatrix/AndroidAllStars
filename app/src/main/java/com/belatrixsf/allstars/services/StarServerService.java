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
public class StarServerService extends AllStarsService implements StarService {

    private StarAPI starAPI;

    public StarServerService(StarAPI starAPI) {
        this.starAPI = starAPI;
    }

    @Override
    public void getEmployeeSubCategoriesStars(String requestTag, int employeeId, AllStarsCallback<StarSubCategoryResponse> callback) {
        enqueue(requestTag, starAPI.getEmployeeSubCategoriesStars(employeeId), new RetrofitCallback<StarSubCategoryResponse>(callback));
    }

    @Override
    public void star(String requestTag, int fromEmployeeId, int toEmployeeId, StarRequest starRequest, AllStarsCallback<StarResponse> callback) {
        enqueue(requestTag, starAPI.star(fromEmployeeId, toEmployeeId, starRequest), new RetrofitCallback<StarResponse>(callback));
    }

    @Override
    public void getStars(String requestTag, int employeeId, int subcategory, Integer page, AllStarsCallback<StarsResponse> callback) {
        enqueue(requestTag, starAPI.getStars(employeeId, subcategory, page), new RetrofitCallback<StarsResponse>(callback));
    }
}

package com.belatrixsf.allstars.services;

import com.belatrixsf.allstars.networking.retrofit.RetrofitCallback;
import com.belatrixsf.allstars.networking.retrofit.api.StarAPI;
import com.belatrixsf.allstars.networking.retrofit.requests.StarRequest;
import com.belatrixsf.allstars.networking.retrofit.responses.StarKeywordTopListResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.StarsByKeywordsResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.StarsResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.StarResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.StarSubCategoryResponse;
import com.belatrixsf.allstars.utils.AllStarsCallback;

/**
 * Created by PedroCarrillo on 4/26/16.
 */
public class StarServerService extends AllStarsServerService implements StarService {

    private StarAPI starAPI;

    public StarServerService(StarAPI starAPI) {
        this.starAPI = starAPI;
    }

    @Override
    public void getEmployeeSubCategoriesStars(int employeeId, AllStarsCallback<StarSubCategoryResponse> callback) {
        enqueue(starAPI.getEmployeeSubCategoriesStars(employeeId), new RetrofitCallback<StarSubCategoryResponse>(callback));
    }

    @Override
    public void star(int fromEmployeeId, int toEmployeeId, StarRequest starRequest, AllStarsCallback<StarResponse> callback) {
        enqueue(starAPI.star(fromEmployeeId, toEmployeeId, starRequest), new RetrofitCallback<StarResponse>(callback));
    }

    @Override
    public void getStars(int employeeId, int subcategory, Integer page, AllStarsCallback<StarsResponse> callback) {
        enqueue(starAPI.getStars(employeeId, subcategory, page), new RetrofitCallback<StarsResponse>(callback));
    }

    @Override
    public void getStarsKeywordTopList(int keywordId, Integer page, AllStarsCallback<StarKeywordTopListResponse> callback) {
        enqueue(starAPI.getStarsKeywordTop(keywordId, page), new RetrofitCallback<StarKeywordTopListResponse>(callback));
    }

    @Override
    public void getStarsByKeywords(String search, Integer next, AllStarsCallback<StarsByKeywordsResponse> callback) {
        enqueue(starAPI.getStarsByKeywords(search, next), new RetrofitCallback<StarsByKeywordsResponse>(callback));
    }

}
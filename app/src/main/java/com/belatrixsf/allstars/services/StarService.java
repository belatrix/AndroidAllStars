package com.belatrixsf.allstars.services;

import com.belatrixsf.allstars.networking.retrofit.requests.StarRequest;
import com.belatrixsf.allstars.networking.retrofit.responses.StarsByKeywordsResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.StarKeywordTopListResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.StarsResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.StarResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.StarSubCategoryResponse;
import com.belatrixsf.allstars.utils.AllStarsCallback;

/**
 * Created by PedroCarrillo on 4/26/16.
 */
public interface StarService {

    void getEmployeeSubCategoriesStars(int employeeId, AllStarsCallback<StarSubCategoryResponse> callback);
    void star(int fromEmployeeId, int toEmployeeId, StarRequest starRequest, AllStarsCallback<StarResponse> callback);
    void getStarsByKeywords(String search, Integer next, AllStarsCallback<StarsByKeywordsResponse> callback);
    void getStars(int employeeId, int subcategory, Integer page, AllStarsCallback<StarsResponse> callback);
    void getStarsKeywordTopList(int keywordId, Integer page, AllStarsCallback<StarKeywordTopListResponse> callback);

}

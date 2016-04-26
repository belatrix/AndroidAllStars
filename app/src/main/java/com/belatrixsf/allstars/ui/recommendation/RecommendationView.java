package com.belatrixsf.allstars.ui.recommendation;

import com.belatrixsf.allstars.ui.common.AllStarsView;

/**
 * Created by pedrocarrillo on 4/25/16.
 */
public interface RecommendationView extends AllStarsView {

    void goSearchUser();
    void showUserFullName(String fullName);
    void showUserProfileImage(String image);
    void showUserLevel(String level);

    void goWriteComment();
    void showComment(String comment);

    void goSelectSubcategory();
    void showCategory(String category);

}

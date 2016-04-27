package com.belatrixsf.allstars.ui.givestar;

import com.belatrixsf.allstars.ui.common.AllStarsView;

/**
 * Created by pedrocarrillo on 4/25/16.
 */
public interface GiveStarView extends AllStarsView {

    void goSearchUser();

    void showUser();

    void showUserFullName(String fullName);

    void showUserProfileImage(String image);

    void showUserLevel(String level);

    void goWriteComment(String comment);

    void showComment(String comment);

    void goSelectSubcategory();

    void showCategory(String category);

    void enableContinueButton();

    void finishRecommendation();

}

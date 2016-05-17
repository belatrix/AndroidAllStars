package com.belatrixsf.allstars.ui.account.edit;

import com.belatrixsf.allstars.ui.common.AllStarsView;

/**
 * Created by PedroCarrillo on 5/17/16.
 */
public interface EditAccountView extends AllStarsView {

    void showProfileImage(String imageUrl);
    void showFirstName(String firstName);
    void showLastName(String lastName);
    void showSkypeId(String skypeId);
    void endSuccessfulEdit();
    void showFirstNameError(String error);
    void showLastNameError(String error);
    void showSkypeIdError(String error);

}

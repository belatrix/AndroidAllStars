package com.belatrixsf.connect.ui.skills.Add;

import com.belatrixsf.connect.entities.Keyword;
import com.belatrixsf.connect.ui.common.BelatrixConnectView;

import java.util.List;

/**
 * Created by echuquilin on 10/08/16.
 */
public interface AddSkillView extends BelatrixConnectView {

    void addKeywords(List<Keyword> keywords);
    void showSearchActionMode();
    void resetList();
    void showNoDataView();
    void hideNoDataView();
    void showAddedConfirmation(String message);
    void showAddNewKeywordButton();
    void hideAddNewKeywordButton();
    void showAlreadyExistsConfirmation(String message);
    void hideRefreshData();
}

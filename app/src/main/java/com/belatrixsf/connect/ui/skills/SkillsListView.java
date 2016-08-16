package com.belatrixsf.connect.ui.skills;

import com.belatrixsf.connect.entities.Keyword;
import com.belatrixsf.connect.ui.common.BelatrixConnectView;

import java.util.List;

/**
 * Created by echuquilin on 4/08/16.
 */
public interface SkillsListView extends BelatrixConnectView{
    void addSkills(List<Keyword> skills);
    void resetList();
    void showNoDataView(String message);
    void hideNoDataView();
    void showDeleteConfirmationDialog(String message, Keyword keyword);
    void showDeletedInformation(String message);
    void hideRefreshData();
}

/* The MIT License (MIT)
* Copyright (c) 2016 BELATRIX
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:

* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.

* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/
package com.belatrixsf.connect.ui.stars;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.entities.Category;
import com.belatrixsf.connect.entities.Employee;
import com.belatrixsf.connect.entities.Keyword;
import com.belatrixsf.connect.entities.SubCategory;
import com.belatrixsf.connect.ui.category.CategoriesActivity;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.ui.common.views.AccountSelectionView;
import com.belatrixsf.connect.ui.common.views.DataSelectionView;
import com.belatrixsf.connect.ui.common.views.KeywordSelectionView;
import com.belatrixsf.connect.ui.contacts.ContactsListActivity;
import com.belatrixsf.connect.ui.stars.comment.CommentActivity;
import com.belatrixsf.connect.ui.stars.keyword.KeywordsActivity;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.di.modules.presenters.GiveStarPresenterModule;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by PedroCarrillo on 4/22/16.
 */
public class GiveStarFragment extends BelatrixConnectFragment implements GiveStarView {

    public static final String SELECTED_USER_KEY = "_selected_user_key";
    public static final String SELECTED_KEYWORD_KEY = "_selected_keyword_key";
    public static final String COMMENT_KEY = "_user_comment_key";
    public static final String SELECTED_CATEGORY_KEY = "_selected_category_key";
    public static final String MESSAGE_KEY = "_message_key";
    public static final int RQ_CONTACT = 100;
    public static final int RQ_COMMENT = 101;
    public static final int RQ_SUBCATEGORY = 102;
    public static final int RQ_KEYWORD = 103;

    private GiveStarPresenter giveStarPresenter;
    private MenuItem menuDone;

    @Bind(R.id.account_selection) AccountSelectionView accountSelectionView;
    @Bind(R.id.category_selection) DataSelectionView categorySelectionView;
    @Bind(R.id.comment_selection) DataSelectionView commentSelectionView;
    @Bind(R.id.keyword_selection) KeywordSelectionView keywordSelectionView;

    public static GiveStarFragment newInstance(Employee employee) {
        Bundle bundle = new Bundle();
        if (employee != null) {
            bundle.putParcelable(SELECTED_USER_KEY, employee);
        }
        GiveStarFragment giveStarFragment = new GiveStarFragment();
        giveStarFragment.setArguments(bundle);
        return giveStarFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_give_star, container, false);
    }

    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        giveStarPresenter = belatrixConnectApplication.getApplicationComponent().giveStarComponent(new GiveStarPresenterModule(this)).giveStarPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        } else if (getArguments() != null && getArguments().containsKey(SELECTED_USER_KEY)) {
            Employee employee = getArguments().getParcelable(SELECTED_USER_KEY);
            giveStarPresenter.initWithUser(employee);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        saveState(outState);
        super.onSaveInstanceState(outState);
    }

    private void restoreState(Bundle savedInstanceState) {
        Employee savedEmployee = savedInstanceState.getParcelable(SELECTED_USER_KEY);
        String savedComment = savedInstanceState.getString(COMMENT_KEY);
        SubCategory savedCategory = savedInstanceState.getParcelable(SELECTED_CATEGORY_KEY);
        Keyword savedKeyword = savedInstanceState.getParcelable(SELECTED_KEYWORD_KEY);
        giveStarPresenter.loadSelectedUser(savedEmployee);
        giveStarPresenter.loadSelectedComment(savedComment);
        giveStarPresenter.loadSelectedSubCategory(savedCategory);
        giveStarPresenter.loadSelectedKeyword(savedKeyword);
    }

    private void saveState(Bundle outState) {
        Employee selectedEmployee = giveStarPresenter.getSelectedEmployee();
        String selectedComment = giveStarPresenter.getSelectedComment();
        Category selectedSubCategory = giveStarPresenter.getSelectedSubCategory();
        Keyword selectedKeyword = giveStarPresenter.getSelectedKeyword();
        if (selectedEmployee != null) {
            outState.putParcelable(SELECTED_USER_KEY, selectedEmployee);
        }
        if (selectedComment != null && !selectedComment.isEmpty()) {
            outState.putString(COMMENT_KEY, selectedComment);
        }
        if (selectedSubCategory != null) {
            outState.putParcelable(SELECTED_CATEGORY_KEY, selectedSubCategory);
        }
        if (selectedKeyword != null) {
            outState.putParcelable(SELECTED_KEYWORD_KEY, selectedKeyword);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_recommend, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menuDone = menu.findItem(R.id.action_done);
        giveStarPresenter.checkRecommendationEnabled();
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                giveStarPresenter.makeRecommendation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @OnClick(R.id.account_selection)
    public void accountSelectionViewClicked() {
        giveStarPresenter.userSelectionClicked();
    }

    @OnClick(R.id.category_selection)
    public void categorySelectionViewClicked() {
        giveStarPresenter.categorySelectionClicked();
    }

    @OnClick(R.id.comment_selection)
    public void commentSelectionViewClicked() {
        giveStarPresenter.commentSelectionClicked();
    }

    @OnClick(R.id.keyword_selection)
    public void keywordSelectionViewClicked() {
        giveStarPresenter.keywordSelectionClicked();
    }

    @Override
    public void goSearchUser() {
        Intent intent = new Intent(getActivity(), ContactsListActivity.class);
        intent.putExtra(ContactsListActivity.PROFILE_ENABLED_KEY, false);
        startActivityForResult(intent, RQ_CONTACT);
    }

    @Override
    public void showUser() {
        accountSelectionView.showData();
    }

    @Override
    public void showUserFullName(String fullName) {
        accountSelectionView.setFullName(fullName);
    }

    @Override
    public void showUserProfileImage(String image) {
        accountSelectionView.setProfileImage(image);
    }

    @Override
    public void showUserLevel(String level) {
        accountSelectionView.setLevel(level);
    }

    @Override
    public void showCommentHint() {
        commentSelectionView.showHint();
    }

    @Override
    public void goWriteComment(String comment) {
        Intent intent = new Intent(getActivity(), CommentActivity.class);
        intent.putExtra(COMMENT_KEY, comment);
        startActivityForResult(intent, RQ_COMMENT);
    }

    @Override
    public void showComment(String comment) {
        commentSelectionView.setData(comment);
        commentSelectionView.showData();
    }

    @Override
    public void goSelectSubcategory() {
        Intent intent = new Intent(getActivity(), CategoriesActivity.class);
        startActivityForResult(intent, RQ_SUBCATEGORY);
    }

    @Override
    public void showCategory(String category) {
        categorySelectionView.setData(category);
        categorySelectionView.showData();
    }

    @Override
    public void goSelectKeyword() {
        startActivityForResult(new Intent(getActivity(), KeywordsActivity.class), RQ_KEYWORD);
    }

    @Override
    public void showKeywordSelected(String keyword) {
        keywordSelectionView.setKeyword(keyword);
        keywordSelectionView.showData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RQ_CONTACT) {
                giveStarPresenter.loadSelectedUser((Employee) data.getParcelableExtra(SELECTED_USER_KEY));
            } else if (requestCode == RQ_COMMENT) {
                giveStarPresenter.loadSelectedComment(data.getStringExtra(COMMENT_KEY));
            } else if (requestCode == RQ_SUBCATEGORY) {
                giveStarPresenter.loadSelectedSubCategory((SubCategory) data.getParcelableExtra(CategoriesActivity.SUBCATEGORY_KEY));
            } else if (requestCode == RQ_KEYWORD) {
                giveStarPresenter.loadSelectedKeyword((Keyword) data.getParcelableExtra(KeywordsActivity.KEYWORD_KEY));
            }
        }
    }

    @Override
    public void finishRecommendation() {
        Intent intent = new Intent();
        intent.putExtra(MESSAGE_KEY, getString(R.string.success_recommendation));
        fragmentListener.setActivityResult(Activity.RESULT_OK, intent);
        fragmentListener.closeActivity();
    }

    @Override
    public void blockWithUserSelected() {
        accountSelectionView.setArrowVisibility(View.GONE);
        accountSelectionView.setClickable(false);
    }

    @Override
    public void showDoneMenu(boolean show) {
        if (menuDone != null) {
            menuDone.setEnabled(show);
        }
    }

    @Override
    public void onDestroyView() {
        giveStarPresenter.cancelRequests();
        super.onDestroyView();
    }
}
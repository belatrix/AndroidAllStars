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
package com.belatrixsf.connect.ui.keywords;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.adapters.KeywordsFilterListAdapter;
import com.belatrixsf.connect.entities.Keyword;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.ui.common.views.DividerItemDecoration;
import com.belatrixsf.connect.ui.common.views.searchingview.SearchingView;
import com.belatrixsf.connect.ui.stars.keyword.KeywordsListListener;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.Constants;
import com.belatrixsf.connect.utils.DialogUtils;
import com.belatrixsf.connect.utils.KeyboardUtils;
import com.belatrixsf.connect.utils.di.modules.presenters.KeywordsListModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by gyosida on 5/9/16.
 */
public class SearchingKeywordsFragment extends BelatrixConnectFragment implements SearchingKeywordsView, KeywordsFilterListAdapter.KeywordListener {

    private static final String KEYWORDS_KEY = "_keywords_key";
    private static final String SEARCH_TEXT_KEY = "_search_text_key";
    private static final String SEARCHING_KEY = "_searching_key";

    private KeywordsFilterListAdapter keywordsListAdapter;

    @Inject SearchingKeywordsPresenter keywordsPresenter;

    @Bind(R.id.keywords) RecyclerView keywordsRecyclerView;
    @Bind(R.id.refresh_keywords) SwipeRefreshLayout keywordsRefreshLayout;
    @Bind(R.id.no_data_textview) TextView noDataTextView;
    @Bind(R.id.button_add_new_skill) Button addNewSkillButton;

    private String newKeywordName;

    public SearchingKeywordsFragment() {
        // Required empty public constructor
    }

    public static SearchingKeywordsFragment newInstance() {
        return new SearchingKeywordsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_keywords_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        }
        keywordsPresenter.getKeywords();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                keywordsPresenter.searchKeywords();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        saveState(outState);
        super.onSaveInstanceState(outState);
    }

    private void restoreState(Bundle savedInstanceState) {
        List<Keyword> keywords = savedInstanceState.getParcelableArrayList(KEYWORDS_KEY);
        String searchText = savedInstanceState.getString(SEARCH_TEXT_KEY, null);
        boolean searching = savedInstanceState.getBoolean(SEARCHING_KEY, false);
        keywordsPresenter.loadPresenterState(keywords, searchText, searching);
    }

    private void saveState(Bundle outState) {
        outState.putString(SEARCH_TEXT_KEY, keywordsPresenter.getSearchText());
        outState.putBoolean(SEARCHING_KEY, keywordsPresenter.isSearching());
        List<Keyword> keywords = keywordsPresenter.getKeywordsSync();
        if (keywords != null && keywords instanceof ArrayList) {
            outState.putParcelableArrayList(KEYWORDS_KEY, (ArrayList<Keyword>) keywords);
        }
    }

    private void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        keywordsListAdapter = new KeywordsFilterListAdapter();
        keywordsListAdapter.setKeywordListener(this);
        keywordsRecyclerView.setAdapter(keywordsListAdapter);
        keywordsRecyclerView.setLayoutManager(linearLayoutManager);
        keywordsRecyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));
        keywordsRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                keywordsRefreshLayout.setRefreshing(true);
                keywordsPresenter.getKeywords();
            }
        });
        keywordsRefreshLayout.setColorSchemeResources(R.color.swipe_refresh);
    }

    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        belatrixConnectApplication
                .getApplicationComponent()
                .keywordsListComponent(new KeywordsListModule(this))
                .inject(this);
    }


    @OnClick(R.id.button_add_new_skill)
    public void onClickAddNewKeyword(){
        keywordsPresenter.addNewKeyword(newKeywordName);
    }

    @Override
    public void showProgressIndicator() {
        if (keywordsRefreshLayout != null) {
            keywordsRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideProgressIndicator() {
        if (keywordsRefreshLayout != null) {
            keywordsRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showKeywords(List<Keyword> keywords) {
        keywordsListAdapter.update(keywords);
    }

    @Override
    public void showKeywordDetail(Keyword keyword) {
        if (getActivity() instanceof KeywordsListListener) {
            ((KeywordsListListener) getActivity()).onKeywordSelectedForDispatching(keyword);
        }
    }

    @Override
    public void showSearchActionMode() {
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
        }
    }

    @Override
    public void resetList() {
        keywordsListAdapter.reset();
    }

    @Override
    public void onKeywordSelected(Keyword keyword) {
        keywordsPresenter.onKeywordSelected(keyword);
    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            SearchingView searchingView = new SearchingView(getActivity());
            searchingView.setCustomHint(getString(R.string.hint_keyword_search));
            searchingView.setInputRegex(Constants.REGEX_ONLY_LETTERS);
            searchingView.setSearchingListener(new SearchingView.SearchingListener() {
                @Override
                public void onSearchingTextTyped(String searchText) {
                        updateNewKeywordName(searchText);
                        keywordsListAdapter.getFilter().filter(searchText);
                }
            });
            mode.setCustomView(searchingView);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            resetAddTagButton();
            keywordsPresenter.stopSearchingKeywords();
            KeyboardUtils.hideKeyboard(getActivity(), getView());
            keywordsListAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void showAddedConfirmation(Keyword keyword) {
        keywordsListAdapter.getFilter().filter(keyword.getName());
        String message = "\"" +keyword.getName() + "\" " + getString(R.string.dialog_confirmation_added);
        fragmentListener.showSnackBar(message);
        KeyboardUtils.hideKeyboard(getActivity(), getView());
    }

    @Override
    public void showErrorConfirmation(String keywordName) {
        String message = "\"" +keywordName + "\" " + getString(R.string.dialog_title_error);
        fragmentListener.showSnackBar(message);
    }

    @Override
    public void showAlreadyExistsConfirmation(String skillName) {
        String message = "\"" + skillName + "\" " + getString(R.string.dialog_confirmation_already_exists);
        DialogUtils.createInformationDialog(getActivity(), message, getString(R.string.app_name), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //nothing
            }
        }).show();
    }

    @Override
    public void showNoDataView() {
        noDataTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoDataView() {
        noDataTextView.setVisibility(View.GONE);
    }


    private void resetAddTagButton(){
        addNewSkillButton.setVisibility(View.GONE);
        addNewSkillButton.setText("");
    }

    private void updateNewKeywordName(String newName){
        if (newName != null && !newName.isEmpty()){
            addNewSkillButton.setVisibility(View.VISIBLE);
            newName = newName.toLowerCase();
            String buttonMessage = getString(R.string.skills_button_create_new_first,newName);
            this.newKeywordName = newName;
            this.addNewSkillButton.setText(buttonMessage);
        } else {
            addNewSkillButton.setVisibility(View.GONE);
        }

    }
}

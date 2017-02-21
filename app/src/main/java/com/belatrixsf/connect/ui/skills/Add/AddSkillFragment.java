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
package com.belatrixsf.connect.ui.skills.Add;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
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
import com.belatrixsf.connect.adapters.KeywordsListAdapter;
import com.belatrixsf.connect.entities.Keyword;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.ui.common.EndlessRecyclerOnScrollListener;
import com.belatrixsf.connect.ui.common.views.DividerItemDecoration;
import com.belatrixsf.connect.ui.common.views.searchingview.SearchingView;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.DialogUtils;
import com.belatrixsf.connect.utils.KeyboardUtils;
import com.belatrixsf.connect.utils.di.modules.presenters.AddSkillPresenterModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by echuquilin on 10/08/16.
 */
public class AddSkillFragment extends BelatrixConnectFragment implements AddSkillView, KeywordsListAdapter.KeywordListener {

    private static final String KEYWORDS_KEY = "_keywords_key";
    private static final String SEARCH_TEXT_KEY = "_search_text_key";
    private static final String PAGING_KEY = "_paging_key";
    private static final String SEARCHING_KEY = "_searching_key";

    private KeywordsListAdapter keywordsListAdapter;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;
    private String newKeywordName;

    @Inject AddSkillPresenter keywordsPresenter;

    @Bind(R.id.keywords) RecyclerView keywordsRecyclerView;
    @Bind(R.id.refresh_keywords) SwipeRefreshLayout keywordsRefreshLayout;
    @Bind(R.id.no_data_textview) TextView noDataTextView;
    @Bind(R.id.button_add_new_skill) Button addNewSkillButton;
    @Bind(R.id.coordinator_keywords) CoordinatorLayout keywordscoordinatorLayout;

    public AddSkillFragment() {
        // Required empty public constructor
    }

    public static AddSkillFragment newInstance() {
        return new AddSkillFragment();
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
        keywordsPresenter.getKeywords(false);
        keywordsPresenter.updateSkillsList();
    }


    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        keywordsPresenter = belatrixConnectApplication.getApplicationComponent()
                .addSkillComponent(new AddSkillPresenterModule(this)).addSkillPresenter();
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

    private void saveState(Bundle outState) {
        outState.putString(SEARCH_TEXT_KEY, keywordsPresenter.getSearchText());
        outState.putParcelable(PAGING_KEY, keywordsPresenter.getKeywordsPaging());
        outState.putBoolean(SEARCHING_KEY, keywordsPresenter.isSearching());
        List<Keyword> keywords = keywordsPresenter.getKeywordsSync();
        if (keywords != null && keywords instanceof ArrayList) {
            outState.putParcelableArrayList(KEYWORDS_KEY, (ArrayList<Keyword>) keywords);
        }
    }

    private void restoreState(Bundle savedInstanceState) {
        List<Keyword> keywords = savedInstanceState.getParcelableArrayList(KEYWORDS_KEY);
        PaginatedResponse paging = savedInstanceState.getParcelable(PAGING_KEY);
        String searchText = savedInstanceState.getString(SEARCH_TEXT_KEY, null);
        boolean searching = savedInstanceState.getBoolean(SEARCHING_KEY, false);
        keywordsPresenter.loadPresenterState(keywords, paging, searchText, searching);
    }

    private void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                keywordsPresenter.callNextPage();
            }
        };
        keywordsListAdapter = new KeywordsListAdapter();
        keywordsListAdapter.setKeywordListener(this);
        keywordsRecyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
        keywordsRecyclerView.setAdapter(keywordsListAdapter);
        keywordsRecyclerView.setLayoutManager(linearLayoutManager);
        keywordsRecyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));
        keywordsRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                keywordsPresenter.refreshKeywords(true);
            }
        });
        keywordsRefreshLayout.setColorSchemeResources(R.color.swipe_refresh);

        addNewSkillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keywordsPresenter.addKeywordToEmployee(newKeywordName);
            }
        });
    }

    @Override
    public void showProgressIndicator() {
        if (keywordsListAdapter != null) {
            keywordsListAdapter.setLoading(true);
        }
        if (endlessRecyclerOnScrollListener != null) {
            endlessRecyclerOnScrollListener.setLoading(true);
        }
    }

    @Override
    public void hideProgressIndicator() {
        if (keywordsListAdapter != null) {
            keywordsListAdapter.setLoading(false);
        }
        if (endlessRecyclerOnScrollListener != null) {
            endlessRecyclerOnScrollListener.setLoading(false);
        }
        if (keywordsRefreshLayout != null) {
            keywordsRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void addKeywords(List<Keyword> keywords) {
        keywordsListAdapter.add(keywords);
    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            SearchingView searchingView = new SearchingView(getActivity());
            searchingView.setSearchingListener(new SearchingView.SearchingListener() {
                @Override
                public void onSearchingTextTyped(String searchText) {
                    updateNewKeywordName(searchText);
                    keywordsPresenter.getKeywords(searchText, keywordsRefreshLayout.isRefreshing());
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
            keywordsPresenter.stopSearchingKeywords();
            KeyboardUtils.hideKeyboard(getActivity(), getView());
        }
    };

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
    public void onKeywordSelected(int position) {
        keywordsPresenter.onKeywordSelected(position);
    }

    private void updateNewKeywordName(String newName){
        String buttonMessage = getString(R.string.skills_button_add_new_first)
                + " " + newName + " " + getString(R.string.skills_button_add_new_second) ;
        this.newKeywordName = newName;
        this.addNewSkillButton.setText(buttonMessage);
    }

    @Override
    public void showNoDataView() {
        noDataTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoDataView() {
        noDataTextView.setVisibility(View.GONE);
    }

    @Override
    public void showAddedConfirmation(String skillName) {
        String message = skillName + " " + getString(R.string.dialog_confirmation_added);
        fragmentListener.showSnackBar(message);
    }

    @Override
    public void showAlreadyExistsConfirmation(String skillName) {
        String message = skillName + " " + getString(R.string.dialog_confirmation_already_exists);
        DialogUtils.createInformationDialog(getActivity(), message, getString(R.string.app_name), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //nothing
            }
        }).show();
    }

    @Override
    public void hideRefreshData() {
        if (keywordsRefreshLayout != null) {
            keywordsRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showAddNewKeywordButton() {
        this.addNewSkillButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAddNewKeywordButton() {
        this.addNewSkillButton.setVisibility(View.GONE);
    }

}

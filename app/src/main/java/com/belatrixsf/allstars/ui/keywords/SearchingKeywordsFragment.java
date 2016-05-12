package com.belatrixsf.allstars.ui.keywords;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.adapters.KeywordsListAdapter;
import com.belatrixsf.allstars.entities.Keyword;
import com.belatrixsf.allstars.ui.common.AllStarsFragment;
import com.belatrixsf.allstars.ui.common.views.DividerItemDecoration;
import com.belatrixsf.allstars.ui.common.views.searchingview.SearchingView;
import com.belatrixsf.allstars.utils.AllStarsApplication;
import com.belatrixsf.allstars.utils.KeyboardUtils;
import com.belatrixsf.allstars.utils.di.modules.presenters.KeywordsListModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class SearchingKeywordsFragment extends AllStarsFragment implements SearchingKeywordsView, KeywordsListAdapter.KeywordListener {

    private KeywordsListAdapter keywordsListAdapter;

    @Inject SearchingKeywordsPresenter keywordsPresenter;

    @Bind(R.id.keywords) RecyclerView keywords;


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

    private void initViews() {
        keywordsListAdapter = new KeywordsListAdapter();
        keywordsListAdapter.setKeywordListener(this);
        keywords.setLayoutManager(new LinearLayoutManager(getActivity()));
        keywords.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));
        keywords.setAdapter(keywordsListAdapter);
    }

    @Override
    protected void initDependencies(AllStarsApplication allStarsApplication) {
        allStarsApplication
                .getApplicationComponent()
                .keywordsListComponent(new KeywordsListModule(this))
                .inject(this);
    }

    @Override
    public void addKeywords(List<Keyword> keywords) {
        keywordsListAdapter.add(keywords);
    }

    @Override
    public void showKeywordDetail(Keyword keyword) {
        //TODO: display detail
    }

    @Override
    public void showSearchActionMode() {
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
        }
    }

    @Override
    public void onKeywordSelected(int position) {
        keywordsPresenter.onKeywordSelected(position);
    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            SearchingView searchingView = new SearchingView(getActivity());
            searchingView.setSearchingListener(new SearchingView.SearchingListener() {
                @Override
                public void onSearchingTextTyped(String searchText) {
                    keywordsPresenter.getKeywords(searchText);
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

}

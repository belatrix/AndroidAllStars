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

public class KeywordsListFragment extends AllStarsFragment implements KeywordsListView {

    private static final String MODE_KEY = "mode_key";

    private KeywordsListAdapter keywordsListAdapter;
    private int modeCode;

    @Inject KeywordsListPresenter keywordsListPresenter;

    @Bind(R.id.keywords) RecyclerView keywords;


    public KeywordsListFragment() {
        // Required empty public constructor
    }

    public static KeywordsListFragment newInstance(int mode) {
        KeywordsListFragment fragment = new KeywordsListFragment();
        Bundle args = new Bundle();
        args.putInt(MODE_KEY, mode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            modeCode = getArguments().getInt(MODE_KEY, KeywordsMode.LIST.getCode());
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_keywords_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        keywordsListPresenter.getKeywords();
    }

    private void initViews() {
        keywordsListAdapter = new KeywordsListAdapter();
        keywords.setLayoutManager(new LinearLayoutManager(getActivity()));
        keywords.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));
        keywords.setAdapter(keywordsListAdapter);
    }

    @Override
    protected void initDependencies(AllStarsApplication allStarsApplication) {
        allStarsApplication
                .getApplicationComponent()
                .keywordsListComponent(new KeywordsListModule(this, matchKeywordCode()))
                .inject(this);
    }

    private KeywordsMode matchKeywordCode() {
        for (KeywordsMode keywordsMode : KeywordsMode.values()) {
            if (keywordsMode.getCode() == modeCode) {
                return keywordsMode;
            }
        }
        return KeywordsMode.LIST;
    }

    @Override
    public void addKeywords(List<Keyword> keywords) {
        keywordsListAdapter.add(keywords);
    }

    @Override
    public void deliverSelectedKeyword(Keyword keyword) {

    }

    @Override
    public void showSearchActionMode(boolean show) {
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
        }
    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            SearchingView searchingView = new SearchingView(getActivity());
            searchingView.setSearchingListener(new SearchingView.SearchingListener() {
                @Override
                public void onSearchingTextTyped(String searchText) {
                    if (keywordsListPresenter instanceof SearchingKeywordsListPresenter) {
                        ((SearchingKeywordsListPresenter) keywordsListPresenter).getKeywords(searchText);
                    }
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
            KeyboardUtils.hideKeyboard(getActivity(), getView());
        }
    };

}

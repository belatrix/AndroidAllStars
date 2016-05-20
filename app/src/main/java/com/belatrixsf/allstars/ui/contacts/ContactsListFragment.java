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
package com.belatrixsf.allstars.ui.contacts;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.adapters.ContactsListAdapter;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.allstars.ui.account.AccountActivity;
import com.belatrixsf.allstars.ui.common.AllStarsFragment;
import com.belatrixsf.allstars.ui.common.EndlessRecyclerOnScrollListener;
import com.belatrixsf.allstars.ui.common.RecyclerOnItemClickListener;
import com.belatrixsf.allstars.ui.common.views.DividerItemDecoration;
import com.belatrixsf.allstars.ui.common.views.searchingview.SearchingView;
import com.belatrixsf.allstars.utils.AllStarsApplication;
import com.belatrixsf.allstars.utils.KeyboardUtils;
import com.belatrixsf.allstars.utils.di.modules.presenters.ContactsListPresenterModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.belatrixsf.allstars.ui.stars.GiveStarFragment.SELECTED_USER_KEY;

/**
 * Created by icerrate on 15/04/2016.
 */
public class ContactsListFragment extends AllStarsFragment implements ContactsListView, RecyclerOnItemClickListener {

    public static final String PROFILE_ENABLED_KEY = "_is_search";
    public static final String CONTACTS_KEY = "_employees_key";
    public static final String ACTION_MODE_KEY = "_action_mode_key";
    public static final String PAGINATION_RESPONSE_KEY = "_pagination_response_key";
    public static final String CURRENT_PAGE_KEY = "_current_page_key";
    public static final String SEARCH_TERM_KEY = "_search_term_key";

    private ContactsListPresenter contactsListPresenter;
    private ContactsListAdapter contactsListAdapter;
    private ContactsListFragmentListener contactsListFragmentListener;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

    private ImageView photoImageView;

    @Bind(R.id.employees) RecyclerView contactsRecyclerView;

    public static ContactsListFragment newInstance(boolean profileEnabled) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(PROFILE_ENABLED_KEY, profileEnabled);
        ContactsListFragment contactsListFragment = new ContactsListFragment();
        contactsListFragment.setArguments(bundle);
        return contactsListFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        castOrThrowException(activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        castOrThrowException(context);
    }

    private void castOrThrowException(Context context) {
        try {
            contactsListFragmentListener = (ContactsListFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ContactsListFragmentListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts_list, container, false);
    }

    @Override
    protected void initDependencies(AllStarsApplication allStarsApplication) {
        contactsListPresenter = allStarsApplication.getApplicationComponent()
                .contactsListComponent(new ContactsListPresenterModule(this))
                .contactsListPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        boolean hasArguments = (getArguments() != null && getArguments().containsKey(PROFILE_ENABLED_KEY));
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        } else if (hasArguments){
            contactsListPresenter.setProfileEnabled(getArguments().getBoolean(PROFILE_ENABLED_KEY));
            contactsListPresenter.getContacts();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        saveState(outState);
        super.onSaveInstanceState(outState);
    }

    private void restoreState(Bundle savedInstanceState) {
        List<Employee> savedContacts = savedInstanceState.getParcelableArrayList(CONTACTS_KEY);
        boolean actionModeEnabled = savedInstanceState.getBoolean(ACTION_MODE_KEY);
        Integer currentPage = savedInstanceState.getInt(CURRENT_PAGE_KEY);
        PaginatedResponse paginatedResponse = savedInstanceState.getParcelable(PAGINATION_RESPONSE_KEY);
        String searchTerm = savedInstanceState.getString(SEARCH_TERM_KEY);
        boolean profileEnabled = savedInstanceState.getBoolean(PROFILE_ENABLED_KEY);
        contactsListPresenter.setProfileEnabled(profileEnabled);
        contactsListPresenter.setLoadedContacts(actionModeEnabled, savedContacts, currentPage, paginatedResponse, searchTerm);
    }

    private void saveState(Bundle outState) {
        List<Employee> contactsList = contactsListPresenter.getLoadedContacts();
        if (contactsList != null && contactsList instanceof ArrayList) {
            outState.putParcelableArrayList(CONTACTS_KEY, (ArrayList<Employee>) contactsList);
        }
        outState.putBoolean(ACTION_MODE_KEY, contactsListPresenter.isInActionMode());
        outState.putInt(CURRENT_PAGE_KEY, contactsListPresenter.getCurrentPage());
        outState.putParcelable(PAGINATION_RESPONSE_KEY, contactsListPresenter.getContactPaginatedResponse());
        outState.putString(SEARCH_TERM_KEY, contactsListPresenter.getSearchTerm());
        outState.putBoolean(PROFILE_ENABLED_KEY, contactsListPresenter.getProfileEnabled());
    }

    private void initViews() {
        contactsListAdapter = new ContactsListAdapter(this);
        contactsRecyclerView.setAdapter(contactsListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        contactsRecyclerView.setLayoutManager(linearLayoutManager);
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                contactsListPresenter.getContacts(currentPage);
            }
        };
        contactsRecyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
        contactsRecyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));
    }

    @Override
    public void showContacts(List<Employee> contacts) {
        contactsListAdapter.updateData(contacts);
    }

    @Override
    public void showProgressIndicator() {
        super.showProgressIndicator();
        contactsListAdapter.setLoading(true);
        endlessRecyclerOnScrollListener.setLoading(true);
    }

    @Override
    public void hideProgressIndicator() {
        super.hideProgressIndicator();
        contactsListAdapter.setLoading(false);
        endlessRecyclerOnScrollListener.setLoading(false);
    }

    @Override
    public void showCurrentPage(int currentPage) {
        endlessRecyclerOnScrollListener.setCurrentPage(currentPage);
    }

    @Override
    public void startActionMode() {
        contactsListFragmentListener.setActionMode(actionModeCallback);
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
                contactsListFragmentListener.setActionMode(actionModeCallback);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(final ActionMode mode, Menu menu) {
            contactsListPresenter.startActionMode();
            SearchingView searchingView = new SearchingView(getActivity());
            searchingView.setSearchingListener(new SearchingView.SearchingListener() {
                @Override
                public void onSearchingTextTyped(String searchText) {
                    contactsListPresenter.getContacts(searchText);
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
            return  false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            contactsListPresenter.finishActionMode();
            KeyboardUtils.hideKeyboard(getActivity(), getView());
        }
    };

    @Override
    public void onClick(View v) {
        photoImageView = ButterKnife.findById(v, R.id.contact_photo);
        contactsListPresenter.onContactClicked(v.getTag());
    }

    @Override
    public void goContactProfile(Integer id) {
        AccountActivity.startActivityAnimatingProfilePic(getActivity(), photoImageView, id);
    }

    @Override
    public void selectContact(Employee contact) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(SELECTED_USER_KEY, contact);
        fragmentListener.setActivityResult(Activity.RESULT_OK, resultIntent);
        fragmentListener.closeActivity();
    }

}
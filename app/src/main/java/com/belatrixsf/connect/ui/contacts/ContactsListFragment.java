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
package com.belatrixsf.connect.ui.contacts;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.adapters.ContactsListAdapter;
import com.belatrixsf.connect.entities.Employee;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.ui.account.AccountActivity;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.ui.common.EndlessRecyclerOnScrollListener;
import com.belatrixsf.connect.ui.common.RecyclerOnItemClickListener;
import com.belatrixsf.connect.ui.common.views.DividerItemDecoration;
import com.belatrixsf.connect.ui.common.views.searchingview.SearchingView;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.KeyboardUtils;
import com.belatrixsf.connect.utils.di.modules.presenters.ContactsListPresenterModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.belatrixsf.connect.ui.stars.GiveStarFragment.SELECTED_USER_KEY;

/**
 * Created by icerrate on 15/04/2016.
 */
public class ContactsListFragment extends BelatrixConnectFragment implements ContactsListView, RecyclerOnItemClickListener {

    public static final String CONTACTS_KEY = "_employees_key";
    public static final String SEARCH_TEXT_KEY = "_search_text_key";
    public static final String PAGING_KEY = "_paging_key";
    public static final String SEARCHING_KEY = "_searching_key";

    private ContactsListPresenter contactsListPresenter;
    private ContactsListAdapter contactsListAdapter;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

    private ImageView photoImageView;

    @Bind(R.id.employees) RecyclerView contactsRecyclerView;

    @Bind(R.id.no_data_textview)
    TextView noDataTextView;

    public static ContactsListFragment newInstance(boolean profileEnabled) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContactsListActivity.PROFILE_ENABLED_KEY, profileEnabled);
        ContactsListFragment contactsListFragment = new ContactsListFragment();
        contactsListFragment.setArguments(bundle);
        return contactsListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_contacts_list, container, false);
    }

    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        contactsListPresenter = belatrixConnectApplication.getApplicationComponent()
                .contactsListComponent(new ContactsListPresenterModule(this))
                .contactsListPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        boolean hasArguments = (getArguments() != null && getArguments().containsKey(ContactsListActivity.PROFILE_ENABLED_KEY));
        if (savedInstanceState != null) {
            restorePresenterState(savedInstanceState);
        } else if (hasArguments) {
            contactsListPresenter.setProfileEnabled(getArguments().getBoolean(ContactsListActivity.PROFILE_ENABLED_KEY));
        }
        contactsListPresenter.getContacts();
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
                contactsListPresenter.searchContacts();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        savePresenterState(outState);
        super.onSaveInstanceState(outState);
    }

    private void restorePresenterState(Bundle savedInstanceState) {
        List<Employee> contacts = savedInstanceState.getParcelableArrayList(CONTACTS_KEY);
        boolean searching = savedInstanceState.getBoolean(SEARCHING_KEY, false);
        PaginatedResponse paging = savedInstanceState.getParcelable(PAGING_KEY);
        String searchText = savedInstanceState.getString(SEARCH_TEXT_KEY, null);
        boolean profileEnabled = savedInstanceState.getBoolean(ContactsListActivity.PROFILE_ENABLED_KEY);
        contactsListPresenter.setProfileEnabled(profileEnabled);
        contactsListPresenter.load(contacts, paging, searchText, searching);
    }

    private void savePresenterState(Bundle outState) {
        outState.putBoolean(ContactsListActivity.PROFILE_ENABLED_KEY, contactsListPresenter.getProfileEnabled());
        outState.putBoolean(SEARCHING_KEY, contactsListPresenter.isSearching());
        outState.putParcelable(PAGING_KEY, contactsListPresenter.getContactsPaging());
        outState.putString(SEARCH_TEXT_KEY, contactsListPresenter.getSearchText());
        List<Employee> contacts = contactsListPresenter.getContactsSync();
        if (contacts != null && contacts instanceof ArrayList) {
            outState.putParcelableArrayList(CONTACTS_KEY, (ArrayList<Employee>) contacts);
        }
    }

    private void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                contactsListPresenter.callNextPage();
            }
        };
        contactsListAdapter = new ContactsListAdapter(this);
        contactsRecyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
        contactsRecyclerView.setAdapter(contactsListAdapter);
        contactsRecyclerView.setLayoutManager(linearLayoutManager);
        contactsRecyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));
    }

    @Override
    public void showProgressIndicator() {
        contactsListAdapter.setLoading(true);
        endlessRecyclerOnScrollListener.setLoading(true);
    }

    @Override
    public void hideProgressIndicator() {
        contactsListAdapter.setLoading(false);
        endlessRecyclerOnScrollListener.setLoading(false);
    }

    @Override
    public void addContacts(List<Employee> contacts) {
        contactsListAdapter.add(contacts);
    }

    @Override
    public void showSearchActionMode() {
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
        }
    }

    @Override
    public void resetList() {
        contactsListAdapter.reset();
    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(final ActionMode mode, Menu menu) {
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
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            contactsListPresenter.stopSearchingContacts();
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

    @Override
    public void showNoDataView() {
        noDataTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoDataView() {
        noDataTextView.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        contactsListPresenter.cancelRequests();
        super.onDestroyView();
    }
}
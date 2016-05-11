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
import com.belatrixsf.allstars.ui.account.AccountActivity;
import com.belatrixsf.allstars.ui.common.AllStarsFragment;
import com.belatrixsf.allstars.ui.common.EndlessRecyclerOnScrollListener;
import com.belatrixsf.allstars.ui.common.RecyclerOnItemClickListener;
import com.belatrixsf.allstars.ui.common.views.DividerItemDecoration;
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

    public static final String ARG_PROFILE_ENABLED_KEY = "_is_search";
    private static final String STATE_EMPLOYEES_KEY = "employees_key";
    private static final String STATE_ACTION_MODE_KEY = "action_mode_key";
    private static final String CURRENT_PAGE_KEY = "_current_page_key";

    private ContactsListPresenter contactsListPresenter;
    private ContactsListFragmentListener contactsListFragmentListener;
    private ContactsListAdapter contactsListAdapter;

    private EditText searchTermEditText;
    private ImageButton cleanImageButton;
    private ImageView photoImageView;

    @Bind(R.id.employees) RecyclerView contactsRecyclerView;

    public static ContactsListFragment newInstance(boolean profileEnabled) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ARG_PROFILE_ENABLED_KEY, profileEnabled);
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
        if (getArguments() != null && getArguments().containsKey(ARG_PROFILE_ENABLED_KEY)) {
            contactsListPresenter.setProfileEnabled(getArguments().getBoolean(ARG_PROFILE_ENABLED_KEY));
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
            contactsListPresenter.shouldShowActionMode();
        }else{
            contactsListPresenter.setCurrentPage(1);
            contactsListPresenter.setHasNextPage(true);
        }
        contactsListPresenter.getContacts();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        saveState(outState);
        super.onSaveInstanceState(outState);
    }

    private void restoreState(Bundle savedInstanceState) {
        List<Employee> savedContacts = savedInstanceState.getParcelableArrayList(STATE_EMPLOYEES_KEY);
        boolean actionModeEnabled = savedInstanceState.getBoolean(STATE_ACTION_MODE_KEY);
        int currentPage = savedInstanceState.getInt(CURRENT_PAGE_KEY);
        contactsListPresenter.loadSavedContacts(savedContacts);
        contactsListPresenter.setInActionMode(actionModeEnabled);
        contactsListPresenter.setCurrentPage(currentPage);
    }

    private void saveState(Bundle outState) {
        List<Employee> forSavingContacts = contactsListPresenter.getForSavingContacts();
        boolean forSavingActionMode = contactsListPresenter.isInActionMode();
        int forSavingCurrentPage = contactsListPresenter.getCurrentPage();
        if (forSavingContacts != null && forSavingContacts instanceof ArrayList) {
            outState.putParcelableArrayList(STATE_EMPLOYEES_KEY, (ArrayList<Employee>) forSavingContacts);
            outState.putBoolean(STATE_ACTION_MODE_KEY, forSavingActionMode);
            outState.putInt(CURRENT_PAGE_KEY, forSavingCurrentPage);
        }
    }

    private void initViews() {
        contactsListAdapter = new ContactsListAdapter(this);
        contactsRecyclerView.setAdapter(contactsListAdapter);
        contactsRecyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));
        preparePagination();
    }

    private void preparePagination(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        contactsRecyclerView.setLayoutManager(linearLayoutManager);
        contactsRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                if (contactsListAdapter != null && contactsListPresenter.hasNextPage()) {
                    contactsListPresenter.setCurrentPage(currentPage);
                    contactsListPresenter.getContacts();
                }
            }
        });
    }

    @Override
    public void showContacts(int currentPage, List<Employee> contacts) {
        contactsListAdapter.updatePaginationData(currentPage, 20, contacts);
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
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                //ActionMode
                contactsListFragmentListener.setActionMode(actionModeCallback);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(final ActionMode mode, Menu menu) {
            contactsListPresenter.setInActionMode(true);
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View customView = inflater.inflate(R.layout.item_action_mode, null);

            searchTermEditText = (EditText) customView.findViewById(R.id.search_term);
            cleanImageButton = (ImageButton) customView.findViewById(R.id.clean);

            cleanImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchTermEditText.setText("");
                    searchTermEditText.requestFocus();
                }
            });
            searchTermEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    contactsListPresenter.onSearchTermChange(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            searchTermEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH){
                        contactsListPresenter.submitSearchTerm(v.getText().toString());
                        KeyboardUtils.hideKeyboard(getActivity(), getView());
                    }
                    return false;
                }
            });
            mode.setCustomView(customView);
            searchTermEditText.requestFocus();
            KeyboardUtils.showKeyboard(getActivity(), searchTermEditText);
            contactsListPresenter.setCurrentPage(1);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return  false;
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            contactsListPresenter.setCurrentPage(1);
            contactsListPresenter.setHasNextPage(true);
            contactsListPresenter.setInActionMode(false);
            contactsListPresenter.getContacts();
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
    public void showCleanButton() {
        cleanImageButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCleanButton() {
        cleanImageButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showListLoading(boolean loading) {
        contactsListAdapter.setLoading(loading);
    }

}
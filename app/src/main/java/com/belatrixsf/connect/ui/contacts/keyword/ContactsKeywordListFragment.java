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
package com.belatrixsf.connect.ui.contacts.keyword;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.adapters.ContactsKeywordListAdapter;
import com.belatrixsf.connect.entities.Employee;
import com.belatrixsf.connect.entities.Keyword;
import com.belatrixsf.connect.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.connect.ui.account.AccountActivity;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.ui.common.EndlessRecyclerOnScrollListener;
import com.belatrixsf.connect.ui.common.RecyclerOnItemClickListener;
import com.belatrixsf.connect.ui.common.views.DividerItemDecoration;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.di.modules.presenters.ContactsKeywordPresenterModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by PedroCarrillo on 5/12/16.
 */
public class ContactsKeywordListFragment extends BelatrixConnectFragment implements ContactsKeywordListView, RecyclerOnItemClickListener {

    private ContactsKeywordListPresenter contactsKeywordListPresenter;
    private ContactsKeywordListAdapter contactsKeywordListAdapter;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

    public static final String PAGINATED_RESPONSE_KEY = "_paginated_response_key";
    public static final String EMPLOYEE_LIST_KEY = "_employee_list_key";

    @Bind(R.id.employees) RecyclerView employeesRecyclerView;
    ImageView photoImageView;

    public static ContactsKeywordListFragment newInstance(Keyword keyword) {
        ContactsKeywordListFragment contactsKeywordListFragment = new ContactsKeywordListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ContactsKeywordListActivity.KEYWORD_KEY, keyword);
        contactsKeywordListFragment.setArguments(bundle);
        return contactsKeywordListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_keyword_list, container, false);
    }


    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        contactsKeywordListPresenter = belatrixConnectApplication.getApplicationComponent()
                .contactsKeywordListComponent(new ContactsKeywordPresenterModule(this))
                .contactsKeywordPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        boolean hasArguments = (getArguments() != null && getArguments().containsKey(ContactsKeywordListActivity.KEYWORD_KEY));
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        } else if (hasArguments) {
            Keyword keyword = getArguments().getParcelable(ContactsKeywordListActivity.KEYWORD_KEY);
            contactsKeywordListPresenter.init(keyword);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        saveState(outState);
        super.onSaveInstanceState(outState);
    }

    private void saveState(Bundle outState) {
        Keyword keyword = contactsKeywordListPresenter.getKeyword();
        PaginatedResponse paginatedResponse = contactsKeywordListPresenter.getStarPaginatedResponse();
        List<Employee> employeeList = contactsKeywordListPresenter.getEmployeeList();
        outState.putParcelable(ContactsKeywordListActivity.KEYWORD_KEY, keyword);
        outState.putParcelable(PAGINATED_RESPONSE_KEY, paginatedResponse);
        outState.putParcelableArrayList(EMPLOYEE_LIST_KEY, new ArrayList<>(employeeList));
    }

    private void restoreState(Bundle savedInstanceState) {
        Keyword keyword = savedInstanceState.getParcelable(ContactsKeywordListActivity.KEYWORD_KEY);
        PaginatedResponse paginatedResponse = savedInstanceState.getParcelable(PAGINATED_RESPONSE_KEY);
        List<Employee> employeeList = savedInstanceState.getParcelableArrayList(EMPLOYEE_LIST_KEY);
        contactsKeywordListPresenter.loadPresenterState(employeeList, paginatedResponse);
        contactsKeywordListPresenter.setKeyword(keyword);
    }

    private void initViews() {
        contactsKeywordListAdapter = new ContactsKeywordListAdapter(this);
        employeesRecyclerView.setAdapter(contactsKeywordListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        employeesRecyclerView.setLayoutManager(linearLayoutManager);
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                contactsKeywordListPresenter.callNextPage();
            }
        };
        employeesRecyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
        employeesRecyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));
    }

    @Override
    public void showEmployees(List<Employee> contacts) {
        contactsKeywordListAdapter.update(contacts);
    }


    @Override
    public void showProgressIndicator() {
        if (contactsKeywordListAdapter != null) {
            contactsKeywordListAdapter.setLoading(true);
        }
        if (endlessRecyclerOnScrollListener != null) {
            endlessRecyclerOnScrollListener.setLoading(true);
        }
    }

    @Override
    public void hideProgressIndicator() {
        if (contactsKeywordListAdapter != null) {
            contactsKeywordListAdapter.setLoading(false);
        }
        if (endlessRecyclerOnScrollListener != null) {
            endlessRecyclerOnScrollListener.setLoading(false);
        }
    }


    @Override
    public void showCurrentPage(int currentPage) {
        endlessRecyclerOnScrollListener.setCurrentPage(currentPage);
    }

    @Override
    public void goContactProfile(Integer id) {
        AccountActivity.startActivityAnimatingProfilePic(getActivity(), photoImageView, id);
    }

    @Override
    public void onClick(View v) {
        photoImageView = ButterKnife.findById(v, R.id.contact_photo);
        contactsKeywordListPresenter.onContactClicked(v.getTag());
    }

    @Override
    public void onDestroyView() {
        contactsKeywordListPresenter.cancelRequests();
        super.onDestroyView();
    }

}

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
package com.belatrixsf.connect.ui.account.badges;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.adapters.AccountBadgesAdapter;
import com.belatrixsf.connect.entities.Badge;
import com.belatrixsf.connect.entities.EmployeeBadge;
import com.belatrixsf.connect.ui.account.AccountActivity;
import com.belatrixsf.connect.ui.account.AccountFragmentListener;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.ui.common.RecyclerOnItemClickListener;
import com.belatrixsf.connect.ui.common.views.DividerItemDecoration;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.di.modules.presenters.AccountBadgesPresenterModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;

/**
 * Created by dvelasquez on 16/2/17.
 */
public class AccountBadgesFragment extends BelatrixConnectFragment implements AccountBadgesView, RecyclerOnItemClickListener {

    public static final String _EMPLOYEE_BADGE_LIST_KEY = "_employeeBadge_list_key";

    private AccountBadgesPresenter accountBadgesPresenter;
    private AccountBadgesAdapter accountBadgesAdapter;
    private AccountFragmentListener accountFragmentListener;

    @Bind(R.id.account_badges) RecyclerView mRecyclerView;
    @Bind(R.id.badges_progress_bar) ProgressBar categoriesProgressBar;
    @Bind(R.id.no_data_textview) TextView noDataTextView;
    @BindString(R.string.dialog_option_confirm) String stringDialogConfirm;

    public static AccountBadgesFragment newInstance(Integer userId) {
        Bundle bundle = new Bundle();
        if (userId != null) {
            bundle.putInt(AccountActivity.USER_ID_KEY, userId);
        }
        AccountBadgesFragment accountFragment = new AccountBadgesFragment();
        accountFragment.setArguments(bundle);
        return accountFragment;
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
            accountFragmentListener = (AccountFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement AccountFragmentListener");
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
        return inflater.inflate(R.layout.fragment_account_badges, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        }else if (getArguments() != null) {
            Integer userId = null;
            byte [] userImg = null;
            if (getArguments().containsKey(AccountActivity.USER_ID_KEY)) {
                userId = getArguments().getInt(AccountActivity.USER_ID_KEY);
            }

            accountBadgesPresenter.setUserInfo(userId);
        }
       accountBadgesPresenter.loadBadgesByEmployee(true);
    }


    private void setupViews() {
        accountBadgesAdapter = new AccountBadgesAdapter(this);
        mRecyclerView.setAdapter(accountBadgesAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        gridLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(gridLayoutManager);

    }

    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        accountBadgesPresenter = belatrixConnectApplication.getApplicationComponent().
                accountBadgesComponent(new AccountBadgesPresenterModule(this))
                .accountBadgesPresenter();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        saveState(outState);
        super.onSaveInstanceState(outState);
    }

    private void restoreState(Bundle savedInstanceState) {
        List<EmployeeBadge> list = savedInstanceState.getParcelableArrayList(_EMPLOYEE_BADGE_LIST_KEY);
        accountBadgesPresenter.loadPresenterState(list);
        accountBadgesPresenter.setUserInfo(savedInstanceState.getInt(AccountActivity.USER_ID_KEY));
    }

    private void saveState(Bundle outState) {
        outState.putParcelableArrayList(_EMPLOYEE_BADGE_LIST_KEY, (ArrayList<? extends Parcelable>) accountBadgesPresenter.getBadges());
        outState.putInt(AccountActivity.USER_ID_KEY, accountBadgesPresenter.getEmployeeId());
    }


    @Override
    public void showProgressIndicator() {
        mRecyclerView.setVisibility(View.GONE);
        setProgressViewVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressIndicator() {
        setProgressViewVisibility(View.GONE);
    }

    public void setProgressViewVisibility(int visibility) {
        if (categoriesProgressBar != null) {
            categoriesProgressBar.setVisibility(visibility);
        }
    }


    @Override
    public void showBadges(List<EmployeeBadge> list) {
        mRecyclerView.setVisibility(View.VISIBLE);
        accountBadgesAdapter.setData(list);
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
    public void onClick(View v) {
        Badge badge = (Badge) v.getTag();
        accountBadgesPresenter.onBadgeClicked(badge);
    }

    @Override
    public void goBadgeDetail(Badge badge) {
        int position = accountBadgesAdapter.getPositionByItemId(badge.getId());
        if (position >= 0){
            AccountBadgesAdapter.AccountBadgesViewHolder viewHolder = (AccountBadgesAdapter.AccountBadgesViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
            AccountBadgeDetailActivity.startActivityAnimatingPic(getActivity(),viewHolder.badgeImageView,badge);
        }
    }



    @Override
    public void onDestroyView() {
        accountBadgesPresenter.cancelRequests();
        super.onDestroyView();
    }
}
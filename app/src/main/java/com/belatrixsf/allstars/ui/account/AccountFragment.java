package com.belatrixsf.allstars.ui.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.adapters.AccountCategoriesAdapter;
import com.belatrixsf.allstars.entities.Category;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.ui.common.AllStarsFragment;
import com.belatrixsf.allstars.ui.common.RecyclerOnItemClickListener;
import com.belatrixsf.allstars.ui.common.views.DividerItemDecoration;
import com.belatrixsf.allstars.utils.AllStarsApplication;
import com.belatrixsf.allstars.utils.di.components.ApplicationComponent;
import com.belatrixsf.allstars.utils.di.components.DaggerAccountComponent;
import com.belatrixsf.allstars.utils.di.modules.presenters.AccountPresenterModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by pedrocarrillo on 4/9/16.
 */
public class AccountFragment extends AllStarsFragment implements AccountView, RecyclerOnItemClickListener{

    private AccountPresenter accountPresenter;

    @Bind(R.id.rv_account_recommendations) RecyclerView recommendationRecyclerView;

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initDependencies(AllStarsApplication allStarsApplication) {
        accountPresenter = DaggerAccountComponent.builder()
                .applicationComponent(allStarsApplication.getApplicationComponent())
                .accountPresenterModule(new AccountPresenterModule(this))
                .build()
                .accountPresenter();
        accountPresenter.onAccountCreated();
    }

    @Override
    public void goCategoryDetail(Category category) {

    }

    @Override
    public void loadEmployeeData(Employee employee) {
        setupCategories(employee);
    }

    private void setupCategories(Employee employee) {
        AccountCategoriesAdapter accountCategoriesAdapter = new AccountCategoriesAdapter(employee.getCategoryList(), this);
        recommendationRecyclerView.setAdapter(accountCategoriesAdapter);
        recommendationRecyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setAutoMeasureEnabled(true);
        recommendationRecyclerView.setNestedScrollingEnabled(false);
        recommendationRecyclerView.setLayoutManager(linearLayoutManager);
    }


}

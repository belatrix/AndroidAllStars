package com.belatrixsf.allstars.ui.account;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.adapters.AccountCategoriesAdapter;
import com.belatrixsf.allstars.entities.Category;
import com.belatrixsf.allstars.ui.common.AllStarsFragment;
import com.belatrixsf.allstars.ui.common.RecyclerOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pedrocarrillo on 4/9/16.
 */
public class AccountFragment extends AllStarsFragment implements RecyclerOnItemClickListener{

    private RecyclerView rvRecommendations;

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        rvRecommendations = (RecyclerView)rootView.findViewById(R.id.rv_account_recommendations);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("1", "leaders", 0));
        categoryList.add(new Category("1", "marketing", 0));
        categoryList.add(new Category("1", "coworkers", 0));
        categoryList.add(new Category("1", "etc", 0));
        categoryList.add(new Category("1", "ac", 0));
        categoryList.add(new Category("1", "test", 0));
        AccountCategoriesAdapter accountCategoriesAdapter = new AccountCategoriesAdapter(categoryList, this);
        rvRecommendations.setAdapter(accountCategoriesAdapter);
        rvRecommendations.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onClick(View v) {

    }
}

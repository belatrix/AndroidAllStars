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
package com.belatrixsf.allstars.ui.account;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.adapters.AccountCategoriesAdapter;
import com.belatrixsf.allstars.entities.Category;
import com.belatrixsf.allstars.ui.common.AllStarsFragment;
import com.belatrixsf.allstars.ui.common.RecyclerOnItemClickListener;
import com.belatrixsf.allstars.ui.common.views.CircleTransform;
import com.belatrixsf.allstars.ui.common.views.DividerItemDecoration;
import com.belatrixsf.allstars.utils.AllStarsApplication;
import com.belatrixsf.allstars.utils.di.components.DaggerAccountComponent;
import com.belatrixsf.allstars.utils.di.modules.presenters.AccountPresenterModule;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;

/**
 * Created by pedrocarrillo on 4/9/16.
 */
public class AccountFragment extends AllStarsFragment implements AccountView, RecyclerOnItemClickListener{

    private AccountPresenter accountPresenter;

    @Bind(R.id.rv_account_recommendations) RecyclerView recommendationRecyclerView;
    @Bind(R.id.skype_id) TextView skypeTextView;
    @Bind(R.id.level) TextView levelTextView;
    @Bind(R.id.score) TextView scoreTextView;
    @Bind(R.id.profile_name) TextView nameTextView;
    @Bind(R.id.profile_role) TextView roleTextView;
    @Bind(R.id.profile_picture) ImageView pictureImageView;

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
        accountPresenter.loadEmployeeAccount();
    }

    @Override
    public void goCategoryDetail(Category category) {

    }

    @Override
    public void showSkypeId(String skypeId) {
        skypeTextView.setText(String.valueOf(skypeId));
    }

    @Override
    public void showScore(String score) {
        scoreTextView.setText(String.valueOf(score));
    }

    @Override
    public void showCategories(List<Category> categories) {
        AccountCategoriesAdapter accountCategoriesAdapter = new AccountCategoriesAdapter(categories, this);
        recommendationRecyclerView.setAdapter(accountCategoriesAdapter);
        recommendationRecyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setAutoMeasureEnabled(true);
        recommendationRecyclerView.setNestedScrollingEnabled(false);
        recommendationRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void showLevel(String level) {
        levelTextView.setText(String.valueOf(level));
    }

    @Override
    public void showEmployeeName(String employeName) {
        nameTextView.setText(employeName);
    }

    @Override
    public void showRole(String role) {
        roleTextView.setText(role);
    }

    @Override
    public void showProfilePicture(final String profilePicture) {
        int size = getActivity().getResources().getDimensionPixelSize(R.dimen.dimen_10_8);
        Glide.with(getActivity()).load(profilePicture).override(size,size).centerCrop().transform(new CircleTransform(getActivity())).into(pictureImageView);
    }
}

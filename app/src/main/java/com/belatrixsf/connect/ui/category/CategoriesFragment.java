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
package com.belatrixsf.connect.ui.category;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.adapters.CategoriesAdapter;
import com.belatrixsf.connect.entities.Category;
import com.belatrixsf.connect.entities.SubCategory;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.ui.common.views.DividerItemDecoration;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.di.modules.presenters.CategoriesListModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by gyosida on 4/27/16.
 */
public class CategoriesFragment extends BelatrixConnectFragment implements CategoriesView, CategoriesAdapter.CategoriesListListener {

    private static final String CATEGORY_KEY = "_category_key";
    private static final String CATEGORIES_KEY = "_categories_key";

    private SubcategorySelectionListener subcategorySelectionListener;
    private CategoriesAdapter categoriesAdapter;

    @Inject CategoriesPresenter categoriesPresenter;

    @Bind(R.id.categories) RecyclerView categoriesRecyclerView;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    public static CategoriesFragment newInstance(Category category) {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
        args.putParcelable(CATEGORY_KEY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        castOrThrowException(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        castOrThrowException(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        }
        categoriesPresenter.init();
        categoriesPresenter.getCategories();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        saveState(outState);
        super.onSaveInstanceState(outState);
    }

    private void castOrThrowException(Context context) {
        try {
            subcategorySelectionListener = (SubcategorySelectionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity has to implement SubcategorySelectionListener");
        }
    }

    private void initViews() {
        categoriesAdapter = new CategoriesAdapter();
        categoriesAdapter.setCategoriesListListener(this);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoriesRecyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));
        categoriesRecyclerView.setAdapter(categoriesAdapter);
    }

    private Category getCategoryIfExists() {
        if (getArguments() != null) {
            return getArguments().getParcelable(CATEGORY_KEY);
        }
        return null;
    }

    private void restoreState(Bundle savedInstanceState) {
        List<Category> savedCategories = savedInstanceState.getParcelableArrayList(CATEGORIES_KEY);
        categoriesPresenter.loadPresenterState(savedCategories);
    }

    private void saveState(Bundle outState) {
        List<Category> forSavingCategories = categoriesPresenter.forSavingCategories();
        if (forSavingCategories != null && forSavingCategories instanceof ArrayList) {
            outState.putParcelableArrayList(CATEGORIES_KEY, (ArrayList<Category>) forSavingCategories);
        }
    }

    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        belatrixConnectApplication
                .getApplicationComponent()
                .categoriesListComponent(new CategoriesListModule(this))
                .inject(this);
    }

    @Override
    public void notifyAreSubcategories(boolean areSubcategories) {
        categoriesAdapter.setAreSubcategories(areSubcategories);
    }

    @Override
    public void notifySelection(Category subcategory) {
        subcategorySelectionListener.onSubcategorySelected(subcategory);
    }

    @Override
    public void showCategories(List<Category> categories) {
        categoriesAdapter.update(categories);
    }

    @Override
    public void onCategorySelected(int position) {
        categoriesPresenter.categorySelected(position);
    }

    @Override
    public void showSubcategories(Category category) {
        fragmentListener.replaceFragment(CategoriesFragment.newInstance(category), true);
    }

    public interface SubcategorySelectionListener {
        void onSubcategorySelected(Category subcategory);
    }

    @Override
    public void onDestroyView() {
        categoriesPresenter.cancelRequests();
        super.onDestroyView();
    }

}

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
package com.belatrixsf.allstars.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Category;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gyosida on 4/26/16.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {

    private List<Category> categories = new ArrayList<>();
    private CategoriesListListener categoriesListListener;
    private boolean areSubcategories = false;

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view, categoriesListListener);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.categoryName.setText(category.getName());
        holder.chevronRight.setVisibility(areSubcategories ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setAreSubcategories(boolean areSubcategories) {
        this.areSubcategories = areSubcategories;
    }

    public void setCategoriesListListener(CategoriesListListener categoriesListListener) {
        this.categoriesListListener = categoriesListListener;
    }

    public void update(List<Category> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
        notifyDataSetChanged();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {

        private CategoriesListListener categoriesListListener;

        @Bind(R.id.category_name) TextView categoryName;
        @Bind(R.id.chevron_right) ImageView chevronRight;

        CategoryViewHolder(View itemView, CategoriesListListener categoriesListListener) {
            super(itemView);
            this.categoriesListListener = categoriesListListener;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.category_holder)
        void categoryClicked() {
            if (categoriesListListener != null) {
                categoriesListListener.onCategorySelected(getLayoutPosition());
            }
        }
    }

    public interface CategoriesListListener {
        void onCategorySelected(int position);
    }
}

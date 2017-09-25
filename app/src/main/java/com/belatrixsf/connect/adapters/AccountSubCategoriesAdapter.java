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
package com.belatrixsf.connect.adapters;

import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.entities.SubCategory;
import com.belatrixsf.connect.ui.common.LoadMoreBaseAdapter;
import com.belatrixsf.connect.ui.common.RecyclerSharedClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.belatrixsf.connect.ui.common.BelatrixConnectActivity.supportSharedElements;

/**
 * Created by pedrocarrillo on 4/9/16.
 */
    public class AccountSubCategoriesAdapter extends LoadMoreBaseAdapter<SubCategory> {

    public static final int TYPE_SUB_CATEGORY = 1;

    private RecyclerSharedClickListener itemClickListener;

    public AccountSubCategoriesAdapter(RecyclerSharedClickListener itemClickListener) {
        this(new ArrayList<SubCategory>(), itemClickListener);
    }

    public AccountSubCategoriesAdapter(List<SubCategory> subCategories, RecyclerSharedClickListener itemClickListener) {
        this.data = subCategories;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_category_account_item, parent, false);
        return new AccountSubCategoriesViewHolder(view);
    }

    @Override
    public void onBindDataViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final AccountSubCategoriesViewHolder accountSubCategoriesViewHolder = (AccountSubCategoriesViewHolder) holder;
        final SubCategory subCategory = data.get(position);
        holder.itemView.setTag(subCategory);
        accountSubCategoriesViewHolder.titleTextView.setText(subCategory.getName());
        accountSubCategoriesViewHolder.valueTextView.setText(String.valueOf(subCategory.getNumStars()));
        if (supportSharedElements()) {
            ViewCompat.setTransitionName(accountSubCategoriesViewHolder.containter, subCategory.getName());
        }
        accountSubCategoriesViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClick(holder.itemView, accountSubCategoriesViewHolder.containter, subCategory.getName());
            }
        });
    }

    @Override
    public int getDataItemViewType(int position) {
        return TYPE_SUB_CATEGORY;
    }

    public static class AccountSubCategoriesViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.title) public TextView titleTextView;
        @Bind(R.id.value) public TextView valueTextView;
        @Bind(R.id.sub_category_container) public View containter;

        public AccountSubCategoriesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}

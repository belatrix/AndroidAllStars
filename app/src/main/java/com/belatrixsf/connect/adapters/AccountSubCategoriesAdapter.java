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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.entities.SubCategory;
import com.belatrixsf.connect.ui.common.LoadMoreBaseAdapter;
import com.belatrixsf.connect.ui.common.RecyclerOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by pedrocarrillo on 4/9/16.
 */
    public class AccountSubCategoriesAdapter extends LoadMoreBaseAdapter<SubCategory> {

    public static final int TYPE_SUB_CATEGORY = 1;

    private RecyclerOnItemClickListener recyclerOnItemClickListener;

    public AccountSubCategoriesAdapter(RecyclerOnItemClickListener recyclerOnItemClickListener) {
        this(new ArrayList<SubCategory>(), recyclerOnItemClickListener);
    }

    public AccountSubCategoriesAdapter(List<SubCategory> subCategories, RecyclerOnItemClickListener recyclerOnItemClickListener) {
        this.data = subCategories;
        this.recyclerOnItemClickListener = recyclerOnItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_category_account_item, parent, false);
        return new AccountSubCategoriesViewHolder(view, recyclerOnItemClickListener);
    }

    @Override
    public void onBindDataViewHolder(RecyclerView.ViewHolder holder, int position) {
        AccountSubCategoriesViewHolder accountSubCategoriesViewHolder = (AccountSubCategoriesViewHolder) holder;
        final SubCategory subCategory = data.get(position);
        holder.itemView.setTag(subCategory);
        accountSubCategoriesViewHolder.titleTextView.setText(subCategory.getName());
        accountSubCategoriesViewHolder.valueTextView.setText(String.valueOf(subCategory.getNumStars()));
    }

    @Override
    public int getDataItemViewType(int position) {
        return TYPE_SUB_CATEGORY;
    }

    public static class AccountSubCategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.title) public TextView titleTextView;
        @Bind(R.id.value) public TextView valueTextView;
        private RecyclerOnItemClickListener recyclerOnItemClickListener;

        public AccountSubCategoriesViewHolder(View itemView, RecyclerOnItemClickListener recyclerOnItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.titleTextView = (TextView) itemView.findViewById(R.id.title);
            this.valueTextView = (TextView) itemView.findViewById(R.id.value);
            this.recyclerOnItemClickListener = recyclerOnItemClickListener;
            this.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (recyclerOnItemClickListener != null){
                recyclerOnItemClickListener.onClick(this.itemView);
            }
        }

    }

}

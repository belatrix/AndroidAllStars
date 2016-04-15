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
import android.widget.TextView;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Category;
import com.belatrixsf.allstars.ui.common.RecyclerOnItemClickListener;

import java.util.List;

/**
 * Created by pedrocarrillo on 4/9/16.
 */
public class AccountCategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Category> categories;
    private RecyclerOnItemClickListener recyclerOnItemClickListener;

    public AccountCategoriesAdapter(List<Category> categories, RecyclerOnItemClickListener recyclerOnItemClickListener) {
        this.categories = categories;
        this.recyclerOnItemClickListener = recyclerOnItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_account_item, parent, false);
        return new AccountCategoriesViewHolder(view, recyclerOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AccountCategoriesViewHolder accountCategoriesViewHolder = (AccountCategoriesViewHolder) holder;
        final Category category = categories.get(position);
        accountCategoriesViewHolder.tvTitle.setText(category.getName());
        accountCategoriesViewHolder.tvValue.setText(String.valueOf(category.getValue()));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class AccountCategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTitle;
        private TextView tvValue;
        private RecyclerOnItemClickListener recyclerOnItemClickListener;

        public AccountCategoriesViewHolder(View itemView, RecyclerOnItemClickListener recyclerOnItemClickListener) {
            super(itemView);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            this.tvValue = (TextView) itemView.findViewById(R.id.tv_value);
            this.recyclerOnItemClickListener = recyclerOnItemClickListener;
        }

        @Override
        public void onClick(View v) {
            if (recyclerOnItemClickListener != null) recyclerOnItemClickListener.onClick(this.itemView);
        }

    }

}

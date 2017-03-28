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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.entities.Keyword;
import com.belatrixsf.connect.ui.common.LoadMoreBaseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gyosida on 5/10/16.
 */
public class KeywordsFilterListAdapter extends LoadMoreBaseAdapter<Keyword> implements Filterable{

    private static final int KEYWORD_TYPE = 1;

    private KeywordListener keywordListener;
    private List<Keyword> filterList;

    public KeywordsFilterListAdapter() {
        this.data = new ArrayList<>();
        this.filterList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_keyword, parent, false);
        return new KeywordHolder(view, keywordListener);
    }

    @Override
    public void onBindDataViewHolder(RecyclerView.ViewHolder holder, int position) {
        Keyword keyword = filterList.get(position);
        KeywordHolder keywordHolder = (KeywordHolder) holder;
        keywordHolder.keywordName.setText(keyword.getName());
        keywordHolder.itemView.setTag(keyword);
    }

    @Override
    public int getDataItemViewType(int position) {
        return KEYWORD_TYPE;
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public void update(List<Keyword> keywords) {
        this.data.clear();
        this.data.addAll(keywords);
        this.filterList = keywords;
        notifyDataSetChanged();
    }

    public void add(Keyword keyword) {
        this.filterList.add(keyword);
        notifyDataSetChanged();
    }

    public void reset() {
        data.clear();
        notifyDataSetChanged();
    }

    public void setKeywordListener(KeywordListener keywordListener) {
        this.keywordListener = keywordListener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Keyword> filteredResults;
                if (constraint.length() == 0) {
                    filteredResults = data;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }
                FilterResults results = new FilterResults();
                results.values = filteredResults;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterList = (List<Keyword>) results.values;
                KeywordsFilterListAdapter.this.notifyDataSetChanged();
            }
        };
    }

    protected List<Keyword> getFilteredResults(String constraint) {
        List<Keyword> results = new ArrayList<>();
        for (Keyword item : data) {
            if (item.getName().toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }
        return results;
    }

    static class KeywordHolder extends RecyclerView.ViewHolder {

        private KeywordListener keywordListener;
        @Bind(R.id.keyword_name) TextView keywordName;

        public KeywordHolder(View itemView, KeywordListener keywordListener) {
            super(itemView);
            this.keywordListener = keywordListener;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.keyword_holder)
        public void keywordSelected() {
            if (keywordListener != null) {
                keywordListener.onKeywordSelected((Keyword) itemView.getTag());
            }
        }
    }

    public interface KeywordListener {
        void onKeywordSelected(Keyword keyword);
    }

}

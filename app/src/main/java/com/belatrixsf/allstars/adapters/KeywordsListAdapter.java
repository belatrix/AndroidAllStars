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
import com.belatrixsf.allstars.entities.Keyword;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gyosida on 5/10/16.
 */
public class KeywordsListAdapter extends RecyclerView.Adapter<KeywordsListAdapter.KeywordHolder> {

    private List<Keyword> keywords;
    private KeywordListener keywordListener;

    public KeywordsListAdapter() {
        this.keywords = new ArrayList<>();
    }

    @Override
    public KeywordsListAdapter.KeywordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_keyword, parent, false);
        return new KeywordHolder(view, keywordListener);
    }

    @Override
    public void onBindViewHolder(KeywordHolder holder, int position) {
        Keyword keyword = keywords.get(position);
        holder.keywordName.setText(keyword.getName());
    }

    @Override
    public int getItemCount() {
        return keywords.size();
    }

    public void add(List<Keyword> keywords) {
        this.keywords.addAll(keywords);
        notifyDataSetChanged();
    }

    public void setKeywordListener(KeywordListener keywordListener) {
        this.keywordListener = keywordListener;
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
                keywordListener.onKeywordSelected(getLayoutPosition());
            }
        }
    }

    public interface KeywordListener {
        void onKeywordSelected(int position);
    }

}

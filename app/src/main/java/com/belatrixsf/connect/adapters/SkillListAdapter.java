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
import com.belatrixsf.connect.entities.Keyword;
import com.belatrixsf.connect.ui.common.LoadMoreBaseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnLongClick;

/**
 * Created by echuquilin on 5/08/16.
 */
public class SkillListAdapter extends LoadMoreBaseAdapter<Keyword> {

    private static final int KEYWORD_TYPE = 1;

    private KeywordListener skillListListener;

    public SkillListAdapter() {
        this.data = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_keyword, parent, false);
        return new KeywordHolder(view, skillListListener);
    }

    @Override
    public void onBindDataViewHolder(RecyclerView.ViewHolder holder, int position) {
        Keyword keyword = data.get(position);
        KeywordHolder keywordHolder = (KeywordHolder) holder;
        keywordHolder.keywordName.setText(keyword.getName());
    }

    @Override
    public int getDataItemViewType(int position) {
        return KEYWORD_TYPE;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void update(List<Keyword> keywords) {
        this.data.clear();
        this.data.addAll(keywords);
        notifyDataSetChanged();
    }

    public void add(List<Keyword> keywords) {
        this.data.addAll(keywords);
        notifyDataSetChanged();
    }

    public void reset() {
        data.clear();
        notifyDataSetChanged();
    }

    public void setSkillListListener(KeywordListener skillListListener) {
        this.skillListListener = skillListListener;
    }

    static class KeywordHolder extends RecyclerView.ViewHolder {

        private KeywordListener keywordListener;
        @Bind(R.id.keyword_name) TextView keywordName;

        public KeywordHolder(View itemView, KeywordListener keywordListener) {
            super(itemView);
            this.keywordListener = keywordListener;
            ButterKnife.bind(this, itemView);
        }

        @OnLongClick(R.id.keyword_holder)
        public boolean keywordSelected() {
            if (keywordListener != null) {
                keywordListener.onKeywordSelected(getLayoutPosition());
            }
            return true;
        }
    }

    public interface KeywordListener {
        void onKeywordSelected(int position);
    }

}

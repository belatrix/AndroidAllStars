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

    private KeywordListener keywordListener;

    public SkillListAdapter() {
        this.data = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_keyword, parent, false);
        return new KeywordHolder(view, keywordListener);
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

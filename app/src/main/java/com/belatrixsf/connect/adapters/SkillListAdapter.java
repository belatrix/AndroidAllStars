package com.belatrixsf.connect.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.belatrixsf.connect.entities.Keyword;
import com.belatrixsf.connect.ui.common.LoadMoreBaseAdapter;

/**
 * Created by echuquilin on 5/08/16.
 */
public class SkillListAdapter extends LoadMoreBaseAdapter<Keyword> {


    @Override
    public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindDataViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getDataItemViewType(int position) {
        return 0;
    }
}

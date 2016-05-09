package com.belatrixsf.allstars.ui.common;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.belatrixsf.allstars.R;

import java.util.List;

/**
 * Created by PedroCarrillo on 4/26/16.
 */
public abstract class LoadMoreBaseAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected static final int VIEW_TYPE_LOAD = 0;

    private boolean hasLoadingFooter = false;

    protected List<T> data;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOAD) {
            View progressView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_bar_item, parent, false);
            return new ProgressViewHolder(progressView);
        } else {
            return onCreateDataViewHolder(parent, viewType);
        }
    }

    public abstract RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_LOAD ) {
            onBindProgressView(holder, position);
        } else {
            onBindDataViewHolder(holder, position);
        }
    }

    protected void onBindProgressView(RecyclerView.ViewHolder holder, int position){
        ((ProgressViewHolder)holder).progressBar.setIndeterminate(true);
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position) != null ? getDataItemViewType(position) : VIEW_TYPE_LOAD;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public abstract void onBindDataViewHolder(RecyclerView.ViewHolder holder, int position);
    public abstract int getDataItemViewType(int position);

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        }
    }

    public T getItem(int index) {
        if (data != null && data.get(index) != null) {
            return data.get(index);
        } else {
            throw new IllegalArgumentException("Item with index " + index + " doesn't exist, dataSet is " + data);
        }
    }

    public boolean isLoading() {
        return hasLoadingFooter;
    }

    public void updateData(List<T> moreData) {
        setLoading(false);
        this.data.addAll(moreData);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
    }

    public void setLoading(boolean loading) {
        if (loading) {
            hasLoadingFooter = true;
            if (!data.contains(null)) {
                data.add(null);
                notifyItemInserted(data.size() - 1);
            }
        } else {
            hasLoadingFooter = false;
            data.remove(null);
            notifyItemRemoved(data.size() - 1);
        }
        notifyDataSetChanged();
    }

}

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
package com.belatrixsf.connect.ui.common;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.belatrixsf.connect.R;

import java.util.List;

/**
 * Created by PedroCarrillo on 4/26/16.
 */
public abstract class LoadMoreBaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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

    public void update(List<T> moreData) {
        this.data.addAll(moreData);
        notifyDataSetChanged();
    }

    public void setData(List<T> moreData) {
        this.data = moreData;
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
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
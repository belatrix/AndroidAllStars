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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Recommendation;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by icerrate on 25/04/2016.
 */
public class RecommendationListAdapter extends RecyclerView.Adapter<RecommendationListAdapter.RecommendationViewHolder> {

    private List<Recommendation> recommendationList;
    private WeakReference<Context> context;

    public RecommendationListAdapter(Context context) {
        this(context, new ArrayList<Recommendation>());
    }

    public RecommendationListAdapter(Context context, List<Recommendation> recommendationList) {
        this.recommendationList = recommendationList;
        this.context = new WeakReference<>(context);
    }

    @Override
    public RecommendationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommendation, parent, false);
        return new RecommendationViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(RecommendationViewHolder holder, int position) {
        Recommendation recommendation = recommendationList.get(position);

        holder.messageTextView.setText(recommendation.getMessage());
        holder.userIdTextView.setText(recommendation.getFromUser());
    }

    @Override
    public int getItemCount() {
        return this.recommendationList.size();
    }

    public void updateData(List<Recommendation> recommendations){
        recommendationList.clear();
        recommendationList.addAll(recommendations);
        notifyDataSetChanged();
    }

    static class RecommendationViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.message) public TextView messageTextView;
        @Bind(R.id.user) public TextView userIdTextView;

        public RecommendationViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

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
import com.belatrixsf.allstars.entities.Recommendation;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by icerrate on 25/04/2016.
 */
public class RecommendationListAdapter extends RecyclerView.Adapter<RecommendationListAdapter.RecommendationViewHolder> {

    private List<Recommendation> recommendationList;

    public RecommendationListAdapter() {
        this(new ArrayList<Recommendation>());
    }

    public RecommendationListAdapter(List<Recommendation> recommendationList) {
        this.recommendationList = recommendationList;
    }

    @Override
    public RecommendationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_folding_recommendation, parent, false);
        return new RecommendationViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(final RecommendationViewHolder holder, int position) {
        Recommendation recommendation = recommendationList.get(position);
        //Title
        holder.titleUserTextView.setText(recommendation.getFromUser().getFullName());
        holder.titleMessageTextView.setText(recommendation.getMessage());
        //Content
        holder.contentUserTextView.setText(recommendation.getFromUser().getFullName());
        holder.contentMessageTextView.setText(recommendation.getMessage());
        holder.contentCategoryTextView.setText(recommendation.getCategory().getName());
        holder.contentDateTextView.setText(recommendation.getDate());
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

    static class RecommendationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @Bind(R.id.folding_cell) public FoldingCell foldingCell;
        //Title
        @Bind(R.id.title_user) public TextView titleUserTextView;
        @Bind(R.id.title_message) public TextView titleMessageTextView;
        //Content
        @Bind(R.id.content_user) public TextView contentUserTextView;
        @Bind(R.id.content_message) public TextView contentMessageTextView;
        @Bind(R.id.content_category) public TextView contentCategoryTextView;
        @Bind(R.id.content_date) public TextView contentDateTextView;

        public RecommendationViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            foldingCell.toggle(false);
        }
    }
}

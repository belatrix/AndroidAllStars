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
import android.widget.ImageView;
import android.widget.TextView;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Recommendation;
import com.belatrixsf.allstars.utils.DateUtils;
import com.belatrixsf.allstars.utils.media.ImageFactory;
import com.belatrixsf.allstars.utils.media.loaders.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by icerrate on 25/04/2016.
 */
public class RecommendationListAdapter extends RecyclerView.Adapter<RecommendationListAdapter.RecommendationViewHolder> {

    private List<Recommendation> recommendationList;
    private String noMessagePlaceHolder;

    public RecommendationListAdapter(Context context) {
        this(context, new ArrayList<Recommendation>());
    }

    public RecommendationListAdapter(Context context, List<Recommendation> recommendationList) {
        this.recommendationList = recommendationList;
        this.noMessagePlaceHolder = context.getString(R.string.message_placeholder);
    }

    @Override
    public RecommendationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommendation, parent, false);
        return new RecommendationViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(final RecommendationViewHolder holder, int position) {
        Recommendation recommendation = recommendationList.get(position);
        String formattedDate = DateUtils.formatDate(recommendation.getDate(), DateUtils.DATE_FORMAT_1, DateUtils.DATE_FORMAT_2);
        String message = recommendation.getMessage() != null && !recommendation.getMessage().isEmpty() ? recommendation.getMessage() : noMessagePlaceHolder;
        holder.contentUserTextView.setText(recommendation.getFromUser().getFullName());
        holder.contentMessageTextView.setText(message);
        holder.contentCategoryTextView.setText(recommendation.getCategory().getName());
        holder.contentDateTextView.setText(formattedDate);
        if (recommendation.getFromUser().getAvatar() != null) {
            new ImageFactory().getLoader().loadFromUrl(recommendation.getFromUser().getAvatar(), holder.contentPhotoImageView, ImageLoader.ImageTransformation.CIRCLE);
        }
    }

    @Override
    public int getItemCount() {
        return this.recommendationList.size();
    }

    public void updateData(List<Recommendation> recommendations) {
        recommendationList.clear();
        recommendationList.addAll(recommendations);
        notifyDataSetChanged();
    }

    static class RecommendationViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.content_photo)
        public ImageView contentPhotoImageView;
        @Bind(R.id.content_user)
        public TextView contentUserTextView;
        @Bind(R.id.content_message)
        public TextView contentMessageTextView;
        @Bind(R.id.content_category)
        public TextView contentCategoryTextView;
        @Bind(R.id.content_date)
        public TextView contentDateTextView;

        public RecommendationViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
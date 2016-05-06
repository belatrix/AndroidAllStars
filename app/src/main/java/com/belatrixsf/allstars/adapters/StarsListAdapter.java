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
import com.belatrixsf.allstars.entities.Star;
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
public class StarsListAdapter extends RecyclerView.Adapter<StarsListAdapter.StarViewHolder> {

    private List<Star> starList;
    private String noMessagePlaceHolder;

    public StarsListAdapter(Context context) {
        this(context, new ArrayList<Star>());
    }

    public StarsListAdapter(Context context, List<Star> starList) {
        this.starList = starList;
        this.noMessagePlaceHolder = context.getString(R.string.message_placeholder);
    }

    @Override
    public StarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_star, parent, false);
        return new StarViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(final StarViewHolder holder, int position) {
        Star star = starList.get(position);
        String formattedDate = DateUtils.formatDate(star.getDate(), DateUtils.DATE_FORMAT_1, DateUtils.DATE_FORMAT_2);
        String message = star.getMessage() != null && !star.getMessage().isEmpty() ? star.getMessage() : noMessagePlaceHolder;
        holder.employeeFullNameTextView.setText(star.getFromUser().getFullName());
        holder.starMessageTextView.setText(message);
        holder.starCategoryTextView.setText(star.getCategory().getName());
        holder.starDateTextView.setText(formattedDate);
        if (star.getFromUser().getAvatar() != null) {
            ImageFactory.getLoader().loadFromUrl(star.getFromUser().getAvatar(), holder.photoImageView, ImageLoader.ImageTransformation.CIRCLE);
        }
    }

    @Override
    public int getItemCount() {
        return this.starList.size();
    }

    public void updateData(List<Star> stars) {
        starList.clear();
        starList.addAll(stars);
        notifyDataSetChanged();
    }

    static class StarViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.employee_photo)
        public ImageView photoImageView;
        @Bind(R.id.employee_full_name)
        public TextView employeeFullNameTextView;
        @Bind(R.id.star_message)
        public TextView starMessageTextView;
        @Bind(R.id.star_category)
        public TextView starCategoryTextView;
        @Bind(R.id.star_date)
        public TextView starDateTextView;

        public StarViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
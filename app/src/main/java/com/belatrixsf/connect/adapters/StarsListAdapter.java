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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.entities.Star;
import com.belatrixsf.connect.ui.common.LoadMoreBaseAdapter;
import com.belatrixsf.connect.ui.common.views.KeywordView;
import com.belatrixsf.connect.utils.DateUtils;
import com.belatrixsf.connect.utils.media.ImageFactory;
import com.belatrixsf.connect.utils.media.loaders.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by icerrate on 25/04/2016.
 */
public class StarsListAdapter extends LoadMoreBaseAdapter<Star> {

    public static final int VIEW_TYPE_STAR_ITEM = 1;

    private String noMessagePlaceHolder;
    private KeywordClickListener keywordClickListener;

    public StarsListAdapter(Context context, KeywordClickListener keywordClickListener) {
        this(context, new ArrayList<Star>(), keywordClickListener);
    }

    public StarsListAdapter(Context context, List<Star> starList, KeywordClickListener keywordClickListener) {
        this.data = starList;
        this.keywordClickListener = keywordClickListener;
        this.noMessagePlaceHolder = context.getString(R.string.message_placeholder);
    }

    @Override
    public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_star, parent, false);
        return new StarViewHolder(layoutView, keywordClickListener);
    }

    @Override
    public void onBindDataViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StarViewHolder) {
            StarViewHolder starViewHolder = (StarViewHolder) holder;
            Star star = data.get(position);
            String formattedDate = DateUtils.formatDate(star.getDate(), DateUtils.DATE_FORMAT_1, DateUtils.DATE_FORMAT_2);
            String message = star.getMessage() != null && !star.getMessage().isEmpty() ? star.getMessage() : noMessagePlaceHolder;
            String keyword = star.getKeyword().getName();
            starViewHolder.employeeFullNameTextView.setText(star.getFromUser().getFullName());
            starViewHolder.starDateTextView.setText(formattedDate);
            if (message != null && !message.isEmpty()) {
                starViewHolder.starMessageTextView.setText(message);
                starViewHolder.starMessageTextView.setVisibility(View.VISIBLE);
            } else {
                starViewHolder.starMessageTextView.setVisibility(View.GONE);
            }
            starViewHolder.starCategoryTextView.setText(star.getCategory().getName());
            starViewHolder.starKeywordView.setKeyword(keyword);
            ImageFactory.getLoader().loadFromUrl(star.getFromUser().getAvatar(),
                    starViewHolder.photoImageView,
                    ImageLoader.ImageTransformation.CIRCLE,
                    starViewHolder.photoImageView.getResources().getDrawable(R.drawable.contact_placeholder)
            );
        }
    }

    @Override
    public int getDataItemViewType(int position) {
        return VIEW_TYPE_STAR_ITEM;
    }

    public void update(List<Star> stars) {
        data.clear();
        data.addAll(stars);
        notifyDataSetChanged();
    }

    static class StarViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.employee_photo)
        public ImageView photoImageView;
        @Bind(R.id.employee_full_name)
        public TextView employeeFullNameTextView;
        @Bind(R.id.star_date)
        public TextView starDateTextView;
        @Bind(R.id.star_message)
        public TextView starMessageTextView;
        @Bind(R.id.star_category)
        public TextView starCategoryTextView;
        @Bind(R.id.star_keyword)
        public KeywordView starKeywordView;

        KeywordClickListener keywordClickListener;

        public StarViewHolder(View view, KeywordClickListener keywordClickListener) {
            super(view);
            ButterKnife.bind(this, view);
            this.keywordClickListener = keywordClickListener;
        }

        @OnClick(R.id.star_keyword)
        public void onKeywordClick(View v) {
            if (keywordClickListener != null) {
                keywordClickListener.onKeywordSelected(getAdapterPosition());
            }
        }
    }

    public interface KeywordClickListener {

        void onKeywordSelected(int position);

    }

}
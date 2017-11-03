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

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.entities.Badge;
import com.belatrixsf.connect.entities.EmployeeBadge;
import com.belatrixsf.connect.ui.common.LoadMoreBaseAdapter;
import com.belatrixsf.connect.ui.common.RecyclerOnItemClickListener;
import com.belatrixsf.connect.utils.media.ImageFactory;
import com.belatrixsf.connect.utils.media.loaders.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.belatrixsf.connect.ui.common.BelatrixConnectActivity.supportSharedElements;

/**
 * Created by dvelasquez on 17/02/17.
 */
    public class AccountBadgesAdapter extends LoadMoreBaseAdapter<EmployeeBadge> {

    public static final int TYPE_SUB_CATEGORY = 1;

    private RecyclerOnItemClickListener recyclerOnItemClickListener;

    public AccountBadgesAdapter(RecyclerOnItemClickListener recyclerOnItemClickListener) {
        this(new ArrayList<EmployeeBadge>(), recyclerOnItemClickListener);
    }

    public AccountBadgesAdapter(List<EmployeeBadge> employeBadges, RecyclerOnItemClickListener recyclerOnItemClickListener) {
        this.data = employeBadges;
        this.recyclerOnItemClickListener = recyclerOnItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_badge, parent, false);
        return new AccountBadgesViewHolder(view, recyclerOnItemClickListener);
    }

    @Override
    public void onBindDataViewHolder(RecyclerView.ViewHolder holder, int position) {
        AccountBadgesViewHolder accountBadgesViewHolder = (AccountBadgesViewHolder) holder;
        final EmployeeBadge object = data.get(position);
        final Badge badge = object.getBadge();
        holder.itemView.setTag(object);
        accountBadgesViewHolder.titleTextView.setText(badge.getName());
        accountBadgesViewHolder.itemView.setTag(badge);
        ImageFactory.getLoader().loadFromUrl(badge.getIcon(),
                accountBadgesViewHolder.badgeImageView,
                ImageLoader.ImageTransformation.BORDERED_CIRCLE,
                accountBadgesViewHolder.badgeImageView.getResources().getDrawable(R.drawable.contact_placeholder),
                ImageLoader.ScaleType.CENTER_CROP
        );
    }

    @Override
    public int getDataItemViewType(int position) {
        return TYPE_SUB_CATEGORY;
    }

    public int getPositionByItemId(int id) {
        if (this.data !=  null ){
            for(int i=0;i<this.data.size();i++){
                if (this.data.get(i).getBadge().getId().intValue() == id){
                    return i;
                }
            }
        }
        return -1;
    }

    public static class AccountBadgesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.badge_title) public TextView titleTextView;
        @Bind(R.id.badge_icon) public ImageView badgeImageView;
        private RecyclerOnItemClickListener recyclerOnItemClickListener;

        public AccountBadgesViewHolder(View itemView, RecyclerOnItemClickListener recyclerOnItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.titleTextView = (TextView) itemView.findViewById(R.id.badge_title);
            this.badgeImageView = (ImageView) itemView.findViewById(R.id.badge_icon);
            this.recyclerOnItemClickListener = recyclerOnItemClickListener;
            this.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (recyclerOnItemClickListener != null){
                recyclerOnItemClickListener.onClick(this.itemView);
            }
        }

    }

}

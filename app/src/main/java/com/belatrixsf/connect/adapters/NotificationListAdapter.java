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
import com.belatrixsf.connect.entities.Notification;
import com.belatrixsf.connect.ui.common.LoadMoreBaseAdapter;
import com.belatrixsf.connect.utils.media.ImageFactory;
import com.belatrixsf.connect.utils.media.loaders.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by icerrate on 20/06/2016.
 */
public class NotificationListAdapter extends LoadMoreBaseAdapter<Notification> {

    public static final int TYPE_NOTIFICATION = 1;

    private NotificationListener notificationListener;

    public NotificationListAdapter(NotificationListener notificationListener) {
        this(notificationListener, new ArrayList<Notification>());
    }

    public NotificationListAdapter(NotificationListener notificationListener, List<Notification> notificationList) {
        this.data = notificationList;
        this.notificationListener = notificationListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(layoutView, notificationListener);
    }

    @Override
    public void onBindDataViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NotificationViewHolder) {
            NotificationViewHolder notificationViewHolder = (NotificationViewHolder) holder;
            Notification notification = data.get(position);
            Context context = notificationViewHolder.notificationImageView.getContext();
            String fromUserFullName = notification.getFromUser() != null ? notification.getFromUser().getFullName() : context.getResources().getString(R.string.notification_user_fullname_placeholder);
            String detail = notification.getDetail() != null && !notification.getDetail().isEmpty() ? notification.getDetail() : context.getString(R.string.notification_detail_placeholder);
            String dateHour = notification.getDate() != null && !notification.getDate().isEmpty() ? notification.getDate() : context.getString(R.string.notification_date_placeholder);
            notificationViewHolder.notificationDetail.setText(String.format(context.getString(R.string.notification_user_and_detail), fromUserFullName, detail));
            notificationViewHolder.notificationDateHour.setText(dateHour);
            notificationViewHolder.itemView.setTag(notification);
            ImageFactory.getLoader().loadFromUrl(notification.getFromUser().getAvatar(),
                    notificationViewHolder.notificationImageView,
                    ImageLoader.ImageTransformation.BORDERED_CIRCLE,
                    context.getResources().getDrawable(R.drawable.contact_placeholder)
            );
        }
    }
    @Override
    public int getDataItemViewType(int position) {
        return TYPE_NOTIFICATION;
    }

    public void update(List<Notification> notifications) {
        data.clear();
        data.addAll(notifications);
        notifyDataSetChanged();
    }

    public void add(List<Notification> notifications) {
        this.data.addAll(notifications);
        notifyDataSetChanged();
    }

    public void reset() {
        data.clear();
        notifyDataSetChanged();
    }

    public void setNotificationListener(NotificationListener notificationListener) {
        this.notificationListener = notificationListener;
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private NotificationListener notificationListener;
        @Bind(R.id.notification_photo) public ImageView notificationImageView;
        @Bind(R.id.notification_detail) public TextView notificationDetail;
        @Bind(R.id.notification_date_hour) public TextView notificationDateHour;

        public NotificationViewHolder(View view, NotificationListener notificationListener) {
            super(view);
            ButterKnife.bind(this, view);
            this.notificationListener = notificationListener;
            this.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (notificationListener != null) {
                notificationListener.onNotificationSelected(getLayoutPosition());
            }
        }
    }

    public interface NotificationListener {
        void onNotificationSelected(int position);
    }
}

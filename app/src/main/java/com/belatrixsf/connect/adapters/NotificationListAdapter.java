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
import com.belatrixsf.connect.utils.DateUtils;
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

    public NotificationListAdapter() {
        this.data = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(layoutView);
    }

    @Override
    public void onBindDataViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NotificationViewHolder) {
            NotificationViewHolder notificationViewHolder = (NotificationViewHolder) holder;
            Notification notification = data.get(position);
            Context context = notificationViewHolder.dateTimeTextView.getContext();
            ImageFactory.getLoader().loadFromUrl(notification.getAvatar(),
                    notificationViewHolder.photoImageView,
                    ImageLoader.ImageTransformation.BORDERED_CIRCLE,
                    notificationViewHolder.photoImageView.getResources().getDrawable(R.drawable.ic_connect)
            );
            String dateTime = notification.getDateTime() != null && !notification.getDateTime().isEmpty() ? DateUtils.formatDate(notification.getDateTime(), DateUtils.DATE_FORMAT_1, DateUtils.DATE_FORMAT_4) : context.getString(R.string.notification_datetime_placeholder);
            String message = notification.getText() != null && !notification.getText().isEmpty() ? notification.getText() : context.getString(R.string.notification_message_placeholder);
            notificationViewHolder.dateTimeTextView.setText(dateTime);
            notificationViewHolder.messageTextView.setText(message);
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

    static class NotificationViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.photo) public ImageView photoImageView;
        @Bind(R.id.datetime) public TextView dateTimeTextView;
        @Bind(R.id.message) public TextView messageTextView;

        public NotificationViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}

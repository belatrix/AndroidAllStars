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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.entities.Event;
import com.belatrixsf.connect.ui.common.LoadMoreBaseAdapter;
import com.belatrixsf.connect.ui.common.RecyclerOnItemClickListener;
import com.belatrixsf.connect.utils.DateUtils;
import com.belatrixsf.connect.utils.media.ImageFactory;
import com.belatrixsf.connect.utils.media.loaders.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by icerrate on 13/06/2016.
 */
public class EventListAdapter extends LoadMoreBaseAdapter<Event> {

    public static final int TYPE_EVENT = 1;

    private RecyclerOnItemClickListener recyclerOnItemClickListener;

    public EventListAdapter(RecyclerOnItemClickListener recyclerOnItemClickListener) {
        this(recyclerOnItemClickListener, new ArrayList<Event>());
    }

    public EventListAdapter(RecyclerOnItemClickListener recyclerOnItemClickListener, List<Event> eventList) {
        this.data = eventList;
        this.recyclerOnItemClickListener = recyclerOnItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(layoutView, recyclerOnItemClickListener);
    }

    @Override
    public void onBindDataViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EventViewHolder) {
            EventViewHolder eventViewHolder = (EventViewHolder) holder;
            Event event = data.get(position);
            String title = event.getTitle() != null && !event.getTitle().isEmpty() ? event.getTitle() : eventViewHolder.eventTitleTextView.getContext().getString(R.string.title_title_placeholder);
            String date = event.getDatetime();
            String formattedDate = date != null && !date.isEmpty() ? DateUtils.formatDate(date, DateUtils.DATE_FORMAT_3, DateUtils.DATE_FORMAT_2) : eventViewHolder.eventDateTextView.getContext().getString(R.string.title_date_placeholder);
            eventViewHolder.eventTitleTextView.setText(title);
            eventViewHolder.eventDateTextView.setText(formattedDate);
            eventViewHolder.itemView.setTag(event);
            ImageFactory.getLoader().loadFromUrl(event.getPicture(),
                    eventViewHolder.pictureImageView,
                    ImageLoader.ImageTransformation.BORDERED_CIRCLE,
                    eventViewHolder.pictureImageView.getResources().getDrawable(R.drawable.event_placeholder)
            );
        }
    }
    @Override
    public int getDataItemViewType(int position) {
        return TYPE_EVENT;
    }

    public void update(List<Event> events) {
        data.clear();
        data.addAll(events);
        notifyDataSetChanged();
    }

    public void add(List<Event> events) {
        this.data.addAll(events);
        notifyDataSetChanged();
    }

    public void reset() {
        data.clear();
        notifyDataSetChanged();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.event_picture) public ImageView pictureImageView;
        @Bind(R.id.event_title) public TextView eventTitleTextView;
        @Bind(R.id.event_date) public TextView eventDateTextView;
        protected RecyclerOnItemClickListener recyclerOnItemClickListener;

        public EventViewHolder(View view, RecyclerOnItemClickListener recyclerOnItemClickListener) {
            super(view);
            ButterKnife.bind(this, view);
            this.recyclerOnItemClickListener = recyclerOnItemClickListener;
            this.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (recyclerOnItemClickListener != null) {
                recyclerOnItemClickListener.onClick(this.itemView);
            }
        }
    }
}

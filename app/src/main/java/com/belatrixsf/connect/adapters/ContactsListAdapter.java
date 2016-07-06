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
import com.belatrixsf.connect.entities.Employee;
import com.belatrixsf.connect.ui.common.LoadMoreBaseAdapter;
import com.belatrixsf.connect.ui.common.RecyclerOnItemClickListener;
import com.belatrixsf.connect.utils.media.ImageFactory;
import com.belatrixsf.connect.utils.media.loaders.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by icerrate on 15/04/2016.
 */
public class ContactsListAdapter extends LoadMoreBaseAdapter<Employee> {

    public static final int TYPE_EMPLOYEE = 1;

    private RecyclerOnItemClickListener recyclerOnItemClickListener;

    public ContactsListAdapter(RecyclerOnItemClickListener recyclerOnItemClickListener) {
        this(recyclerOnItemClickListener, new ArrayList<Employee>());
    }

    public ContactsListAdapter(RecyclerOnItemClickListener recyclerOnItemClickListener, List<Employee> contactsList) {
        this.data = contactsList;
        this.recyclerOnItemClickListener = recyclerOnItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(layoutView, recyclerOnItemClickListener);
    }

    @Override
    public void onBindDataViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContactViewHolder) {
            ContactViewHolder contactViewHolder = (ContactViewHolder) holder;
            Employee contact = data.get(position);
            String fullName = contact.getFullName() != null && !contact.getFullName().isEmpty() ? contact.getFullName() : contactViewHolder.contactFullNameTextView.getContext().getString(R.string.fullname_placeholder);
            String level = contact.getLevel() != null ? String.format(contactViewHolder.contactLevelTextView.getContext().getString(R.string.contact_list_level), String.valueOf(contact.getLevel())) : contactViewHolder.contactLevelTextView.getContext().getString(R.string.level_placeholder);
            contactViewHolder.contactFullNameTextView.setText(fullName);
            contactViewHolder.contactLevelTextView.setText(level);
            contactViewHolder.itemView.setTag(contact);
            ImageFactory.getLoader().loadFromUrl(contact.getAvatar(),
                    contactViewHolder.photoImageView,
                    ImageLoader.ImageTransformation.BORDERED_CIRCLE,
                    contactViewHolder.photoImageView.getResources().getDrawable(R.drawable.contact_placeholder)
            );
        }
    }
    @Override
    public int getDataItemViewType(int position) {
        return TYPE_EMPLOYEE;
    }

    public void update(List<Employee> contacts) {
        data.clear();
        data.addAll(contacts);
        notifyDataSetChanged();
    }

    public void add(List<Employee> contacts) {
        this.data.addAll(contacts);
        notifyDataSetChanged();
    }

    public void reset() {
        data.clear();
        notifyDataSetChanged();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.contact_photo) public ImageView photoImageView;
        @Bind(R.id.contact_full_name) public TextView contactFullNameTextView;
        @Bind(R.id.contact_level) public TextView contactLevelTextView;
        protected RecyclerOnItemClickListener recyclerOnItemClickListener;

        public ContactViewHolder(View view, RecyclerOnItemClickListener recyclerOnItemClickListener) {
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

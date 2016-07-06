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
 * Created by PedroCarrillo on 5/12/16.
 */
public class ContactsKeywordListAdapter extends LoadMoreBaseAdapter<Employee> {

    public static final int VIEW_TYPE_CONTACT_ITEM = 1;

    private String noMessagePlaceHolder;
    private RecyclerOnItemClickListener recyclerOnItemClickListener;

    public ContactsKeywordListAdapter(RecyclerOnItemClickListener recyclerOnItemClickListener) {
        this(recyclerOnItemClickListener, new ArrayList<Employee>());
    }

    public ContactsKeywordListAdapter(RecyclerOnItemClickListener recyclerOnItemClickListener, List<Employee> contactList) {
        this.data = contactList;
        this.recyclerOnItemClickListener = recyclerOnItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_keyword, parent, false);
        return new ContactKeywordViewHolder(layoutView, recyclerOnItemClickListener);
    }

    @Override
    public void onBindDataViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContactKeywordViewHolder) {
            ContactKeywordViewHolder contactKeywordViewHolder = (ContactKeywordViewHolder) holder;
            Employee employee = data.get(position);
            contactKeywordViewHolder.contactHolder.setBackground(null);
            contactKeywordViewHolder.itemView.setTag(employee);
            contactKeywordViewHolder.contactFullNameTextView.setText(employee.getFullName());
            String levelLabel = String.format(contactKeywordViewHolder.contactLevelTextView.getContext().getString(R.string.contact_list_level), String.valueOf(employee.getLevel()));
            contactKeywordViewHolder.contactLevelTextView.setText(levelLabel);
            contactKeywordViewHolder.numberStarsTextView.setText(String.valueOf(employee.getNumStars()));
            ImageFactory.getLoader().loadFromUrl(employee.getAvatar(),
                    contactKeywordViewHolder.photoImageView,
                    ImageLoader.ImageTransformation.BORDERED_CIRCLE,
                    contactKeywordViewHolder.photoImageView.getResources().getDrawable(R.drawable.contact_placeholder)
            );
        }
    }

    @Override
    public int getDataItemViewType(int position) {
        return VIEW_TYPE_CONTACT_ITEM;
    }

    public void update(List<Employee> employeeList) {
        data.clear();
        data.addAll(employeeList);
        notifyDataSetChanged();
    }

    static class ContactKeywordViewHolder extends ContactsListAdapter.ContactViewHolder {

        @Bind(R.id.number_stars_textview)
        TextView numberStarsTextView;
        @Bind(R.id.contact_holder)
        View contactHolder;

        public ContactKeywordViewHolder(View view, RecyclerOnItemClickListener recyclerOnItemClickListener) {
            super(view, null);
            ButterKnife.bind(this, view);
            this.recyclerOnItemClickListener = recyclerOnItemClickListener;
            this.itemView.setOnClickListener(this);
        }

    }

}
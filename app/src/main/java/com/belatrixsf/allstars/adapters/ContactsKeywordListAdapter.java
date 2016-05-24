package com.belatrixsf.allstars.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.ui.common.LoadMoreBaseAdapter;
import com.belatrixsf.allstars.ui.common.RecyclerOnItemClickListener;
import com.belatrixsf.allstars.utils.media.ImageFactory;
import com.belatrixsf.allstars.utils.media.loaders.ImageLoader;

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
            if (employee.getAvatar() != null) {
                ImageFactory.getLoader().loadFromUrl(employee.getAvatar(), contactKeywordViewHolder.photoImageView, ImageLoader.ImageTransformation.BORDERED_CIRCLE);
            }
            contactKeywordViewHolder.numberStarsTextView.setText(String.valueOf(employee.getNumStars()));
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
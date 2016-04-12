package com.belatrixsf.allstars.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Category;
import com.belatrixsf.allstars.ui.common.RecyclerOnItemClickListener;

import java.util.List;

/**
 * Created by pedrocarrillo on 4/9/16.
 */
public class AccountCategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_ITEM = 0;
    public static final int VIEW_TYPE_HEADER = 1;

    private List<Category> categories;
    private RecyclerOnItemClickListener recyclerOnItemClickListener;

    public AccountCategoriesAdapter(List<Category> categories, RecyclerOnItemClickListener recyclerOnItemClickListener) {
        this.categories = categories;
        this.recyclerOnItemClickListener = recyclerOnItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
//        if (position == 0) {
//            return VIEW_TYPE_HEADER;
//        } else {
            return VIEW_TYPE_ITEM;
//        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_header_item2, parent, false);
            return new CategoriesHeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_account_item2, parent, false);
            return new AccountCategoriesViewHolder(view, recyclerOnItemClickListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == VIEW_TYPE_ITEM) {
            AccountCategoriesViewHolder accountCategoriesViewHolder = (AccountCategoriesViewHolder) holder;
            final Category category = categories.get(position);
            accountCategoriesViewHolder.tvTitle.setText(category.getName());
            accountCategoriesViewHolder.tvValue.setText(String.valueOf(category.getValue()));
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class AccountCategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTitle;
        private TextView tvValue;
        private RecyclerOnItemClickListener recyclerOnItemClickListener;

        public AccountCategoriesViewHolder(View itemView, RecyclerOnItemClickListener recyclerOnItemClickListener) {
            super(itemView);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            this.tvValue = (TextView) itemView.findViewById(R.id.tv_value);
            this.recyclerOnItemClickListener = recyclerOnItemClickListener;
        }

        @Override
        public void onClick(View v) {
            if (recyclerOnItemClickListener != null) recyclerOnItemClickListener.onClick(this.itemView);
        }

    }

    public class CategoriesHeaderViewHolder extends RecyclerView.ViewHolder {

        public CategoriesHeaderViewHolder(View view) {
            super(view);
        }

    }

}

package com.belatrixsf.allstars.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.ui.common.RecyclerOnItemClickListener;
import com.belatrixsf.allstars.utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by icerrate on 15/04/2016.
 */
public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.EmployeeViewHolder> {

    private List<Employee> employeeList;
    private RecyclerOnItemClickListener recyclerOnItemClickListener;

    public EmployeeListAdapter(RecyclerOnItemClickListener recyclerOnItemClickListener) {
        this(recyclerOnItemClickListener, new ArrayList<Employee>());
    }

    public EmployeeListAdapter(RecyclerOnItemClickListener recyclerOnItemClickListener, List<Employee> employeeList) {
        this.employeeList = employeeList;
        this.recyclerOnItemClickListener = recyclerOnItemClickListener;
    }

    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new EmployeeViewHolder(layoutView, recyclerOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {
        final Employee employee = employeeList.get(position);
        holder.itemView.setTag(employee);
        String fullName = employee.getFullName();
        String currentFirstLetter = (fullName != null && !fullName.isEmpty()) ? String.valueOf(fullName.charAt(Constants.CAP_POSITION)).toUpperCase() : Constants.NUMERIC_SYMBOL;



//        holder.firstLetter.setText(showLetter);
        holder.fullName.setText(employee.getFullName());
        holder.level.setText(String.valueOf(employee.getLevel()));
    }

    @Override
    public int getItemCount() {
        return this.employeeList.size();
    }

    public void updateData(List<Employee> employees){
        employeeList.clear();
        employeeList.addAll(employees);
        notifyDataSetChanged();
    }

    static class EmployeeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.first_letter) public TextView firstLetter;
        @Bind(R.id.photo) public ImageView photo;
        @Bind(R.id.full_name) public TextView fullName;
        @Bind(R.id.level) public TextView level;
        private RecyclerOnItemClickListener recyclerOnItemClickListener;

        public EmployeeViewHolder(View view, RecyclerOnItemClickListener recyclerOnItemClickListener) {
            super(view);
            ButterKnife.bind(this, view);
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

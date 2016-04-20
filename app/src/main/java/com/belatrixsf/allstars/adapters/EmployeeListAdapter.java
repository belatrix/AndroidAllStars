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

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by icerrate on 15/04/2016.
 */
public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.EmployeeViewHolder> {

    private List<Employee> employeeList;
    private Context context;

    public EmployeeListAdapter(Context context, List<Employee> employeeList) {
        this.employeeList = employeeList;
        this.context = context;
    }

    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        EmployeeViewHolder rcv = new EmployeeViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {
        Employee emp = employeeList.get(position);

        String letter = "Z";
        if (null != emp.getFullName() && !"".equals(emp.getFullName())){
            letter = emp.getFullName().charAt(0)+"";
        }

        holder.firstLetter.setText(letter);

        //holder.photo.setImageResource();

        holder.fullName.setText(emp.getFullName());
        holder.level.setText(String.valueOf(employeeList.get(position).getLevel()));
    }

    @Override
    public int getItemCount() {
        return this.employeeList.size();
    }

    static class EmployeeViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.first_letter) public TextView firstLetter;
        @Bind(R.id.photo) public ImageView photo;
        @Bind(R.id.full_name) public TextView fullName;
        @Bind(R.id.level) public TextView level;

        public EmployeeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

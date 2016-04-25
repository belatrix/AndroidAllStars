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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by icerrate on 15/04/2016.
 */
public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.EmployeeViewHolder> {

    static final String EMPTY_STRING = "";
    static final String NUMERIC_SYMBOL = "#";
    static final int CAP_POSITION = 0;

    private List<Employee> employeeList;
    private WeakReference<Context> context;
    private String lastFirstLetter;

    public EmployeeListAdapter(Context context) {
        this(context, new ArrayList<Employee>());
    }

    public EmployeeListAdapter(Context context, List<Employee> employeeList) {
        this.employeeList = employeeList;
        this.context = new WeakReference<>(context);
    }

    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new EmployeeViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        String fullName = employee.getFullName();
        String currentFirstLetter = (fullName != null && !fullName.isEmpty()) ? String.valueOf(fullName.charAt(CAP_POSITION)).toUpperCase() : NUMERIC_SYMBOL;
        String showLetter;
        if (lastFirstLetter == null){
            showLetter = currentFirstLetter;
        }
        else{
            showLetter = (lastFirstLetter.equalsIgnoreCase(currentFirstLetter)) ? EMPTY_STRING : currentFirstLetter;
        }
        holder.firstLetter.setText(showLetter);
        holder.fullName.setText(employee.getFullName());
        holder.level.setText(String.valueOf(employee.getLevel()));

        lastFirstLetter = currentFirstLetter;
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

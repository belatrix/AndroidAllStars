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
package com.belatrixsf.allstars.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.ui.common.RecyclerOnItemClickListener;

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
        holder.fullName.setText(employee.getFullName());
        String levelLabel = String.format(holder.level.getContext().getString(R.string.contact_list_level), String.valueOf(employee.getLevel()));
        holder.level.setText(levelLabel);
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

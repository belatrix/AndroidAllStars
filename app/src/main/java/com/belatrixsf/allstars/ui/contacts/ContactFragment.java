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
package com.belatrixsf.allstars.ui.contacts;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.adapters.EmployeeListAdapter;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.ui.common.AllStarsFragment;
import com.belatrixsf.allstars.utils.AllStarsApplication;
import com.belatrixsf.allstars.utils.di.components.DaggerContactComponent;
import com.belatrixsf.allstars.utils.di.modules.presenters.ContactPresenterModule;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by icerrate on 15/04/2016.
 */
public class ContactFragment extends AllStarsFragment implements ContactView {

    private ContactPresenter contactPresenter;

    @Bind(R.id.rv_employees)
    RecyclerView employeeRecyclerView;
    @Bind(R.id.search)
    ImageButton searchImageButton;
    @Bind(R.id.search_term)
    EditText searchTermEditText;

    public static ContactFragment newInstance() {
        return new ContactFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    protected void initDependencies(AllStarsApplication allStarsApplication) {
        contactPresenter = DaggerContactComponent.builder()
                .applicationComponent(allStarsApplication.getApplicationComponent())
                .contactPresenterModule(new ContactPresenterModule(this))
                .build()
                .contactPresenter();
        contactPresenter.getEmployeeList();
    }

    @Override
    public void showEmployees(List<Employee> employees) {
        EmployeeListAdapter employeeListAdapter = new EmployeeListAdapter(getActivity(), employees);
        employeeRecyclerView.setAdapter(employeeListAdapter);
        employeeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @OnClick(R.id.search)
    public void searchClicked() {
        String searchTerm = searchTermEditText.getText().toString();
        contactPresenter.searchEmployee(searchTerm);
    }
}

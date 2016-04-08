package com.belatrixsf.allstars.ui.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.ui.common.BaseFragment;

/**
 * Created by PedroCarrillo on 4/8/16.
 */
public class ExampleFragment extends BaseFragment<ExampleContractor.ExamplePresenter> implements  ExampleContractor.ExampleView {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mPresenter = new ExamplePresenter();
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_example, container, false);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getBooks();
            }
        });

        return rootView;
    }

    @Override
    public void showBooks() {
        Snackbar.make(getView(), "show books called", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}

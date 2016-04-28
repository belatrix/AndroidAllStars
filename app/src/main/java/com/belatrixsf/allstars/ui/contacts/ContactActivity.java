package com.belatrixsf.allstars.ui.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.view.ActionMode;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.ui.common.AllStarsActivity;

/**
 * Created by pedrocarrillo on 4/26/16.
 */
public class ContactActivity  extends AllStarsActivity implements ContactFragmentListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (savedInstanceState == null) {
            replaceFragment(ContactFragment.newInstance(false), false);
        }
        setNavigationToolbar();
    }


    @Override
    public void setActionMode(ActionMode.Callback callback) {
        startSupportActionMode(callback);
    }

    @Override
    public AppBarLayout getAppBarLayout() {
        return null;
    }

}
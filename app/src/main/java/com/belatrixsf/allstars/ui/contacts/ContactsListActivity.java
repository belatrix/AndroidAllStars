package com.belatrixsf.allstars.ui.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.ui.common.AllStarsActivity;

/**
 * Created by pedrocarrillo on 4/26/16.
 */
public class ContactsListActivity extends AllStarsActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (savedInstanceState == null) {
            replaceFragment(ContactsListFragment.newInstance(false), false);
        }
        setNavigationToolbar();
    }

}
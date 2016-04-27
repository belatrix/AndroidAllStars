package com.belatrixsf.allstars.ui.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.ui.common.AllStarsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by pedrocarrillo on 4/26/16.
 */
public class AccountActivity extends AllStarsActivity {

    public static final String USER_ID_KEY = "_user_id";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            Integer userId = getIntent().getIntExtra(USER_ID_KEY, -1);
            replaceFragment(AccountFragment.newInstance(userId), false);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}

package com.belatrixsf.connect.ui.skills;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.common.BelatrixConnectActivity;

/**
 * Created by echuquilin on 4/08/16.
 */
public class SkillsListActivity extends BelatrixConnectActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (savedInstanceState == null) {
            replaceFragment(SkillsListFragment.newInstance(), false);
        }
        setNavigationToolbar();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SkillsListActivity.class);
    }
}

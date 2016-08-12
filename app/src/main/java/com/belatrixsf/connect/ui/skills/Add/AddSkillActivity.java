package com.belatrixsf.connect.ui.skills.Add;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.common.BelatrixConnectActivity;
import com.belatrixsf.connect.ui.keywords.SearchingKeywordsFragment;
import com.belatrixsf.connect.ui.skills.SkillsListFragment;

import butterknife.Bind;

/**
 * Created by echuquilin on 9/08/16.
 */
public class AddSkillActivity extends BelatrixConnectActivity{

    @Nullable @Bind(R.id.toolbar) protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (savedInstanceState == null) {
            replaceFragment(AddSkillFragment.newInstance(), false);
        }
        setNavigationToolbar();
    }

    @Override
    protected void navigateBack() {
        SkillsListFragment.refreshFromAddSkill(); //fragment already exists
        finish();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddSkillActivity.class);
    }
}

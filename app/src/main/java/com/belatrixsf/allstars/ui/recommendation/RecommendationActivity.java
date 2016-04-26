package com.belatrixsf.allstars.ui.recommendation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.ui.common.AllStarsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by PedroCarrillo on 4/22/16.
 */
public class RecommendationActivity extends AllStarsActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            replaceFragment(new RecommendationFragment(), false);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}

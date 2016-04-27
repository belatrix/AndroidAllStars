package com.belatrixsf.allstars.ui.startrecommendation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.ui.common.AllStarsActivity;

import butterknife.Bind;

/**
 * Created by PedroCarrillo on 4/22/16.
 */
public class StartRecommendationActivity extends AllStarsActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (savedInstanceState == null) {
            replaceFragment(new StartRecommendationFragment(), false);
        }
        setNavigationToolbar();
    }

}

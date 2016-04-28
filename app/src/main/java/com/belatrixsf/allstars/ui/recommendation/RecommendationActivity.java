package com.belatrixsf.allstars.ui.recommendation;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.ui.common.AllStarsActivity;


/**
 * Created by PedroCarrillo on 4/28/16.
 */
public class RecommendationActivity extends AllStarsActivity {

    public static final String SUBCATEGORY_ID = "_category_id";
    public static final String USER_ID = "_user_id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (savedInstanceState == null) {
            Integer categoryId = getIntent().getIntExtra(SUBCATEGORY_ID, -1);
            Integer userId = getIntent().getIntExtra(USER_ID, -1);
            replaceFragment(RecommendationFragment.newInstance(userId, categoryId), false);
        }
        setNavigationToolbar();
    }

}

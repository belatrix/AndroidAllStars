package com.belatrixsf.allstars.ui.recommendation;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.ui.common.AllStarsActivity;
import static com.belatrixsf.allstars.ui.recommendation.RecommendationFragment.CATEGORY_ID;
import static com.belatrixsf.allstars.ui.recommendation.RecommendationFragment.USER_ID;


/**
 * Created by PedroCarrillo on 4/28/16.
 */
public class RecommendationActivity extends AllStarsActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (savedInstanceState == null) {
            Integer categoryId = getIntent().getIntExtra(CATEGORY_ID, -1);
            Integer userId = getIntent().getIntExtra(USER_ID, -1);
            replaceFragment(RecommendationFragment.newInstance(userId, categoryId), false);
        }
        setNavigationToolbar();
    }

}

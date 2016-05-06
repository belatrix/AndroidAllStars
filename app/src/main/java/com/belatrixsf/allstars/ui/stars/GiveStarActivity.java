package com.belatrixsf.allstars.ui.stars;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.ui.common.AllStarsActivity;

import static com.belatrixsf.allstars.ui.stars.GiveStarFragment.SELECTED_USER_KEY;

/**
 * Created by PedroCarrillo on 4/22/16.
 */
public class GiveStarActivity extends AllStarsActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (savedInstanceState == null) {
            Employee employee = null;
            if (getIntent() != null && getIntent().hasExtra(SELECTED_USER_KEY)) {
                employee = getIntent().getParcelableExtra(SELECTED_USER_KEY);
            }
            replaceFragment(GiveStarFragment.newInstance(employee), false);
        }
        setNavigationToolbar();
    }

}

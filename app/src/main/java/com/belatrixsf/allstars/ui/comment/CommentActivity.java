package com.belatrixsf.allstars.ui.comment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.ui.common.AllStarsActivity;
import static com.belatrixsf.allstars.ui.givestar.GiveStarFragment.EXTRA_COMMENT_KEY;

/**
 * Created by PedroCarrillo on 4/27/16.
 */
public class CommentActivity extends AllStarsActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (savedInstanceState == null) {
            String comment = null;
            if (getIntent().getExtras() != null && getIntent().hasExtra(EXTRA_COMMENT_KEY)) {
                comment = getIntent().getStringExtra(EXTRA_COMMENT_KEY);
            }
            replaceFragment(CommentFragment.newInstance(comment), false);
        }
        setNavigationToolbar();
    }

}

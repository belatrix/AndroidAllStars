package com.belatrixsf.allstars.ui.contacts.keyword;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Keyword;
import com.belatrixsf.allstars.ui.common.AllStarsActivity;

/**
 * Created by PedroCarrillo on 5/12/16.
 */
public class ContactsKeywordListActivity extends AllStarsActivity {

    public static final String KEYWORD_KEY = "_keyword_key";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (savedInstanceState == null) {
            Keyword keyword = getIntent().getParcelableExtra(KEYWORD_KEY);
            replaceFragment(ContactsKeywordListFragment.newInstance(keyword), false);
        }
        setNavigationToolbar();
    }

}

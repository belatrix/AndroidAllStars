package com.belatrixsf.allstars.ui.stars.keyword;

import android.content.Intent;
import android.os.Bundle;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Keyword;
import com.belatrixsf.allstars.ui.common.AllStarsActivity;

public class KeywordsActivity extends AllStarsActivity implements KeywordsListFragment.KeywordsListListener {

    public static final String KEYWORD_KEY = "keyword_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (savedInstanceState == null) {
            replaceFragment(KeywordsListFragment.newInstance(), false);
        }
        setNavigationToolbar();
    }

    @Override
    public void onKeywordSelectedForDispatching(Keyword keyword) {
        Intent intent = new Intent();
        intent.putExtra(KEYWORD_KEY, keyword);
        setResult(RESULT_OK, intent);
        finish();
    }

}

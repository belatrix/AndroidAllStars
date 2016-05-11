package com.belatrixsf.allstars.ui.keywords;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Keyword;
import com.belatrixsf.allstars.ui.common.AllStarsActivity;

import java.io.Serializable;

public class KeywordsActivity extends AllStarsActivity implements KeywordsListFragment.KeywordsListListener {

    public static final String KEYWORD_MODE_KEY = "keyword_mode_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (savedInstanceState == null) {
            Serializable serializedMode = getIntent().getSerializableExtra(KEYWORD_MODE_KEY);
            KeywordsMode keywordsMode = serializedMode != null? (KeywordsMode) serializedMode : KeywordsMode.LIST;
            replaceFragment(KeywordsListFragment.newInstance(keywordsMode), false);
        }
    }

    @Override
    public void onKeywordSelectedForDispatching(Keyword keyword) {
        Intent intent = new Intent();
        intent.putExtra(KEYWORD_MODE_KEY, keyword);
        setResult(RESULT_OK, intent);
        finish();
    }

    public static Intent makeIntent(Context context, KeywordsMode keywordsMode) {
        Intent intent = new Intent(context, KeywordsActivity.class);
        intent.putExtra(KEYWORD_MODE_KEY, keywordsMode);
        return intent;
    }

}

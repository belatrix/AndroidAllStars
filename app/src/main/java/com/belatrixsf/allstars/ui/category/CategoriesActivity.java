package com.belatrixsf.allstars.ui.category;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Category;
import com.belatrixsf.allstars.ui.common.AllStarsActivity;

public class CategoriesActivity extends AllStarsActivity implements CategoriesFragment.SubcategorySelectionListener {

    public static final String SUBCATEGORY_KEY = "subcategory_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (savedInstanceState == null) {
            replaceFragment(new CategoriesFragment(), false);
        }
        setNavigationToolbar();
    }

    @Override
    public void onSubcategorySelected(Category subcategory) {
        Intent intent = new Intent();
        intent.putExtra(SUBCATEGORY_KEY, subcategory);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}

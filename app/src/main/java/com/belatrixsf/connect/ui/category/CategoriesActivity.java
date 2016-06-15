/* The MIT License (MIT)
* Copyright (c) 2016 BELATRIX
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:

* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.

* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/
package com.belatrixsf.connect.ui.category;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.entities.Category;
import com.belatrixsf.connect.ui.common.AllStarsActivity;

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

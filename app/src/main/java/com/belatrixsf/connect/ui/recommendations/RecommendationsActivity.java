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
package com.belatrixsf.connect.ui.recommendations;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.Window;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.account.AccountFragmentListener;
import com.belatrixsf.connect.ui.common.BelatrixConnectActivity;

/**
 * Created by dvelasquez on 17/2/17.
 */
public class RecommendationsActivity extends BelatrixConnectActivity implements AccountFragmentListener {

    public static final String USER_ID_KEY = "_user_id";
    public static final String CATEGORY_ID_KEY = "_category_id";
    public static final String SHARED_NAME_KEY = "_shared_name";

    private final int TOOLBAR_ANIMATION_DURATION = 450;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setNavigationToolbar();
        if (savedInstanceState == null) {
            Integer userId = getIntent().getIntExtra(USER_ID_KEY, -1);
            Integer categoryId = getIntent().getIntExtra(CATEGORY_ID_KEY, -1);
            String categoryName = getIntent().getStringExtra(SHARED_NAME_KEY);
            setTitle(categoryName);
            if (supportSharedElements()) {
                toolbar.setTransitionName(categoryName);
                setupEnterSharedAnimation();
                setupExitSharedAnimation();
            }
            replaceFragment(RecommendationsFragment.newInstance(userId, categoryId), false);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupEnterSharedAnimation() {
        Window window = getWindow();
        TransitionSet set = new TransitionSet();
        set.addTransition(new ChangeImageTransform());
        set.addTransition(new ChangeBounds());
        set.setDuration(TOOLBAR_ANIMATION_DURATION);
        window.setSharedElementEnterTransition(set);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupExitSharedAnimation() {
        Window window = getWindow();
        TransitionSet set = new TransitionSet();
        set.addTransition(new ChangeImageTransform());
        set.addTransition(new ChangeBounds());
        set.setDuration(TOOLBAR_ANIMATION_DURATION);
        window.setSharedElementEnterTransition(set);
    }

    @Override
    protected void navigateBack() {
        // both activities are single Task, instead of create a new instance
        // with startActivity it returns to the existing instance
        finish();
    }

    @Override
    public void refreshNavigationDrawer() {
        // Required empty implementation
    }

}

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
package com.belatrixsf.connect.ui.event.detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.common.BelatrixConnectActivity;
import com.belatrixsf.connect.ui.event.notification.EventNewsFragment;
import com.belatrixsf.connect.utils.media.ImageFactory;
import com.belatrixsf.connect.utils.media.loaders.ImageLoader;

import butterknife.Bind;
import butterknife.BindString;

/**
 * Created by icerrate on 27/06/2016.
 */
public class EventDetailActivity extends BelatrixConnectActivity implements EventDetailFragmentListener{

    public static final String EVENT_ID_KEY = "_event_id";

    @Bind(R.id.event_picture) ImageView pictureImageView;
    @Bind(R.id.bottom_navigation) AHBottomNavigation bottomNavigation;
    @BindString(R.string.bottom_navigation_color) String navigationColor;
    private int eventId;
    public static final int TAB_ABOUT = 0;
    public static final int TAB_NEWS = 1;
    @Bind(R.id.collapsing) CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ActivityCompat.postponeEnterTransition(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar);
        setNavigationToolbar();
        if (savedInstanceState == null) {
            eventId = getIntent().getIntExtra(EVENT_ID_KEY, -1);
        }
        initViews();
    }

    private void initViews() {
        setupViews();
        replaceFragment(EventDetailFragment.newInstance(eventId),false);
        bottomNavigation.setCurrentItem(0);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                if (!wasSelected) {
                    switch (position) {

                        case TAB_ABOUT:
                            replaceFragment(EventDetailFragment.newInstance(eventId),false);
                            break;

                        case TAB_NEWS:
                            replaceFragment(EventNewsFragment.newInstance(eventId),false);
                            break;

                    }
                }
                return true;
            }
        });
    }

    private void setupViews() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_event_about, R.drawable.ic_about, R.color.colorAccent);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_event_news, R.drawable.ic_activity, R.color.colorAccent);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setAccentColor(Color.parseColor(navigationColor));
        bottomNavigation.setBehaviorTranslationEnabled(false);
    }

    public static void startActivityAnimatingPic(Activity activity, ImageView photoImageView, Integer eventId) {
        Intent intent = new Intent(activity, EventDetailActivity.class);
        intent.putExtra(EventDetailActivity.EVENT_ID_KEY, eventId);
        ViewCompat.setTransitionName(photoImageView, activity.getString(R.string.transition_photo));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, photoImageView, activity.getString(R.string.transition_photo));
        activity.startActivity(intent, options.toBundle());
    }

    @Override
    public void setTitle(String title) {
        collapsingToolbarLayout.setTitle(title);
    }

    @Override
    public void showPicture(final String profilePicture) {
        if (pictureImageView != null) {
            ImageFactory.getLoader().loadFromUrl(
                    profilePicture,
                    pictureImageView,
                    null,
                    new ImageLoader.Callback() {
                        @Override
                        public void onSuccess() {
                            startTransition();
                        }

                        @Override
                        public void onFailure() {
                            startTransition();
                        }
                    },
                    getResources().getDrawable(R.drawable.event_placeholder),
                    ImageLoader.ScaleType.CENTER_CROP
            );
        }
    }

    private void startTransition() {
        if (pictureImageView == null) {
            return;
        }
        pictureImageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                pictureImageView.getViewTreeObserver().removeOnPreDrawListener(this);
                ActivityCompat.startPostponedEnterTransition(EventDetailActivity.this);
                return false;
            }
        });
    }

}

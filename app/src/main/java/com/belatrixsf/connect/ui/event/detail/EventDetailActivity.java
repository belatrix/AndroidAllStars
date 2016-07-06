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
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.common.BelatrixConnectActivity;
import com.belatrixsf.connect.utils.media.ImageFactory;
import com.belatrixsf.connect.utils.media.loaders.ImageLoader;

import butterknife.Bind;

/**
 * Created by icerrate on 27/06/2016.
 */
public class EventDetailActivity extends BelatrixConnectActivity implements EventDetailFragmentListener {

    public static final String EVENT_ID_KEY = "_event_id";

    @Bind(R.id.event_picture) ImageView pictureImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ActivityCompat.postponeEnterTransition(this);
        setNavigationToolbar();
        if (savedInstanceState == null) {
            Integer eventId = getIntent().getIntExtra(EVENT_ID_KEY, -1);
            replaceFragment(EventDetailFragment.newInstance(eventId), false);
        }
    }

    public static void startActivityAnimatingPic(Activity activity, ImageView photoImageView, Integer eventId) {
        Intent intent = new Intent(activity, EventDetailActivity.class);
        intent.putExtra(EventDetailActivity.EVENT_ID_KEY, eventId);
        ViewCompat.setTransitionName(photoImageView, activity.getString(R.string.transition_photo));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, photoImageView, activity.getString(R.string.transition_photo));
        activity.startActivity(intent, options.toBundle());
    }

    @Override
    public void showPicture(final String profilePicture) {
        ImageFactory.getLoader().loadFromUrl(
                profilePicture,
                pictureImageView,
                ImageLoader.ImageTransformation.BORDERED_CIRCLE,
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
                getResources().getDrawable(R.drawable.event_placeholder)
        );
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

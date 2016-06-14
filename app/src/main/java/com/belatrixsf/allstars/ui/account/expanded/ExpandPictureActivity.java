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
package com.belatrixsf.allstars.ui.account.expanded;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.widget.ImageView;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.ui.common.AllStarsActivity;

/**
 * Created by icerrate on 10/06/2016.
 */
public class ExpandPictureActivity extends AllStarsActivity {

    public static final String USER_AVATAR_KEY = "_user_avatar";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_picture);
        ActivityCompat.postponeEnterTransition(this);
        setNavigationToolbar();
        if (savedInstanceState == null) {
            String avatarUrl = getIntent().getStringExtra(USER_AVATAR_KEY);
            replaceFragment(ExpandPictureFragment.newInstance(avatarUrl), false);
        }
    }

    public static void startActivityAnimatingProfilePic(Activity activity, ImageView photoImageView, String url) {
        Intent intent = new Intent(activity, ExpandPictureActivity.class);
        intent.putExtra(ExpandPictureActivity.USER_AVATAR_KEY, url);
        ViewCompat.setTransitionName(photoImageView, activity.getString(R.string.transition_photo));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, photoImageView, activity.getString(R.string.transition_photo));
        activity.startActivity(intent, options.toBundle());
    }

}

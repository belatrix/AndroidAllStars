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
package com.belatrixsf.connect.ui.account;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.widget.ImageView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.common.BelatrixConnectActivity;
import com.belatrixsf.connect.ui.contacts.ContactsListActivity;
import com.belatrixsf.connect.ui.home.UserActivity;
import com.belatrixsf.connect.utils.MediaUtils;

/**
 * Created by pedrocarrillo on 4/26/16.
 */
public class AccountActivity extends BelatrixConnectActivity implements AccountFragmentListener {

    public static final String USER_ID_KEY = "_user_id";
    public static final String USER_IMG_PROFILE_KEY = "_user_img_profile";
    public static int PARENT_ACTIVITY_INDEX = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ActivityCompat.postponeEnterTransition(this);
        setNavigationToolbar();
        if (savedInstanceState == null) {
            Integer userId = getIntent().getIntExtra(USER_ID_KEY, -1);
            byte[] bytesImg = getIntent().getExtras().getByteArray(USER_IMG_PROFILE_KEY);
            replaceFragment(AccountFragment.newInstance(userId, bytesImg), false);
        }
    }

    public static void startActivityAnimatingProfilePic(Activity activity, ImageView photoImageView, Integer employeeId) {
        Drawable drawable = photoImageView.getDrawable();
        Intent intent = new Intent(activity, AccountActivity.class);
        intent.putExtra(AccountActivity.USER_ID_KEY, employeeId);
        intent.putExtra(AccountActivity.USER_IMG_PROFILE_KEY, MediaUtils.compressDrawable(drawable));
        ViewCompat.setTransitionName(photoImageView, activity.getString(R.string.transition_photo));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, photoImageView, activity.getString(R.string.transition_photo));
        activity.startActivity(intent, options.toBundle());
    }

    @Override
    protected void navigateBack() {
        // both activities are single Task, instead of create a new instance
        // with startActivity it returns to the existing instance

        //TODO: review this condition, is causing a weird behavior on the first time that select a contact and try to back
        //if (PARENT_ACTIVITY_INDEX == UserActivity.PARENT_INDEX) {
            startActivity(UserActivity.makeIntent(this));
        //} else{
        //    startActivity(ContactsListActivity.makeIntent(this));
        //}
    }

    @Override
    public void refreshNavigationDrawer() {
        // Required empty implementation
    }

}

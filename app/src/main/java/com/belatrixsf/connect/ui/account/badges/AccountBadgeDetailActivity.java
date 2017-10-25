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
package com.belatrixsf.connect.ui.account.badges;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.entities.Badge;
import com.belatrixsf.connect.ui.common.BelatrixConnectActivity;
import com.belatrixsf.connect.utils.media.ImageFactory;
import com.belatrixsf.connect.utils.media.loaders.ImageLoader;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by dvelasquez on 27/03/2017.
 */
public class AccountBadgeDetailActivity extends BelatrixConnectActivity{

    public static final String BADGE = "_badge_";
    @Bind(R.id.badge_icon)
    ImageView badgeImageView;
    @Bind(R.id.badge_title)
    TextView badgeTitleTextView;
    @Bind(R.id.badge_description)
    TextView badgeDescriptionTextView;
    private Badge badge;

    @OnClick(R.id.close_button)
    public void onClickClose(){
        onBackPressed();
    }

    @OnClick(R.id.share_button)
    public void onClickShare(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareText = (badge.getSharingText() == null || badge.getSharingText().isEmpty()? badge.getDescription(): badge.getSharingText());
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareText + "\n" +badge.getIcon());
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge_detail);
        if (supportSharedElements())
            postponeEnterTransition();

        if (savedInstanceState == null) {
            badge = getIntent().getParcelableExtra(BADGE);
        }
        initViews();
    }

    private void initViews() {
        badgeTitleTextView.setText(badge.getName());
        badgeDescriptionTextView.setText(badge.getDescription());
        ImageFactory.getLoader().loadFromUrl(badge.getIcon(),
                badgeImageView,
                ImageLoader.ImageTransformation.BORDERED_CIRCLE,
                new ImageLoader.Callback() {
                    @Override
                    public void onSuccess() {
                        if(supportSharedElements())
                            startPostponedEnterTransition();
                    }

                    @Override
                    public void onFailure() {
                        if(supportSharedElements())
                            startPostponedEnterTransition();
                    }
                },
                badgeImageView.getResources().getDrawable(R.drawable.contact_placeholder),
                ImageLoader.ScaleType.CENTER_CROP
        );
    }


    public static void startActivityAnimatingPic(Activity activity, ImageView photoImageView, Badge badge) {
        Intent intent = new Intent(activity, AccountBadgeDetailActivity.class);
        intent.putExtra(AccountBadgeDetailActivity.BADGE, badge);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, photoImageView, ViewCompat.getTransitionName(photoImageView));
        activity.startActivity(intent, options.toBundle());
    }
}

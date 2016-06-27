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
package com.belatrixsf.connect.ui.account.expanded;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.di.modules.presenters.ExpandPicturePresenterModule;
import com.belatrixsf.connect.utils.media.ImageFactory;
import com.belatrixsf.connect.utils.media.loaders.ImageLoader;

import butterknife.Bind;

import static com.belatrixsf.connect.ui.account.expanded.ExpandPictureActivity.USER_AVATAR_KEY;

/**
 * Created by icerrate on 10/06/2016.
 */
public class ExpandPictureFragment extends BelatrixConnectFragment implements ExpandPictureView {

    private ExpandPicturePresenter expandPicturePresenter;

    @Bind(R.id.profile_picture) ImageView pictureImageView;

    public static ExpandPictureFragment newInstance(String avatarUrl) {
        Bundle bundle = new Bundle();
        if (avatarUrl != null) {
            bundle.putString(USER_AVATAR_KEY, avatarUrl);
        }
        ExpandPictureFragment accountFragment = new ExpandPictureFragment();
        accountFragment.setArguments(bundle);
        return accountFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expand_picture, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        String avatarUrl = null;
        if (getArguments() != null) {
            if (getArguments().containsKey(USER_AVATAR_KEY)) {
                avatarUrl = getArguments().getString(USER_AVATAR_KEY);
            }
        }
        expandPicturePresenter.setAvatarUrl(avatarUrl);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPicture();
    }

    private void setupViews() {

    }

    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        expandPicturePresenter = belatrixConnectApplication.getApplicationComponent()
                .expandPictureComponent(new ExpandPicturePresenterModule(this))
                .expandPicturePresenter();
    }

    @Override
    public void showProfilePicture(final String profilePicture) {
        ImageFactory.getLoader().loadFromUrl(
                profilePicture,
                pictureImageView,
                null,
                new ImageLoader.Callback() {
                    @Override
                    public void onSuccess() {
                        startPostponedEnterTransition();
                    }

                    @Override
                    public void onFailure() {
                        startPostponedEnterTransition();
                    }
                },
                pictureImageView.getResources().getDrawable(R.drawable.contact_placeholder)
        );
    }

    private void startPostponedEnterTransition() {
        if (pictureImageView == null) {
            return;
        }
        pictureImageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                pictureImageView.getViewTreeObserver().removeOnPreDrawListener(this);
                ActivityCompat.startPostponedEnterTransition(getActivity());
                return false;
            }
        });
    }

    @Override
    public void showProgressDialog() {
    }

    @Override
    public void showProgressIndicator() {

    }

    @Override
    public void dismissProgressDialog() {

    }

    @Override
    public void hideProgressIndicator() {

    }

    private void loadPicture() {
        expandPicturePresenter.loadPicture();
    }


}
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

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.MediaUtils;
import com.belatrixsf.connect.utils.di.modules.presenters.ExpandPicturePresenterModule;

import butterknife.Bind;
import butterknife.OnClick;

import static com.belatrixsf.connect.ui.account.expanded.ExpandPictureActivity.USER_AVATAR_KEY;

/**
 * Created by icerrate on 10/06/2016.
 */
public class ExpandPictureFragment extends BelatrixConnectFragment implements ExpandPictureView {

    private ExpandPicturePresenter expandPicturePresenter;

    @Bind(R.id.profile_picture) ImageView pictureImageView;
    @Bind(R.id.expand_background_relative) View background;

    public static ExpandPictureFragment newInstance(byte[] bytesImg) {
        Bundle bundle = new Bundle();
        if (bytesImg != null) {
            bundle.putByteArray(USER_AVATAR_KEY, bytesImg);
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
        if (savedInstanceState == null) {
            byte[] avatarUrl = getArguments().getByteArray(USER_AVATAR_KEY);
            loadPicture(MediaUtils.get().getBitmapFromByteArray(avatarUrl));
        }
    }

    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        expandPicturePresenter = belatrixConnectApplication.getApplicationComponent()
                .expandPictureComponent(new ExpandPicturePresenterModule(this))
                .expandPicturePresenter();
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

    @OnClick(R.id.expand_background_relative)
    public void onPictureClicked() {
        fragmentListener.fragmentBackPressed();
    }

    private void loadPicture(Bitmap bitmap) {
        if (pictureImageView != null) {
            pictureImageView.setImageBitmap(bitmap);
        }
    }

}
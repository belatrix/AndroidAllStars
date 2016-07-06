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
package com.belatrixsf.connect.ui.common.views;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.utils.media.ImageFactory;
import com.belatrixsf.connect.utils.media.loaders.ImageLoader;

/**
 * Created by PedroCarrillo on 4/25/16.
 */
public class AccountSelectionView extends DataSelectionView {

    private String profileImageUrl;
    private ImageView profileImageView;
    private TextView fullNameTextView;
    private TextView levelTextView;

    public AccountSelectionView(Context context) {
        super(context);
    }

    public AccountSelectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initValueView() {
        dataStub.setLayoutResource(R.layout.item_contact);
        valueView = dataStub.inflate();
        valueView.setPadding(0, 0, 0, 0);
        valueView.setVisibility(View.GONE);
        profileImageView = (ImageView) valueView.findViewById(R.id.contact_photo);
        fullNameTextView = (TextView) valueView.findViewById(R.id.contact_full_name);
        levelTextView = (TextView) valueView.findViewById(R.id.contact_level);
    }

    @Override
    public void setData(String data) {
        //Empty because is a custom view.
    }

    public void setProfileImage(String imageUrl) {
        profileImageUrl = imageUrl;
        ImageFactory.getLoader().loadFromUrl(imageUrl,
                profileImageView,
                ImageLoader.ImageTransformation.CIRCLE,
                profileImageView.getResources().getDrawable(R.drawable.contact_placeholder)
        );
    }

    public void setFullName(String fullName) {
        fullNameTextView.setText(fullName);
    }

    public void setLevel(String level) {
        String levelLabel = String.format(levelTextView.getContext().getString(R.string.contact_list_level), String.valueOf(level));
        levelTextView.setText(levelLabel);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState state = new SavedState(superState);
        if (fullNameTextView != null){
            state.fullName = fullNameTextView.getText();
        }
        if (levelTextView != null){
            state.level = levelTextView.getText();
        }
        if (profileImageUrl != null){
            state.imageUrl = profileImageUrl;
        }
        return state;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState)state;
        super.onRestoreInstanceState(ss.getSuperState());
        if (ss.fullName != null && ss.level != null) {
            fullNameTextView.setText(ss.fullName);
            levelTextView.setText(ss.level);
            setProfileImage(ss.imageUrl);
            showData();
        } else {
            showHint();
        }
    }

    public static class SavedState extends android.view.View.BaseSavedState {

        String imageUrl;
        CharSequence fullName;
        CharSequence level;

        SavedState(Parcel in) {
            super(in);
            fullName = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            level = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            imageUrl = in.readString();
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            TextUtils.writeToParcel(fullName, out, flags);
            TextUtils.writeToParcel(level, out, flags);
            out.writeString(imageUrl);
        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

}

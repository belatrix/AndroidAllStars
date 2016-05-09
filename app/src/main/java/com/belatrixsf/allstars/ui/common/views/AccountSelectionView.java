package com.belatrixsf.allstars.ui.common.views;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.utils.media.ImageFactory;
import com.belatrixsf.allstars.utils.media.loaders.ImageLoader;

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
        ImageFactory.getLoader().loadFromUrl(imageUrl, profileImageView, ImageLoader.ImageTransformation.CIRCLE);
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
        if (ss.fullName != null) {
            fullNameTextView.setText(ss.fullName);
            showData();
        }
        if (ss.level != null) {
            levelTextView.setText(ss.level);
        }
        if (ss.imageUrl != null) {
            setProfileImage(ss.imageUrl);
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

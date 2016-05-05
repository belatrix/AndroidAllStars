package com.belatrixsf.allstars.ui.common.views;

import android.content.Context;
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
        ImageFactory.getLoader().loadFromUrl(imageUrl, profileImageView, ImageLoader.ImageTransformation.CIRCLE);
    }

    public void setFullName(String fullName) {
        fullNameTextView.setText(fullName);
    }

    public void setLevel(String level) {
        String levelLabel = String.format(levelTextView.getContext().getString(R.string.contact_list_level), String.valueOf(level));
        levelTextView.setText(levelLabel);
    }

}

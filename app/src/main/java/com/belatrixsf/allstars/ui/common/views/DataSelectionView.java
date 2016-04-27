package com.belatrixsf.allstars.ui.common.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.belatrixsf.allstars.R;

/**
 * Created by PedroCarrillo on 4/25/16.
 */
public class DataSelectionView extends View {

    private ImageView rightArrowImageView;
    private TextView hintTextView;

    public DataSelectionView(Context context) {
        super(context);
        initView();
    }

    public DataSelectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_data_selection, (ViewGroup)this.getParent() , true);
        rightArrowImageView = (ImageView) findViewById(R.id.iv_right_arrow);
        hintTextView = (TextView) findViewById(R.id.hint_text_view);
    }

    public void showHint() {
        hintTextView.setVisibility(View.VISIBLE);
        rightArrowImageView.setVisibility(View.VISIBLE);
    }

    public void showUserData() {
        // show the user data according to the data passed by the parameters
        hintTextView.setVisibility(View.GONE);
        rightArrowImageView.setVisibility(View.GONE);
    }


}

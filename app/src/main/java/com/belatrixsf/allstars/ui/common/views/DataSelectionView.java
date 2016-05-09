package com.belatrixsf.allstars.ui.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.belatrixsf.allstars.R;

/**
 * Created by PedroCarrillo on 4/25/16.
 */
public class DataSelectionView extends LinearLayout {

    private ImageView rightArrowImageView;
    private TextView hintTextView;
    private String selectionValue;

    /**
     * Views for default data views
     */
    protected ViewStub dataStub;
    protected View valueView;

    public DataSelectionView(Context context) {
        super(context);
        initView();
    }

    public DataSelectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DataSelectionView);

        String hintText = typedArray.getString(R.styleable.DataSelectionView_hint_text);
        hintTextView.setText(hintText);

        typedArray.recycle();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_data_selection, this, true);
        rightArrowImageView = (ImageView) findViewById(R.id.iv_right_arrow);
        hintTextView = (TextView) findViewById(R.id.hint_text_view);
        dataStub = (ViewStub) findViewById(R.id.dataStub);
        initValueView();
    }

    protected void initValueView() {
        valueView = dataStub.inflate();
    }

    public void showHint() {
        hintTextView.setVisibility(View.VISIBLE);
        hideValueView();
    }

    protected void hideValueView() {
        valueView.setVisibility(View.GONE);
    }

    public void setData(String data) {
        if (valueView != null) {
            ((TextView)valueView).setText(data);
            selectionValue = data;
        }
    }

    public void showData() {
        hintTextView.setVisibility(View.GONE);
        showValueView();
    }

    protected void showValueView() {
        valueView.setVisibility(View.VISIBLE);
    }

    public void setArrowVisibility(int arrowVisibility) {
        rightArrowImageView.setVisibility(arrowVisibility);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState state = new SavedState(superState);
        if (selectionValue != null){
            state.selectionValue = selectionValue;
        }
        return state;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedState = (SavedState)state;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.selectionValue != null) {
            setData(savedState.selectionValue.toString());
            showData();
        }
    }

    public static class SavedState extends android.view.View.BaseSavedState {

        CharSequence selectionValue;

        SavedState(Parcel in) {
            super(in);
            selectionValue = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            TextUtils.writeToParcel(selectionValue, out, flags);
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

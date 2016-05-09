package com.belatrixsf.allstars.ui.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.belatrixsf.allstars.R;

/**
 * Created by icerrate on 06/05/2016.
 */
public class KeywordView extends LinearLayout {

    private TextView keyboardTextView;
    private String value;

    public KeywordView(Context context) {
        super(context);
        initView();
    }

    public KeywordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.KeywordView);

        String text = typedArray.getString(R.styleable.KeywordView_key_text);
        keyboardTextView.setText(text);

        typedArray.recycle();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_keyword, this, true);
        keyboardTextView = (TextView) findViewById(R.id.keyword);
    }

    public void setKeyword(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            keyboardTextView.setText(keyword);
            value = keyword;
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState state = new SavedState(superState);
        if (value != null){
            state.value = value;
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
        if (ss.value != null) {
            setKeyword(ss.value.toString());
        }
    }

    public static class SavedState extends BaseSavedState {

        CharSequence value;

        SavedState(Parcel in) {
            super(in);
            value = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            TextUtils.writeToParcel(value, out, flags);
        }

        public static final Creator<SavedState> CREATOR
                = new Creator<SavedState>() {
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

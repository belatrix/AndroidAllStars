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

    private TextView keywordTextView;
    private String keywordValue;

    public KeywordView(Context context) {
        super(context);
        initView();
    }

    public KeywordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.KeywordView);

        String text = typedArray.getString(R.styleable.KeywordView_key_text);
        keywordTextView.setText(text);

        typedArray.recycle();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_keyword, this, true);
        keywordTextView = (TextView) findViewById(R.id.keyword);
    }

    public void setKeyword(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            keywordTextView.setText(keyword);
            keywordValue = keyword;
        }
    }

    public String getKeyword() {
        return (keywordTextView != null) ? keywordTextView.getText().toString() : null;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        findViewById(R.id.keyword_hashtag).setEnabled(enabled);
        keywordTextView.setEnabled(enabled);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState state = new SavedState(superState);
        if (keywordValue != null){
            state.keywordValue = keywordValue;
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
        if (savedState.keywordValue != null) {
            setKeyword(savedState.keywordValue.toString());
        }
    }

    public static class SavedState extends BaseSavedState {

        CharSequence keywordValue;

        SavedState(Parcel in) {
            super(in);
            keywordValue = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            TextUtils.writeToParcel(keywordValue, out, flags);
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

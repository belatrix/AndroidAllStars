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

package com.belatrixsf.allstars.ui.common.views;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import com.belatrixsf.allstars.R;

/**
 * Created by PedroCarrillo on 5/9/16.
 */
public class KeywordSelectionView extends DataSelectionView {

    private KeywordView keywordView;

    public KeywordSelectionView(Context context) {
        super(context);
    }

    public KeywordSelectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initValueView() {
        dataStub.setLayoutResource(R.layout.item_keyword_selection);
        valueView = dataStub.inflate();
        valueView.setVisibility(View.GONE);
        keywordView = (KeywordView) valueView.findViewById(R.id.keywordView);
        keywordView.setEnabled(false);
    }

    @Override
    public void setData(String data) {
        //Empty because is a custom view.
    }

    public void setKeyword(String keyword) {
        keywordView.setKeyword(keyword);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState state = new SavedState(superState);
        if (keywordView != null) {
            state.keyword = keywordView.getKeyword();
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
        if (savedState.keyword != null && !savedState.keyword.isEmpty()) {
            keywordView.setKeyword(savedState.keyword);
            showData();
        } else {
            showHint();
        }
        super.onRestoreInstanceState(savedState.getSuperState());
    }

    public static class SavedState extends android.view.View.BaseSavedState {

        String keyword;

        SavedState(Parcel in) {
            super(in);
            keyword = in.readString();
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(keyword);
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

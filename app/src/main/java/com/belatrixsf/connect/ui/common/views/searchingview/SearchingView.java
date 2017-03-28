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
package com.belatrixsf.connect.ui.common.views.searchingview;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.utils.KeyboardUtils;

/**
 * Created by gyosida on 5/10/16.
 */
public class SearchingView extends LinearLayout implements SearchableView {

    private SearchingViewPresenter searchingViewPresenter = new SearchingViewPresenter(this);
    private SearchingListener searchingListener;
    private EditText searchingEditText;
    private ImageButton clearImageButton;

    public SearchingView(Context context) {
        this(context, null);
    }

    public SearchingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    public void setCustomHint(String hint){
        searchingEditText.setHint(hint);
    }

    public void setInputRegex(final String regex){
        searchingEditText.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        if(cs.equals("") || cs.toString().matches(regex)){
                            return cs;
                        }
                        return "";
                    }
                }
        });
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_action_mode, this, true);
        searchingEditText = (EditText) findViewById(R.id.search_term);
        clearImageButton = (ImageButton) findViewById(R.id.clean);
        setViewListeners();
        post(new Runnable() {
            @Override
            public void run() {
                searchingEditText.requestFocus();
                KeyboardUtils.showKeyboard((Activity) getContext(), searchingEditText);
            }
        });
    }

    private void setViewListeners() {
        searchingEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchingViewPresenter.searchingTextTyped(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        searchingEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchingViewPresenter.onSearchImeOptionClicked();
                    return true;
                }
                return false;
            }
        });
        clearImageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                searchingViewPresenter.clearSearching();
            }
        });
    }

    @Override
    public void changeClearButtonVisibility(boolean show) {
        clearImageButton.setVisibility(show? VISIBLE : INVISIBLE);
    }

    @Override
    public void notifySearchImeOption() {
        KeyboardUtils.hideKeyboard((Activity) getContext(), searchingEditText);
    }

    @Override
    public void clearSearching() {
        searchingEditText.setText("");
    }

    @Override
    public void notifyTextTyped(String typedText) {
        searchingListener.onSearchingTextTyped(typedText);
    }

    public void setSearchingListener(SearchingListener searchingListener) {
        this.searchingListener = searchingListener;
    }

    public interface SearchingListener {
        void onSearchingTextTyped(String searchText);
    }

}

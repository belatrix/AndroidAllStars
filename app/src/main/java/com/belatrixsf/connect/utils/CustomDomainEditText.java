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
package com.belatrixsf.connect.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import com.belatrixsf.connect.R;

/**
 * Created by echuquilin on 24/01/17.
 */
public class CustomDomainEditText extends TextInputEditText {

    private String defaultDomain;
    private String defaultUsername;
    private boolean isInitialized = false;
    private final int hintColor = getResources().getColor(R.color.grey);
    private final char atChar = '@';

    public CustomDomainEditText(Context context) {
        super(context);
    }

    public CustomDomainEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomDomainEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        if (isInitialized) {
            int atIndex = getText().toString().indexOf(atChar);
            if (atIndex > -1) {
                String username = getUserName();
                if (username.equals(defaultUsername)) {
                    if (selStart >= 0 && selEnd >= 0) {
                        setSelection(0, 0);
                        return;
                    }
                } else {
                    if (selStart <= atIndex && selEnd >= atIndex) {
                        setSelection(selStart, atIndex);
                        return;
                    } else if (selStart > atIndex && selEnd > atIndex) {
                        setSelection(atIndex, atIndex);
                        return;
                    }
                }
            }
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (isInitialized) {
            String userText = getUserName();
            if (userText.isEmpty()) {
                setTextWithDomain(defaultUsername);
                setFormat(hintColor);
            } else if (!userText.equals(defaultUsername) && userText.contains(defaultUsername)) {
                setTextWithDomain(userText.replace(defaultUsername, ""));
                setFormat(Color.BLACK);
            }
        }
    }

    public void setDefaultDomain(String defaultDomain) {
        this.defaultDomain = defaultDomain;
    }

    public String getDefaultDomain() {
        return defaultDomain;
    }

    public void setDefaultUsername(String defaultUsername) {
        this.defaultUsername = defaultUsername;
        setTextWithDomain(defaultUsername);
        isInitialized = true;
    }

    public String getDefaultUsername() {
        return defaultUsername;
    }

    public String getUserName() {
        int atIndex = getText().toString().indexOf(atChar);
        return (atIndex > -1) ? getText().toString().substring(0, atIndex) : "";
    }

    public void setTextWithDomain(String text) {
        setText(text + defaultDomain);
        if (text.equals(defaultUsername)) {
            setFormat(hintColor);
        } else {
            setFormat(Color.BLACK);
        }
    }

    private void setFormat(int color) {
        int atIndex = getText().toString().indexOf(atChar);
        if (atIndex > -1) {
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
            getText().setSpan(colorSpan, 0, atIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
    }
}

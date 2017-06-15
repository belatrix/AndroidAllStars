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
package com.belatrixsf.connect.ui.event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.common.BelatrixConnectActivity;

/**
 * Created by icerrate on 13/06/2016.
 * modified by dvelasquez on 21/02/2017
 */
public class EventListActivity extends BelatrixConnectActivity {

    public static String EVENT_TYPE_KEY = "_event_type_key";
    public static String EVENT_TITLE_KEY = "_event_title_key";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setNavigationToolbar();
        if (savedInstanceState == null) {
            String eventType = getIntent().getStringExtra(EVENT_TYPE_KEY);
            String eventTitle = getIntent().getStringExtra(EVENT_TITLE_KEY);
            replaceFragment(EventListFragment.newInstance(eventType, eventTitle), false);
        }
    }


    public static void startActivity(Activity activity, String eventType, String title) {
        Intent intent = new Intent(activity, EventListActivity.class);
        intent.putExtra(EventListActivity.EVENT_TYPE_KEY, eventType);
        intent.putExtra(EventListActivity.EVENT_TITLE_KEY, title);
        activity.startActivity(intent);
    }

}
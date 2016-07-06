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
package com.belatrixsf.connect.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.account.AccountFragment;
import com.belatrixsf.connect.ui.contacts.ContactsListFragment;
import com.belatrixsf.connect.ui.keywords.SearchingKeywordsFragment;
import com.belatrixsf.connect.ui.ranking.RankingContainerFragment;

import java.lang.ref.WeakReference;

/**
 * Created by PedroCarrillo on 4/14/16.
 */
public class UserNavigationViewPagerAdapter extends FragmentPagerAdapter {

    public static final int NUM_TABS = 4;
    private WeakReference<Context> contextWeakReference;

    public UserNavigationViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        contextWeakReference = new WeakReference<>(context);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return AccountFragment.newInstance(null);
            case 1:
                return RankingContainerFragment.newInstance();
            case 2:
                return ContactsListFragment.newInstance(true);
            default:
                return SearchingKeywordsFragment.newInstance();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return contextWeakReference.get().getString(R.string.tab_account_title);
            case 1:
                return contextWeakReference.get().getString(R.string.tab_ranking_title);
            case 2:
                return contextWeakReference.get().getString(R.string.tab_contacts_title);
            default:
                return contextWeakReference.get().getString(R.string.tab_keywords_title);
        }
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }
}

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
package com.belatrixsf.allstars.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.belatrixsf.allstars.ui.account.AccountFragment;
import com.belatrixsf.allstars.ui.contacts.ContactsListFragment;
import com.belatrixsf.allstars.ui.keywords.KeywordsListFragment;
import com.belatrixsf.allstars.ui.keywords.KeywordsMode;
import com.belatrixsf.allstars.ui.ranking.RankingContainerFragment;

/**
 * Created by PedroCarrillo on 4/14/16.
 */
public class MainNavigationViewPagerAdapter extends FragmentPagerAdapter {

    public static final int NUM_TABS = 4;

    public MainNavigationViewPagerAdapter(FragmentManager fm) {
        super(fm);
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
                return KeywordsListFragment.newInstance(KeywordsMode.SEARCH.getCode());
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Account";
            case 1:
                return "Ranking";
            case 2:
                return "Contacts";
            default:
                return "Keywords";
        }
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }
}

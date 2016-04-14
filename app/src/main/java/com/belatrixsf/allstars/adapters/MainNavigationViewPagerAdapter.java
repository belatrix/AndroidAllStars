package com.belatrixsf.allstars.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.belatrixsf.allstars.ui.account.AccountFragment;

/**
 * Created by PedroCarrillo on 4/14/16.
 */
public class MainNavigationViewPagerAdapter extends FragmentPagerAdapter {

    public static final int NUM_TABS = 3;

    public MainNavigationViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return AccountFragment.newInstance();
            case 1:
                return AccountFragment.newInstance();
            case 2:
                return AccountFragment.newInstance();
            default:
                return AccountFragment.newInstance();
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
                return "Account";
        }
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }
}

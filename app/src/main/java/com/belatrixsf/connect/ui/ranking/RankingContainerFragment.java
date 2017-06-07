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
package com.belatrixsf.connect.ui.ranking;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.Constants;

import butterknife.Bind;
import butterknife.BindString;

/**
 * Created by pedrocarrillo on 4/28/16.
 */
public class RankingContainerFragment extends BelatrixConnectFragment {

    public static final int TAB_LAST_MONTH = 1;
    public static final int TAB_CURRENT_MONTH = 0;
    public static final int TAB_ALL_TIME = 2;

    private int tabPosition = 0;

    @Bind(R.id.bottom_navigation) AHBottomNavigation bottomNavigation;
    @BindString(R.string.bottom_navigation_color) String navigationColor;

    public static RankingContainerFragment newInstance() {
        return new RankingContainerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState != null){
            tabPosition = savedInstanceState.getInt("tabPosition");
        }
        return inflater.inflate(R.layout.fragment_ranking_container, container, false);
    }

    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        //rankingPresenter = belatrixConnectApplication.getApplicationComponent()
          //      .rankingComponent(new RankingPresenterModule(this))
            //    .rankingPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();

    }

    private void initViews() {
        setupViews();
        selectFragmentBySelectedPosition(tabPosition);
        bottomNavigation.setCurrentItem(tabPosition);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (!wasSelected) {
                    tabPosition = position;
                    selectFragmentBySelectedPosition(tabPosition);
                }
                return true;
            }
        });
    }

    private void setupViews() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_current_month, R.drawable.ic_whatshot, R.color.colorAccent);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_last_month, R.drawable.ic_event, R.color.colorAccent);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_all_time, R.drawable.ic_star, R.color.colorAccent);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setAccentColor(Color.parseColor(navigationColor));
        bottomNavigation.setBehaviorTranslationEnabled(false);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("tabPosition",tabPosition);
        super.onSaveInstanceState(outState);
    }

    private void selectFragmentBySelectedPosition(int tabPosition){
        final int idFragmentContainer = R.id.fragment_ranking_container;
        switch (tabPosition) {
            case TAB_CURRENT_MONTH:
                replaceChildFragment(RankingFragment.newInstance(Constants.KIND_CURRENT_MONTH),idFragmentContainer);
                break;

            case TAB_LAST_MONTH:
                replaceChildFragment(RankingFragment.newInstance(Constants.KIND_LAST_MONTH_SCORE), idFragmentContainer);
                break;

            case TAB_ALL_TIME:
                replaceChildFragment(RankingFragment.newInstance(Constants.KIND_TOTAL_SCORE),idFragmentContainer);
                break;
        }
    }

}

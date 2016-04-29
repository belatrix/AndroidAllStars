package com.belatrixsf.allstars.ui.ranking;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.utils.Constants;

/**
 * Created by pedrocarrillo on 4/28/16.
 */
public class RankingContainerFragment extends Fragment {

    private RankingFragmentListener rankingFragmentListener;
    public static final int TAB_CURRENT_MONTH = 0;
    public static final int TAB_LAST_MONTH = 1;
    public static final int TAB_ALL_TIME = 2;

    public static RankingContainerFragment newInstance() {
        return new RankingContainerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ranking_container, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rankingFragmentListener.setBottomTabListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                if (position == TAB_CURRENT_MONTH && !wasSelected) {
                    replaceChildFragment(RankingFragment.newInstance(Constants.KIND_CURRENT_MONTH));
                } else if (position == TAB_LAST_MONTH && !wasSelected) {
                    replaceChildFragment(RankingFragment.newInstance(Constants.KIND_LAST_MONTH_SCORE));
                } else if (position == TAB_ALL_TIME && !wasSelected) {
                    replaceChildFragment(RankingFragment.newInstance(Constants.KIND_SCORE));
                }
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        castOrThrowException(activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        castOrThrowException(context);
    }

    private void castOrThrowException(Context context) {
        try {
            rankingFragmentListener = (RankingFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ContactFragmentListener");
        }
    }

    public void replaceChildFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        String tag = fragment.getClass().getSimpleName();
        transaction.replace(R.id.fragment_ranking_container, fragment, tag);
        transaction.commit();
    }

}

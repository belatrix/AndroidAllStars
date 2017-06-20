package com.belatrixsf.connect.ui.wizard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.home.UserActivity;
import com.cleveroad.slidingtutorial.IndicatorOptions;
import com.cleveroad.slidingtutorial.OnTutorialPageChangeListener;
import com.cleveroad.slidingtutorial.PageOptions;
import com.cleveroad.slidingtutorial.TransformItem;
import com.cleveroad.slidingtutorial.TutorialOptions;
import com.cleveroad.slidingtutorial.TutorialPageOptionsProvider;
import com.cleveroad.slidingtutorial.TutorialSupportFragment;

/**
 * Created by ggutierrez on 20/06/2017.
 */

public class WizardCustomFragment extends TutorialSupportFragment
        implements OnTutorialPageChangeListener {


    private static final String TAG = "CustomTutorialSFragment";
    private static final int TOTAL_PAGES = 2;
    private static final int ACTUAL_PAGES_COUNT = 3;

    private final View.OnClickListener mOnSkipClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), UserActivity.class);
            getActivity().startActivity(intent);
        }
    };

    private final TutorialPageOptionsProvider mTutorialPageOptionsProvider = new TutorialPageOptionsProvider() {
        @NonNull
        @Override
        public PageOptions provide(int position) {
            @LayoutRes int pageLayoutResId;
            TransformItem[] tutorialItems;
            switch (position) {
                case 0:
                    pageLayoutResId = R.layout.wizard_first_page_fragment;
                    tutorialItems = new TransformItem[]{};
                    break;
                case 1:
                    pageLayoutResId = R.layout.wizard_second_page_fragment;
                    tutorialItems = new TransformItem[]{};
                    break;
                case 2:
                    pageLayoutResId = R.layout.wizard_third_page_fragment;
                    tutorialItems = new TransformItem[]{};
                    break;
                default:
                    throw new IllegalArgumentException("Unknown position: " + position);
            }

            return PageOptions.create(pageLayoutResId, position, tutorialItems);
        }
    };

    /*private final TutorialPageProvider<Fragment> mTutorialPageProvider = new TutorialPageProvider<Fragment>() {
        @NonNull
        @Override
        public Fragment providePage(int position) {
            position %= ACTUAL_PAGES_COUNT;
            switch (position) {
                case 0:
                    return new FirstCustomPageSupportFragment();
                case 1:
                    return new SecondCustomPageSupportFragment();
                case 2:
                    return new ThirdCustomPageSupportFragment();
                default: {
                    throw new IllegalArgumentException("Unknown position: " + position);
                }
            }
        }
    };*/

    private int[] pagesColors;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (pagesColors == null) {
            pagesColors = new int[]{
                    ContextCompat.getColor(getContext(), android.R.color.holo_orange_dark),
                    ContextCompat.getColor(getContext(), android.R.color.holo_green_dark),
                    ContextCompat.getColor(getContext(), android.R.color.holo_blue_dark),
                    ContextCompat.getColor(getContext(), android.R.color.holo_red_dark),
                    ContextCompat.getColor(getContext(), android.R.color.holo_purple),
                    ContextCompat.getColor(getContext(), android.R.color.darker_gray)
            };
        }*/
        addOnTutorialPageChangeListener(this);
    }

    @Override
    protected TutorialOptions provideTutorialOptions() {
        return newTutorialOptionsBuilder(getContext())
                .setUseAutoRemoveTutorialFragment(true)
                .setUseInfiniteScroll(false)
                .setPagesColors(pagesColors)
                .setPagesCount(TOTAL_PAGES)
                .setTutorialPageProvider(mTutorialPageOptionsProvider)
                .setIndicatorOptions(IndicatorOptions.newBuilder(getContext()).build())
//                .setIndicatorOptions(IndicatorOptions.newBuilder(getContext())
//                        .setElementSizeRes(R.dimen.indicator_size)
//                        .setElementSpacingRes(R.dimen.indicator_spacing)
//                        .setElementColorRes(android.R.color.darker_gray)
//                        .setSelectedElementColor(Color.LTGRAY)
//                        .setRenderer(RhombusRenderer.create())
//                        .build())
                .setOnSkipClickListener(mOnSkipClickListener)
                //.setTutorialPageProvider(mTutorialPageProvider)
                .build();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.custom_wizard_layout;
    }

    @Override
    public void onPageChanged(int position) {
        Log.i(TAG, "onPageChanged: position = " + position);
        if (position == TutorialSupportFragment.EMPTY_FRAGMENT_POSITION) {
            Intent intent = new Intent(getActivity(), UserActivity.class);
            getActivity().startActivity(intent);
        }
    }
}

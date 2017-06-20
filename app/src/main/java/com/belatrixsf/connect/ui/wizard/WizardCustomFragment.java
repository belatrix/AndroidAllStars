package com.belatrixsf.connect.ui.wizard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.home.UserActivity;
import com.cleveroad.slidingtutorial.Direction;
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
    private static final int TOTAL_PAGES = 3;
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
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.ivFirstImage, Direction.LEFT_TO_RIGHT, 0.2f)
                    };
                    break;
                case 1:
                    pageLayoutResId = R.layout.wizard_second_page_fragment;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.ivFirstImage, Direction.LEFT_TO_RIGHT, 0.2f)
                    };
                    break;
                case 2:
                    pageLayoutResId = R.layout.wizard_third_page_fragment;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.ivFirstImage, Direction.LEFT_TO_RIGHT, 0.2f)
                    };
                    break;
                default:
                    throw new IllegalArgumentException("Unknown position: " + position);
            }

            return PageOptions.create(pageLayoutResId, position, tutorialItems);
        }
    };


    private int[] pagesColors;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                .setOnSkipClickListener(mOnSkipClickListener)
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

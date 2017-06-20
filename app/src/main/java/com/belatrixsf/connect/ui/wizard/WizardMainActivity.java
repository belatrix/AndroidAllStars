package com.belatrixsf.connect.ui.wizard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.common.BelatrixConnectActivity;
import com.belatrixsf.connect.ui.home.UserActivity;
import com.cleveroad.slidingtutorial.OnTutorialPageChangeListener;
import com.cleveroad.slidingtutorial.TutorialSupportFragment;

/**
 * Created by ggutierrez on 19/06/2017.
 */

public class WizardMainActivity extends BelatrixConnectActivity {

    private int[] mPagesColors;

    private int PAGE_COUNT = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wizard_main_activity);

        mPagesColors = new int[]{
                ContextCompat.getColor(this, R.color.subTitle),
                ContextCompat.getColor(this, R.color.subTitle),
                ContextCompat.getColor(this, R.color.subTitle)
        };

        /*final IndicatorOptions indicatorOptions = IndicatorOptions.newBuilder(this)
                .build();
        TutorialOptions.Builder options = TutorialSupportFragment.newTutorialOptionsBuilder(this);
        options.setPagesCount(PAGE_COUNT).setTutorialPageProvider(new TutorialPageOptionsProvider() {
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
                                TransformItem.create(R.id.ivFirstImage, Direction.LEFT_TO_RIGHT, 0.8f),
                                TransformItem.create(R.id.ivSecondImage, Direction.RIGHT_TO_LEFT, 0.75f),
                                TransformItem.create(R.id.ivThirdImage, Direction.LEFT_TO_RIGHT, 0.2f),
                                TransformItem.create(R.id.ivFourthImage, Direction.RIGHT_TO_LEFT, 0.06f),
                                TransformItem.create(R.id.ivFirstText, Direction.LEFT_TO_RIGHT, 0.06f),
                                TransformItem.create(R.id.ivSecondText, Direction.RIGHT_TO_LEFT, 0.7f),
                                TransformItem.create(R.id.ivThirdText, Direction.LEFT_TO_RIGHT, 0.06f),
                                TransformItem.create(R.id.ivFourthText, Direction.RIGHT_TO_LEFT, 0.4f)
                        };
                        break;
                    /*case 2:
                        pageLayoutResId = R.layout.wizard_third_page_fragment;
                        tutorialItems = new TransformItem[]{
                                TransformItem.create(R.id.ivFirstImage, Direction.LEFT_TO_RIGHT, 0.2f),
                                TransformItem.create(R.id.ivSecondImage, Direction.RIGHT_TO_LEFT, 0.06f)
                        };
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown position: " + position);
                }
                return PageOptions.create(pageLayoutResId, position, tutorialItems);
            }
        }).setUseAutoRemoveTutorialFragment(true)
                .setUseInfiniteScroll(false)
                .setIndicatorOptions(indicatorOptions)
                .setOnSkipClickListener(new OnSkipClickListener(this));
                //.setPagesColors(mPagesColors);*/

        TutorialSupportFragment tutorialFragment = new WizardCustomFragment();
        tutorialFragment.addOnTutorialPageChangeListener(new OnTutorialPageChangeListener() {
            @Override
            public void onPageChanged(int position) {
                Log.i(this.getClass().getSimpleName(), "onPageChanged: position = " + position);
                if (position == TutorialSupportFragment.EMPTY_FRAGMENT_POSITION) {
                    Intent intent = new Intent(WizardMainActivity.this, UserActivity.class);
                    startActivity(intent);
                }
            }
        });
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, tutorialFragment)
                .commit();
    }


    public static Intent makeIntent(Context context) {
        return new Intent(context, WizardMainActivity.class);
    }

    private static final class OnSkipClickListener implements View.OnClickListener {

        @NonNull
        private final Context mContext;

        OnSkipClickListener(@NonNull Context context) {
            mContext = context.getApplicationContext();
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, UserActivity.class);
            mContext.startActivity(intent);
            //startActivity(intent);

        }
    }

}

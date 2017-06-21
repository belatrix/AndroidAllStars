package com.belatrixsf.connect.ui.wizard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.ui.common.BelatrixConnectActivity;
import com.belatrixsf.connect.ui.home.UserActivity;
import com.cleveroad.slidingtutorial.OnTutorialPageChangeListener;
import com.cleveroad.slidingtutorial.TutorialSupportFragment;

/**
 * Created by ggutierrez on 19/06/2017.
 */

public class WizardMainActivity extends BelatrixConnectActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wizard_main_activity);
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

}

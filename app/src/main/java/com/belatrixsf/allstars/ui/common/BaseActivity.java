package com.belatrixsf.allstars.ui.common;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.belatrixsf.allstars.listeners.FragmentListener;

/**
 * @author PedroCarrillo
 */
public class BaseActivity extends AppCompatActivity implements FragmentListener {

    @Override
    public void replaceFragment(int containerId, Fragment fragment, boolean addToBackStack) {

    }

    @Override
    public void replaceFragment(Fragment fragment, boolean addToBackStack) {

    }

    @Override
    public void closeActivity() {
        finish();
    }

    @Override
    public void setTitle() {
    }

}

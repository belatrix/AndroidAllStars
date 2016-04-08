package com.belatrixsf.allstars.listeners;

import android.app.Fragment;

/**
 * Created by PedroCarrillo on 4/8/16.
 */
public interface FragmentListener {

    void replaceFragment(Fragment fragment, boolean addToBackStack);
    void replaceFragment(int containerId, Fragment fragment, boolean addToBackStack);
    void closeActivity();
    void setTitle();

}

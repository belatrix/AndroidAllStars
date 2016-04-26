package com.belatrixsf.allstars.ui.recommendation;

import android.accounts.Account;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.ui.common.AllStarsFragment;
import com.belatrixsf.allstars.ui.common.views.AccountSelectionView;
import com.belatrixsf.allstars.ui.common.views.DataSelectionView;
import com.belatrixsf.allstars.utils.AllStarsApplication;

import butterknife.Bind;

/**
 * Created by PedroCarrillo on 4/22/16.
 */
public class RecommendationFragment extends AllStarsFragment {

    @Bind(R.id.account_selection) AccountSelectionView accountSelectionView;
    @Bind(R.id.category_selection) DataSelectionView categorySelectionView;
    @Bind(R.id.comment_selection) DataSelectionView commentSelectionView;

    boolean aBoolean = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recommendation, container, false);
    }

    @Override
    protected void initDependencies(AllStarsApplication allStarsApplication) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        accountSelectionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aBoolean) {
                    aBoolean = false;
                    accountSelectionView.setFullName("Pedro");
                    accountSelectionView.setLevel("102");
                    accountSelectionView.showData();
                } else {
                    aBoolean = true;
                    accountSelectionView.showHint();
                }
            }
        });

        categorySelectionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aBoolean) {
                    aBoolean = false;
                    categorySelectionView.setData("Test");
                    categorySelectionView.showData();
                } else {
                    aBoolean = true;
                    categorySelectionView.showHint();
                }
            }
        });

        commentSelectionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aBoolean) {
                    aBoolean = false;
                    commentSelectionView.setData("He is a great guy! Best Android Developer");
                    commentSelectionView.showData();
                } else {
                    aBoolean = true;
                    commentSelectionView.showHint();
                }
            }
        });

    }

}

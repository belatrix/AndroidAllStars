package com.belatrixsf.allstars.ui.recommendation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.entities.SubCategory;
import com.belatrixsf.allstars.ui.comment.CommentActivity;
import com.belatrixsf.allstars.ui.common.AllStarsFragment;
import com.belatrixsf.allstars.ui.common.views.AccountSelectionView;
import com.belatrixsf.allstars.ui.common.views.DataSelectionView;
import com.belatrixsf.allstars.ui.contacts.ContactActivity;
import com.belatrixsf.allstars.utils.AllStarsApplication;
import com.belatrixsf.allstars.utils.di.modules.presenters.RecommendationPresenterModule;

import butterknife.Bind;

/**
 * Created by PedroCarrillo on 4/22/16.
 */
public class RecommendationFragment extends AllStarsFragment implements RecommendationView {

    public static final String SELECTED_USER_KEY = "_selected_user";
    public static final String COMMENT_KEY = "_user_comment";
    public static final String SUBCATEGORY_KEY = "_user_comment";
    public static final int RQ_CONTACT = 100;
    public static final int RQ_COMMENT = 101;
    public static final int RQ_SUBCATEGORY = 102;

    private RecommendationPresenter recommendationPresenter;

    @Bind(R.id.account_selection) AccountSelectionView accountSelectionView;
    @Bind(R.id.category_selection) DataSelectionView categorySelectionView;
    @Bind(R.id.comment_selection) DataSelectionView commentSelectionView;

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
        recommendationPresenter = allStarsApplication.getApplicationComponent()
                .recommendationComponent(new RecommendationPresenterModule(this))
                .recommendationPresenter();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.recommend_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.action_done);
        menuItem.setEnabled(recommendationPresenter.checkRecommendationEnabled());
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                recommendationPresenter.makeRecommendation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        accountSelectionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recommendationPresenter.userSelectionClicked();
            }
        });

        categorySelectionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recommendationPresenter.categorySelectionClicked();
            }
        });

        commentSelectionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recommendationPresenter.commentSelectionClicked();
            }
        });

    }

    @Override
    public void goSearchUser() {
        Intent intent = new Intent(getActivity(), ContactActivity.class);
        startActivityForResult(intent, RQ_CONTACT);
    }

    @Override
    public void showUser() {
        accountSelectionView.showData();
    }

    @Override
    public void showUserFullName(String fullName) {
        accountSelectionView.setFullName(fullName);
    }

    @Override
    public void showUserProfileImage(String image) {
        accountSelectionView.setProfileImage(image);
    }

    @Override
    public void showUserLevel(String level) {
        accountSelectionView.setLevel(level);
    }

    @Override
    public void goWriteComment() {
        Intent intent = new Intent(getActivity(), CommentActivity.class);
        startActivityForResult(intent, RQ_COMMENT);
    }

    @Override
    public void showComment(String comment) {
        commentSelectionView.setData(comment);
        commentSelectionView.showData();
    }

    @Override
    public void goSelectSubcategory() {
        //TODO: init selection of subcategories
    }

    @Override
    public void showCategory(String category) {
        categorySelectionView.setData(category);
    }

    @Override
    public void enableContinueButton() {
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RQ_CONTACT) {
                recommendationPresenter.loadSelectedUser((Employee) data.getParcelableExtra(SELECTED_USER_KEY));
            } else if (requestCode == RQ_COMMENT) {
                recommendationPresenter.loadSelectedComment(data.getStringExtra(COMMENT_KEY));
            } else if (requestCode == RQ_SUBCATEGORY) {
                recommendationPresenter.loadSelectedSubCategory((SubCategory)data.getParcelableExtra(SUBCATEGORY_KEY));
            }
        }
    }

    @Override
    public void finishRecommendation() {
        Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_LONG).show();
        fragmentListener.closeActivity();
    }
}

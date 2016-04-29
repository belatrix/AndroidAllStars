package com.belatrixsf.allstars.ui.givestar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Category;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.ui.category.CategoriesActivity;
import com.belatrixsf.allstars.ui.comment.CommentActivity;
import com.belatrixsf.allstars.ui.common.AllStarsFragment;
import com.belatrixsf.allstars.ui.common.views.AccountSelectionView;
import com.belatrixsf.allstars.ui.common.views.DataSelectionView;
import com.belatrixsf.allstars.ui.contacts.ContactActivity;
import com.belatrixsf.allstars.ui.home.MainActivity;
import com.belatrixsf.allstars.utils.AllStarsApplication;
import com.belatrixsf.allstars.utils.di.modules.presenters.GiveStarPresenterModule;

import butterknife.Bind;

/**
 * Created by PedroCarrillo on 4/22/16.
 */
public class GiveStarFragment extends AllStarsFragment implements GiveStarView {

    public static final String SELECTED_USER_KEY = "_selected_user";
    public static final String COMMENT_KEY = "_user_comment";
    public static final int RQ_CONTACT = 100;
    public static final int RQ_COMMENT = 101;
    public static final int RQ_SUBCATEGORY = 102;

    private GiveStarPresenter giveStarPresenter;

    @Bind(R.id.account_selection) AccountSelectionView accountSelectionView;
    @Bind(R.id.category_selection) DataSelectionView categorySelectionView;
    @Bind(R.id.comment_selection) DataSelectionView commentSelectionView;

    public static GiveStarFragment newInstance(Employee employee) {
        Bundle bundle = new Bundle();
        if (employee != null) {
            bundle.putParcelable(SELECTED_USER_KEY, employee);
        }
        GiveStarFragment giveStarFragment = new GiveStarFragment();
        giveStarFragment.setArguments(bundle);
        return giveStarFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_give_star, container, false);
    }

    @Override
    protected void initDependencies(AllStarsApplication allStarsApplication) {
        giveStarPresenter = allStarsApplication.getApplicationComponent().giveStarComponent(new GiveStarPresenterModule(this)).giveStarPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(SELECTED_USER_KEY)) {
            Employee employee = getArguments().getParcelable(SELECTED_USER_KEY);
            giveStarPresenter.initWithUser(employee);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_recommend, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.action_done);
        menuItem.setEnabled(giveStarPresenter.checkRecommendationEnabled());
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                giveStarPresenter.makeRecommendation();
                return true;
            default:
                fragmentListener.setActivityResult(Activity.RESULT_OK);
                fragmentListener.closeActivity();
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        accountSelectionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                giveStarPresenter.userSelectionClicked();
            }
        });

        categorySelectionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                giveStarPresenter.categorySelectionClicked();
            }
        });

        commentSelectionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                giveStarPresenter.commentSelectionClicked();
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
    public void goWriteComment(String comment) {
        Intent intent = new Intent(getActivity(), CommentActivity.class);
        intent.putExtra(COMMENT_KEY, comment);
        startActivityForResult(intent, RQ_COMMENT);
    }

    @Override
    public void showComment(String comment) {
        commentSelectionView.setData(comment);
        commentSelectionView.showData();
    }

    @Override
    public void goSelectSubcategory() {
        Intent intent = new Intent(getActivity(), CategoriesActivity.class);
        startActivityForResult(intent, RQ_SUBCATEGORY);
    }

    @Override
    public void showCategory(String category) {
        categorySelectionView.setData(category);
        categorySelectionView.showData();
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
                giveStarPresenter.loadSelectedUser((Employee) data.getParcelableExtra(SELECTED_USER_KEY));
            } else if (requestCode == RQ_COMMENT) {
                giveStarPresenter.loadSelectedComment(data.getStringExtra(COMMENT_KEY));
            } else if (requestCode == RQ_SUBCATEGORY) {
                giveStarPresenter.loadSelectedSubCategory((Category) data.getParcelableExtra(CategoriesActivity.SUBCATEGORY_KEY));
            }
        }
    }

    @Override
    public void finishRecommendation() {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.MESSAGE_KEY, getString(R.string.success_recommendation));
        fragmentListener.setActivityResult(Activity.RESULT_OK, intent);
        fragmentListener.closeActivity();
    }

    @Override
    public void blockWithUserSelected() {
        accountSelectionView.setArrowVisibility(View.GONE);
        accountSelectionView.setOnClickListener(null);
    }
}

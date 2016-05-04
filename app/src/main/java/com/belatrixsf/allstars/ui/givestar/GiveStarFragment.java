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
import com.belatrixsf.allstars.utils.AllStarsApplication;
import com.belatrixsf.allstars.utils.di.modules.presenters.GiveStarPresenterModule;

import butterknife.Bind;

/**
 * Created by PedroCarrillo on 4/22/16.
 */
public class GiveStarFragment extends AllStarsFragment implements GiveStarView {

    public static final String ARG_SELECTED_USER_KEY = "_selected_user";
    public static final String EXTRA_COMMENT_KEY = "_user_comment_key";
    public static final String EXTRA_MESSAGE_KEY = "_message_key";
    public static final String STATE_COMMENT_KEY = "_comment_message_key";
    public static final String STATE_TO_EMPLOYEE_KEY = "_comment_to_employee_key";
    public static final String STATE_CATEGORY_KEY = "_comment_category_key";
    public static final int RQ_CONTACT = 100;
    public static final int RQ_COMMENT = 101;
    public static final int RQ_SUBCATEGORY = 102;

    private GiveStarPresenter giveStarPresenter;
    private MenuItem menuDone;

    @Bind(R.id.account_selection) AccountSelectionView accountSelectionView;
    @Bind(R.id.category_selection) DataSelectionView categorySelectionView;
    @Bind(R.id.comment_selection) DataSelectionView commentSelectionView;

    public static GiveStarFragment newInstance(Employee employee) {
        Bundle bundle = new Bundle();
        if (employee != null) {
            bundle.putParcelable(ARG_SELECTED_USER_KEY, employee);
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
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        }else if (getArguments() != null && getArguments().containsKey(ARG_SELECTED_USER_KEY)) {
            Employee employee = getArguments().getParcelable(ARG_SELECTED_USER_KEY);
            giveStarPresenter.initWithUser(employee);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        saveState(outState);
        super.onSaveInstanceState(outState);
    }

    private void restoreState(Bundle savedInstanceState) {
        Employee savedEmployee = savedInstanceState.getParcelable(STATE_TO_EMPLOYEE_KEY);
        String savedComment = savedInstanceState.getString(STATE_COMMENT_KEY);
        Category savedCategory = savedInstanceState.getParcelable(STATE_CATEGORY_KEY);
        giveStarPresenter.loadSelectedUser(savedEmployee);
        giveStarPresenter.loadSelectedComment(savedComment);
        giveStarPresenter.loadSelectedSubCategory(savedCategory);
    }

    private void saveState(Bundle outState) {
        Employee selectedEmployee = giveStarPresenter.getSelectedEmployee();
        String selectedComment = giveStarPresenter.getSelectedComment();
        Category selectedSubCategory = giveStarPresenter.getSelectedSubCategory();
        if (selectedEmployee != null) {
            outState.putParcelable(STATE_TO_EMPLOYEE_KEY, selectedEmployee);
        }
        if (selectedComment != null && !selectedComment.isEmpty()) {
            outState.putString(STATE_COMMENT_KEY, selectedComment);
        }
        if (selectedSubCategory != null) {
            outState.putParcelable(STATE_CATEGORY_KEY, selectedSubCategory);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_recommend, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menuDone = menu.findItem(R.id.action_done);
        giveStarPresenter.checkRecommendationEnabled();
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                giveStarPresenter.makeRecommendation();
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
        intent.putExtra(EXTRA_COMMENT_KEY, comment);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RQ_CONTACT) {
                giveStarPresenter.loadSelectedUser((Employee) data.getParcelableExtra(ARG_SELECTED_USER_KEY));
            } else if (requestCode == RQ_COMMENT) {
                giveStarPresenter.loadSelectedComment(data.getStringExtra(EXTRA_COMMENT_KEY));
            } else if (requestCode == RQ_SUBCATEGORY) {
                giveStarPresenter.loadSelectedSubCategory((Category) data.getParcelableExtra(CategoriesActivity.SUBCATEGORY_KEY));
            }
        }
    }

    @Override
    public void finishRecommendation() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_MESSAGE_KEY, getString(R.string.success_recommendation));
        fragmentListener.setActivityResult(Activity.RESULT_OK, intent);
        fragmentListener.closeActivity();
    }

    @Override
    public void blockWithUserSelected() {
        accountSelectionView.setArrowVisibility(View.GONE);
        accountSelectionView.setClickable(false);
    }

    @Override
    public void showDoneMenu(boolean show) {
        if (menuDone != null) {
            menuDone.setEnabled(show);
        }
    }

}

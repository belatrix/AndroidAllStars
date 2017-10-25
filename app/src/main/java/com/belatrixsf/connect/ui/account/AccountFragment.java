/* The MIT License (MIT)
* Copyright (c) 2016 BELATRIX
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:

* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.

* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/
package com.belatrixsf.connect.ui.account;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.belatrixsf.connect.R;
import com.belatrixsf.connect.entities.Employee;
import com.belatrixsf.connect.ui.account.badges.AccountBadgesFragment;
import com.belatrixsf.connect.ui.account.edit.EditAccountActivity;
import com.belatrixsf.connect.ui.account.edit.EditAccountFragment;
import com.belatrixsf.connect.ui.account.expanded.ExpandPictureActivity;
import com.belatrixsf.connect.ui.account.recommendations.AccountRecommendationsFragment;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.ui.login.LoginActivity;
import com.belatrixsf.connect.ui.skills.SkillsListActivity;
import com.belatrixsf.connect.ui.stars.GiveStarActivity;
import com.belatrixsf.connect.ui.stars.GiveStarFragment;
import com.belatrixsf.connect.ui.stars.StarsListActivity;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.DialogUtils;
import com.belatrixsf.connect.utils.MediaUtils;
import com.belatrixsf.connect.utils.di.modules.presenters.AccountPresenterModule;
import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.belatrixsf.connect.ui.common.BelatrixConnectActivity.supportSharedElements;

/**
 * Created by pedrocarrillo on 4/9/16.
 */
public class AccountFragment extends BelatrixConnectFragment implements AccountView {

    public static final String EMPLOYEE_KEY = "_employee_key";

    public static final int RQ_GIVE_STAR = 99;

    private AccountPresenter accountPresenter;
    private AccountFragmentListener accountFragmentListener;

    private MenuItem recommendMenuItem;
    private MenuItem editProfileMenuItem;
    private MenuItem editSkillsMenuItem;

    @Bind(R.id.skype_id) TextView skypeIdTextView;
    @Bind(R.id.level) TextView levelTextView;
    @Bind(R.id.score) TextView scoreTextView;
    @Bind(R.id.profile_email) TextView emailTextView;
    @Bind(R.id.profile_picture) ImageView pictureImageView;
    @Bind(R.id.location_name) TextView profileLocationImageView;
    @Bind(R.id.account_swipe_refresh) SwipeRefreshLayout accountSwipeRefresh;
    @Bind(R.id.bottom_navigation) AHBottomNavigation bottomNavigation;
    @BindString(R.string.bottom_navigation_color) String navigationColor;

    public static final int TAB_RECOMMENDATIONS = 0;
    public static final int TAB_BADGES = 1;

    public static AccountFragment newInstance(Integer userId, byte[] imgBitmap) {
        Bundle bundle = new Bundle();
        if (userId != null) {
            bundle.putInt(AccountActivity.USER_ID_KEY, userId);
        }
        if(imgBitmap != null){
            bundle.putByteArray(AccountActivity.USER_IMG_PROFILE_KEY, imgBitmap);
        }
        AccountFragment accountFragment = new AccountFragment();
        accountFragment.setArguments(bundle);
        return accountFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        castOrThrowException(activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        castOrThrowException(context);
    }

    private void castOrThrowException(Context context) {
        try {
            accountFragmentListener = (AccountFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement AccountFragmentListener");
        }
    }

    private void getUserInputData() {
        Bundle arguments = getArguments();
        Integer userId = (arguments != null && arguments.containsKey(AccountActivity.USER_ID_KEY)) ?
                                               arguments.getInt(AccountActivity.USER_ID_KEY) : null;
        byte [] userImg = (arguments != null && arguments.containsKey(AccountActivity.USER_IMG_PROFILE_KEY)) ?
                                                arguments.getByteArray(AccountActivity.USER_IMG_PROFILE_KEY) : null;
        accountPresenter.setUserInfo(userId, MediaUtils.get().getBitmapFromByteArray(userImg));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getUserInputData();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            //restoreState(savedInstanceState);
        } else {
            setupViews();
            accountPresenter.loadUserData();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_account, menu);
        recommendMenuItem = menu.findItem(R.id.action_recommend);
        editProfileMenuItem = menu.findItem(R.id.action_edit_profile);
        editSkillsMenuItem = menu.findItem(R.id.action_edit_skills);
        accountPresenter.checkForMenuItemsEnabled();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_recommend:
                accountPresenter.startRecommendation();
                return true;
            case R.id.action_edit_profile:
                accountPresenter.startEditProfile();
                return true;
            case R.id.action_edit_skills:
                accountPresenter.startEditSkills();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onEmployeeLoaded(final int employeeId) {
        bottomNavigation.setCurrentItem(0);
        final int idFragmentContainer = R.id.fragment_profile_container;
        replaceChildFragment(AccountRecommendationsFragment.newInstance(employeeId), idFragmentContainer);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (!wasSelected) {
                    switch (position) {
                        case TAB_RECOMMENDATIONS:
                            replaceChildFragment(AccountRecommendationsFragment.newInstance(employeeId), idFragmentContainer);
                            break;
                        case TAB_BADGES:
                            replaceChildFragment(AccountBadgesFragment.newInstance(employeeId), idFragmentContainer);
                            break;
                    }
                }
                return true;
            }
        });
    }

    private void setupViews() {
        accountSwipeRefresh.setColorSchemeResources(R.color.swipe_refresh);
        accountSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                accountPresenter.loadUserData();
            }
        });
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_gratitudes, R.drawable.ic_recommendations, R.color.colorAccent);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_achievements, R.drawable.ic_badges, R.color.colorAccent);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setAccentColor(Color.parseColor(navigationColor));
        bottomNavigation.setBehaviorTranslationEnabled(false);

    }

    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        accountPresenter = belatrixConnectApplication.getApplicationComponent()
                .accountComponent(new AccountPresenterModule(this))
                .accountPresenter();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //saveState(outState);
        super.onSaveInstanceState(outState);
    }

    private void restoreState(Bundle savedInstanceState) {
        Employee employee = savedInstanceState.getParcelable(EMPLOYEE_KEY);
        byte [] employeeImg = savedInstanceState.getByteArray(AccountActivity.USER_IMG_PROFILE_KEY);
        accountPresenter.loadPresenterState(employee, employeeImg);
    }

    private void saveState(Bundle outState) {
        outState.putParcelable(EMPLOYEE_KEY, accountPresenter.getEmployee());
        outState.putByteArray(AccountActivity.USER_IMG_PROFILE_KEY,
                MediaUtils.get().getByteArrayFromBitmap((Bitmap) accountPresenter.getEmployeeImg()));
    }

    @Override
    public void goSubCategoryDetail(Integer categoryId, Integer employeeId) {
        Intent intent = new Intent(getActivity(), StarsListActivity.class);
        intent.putExtra(StarsListActivity.USER_ID, employeeId);
        intent.putExtra(StarsListActivity.SUBCATEGORY_ID, categoryId);
        startActivity(intent);
    }

    @Override
    public void showScore(String score) {
        if (scoreTextView != null) {
            scoreTextView.setText(String.valueOf(score));
        }
    }


    @Override
    public void showLevel(String level) {
        if (levelTextView != null) {
            levelTextView.setText(String.valueOf(level));
        }
    }

    @Override
    public void showEmployeeName(String employeName) {
        fragmentListener.setTitle(employeName);
    }

    @Override
    public void showEmail(String role) {
        if (emailTextView != null) {
            emailTextView.setText(role);
        }
    }

    @Override
    public void showSkypeId(String skypeID) {
        if (skypeIdTextView != null) {
            skypeIdTextView.setText(skypeID);
        }
    }

    @Override
    public void showProfilePicture(Object profilePicture) {
        if (pictureImageView != null) {
            if (profilePicture instanceof String) {
                Glide.with(getActivity()).load((String) profilePicture).into(pictureImageView);
            } else if (profilePicture instanceof Bitmap) {
                pictureImageView.setImageBitmap((Bitmap) profilePicture);
            }
            /*if (accountPresenter.getEmployeeImg() == null) {
                ImageFactory.getLoader().loadFromUrl(
                        profilePicture,
                        pictureImageView,
                        ImageLoader.ImageTransformation.BORDERED_CIRCLE,
                        new ImageLoader.Callback() {
                            @Override
                            public void onSuccess() {
                                startPostponedEnterTransition();
                            }

                            @Override
                            public void onFailure() {
                                startPostponedEnterTransition();
                            }
                        },
                        getResources().getDrawable(R.drawable.contact_placeholder),
                        ImageLoader.ScaleType.CENTER_CROP
                );
            } else {
                ImageFactory.getLoader().loadFromBitmap(
                        accountPresenter.getEmployeeImg(),
                        pictureImageView,
                        ImageLoader.ImageTransformation.BORDERED_CIRCLE,
                        new ImageLoader.Callback() {
                            @Override
                            public void onSuccess() {
                                startPostponedEnterTransition();
                            }

                            @Override
                            public void onFailure() {
                                startPostponedEnterTransition();
                            }
                        },
                        getResources().getDrawable(R.drawable.contact_placeholder),
                        ImageLoader.ScaleType.CENTER_CROP
                );
            }*/
        }
    }

    @Override
    public void resetProfilePicture() {
        if (pictureImageView != null) {
            pictureImageView.setImageDrawable(getResources().getDrawable(R.drawable.placeholder_user));
        }
    }

    @Override
    public void showLocation(String location) {
        profileLocationImageView.setText(location);
    }

    public void startPostponedEnterTransition() {
        if (pictureImageView == null) {
            return;
        }
        pictureImageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                pictureImageView.getViewTreeObserver().removeOnPreDrawListener(this);
                ActivityCompat.startPostponedEnterTransition(getActivity());
                return false;
            }
        });
    }

    @Override
    public void showRecommendMenu(boolean show) {
        recommendMenuItem.setVisible(show);
    }

    @Override
    public void showEditProfileButton(boolean show) {
        if (editProfileMenuItem != null) {
            editProfileMenuItem.setVisible(show);
        }
    }

    @Override
    public void showEditSkillsButton(boolean show) {
        if (editSkillsMenuItem != null) {
            editSkillsMenuItem.setVisible(show);
        }
    }

    @Override
    public void goToEditProfile(Employee employee) {
        Intent intent = new Intent(getActivity(), EditAccountActivity.class);
        intent.putExtra(EditAccountFragment.IS_NEW_USER, false);
        if (supportSharedElements()) {
            String transitionName = getActivity().getString(R.string.transition_photo);
            startAnimatedActivity(intent, transitionName, pictureImageView, EditAccountFragment.RQ_EDIT_ACCOUNT);
        } else {
            startActivityForResult(intent, EditAccountFragment.RQ_EDIT_ACCOUNT);
        }
    }

    @OnClick(R.id.profile_picture)
    public void profilePictureClicked() {
        Drawable drawable = pictureImageView.getDrawable();
        Intent intent = ExpandPictureActivity.makeIntent(getActivity());
        intent.putExtra(ExpandPictureActivity.USER_AVATAR_KEY, MediaUtils.compressDrawable(drawable));
        if (supportSharedElements()) {
            String transitionName = getActivity().getString(R.string.transition_photo);
            startAnimatedActivity(intent, transitionName, pictureImageView);
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void goToEditSkills() {
        startActivity(SkillsListActivity.makeIntent(getActivity()));
    }

    @Override
    public void goToGiveStar(Employee employee) {
        Intent intent = new Intent(getActivity(), GiveStarActivity.class);
        intent.putExtra(GiveStarFragment.SELECTED_USER_KEY, employee);
        startActivityForResult(intent, RQ_GIVE_STAR);
    }

    Handler handler =  new Handler();

    @Override
    public void onActivityResult(final int requestCode, final int resultCode,final  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (requestCode == RQ_GIVE_STAR && resultCode == Activity.RESULT_OK && data != null) {
                    fragmentListener.showSnackBar(data.getStringExtra(GiveStarFragment.MESSAGE_KEY));
                    accountPresenter.loadUserData();
                } else if (requestCode == EditAccountFragment.RQ_EDIT_ACCOUNT && resultCode == RESULT_OK) {
                    accountPresenter.refreshEmployee();
                    accountPresenter.getUserDataFromServer();
                }
            }
        });

    }

    @Override
    public Drawable getProfilePicture() {
        return pictureImageView != null ? pictureImageView.getDrawable() : null;
    }

    @Override
    public void showProgressIndicator() {
        accountSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgressIndicator() {
        if(accountSwipeRefresh != null)
            accountSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void notifyNavigationRefresh() {
        accountFragmentListener.refreshNavigationDrawer();
    }

    @Override
    public void showInformativeDialog(String information) {
        DialogUtils.createInformationDialog(getActivity(), information, getString(R.string.app_name), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                accountPresenter.confirmEndSession();
            }
        }).show();
    }

    @Override
    public void goBackToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        fragmentListener.closeActivity();
    }


    @Override
    public void onDestroyView() {
        accountPresenter.cancelRequests();
        super.onDestroyView();
    }

}
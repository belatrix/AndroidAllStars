package com.belatrixsf.allstars.ui.account.edit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.ui.common.AllStarsFragment;
import com.belatrixsf.allstars.utils.AllStarsApplication;
import com.belatrixsf.allstars.utils.di.modules.presenters.EditAccountPresenterModule;
import com.belatrixsf.allstars.utils.media.ImageFactory;
import com.belatrixsf.allstars.utils.media.loaders.ImageLoader;

import butterknife.Bind;

import static com.belatrixsf.allstars.ui.account.edit.EditAccountActivity.EMPLOYEE_KEY;

/**
 * Created by PedroCarrillo on 5/17/16.
 */
public class EditAccountFragment extends AllStarsFragment implements EditAccountView {

    public static final int RQ_EDIT_ACCOUNT = 22;

    @Bind(R.id.profile_picture) ImageView pictureImageView;
    @Bind(R.id.firstName) EditText firstNameEditText;
    @Bind(R.id.lastName) EditText lastNameEditText;
    @Bind(R.id.skypeId) EditText skypeIdEditText;

    private EditAccountPresenter editAccountPresenter;

    public static EditAccountFragment newInstance(Employee employee) {
        Bundle bundle = new Bundle();
        if (employee != null) {
            bundle.putParcelable(EMPLOYEE_KEY, employee);
        }
        EditAccountFragment editAccountFragment = new EditAccountFragment();
        editAccountFragment.setArguments(bundle);
        return editAccountFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(EMPLOYEE_KEY)) {
            Employee employee = getArguments().getParcelable(EMPLOYEE_KEY);
            editAccountPresenter.init(employee);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_done, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                editAccountPresenter.finishEdit(firstNameEditText.getText().toString(), lastNameEditText.getText().toString(), skypeIdEditText.getText().toString() );
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startPostponedEnterTransition() {
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
    protected void initDependencies(AllStarsApplication allStarsApplication) {
        editAccountPresenter = allStarsApplication.getApplicationComponent().editAccountComponent(new EditAccountPresenterModule(this)).editAccountPresenter();
    }

    @Override
    public void showProfileImage(String imageUrl) {
        ImageFactory.getLoader().loadFromUrl(
                imageUrl,
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
                }
        );
    }

    @Override
    public void showFirstName(String firstName) {
        firstNameEditText.setText(firstName);
    }

    @Override
    public void showLastName(String lastName) {
        lastNameEditText.setText(lastName);
    }

    @Override
    public void showSkypeId(String skypeId) {
        skypeIdEditText.setText(skypeId);
    }

    @Override
    public void showFirstNameError(String error) {
        firstNameEditText.setError(error);
    }

    @Override
    public void showLastNameError(String error) {
        lastNameEditText.setError(error);
    }

    @Override
    public void showSkypeIdError(String error) {
        skypeIdEditText.setError(error);
    }

    @Override
    public void endSuccessfulEdit() {
        Intent intent = new Intent();
        fragmentListener.setActivityResult(Activity.RESULT_OK, intent);
        fragmentListener.closeActivity();
    }

}

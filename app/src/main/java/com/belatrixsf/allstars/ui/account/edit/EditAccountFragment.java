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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.entities.Location;
import com.belatrixsf.allstars.ui.common.AllStarsFragment;
import com.belatrixsf.allstars.ui.home.MainActivity;
import com.belatrixsf.allstars.utils.AllStarsApplication;
import com.belatrixsf.allstars.utils.di.modules.presenters.EditAccountPresenterModule;
import com.belatrixsf.allstars.utils.media.ImageFactory;
import com.belatrixsf.allstars.utils.media.loaders.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.belatrixsf.allstars.ui.account.edit.EditAccountActivity.EMPLOYEE_KEY;

/**
 * Created by PedroCarrillo on 5/17/16.
 */
public class EditAccountFragment extends AllStarsFragment implements EditAccountView {

    public static final int RQ_EDIT_ACCOUNT = 22;
    public static final String LOCATION_KEY = "_location_key";
    public static final String LOCATIONS_KEY = "_locations_key";
    public static final String IS_CREATION = "_is_creation_key";

    @Bind(R.id.profile_picture) ImageView pictureImageView;
    @Bind(R.id.firstName) EditText firstNameEditText;
    @Bind(R.id.lastName) EditText lastNameEditText;
    @Bind(R.id.skypeId) EditText skypeIdEditText;
    @Bind(R.id.locationRadioGroup) RadioGroup locationRadioGroup;

    private EditAccountPresenter editAccountPresenter;

    public static EditAccountFragment newInstance(Employee employee, boolean isCreation) {
        Bundle bundle = new Bundle();
        if (employee != null) {
            bundle.putParcelable(EMPLOYEE_KEY, employee);
            bundle.putBoolean(IS_CREATION, isCreation);
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
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        } else if (getArguments() != null && getArguments().containsKey(EMPLOYEE_KEY)) {
            Employee employee = getArguments().getParcelable(EMPLOYEE_KEY);
            boolean isCreation = getArguments().getBoolean(IS_CREATION);
            editAccountPresenter.init(employee, isCreation);
            initViews();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        saveState(outState);
        super.onSaveInstanceState(outState);
    }

    private void restoreState(Bundle savedInstanceState) {
        Employee employee = savedInstanceState.getParcelable(EMPLOYEE_KEY);
        Location locationSelected = savedInstanceState.getParcelable(LOCATION_KEY);
        boolean isCreation = savedInstanceState.getBoolean(IS_CREATION);
        List<Location> locations = savedInstanceState.getParcelableArrayList(LOCATIONS_KEY);
        editAccountPresenter.loadData(employee, locationSelected, locations, isCreation);
    }

    private void saveState(Bundle outState) {
        Employee employee = editAccountPresenter.getEmployee();
        Location locationSelected = editAccountPresenter.getLocationSelected();
        List<Location> locations = editAccountPresenter.getLocationList();
        outState.putBoolean(IS_CREATION, editAccountPresenter.isCreation());
        outState.putParcelable(EMPLOYEE_KEY, employee);
        outState.putParcelable(LOCATION_KEY, locationSelected);
        outState.putParcelableArrayList(LOCATIONS_KEY, new ArrayList<>(locations));
    }

    private void initViews() {
        locationRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                int position = locationRadioGroup.indexOfChild(radioButton);
                editAccountPresenter.selectLocation(position);
            }
        });
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

    @Override
    public void addLocation(String location) {
        RadioButton radioButton = new RadioButton(getActivity());
        radioButton.setText(location);
        locationRadioGroup.addView(radioButton);
    }

    @Override
    public void showLocation(int position) {
        RadioButton radioButton = (RadioButton) locationRadioGroup.getChildAt(position);
        radioButton.setChecked(true);
    }

    @Override
    public void endSuccessfulCreation() {
        startActivity(MainActivity.makeIntent(getActivity()));
        fragmentListener.closeActivity();
    }

}

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
package com.belatrixsf.connect.ui.account.edit;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.entities.Employee;
import com.belatrixsf.connect.entities.Location;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.ui.home.UserActivity;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.DialogUtils;
import com.belatrixsf.connect.utils.KeyboardUtils;
import com.belatrixsf.connect.utils.MediaUtils;
import com.belatrixsf.connect.utils.PermissionHelper;
import com.belatrixsf.connect.utils.di.modules.presenters.EditAccountPresenterModule;
import com.belatrixsf.connect.utils.media.ImageFactory;
import com.belatrixsf.connect.utils.media.loaders.ImageLoader;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static com.belatrixsf.connect.ui.account.edit.EditAccountActivity.EMPLOYEE_KEY;

/**
 * Created by PedroCarrillo on 5/17/16.
 */
public class EditAccountFragment extends BelatrixConnectFragment implements EditAccountView {

    public static final int RQ_EDIT_ACCOUNT = 22;
    public static final int RQ_CAMERA = 23;
    public static final int RQ_GALLERY = 24;
    public static final int RQ_PERMISSIONS_REQUEST = 25;
    public static final String LOCATION_KEY = "_location_key";
    public static final String LOCATIONS_KEY = "_locations_key";
    public static final String IS_NEW_USER = "_is_creation_key";
    public static final String FILE_KEY = "_selected_file_key";

    @Bind(R.id.profile_picture) ImageView pictureImageView;
    @Bind(R.id.firstName) EditText firstNameEditText;
    @Bind(R.id.lastName) EditText lastNameEditText;
    @Bind(R.id.skypeId) EditText skypeIdEditText;
    @Bind(R.id.locationRadioGroup) RadioGroup locationRadioGroup;
    @Bind(R.id.edit_image) ImageView editPictureImageView;

    private EditAccountPresenter editAccountPresenter;
    private String mProfilePicturePath;

    public static EditAccountFragment newInstance(boolean isNewUser) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(IS_NEW_USER, isNewUser);
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
        return inflater.inflate(R.layout.fragment_edit_account, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        } else if (getArguments() != null && getArguments().containsKey(IS_NEW_USER)) {
            boolean isNewUser = getArguments().getBoolean(IS_NEW_USER);
            editAccountPresenter.init(isNewUser);
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
        boolean isCreation = savedInstanceState.getBoolean(IS_NEW_USER);
        List<Location> locations = savedInstanceState.getParcelableArrayList(LOCATIONS_KEY);
        File file = (File) savedInstanceState.getSerializable(FILE_KEY);
        editAccountPresenter.loadData(employee, locationSelected, locations, isCreation, file);
    }

    private void saveState(Bundle outState) {
        Employee employee = editAccountPresenter.getEmployee();
        Location locationSelected = editAccountPresenter.getLocationSelected();
        List<Location> locations = editAccountPresenter.getLocationList();
        outState.putBoolean(IS_NEW_USER, editAccountPresenter.isNewUser());
        outState.putParcelable(EMPLOYEE_KEY, employee);
        outState.putParcelable(LOCATION_KEY, locationSelected);
        outState.putParcelableArrayList(LOCATIONS_KEY, new ArrayList<>(locations));
        outState.putSerializable(FILE_KEY, editAccountPresenter.getSelectedFile());
    }

    private void initViews() {
        skypeIdEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    KeyboardUtils.hideKeyboard((Activity) getContext(), v);
                }
                return false;
            }
        });
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
                int checkedRadioId = locationRadioGroup.getCheckedRadioButtonId();
                View radioButton = locationRadioGroup.findViewById(checkedRadioId);
                int locationIndex = locationRadioGroup.indexOfChild(radioButton);
                editAccountPresenter.finishEdit(firstNameEditText.getText().toString(), lastNameEditText.getText().toString(), skypeIdEditText.getText().toString(), locationIndex);
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
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        editAccountPresenter = belatrixConnectApplication.getApplicationComponent().editAccountComponent(new EditAccountPresenterModule(this)).editAccountPresenter();
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
                },
                pictureImageView.getResources().getDrawable(R.drawable.contact_placeholder)
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
    public void showLocationError(String error) {
        DialogUtils.createErrorDialog(getActivity(), error).show();
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

    @OnClick(R.id.edit_image)
    public void onEditPictureClicked() {
        editAccountPresenter.onEditImageClicked();
    }

    @Override
    public void showEditProfileImagePicker() {
        checkPermissions();
    }

    @Override
    public void showGalleryPicker() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(intent, getActivity().getString(R.string.select_image_title)), RQ_GALLERY);
    }

    public void startPicker() {
        final List<Object> choiceList = Arrays.asList(new Object[]{getString(R.string.photo_option), getString(R.string.gallery_option)});
        ArrayAdapter<Object> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, choiceList);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.select_photo_title);
        builder.setAdapter(arrayAdapter, new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (choiceList.get(which).toString().equalsIgnoreCase(getString(R.string.photo_option))) {
                    editAccountPresenter.onPhotoPickerSelected();
                } else if (choiceList.get(which).toString().equalsIgnoreCase(getString(R.string.gallery_option))) {
                    editAccountPresenter.onGalleryPickedSelected();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void checkPermissions() {
        String[] mediaPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!PermissionHelper.checkPermissions(getActivity(), mediaPermissions)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                DialogUtils.createInformationDialog(getActivity(), getString(R.string.app_name), getString(R.string.permission_profile), null);
            } else {
                ActivityCompat.requestPermissions(getActivity(), mediaPermissions ,
                        RQ_PERMISSIONS_REQUEST);
            }
        } else {
            startPicker();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RQ_PERMISSIONS_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startPicker();
            } else {
                editAccountPresenter.onPermissionDenied();
            }
        }
    }

    @Override
    public void showPhotoPicker() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = MediaUtils.get().createLocalProfilePicture();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                editAccountPresenter.setSelectedFile(photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                mProfilePicturePath = photoFile.getAbsolutePath();
                startActivityForResult(cameraIntent, RQ_CAMERA);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == RQ_GALLERY) {
            if (data != null) {
                initCropImageActivity(data.getData());
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == RQ_CAMERA) {
            mProfilePicturePath = editAccountPresenter.getSelectedFile().getAbsolutePath();
            Uri selectedPhotoUri = Uri.fromFile(new File(mProfilePicturePath));
            initCropImageActivity(selectedPhotoUri);
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri croppedImageUri = result.getUri();
                mProfilePicturePath = croppedImageUri.getPath();
                editAccountPresenter.uploadImage(MediaUtils.get().getReducedBitmapFile(croppedImageUri));
            }
        }
    }

    public void initCropImageActivity(Uri uri) {
        Intent intent = CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON).setMinCropResultSize(500,500).setCropShape(CropImageView.CropShape.RECTANGLE).getIntent(getActivity());
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void disableEditProfilePicture() {
        editPictureImageView.setVisibility(View.INVISIBLE);
        editPictureImageView.setEnabled(false);
    }

    @Override
    public void endSuccessfulCreation() {
        startActivity(UserActivity.makeIntent(getActivity()));
        fragmentListener.closeActivity();
    }

}

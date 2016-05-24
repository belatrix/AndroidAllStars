package com.belatrixsf.allstars.ui.account.edit;

import android.util.Log;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.entities.Location;
import com.belatrixsf.allstars.services.contracts.EmployeeService;
import com.belatrixsf.allstars.ui.common.AllStarsPresenter;
import com.belatrixsf.allstars.utils.AllStarsCallback;
import com.belatrixsf.allstars.utils.ServiceError;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by PedroCarrillo on 5/17/16.
 */
public class EditAccountPresenter extends AllStarsPresenter<EditAccountView> {

    private Employee employee;
    private List<Location> locationList;
    private Location locationSelected;
    private boolean isCreation;
    protected EmployeeService employeeService;

    @Inject
    protected EditAccountPresenter(EditAccountView view, EmployeeService employeeAPI) {
        super(view);
        this.employeeService = employeeAPI;
    }

    public void init(Employee employee, boolean isCreation) {
        this.isCreation = isCreation;
        showEmployeeData(employee);
        obtainLocations();
    }

    public void loadData(Employee employee, Location locationSelected, List<Location> locations, boolean isCreation) {
        showEmployeeData(employee);
        this.isCreation = isCreation;
        this.locationSelected = locationSelected;
        this.locationList = locations;
        loadLocations();
    }

    public void showEmployeeData(Employee employee) {
        this.employee = employee;
        view.showProfileImage(employee.getAvatar());
        view.showFirstName(employee.getFirstName());
        view.showLastName(employee.getLastName());
        view.showSkypeId(employee.getSkypeId());
        locationSelected = employee.getLocation();
    }

    public void obtainLocations() {
        employeeService.getEmployeeLocations(new AllStarsCallback<List<Location>>() {
            @Override
            public void onSuccess(List<Location> locationList) {
                EditAccountPresenter.this.locationList = locationList;
                loadLocations();
            }

            @Override
            public void onFailure(ServiceError serviceError) {
                showError(serviceError.getDetail());
            }
        });
    }

    private void loadLocations() {
        for (int i = 0; i < locationList.size(); i++) {
            Location location = locationList.get(i);
            view.addLocation(location.getName());
            if (locationSelected != null && locationSelected.getName().equalsIgnoreCase(location.getName())) {
                view.showLocation(i);
            }
        }
    }

    public void finishEdit(String firstName, String lastName, String skypeId) {
        if (checkValidFirstName(firstName) && checkValidLastName(lastName) && checkValidSkypeId(skypeId)) {
            view.showProgressDialog();
            employeeService.updateEmployee(employee.getPk(), firstName, lastName, skypeId, locationSelected.getPk(), new AllStarsCallback<Employee>() {
                @Override
                public void onSuccess(Employee employee) {
                    view.dismissProgressDialog();
                    if (isCreation) {
                        view.endSuccessfulCreation();
                    } else {
                        view.endSuccessfulEdit();
                    }
                }

                @Override
                public void onFailure(ServiceError serviceError) {
                    view.showError(serviceError.getDetail());
                }
            });
        }
    }

    private boolean checkValidFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            view.showFirstNameError(getString(R.string.empty_error));
            return false;
        } else {
            return true;
        }
    }

    private boolean checkValidLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            view.showLastNameError(getString(R.string.empty_error));
            return false;
        } else {
            return true;
        }
    }

    private boolean checkValidSkypeId(String skypeId) {
        if (skypeId == null || skypeId.isEmpty()) {
            view.showSkypeIdError(getString(R.string.empty_error));
            return false;
        } else {
            return true;
        }
    }

    public void onEditImageClicked() {
        view.showEditProfileImagePicker();
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public void selectLocation(int position) {
        locationSelected = locationList.get(position);
    }

    public Employee getEmployee() {
        return employee;
    }

    public Location getLocationSelected() {
        return locationSelected;
    }

    public boolean isCreation() {
        return isCreation;
    }

    @Override
    public void cancelRequests() {
        employeeService.cancelAll();
    }

    public void onGalleryPickedSelected() {
        view.showGalleryPicker();
    }

    public void onPhotoPickerSelected() {
        view.showPhotoPicker();
    }

    public void uploadImage(File file) {
        if (file != null) {
            employeeService.updateEmployeeImage(employee.getPk(), file, new AllStarsCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("onSu", "onSuccess: Asdas");
                }

                @Override
                public void onFailure(ServiceError serviceError) {
                    Log.d("onError", "onSuccess: Asdas");
                }
            });
        }
    }

    public void onPermissionDenied() {
        view.disableEditProfilePicture();
    }
    
}

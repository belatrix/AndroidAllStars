package com.belatrixsf.allstars.ui.account.edit;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.services.EmployeeService;
import com.belatrixsf.allstars.ui.common.AllStarsPresenter;
import com.belatrixsf.allstars.utils.AllStarsCallback;
import com.belatrixsf.allstars.utils.ServiceError;

import javax.inject.Inject;

/**
 * Created by PedroCarrillo on 5/17/16.
 */
public class EditAccountPresenter extends AllStarsPresenter<EditAccountView> {

    private Employee employee;
    protected EmployeeService employeeService;

    @Inject
    protected EditAccountPresenter(EditAccountView view, EmployeeService employeeAPI) {
        super(view);
        this.employeeService = employeeAPI;
    }

    public void init(Employee employee) {
        this.employee = employee;
        view.showProfileImage(employee.getAvatar());
        view.showFirstName(employee.getFirstName());
        view.showLastName(employee.getLastName());
        view.showSkypeId(employee.getSkypeId());
    }

    public void finishEdit(String firstName, String lastName, String skypeId) {
        if (checkValidFirstName(firstName) && checkValidLastName(lastName) && checkValidSkypeId(skypeId)) {
            view.showProgressDialog();
            employeeService.updateEmployee(employee.getPk(), firstName, lastName, skypeId, 1, new AllStarsCallback<Employee>() {
                @Override
                public void onSuccess(Employee employee) {
                    //SUCCESS
                    view.dismissProgressDialog();
                    view.endSuccessfulEdit();
                }

                @Override
                public void onFailure(ServiceError serviceError) {
                    view.showError(serviceError.getErrorMessage());
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

}

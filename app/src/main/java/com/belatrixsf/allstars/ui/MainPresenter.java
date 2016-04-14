package com.belatrixsf.allstars.ui;

import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.managers.EmployeeManager;
import com.belatrixsf.allstars.ui.common.AllStarsPresenter;
import com.belatrixsf.allstars.utils.AllStarsCallback;
import com.belatrixsf.allstars.utils.ServiceError;

import javax.inject.Inject;

/**
 * Created by PedroCarrillo on 4/14/16.
 */
public class MainPresenter extends AllStarsPresenter<MainView> {

    private EmployeeManager employeeManager;
    private Employee employee;

    @Inject
    public MainPresenter(MainView view, EmployeeManager employeeManager) {
        super(view);
        this.employeeManager = employeeManager;
    }

    public void onAccountCreated() {
        employeeManager.getLoggedInEmployee(new AllStarsCallback<Employee>() {
            @Override
            public void onSuccess(Employee employee) {
                MainPresenter.this.employee = employee;
                view.showEmployeeData(employee);
            }

            @Override
            public void onFailure(ServiceError serviceError) {
                showError(serviceError.getErrorMessage());
            }
        });
    }


}

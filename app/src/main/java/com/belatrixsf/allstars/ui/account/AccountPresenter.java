package com.belatrixsf.allstars.ui.account;

import com.belatrixsf.allstars.entities.Category;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.managers.EmployeeManager;
import com.belatrixsf.allstars.ui.common.AllStarsPresenter;
import com.belatrixsf.allstars.utils.AllStarsCallback;
import com.belatrixsf.allstars.utils.ServiceError;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by PedroCarrillo on 4/13/16.
 */
public class AccountPresenter extends AllStarsPresenter<AccountView> {

    private EmployeeManager employeeManager;
    private Employee employee;

    @Inject
    public AccountPresenter(AccountView view, EmployeeManager employeeManager) {
        super(view);
        this.employeeManager = employeeManager;
    }

    public void onAccountCreated() {
        employeeManager.getLoggedInEmployee(new AllStarsCallback<Employee>() {
            @Override
            public void onSuccess(Employee employee) {
                AccountPresenter.this.employee = employee;
                view.loadEmployeeData(employee);
            }

            @Override
            public void onFailure(ServiceError serviceError) {
                showError(serviceError.getErrorMessage());
            }
        });
    }

    public void onCategoryClicked(int position) {
        view.goCategoryDetail(employee.getCategoryList().get(position));
    }

}

package com.belatrixsf.allstars.ui.account;

import com.belatrixsf.allstars.entities.Category;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.ui.common.AllStarsView;

/**
 * Created by PedroCarrillo on 4/13/16.
 */
public interface AccountView extends AllStarsView {

    void goCategoryDetail(Category category);
    void loadEmployeeData(Employee employee);

}

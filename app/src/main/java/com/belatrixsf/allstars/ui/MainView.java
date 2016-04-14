package com.belatrixsf.allstars.ui;

import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.ui.common.AllStarsView;

/**
 * Created by PedroCarrillo on 4/14/16.
 */
public interface MainView extends AllStarsView {

    void showEmployeeData(Employee employee);

}

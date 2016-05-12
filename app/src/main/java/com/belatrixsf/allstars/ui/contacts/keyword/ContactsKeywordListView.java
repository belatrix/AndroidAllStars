package com.belatrixsf.allstars.ui.contacts.keyword;

import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.ui.common.AllStarsView;

import java.util.List;

/**
 * Created by PedroCarrillo on 5/12/16.
 */
public interface ContactsKeywordListView extends AllStarsView {

    void showEmployees(List<Employee> contacts);
    void showCurrentPage(int currentPage);
    void goContactProfile(Integer id);
    void selectContact(Employee contact);

}

package com.belatrixsf.allstars.ui.contacts.keyword;

import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.entities.Keyword;
import com.belatrixsf.allstars.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.StarKeywordTopListResponse;
import com.belatrixsf.allstars.services.StarService;
import com.belatrixsf.allstars.ui.common.AllStarsPresenter;
import com.belatrixsf.allstars.utils.AllStarsCallback;
import com.belatrixsf.allstars.utils.ServiceError;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by PedroCarrillo on 5/12/16.
 */
public class ContactsKeywordListPresenter extends AllStarsPresenter<ContactsKeywordListView> {

    private StarService starService;
    private Keyword keyword;
    private PaginatedResponse starPaginatedResponse = new PaginatedResponse();
    private List<Employee> employeeList = new ArrayList<>();
    private int currentPage = 1;

    @Inject
    protected ContactsKeywordListPresenter(ContactsKeywordListView view, StarService starService) {
        super(view);
        this.starService = starService;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public PaginatedResponse getStarPaginatedResponse() {
        return starPaginatedResponse;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }

    public void init(Keyword keyword) {
        this.keyword = keyword;
        this.currentPage = 1;
        getEmployeesByStarKeywords();
    }

    public void setLoadedEmployeeList(List<Employee> employeeList, int currentPage, PaginatedResponse starPaginatedResponse) {
        if (employeeList != null) {
            this.employeeList = employeeList;
        }
        this.starPaginatedResponse = starPaginatedResponse;
        this.currentPage = currentPage;
        view.showCurrentPage(currentPage);
        view.showEmployees(employeeList);
    }

    public void callNextPage() {
        if (starPaginatedResponse.getNext() != null) {
            getEmployeesByStarKeywords();
        }
    }

    public void getEmployeesByStarKeywords() {
        currentPage = starPaginatedResponse.getNextPage();
        view.showProgressIndicator();
        starService.getStarsKeywordTopList(keyword.getId(), currentPage, new AllStarsCallback<StarKeywordTopListResponse>() {
            @Override
            public void onSuccess(StarKeywordTopListResponse starKeywordTopListResponse) {
                employeeList.addAll(starKeywordTopListResponse.getEmployees());
                starPaginatedResponse.setNext(starKeywordTopListResponse.getNext());
                view.hideProgressIndicator();
                view.showEmployees(employeeList);
            }

            @Override
            public void onFailure(ServiceError serviceError) {
                view.hideProgressIndicator();
                showError(serviceError.getErrorMessage());
            }
        });
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void onContactClicked(Object contact) {
        if (contact != null && contact instanceof Employee) {
            Employee employee = (Employee) contact;
            view.goContactProfile(employee.getPk());
        }
    }

}

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
package com.belatrixsf.allstars.ui.category;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Category;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.entities.SubCategory;
import com.belatrixsf.allstars.managers.EmployeeManager;
import com.belatrixsf.allstars.services.contracts.CategoryService;
import com.belatrixsf.allstars.services.contracts.EmployeeService;
import com.belatrixsf.allstars.ui.common.AllStarsPresenter;
import com.belatrixsf.allstars.utils.AllStarsCallback;
import com.belatrixsf.allstars.utils.ServiceError;

import java.util.List;

/**
 * Created by gyosida on 4/27/16.
 */
public class CategoriesPresenter extends AllStarsPresenter<CategoriesView> {

    private CategoryService categoryService;
    private EmployeeService employeeService;
    private EmployeeManager employeeManager;
    private List<Category> categories;
    private Category category;

    public CategoriesPresenter(CategoriesView view, CategoryService categoryService, Category category) {
        this(view, null, null, categoryService, category);
    }

    public CategoriesPresenter(CategoriesView view, EmployeeManager employeeManager, EmployeeService employeeService) {
        this(view, employeeManager, employeeService, null, null);
    }

    private CategoriesPresenter(CategoriesView view, EmployeeManager employeeManager, EmployeeService employeeService, CategoryService categoryService, Category category) {
        super(view);
        this.categoryService = categoryService;
        this.employeeService = employeeService;
        this.employeeManager = employeeManager;
        this.category = category;
    }

    public void init() {
        view.setTitle(viewPresentsCategories() ? getString(R.string.title_select_category) : getString(R.string.title_select_subcategory));
    }

    public void getCategories() {
        if (categories == null || categories.isEmpty()) {
            if (viewPresentsCategories()) {
                if (employeeManager != null && employeeService != null) {
                    view.showProgressIndicator();
                    employeeManager.getLoggedInEmployee(new PresenterCallback<Employee>() {
                        @Override
                        public void onSuccess(Employee employee) {
                            employeeService.getEmployeeCategories(employee.getPk(), categoriesCallback);
                        }
                    });
                }
            } else {
                if (categoryService != null) {
                    view.showProgressIndicator();
                    categoryService.getSubcategories(category.getId(), categoriesCallback);
                }
            }
        } else {
            showItemsAndNotifyIfAreSubcategories();
        }
    }

    public void categorySelected(int position) {
        if (position >= 0 && position < categories.size()) {
            Category category = categories.get(position);
            if (!viewPresentsCategories()) {
                SubCategory subCategory = (SubCategory) category;
                subCategory.setParentCategory(this.category);
                view.notifySelection(subCategory);
            } else {
                view.showSubcategories(category);
            }
        }
    }

    public List<Category> forSavingCategories() {
        return categories;
    }

    public void loadSavedCategories(List<Category> categories) {
        this.categories = categories;
    }

    private void showItemsAndNotifyIfAreSubcategories() {
        view.showCategories(categories);
        view.notifyAreSubcategories(category != null);
    }

    private boolean viewPresentsCategories() {
        return category == null;
    }

    private AllStarsCallback<List<Category>> categoriesCallback = new PresenterCallback<List<Category>>() {
        @Override
        public void onSuccess(List<Category> categories) {
            CategoriesPresenter.this.categories = categories;
            showItemsAndNotifyIfAreSubcategories();
            view.hideProgressIndicator();
        }
    };

    @Override
    public void cancelRequests() {
        if (categoryService != null) {
            categoryService.cancelAll();
        }
        if (employeeService != null) {
            employeeService.cancelAll();
        }
    }
}

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
package com.belatrixsf.allstars.ui.stars;

import com.belatrixsf.allstars.entities.Keyword;
import com.belatrixsf.allstars.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.allstars.entities.Star;
import com.belatrixsf.allstars.networking.retrofit.responses.StarsResponse;
import com.belatrixsf.allstars.services.StarService;
import com.belatrixsf.allstars.ui.common.AllStarsPresenter;
import com.belatrixsf.allstars.utils.AllStarsCallback;
import com.belatrixsf.allstars.utils.ServiceError;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by icerrate on 25/04/2016.
 */
public class StarsListPresenter extends AllStarsPresenter<StarsListView> {

    private StarService starService;
    private int employeeId;
    private int subCategoryId;
    private PaginatedResponse starPaginatedResponse = new PaginatedResponse();
    private List<Star> stars = new ArrayList<>();

    @Inject
    public StarsListPresenter(StarsListView view, StarService starService) {
        super(view);
        this.starService = starService;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public PaginatedResponse getStarPaginatedResponse() {
        return starPaginatedResponse;
    }

    public List<Star> getLoadedStars(){
        return stars;
    }

    public void setLoadedStars(int employeeId, int subCategoryId, List<Star> stars, PaginatedResponse starPaginatedResponse) {
        if (stars != null) {
            this.stars = stars;
        }
        this.employeeId = employeeId;
        this.subCategoryId = subCategoryId;
        this.starPaginatedResponse = starPaginatedResponse;
        view.showCurrentPage(starPaginatedResponse.getNextPage() == null ? 1 : starPaginatedResponse.getNextPage());
        view.showStars(stars);
    }

    public void getStars(int employeeId, int subcategoryId) {
        this.employeeId = employeeId;
        this.subCategoryId = subcategoryId;
        getStars();
    }

    public void callNextPage() {
        if (starPaginatedResponse.getNext() != null) {
            getStars();
        }
    }

    public void getStars() {
        view.showProgressIndicator();
        starService.getStars(employeeId, subCategoryId, starPaginatedResponse.getNextPage(), new AllStarsCallback<StarsResponse>() {
            @Override
            public void onSuccess(StarsResponse starsResponse) {
                stars.addAll(starsResponse.getStarList());
                starPaginatedResponse.setNext(starsResponse.getNext());
                view.hideProgressIndicator();
                view.showStars(stars);
            }

            @Override
            public void onFailure(ServiceError serviceError) {
                view.hideProgressIndicator();
                showError(serviceError.getDetail());
            }
        });
    }

    public void onKeywordSelected(int position) {
        Keyword keyword = stars.get(position).getKeyword();
        view.goToKeywordContacts(keyword);
    }
}

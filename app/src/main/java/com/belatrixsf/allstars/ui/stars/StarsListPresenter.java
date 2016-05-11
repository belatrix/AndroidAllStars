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

import com.belatrixsf.allstars.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.StarsResponse;
import com.belatrixsf.allstars.services.StarService;
import com.belatrixsf.allstars.ui.common.AllStarsPresenter;
import com.belatrixsf.allstars.utils.AllStarsCallback;
import com.belatrixsf.allstars.utils.ServiceError;

import javax.inject.Inject;

/**
 * Created by icerrate on 25/04/2016.
 */
public class StarsListPresenter extends AllStarsPresenter<StarsListView> {

    private StarService starService;
    private int employeeId;
    private int subCategoryId;
    private PaginatedResponse starPaginatedResponse = new PaginatedResponse();

    @Inject
    public StarsListPresenter(StarsListView view, StarService starService) {
        super(view);
        this.starService = starService;
    }

    public void getStars(int employeeId, int subcategoryId, Integer page) {
        this.employeeId = employeeId;
        this.subCategoryId = subcategoryId;
        getStars(page);
    }

    public void getStars(Integer page) {
        if (starPaginatedResponse.getNext() != null || page == 1) {
            view.showProgressIndicator();
            starService.getStars(employeeId, subCategoryId, page, new AllStarsCallback<StarsResponse>() {
                @Override
                public void onSuccess(StarsResponse starsResponse) {
                    starPaginatedResponse.setNext(starsResponse.getNext());
                    view.hideProgressIndicator();
                    view.showStars(starsResponse.getStarList());
                }

                @Override
                public void onFailure(ServiceError serviceError) {
                    view.hideProgressIndicator();
                    showError(serviceError.getErrorMessage());
                }
            });
        }
    }

}

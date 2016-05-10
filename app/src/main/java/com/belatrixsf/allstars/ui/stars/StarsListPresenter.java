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

import com.belatrixsf.allstars.entities.Star;
import com.belatrixsf.allstars.networking.retrofit.responses.StarsResponse;
import com.belatrixsf.allstars.services.StarService;
import com.belatrixsf.allstars.ui.common.AllStarsPresenter;
import com.belatrixsf.allstars.utils.AllStarsCallback;
import com.belatrixsf.allstars.utils.ServiceError;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by icerrate on 25/04/2016.
 */
public class StarsListPresenter extends AllStarsPresenter<StarsListView> {

    private StarService starService;
    private List<Star> stars;

    @Inject
    public StarsListPresenter(StarsListView view, StarService starService) {
        super(view);
        this.starService = starService;
    }

    public List<Star> getLoadedStars(){
        return stars;
    }

    public void setLoadedStars(List<Star> stars){
        if (stars != null ){
            this.stars = stars;
        }
    }

    public void getStars(int employeeId, int subcategoryId) {
        if (stars == null) {
            view.showProgressIndicator();
            starService.getStars(employeeId, subcategoryId, new AllStarsCallback<StarsResponse>() {
                @Override
                public void onSuccess(StarsResponse starsResponse) {
                    stars = starsResponse.getStarList();
                    view.hideProgressIndicator();
                    view.showStars(starsResponse.getStarList());
                }

                @Override
                public void onFailure(ServiceError serviceError) {
                    showError(serviceError.getErrorMessage());
                }
            });
        }else{
            view.showStars(stars);
        }
    }
}

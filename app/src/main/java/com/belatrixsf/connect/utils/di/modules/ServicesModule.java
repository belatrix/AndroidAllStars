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
package com.belatrixsf.connect.utils.di.modules;

import com.belatrixsf.connect.networking.retrofit.api.CategoryAPI;
import com.belatrixsf.connect.networking.retrofit.api.EmployeeAPI;
import com.belatrixsf.connect.networking.retrofit.api.EventAPI;
import com.belatrixsf.connect.networking.retrofit.api.GuestAPI;
import com.belatrixsf.connect.networking.retrofit.api.StarAPI;
import com.belatrixsf.connect.services.contracts.CategoryService;
import com.belatrixsf.connect.services.contracts.EmployeeService;
import com.belatrixsf.connect.services.contracts.EventService;
import com.belatrixsf.connect.services.contracts.GuestService;
import com.belatrixsf.connect.services.contracts.StarService;
import com.belatrixsf.connect.services.server.CategoryServerService;
import com.belatrixsf.connect.services.server.EmployeeServerService;
import com.belatrixsf.connect.services.server.EventServerService;
import com.belatrixsf.connect.services.server.GuestServerService;
import com.belatrixsf.connect.services.server.StarServerService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gyosida on 4/12/16.
 */
@Module
public class ServicesModule {

    @Provides
    public EmployeeService providesEmployeeService(EmployeeAPI employeeAPI) {
        return new EmployeeServerService(employeeAPI);
    }

    @Provides
    public StarService providesStarService(StarAPI starAPI) {
        return new StarServerService(starAPI);
    }

    @Provides
    public CategoryService provideCategoryService(CategoryAPI categoryAPI) {
        return new CategoryServerService(categoryAPI);
    }

    @Provides
    public EventService provideEventService(EventAPI eventAPI) {
        return new EventServerService(eventAPI);
    }

    @Provides
    public GuestService provideGuestService(GuestAPI guestAPI) {
        return new GuestServerService(guestAPI);
    }

}

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
package com.belatrixsf.connect.utils.di.components;

import com.belatrixsf.connect.utils.di.modules.presenters.AboutPresenterModule;
import com.belatrixsf.connect.managers.EmployeeManager;
import com.belatrixsf.connect.managers.GuestManager;
import com.belatrixsf.connect.utils.di.modules.RetrofitModule;
import com.belatrixsf.connect.utils.di.modules.ServicesModule;
import com.belatrixsf.connect.utils.di.modules.presenters.AccountPresenterModule;
import com.belatrixsf.connect.utils.di.modules.presenters.CategoriesListModule;
import com.belatrixsf.connect.utils.di.modules.presenters.ContactsKeywordPresenterModule;
import com.belatrixsf.connect.utils.di.modules.presenters.ContactsListPresenterModule;
import com.belatrixsf.connect.utils.di.modules.presenters.EditAccountPresenterModule;
import com.belatrixsf.connect.utils.di.modules.presenters.EventDetailPresenterModule;
import com.belatrixsf.connect.utils.di.modules.presenters.EventListPresenterModule;
import com.belatrixsf.connect.utils.di.modules.presenters.ExpandPicturePresenterModule;
import com.belatrixsf.connect.utils.di.modules.presenters.GiveStarPresenterModule;
import com.belatrixsf.connect.utils.di.modules.presenters.KeywordsListModule;
import com.belatrixsf.connect.utils.di.modules.presenters.RankingPresenterModule;
import com.belatrixsf.connect.utils.di.modules.presenters.SignUpPresenterModule;
import com.belatrixsf.connect.utils.di.modules.presenters.StarsListPresenterModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by gyosida on 4/12/16.
 */
@Singleton
@Component(
        modules = {
                RetrofitModule.class,
                ServicesModule.class
        }
)
public interface ApplicationComponent {

    EmployeeManager employeeManager();

    GuestManager guestManager();

    SignUpComponent signUpComponent(SignUpPresenterModule signUpPresenterModule);

    AccountComponent accountComponent(AccountPresenterModule accountPresenterModule);

    ExpandPictureComponent expandPictureComponent(ExpandPicturePresenterModule expandPicturePresenterModule);

    ContactsListComponent contactsListComponent(ContactsListPresenterModule contactsListPresenterModule);

    StarsListComponent starsListComponent(StarsListPresenterModule StarsListPresenterModule);

    RankingComponent rankingComponent(RankingPresenterModule rankingPresenterModule);

    GiveStarComponent giveStarComponent(GiveStarPresenterModule giveStarPresenterModule);

    CategoriesListComponent categoriesListComponent(CategoriesListModule categoriesListModule);

    ContactsKeywordListComponent contactsKeywordListComponent(ContactsKeywordPresenterModule contactsKeywordPresenterModule);

    KeywordsComponent keywordsListComponent(KeywordsListModule keywordsListModule);

    EditAccountComponent editAccountComponent(EditAccountPresenterModule editAccountPresenterModule);

    EventListComponent eventListComponent(EventListPresenterModule EventListPresenterModule);

    EventDetailComponent eventDetailComponent(EventDetailPresenterModule eventDetailPresenterModule);

    AboutComponent aboutComponent(AboutPresenterModule aboutPresenterModule);

}

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
package com.belatrixsf.allstars.ui.welcome;

import com.belatrixsf.allstars.managers.EmployeeManager;
import com.belatrixsf.allstars.ui.common.AllStarsPresenter;

import javax.inject.Inject;

/**
 * Created by icerrate on 17/05/16.
 */
public class WelcomePresenter extends AllStarsPresenter<WelcomeView> {

    static final int TYPE_LOGIN = 0;
    static final int TYPE_SIGNUP = 1;

    private EmployeeManager employeeManager;
    private int lastViewType = 0;

    @Inject
    public WelcomePresenter(WelcomeView view, EmployeeManager employeeManager) {
        super(view);
        this.employeeManager = employeeManager;
    }

    public void loginViewButtonClicked(){
        if (lastViewType == TYPE_SIGNUP){
            lastViewType = TYPE_LOGIN;
            view.showLogin();
        }
    }

    public void signUpViewButtonClicked(){
        if (lastViewType == TYPE_LOGIN){
            lastViewType = TYPE_SIGNUP;
            view.showSignUp();
        }
    }

}

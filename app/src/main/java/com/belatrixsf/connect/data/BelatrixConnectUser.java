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
package com.belatrixsf.connect.data;

import com.belatrixsf.connect.entities.Employee;

/**
 * Created by echuquilin on 9/25/17.
 */
public class BelatrixConnectUser {

    private Object userPicture;
    private Employee userInfo;

    private static BelatrixConnectUser instance;

    public BelatrixConnectUser() {
        userPicture = null;
        userInfo = null;
    }

    public static BelatrixConnectUser get() {
        if (instance == null) {
            instance = new BelatrixConnectUser();
        }
        return instance;
    }

    public Object getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(Object userPicture) {
        this.userPicture = userPicture;
    }

    public Employee getUserInfo() {
        return userInfo;
    }

    public void registerUser(Employee userInfo) {
        this.userInfo = userInfo;
        if (userPicture == null) {
            userPicture = userInfo.getAvatar();
        }
    }

    public void clear() {
        userInfo = null;
        userPicture = null;
        instance = null;
    }

}

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

package com.belatrixsf.allstars.services;

import android.os.NetworkOnMainThreadException;

import com.belatrixsf.allstars.networking.retrofit.RetrofitCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;


/**
 * @author Carlos Piñan
 */
// TODO Implement a way to clean the callHashMap every X time.
public abstract class AllStarsService {

    private static HashMap<String, List<Call>> callHashMap;

    protected static void enqueue(String tag, Call call, RetrofitCallback callback) {
        if (callHashMap == null) {
            callHashMap = new HashMap<>();
        }
        if (callHashMap.containsKey(tag)) {
            callHashMap.get(tag).add(call);
        } else {
            List<Call> callList = new ArrayList<>();
            callList.add(call);
            callHashMap.put(tag, callList);
        }
        call.enqueue(callback);
    }

    public static void cancel(String tag) {
        if (tag != null && callHashMap != null && !callHashMap.isEmpty() && callHashMap.containsKey(tag)) {
            List<Call> callList = callHashMap.get(tag);
            try {
                for (Call call : callList) {
                    call.cancel();
                }
            } catch (NetworkOnMainThreadException e) {
                e.printStackTrace();
            }
            callList.clear();
            callHashMap.remove(tag);
        }
    }

    public static void cancelAll() {
        if (callHashMap != null && !callHashMap.isEmpty()) {
            for (Map.Entry<String, List<Call>> entry : callHashMap.entrySet()) {
                cancel(entry.getKey());
            }
            callHashMap.clear();
        }
    }
}

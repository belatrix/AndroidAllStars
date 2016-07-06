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

import com.belatrixsf.connect.BuildConfig;
import com.belatrixsf.connect.managers.PreferencesManager;
import com.belatrixsf.connect.networking.retrofit.api.CategoryAPI;
import com.belatrixsf.connect.networking.retrofit.api.EmployeeAPI;
import com.belatrixsf.connect.networking.retrofit.api.EventAPI;
import com.belatrixsf.connect.networking.retrofit.api.GuestAPI;
import com.belatrixsf.connect.networking.retrofit.api.StarAPI;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by gyosida on 4/12/16.
 */
@Module
public class RetrofitModule {

    @Singleton
    @Provides
    public OkHttpClient providesOkHttpClient() {
        return new OkHttpClient();
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        OkHttpClient.Builder builder = okHttpClient.newBuilder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String token = PreferencesManager.get().getToken();
                if (token != null) {
                    Request request = chain.request().newBuilder().addHeader("Authorization", "Token " + token).build();
                    return chain.proceed(request);
                }
                return chain.proceed(chain.request());
            }
        });
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .client(builder.build())
                .build();
    }

    @Provides
    public EmployeeAPI provideUserAPI(Retrofit retrofit) {
        return retrofit.create(EmployeeAPI.class);
    }

    @Provides
    public StarAPI providesStarAPI(Retrofit retrofit) {
        return retrofit.create(StarAPI.class);
    }

    @Provides
    public CategoryAPI provideCategoryAPI(Retrofit retrofit) {
        return retrofit.create(CategoryAPI.class);
    }

    @Provides
    public EventAPI provideEventAPI(Retrofit retrofit) {
        return retrofit.create(EventAPI.class);
    }
    @Provides
    public GuestAPI provideGuestAPI(Retrofit retrofit) {
        return retrofit.create(GuestAPI.class);
    }
}
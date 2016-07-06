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
package com.belatrixsf.connect.utils.media.loaders;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.belatrixsf.connect.utils.media.transformations.glide.BorderedCircleGlideTransformation;
import com.belatrixsf.connect.utils.media.transformations.glide.CircleGlideTransformation;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;

/**
 * @author Carlos Piñan
 */
public class GlideLoader implements ImageLoader {

    @Override
    public void loadFromRes(int resId, ImageView imageView, Drawable placeholder) {
        Glide.with(imageView.getContext()).load(resId).placeholder(placeholder).into(imageView);
    }

    @Override
    public void loadFromRes(int resId, ImageView imageView, ImageTransformation transformation, Drawable placeholder) {
        loadFromRes(resId, imageView, transformation, null, placeholder);
    }

    @Override
    public void loadFromRes(int resId, ImageView imageView, ImageTransformation transformation, Callback callback, Drawable placeholder) {
        Context context = imageView.getContext();
        load(context, Glide.with(context).load(resId), transformation, callback, placeholder).into(imageView);
    }

    @Override
    public void loadFromUrl(String url, ImageView imageView, Drawable placeholder) {
        Glide.with(imageView.getContext()).load(url).placeholder(placeholder).into(imageView);
    }

    @Override
    public void loadFromUrl(String url, ImageView imageView, ImageTransformation transformation, Drawable placeholder) {
        loadFromUrl(url, imageView, transformation, null, placeholder);
    }

    @Override
    public void loadFromUrl(String url, ImageView imageView, ImageTransformation transformation, Callback callback, Drawable placeholder) {
        Context context = imageView.getContext();
        load(context, Glide.with(context).load(url), transformation, callback, placeholder).into(imageView);
    }

    @Override
    public void loadFromPath(String path, ImageView imageView, ImageTransformation transformation, Drawable placeholder) {
        loadFromPath(path, imageView, transformation, null, placeholder);
    }

    @Override
    public void loadFromPath(String path, ImageView imageView, ImageTransformation transformation, Callback callback, Drawable placeholder) {
        Context context = imageView.getContext();
        load(context, Glide.with(context).load(new File(path)), transformation, callback, placeholder).into(imageView);
    }

    private <T> DrawableTypeRequest<T> load(
            Context context,
            DrawableTypeRequest<T> load,
            ImageTransformation transformation,
            final Callback callback,
            Drawable placeholder
    ) {
        load.fitCenter();
        load.placeholder(placeholder);
        if (context != null && transformation != null) {
            switch (transformation) {
                case BORDERED_CIRCLE:
                    load.transform(new BorderedCircleGlideTransformation(context));
                    break;
                case CIRCLE:
                    load.transform(new CircleGlideTransformation(context));
                    break;
            }
        }
        if (callback != null) {
            load.listener(new RequestListener<T, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, T model, Target<GlideDrawable> target, boolean isFirstResource) {
                    callback.onSuccess();
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, T model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    callback.onFailure();
                    return false;
                }
            });
        }
        return load;
    }
}
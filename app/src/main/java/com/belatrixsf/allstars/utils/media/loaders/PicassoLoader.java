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

package com.belatrixsf.allstars.utils.media.loaders;

import android.content.Context;
import android.widget.ImageView;

import com.belatrixsf.allstars.utils.media.transformations.picasso.BorderedCirclePicassoTransformation;
import com.belatrixsf.allstars.utils.media.transformations.picasso.CirclePicassoTransformation;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

/**
 * @author Carlos Piñan
 */
public class PicassoLoader implements ImageLoader {

    @Override
    public void loadFromUrl(String url, ImageView imageView, ImageTransformation transformation) {
        loadFromUrl(url, imageView, transformation, null);
    }

    @Override
    public void loadFromUrl(String url, ImageView imageView, ImageTransformation transformation, Callback callback) {
        Context context = imageView.getContext();
        load(context, Picasso.with(context).load(url), transformation, imageView, callback);
    }

    @Override
    public void loadFromPath(String path, ImageView imageView, ImageTransformation transformation) {
        loadFromPath(path, imageView, transformation, null);
    }

    @Override
    public void loadFromPath(String path, ImageView imageView, ImageTransformation transformation, Callback callback) {
        Context context = imageView.getContext();
        load(context, Picasso.with(context).load(new File(path)), transformation, imageView, callback);
    }

    private void load(
            Context context,
            RequestCreator load,
            ImageTransformation transformation,
            ImageView imageView,
            final Callback callback
    ) {
        load.centerInside();
        if (context != null && transformation != null) {
            switch (transformation) {
                case BORDERED_CIRCLE:
                    load.transform(new BorderedCirclePicassoTransformation(context));
                    break;
                case CIRCLE:
                    load.transform(new CirclePicassoTransformation());
                    break;
            }
        }
        if (callback != null) {
            load.into(imageView, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    callback.onSuccess();
                }

                @Override
                public void onError() {
                    callback.onFailure();
                }
            });
        } else {
            load.into(imageView);
        }
    }
}
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
package com.belatrixsf.allstars.ui.common.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.belatrixsf.allstars.R;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by pedrocarrillo on 4/16/16.
 */
public class BorderedCircleTransformation extends BitmapTransformation {

    private final int strokeWidth;

    public BorderedCircleTransformation(Context context) {
        super(context);
        Resources resources = context.getResources();
        strokeWidth = resources.getDimensionPixelSize(R.dimen.default_picture_stroke_width);
    }

    public BorderedCircleTransformation(Context context, int strokeWidth) {
        super(context);
        this.strokeWidth = strokeWidth;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;
        int size = Math.min(source.getWidth(), source.getHeight());
        int radius = size / 2;
        int outputSize = size + strokeWidth;
        int halfStrokeWidth = strokeWidth / 2;
        Bitmap output = pool.get(outputSize, outputSize, Bitmap.Config.ARGB_8888);
        if (output == null) {
            output = Bitmap.createBitmap(outputSize, outputSize, Bitmap.Config.ARGB_8888);
        }
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Canvas canvas = new Canvas(output);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(radius + halfStrokeWidth, radius + halfStrokeWidth, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, halfStrokeWidth, halfStrokeWidth, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(radius + halfStrokeWidth, radius + halfStrokeWidth, radius, paint);
        return output;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }

}
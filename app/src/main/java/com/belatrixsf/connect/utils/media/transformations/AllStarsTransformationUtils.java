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
package com.belatrixsf.connect.utils.media.transformations;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

/**
 * @author Carlos Piñan
 * @doc Named in this way to avoid issues with the Glide #TransformationUtils
 */
public class AllStarsTransformationUtils {

    private AllStarsTransformationUtils() { /* UNUSED */}

    public static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
        Bitmap result = null;
        if (pool != null) {
            result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        }
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float radius = size / 2f;
        canvas.drawCircle(radius, radius, radius, paint);
        return result;
    }

    public static Bitmap borderedCircle(BitmapPool pool, Bitmap source, int strokeWidth) {
        if (source == null) return null;
        int size = Math.min(source.getWidth(), source.getHeight());
        int radius = size / 2;
        int outputSize = size + strokeWidth;
        int halfStrokeWidth = strokeWidth / 2;
        Bitmap output = null;
        if (pool == null) {
            output = pool.get(outputSize, outputSize, Bitmap.Config.ARGB_8888);
        }
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
}

package com.belatrixsf.allstars.ui.common.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;

import com.belatrixsf.allstars.R;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.lang.ref.WeakReference;

/**
 * Created by pedrocarrillo on 4/16/16.
 */
public class CircleTransform extends BitmapTransformation {

    private WeakReference<Context> contextWeakReference;

    public CircleTransform(Context context) {
        super(context);
        contextWeakReference = new WeakReference<>(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        // TODO this could be acquired from the pool too
        int m = 200;
        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

        Bitmap result = pool.get(size+m, size+m, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size+m, size+m, Bitmap.Config.ARGB_8888);
        }
        float r = size / 2f;

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();

        paint.setShader(null);
        paint.setStrokeWidth(100);
        paint.setColor(ContextCompat.getColor(contextWeakReference.get(), R.color.belatrix));
        canvas.drawCircle(r, r, r*2, paint);

//        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
//        paint.setAntiAlias(true);


//        canvas.drawCircle(r, r, r, paint);

        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }

}
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

    private final int marginSize;
    private final int strokeWidth;

    public BorderedCircleTransformation(Context context) {
        super(context);
        Resources resources = context.getResources();
        marginSize = resources.getDimensionPixelSize(R.dimen.picture_margin_size);
        strokeWidth = resources.getDimensionPixelSize(R.dimen.picture_stroke_width);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;
        int size = Math.min(source.getWidth(), source.getHeight());
        int radius = Math.min(size / 2, size / 2);
        int outputSize = size + marginSize;
        int halfMargin = marginSize / 2;
        Bitmap output = pool.get(outputSize, outputSize, Bitmap.Config.ARGB_8888);
        if (output == null) {
            output = Bitmap.createBitmap(outputSize, outputSize, Bitmap.Config.ARGB_8888);
        }
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Canvas canvas = new Canvas(output);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(radius + halfMargin, radius + halfMargin, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, halfMargin, halfMargin, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(radius + halfMargin, radius + halfMargin, radius, paint);
        return output;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }

}
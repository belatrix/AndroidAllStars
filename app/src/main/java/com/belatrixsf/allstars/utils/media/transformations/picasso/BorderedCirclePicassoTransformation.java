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

package com.belatrixsf.allstars.utils.media.transformations.picasso;

import android.content.Context;
import android.graphics.Bitmap;

import com.belatrixsf.allstars.utils.media.transformations.AllStarsTransformationUtils;
import com.belatrixsf.allstars.utils.media.transformations.glide.BorderedCircleGlideTransformation;
import com.squareup.picasso.Transformation;

/**
 * @author Carlos Piñan
 */
public class BorderedCirclePicassoTransformation extends BorderedCircleGlideTransformation implements Transformation {

    public BorderedCirclePicassoTransformation(Context context) {
        super(context);
    }

    @Override
    public Bitmap transform(Bitmap source) {
        return AllStarsTransformationUtils.borderedCircle(null, source, marginSize, strokeWidth);
    }

    @Override
    public String key() {
        return "borderedCirclePicasso()";
    }
}

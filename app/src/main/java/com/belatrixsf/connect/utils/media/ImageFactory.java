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
package com.belatrixsf.connect.utils.media;

import com.belatrixsf.connect.utils.media.loaders.GlideLoader;
import com.belatrixsf.connect.utils.media.loaders.ImageLoader;
import com.belatrixsf.connect.utils.media.loaders.PicassoLoader;

/**
 * @author Carlos Piñan
 */
public class ImageFactory {

    public enum ImageProvider {
        GLIDE,
        PICASSO
    }

    private ImageFactory() { /* UNUSED */ }

    public static ImageLoader getLoader() {
        return getLoader(ImageProvider.GLIDE);
    }

    public static ImageLoader getLoader(ImageProvider imageProvider) {
        switch (imageProvider) {
            case GLIDE:
                return new GlideLoader();
            case PICASSO:
                return new PicassoLoader();
            default:
                return new GlideLoader();
        }
    }
}
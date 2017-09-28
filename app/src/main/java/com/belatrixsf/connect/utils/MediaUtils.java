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
package com.belatrixsf.connect.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by PedroCarrillo on 5/24/16.
 */
public class MediaUtils {

    public static final String JPEG_FILE_SUFFIX = ".jpg";

    private static MediaUtils ourInstance = new MediaUtils();

    public static MediaUtils get() {
        return ourInstance;
    }

    private MediaUtils() {}

    public Bitmap getBitmapFromByteArray(byte[] array) {
        return array != null ? BitmapFactory.decodeByteArray(array, 0, array.length) : null;
    }

    public byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        byte[] byteArray = null;
        if (bitmap != null) {
            ByteArrayOutputStream blob = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, blob);
            byteArray = blob.toByteArray();
        }
        return byteArray;
    }

    private Bitmap getResizedBitmap(String imagePath){

        int targetW = 200;
        int targetH = 200;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        bmOptions.inScaled = false;

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
        int degree = getRotationFromImageFile(imagePath);

        if (degree>0){
            Matrix matrix = new Matrix();
            if(bitmap.getWidth()>bitmap.getHeight()){
                matrix.setRotate(degree);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            }
        }

        return bitmap;
    }

    private int getRotationFromImageFile(String photoPath){
        try {
            ExifInterface ei = new ExifInterface(photoPath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int degree = 0;

            switch (orientation) {
                case ExifInterface.ORIENTATION_NORMAL:
                    degree = 0;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                case ExifInterface.ORIENTATION_UNDEFINED:
                    degree = 0;
                    break;
                default:
                    degree = 90;
            }

            return degree;
        } catch (IOException e){
            e.printStackTrace();
            return 0;
        }
    }

    public File getReducedBitmapFile(Uri uri) {
        File file = new File(uri.getPath());
        return getReducedBitmapFile(file.getAbsolutePath(), file.getName());
    }

    public File getReducedBitmapFile(String imagePath, String fileName){
        Bitmap reducedBitmap = getResizedBitmap(imagePath);

        int degree = getRotationFromImageFile(imagePath);

        Matrix matrix = new Matrix();
        matrix.setRotate(degree);
        reducedBitmap = Bitmap.createBitmap(reducedBitmap, 0, 0, reducedBitmap.getWidth(), reducedBitmap.getHeight(), matrix, true);

        return createFileFromBitmap(reducedBitmap, fileName);
    }

    protected File createFileFromBitmap(Bitmap bitmap, String imageName){
        File albumF = getAlbumDir();
        try {
            File imageFile = File.createTempFile(imageName, JPEG_FILE_SUFFIX, albumF);
            OutputStream fOut = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            return imageFile;
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public File createLocalImage(String fileName) throws IOException {
        File storageDir = getAlbumDir();
        File image = File.createTempFile(
                fileName,  /* prefix */
                JPEG_FILE_SUFFIX,         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }

    public File getAlbumDir() {
        return Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
    }

    /**
     * Specific methods for Profile Picture Media Handling
     */

    public File getReducedProfilePictureBitmapFile(String imagePath) {
        return getReducedBitmapFile(imagePath,  getLocalProfileFileName());
    }

    public File createLocalProfilePicture() throws IOException {
        return createLocalImage(getLocalProfileFileName());
    }

    public String getLocalProfileFileName() {
        return "belatrix_connect_profile_picture";
    }

    /**
     *  Methods to obtain files paths
     */

    public String getFilePathFromMediaUri(Context context, Uri uri) {
        if ( uri == null ) {
            return null;
        }
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(
                uri, projection, null, null, null);
        String path = "";
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(projection[0]);
            path = cursor.getString(columnIndex);
            cursor.close();
        }
        return path;
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static byte[] compressDrawable (Drawable imgDrawable){
        Bitmap bitmap = drawableToBitmap(imgDrawable);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

}

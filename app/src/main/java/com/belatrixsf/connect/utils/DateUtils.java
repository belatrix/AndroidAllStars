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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by icerrate on 28/04/2016.
 */
public class DateUtils {
    public static final String DATE_FORMAT_1 = "yyyy-MM-dd'T'HH:mm:ss.S";
    public static final String DATE_FORMAT_2 = "dd/MM/yyyy";
    public static final String DATE_FORMAT_3 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String DATE_FORMAT_4 = "dd/MM/yyyy hh:mm aa";

    public static String formatDate(String date, String inputFormat, String outputFormat){
        try {
            SimpleDateFormat inFormat = new SimpleDateFormat(inputFormat);
            Date newDate = inFormat.parse(date);

            SimpleDateFormat outformat = new SimpleDateFormat(outputFormat);
            return outformat.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

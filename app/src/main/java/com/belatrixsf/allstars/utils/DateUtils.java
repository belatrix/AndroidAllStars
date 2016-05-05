package com.belatrixsf.allstars.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by icerrate on 28/04/2016.
 */
public class DateUtils {
    public static final String DATE_FORMAT_1 = "yyyy-MM-dd'T'HH:mm:ss.S";
    public static final String DATE_FORMAT_2 = "dd/MM/yyyy";

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

package com.knotlink.salseman.utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String getTodayDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(Constant.DATE_FORMAT_DD_MMM_YYYY, Locale.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public static String getCurrentTime() {

        final Calendar c = Calendar.getInstance();
        int currentTime =     (c.get(Calendar.HOUR_OF_DAY) * 10000) + (c.get(Calendar.MINUTE) * 100) + (c.get(Calendar.SECOND));

        return(String.valueOf(currentTime));

    }

    public static String getTodaysDate() {

        final Calendar c = Calendar.getInstance();
        int todaysDate =     (c.get(Calendar.YEAR) * 10000) + ((c.get(Calendar.MONTH) + 1) * 100) + (c.get(Calendar.DAY_OF_MONTH));
        return(String.valueOf(todaysDate));

    }

    public static String getDeliveryDate(String dateToday) {

        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_DD_MMM_YYYY);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dateToday));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Number of Days to add
        c.add(Calendar.DAY_OF_MONTH, 2);

        return sdf.format(c.getTime());
    }
    public static String getPresentDay() {

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
    }
    public static String timeDiff(String dateStart, String dateStop) {
        String dH = "";
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_FORMAT_DD_MMM_YYYY);

            Date d1 = format.parse(dateStart);
            Date d2 = format.parse(dateStop);
            long diff = d2.getTime() - d1.getTime();

            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if (diffDays > 0) {
                dH = String.valueOf(diffDays + " d " + (diffHours) + " hr " + (diffMinutes) + " min");
            } else if (diffHours > 0) {
                dH = String.valueOf((diffHours) + " hr " + (diffMinutes) + " min");
            } else {
                dH = String.valueOf((diffMinutes) + " min");
            }


        } catch (Exception e) {
            CustomLog.e("DateUtil",e.toString());
        }
        return dH;
    }

    public static String compareCurrent(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_DD_MMM_YYYY);
        String getCurrentDateTime = sdf.format(c.getTime());
        return getCurrentDateTime;
    }


}

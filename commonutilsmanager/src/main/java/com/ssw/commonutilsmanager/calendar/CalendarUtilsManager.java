package com.ssw.commonutilsmanager.calendar;

import com.ssw.commonutilsmanager.common.ConstantList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.ssw.commonutilsmanager.common.ConstantList.DEV_MODE;
import static com.ssw.commonutilsmanager.common.ConstantList.EMPTY_STRING;

public class CalendarUtilsManager {
    private static final String TAG = "CalendarUtilsManager";

    private static final List<Long> times = Arrays.asList(
            TimeUnit.DAYS.toMillis(365),
            TimeUnit.DAYS.toMillis(30),
            TimeUnit.DAYS.toMillis(1),
            TimeUnit.HOURS.toMillis(1),
            TimeUnit.MINUTES.toMillis(1),
            TimeUnit.SECONDS.toMillis(1));

    private static final List<String> timesString = Arrays.asList("year", "month", "day", "hour", "minute", "second");

    private static CalendarUtilsManager calendarUtilsManager;

    public static synchronized CalendarUtilsManager getInstance() {
        if (calendarUtilsManager == null) {
            calendarUtilsManager = new CalendarUtilsManager();
        }
        return calendarUtilsManager;
    }

    public String getDuration(long duration) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < times.size(); i++) {
            Long current = times.get(i);
            long temp = duration / current;
            if (temp > 0) {
                res.append(temp).append(" ").append(timesString.get(i)).append(temp > 1 ? "s" : "").append(" ago");
                break;
            }
        }
        if (res.toString().equals("") || res.toString().trim().isEmpty())
            return "0 second ago";
        else if (res.toString().equalsIgnoreCase("null")) {
            return "0 second ago";
        } else
            return res.toString();
    }

    public Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    public String getCurrentDateCustomFormat(String outputDateFormat) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(outputDateFormat);
            return simpleDateFormat.format(Calendar.getInstance().getTime());
        } catch (Exception ex) {
            if (DEV_MODE) {
                ex.printStackTrace();
            }
            return EMPTY_STRING;
        }
    }

    public String getDateInCustomFormat(Date date, String outputDateFormat) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(outputDateFormat);
            return simpleDateFormat.format(date);
        } catch (Exception ex) {
            if (DEV_MODE) {
                ex.printStackTrace();
            }
            return EMPTY_STRING;
        }
    }

    public String getPreviousDate(int numberOfDays, String outputDateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(outputDateFormat);
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        return simpleDateFormat.format(new Date(System.currentTimeMillis() - (numberOfDays * DAY_IN_MS)));
    }

    public Date getPreviousDate(int numberOfDays) {
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        return new Date(System.currentTimeMillis() - (numberOfDays * DAY_IN_MS));
    }

    public ArrayList<String> getPreviousMonth(int numberOfMonths, String outputDateFormat) {
        ArrayList<String> returningDateList = new ArrayList<>();

        Calendar aCalendar = Calendar.getInstance();
        aCalendar.add(Calendar.MONTH, -1 * numberOfMonths);
        aCalendar.set(Calendar.DATE, 1);
        returningDateList.add(convertDateToString(aCalendar.getTime(), outputDateFormat));

        aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        returningDateList.add(convertDateToString(aCalendar.getTime(), outputDateFormat));

        return returningDateList;
    }

    public String convertDateToString(Date inputDate, String dateFormat) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            return simpleDateFormat.format(inputDate);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public String getFormattedDateTime(String date, String oldFormat, String newFormat) {
        String result = date;
        try {
            String newDateString;

            SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
            Date d = sdf.parse(date);
            sdf.applyPattern(newFormat);
            newDateString = sdf.format(d);
            result = newDateString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getDefaultFormattedDate(String date, String oldFormat) {
        String result = date;
        try {
            String newDateString;

            SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
            Date d = sdf.parse(date);
            sdf.applyPattern(ConstantList.DEFAULT_DATE_FORMAT_NEW);
            newDateString = sdf.format(d);
            result = newDateString;
        } catch (Exception e) {
            e.printStackTrace();
            return "N/A";
        }
        return result;
    }

    public String convertDateString(String dateAsString, String newFormat, String oldFormat, String alterOldFormat) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
            SimpleDateFormat sdf2 = new SimpleDateFormat(alterOldFormat);
            Date date;
            try {
                date = sdf.parse(dateAsString);
                sdf.applyPattern(newFormat);
                return sdf.format(date);
            } catch (Exception e) {
                date = sdf2.parse(dateAsString);
                sdf2.applyPattern(newFormat);
                return sdf2.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getServerFormattedDate(String date, String oldFormat) {
        String result;
        try {
            String newDateString;

            SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
            Date d = sdf.parse(date);
            sdf.applyPattern(ConstantList.SERVER_DATE_FORMAT);
            newDateString = sdf.format(d);
            result = newDateString;
        } catch (Exception e) {
            result = "";
        }
        return result;
    }
}

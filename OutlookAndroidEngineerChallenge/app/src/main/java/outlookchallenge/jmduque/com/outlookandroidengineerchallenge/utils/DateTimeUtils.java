package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.utils;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Jose on 7/29/2016.
 * This class helps with date creations and comparisons.
 */
public class DateTimeUtils {

    public static final Calendar gregorianCalendar = new GregorianCalendar();

    public static final SimpleDateFormat dayOfTheYear = new SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
    );

    public static final SimpleDateFormat monthOfTheYear = new SimpleDateFormat(
            "yyyy-MM",
            Locale.getDefault()
    );

    public static final SimpleDateFormat monthName = new SimpleDateFormat(
            "MMMM",
            Locale.getDefault()
    );

    public static final SimpleDateFormat year = new SimpleDateFormat(
            "yyyy",
            Locale.getDefault()
    );

    public static final SimpleDateFormat dayOfTheMonth = new SimpleDateFormat(
            "dd",
            Locale.getDefault()
    );

    public static final SimpleDateFormat dayOfTheMonthWithWeekday = new SimpleDateFormat(
            "EEEE, MMMM dd",
            Locale.getDefault()
    );

    public static final SimpleDateFormat dayOfTheYearWithWeekday = new SimpleDateFormat(
            "EEEE, MMMM dd yyyy",
            Locale.getDefault()
    );

    /**
     * @param referenceDate  base Date to compare
     * @param comparableDate item to be compared with the referenceDate
     * @return true if comparableDate and referenceDate are within same day
     */
    public static boolean isSameDay(
            Date referenceDate,
            Date comparableDate
    ) {
        if (referenceDate == null) {
            return false;
        }

        if (comparableDate == null) {
            return false;
        }

        if (Math.abs(referenceDate.getTime() - comparableDate.getTime())
                > DateUtils.DAY_IN_MILLIS) {
            return false;
        }

        synchronized (dayOfTheYear) {
            return TextUtils.equals(
                    dayOfTheYear.format(referenceDate),
                    dayOfTheYear.format(comparableDate)
            );
        }
    }

    /**
     * @param referenceDate  base Date to compare
     * @param comparableDate item to be compared with the referenceDate
     * @return true if comparableDate is the day after referenceDate
     */
    public static boolean isNextDay(
            Date referenceDate,
            Date comparableDate
    ) {
        if (referenceDate == null) {
            return false;
        }

        if (comparableDate == null) {
            return false;
        }

        Date tomorrowDate = new Date(
                referenceDate.getTime() + DateUtils.DAY_IN_MILLIS
        );

        synchronized (dayOfTheYear) {
            return TextUtils.equals(
                    dayOfTheYear.format(tomorrowDate),
                    dayOfTheYear.format(comparableDate)
            );
        }
    }

    /**
     * @param referenceDate  base Date to compare
     * @param comparableDate item to be compared with the referenceDate
     * @return true comparableDate and referenceDate are within same month
     */
    public static boolean isSameMonth(
            Date referenceDate,
            Date comparableDate
    ) {
        if (referenceDate == null) {
            return false;
        }

        if (comparableDate == null) {
            return false;
        }

        synchronized (monthOfTheYear) {
            return TextUtils.equals(
                    monthOfTheYear.format(referenceDate),
                    monthOfTheYear.format(comparableDate)
            );
        }
    }


    /**
     * @param referenceDate  base Date to compare
     * @param comparableDate item to be compared with the referenceDate
     * @return true if comparableDate and referenceDate are within the same year
     */
    public static boolean isSameYear(
            Date referenceDate,
            Date comparableDate
    ) {
        if (referenceDate == null) {
            return false;
        }

        if (comparableDate == null) {
            return false;
        }

        Date tomorrowDate = new Date(
                referenceDate.getTime() + DateUtils.DAY_IN_MILLIS
        );

        synchronized (year) {
            return TextUtils.equals(
                    year.format(tomorrowDate),
                    year.format(comparableDate)
            );
        }
    }

    /**
     * @return true if the provided @param date is within @param startDate and @param endDate
     * with limits included. If start or end date are null, we won't apply those dates as a limiter.
     */
    public static boolean isDateWithinDates(
            @Nullable Date date,
            @Nullable Date startDate,
            @Nullable Date endDate
    ) {
        //If no date is provided, we will always return false
        if (date == null) {
            return false;
        }

        long dateTime = date.getTime();
        //
        long startTime = startDate != null ? startDate.getTime() : 0;
        long endTime = endDate != null ? endDate.getTime() : Long.MAX_VALUE;

        return dateTime >= startTime && dateTime <= endTime;
    }


    public static int getYear(Date date) {
        gregorianCalendar.setTime(date);
        return gregorianCalendar.get(Calendar.YEAR);
    }

    /**
     * @return 0-based month of the year
     */
    public static int getMonth(Date date) {
        gregorianCalendar.setTime(date);
        return gregorianCalendar.get(Calendar.MONTH);
    }

    /**
     * @return returns the value of the day of the month
     */
    public static int getDay(Date date) {
        gregorianCalendar.setTime(date);
        return gregorianCalendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * @return 0-based day of the week (Sunday == 0)
     */
    public static int getFirstDayOfWeek(Date date) {
        gregorianCalendar.setTime(date);
        return gregorianCalendar.get(Calendar.DAY_OF_WEEK) - 1;
    }
}

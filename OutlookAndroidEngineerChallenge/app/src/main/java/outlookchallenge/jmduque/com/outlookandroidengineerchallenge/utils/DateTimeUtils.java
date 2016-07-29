package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.utils;

import android.text.TextUtils;
import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Jose on 7/29/2016.
 * This class helps with date creations and comparisons.
 */
public class DateTimeUtils {

    public static final SimpleDateFormat dayOfTheYear = new SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
    );

    public static final SimpleDateFormat year = new SimpleDateFormat(
            "yyyy",
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

        synchronized (dayOfTheYear) {
            return TextUtils.equals(
                    dayOfTheYear.format(referenceDate),
                    dayOfTheYear.format(comparableDate)
            );
        }
    }

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
}

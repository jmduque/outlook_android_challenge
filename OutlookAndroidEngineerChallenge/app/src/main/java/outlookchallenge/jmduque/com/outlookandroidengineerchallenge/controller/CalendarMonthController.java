package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.R;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.CalendarDay;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.CalendarMonth;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.utils.DateTimeUtils;

/**
 * Created by Jose on 7/31/2016.
 */
public class CalendarMonthController {

    public static final int DAYS_IN_A_WEEK = 7;

    /**
     * This method generates a CalendarMonth object by a provided year and month value.
     *
     * @param context to be used to generate strings
     * @param year    the calendar year i.e., 2016-06-30 -> need to provide 2016
     * @param month   the 0-based calendar month i.e., 2016-06-30 -> need to provide 5
     */
    public CalendarMonth generateCalendarMonth(
            @NonNull final Context context,
            final int year,
            final int month
    ) {
        int nextMonth = month + 1;
        int nextYear = year;
        nextYear += nextMonth / 12;
        nextMonth = nextMonth % 12;

        //We generate a calendar event
        CalendarMonth calendarMonth = new CalendarMonth();
        calendarMonth.setYear(year);
        calendarMonth.setMonth(month);
        try {
            Date firstDayOfTheMonth = DateTimeUtils.dayOfTheYear.parse(
                    context.getString(
                            R.string.oaec_calendar_month_first_day,
                            year,
                            month + 1
                    )
            );

            calendarMonth.setFirstDayOfTheMonth(
                    firstDayOfTheMonth
            );
            calendarMonth.setMonthName(
                    DateTimeUtils.monthName.format(firstDayOfTheMonth)
            );
            calendarMonth.setFirstWeekDayOfTheMonth(
                    DateTimeUtils.getFirstDayOfWeek(firstDayOfTheMonth)
            );

            long firstDayOfNextMonthTime = DateTimeUtils.dayOfTheYear.parse(
                    context.getString(
                            R.string.oaec_calendar_month_first_day,
                            nextYear,
                            nextMonth + 1
                    )
            ).getTime();
            //Last day of this month is 1ms earlier than the begining of next month
            calendarMonth.setLastDayOfTheMonth(
                    new Date(firstDayOfNextMonthTime - 1)
            );

            generateCalendarDays(
                    calendarMonth
            );
        } catch (ParseException ignored) {
        }

        return calendarMonth;
    }

    /**
     * Creates the calendar days of a month including overflow (i.e., if a month does not start
     * on the first day of the week, we will add days to fill before that day).
     * We will also add days from next month till we fill the 6 rows of calendar.
     *
     * @param calendarMonth to be populated
     */
    public void generateCalendarDays(
            CalendarMonth calendarMonth
    ) {
        List<CalendarDay> daysList = new ArrayList<>();
        Calendar gregorianCalendar = new GregorianCalendar();

        Date firstDayOfMonth = calendarMonth.getFirstDayOfTheMonth();
        Date lastDayOfMonth = calendarMonth.getLastDayOfTheMonth();

        gregorianCalendar.setTime(firstDayOfMonth);
        int firstWeekDayOfTheMonth = calendarMonth.getFirstWeekDayOfTheMonth();

        //We visualize calendar within 6 weeks rows and we fill the gaps accordingly
        for (int i = 0; i < DAYS_IN_A_WEEK * 6; i++) {
            Date date = createDateForPosition(
                    i,
                    firstWeekDayOfTheMonth,
                    calendarMonth.getFirstDayOfTheMonth()
            );
            gregorianCalendar.setTime(date);

            CalendarDay calendarDay = new CalendarDay();
            calendarDay.setDate(
                    date
            );
            calendarDay.setYear(
                    calendarMonth.getYear()
            );
            calendarDay.setMonth(
                    calendarMonth.getMonth()
            );
            calendarDay.setDay(
                    gregorianCalendar.get(
                            Calendar.DAY_OF_MONTH
                    )
            );

            //We consider overflow days those not within the first and last day of month
            calendarDay.setOverflowDay(
                    !DateTimeUtils.isDateWithinDates(
                            date,
                            firstDayOfMonth,
                            lastDayOfMonth
                    )
            );

            //Today is a special day :)
            calendarDay.setToday(
                    DateUtils.isToday(date.getTime())
            );

            daysList.add(calendarDay);
        }

        calendarMonth.setDays(daysList);
    }

    /**
     * @param position           for the calendar's days array
     * @param firstDayOfTheWeek  0 if the month start on Sunday, 1 (Monday) to 6 (Saturday) otherwise
     * @param firstDayOfTheMonth date for the first day of this month
     */
    private Date createDateForPosition(
            int position,
            int firstDayOfTheWeek,
            Date firstDayOfTheMonth
    ) {
        long time = firstDayOfTheMonth.getTime();

        //Take into consideration that some months don't start on first day of the week
        time -= DateUtils.DAY_IN_MILLIS * firstDayOfTheWeek;
        //Add time according to the position within the month
        time += DateUtils.DAY_IN_MILLIS * position;

        return new Date(time);
    }

}

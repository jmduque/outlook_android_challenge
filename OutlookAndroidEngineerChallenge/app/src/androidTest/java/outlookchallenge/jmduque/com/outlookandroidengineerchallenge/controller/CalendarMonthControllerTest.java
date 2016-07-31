package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.controller;

import android.content.Context;

import org.junit.Test;

import java.util.Date;

import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ApplicationTest;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.CalendarMonth;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.utils.DateTimeUtils;

/**
 * Created by Jose on 7/31/2016.
 * Validates methods contained in CalendarMontController class
 */
public class CalendarMonthControllerTest
        extends
        ApplicationTest {

    @Test
    public void testGenerateCalendarMonth() throws Exception {
        Context context = getContext();
        CalendarMonthController calendarMonthController = new CalendarMonthController();

        //Generate 2011's calendar month
        CalendarMonth calendarMonth = calendarMonthController.generateCalendarMonth(
                context,
                2011,
                2
        );

        assertEquals(
                "Unexpected Calendar Year",
                2011,
                calendarMonth.getYear()
        );

        assertEquals(
                "Unexpected Calendar Month",
                2,
                calendarMonth.getMonth()
        );

        assertEquals(
                "Unexpected First Day of the Month",
                DateTimeUtils.dayOfTheYear.parse("2011-03-01"),
                calendarMonth.getFirstDayOfTheMonth()
        );

        assertEquals(
                "Unexpected Last Day of the Month",
                new Date(
                        DateTimeUtils.dayOfTheYear.parse("2011-04-01").getTime() - 1
                ),
                calendarMonth.getLastDayOfTheMonth()
        );

        assertEquals(
                "Unexpected first weekday of the month",
                2, //March 1st, 2011 was Tuesday
                calendarMonth.getFirstWeekDayOfTheMonth()
        );

        assertEquals(
                "Unexpected month name",
                "March",
                calendarMonth.getMonthName()
        );
    }

    @Test
    public void testGenerateCalendarDays() throws Exception {
        //TODO
    }
}
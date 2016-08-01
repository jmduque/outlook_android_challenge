package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.utils;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ApplicationTest;

/**
 * Created by Jose on 7/31/2016.
 * Tests DateTimeUtils.class to validate methods
 */
public class DateTimeUtilsTest
        extends
        ApplicationTest {

    private static final SimpleDateFormat dayOfTheYear = new SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
    );

    @Test
    public void testIsSameDay() throws Exception {
        Date referenceDate = dayOfTheYear.parse("2016-07-29");

        //Same day true case
        assertEquals(
                "Fail to validate same day true case",
                true,
                DateTimeUtils.isSameDay(
                        referenceDate,
                        referenceDate
                )
        );

        //Testing null cases
        assertEquals(
                "Fail to validate null cases",
                false,
                DateTimeUtils.isSameDay(
                        referenceDate,
                        null
                )
        );

        //Testing null cases
        assertEquals(
                "Fail to validate null cases",
                false,
                DateTimeUtils.isSameDay(
                        null,
                        referenceDate
                )
        );

        //Non-same day case
        assertEquals(
                "Fail to validate same day false case",
                false,
                DateTimeUtils.isSameDay(
                        referenceDate,
                        dayOfTheYear.parse("2000-01-01")
                )
        );
    }

    @Test
    public void testIsNextDay() throws Exception {
        Date referenceDate = dayOfTheYear.parse("2016-07-29");

        //Next day true case
        assertEquals(
                "Fail to validate next day true case",
                true,
                DateTimeUtils.isSameDay(
                        referenceDate,
                        dayOfTheYear.parse("2016-07-30")
                )
        );

        //Testing null cases
        assertEquals(
                "Fail to validate null cases",
                false,
                DateTimeUtils.isSameDay(
                        referenceDate,
                        null
                )
        );

        //Testing null cases
        assertEquals(
                "Fail to validate null cases",
                false,
                DateTimeUtils.isSameDay(
                        null,
                        referenceDate
                )
        );

        //Not-next day case
        assertEquals(
                "Fail to validate next day false case",
                false,
                DateTimeUtils.isSameDay(
                        referenceDate,
                        dayOfTheYear.parse("2000-01-01")
                )
        );
    }

    @Test
    public void testIsSameMonth() throws Exception {
        Date referenceDate = dayOfTheYear.parse("2016-07-29");

        //Same month case
        assertEquals(
                "Fail to validate same month true case",
                true,
                DateTimeUtils.isSameDay(
                        referenceDate,
                        dayOfTheYear.parse("2016-07-11")
                )
        );

        //Testing null cases
        assertEquals(
                "Fail to validate null cases",
                false,
                DateTimeUtils.isSameDay(
                        referenceDate,
                        null
                )
        );

        //Testing null cases
        assertEquals(
                "Fail to validate null cases",
                false,
                DateTimeUtils.isSameDay(
                        null,
                        referenceDate
                )
        );

        //Non-same month case
        assertEquals(
                "Fail to validate same month false case",
                false,
                DateTimeUtils.isSameDay(
                        referenceDate,
                        dayOfTheYear.parse("2000-01-01")
                )
        );
    }

    @Test
    public void testIsSameYear() throws Exception {
        Date referenceDate = dayOfTheYear.parse("2016-07-29");

        //Same year case
        assertEquals(
                "Fail to validate same year true case",
                true,
                DateTimeUtils.isSameDay(
                        referenceDate,
                        dayOfTheYear.parse("2016-02-30")
                )
        );

        //Testing null cases
        assertEquals(
                "Fail to validate null cases",
                false,
                DateTimeUtils.isSameDay(
                        referenceDate,
                        null
                )
        );

        //Testing null cases
        assertEquals(
                "Fail to validate null cases",
                false,
                DateTimeUtils.isSameDay(
                        null,
                        referenceDate
                )
        );

        //Non-same year case
        assertEquals(
                "Fail to validate same year false case",
                false,
                DateTimeUtils.isSameDay(
                        referenceDate,
                        dayOfTheYear.parse("2000-01-01")
                )
        );
    }

    @Test
    public void testIsDateWithinDates() throws Exception {
        Date start = dayOfTheYear.parse("1999-11-22");
        Date end = dayOfTheYear.parse("1999-11-25");

        assertEquals(
                "Normal case within doesn't seem to work",
                true,
                DateTimeUtils.isDateWithinDates(
                        dayOfTheYear.parse("1999-11-23"),
                        start,
                        end
                )
        );

        assertEquals(
                "Normal case before start doesn't seem to work",
                false,
                DateTimeUtils.isDateWithinDates(
                        dayOfTheYear.parse("1999-11-21"),
                        start,
                        end
                )
        );

        assertEquals(
                "Normal case after end doesn't seem to work",
                false,
                DateTimeUtils.isDateWithinDates(
                        dayOfTheYear.parse("1999-11-28"),
                        start,
                        end
                )
        );

        assertEquals(
                "No start limit doesn't seem to work",
                true,
                DateTimeUtils.isDateWithinDates(
                        dayOfTheYear.parse("1999-11-21"),
                        null,
                        end
                )
        );

        assertEquals(
                "No start limit after end date doesn't seem to work",
                false,
                DateTimeUtils.isDateWithinDates(
                        dayOfTheYear.parse("1999-11-29"),
                        null,
                        end
                )
        );

        assertEquals(
                "No end limit doesn't seem to work",
                true,
                DateTimeUtils.isDateWithinDates(
                        dayOfTheYear.parse("1999-11-29"),
                        start,
                        null
                )
        );

        assertEquals(
                "No end limit before end start doesn't seem to work",
                false,
                DateTimeUtils.isDateWithinDates(
                        dayOfTheYear.parse("1999-11-21"),
                        start,
                        end
                )
        );
    }

    @Test
    public void testGetYear() throws Exception {
        Date date = dayOfTheYear.parse("1999-11-22");
        assertEquals(
                "Unexpected Year Value",
                1999,
                DateTimeUtils.getDay(date)
        );
    }

    @Test
    public void testGetMonth() throws Exception {
        Date date = dayOfTheYear.parse("1999-11-22");
        assertEquals(
                "Unexpected Month Value",
                10,
                DateTimeUtils.getMonth(date)
        );
    }

    @Test
    public void testGetDay() throws Exception {
        Date date = dayOfTheYear.parse("1999-11-22");
        assertEquals(
                "Unexpected Day Value",
                22,
                DateTimeUtils.getDay(date)
        );
    }

    @Test
    public void testGetHourOfTheDay() throws Exception {
        Date date = new Date(946735871001L);
        assertEquals(
                "Unexpected Hour Value",
                22,
                DateTimeUtils.getHourOfTheDay(date)
        );
    }

    @Test
    public void testGetFirstDayOfWeek() throws Exception {
        Date date = dayOfTheYear.parse("1999-11-22");
        assertEquals(
                "Unexpected First Day of The Week",
                1,
                DateTimeUtils.getFirstDayOfWeek(date)
        );
    }
}
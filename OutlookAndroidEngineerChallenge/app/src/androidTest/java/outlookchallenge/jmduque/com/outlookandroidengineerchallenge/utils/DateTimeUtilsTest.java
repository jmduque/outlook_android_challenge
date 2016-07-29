package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.utils;

import junit.framework.Assert;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ApplicationTest;

/**
 * Created by Jose on 7/29/2016.
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

        //SAME DAY CASE
        Assert.assertEquals(
                "Fail to validate same day true case",
                true,
                DateTimeUtils.isSameDay(
                        referenceDate,
                        referenceDate
                )
        );

        //TESTING NULL CASES
        Assert.assertEquals(
                "Fail to validate null cases",
                false,
                DateTimeUtils.isSameDay(
                        referenceDate,
                        null
                )
        );

        //TESTING NULL CASES
        Assert.assertEquals(
                "Fail to validate null cases",
                false,
                DateTimeUtils.isSameDay(
                        null,
                        referenceDate
                )
        );

        //DIFFERENT DAY CASE
        Assert.assertEquals(
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

        //NEXT DAY CASE
        Assert.assertEquals(
                "Fail to validate next day true case",
                true,
                DateTimeUtils.isSameDay(
                        referenceDate,
                        dayOfTheYear.parse("2016-07-30")
                )
        );

        //TESTING NULL CASES
        Assert.assertEquals(
                "Fail to validate null cases",
                false,
                DateTimeUtils.isSameDay(
                        referenceDate,
                        null
                )
        );

        //TESTING NULL CASES
        Assert.assertEquals(
                "Fail to validate null cases",
                false,
                DateTimeUtils.isSameDay(
                        null,
                        referenceDate
                )
        );

        //NOT NEXT DAY CASE
        Assert.assertEquals(
                "Fail to validate next day false case",
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

        //SAME YEAR CASE
        Assert.assertEquals(
                "Fail to validate same year true case",
                true,
                DateTimeUtils.isSameDay(
                        referenceDate,
                        dayOfTheYear.parse("2016-02-30")
                )
        );

        //TESTING NULL CASES
        Assert.assertEquals(
                "Fail to validate null cases",
                false,
                DateTimeUtils.isSameDay(
                        referenceDate,
                        null
                )
        );

        //TESTING NULL CASES
        Assert.assertEquals(
                "Fail to validate null cases",
                false,
                DateTimeUtils.isSameDay(
                        null,
                        referenceDate
                )
        );

        //SAME YEAR FALSE CASE
        Assert.assertEquals(
                "Fail to validate same year false case",
                false,
                DateTimeUtils.isSameDay(
                        referenceDate,
                        dayOfTheYear.parse("2000-01-01")
                )
        );
    }
}
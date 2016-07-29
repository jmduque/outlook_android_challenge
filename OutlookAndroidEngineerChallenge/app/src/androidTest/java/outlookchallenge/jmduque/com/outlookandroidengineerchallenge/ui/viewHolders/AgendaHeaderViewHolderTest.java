package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders;

import android.view.LayoutInflater;

import junit.framework.Assert;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ApplicationTest;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.R;

/**
 * Created by Jose on 7/29/2016.
 * Tests the AgendaHeaderViewHolder.class
 */
public class AgendaHeaderViewHolderTest
        extends
        ApplicationTest {

    private SimpleDateFormat dailyFormat = new SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
    );

    @Test
    public void testGetDateText() throws Exception {

    }

    @Test
    public void testGetDayDateText() throws Exception {
        AgendaHeaderViewHolder view = new AgendaHeaderViewHolder(
                LayoutInflater.from(
                        getContext()
                ).inflate(
                        R.layout.item_agenda_header,
                        null
                )
        );

        //TODAY FRIDAY JULY 1st
        Date today = dailyFormat.parse("2016-07-01");
        Assert.assertEquals(
                "Failed to assert today's text generation",
                "Today - Friday, July 01",
                view.getDayDateText(
                        today,
                        today
                )
        );

        //TOMORROW SATURDAY JULY 2nd
        Date tomorrow = dailyFormat.parse("2016-07-02");
        Assert.assertEquals(
                "Failed to assert tomorrow's text generation",
                "Tomorrow - Saturday, July 02",
                view.getDayDateText(
                        tomorrow,
                        today
                )
        );

        //THURSDAY MARCH 12th
        Date otherDayThisYear = dailyFormat.parse("2016-03-12");
        Assert.assertEquals(
                "Failed to assert other day's of this year text generation",
                "Saturday, March 12",
                view.getDayDateText(
                        otherDayThisYear,
                        today
                )
        );

        //SUNDAY MARCH 12th 2017
        Date otherDayOtherYear = dailyFormat.parse("2017-03-12");
        Assert.assertEquals(
                "Failed to assert today's text generation",
                "Sunday, March 12 2017",
                view.getDayDateText(
                        otherDayOtherYear,
                        today
                )
        );
    }

    @Test
    public void testGetWeeklyDateText() throws Exception {

    }

    @Test
    public void testGetMonthlyDateText() throws Exception {

    }

    @Test
    public void testGetYearlyDateText() throws Exception {

    }
}
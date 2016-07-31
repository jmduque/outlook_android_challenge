package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jose on 7/31/2016.
 */
public class CalendarDay
        implements
        Serializable {

    private int year;
    private int month;
    private int day;
    private Date date;
    private boolean isOverflowDay;

    public CalendarDay() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isOverflowDay() {
        return isOverflowDay;
    }

    public void setOverflowDay(boolean overflowDay) {
        isOverflowDay = overflowDay;
    }
}

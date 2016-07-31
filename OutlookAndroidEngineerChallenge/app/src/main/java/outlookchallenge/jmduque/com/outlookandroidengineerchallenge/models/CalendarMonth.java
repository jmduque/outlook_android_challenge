package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Jose on 7/30/2016.
 */
public class CalendarMonth
        implements
        Serializable {

    private int year;
    private int month; //0 Based;
    private String monthName;
    private Date firstDayOfTheMonth;
    private int firstWeekDayOfTheMonth;
    private Date lastDayOfTheMonth;
    private List<CalendarDay> days;

    public CalendarMonth() {
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

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public Date getFirstDayOfTheMonth() {
        return firstDayOfTheMonth;
    }

    public void setFirstDayOfTheMonth(Date firstDayOfTheMonth) {
        this.firstDayOfTheMonth = firstDayOfTheMonth;
    }

    public int getFirstWeekDayOfTheMonth() {
        return firstWeekDayOfTheMonth;
    }

    public void setFirstWeekDayOfTheMonth(int firstWeekDayOfTheMonth) {
        this.firstWeekDayOfTheMonth = firstWeekDayOfTheMonth;
    }

    public Date getLastDayOfTheMonth() {
        return lastDayOfTheMonth;
    }

    public void setLastDayOfTheMonth(Date lastDayOfTheMonth) {
        this.lastDayOfTheMonth = lastDayOfTheMonth;
    }

    public List<CalendarDay> getDays() {
        return days;
    }

    public void setDays(List<CalendarDay> days) {
        this.days = days;
    }
}

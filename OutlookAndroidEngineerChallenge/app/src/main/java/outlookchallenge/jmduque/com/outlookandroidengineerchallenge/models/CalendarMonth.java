package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models;

import java.io.Serializable;

/**
 * Created by Jose on 7/30/2016.
 */
public class CalendarMonth
        implements
        Serializable {

    private int year;
    private int month; //0 Based;
    private int selectedDay;

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

    public int getSelectedDay() {
        return selectedDay;
    }

    public void setSelectedDay(int selectedDay) {
        this.selectedDay = selectedDay;
    }
}

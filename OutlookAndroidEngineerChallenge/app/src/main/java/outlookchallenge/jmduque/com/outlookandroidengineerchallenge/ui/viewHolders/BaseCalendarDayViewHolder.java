package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders;

import android.view.View;

import java.util.Date;

import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.CalendarDay;

/**
 * Created by Jose on 7/28/2016.
 */
public abstract class BaseCalendarDayViewHolder
        extends
        BaseViewHolder {

    private CalendarDay calendarDay;

    public BaseCalendarDayViewHolder(
            View itemView
    ) {
        super(itemView);
    }

    public CalendarDay getCalendarDay() {
        return calendarDay;
    }

    public void setCalendarDay(CalendarDay calendarDay) {
        this.calendarDay = calendarDay;
    }
}

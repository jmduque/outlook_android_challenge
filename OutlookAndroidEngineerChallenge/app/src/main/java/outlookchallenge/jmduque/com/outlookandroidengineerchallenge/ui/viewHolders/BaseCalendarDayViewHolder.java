package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders;

import android.view.View;

import java.util.Date;

/**
 * Created by Jose on 7/28/2016.
 */
public abstract class BaseCalendarDayViewHolder
        extends
        BaseViewHolder {

    protected Date firstDayOfTheMonth;

    public BaseCalendarDayViewHolder(
            View itemView,
            Date firstDayOfTheMonth
    ) {
        super(itemView);
        this.firstDayOfTheMonth = firstDayOfTheMonth;
    }

    public Date getFirstDayOfTheMonth() {
        return firstDayOfTheMonth;
    }

    public void setFirstDayOfTheMonth(Date firstDayOfTheMonth) {
        this.firstDayOfTheMonth = firstDayOfTheMonth;
    }

}

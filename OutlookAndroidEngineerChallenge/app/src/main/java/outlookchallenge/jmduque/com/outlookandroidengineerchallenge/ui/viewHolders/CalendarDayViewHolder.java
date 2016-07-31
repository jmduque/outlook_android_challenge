package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.R;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.CalendarDay;

/**
 * Created by Jose on 7/30/2016.
 * Displays
 */
public class CalendarDayViewHolder
        extends
        BaseCalendarDayViewHolder
        implements
        View.OnClickListener {

    private CalendarDay.DaySelector daySelector;

    private TextView day;
    private CalendarDay calendarDay;
    private int lastUpdateDayValue;

    public CalendarDayViewHolder(
            View itemView,
            CalendarDay.DaySelector daySelector
    ) {
        super(
                itemView
        );
        this.daySelector = daySelector;
    }

    @Override
    public CalendarDay getModel() {
        return calendarDay;
    }

    @Override
    public void setModel(Object object) {
        this.calendarDay = (CalendarDay) object;
    }

    @Override
    protected void bindViews(View itemView) {
        day = (TextView) itemView.findViewById(R.id.day);
    }

    @Override
    protected void setListeners() {
        itemView.setOnClickListener(this);
    }

    @Override
    public void updateView() {
        int day = calendarDay.getDay();
        if (day != lastUpdateDayValue) {
            setDay(
                    calendarDay
            );
        }

        updateHighlight();
        lastUpdateDayValue = day;
    }

    private void setDay(
            CalendarDay calendarDay
    ) {
        if (day == null) {
            return;
        }

        if (calendarDay == null) {
            day.setText(null);
            return;
        }

        Resources res = day.getResources();
        day.setText(
                res.getString(
                        R.string.oaec_calendar_day_of_the_month,
                        calendarDay.getDay()
                )
        );

        //Overflow days are not clickable
        itemView.setEnabled(
                !calendarDay.isOverflowDay()
        );
    }

    private void updateHighlight() {
        Resources res = itemView.getResources();
        boolean isOverflow = calendarDay.isOverflowDay();
        if (isOverflow) {
            itemView.setSelected(false);
            day.setTextColor(
                    res.getColor(android.R.color.tertiary_text_light)
            );
            return;
        }

        if (calendarDay.isHighlighted()) {
            itemView.setSelected(true);
            day.setTextColor(
                    res.getColor(android.R.color.primary_text_dark)
            );
        } else {
            itemView.setSelected(false);
            day.setTextColor(
                    res.getColor(android.R.color.primary_text_light)
            );
        }
    }

    @Override
    public void onClick(View view) {
        if (daySelector == null) {
            return;
        }

        daySelector.onDayPressed(
                calendarDay
        );
    }
}

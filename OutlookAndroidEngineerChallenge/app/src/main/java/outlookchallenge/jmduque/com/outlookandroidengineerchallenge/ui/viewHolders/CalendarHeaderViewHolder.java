package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.R;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.CalendarDay;

/**
 * Created by Jose on 7/30/2016.
 * Displays a header for the calendar view like i.e., "Mon"
 */
public class CalendarHeaderViewHolder
        extends
        BaseCalendarDayViewHolder {

    private TextView day;
    private Integer weekDay; //0 BASED, 0 == SUNDAY

    public CalendarHeaderViewHolder(
            View itemView
    ) {
        super(
                itemView
        );
    }

    @Override
    public Integer getModel() {
        return weekDay;
    }

    @Override
    public void setModel(Object object) {
        this.weekDay = (Integer) object;
    }

    @Override
    protected void bindViews(View itemView) {
        day = (TextView) itemView.findViewById(R.id.day);
    }

    @Override
    protected void setListeners() {
        //No listeners on this view
    }

    @Override
    public void updateView() {
        setDay(weekDay);
    }

    private void setDay(Integer weekDay) {
        if (day == null) {
            return;
        }

        if (weekDay == null) {
            day.setText(null);
            return;
        }

        Resources res = day.getResources();
        if (weekDay == CalendarDay.SUNDAY
                || weekDay == CalendarDay.SATURDAY) {
            day.setTextColor(
                    res.getColor(
                            android.R.color.tertiary_text_light
                    )
            );
        } else {
            day.setTextColor(
                    res.getColor(
                            android.R.color.primary_text_light
                    )
            );
        }

        day.setText(
                getWeekDayText(
                        weekDay
                )
        );
    }

    protected String getWeekDayText(int weekDay) {
        Resources res = itemView.getResources();
        switch (weekDay) {
            case CalendarDay.SUNDAY: {
                return res.getString(R.string.oaec_calendar_sunday);
            }
            case CalendarDay.MONDAY: {
                return res.getString(R.string.oaec_calendar_monday);
            }
            case CalendarDay.TUESDAY: {
                return res.getString(R.string.oaec_calendar_tuesday);
            }
            case CalendarDay.WEDNESDAY: {
                return res.getString(R.string.oaec_calendar_wednesday);
            }
            case CalendarDay.THURSDAY: {
                return res.getString(R.string.oaec_calendar_thursday);
            }
            case CalendarDay.FRIDAY: {
                return res.getString(R.string.oaec_calendar_friday);
            }
            case CalendarDay.SATURDAY: {
                return res.getString(R.string.oaec_calendar_saturday);
            }
        }
        return null;
    }
}

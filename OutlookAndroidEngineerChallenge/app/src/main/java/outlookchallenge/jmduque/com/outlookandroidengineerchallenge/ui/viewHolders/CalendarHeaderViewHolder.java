package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.R;

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
        if (weekDay == 0
                || weekDay == 6) {
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
            case 0: {
                return res.getString(R.string.oaec_calendar_sunday);
            }
            case 1: {
                return res.getString(R.string.oaec_calendar_monday);
            }
            case 2: {
                return res.getString(R.string.oaec_calendar_tuesday);
            }
            case 3: {
                return res.getString(R.string.oaec_calendar_wednesday);
            }
            case 4: {
                return res.getString(R.string.oaec_calendar_thursday);
            }
            case 5: {
                return res.getString(R.string.oaec_calendar_friday);
            }
            case 6: {
                return res.getString(R.string.oaec_calendar_saturday);
            }
        }
        return null;
    }
}

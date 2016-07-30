package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.R;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.utils.DateTimeUtils;

/**
 * Created by Jose on 7/30/2016.
 * Displays
 */
public class CalendarDayViewHolder
        extends
        BaseCalendarDayViewHolder
        implements
        View.OnClickListener {

    public boolean isSameMonth() {
        return isSameMonth;
    }

    public void setSameMonth(boolean sameMonth) {
        isSameMonth = sameMonth;
    }

    public interface DaySelector {

        boolean isHighlightedDate(Date date);

        void onDayPressed(Date date);

    }

    private DaySelector daySelector;

    private TextView day;
    private Date dayValue;
    private boolean isSameMonth;

    private Date lastUpdateDayValue;

    public CalendarDayViewHolder(
            View itemView,
            DaySelector daySelector,
            Date firstDayOfTheMonth
    ) {
        super(
                itemView,
                firstDayOfTheMonth
        );
        this.daySelector = daySelector;
    }

    @Override
    public Date getModel() {
        return dayValue;
    }

    @Override
    public void setModel(Object object) {
        this.dayValue = (Date) object;
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
        if (dayValue != lastUpdateDayValue) {
            setDay(dayValue);
        }

        updateHighlight(dayValue);

        lastUpdateDayValue = dayValue;
    }

    private void setDay(Date dayValue) {
        if (day == null) {
            return;
        }

        if (dayValue == null) {
            day.setText(null);
            return;
        }

        day.setText(
                DateTimeUtils.dayOfTheMonth.format(dayValue)
        );

        itemView.setEnabled(
                isSameMonth
        );
    }

    private void updateHighlight(Date dayValue) {
        Resources res = itemView.getResources();
        if (itemView.isEnabled()) {
            boolean isHighlighted = isHighlighted(dayValue);
            itemView.setSelected(isHighlighted);
            if (isHighlighted) {
                day.setTextColor(
                        res.getColor(android.R.color.primary_text_dark)
                );
            } else {
                day.setTextColor(
                        res.getColor(android.R.color.primary_text_light)
                );
            }
        } else {
            itemView.setSelected(false);
            day.setTextColor(
                    res.getColor(android.R.color.tertiary_text_light)
            );
        }
    }

    /**
     * @return true if this day needs to be highlighted
     */
    private boolean isHighlighted(Date dayValue) {
        if (daySelector == null) {
            return false;
        }

        return daySelector.isHighlightedDate(dayValue);
    }

    @Override
    public void onClick(View view) {
        if (daySelector == null) {
            return;
        }

        daySelector.onDayPressed(dayValue);
    }
}

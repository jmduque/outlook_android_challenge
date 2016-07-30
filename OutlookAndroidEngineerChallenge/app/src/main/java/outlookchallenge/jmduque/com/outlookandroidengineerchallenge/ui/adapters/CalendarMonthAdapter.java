package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.adapters;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.R;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders.BaseCalendarDayViewHolder;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders.CalendarDayViewHolder;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders.CalendarHeaderViewHolder;

/**
 * Created by Jose on DAYS_IN_A_WEEK/30/2016.
 * Tries to display a month with 6 rows of day plus one row of headers.
 */
public class CalendarMonthAdapter
        extends
        RecyclerView.Adapter<BaseCalendarDayViewHolder> {

    public static final int HEADER = 0;
    public static final int DAY = 1;

    public static final int DAYS_IN_A_WEEK = 7;

    private Context context;
    private LayoutInflater layoutInflater;

    private CalendarDayViewHolder.DaySelector daySelector;

    private Date firstDayOfTheMonth;
    private final Calendar calendar = new GregorianCalendar();
    private int firstDayOfTheWeek;

    public CalendarMonthAdapter(
            Context context,
            CalendarDayViewHolder.DaySelector daySelector
    ) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
        this.daySelector = daySelector;
    }

    public RecyclerView.LayoutManager makeLayoutManager() {
        return new GridLayoutManager(
                context,
                DAYS_IN_A_WEEK
        );
    }

    @Override
    public int getItemViewType(int position) {
        //First row is the headers
        if (position < DAYS_IN_A_WEEK) {
            return HEADER;
        }

        return DAY;
    }

    @Override
    public BaseCalendarDayViewHolder onCreateViewHolder(
            ViewGroup parent,
            int viewType
    ) {
        switch (viewType) {
            case HEADER: {
                return new CalendarHeaderViewHolder(
                        layoutInflater.inflate(
                                R.layout.item_calendar_header,
                                parent,
                                false
                        ),
                        firstDayOfTheMonth
                );
            }
            case DAY: {
                return new CalendarDayViewHolder(
                        layoutInflater.inflate(
                                R.layout.item_calendar_day,
                                parent,
                                false
                        ),
                        daySelector,
                        firstDayOfTheMonth
                );
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(
            BaseCalendarDayViewHolder holder,
            int position
    ) {
        switch (getItemViewType(position)) {
            case HEADER: {
                holder.setModel(position);
                break;
            }
            case DAY: {
                holder.setModel(
                        createDateForPosition(position)
                );
                ((CalendarDayViewHolder) holder).setSameMonth(
                        isSameMonth(position)
                );
            }
        }
        holder.setFirstDayOfTheMonth(firstDayOfTheMonth);
        holder.updateView();
    }

    private Date createDateForPosition(int position) {
        if (position < DAYS_IN_A_WEEK) {
            return null;
        }

        long time = firstDayOfTheMonth.getTime();
        //Add day according to position but need to remove the counting by the header's row
        time += DateUtils.DAY_IN_MILLIS * (position - DAYS_IN_A_WEEK);
        //Take into consideration that some months don't start on first day of the week
        time -= DateUtils.DAY_IN_MILLIS * firstDayOfTheWeek;

        return new Date(time);
    }

    private boolean isSameMonth(int position) {
        if (position < firstDayOfTheWeek) {
            return false;
        }

        return true;
    }

    @Override
    public int getItemCount() {
        //6 WEEK ROWS AND 1 HEADER, DAYS_IN_A_WEEK DAYS A WEEK
        return (6 + 1) * DAYS_IN_A_WEEK;
    }

    public void setFirstDayOfTheMonth(Date firstDayOfTheMonth) {
        this.firstDayOfTheMonth = firstDayOfTheMonth;
        calendar.setTime(firstDayOfTheMonth);
        firstDayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK);
    }
}

package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.adapters;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.R;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.controller.CalendarMonthController;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.CalendarDay;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders.BaseCalendarDayViewHolder;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders.CalendarDayViewHolder;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders.CalendarHeaderViewHolder;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.utils.CollectionUtils;

/**
 * Created by Jose on DAYS_IN_A_WEEK/30/2016.
 * Tries to display a month with 6 rows of day plus one row of headers.
 */
public class CalendarMonthAdapter
        extends
        RecyclerView.Adapter<BaseCalendarDayViewHolder> {

    public static final int HEADER = 0;
    public static final int DAY = 1;

    private Context context;
    private LayoutInflater layoutInflater;

    private CalendarDay.DaySelector daySelector;
    private List<CalendarDay> items;

    private final Calendar calendar = new GregorianCalendar();

    public CalendarMonthAdapter(
            Context context,
            CalendarDay.DaySelector daySelector
    ) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
        this.daySelector = daySelector;
    }

    public RecyclerView.LayoutManager makeLayoutManager() {
        return new GridLayoutManager(
                context,
                CalendarMonthController.DAYS_IN_A_WEEK
        );
    }

    @Override
    public int getItemViewType(int position) {
        //First row is the headers
        if (position < CalendarMonthController.DAYS_IN_A_WEEK) {
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
                        )
                );
            }
            case DAY: {
                return new CalendarDayViewHolder(
                        layoutInflater.inflate(
                                R.layout.item_calendar_day,
                                parent,
                                false
                        ),
                        daySelector
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
                        items.get(position - CalendarMonthController.DAYS_IN_A_WEEK)
                );
            }
        }
        holder.updateView();
    }

    @Override
    public int getItemCount() {
        //One row for the headers and then according to the item's list size
        return CalendarMonthController.DAYS_IN_A_WEEK +
                CollectionUtils.size(items);
    }

    public void setItems(List<CalendarDay> items) {
        this.items = items;
    }
}

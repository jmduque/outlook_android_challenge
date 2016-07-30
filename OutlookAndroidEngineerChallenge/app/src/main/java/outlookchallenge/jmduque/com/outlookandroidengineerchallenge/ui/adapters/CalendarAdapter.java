package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.R;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders.CalendarDayViewHolder;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders.CalendarMonthViewHolder;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.utils.DateTimeUtils;

/**
 * Created by Jose on 7/30/2016.
 * This adapter helps navigate trough different months
 */
public class CalendarAdapter
        extends
        RecyclerView.Adapter<CalendarMonthViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private CalendarDayViewHolder.DaySelector daySelector;

    private int startYear;
    private int startMonth; //0 BASE MONTH
    private int totalMonths; //NUMBER OF MONTHS TO DISPLAY

    public CalendarAdapter(
            Context context,
            CalendarDayViewHolder.DaySelector daySelector,
            int startYear,
            int startMonth,
            int totalMonths
    ) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
        this.daySelector = daySelector;
        this.startYear = startYear;
        this.startMonth = startMonth;
        this.totalMonths = totalMonths;
    }

    @Override
    public CalendarMonthViewHolder onCreateViewHolder(
            ViewGroup parent,
            int viewType
    ) {
        return new CalendarMonthViewHolder(
                layoutInflater.inflate(
                        R.layout.item_calendar_month,
                        parent,
                        false
                ),
                daySelector
        );
    }

    @Override
    public void onBindViewHolder(
            CalendarMonthViewHolder holder,
            int position
    ) {
        int month = startMonth + position;
        int year = startYear;
        year += month / 12;
        month = month % 12;

        try {
            holder.setModel(
                    DateTimeUtils.dayOfTheYear.parse(
                            context.getString(
                                    R.string.oaec_calendar_month_first_day,
                                    year,
                                    month
                            )
                    )
            );
        } catch (Exception ignored) {
            holder.setModel(null);
        }

        holder.updateView();
    }

    @Override
    public int getItemCount() {
        return totalMonths;
    }
}

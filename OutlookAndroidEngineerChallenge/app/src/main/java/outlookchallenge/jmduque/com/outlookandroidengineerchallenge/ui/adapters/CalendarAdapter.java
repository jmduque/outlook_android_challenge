package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.R;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.CalendarDay;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.CalendarMonth;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders.CalendarMonthViewHolder;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.utils.CollectionUtils;

/**
 * Created by Jose on 7/30/2016.
 * This adapter helps navigate trough different months
 */
public class CalendarAdapter
        extends
        RecyclerView.Adapter<CalendarMonthViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private CalendarDay.DaySelector daySelector;
    private final List<CalendarMonth> items;

    public CalendarAdapter(
            Context context,
            CalendarDay.DaySelector daySelector,
            List<CalendarMonth> items
    ) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
        this.daySelector = daySelector;
        this.items = items;
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
        //Update holder's model
        holder.setModel(
                getItem(position)
        );
        //Update holder's view
        holder.updateView();
    }

    @Override
    public int getItemCount() {
        return CollectionUtils.size(items);
    }

    public CalendarMonth getItem(int position) {
        if (!CollectionUtils.isValidPosition(
                items,
                position
        )) {
            return null;
        }

        return items.get(position);
    }
}

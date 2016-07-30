package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Date;

import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.R;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.adapters.CalendarMonthAdapter;

/**
 * Created by Jose on 7/30/2016.
 * This <class>ViewHolder</class> tries ty represent a month of the year.
 */
public class CalendarMonthViewHolder
        extends
        BaseViewHolder {

    private CalendarDayViewHolder.DaySelector daySelector;

    private RecyclerView month;
    private CalendarMonthAdapter monthAdapter;

    //First day of the month we want this view to represent
    private Date firstDayOfTheMonth;

    public CalendarMonthViewHolder(
            View itemView,
            CalendarDayViewHolder.DaySelector daySelector
    ) {
        super(itemView);
        this.daySelector = daySelector;
    }

    @Override
    public Date getModel() {
        return firstDayOfTheMonth;
    }

    @Override
    public void setModel(Object object) {
        firstDayOfTheMonth = (Date) object;
    }

    @Override
    protected void bindViews(View itemView) {
        month = (RecyclerView) itemView.findViewById(R.id.month);
    }

    @Override
    protected void setListeners() {
        //No listeners required on this view
    }

    @Override
    public void updateView() {
        if (monthAdapter == null) {
            monthAdapter = new CalendarMonthAdapter(
                    itemView.getContext(),
                    daySelector
            );
            month.setLayoutManager(
                    monthAdapter.makeLayoutManager()
            );
            month.setAdapter(monthAdapter);
        }

        monthAdapter.setFirstDayOfTheMonth(firstDayOfTheMonth);
        monthAdapter.notifyDataSetChanged();
    }
}

package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.R;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.CalendarDay;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.CalendarMonth;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.adapters.CalendarMonthAdapter;

/**
 * Created by Jose on 7/30/2016.
 * This <class>ViewHolder</class> tries ty represent a month of the year.
 */
public class CalendarMonthViewHolder
        extends
        BaseViewHolder {

    private CalendarDay.DaySelector daySelector;

    private RecyclerView month;
    private CalendarMonthAdapter monthAdapter;

    //First day of the month we want this view to represent
    private CalendarMonth calendarMonth;

    public CalendarMonthViewHolder(
            View itemView,
            CalendarDay.DaySelector daySelector
    ) {
        super(itemView);
        this.daySelector = daySelector;
    }

    @Override
    public CalendarMonth getModel() {
        return calendarMonth;
    }

    @Override
    public void setModel(Object object) {
        calendarMonth = (CalendarMonth) object;
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
        monthAdapter.setItems(calendarMonth.getDays());
        monthAdapter.notifyDataSetChanged();
    }
}

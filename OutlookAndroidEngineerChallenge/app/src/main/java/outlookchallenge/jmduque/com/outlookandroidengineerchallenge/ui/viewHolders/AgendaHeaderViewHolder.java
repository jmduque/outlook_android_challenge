package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.Date;

import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.AgendaHeader;

/**
 * Created by Jose on 7/28/2016.
 * This class defines a ViewHolder that displays a header item for the Agenda View
 */
public class AgendaHeaderViewHolder
        extends
        AgendaItemViewHolder<AgendaHeader> {

    public AgendaHeaderViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindViews(View itemView) {
        super.bindViews(itemView);
    }

    @Override
    public void setListeners() {
        super.setListeners();
        //NO ACTIONS FOR THIS VIEW
    }

    @Override
    protected void updateView() {
        super.updateView();
    }

    @Override
    protected void setNameText(String name) {
        //NO NAME TO BE SET
    }

    @Override
    protected void setDateText(Date date) {
        if (name == null) {
            return;
        }

        name.setText(
                getDateText(
                        date,
                        item != null ?
                                item.getAgendaHeaderType() :
                                AgendaHeader.AgendaHeaderType.day
                )
        );
    }

    /**
     * @return text to display for the header's date value
     */
    protected String getDateText(
            @Nullable Date date,
            @NonNull AgendaHeader.AgendaHeaderType type
    ) {
        switch (type) {
            case day:
                return getDayDateText(
                        date
                );
            case week:
                return getWeeklyDateText(
                        date
                );
            case month:
                return getMonthlyDateText(
                        date
                );
            case year:
                return getYearlyDateText(
                        date
                );
        }
        return null;
    }

    /**
     * @return Three cases for daily display
     * <b>Today or Tomorrow:</b> TODAY/TOMORROW - $WEEKDAY, $MONTH $DAY_OF_THE_MONTH
     * <b>Other days of this year:</b> $WEEKDAY, $MONTH $DAY_OF_THE_MONTH
     * <b>Other days:</b> $WEEKDAY, $MONTH $DAY_OF_THE_MONTH $YEAR
     */
    protected String getDayDateText(
            @Nullable Date date
    ) {
        //TODO
        return null;
    }

    /**
     * @return Three cases for weekly display
     * <b>This or next week:</b> THIS WEEK//NEXT WEEK - WEEK $WEEK_OF_THE_MONTH, $MONTH
     * <b>Other Weeks of this Year:</b> WEEK $WEEK_OF_THE_MONTH, $MONTH
     * <b>Other Weeks:</b> WEEK $WEEK_OF_THE_MONTH, $MONTH $YEAR
     */
    protected String getWeeklyDateText(
            @Nullable Date date
    ) {
        //TODO
        return null;
    }

    /**
     * @return Three cases for monthly display
     * <b>This or next Month:</b> This Month//Next Month - $MONTH
     * <b>Other month of the year:</b> $MONTH
     * <b>Other months:</b> $MONTH $YEAR
     */
    protected String getMonthlyDateText(
            @Nullable Date date
    ) {
        //TODO
        return null;
    }

    /**
     * @return Two cases to display
     * <b>This or next year:</b> THIS YEAR//NEXT YEAR - $YEAR
     * <b>Other years:</b> $YEAR
     */
    protected String getYearlyDateText(
            @Nullable Date date
    ) {
        //TODO
        return null;
    }
}

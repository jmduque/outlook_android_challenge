package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.R;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.AgendaEvent;

/**
 * Created by Jose on 7/28/2016.
 * This class defines a ViewHolder that displays a header item for the Agenda View
 */
public class AgendaEventViewHolder
        extends
        AgendaItemViewHolder<AgendaEvent> {

    //VIEWS
    private TextView date;
    private TextView duration;
    private TextView location;

    public AgendaEventViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindViews(View itemView) {
        super.bindViews(this.itemView);
        date = (TextView) itemView.findViewById(R.id.date);
        duration = (TextView) itemView.findViewById(R.id.duration);
        location = (TextView) itemView.findViewById(R.id.location);
    }

    @Override
    public void setListeners() {
        //NO ACTIONS FOR THIS VIEW
    }

    @Override
    protected void updateView() {
        super.updateView();
        if (item == null) {
            setDurationText(
                    null,
                    null
            );
            setLocationText(null);
            return;
        }

        Date date = item.getDate();
        Date endDate = item.getEndTime();

        setDurationText(
                date,
                endDate
        );

        setLocationText(
                item.getLocation()
        );
    }

    @Override
    protected void setDateText(Date date) {
        setDateText(
                date,
                item != null ?
                        item.getEndTime() :
                        null
        );
    }

    private static final SimpleDateFormat timeOfTheDay = new SimpleDateFormat(
            "hh:mm a",
            Locale.getDefault()
    );

    private void setDateText(
            Date date,
            Date endTime
    ) {
        if (this.date == null) {
            return;
        }

        if (date == null) {
            //IF NO DATE AVAILABLE, WE JUST DISPLAY THE DEFAULT DATE
            this.date.setText(R.string.oaec_agenda_event_default_date);
        } else if (endTime == null) {
            //ALL DAY EVENT
            this.date.setText(
                    R.string.oaec_agenda_event_date_all_day
            );
        } else {
            //STARTING HOUR OF THE DAY FOR THE EVENT
            this.date.setText(
                    timeOfTheDay.format(
                            date
                    )
            );
        }
    }

    private void setDurationText(
            Date date,
            Date endDate
    ) {
        if (duration == null) {
            return;
        }

        duration.setText(
                getDurationText(
                        date,
                        endDate
                )
        );
    }

    protected String getDurationText(
            Date date,
            Date endDate
    ) {
        //IF NO END DATE, ASSUME ALL DAY EVENT
        if (endDate == null) {
            return itemView.getContext().getString(
                    R.string.oaec_agenda_event_duration_all_day
            );
        }

        //IF NO DATE, ASSUME START TIME OF THE EVENT AT 00:00 AM
        if (date == null) {
            return timeOfTheDay.format(endDate);
        }

        //OTHERWISE, CALCULATE TIME DIFFERENCE
        long timeLapse = endDate.getTime() - date.getTime();
        Date timeLapseDate = new Date(timeLapse);
        return timeOfTheDay.format(timeLapseDate);
    }

    private void setLocationText(String value) {
        if (location == null) {
            return;
        }

        // WE ONLY DISPLAY LOCATION FOR THOSE EVENTS THAT HAVE ONE
        if (TextUtils.isEmpty(value)) {
            location.setVisibility(View.GONE);
        } else {
            location.setVisibility(View.VISIBLE);
        }

        location.setText(value);
    }
}

package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders;

import android.view.View;

import java.util.Date;

import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.AgendaNoEvent;

/**
 * Created by Jose on 7/28/2016.
 * This class defines a ViewHolder that displays a no-event item for the Agenda View
 */
public class AgendaNoEventViewHolder
        extends
        AgendaItemViewHolder<AgendaNoEvent> {

    public AgendaNoEventViewHolder(View itemView) {
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
        //NO DATA TO UPDATE, ALL EMPTY EVENTS HAVE SAME PRE-POPULATED DEFAULT TEXT
    }

    @Override
    protected void setDateText(Date date) {

    }

    @Override
    public void setNameText(String name) {
        //NO EVENTS ALWAYS HAVE THE SAME DEFAULT TEXT
    }
}

package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.R;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.AgendaEvent;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.AgendaHeader;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.AgendaItem;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.AgendaNoEvent;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders.AgendaHeaderViewHolder;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders.AgendaItemViewHolder;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.utils.CollectionUtils;

/**
 * Created by Jose on 7/28/2016.
 */
public class AgendaAdapter
        extends
        RecyclerView.Adapter<AgendaItemViewHolder> {

    public static final int HEADER = 0;
    public static final int EVENT = 1;
    public static final int NO_EVENT = 2;

    public static final int DEFAULT_VIEW_TYPE = HEADER;

    private Context context;
    private LayoutInflater layoutInflater;

    private List<AgendaItem> items;

    public AgendaAdapter(
            Context context,
            List<AgendaItem> items
    ) {
        super();
        this.context = context;
        this.items = items;

        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (!CollectionUtils.isValidPosition(
                items,
                position
        )) {
            return DEFAULT_VIEW_TYPE;
        }

        AgendaItem item = items.get(position);
        //First we check if view type is a header since is the most expected view type.
        //Then we check if is an empty event, what should be the second most common view type.
        //Finally, we check if it's an event type
        //#Header = #Days
        //#NoEvent = #Days - #DaysWithEvent
        //#Events = #Events
        if (item instanceof AgendaHeader) {
            return HEADER;
        } else if (item instanceof AgendaNoEvent) {
            return NO_EVENT;
        } else if (item instanceof AgendaEvent) {
            return EVENT;
        }

        return DEFAULT_VIEW_TYPE;
    }

    @Override
    public AgendaItemViewHolder onCreateViewHolder(
            ViewGroup parent,
            int viewType
    ) {
        return null;
    }

    @Override
    public void onBindViewHolder(
            AgendaItemViewHolder holder,
            int position
    ) {
        switch (getItemViewType(position)) {
            case HEADER: {
                return new AgendaHeaderViewHolder(
                        layoutInflater.inflate(
                                R.layout.item_agenda_day,
                                null
                        )
                );
            }
            case NO_EVENT: {
                return new AgendaHeaderViewHolder(
                        layoutInflater.inflate(
                                R.layout.item_agenda_day,
                                null
                        )
                );
            }
            case EVENT: {
                return new AgendaHeaderViewHolder(
                        layoutInflater.inflate(
                                R.layout.item_agenda_day,
                                null
                        )
                );
            }
            default:{
                return new AgendaHeaderViewHolder(
                        layoutInflater.inflate(
                                R.layout.item_agenda_day,
                                null
                        )
                );
            }
        }
    }

    @Override
    public int getItemCount() {
        return CollectionUtils.size(items);
    }
}

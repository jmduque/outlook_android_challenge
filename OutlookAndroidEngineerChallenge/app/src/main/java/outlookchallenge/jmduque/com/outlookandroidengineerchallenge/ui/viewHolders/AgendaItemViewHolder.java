package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders;

import android.view.View;
import android.widget.TextView;

import java.util.Date;

import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.R;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.AgendaItem;

/**
 * Created by Jose on 7/28/2016.
 * This class defines a ViewHolder that displays a header item for the Agenda View
 */
public abstract class AgendaItemViewHolder<T extends AgendaItem>
        extends
        BaseViewHolder {

    //VIEWS
    protected TextView name;

    //DATA
    protected T item;

    public AgendaItemViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public T getModel() {
        return item;
    }

    @Override
    public void setModel(Object object) {
        if (!(object instanceof AgendaItem)) {
            return;
        }

        item = (T) object;
    }

    @Override
    public void bindViews(View itemView) {
        name = (TextView) itemView.findViewById(R.id.name);
    }

    @Override
    public void setListeners() {
        //NO ACTIONS FOR THIS VIEW
    }

    @Override
    public void updateView() {
        if (item == null) {
            setNameText(null);
            setDateText(null);
            return;
        }

        setNameText(
                item.getName()
        );

        setDateText(
                item.getDate()
        );
    }

    protected void setNameText(String name) {
        if (this.name == null) {
            return;
        }

        this.name.setText(name);
    }

    protected abstract void setDateText(Date date);
}

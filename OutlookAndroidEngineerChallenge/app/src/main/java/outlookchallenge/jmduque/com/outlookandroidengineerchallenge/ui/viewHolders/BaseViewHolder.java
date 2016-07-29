package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Jose on 7/28/2016.
 */
public abstract class BaseViewHolder
        extends
        RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
        bindViews(itemView);
        setListeners();
    }

    public abstract Object getModel();

    public abstract void setModel(Object object);

    protected abstract void bindViews(View itemView);

    protected abstract void setListeners();

    protected abstract void updateView();

}

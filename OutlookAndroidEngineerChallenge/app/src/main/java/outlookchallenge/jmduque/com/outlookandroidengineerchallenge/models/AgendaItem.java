package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jose on 7/28/2016.
 * Basic class for all Agenda View visible items
 */
public class AgendaItem
        implements
        Serializable {

    private Date date;
    private String name;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

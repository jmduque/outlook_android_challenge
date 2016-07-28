package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models;

import java.util.Date;

/**
 * Created by Jose on 7/28/2016.
 * This class defines an Event to display within the Agenda View.
 */
public class AgendaEvent
        extends
        AgendaItem {

    private String description;
    private Date endTime;
    private String location;

    public AgendaEvent() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * If end time is null -> whole day event
     */
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

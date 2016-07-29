package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models;

/**
 * Created by Jose on 7/28/2016.
 * This class defines a header to be displayed within the Agenda View.
 */
public class AgendaHeader
        extends
        AgendaItem {

    /**
     * Defines the header type.
     * Agendas can have different kind of headers for each kind of time lapse.
     * By default, an AgendaHeader will always be for a day.
     */
    public enum AgendaHeaderType {
        day,
        week,
        month,
        year
    }

    private AgendaHeaderType agendaHeaderType = AgendaHeaderType.day;

    public AgendaHeader() {

    }

    public AgendaHeaderType getAgendaHeaderType() {
        return agendaHeaderType;
    }

    public void setAgendaHeaderType(AgendaHeaderType agendaHeaderType) {
        this.agendaHeaderType = agendaHeaderType;
    }

}

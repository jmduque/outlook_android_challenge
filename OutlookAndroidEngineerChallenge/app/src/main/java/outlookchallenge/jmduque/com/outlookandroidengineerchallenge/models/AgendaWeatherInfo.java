package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models;

import com.johnhiott.darkskyandroidlib.models.DataPoint;

/**
 * Created by Jose on 8/1/2016.
 */
public class AgendaWeatherInfo
        extends
        AgendaItem {

    public enum InfoType {
        morning,
        afternoon,
        evening
    }

    private DataPoint weatherDataPoint;
    private InfoType infoType;

    public AgendaWeatherInfo() {
    }

    public DataPoint getWeatherDataPoint() {
        return weatherDataPoint;
    }

    public void setWeatherDataPoint(DataPoint weatherDataPoint) {
        this.weatherDataPoint = weatherDataPoint;
    }

    public InfoType getInfoType() {
        return infoType;
    }

    public void setInfoType(InfoType infoType) {
        this.infoType = infoType;
    }
}

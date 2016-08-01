package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.controller;

import android.content.Context;

import com.johnhiott.darkskyandroidlib.models.DataPoint;

import java.util.Date;

import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.R;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.AgendaWeatherInfo;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.utils.DateTimeUtils;

/**
 * Created by Jose on 8/2/2016.
 * This class helps with data creation and manipulation related to <class>AgendaWeatherInfo</class>
 */
public class AgendaWeatherInfoController {

    /**
     * Transforms a <class>DataPoint</class> into a <class>AgendaWeatherInfo</class>
     *
     * @return the expected object according to the provided data, null if the
     * point should not be visualized
     */
    public static AgendaWeatherInfo parseDataPoint(
            Context context,
            DataPoint dataPoint
    ) {
        if (context == null) {
            return null;
        }

        if (dataPoint == null) {
            return null;
        }

        //Api Returns time in seconds not milliseconds
        Date date = new Date(
                dataPoint.getTime() * 1000
        );

        int hourOfTheDay = DateTimeUtils.getHourOfTheDay(date);
        AgendaWeatherInfo.InfoType infoType;
        String name;

        //We will use the weather as follows:
        // 10:00 -> Morning
        // 14:00 -> Afternoon
        // 18:00 -> Night
        if (hourOfTheDay == 10) {
            infoType = AgendaWeatherInfo.InfoType.morning;
            name = context.getString(
                    R.string.oaec_agenda_weather_morning_label
            );
        } else if (hourOfTheDay == 14) {
            infoType = AgendaWeatherInfo.InfoType.afternoon;
            name = context.getString(
                    R.string.oaec_agenda_weather_afternoon_label
            );
        } else if (hourOfTheDay == 18) {
            infoType = AgendaWeatherInfo.InfoType.evening;
            name = context.getString(
                    R.string.oaec_agenda_weather_evening_label
            );
        } else {
            //We only handle 3 cases, other hours of the day, not to be displayed
            return null;
        }

        //Create object and set data accordingly
        AgendaWeatherInfo agendaWeatherInfo = new AgendaWeatherInfo();
        agendaWeatherInfo.setDate(date);
        agendaWeatherInfo.setName(name);
        agendaWeatherInfo.setInfoType(infoType);
        agendaWeatherInfo.setWeatherDataPoint(dataPoint);
        return agendaWeatherInfo;
    }

}

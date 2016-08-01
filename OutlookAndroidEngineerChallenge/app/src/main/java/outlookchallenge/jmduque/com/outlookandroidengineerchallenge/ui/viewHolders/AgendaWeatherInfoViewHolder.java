package outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders;

import android.content.res.Resources;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.johnhiott.darkskyandroidlib.models.DataPoint;

import java.util.Date;

import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.R;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.AgendaWeatherInfo;

/**
 * Created by Jose on 7/28/2016.
 * This class defines a ViewHolder that displays the weather information for a time range
 */
public class AgendaWeatherInfoViewHolder
        extends
        AgendaItemViewHolder<AgendaWeatherInfo> {

    private TextView degree;

    public AgendaWeatherInfoViewHolder(
            View itemView
    ) {
        super(itemView);
    }

    @Override
    public void bindViews(
            View itemView
    ) {
        super.bindViews(itemView);
        degree = (TextView) itemView.findViewById(R.id.tv_degree);
    }

    @Override
    public void setListeners() {
        super.setListeners();
        //NO ACTIONS FOR THIS VIEW
    }

    @Override
    public void updateView() {
        super.updateView();
        AgendaWeatherInfo agendaWeatherInfo = getModel();
        setDegree(agendaWeatherInfo);
        setIcon(agendaWeatherInfo);
    }

    @Override
    protected void setNameText(
            String name
    ) {
        this.name.setText(name);
    }

    @Override
    protected void setDateText(
            Date date
    ) {
        //No visual date for weather events
    }

    public void setDegree(AgendaWeatherInfo info) {
        if (degree == null) {
            return;
        }

        if (info == null) {
            degree.setText(null);
            return;
        }

        DataPoint dataPoint = info.getWeatherDataPoint();
        if (dataPoint == null) {
            degree.setText(null);
            return;
        }

        Resources res = itemView.getResources();
        degree.setText(
                res.getString(
                        R.string.oaec_agenda_weather_temperature,
                        dataPoint.getTemperature()
                )
        );
    }

    public void setIcon(AgendaWeatherInfo info) {
        if (degree == null) {
            return;
        }

        if (info == null) {
            setIconDrawable(0);
            return;
        }

        DataPoint dataPoint = info.getWeatherDataPoint();
        if (dataPoint == null) {
            setIconDrawable(0);
            return;
        }

        String icon = dataPoint.getIcon();
        if (TextUtils.isEmpty(icon)) {
            setIconDrawable(0);
            return;
        }

        @DrawableRes int drawableId = 0;
        switch (icon) {
            case "clear-day": {
                drawableId = R.mipmap.ic_clear;
                break;
            }
            case "clear-night": {
                drawableId = R.mipmap.ic_clear_night;
                break;
            }
            case "rain": {
                drawableId = R.mipmap.ic_rain;
                break;
            }
            case "snow": {
                drawableId = R.mipmap.ic_snow;
                break;
            }
            case "sleet": {
                drawableId = R.mipmap.ic_sleet;
                break;
            }
            case "wind": {
                drawableId = R.mipmap.ic_wind;
                break;
            }
            case "fog": {
                drawableId = R.mipmap.ic_cloudy;
                break;
            }
            case "cloudy": {
                drawableId = R.mipmap.ic_cloudy;
                break;
            }
            case "partly-cloudy-day": {
                drawableId = R.mipmap.ic_partly_cloudy;
                break;
            }
            case "partly-cloudy-night": {
                drawableId = R.mipmap.ic_partly_cloudy;
                break;
            }
            default: {
                break;
            }
        }

        setIconDrawable(drawableId);
    }

    /**
     * Sets the icon expected to represent current weather
     */
    private void setIconDrawable(
            @DrawableRes int drawableId
    ) {
        if (degree == null) {
            return;
        }

        //Default icon is no icon
        degree.setCompoundDrawablesWithIntrinsicBounds(
                drawableId,
                0,
                0,
                0
        );
    }
}

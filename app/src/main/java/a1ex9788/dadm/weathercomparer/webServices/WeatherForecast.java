package a1ex9788.dadm.weathercomparer.webServices;

import java.util.List;

import a1ex9788.dadm.weathercomparer.model.DayForecast;
import a1ex9788.dadm.weathercomparer.model.HourForecast;

public abstract class WeatherForecast {

    protected double latitude, longitude;

    public WeatherForecast(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public abstract List<DayForecast> getDailyForecast() throws Exception;

    public abstract List<HourForecast> getHourlyForecast() throws Exception;

}

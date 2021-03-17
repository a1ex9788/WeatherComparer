package a1ex9788.dadm.weathercomparer.webServices;

import a1ex9788.dadm.weathercomparer.model.DailyForecast;
import a1ex9788.dadm.weathercomparer.model.HourlyForecast;

public abstract class WeatherForecast {

    protected double latitude, longitude;

    public WeatherForecast(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public abstract DailyForecast getDailyForecast() throws Exception;

    public abstract HourlyForecast getHourlyForecast() throws Exception;

}

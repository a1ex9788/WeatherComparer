package a1ex9788.dadm.weathercomparer.webServices.accuWeather;

import a1ex9788.dadm.weathercomparer.model.DailyForecast;
import a1ex9788.dadm.weathercomparer.model.HourlyForecast;
import a1ex9788.dadm.weathercomparer.webServices.WeatherForecast;

public class AccuWeatherForecast extends WeatherForecast {

    public AccuWeatherForecast(double latitude, double longitude) {
        super(latitude, longitude);
    }

    @Override
    public DailyForecast getDailyForecast() throws Exception {
        return null;
    }

    @Override
    public HourlyForecast getHourlyForecast() throws Exception {
        return null;
    }

}

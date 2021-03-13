package a1ex9788.dadm.weathercomparer.webServices;

import a1ex9788.dadm.weathercomparer.model.DailyForecast;
import a1ex9788.dadm.weathercomparer.model.HourlyForecast;

public interface WeatherForecast {

    public DailyForecast GetDailyForecast();

    public HourlyForecast GetHourlyForecast();

}

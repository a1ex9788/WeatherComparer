package a1ex9788.dadm.weathercomparer.webServices.forecasts.average;

import java.util.Arrays;

import a1ex9788.dadm.weathercomparer.webServices.forecasts.accuWeather.AccuWeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.openWeather.OpenWeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.weatherBit.WeatherBitForecast;

public class AverageWeatherMockForecast extends AverageWeatherForecast {

    public AverageWeatherMockForecast(double latitude, double longitude) {
        super(latitude, longitude);

        weatherForecasts = Arrays.asList(
                new AccuWeatherForecast(latitude, longitude),
                new OpenWeatherForecast(latitude, longitude),
                new WeatherBitForecast(latitude, longitude));
    }

}

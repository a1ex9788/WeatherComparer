package a1ex9788.dadm.weathercomparer.webServices.forecasts.average;

import java.util.Arrays;

import a1ex9788.dadm.weathercomparer.webServices.forecasts.accuWeather.AccuWeatherMockForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.openWeather.OpenWeatherMockForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.weatherBit.WeatherBitMockForecast;

public class AverageWeatherMockForecast extends AverageWeatherForecast {

    public AverageWeatherMockForecast(double latitude, double longitude) {
        super(latitude, longitude);

        weatherForecasts = Arrays.asList(new AccuWeatherMockForecast(latitude, longitude),
                new OpenWeatherMockForecast(latitude, longitude),
                new WeatherBitMockForecast(latitude, longitude));
    }

}

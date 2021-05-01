package a1ex9788.dadm.weathercomparer.webServices.forecasts;

import a1ex9788.dadm.weathercomparer.webServices.forecasts.accuWeather.AccuWeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.accuWeather.AccuWeatherMockForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.average.AverageWeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.average.AverageWeatherMockForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.openWeather.OpenWeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.openWeather.OpenWeatherMockForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.weatherBit.WeatherBitForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.weatherBit.WeatherBitMockForecast;

// This class is created in order to manage the implementations of the weather forecast. It
// allows to simply use the mocks or not.
public class WeatherForecastCreator {

    private static final boolean USE_MOCKS = true;

    public static AverageWeatherForecast getAverageWeatherForecast(double latitude,
            double longitude) {
        if (USE_MOCKS) {
            return new AverageWeatherMockForecast(latitude, longitude);
        }

        return new AverageWeatherForecast(latitude, longitude);
    }

    public static AccuWeatherForecast getAccuWeatherForecast(double latitude, double longitude) {
        if (USE_MOCKS) {
            return new AccuWeatherMockForecast(latitude, longitude);
        }

        return new AccuWeatherForecast(latitude, longitude);
    }

    public static OpenWeatherForecast getOpenWeatherForecast(double latitude, double longitude) {
        if (USE_MOCKS) {
            return new OpenWeatherMockForecast(latitude, longitude);
        }

        return new OpenWeatherForecast(latitude, longitude);
    }

    public static WeatherBitForecast getWeatherBitForecast(double latitude, double longitude) {
        if (USE_MOCKS) {
            return new WeatherBitMockForecast(latitude, longitude);
        }

        return new WeatherBitForecast(latitude, longitude);
    }

}

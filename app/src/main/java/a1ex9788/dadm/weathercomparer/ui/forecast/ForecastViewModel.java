package a1ex9788.dadm.weathercomparer.ui.forecast;

import androidx.lifecycle.ViewModel;

import java.util.List;

import a1ex9788.dadm.weathercomparer.model.DayForecast;
import a1ex9788.dadm.weathercomparer.model.HourForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.WeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.accuWeather.AccuWeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.average.AverageWeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.openWeather.OpenWeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.weatherBit.WeatherBitForecast;

public class ForecastViewModel extends ViewModel {

    public List<DayForecast> getAverageDailyForecast(double latitude, double longitude) {
        AverageWeatherForecast averageWeatherForecast = new AverageWeatherForecast(latitude, longitude);

        return averageWeatherForecast.getDailyForecast();
    }

    public List<HourForecast> getAverageHourlyForecast(double latitude, double longitude) {
        AverageWeatherForecast averageWeatherForecast = new AverageWeatherForecast(latitude, longitude);

        return averageWeatherForecast.getHourlyForecast();
    }

    public List<DayForecast> getAccuWeatherDailyForecast(double latitude, double longitude) throws Exception {
        WeatherForecast accuWeatherForecast = new AccuWeatherForecast(latitude, longitude);

        return accuWeatherForecast.getDailyForecast();
    }

    public List<HourForecast> getAccuWeatherHourlyForecast(double latitude, double longitude) throws Exception {
        WeatherForecast accuWeatherForecast = new AccuWeatherForecast(latitude, longitude);

        return accuWeatherForecast.getHourlyForecast();
    }

    public List<DayForecast> getOpenWeatherDailyForecast(double latitude, double longitude) throws Exception {
        WeatherForecast openWeatherForecast = new OpenWeatherForecast(latitude, longitude);

        return openWeatherForecast.getDailyForecast();
    }

    public List<HourForecast> getOpenWeatherHourlyForecast(double latitude, double longitude) throws Exception {
        WeatherForecast openWeatherForecast = new OpenWeatherForecast(latitude, longitude);

        return openWeatherForecast.getHourlyForecast();
    }

    public List<DayForecast> getWeatherBitDailyForecast(double latitude, double longitude) throws Exception {
        WeatherForecast weatherBitForecast = new WeatherBitForecast(latitude, longitude);

        return weatherBitForecast.getDailyForecast();
    }

    public List<HourForecast> getWeatherBitHourlyForecast(double latitude, double longitude) throws Exception {
        WeatherForecast weatherBitForecast = new WeatherBitForecast(latitude, longitude);

        return weatherBitForecast.getHourlyForecast();
    }

}
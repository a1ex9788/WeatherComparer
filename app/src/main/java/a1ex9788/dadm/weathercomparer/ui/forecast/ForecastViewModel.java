package a1ex9788.dadm.weathercomparer.ui.forecast;

import androidx.lifecycle.ViewModel;

import java.util.List;

import a1ex9788.dadm.weathercomparer.model.DayForecast;
import a1ex9788.dadm.weathercomparer.model.HourForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.WeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.accuWeather.AccuWeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.openWeather.OpenWeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.weatherBit.WeatherBitForecast;

public class ForecastViewModel extends ViewModel {

    WeatherForecast accuWeatherForecast = new AccuWeatherForecast(39.289, -0.799);
    WeatherForecast openWeatherForecast = new OpenWeatherForecast(39.289, -0.799);
    WeatherForecast weatherBitForecast = new WeatherBitForecast(39.289, -0.799);

    public List<DayForecast> getAverageDailyForecast() {
        return null;
    }

    public List<HourForecast> getAverageHourlyForecast() {
        return null;
    }

    public List<DayForecast> getAccuWeatherDailyForecast() throws Exception {
        return accuWeatherForecast.getDailyForecast();
    }

    public List<HourForecast> getAccuWeatherHourlyForecast() throws Exception {
        return accuWeatherForecast.getHourlyForecast();
    }

    public List<DayForecast> getOpenWeatherDailyForecast() throws Exception {
        return openWeatherForecast.getDailyForecast();
    }

    public List<HourForecast> getOpenWeatherHourlyForecast() throws Exception {
        return openWeatherForecast.getHourlyForecast();
    }

    public List<DayForecast> getWeatherBitDailyForecast() throws Exception {
        return weatherBitForecast.getDailyForecast();
    }

    public List<HourForecast> getWeatherBitHourlyForecast() throws Exception {
        return weatherBitForecast.getHourlyForecast();
    }

}
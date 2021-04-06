package a1ex9788.dadm.weathercomparer.ui.forecast;

import androidx.lifecycle.ViewModel;

import java.util.List;

import a1ex9788.dadm.weathercomparer.model.DayForecast;
import a1ex9788.dadm.weathercomparer.model.HourForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.AverageForecastCalculator;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.WeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.accuWeather.AccuWeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.openWeather.OpenWeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.weatherBit.WeatherBitForecast;

public class ForecastViewModel extends ViewModel {

    private final double LAT = 39.289, LONG = -0.799;

    private WeatherForecast accuWeatherForecast = new AccuWeatherForecast(LAT, LONG);
    private WeatherForecast openWeatherForecast = new OpenWeatherForecast(LAT, LONG);
    private WeatherForecast weatherBitForecast = new WeatherBitForecast(LAT, LONG);

    private AverageForecastCalculator averageForecastCalculator = new AverageForecastCalculator(LAT, LONG);

    public List<DayForecast> getAverageDailyForecast() throws Exception {
        return averageForecastCalculator.getAverageDailyForecast();
    }

    public List<HourForecast> getAverageHourlyForecast() throws Exception {
        return averageForecastCalculator.getAverageHourlyForecast();
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
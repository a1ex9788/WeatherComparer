package a1ex9788.dadm.weathercomparer.ui.forecast;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import a1ex9788.dadm.weathercomparer.model.DayForecast;
import a1ex9788.dadm.weathercomparer.model.HourForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.WeatherForecastCreator;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.accuWeather.AccuWeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.average.AverageWeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.openWeather.OpenWeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.weatherBit.WeatherBitForecast;
import a1ex9788.dadm.weathercomparer.webServices.places.GooglePlaces;

public class ForecastViewModel extends ViewModel {

    public HourForecast getCurrentWeather(double latitude, double longitude) {
        return getAverageHourlyForecast(latitude, longitude).get(0);
    }

    public List<DayForecast> getAverageDailyForecast(double latitude, double longitude) {
        AverageWeatherForecast averageWeatherForecast = WeatherForecastCreator.getAverageWeatherForecast(latitude, longitude);

        return averageWeatherForecast.getDailyForecast();
    }

    public List<HourForecast> getAverageHourlyForecast(double latitude, double longitude) {
        AverageWeatherForecast averageWeatherForecast = WeatherForecastCreator.getAverageWeatherForecast(latitude, longitude);

        return averageWeatherForecast.getHourlyForecast();
    }

    public List<DayForecast> getAccuWeatherDailyForecast(double latitude, double longitude) throws Exception {
        AccuWeatherForecast accuWeatherForecast = WeatherForecastCreator.getAccuWeatherForecast(latitude, longitude);

        return accuWeatherForecast.getDailyForecast();
    }

    public List<HourForecast> getAccuWeatherHourlyForecast(double latitude, double longitude) throws Exception {
        AccuWeatherForecast accuWeatherForecast = WeatherForecastCreator.getAccuWeatherForecast(latitude, longitude);

        return accuWeatherForecast.getHourlyForecast();
    }

    public List<DayForecast> getOpenWeatherDailyForecast(double latitude, double longitude) throws Exception {
        OpenWeatherForecast openWeatherForecast = WeatherForecastCreator.getOpenWeatherForecast(latitude, longitude);

        return openWeatherForecast.getDailyForecast();
    }

    public List<HourForecast> getOpenWeatherHourlyForecast(double latitude, double longitude) throws Exception {
        OpenWeatherForecast openWeatherForecast = WeatherForecastCreator.getOpenWeatherForecast(latitude, longitude);

        return openWeatherForecast.getHourlyForecast();
    }

    public List<DayForecast> getWeatherBitDailyForecast(double latitude, double longitude) throws Exception {
        WeatherBitForecast weatherBitForecast = WeatherForecastCreator.getWeatherBitForecast(latitude, longitude);

        return weatherBitForecast.getDailyForecast();
    }

    public List<HourForecast> getWeatherBitHourlyForecast(double latitude, double longitude) throws Exception {
        WeatherBitForecast weatherBitForecast = WeatherForecastCreator.getWeatherBitForecast(latitude, longitude);

        return weatherBitForecast.getHourlyForecast();
    }

    public void getPlace(Context context, double latitude, double longitude, OnSuccessListener onPlaceFounded) throws Exception {
        GooglePlaces googlePlaces = new GooglePlaces(context);
        String id = googlePlaces.searchLocalityByNearby(latitude, longitude);
        googlePlaces.getDetails(id, onPlaceFounded);
    }

}
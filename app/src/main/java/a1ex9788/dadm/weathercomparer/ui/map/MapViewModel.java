package a1ex9788.dadm.weathercomparer.ui.map;

import androidx.lifecycle.ViewModel;

import a1ex9788.dadm.weathercomparer.model.HourForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.average.AverageWeatherForecast;

public class MapViewModel extends ViewModel {

    public HourForecast getCurrentForecast(double latitude, double longitude) {
        AverageWeatherForecast averageWeatherForecast = new AverageWeatherForecast(latitude, longitude);

        return averageWeatherForecast.getHourlyForecast().get(0);
    }

}
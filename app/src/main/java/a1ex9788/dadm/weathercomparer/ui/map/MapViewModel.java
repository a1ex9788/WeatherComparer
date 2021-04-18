package a1ex9788.dadm.weathercomparer.ui.map;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import a1ex9788.dadm.weathercomparer.db.Room;
import a1ex9788.dadm.weathercomparer.model.HourForecast;
import a1ex9788.dadm.weathercomparer.model.MapPlace;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.average.AverageWeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.places.GooglePlaces;

public class MapViewModel extends ViewModel {

    private GooglePlaces googlePlaces = new GooglePlaces();

    public HourForecast getCurrentForecast(double latitude, double longitude) {
        AverageWeatherForecast averageWeatherForecast = new AverageWeatherForecast(latitude, longitude);

        return averageWeatherForecast.getHourlyForecast().get(0);
    }

    public void savePlace(Context context, MapPlace place) {
        Room.getInstance(context).room().addPlace(place);
    }

    public void deletePlace(Context context, MapPlace place) {
        Room.getInstance(context).room().deletePlace(place);
    }

    public boolean existsPlace(Context context, String id) {
        return Room.getInstance(context).room().searchPlace(id) != null;
    }

    public MapPlace getPlace(double latitude, double longitude) throws Exception {
        return googlePlaces.searchLocalityByNearby(latitude, longitude);
    }

}
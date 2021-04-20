package a1ex9788.dadm.weathercomparer.ui.map;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;

import a1ex9788.dadm.weathercomparer.db.Room;
import a1ex9788.dadm.weathercomparer.model.HourForecast;
import a1ex9788.dadm.weathercomparer.model.MapPlace;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.average.AverageWeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.places.GooglePlaces;

public class MapViewModel extends  ViewModel {

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

    public void getPlace(Context context,double latitude, double longitude, OnSuccessListener onPlaceFounded) throws Exception {
        GooglePlaces googlePlaces = new GooglePlaces(context);
        String id = googlePlaces.searchLocalityByNearby(latitude, longitude);
        googlePlaces.getDetails(id, onPlaceFounded);
    }

}
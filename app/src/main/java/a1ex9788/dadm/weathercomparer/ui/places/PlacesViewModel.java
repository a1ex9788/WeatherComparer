package a1ex9788.dadm.weathercomparer.ui.places;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import java.util.List;

import a1ex9788.dadm.weathercomparer.db.Room;
import a1ex9788.dadm.weathercomparer.model.MapPlace;

public class PlacesViewModel extends ViewModel {

    public List<MapPlace> getPlaces(Context context) {
        return Room.getInstance(context).room().getPlaces();
    }

    public void deletePlace(Context context, MapPlace place) {
        Room.getInstance(context).room().deletePlace(place);
    }

}
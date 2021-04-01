package a1ex9788.dadm.weathercomparer.ui.places;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import a1ex9788.dadm.weathercomparer.db.Room;

import a1ex9788.dadm.weathercomparer.R;
import a1ex9788.dadm.weathercomparer.db.RoomDao;
import a1ex9788.dadm.weathercomparer.ui.map.MapPlace;

public class PlacesFragment extends Fragment {

    RoomDao db;
    List<MapPlace> places;
    public static String PLACES_TAG = "PLACES";

    private PlacesViewModel placesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        placesViewModel =
                new ViewModelProvider(this).get(PlacesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_places, container, false);

        db = Room.getInstance(getContext()).room();

        new Thread(new Runnable() {
            @Override
            public void run() {
                places = db.getPlaces();
                Log.d(PLACES_TAG,places.toString());
            }
        }).start();

        return root;
    }

}
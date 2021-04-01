package a1ex9788.dadm.weathercomparer.ui.places;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import a1ex9788.dadm.weathercomparer.R;

public class PlacesFragment extends Fragment {

    private PlacesViewModel placesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        placesViewModel =
                new ViewModelProvider(this).get(PlacesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_places, container, false);

        return root;
    }

}
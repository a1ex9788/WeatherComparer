package a1ex9788.dadm.weathercomparer.ui.map;

import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import a1ex9788.dadm.weathercomparer.MainActivity;
import a1ex9788.dadm.weathercomparer.R;
import a1ex9788.dadm.weathercomparer.databinding.FragmentMapBinding;
import a1ex9788.dadm.weathercomparer.db.Room;
import a1ex9788.dadm.weathercomparer.db.RoomDao;
import a1ex9788.dadm.weathercomparer.model.HourForecast;
import a1ex9788.dadm.weathercomparer.webServices.ApiKeys;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.openWeather.OpenWeatherForecast;

public class MapFragment extends Fragment {

    public static String MAP_TAG = "map";
    private GoogleMap map;
    AutocompleteSupportFragment autocompleteFragment;
    FragmentMapBinding binding;
    OpenWeatherForecast forecastService;
    RoomDao db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        ((MainActivity) getActivity()).getSupportActionBar().hide();

        supportMapFragment.getMapAsync(onMapReady(container));

        db = Room.getInstance(getContext()).room();

        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPlace();
            }
        });

        return root;
    }

    @NotNull
    private OnMapReadyCallback onMapReady(ViewGroup container) {
        return googleMap -> {
            map = googleMap;

            map.setOnCameraMoveStartedListener(latLng -> {
                resetInfo(container);
            });

            setupAutocomplete(container);
        };
    }

    private void setupAutocomplete(ViewGroup container) {
        autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.f_autocomplete);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG, Place.Field.PHOTO_METADATAS,Place.Field.UTC_OFFSET));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place placeFounded) {
                MapPlace place = new MapPlace(placeFounded);
                binding.setLoading(true);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(place.getLat(),place.getLng()),11);
                map.animateCamera(cameraUpdate);

                forecastService = new OpenWeatherForecast(place.getLat(),place.getLng());
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    HourForecast forecast = forecastService.getHourlyForecast().get(0);
                                    binding.setForecast(forecast);
                                }
                                catch (Exception error){
                                    Log.d(MAP_TAG,error.getMessage());
                                }
                            }
                        }
                ).start();


                binding.setPlace(place);
                loadPhoto(container);

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(MAP_TAG, "An error occurred: " + status);
            }
        });

        autocompleteFragment.getView().findViewById(R.id.places_autocomplete_clear_button).setOnClickListener(view -> {
            resetInfo(container);
        });

        ImageView ivSearchIcon = (ImageView)autocompleteFragment.getView().findViewById(R.id.places_autocomplete_search_button);

        ivSearchIcon.setImageIcon(Icon.createWithResource(getContext(),R.drawable.ic_menu));
        ivSearchIcon.setOnClickListener(view -> {
            ((DrawerLayout)getActivity().findViewById(R.id.nd_layout)).openDrawer(GravityCompat.START);
        });
    }



    void resetInfo(ViewGroup container) {
        Transition transition = new Fade();
        transition.setDuration(600);
        transition.addTarget(R.id.rlPlace);

        TransitionManager.beginDelayedTransition(container, transition);
        ((EditText) autocompleteFragment.getView().findViewById(R.id.places_autocomplete_search_input)).setText(null);
        autocompleteFragment.getView().findViewById(R.id.places_autocomplete_clear_button).setVisibility(View.INVISIBLE);
        binding.setPlace(null);
    }

    private void loadPhoto(ViewGroup container) {
        Picasso.get().load("https://maps.googleapis.com/maps/api/place/photo?key=AIzaSyAiKQz6mYGVdAYfIWDxkTiOIa0x86e2ntA&maxheight=200&photoreference=" + binding.getPlace().getPhoto()).into((ImageView) container.findViewById(R.id.civ_place), new Callback() {
            @Override
            public void onSuccess() {
                binding.setLoading(false);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    public void addPlace(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.addPlace(binding.getPlace());
            }
        }).start();
    }

}
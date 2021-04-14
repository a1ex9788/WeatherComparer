package a1ex9788.dadm.weathercomparer.ui.map;

import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import a1ex9788.dadm.weathercomparer.MainActivity;
import a1ex9788.dadm.weathercomparer.R;
import a1ex9788.dadm.weathercomparer.databinding.FragmentMapBinding;
import a1ex9788.dadm.weathercomparer.databinding.PlaceViewBinding;
import a1ex9788.dadm.weathercomparer.db.Room;
import a1ex9788.dadm.weathercomparer.db.RoomDao;
import a1ex9788.dadm.weathercomparer.model.HourForecast;
import a1ex9788.dadm.weathercomparer.model.MapPlace;
import a1ex9788.dadm.weathercomparer.webServices.PlacesService;

public class MapFragment extends Fragment {

    public static String MAP_TAG = "map";
    private MapViewModel mapViewModel;
    private GoogleMap map;
    private AutocompleteSupportFragment autocompleteFragment;
    private FragmentMapBinding binding;
    private PlaceViewBinding placeBinding;
    private RoomDao db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setNavigationDrawerCheckedItem();

        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        ((MainActivity) getActivity()).getSupportActionBar().hide();

        supportMapFragment.getMapAsync(onMapReady(container));

        db = Room.getInstance(getContext()).room();

        binding.fabAdd.setOnClickListener(view -> {
            Log.d(MAP_TAG, String.valueOf(binding.getAlreadyAdded()));
            if (binding.getAlreadyAdded()) {
                deletePlace();
                binding.setAlreadyAdded(false);
            } else {
                addPlace();
                binding.setAlreadyAdded(true);
            }
        });

        binding.fabForecast.setOnClickListener(view -> {
            //TODO
        });

        ConstraintLayout constraintLayout = root.findViewById(R.id.clMapLayout);
        placeBinding = PlaceViewBinding.inflate(inflater, constraintLayout, true);

        placeBinding.cvPlace.setOnClickListener(view -> {
            if (binding.llTools.getVisibility() == View.VISIBLE) {
                animateFabOut();
                binding.llTools.setVisibility(View.INVISIBLE);
            } else {
                animateToolsIn();
                binding.llTools.setVisibility(View.VISIBLE);
            }
        });

        return root;
    }

    private void setNavigationDrawerCheckedItem() {
        for(int i = 0; i < 4; i++){
            MenuItem item = ((MainActivity) requireActivity()).getNavigationDrawer().getMenu().getItem(i);
            if(i == 2){
                item.setChecked(true);
            }
            else {
                item.setChecked(false);
            }
        }
    }

    @NotNull
    private OnMapReadyCallback onMapReady(ViewGroup container) {
        return googleMap -> {
            map = googleMap;

            map.setOnCameraMoveStartedListener(latLng -> {
                if (placeBinding.getPlace() != null) {
                    animatePlaceCardOut();
                    if (binding.llTools.getVisibility() == View.VISIBLE) {
                        animateFabOut();
                    }
                    resetInfo();
                }
            });

            map.setOnMapClickListener(latLng -> new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                new PlacesService().searchLocalityByNerby(latLng);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
            ).start());

            setupAutocomplete();
        };
    }

    private void setupAutocomplete() {
        autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.f_autocomplete);

        autocompleteFragment.setTypeFilter(TypeFilter.REGIONS);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.PHOTO_METADATAS, Place.Field.UTC_OFFSET));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place placeFounded) {
                MapPlace place = new MapPlace(placeFounded);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(place.getLat(), place.getLng()), 11);
                map.animateCamera(cameraUpdate);

                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    HourForecast currentForecast = mapViewModel.getCurrentForecast(place.getLat(), place.getLng());
                                    Log.d("forecast",currentForecast.toString());
                                    placeBinding.setForecast(currentForecast);
                                    animatePlaceCardIn();
                                } catch (Exception error) {
                                    Log.d(MAP_TAG, error.getMessage());
                                }
                            }
                        }
                ).start();

                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                boolean exists = db.searchPlace(place.getId()) != null;
                                binding.setAlreadyAdded(exists);
                                Log.d(MAP_TAG, String.valueOf(exists));
                            }
                        }
                ).start();

                placeBinding.setPlace(place);
                if (place.getPhoto() != null) {
                    Picasso.get()
                            .load(place.getPhoto())
                            .into(placeBinding.ivPlace);
                }
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(MAP_TAG, "An error occurred: " + status);
            }
        });

        autocompleteFragment.getView().findViewById(R.id.places_autocomplete_clear_button).setOnClickListener(view -> {
            animatePlaceCardOut();
            if (binding.llTools.getVisibility() == View.VISIBLE) {
                animateFabOut();
            }
            resetInfo();
        });

        ImageView ivSearchIcon = autocompleteFragment.getView().findViewById(R.id.places_autocomplete_search_button);

        ivSearchIcon.setImageIcon(Icon.createWithResource(getContext(), R.drawable.ic_menu));
        ivSearchIcon.setOnClickListener(view -> {
            ((DrawerLayout) getActivity().findViewById(R.id.nd_layout)).openDrawer(GravityCompat.START);
        });

        if (getArguments() != null && getArguments().containsKey("search")) {
            autocompleteFragment.getView().post(new Runnable() {
                @Override
                public void run() {
                    autocompleteFragment.getView().findViewById(R.id.places_autocomplete_search_input)
                            .performClick();
                }
            });
        }
    }

    void animatePlaceCardIn() {
        placeBinding.cvPlace.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
    }

    void animatePlaceCardOut() {
        placeBinding.cvPlace.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out));
    }

    void animateToolsIn() {
        binding.llTools.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.zoom_in));
    }

    void animateFabOut() {
        binding.llTools.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.zoom_out));
    }

    void resetInfo() {
        ((EditText) autocompleteFragment.getView().findViewById(R.id.places_autocomplete_search_input)).setText(null);
        autocompleteFragment.getView().findViewById(R.id.places_autocomplete_clear_button).setVisibility(View.INVISIBLE);
        binding.llTools.setVisibility(View.INVISIBLE);
        placeBinding.setPlace(null);
    }

    public void addPlace() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.addPlace(placeBinding.getPlace());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), placeBinding.getPlace().getName() + " " + getString(R.string.tAdd), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }

    public void deletePlace() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.deletePlace(placeBinding.getPlace());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), placeBinding.getPlace().getName() + " " + getString(R.string.tDelete), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }

}
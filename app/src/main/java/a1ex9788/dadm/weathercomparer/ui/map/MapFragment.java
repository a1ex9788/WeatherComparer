package a1ex9788.dadm.weathercomparer.ui.map;

import android.database.Observable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Arrays;

import a1ex9788.dadm.weathercomparer.MainActivity;
import a1ex9788.dadm.weathercomparer.R;
import a1ex9788.dadm.weathercomparer.databinding.FragmentMapBinding;
import a1ex9788.dadm.weathercomparer.webServices.ApiKeys;


public class MapFragment extends Fragment{
    public static String MAP_TAG = "map";
    private MapViewModel mapViewModel;
    private GoogleMap map;
    MapPlace place;
    AutocompleteSupportFragment autocompleteFragment;
    FragmentMapBinding binding;
    boolean loading = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)  {
        binding = FragmentMapBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        mapViewModel =
                new ViewModelProvider(this).get(MapViewModel.class);


        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        ((MainActivity) getActivity()).getSupportActionBar().hide();



        supportMapFragment.getMapAsync(googleMap -> {
            map = googleMap;
            Log.i(MAP_TAG, map.toString());
            Places.initialize(getContext(), ApiKeys.GOOGLE);
            autocompleteFragment = (AutocompleteSupportFragment)
                    getChildFragmentManager().findFragmentById(R.id.f_autocomplete);

            autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG, Place.Field.PHOTO_METADATAS));

            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place placeFounded) {
                    loading = true;
                    binding.setLoading(loading);
                    Log.i(MAP_TAG, placeFounded.getName() + placeFounded.getLatLng());
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(placeFounded.getLatLng(),11);
                    Log.i(MAP_TAG, cameraUpdate.toString());
                    map.animateCamera(cameraUpdate);
                    place = new MapPlace(placeFounded);
                    Log.d(MAP_TAG,placeFounded.getPhotoMetadatas().get(0).toString());
                    Picasso.get().load("https://maps.googleapis.com/maps/api/place/photo?key=AIzaSyAiKQz6mYGVdAYfIWDxkTiOIa0x86e2ntA&maxheight=200&photoreference=" + place.getPhotos().get(0)).into((ImageView) container.findViewById(R.id.civ_place), new Callback() {
                        @Override
                        public void onSuccess() {
                            loading = false;
                            binding.setLoading(false);
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });


                    binding.setPlace(place);
                }

                @Override
                public void onError(Status status) {
                    // TODO: Handle the error.
                    Log.i(MAP_TAG, "An error occurred: " + status);
                }
            });

            autocompleteFragment.getView().findViewById(R.id.places_autocomplete_clear_button).setOnClickListener(view -> {
                ((EditText) autocompleteFragment.getView().findViewById(R.id.places_autocomplete_search_input)).setText(null);
                autocompleteFragment.getView().findViewById(R.id.places_autocomplete_clear_button).setVisibility(View.INVISIBLE);
                place = null;
                binding.setPlace(place);
            });

            ImageView ivSearchIcon = (ImageView)autocompleteFragment.getView().findViewById(R.id.places_autocomplete_search_button);

            ivSearchIcon.setImageIcon(Icon.createWithResource(getContext(),R.drawable.ic_menu));
            ivSearchIcon.setOnClickListener(view -> {
                ((DrawerLayout)getActivity().findViewById(R.id.nd_layout)).openDrawer(GravityCompat.START);
            });
        });

        return root;
    }
}
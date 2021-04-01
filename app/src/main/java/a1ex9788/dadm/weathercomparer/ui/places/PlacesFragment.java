package a1ex9788.dadm.weathercomparer.ui.places;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import a1ex9788.dadm.weathercomparer.MainActivity;
import a1ex9788.dadm.weathercomparer.adapters.OnPlaceClickListener;
import a1ex9788.dadm.weathercomparer.adapters.PlaceAdapter;
import a1ex9788.dadm.weathercomparer.adapters.SwipeController;
import a1ex9788.dadm.weathercomparer.adapters.SwipeControllerActions;
import a1ex9788.dadm.weathercomparer.db.Room;

import a1ex9788.dadm.weathercomparer.R;
import a1ex9788.dadm.weathercomparer.db.RoomDao;
import a1ex9788.dadm.weathercomparer.ui.map.MapPlace;

public class PlacesFragment extends Fragment {

    RoomDao db;
    List<MapPlace> places;
    public static String PLACES_TAG = "PLACES";

    private PlacesViewModel placesViewModel;
    private PlaceAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        placesViewModel =
                new ViewModelProvider(this).get(PlacesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_places, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.rv_places);

        SwipeController swipeController = new SwipeController(getContext(),new SwipeControllerActions() {
            @Override
            public void onLeftClicked(int position) {
                MapPlace place = adapter.removePlaceAt(position);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        db.deletePlace(place);
                    }
                }).start();
            }
        });
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        adapter = new PlaceAdapter();
        recyclerView.setAdapter(adapter);

        db = Room.getInstance(getContext()).room();

        new Thread(new Runnable() {
            @Override
            public void run() {
                places = db.getPlaces();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setPlaces(places);
                    }
                });
            }
        }).start();

        return root;
    }

}
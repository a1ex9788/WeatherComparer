package a1ex9788.dadm.weathercomparer.ui.places;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import a1ex9788.dadm.weathercomparer.MainActivity;
import a1ex9788.dadm.weathercomparer.R;
import a1ex9788.dadm.weathercomparer.adapters.OnPlaceClickListener;
import a1ex9788.dadm.weathercomparer.adapters.PlaceAdapter;
import a1ex9788.dadm.weathercomparer.adapters.SwipeController;
import a1ex9788.dadm.weathercomparer.adapters.SwipeControllerActions;
import a1ex9788.dadm.weathercomparer.databinding.FragmentPlacesBinding;
import a1ex9788.dadm.weathercomparer.model.MapPlace;
import a1ex9788.dadm.weathercomparer.ui.forecast.ForecastFragment;
import a1ex9788.dadm.weathercomparer.ui.map.MapFragment;

public class PlacesFragment extends Fragment {

	private PlacesViewModel placesViewModel;
	private PlaceAdapter adapter;
	private FragmentPlacesBinding binding;
	private SharedPreferences prefs;

	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		placesViewModel = new ViewModelProvider(this).get(PlacesViewModel.class);

		binding = FragmentPlacesBinding.inflate(inflater, container, false);
		prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

		View root = binding.getRoot();

		setNavigationDrawerCheckedItem();

		RecyclerView recyclerView = root.findViewById(R.id.rv_places);

		SwipeController swipeController = new SwipeController(getContext(), new SwipeControllerActions() {
			@Override
			public void onLeftClicked(int position) {
				MapPlace place = adapter.removePlaceAt(position);
				new Thread(() -> {
					placesViewModel.deletePlace(getContext(), place);
					getActivity().runOnUiThread(() -> {
						binding.tvEmpty.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.INVISIBLE);
						Toast.makeText(getContext(),
								place.getName() + " " + getString(R.string.tDelete),
								Toast.LENGTH_SHORT)
								.show();
					});
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

		adapter = new PlaceAdapter(new OnPlaceClickListener() {
			@Override
			public void onClick(int position) {
				MapPlace place = adapter.getPlaceAt(position);
				Bundle params = new Bundle();

				params.putString("id", place.getId());
				params.putDouble("latitude", place.getLat());
				params.putDouble("longitude", place.getLng());

				((MainActivity) getActivity()).getSupportActionBar().hide();

				getParentFragmentManager().beginTransaction()
						.setReorderingAllowed(true)
						.replace(R.id.fcv_navigation_drawer, ForecastFragment.class, params)
						.commit();
			}
		}, prefs.getString("units", getString(R.string.valueUnits0)));
		recyclerView.setAdapter(adapter);

		new Thread(() -> {
			List<MapPlace> places = placesViewModel.getPlaces(getContext());

			getActivity().runOnUiThread(() -> {
				binding.tvEmpty.setVisibility(places.isEmpty() ? View.VISIBLE : View.INVISIBLE);
				adapter.setPlaces(places);
			});
		}).start();

		binding.floatingActionButton.setOnClickListener(view -> {
			Bundle params = new Bundle();
			params.putBoolean("search", true);
			getParentFragmentManager().beginTransaction()
					.setReorderingAllowed(true)
					.replace(R.id.fcv_navigation_drawer, MapFragment.class, params)
					.commit();
		});

		return root;
	}

	private void setNavigationDrawerCheckedItem() {
		for (int i = 0; i < 4; i++) {
			MenuItem item = ((MainActivity) requireActivity()).getNavigationDrawer().getMenu().getItem(i);
			item.setChecked(i == 1);
		}
	}

}
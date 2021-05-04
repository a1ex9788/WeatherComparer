package a1ex9788.dadm.weathercomparer.ui.forecast;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import a1ex9788.dadm.weathercomparer.MainActivity;
import a1ex9788.dadm.weathercomparer.R;
import a1ex9788.dadm.weathercomparer.bindings.CurrentWeather;
import a1ex9788.dadm.weathercomparer.databinding.FragmentForecastBinding;
import a1ex9788.dadm.weathercomparer.model.HourForecast;
import a1ex9788.dadm.weathercomparer.model.MapPlace;
import a1ex9788.dadm.weathercomparer.model.WeatherCondition;
import a1ex9788.dadm.weathercomparer.utils.UnitsGetter;
import a1ex9788.dadm.weathercomparer.webServices.places.GooglePlaces;
import a1ex9788.dadm.weathercomparer.webServices.places.LocationService;

public class ForecastFragment extends Fragment {

	private ForecastViewModel forecastViewModel;
	private BottomSheetConfigurer bottomSheetConfigurer;

	private FragmentForecastBinding binding;
	private SharedPreferences prefs;
	private String metric;
	private LottieAnimationView animationView;
	private MapPlace currentPlace;
	private double latitude, longitude;

	private WeatherProvider weatherProvider = WeatherProvider.Average;

	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		binding = FragmentForecastBinding.inflate(inflater, container, false);
		View root = binding.getRoot();
		prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
		forecastViewModel = new ViewModelProvider(this).get(ForecastViewModel.class);
		metric = prefs.getString("units", getString(R.string.valueUnits0));
		bottomSheetConfigurer = new BottomSheetConfigurer(forecastViewModel, getContext(), metric);
		animationView = root.findViewById(R.id.animationViewWeather);

		setNavigationDrawerButtonOnClickListener(root);

		setNavigationDrawerCheckedItem();

		recoverMapPlace();

		FloatingActionButton floatingActionButton = root.findViewById(R.id.supplierButton);

		binding.supplierButton.setOnClickListener(view -> {
			binding.setWeatherOptions(!binding.getWeatherOptions());
		});

		binding.averageOption.setOnClickListener(view -> {
			providersFloatingButtonsListenter(floatingActionButton,
					WeatherProvider.Average,
					R.drawable.ic_launcher_foreground);
		});
		binding.openWeather.setOnClickListener(view -> {
			providersFloatingButtonsListenter(floatingActionButton,
					WeatherProvider.OpenWeather,
					R.drawable.ic_openweather);
		});
		binding.accuWeather.setOnClickListener(view -> {
			providersFloatingButtonsListenter(floatingActionButton,
					WeatherProvider.AccuWeather,
					R.drawable.ic_accuweather);
		});
		binding.weatherBit.setOnClickListener(view -> {
			// Weather bit has change its prices an we do not have access to this feature any more.
			// providersFloatingButtonsListenter(floatingActionButton, WeatherProvider.WeatherBit, R.drawable
			// .ic_weatherbit);
		});

		return root;
	}

	private void providersFloatingButtonsListenter(FloatingActionButton floatingActionButton, WeatherProvider weatherProvider, int imageResourceId) {
		if (this.weatherProvider != weatherProvider) {
			this.weatherProvider = weatherProvider;

			setCurrentForecastData();
			bottomSheetConfigurer.configureBottomSheet(getActivity(),
					currentPlace,
					weatherProvider,
					latitude,
					longitude,
					false);

			binding.setWeatherOptions(false);

			floatingActionButton.setImageDrawable(getResources().getDrawable(imageResourceId));
		}
	}

	private void setNavigationDrawerButtonOnClickListener(View root) {
		ImageButton ibNavigationDrawer = root.findViewById(R.id.ibNavigationDrawer);
		ibNavigationDrawer.setOnClickListener(v -> ((MainActivity) requireActivity()).openNavigationDrawer());
	}

	private void setNavigationDrawerCheckedItem() {
		for (int i = 0; i < 4; i++) {
			MenuItem item = ((MainActivity) requireActivity()).getNavigationDrawer().getMenu().getItem(i);
			item.setChecked(i == 0);
		}
	}

	private void setDefaultForecastData() {
		// Set default data. It will be seen before the real one is loaded and in case of error.
		setWeatherConditionAnimation(WeatherCondition.UnknownPrecipitation);

		MapPlace mapPlace = new MapPlace();
		mapPlace.setName("Valencia");
		mapPlace.setTimeZone("16:14 Mar 16");
		binding.setPlace(mapPlace);

		CurrentWeather currentWeather = new CurrentWeather("No data",
				"-",
				UnitsGetter.getSpeedUnits(metric),
				"-",
				UnitsGetter.getTemperatureUnits(metric),
				"-",
				"%");
		binding.setCurrentWeather(currentWeather);
	}

	private void recoverMapPlace() {

		Bundle params = getArguments();

		if (getArguments() != null) {
			String id = params.getString("id");
			this.latitude = params.getDouble("latitude");
			this.longitude = params.getDouble("longitude");

			new Thread(() -> {
				new GooglePlaces(getContext()).getDetails(id, detailsResponse -> {
					Place placeFounded = ((FetchPlaceResponse) detailsResponse).getPlace();
					currentPlace = new MapPlace(placeFounded);

					Log.d("place", currentPlace.toString());

					getActivity().runOnUiThread(() -> {
						binding.setPlace(currentPlace);

						if (currentPlace.getPhoto() != null) {
							Picasso.get().load(currentPlace.getPhoto()).into(binding.ivForecastPlace);
						}
					});
				});

				setCurrentForecastData();

				binding.setLocationPermissionGranted(true);
			}).start();

			bottomSheetConfigurer.configureBottomSheet(
					getActivity(),
					currentPlace,
					weatherProvider,
					latitude,
					longitude,
					true);
		} else {
			if (ContextCompat.checkSelfPermission(getContext(),
					android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
				onLocationPermissionGranted();
			} else {
				requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
			}
		}
	}

	private void onLocationPermissionGranted() {
		binding.setLocationPermissionGranted(true);

		LocationService locationService = new LocationService(getContext());

		new Thread(() -> {
			locationService.getLocation(getContext(), new LocationCallback() {
				@Override
				public void onLocationResult(@NonNull LocationResult locationResult) {
					Location location = locationResult.getLastLocation();
					latitude = location.getLatitude();
					longitude = location.getLongitude();

					new Thread(() -> {
						try {
							forecastViewModel.getPlace(getContext(), latitude, longitude, detailsResponse -> {
								Place placeFounded = ((FetchPlaceResponse) detailsResponse).getPlace();
								currentPlace = new MapPlace(placeFounded);

								if (getActivity() == null) {
									return;
								}

								getActivity().runOnUiThread(() -> {
									binding.setPlace(currentPlace);

									if (currentPlace.getPhoto() != null) {
										Picasso.get().load(currentPlace.getPhoto()).into(binding.ivForecastPlace);
									}
								});
							});

							setCurrentForecastData();

							bottomSheetConfigurer.configureBottomSheet(getActivity(),
									currentPlace,
									weatherProvider,
									latitude,
									longitude,
									true);
						} catch (Exception e) {
							Log.e("", e.toString());
						}
					}).start();

					super.onLocationResult(locationResult);
				}
			});
		}).start();
	}

	private void setCurrentForecastData() {
		new Thread() {
			@Override
			public void run() {
				try {
					HourForecast hourForecast;

					if (weatherProvider == WeatherProvider.Average) {
						hourForecast = forecastViewModel.getCurrentAverageWeather(latitude, longitude);
					} else if (weatherProvider == WeatherProvider.AccuWeather) {
						hourForecast = forecastViewModel.getCurrentAccuWeather(latitude, longitude);
					} else if (weatherProvider == WeatherProvider.OpenWeather) {
						hourForecast = forecastViewModel.getCurrentOpenWeather(latitude, longitude);
					} else {
						hourForecast = forecastViewModel.getCurrentWeatherBit(latitude, longitude);
					}

					CurrentWeather currentWeather = new CurrentWeather(getString(hourForecast.getWeatherCondition()
							.getTextResourceIdentifier()),
							roundToOneDecimal(hourForecast.getWindSpeed_kilometersPerHour()) + "",
							UnitsGetter.getSpeedUnits(metric),
							roundToOneDecimal(hourForecast.getAvgTemperature(metric)) + "",
							UnitsGetter.getTemperatureUnits(metric),
							roundToOneDecimal(hourForecast.getPrecipitationProbability()) + "",
							"%");

					if (getActivity() == null) {
						return;
					}

					getActivity().runOnUiThread(() -> {
						binding.setCurrentWeather(currentWeather);
						setWeatherConditionAnimation(hourForecast.getWeatherCondition());
					});
				} catch (Exception e) {
					if (getActivity() == null) {
						return;
					}

					getActivity().runOnUiThread(() -> {
						Toast.makeText(requireActivity().getBaseContext(),
								R.string.toast_forecastError,
								Toast.LENGTH_LONG)
								.show();
					});
				}
			}
		}.start();
	}

	private double roundToOneDecimal(double d) {
		return Math.round(d * 10.0) / 10.0;
	}

	private void setWeatherConditionAnimation(WeatherCondition weatherCondition) {
		animationView.setAnimationFromUrl(weatherCondition.getIconAddress());
		animationView.playAnimation();
	}

	@Nullable
	@Override
	public Object getExitTransition() {
		animationView.cancelAnimation();

		return super.getExitTransition();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			onLocationPermissionGranted();
		}
	}

	enum WeatherProvider {
		Average,
		AccuWeather,
		OpenWeather,
		WeatherBit
	}

}
package a1ex9788.dadm.weathercomparer.ui.forecast;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import a1ex9788.dadm.weathercomparer.MainActivity;
import a1ex9788.dadm.weathercomparer.R;
import a1ex9788.dadm.weathercomparer.adapters.CustomRecyclerAdapterHourDayForecast;
import a1ex9788.dadm.weathercomparer.adapters.CustomRecyclerAdapterMoreInfo;
import a1ex9788.dadm.weathercomparer.adapters.HourDayForecast;
import a1ex9788.dadm.weathercomparer.adapters.MoreInfo;
import a1ex9788.dadm.weathercomparer.bindings.CurrentWeather;
import a1ex9788.dadm.weathercomparer.databinding.FragmentForecastBinding;
import a1ex9788.dadm.weathercomparer.model.DayForecast;
import a1ex9788.dadm.weathercomparer.model.HourForecast;
import a1ex9788.dadm.weathercomparer.model.MapPlace;
import a1ex9788.dadm.weathercomparer.model.WeatherCondition;
import a1ex9788.dadm.weathercomparer.utils.UnitsGetter;
import a1ex9788.dadm.weathercomparer.webServices.places.GooglePlaces;
import a1ex9788.dadm.weathercomparer.webServices.places.LocationService;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class ForecastFragment extends Fragment {

	private ForecastViewModel forecastViewModel;

	private FragmentForecastBinding binding;
	private boolean chartConfigured;
	private SharedPreferences prefs;
	private String metric;
	private LottieAnimationView animationView;
	private MapPlace currentPlace;
	private Location location;

	private WeatherProvider weatherProvider = WeatherProvider.Average;

	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		binding = FragmentForecastBinding.inflate(inflater, container, false);
		View root = binding.getRoot();
		prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
		forecastViewModel = new ViewModelProvider(this).get(ForecastViewModel.class);
		metric = prefs.getString("units", getString(R.string.valueUnits0));
		animationView = root.findViewById(R.id.animationViewWeather);

		setNavigationDrawerButtonOnClickListener(root);

		setNavigationDrawerCheckedItem();

		recoverMapPlace();

		binding.supplierButton.setOnClickListener(view -> {
			binding.setWeatherOptions(!binding.getWeatherOptions());
		});

		binding.averageOption.setOnClickListener(view -> {
			weatherProvider = WeatherProvider.Average;

			setCurrentForecastData(location.getLatitude(), location.getLongitude());
			configureBottomSheet(location.getLatitude(), location.getLongitude(), false);

			binding.setWeatherOptions(false);
		});
		binding.openWeather.setOnClickListener(view -> {
			weatherProvider = WeatherProvider.OpenWeather;

			setCurrentForecastData(location.getLatitude(), location.getLongitude());
			configureBottomSheet(location.getLatitude(), location.getLongitude(), false);

			binding.setWeatherOptions(false);
		});
		binding.accuWeather.setOnClickListener(view -> {
			weatherProvider = WeatherProvider.AccuWeather;

			setCurrentForecastData(location.getLatitude(), location.getLongitude());
			configureBottomSheet(location.getLatitude(), location.getLongitude(), false);

			binding.setWeatherOptions(false);
		});
		binding.weatherBit.setOnClickListener(view -> {
			weatherProvider = WeatherProvider.WeatherBit;

			setCurrentForecastData(location.getLatitude(), location.getLongitude());
			configureBottomSheet(location.getLatitude(), location.getLongitude(), false);

			binding.setWeatherOptions(false);
		});

		return root;
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
			double latitude = params.getDouble("latitude");
			double longitude = params.getDouble("longitude");

			setCurrentForecastData(latitude, longitude);

			binding.setLocationPermissionGranted(true);

			configureBottomSheet(latitude, longitude, true);

			new Thread(() -> {
				new GooglePlaces(getContext()).getDetails(id, detailsResponse -> {
					Place placeFounded = ((FetchPlaceResponse) detailsResponse).getPlace();
					currentPlace = new MapPlace(placeFounded);

					Log.d("place" , currentPlace.toString());

					getActivity().runOnUiThread(() -> {
						binding.setPlace(currentPlace);

						if (currentPlace.getPhoto() != null) {
							Picasso.get()
									.load(currentPlace.getPhoto())
									.into(binding.ivForecastPlace);
						}
					});
				});
			}).start();
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
					location = locationResult.getLastLocation();
					Log.d("location", location.toString());
					setCurrentForecastData(location.getLatitude(), location.getLongitude());

					configureBottomSheet(location.getLatitude(), location.getLongitude(), true);

					new Thread(() -> {
						try {
							forecastViewModel.getPlace(getContext(),
									location.getLatitude(),
									location.getLongitude(),
									detailsResponse -> {
										Place placeFounded = ((FetchPlaceResponse) detailsResponse).getPlace();
										currentPlace = new MapPlace(placeFounded);

										getActivity().runOnUiThread(() -> {
											binding.setPlace(currentPlace);

											if (currentPlace.getPhoto() != null) {
												Picasso.get()
														.load(currentPlace.getPhoto())
														.into(binding.ivForecastPlace);
											}
										});
									});
						} catch (Exception e) {
						}
					}).start();
					super.onLocationResult(locationResult);
				}
			});
		}).start();
	}

	private void setCurrentForecastData(double latitude, double longitude) {
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();

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
					binding.setCurrentWeather(currentWeather);

					setWeatherConditionAnimation(hourForecast.getWeatherCondition());
				} catch (Exception e) {
					Toast.makeText(requireActivity().getBaseContext(), R.string.toast_forecastError, Toast.LENGTH_LONG)
							.show();
				}
			}
		}.start();
	}

	private void configureBottomSheet(double latitude, double longitude, boolean firstTime) {
		new Thread() {
			@Override
			public void run() {

				try {
					ConstraintLayout clBottomSheet = getActivity().findViewById(R.id.clBottomSheet);
					BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(clBottomSheet);
					DisplayMetrics metrics = new DisplayMetrics();

					getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
					int height = metrics.heightPixels;

					TextView tvToday = getActivity().findViewById(R.id.tvToday);

					RecyclerView rvMoreInfo = getActivity().findViewById(R.id.rvMoreInfo);
					RecyclerView rvHourDayPrediction = getActivity().findViewById(R.id.rvHourDayPrediction);
					RecyclerView.LayoutManager managerMoreInfo = new LinearLayoutManager(getContext(),
							RecyclerView.HORIZONTAL,
							false);
					RecyclerView.LayoutManager managerHourDay = new LinearLayoutManager(getContext(),
							RecyclerView.HORIZONTAL,
							false);
					SnapHelper snapHelper = new LinearSnapHelper();

					List<HourForecast> hourForecastsList, hourForecastsAccuWeather, hourForecastsOpenWeather
							/*, hourForecastsWeatherBit*/;
					List<DayForecast> dayForecastsList;
					try {
						if (weatherProvider == WeatherProvider.Average) {
							hourForecastsList = forecastViewModel.getAverageHourlyForecast(latitude, longitude);
						} else if (weatherProvider == WeatherProvider.AccuWeather) {
							hourForecastsList = forecastViewModel.getAccuWeatherHourlyForecast(latitude, longitude);
						} else if (weatherProvider == WeatherProvider.OpenWeather) {
							hourForecastsList = forecastViewModel.getOpenWeatherHourlyForecast(latitude, longitude);
						} else {
							hourForecastsList = forecastViewModel.getWeatherBitHourlyForecast(latitude, longitude);
						}
					} catch (Exception e) {
						hourForecastsList = new ArrayList<>();
					}
					try {
						if (weatherProvider == WeatherProvider.Average) {
							dayForecastsList = forecastViewModel.getAverageDailyForecast(latitude, longitude);
						} else if (weatherProvider == WeatherProvider.AccuWeather) {
							dayForecastsList = forecastViewModel.getAccuWeatherDailyForecast(latitude, longitude);
						} else if (weatherProvider == WeatherProvider.OpenWeather) {
							dayForecastsList = forecastViewModel.getOpenWeatherDailyForecast(latitude, longitude);
						} else {
							dayForecastsList = forecastViewModel.getWeatherBitDailyForecast(latitude, longitude);
						}
					} catch (Exception e) {
						dayForecastsList = new ArrayList<>();
					}
					try {
						hourForecastsAccuWeather = forecastViewModel.getAccuWeatherHourlyForecast(latitude, longitude);
					} catch (Exception e) {
						hourForecastsAccuWeather = new ArrayList<>();
					}
					try {
						hourForecastsOpenWeather = forecastViewModel.getOpenWeatherHourlyForecast(latitude, longitude);
					} catch (Exception e) {
						hourForecastsOpenWeather = new ArrayList<>();
					}
                    /* La función se ha convertido de pago, por lo que ya no funciona
                    List<HourForecast> hourForecastsWeatherBit = forecastViewModel
                    .getWeatherBitHourlyForecast(latitude, longitude);
                    */
					List<HourForecast> finalHourForecastsList = hourForecastsList;
					List<DayForecast> finalDayForecastsList = dayForecastsList;
					List<HourForecast> finalHourForecastsAccuWeather = hourForecastsAccuWeather;
					List<HourForecast> finalHourForecastsListOpenWeather = hourForecastsOpenWeather;
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							bottomSheetBehavior.setPeekHeight(484);
							clBottomSheet.setMaxHeight(1784);
							clBottomSheet.getLayoutParams().height = (3 * height) / 4;
							if (firstTime) {
								snapHelper.attachToRecyclerView(rvMoreInfo);
							}
							rvMoreInfo.setLayoutManager(managerMoreInfo);
							rvHourDayPrediction.setLayoutManager(managerHourDay);

							List<HourDayForecast> hoursList = new ArrayList<>();
							List<MoreInfo> list = new ArrayList<>();
							if(finalHourForecastsList.size() > 0) {
								HourDayForecast hourForecast1 = new HourDayForecast(
										finalHourForecastsList.get(2).getDate().toString().substring(11, 16),
										finalHourForecastsList.get(2).getAvgTemperature(metric).toString().
												substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
										finalHourForecastsList.get(2).getWeatherCondition().getIconAddress());
								HourDayForecast hourForecast2 = new HourDayForecast(
										finalHourForecastsList.get(3).getDate().toString().substring(11, 16),
										finalHourForecastsList.get(3).getAvgTemperature(metric).toString().
												substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
										finalHourForecastsList.get(3).getWeatherCondition().getIconAddress());
								HourDayForecast hourForecast3 = new HourDayForecast(
										finalHourForecastsList.get(4).getDate().toString().substring(11, 16),
										finalHourForecastsList.get(4).getAvgTemperature(metric).toString().
												substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
										finalHourForecastsList.get(4).getWeatherCondition().getIconAddress());
								HourDayForecast hourForecast4 = new HourDayForecast(
										finalHourForecastsList.get(5).getDate().toString().substring(11, 16),
										finalHourForecastsList.get(5).getAvgTemperature(metric).toString().
												substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
										finalHourForecastsList.get(5).getWeatherCondition().getIconAddress());
								HourDayForecast hourForecast5 = new HourDayForecast(
										finalHourForecastsList.get(6).getDate().toString().substring(11, 16),
										finalHourForecastsList.get(6).getAvgTemperature(metric).toString().
												substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
										finalHourForecastsList.get(6).getWeatherCondition().getIconAddress());
								HourDayForecast hourForecast6 = new HourDayForecast(
										finalHourForecastsList.get(7).getDate().toString().substring(11, 16),
										finalHourForecastsList.get(7).getAvgTemperature(metric).toString().
												substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
										finalHourForecastsList.get(7).getWeatherCondition().getIconAddress());
								hoursList.add(hourForecast1);
								hoursList.add(hourForecast2);
								hoursList.add(hourForecast3);
								hoursList.add(hourForecast4);
								hoursList.add(hourForecast5);
								hoursList.add(hourForecast6);

								CustomRecyclerAdapterHourDayForecast adapterHourDay = new CustomRecyclerAdapterHourDayForecast(
										hoursList);
								rvHourDayPrediction.setAdapter(adapterHourDay);
								MoreInfo moreInfoFeelsLike = new MoreInfo(getString(R.string.feels_like),
										finalHourForecastsList.get(1).getRealFeel(metric).toString().
												substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
										R.drawable.temperature);
								MoreInfo moreInfoPressure = null;
								if (finalDayForecastsList.get(1).getPressure_millibars() != null) {
									moreInfoPressure = new MoreInfo(getString(R.string.pressure),
											finalHourForecastsList.get(1).getPressure_millibars().toString().
													substring(0, 4) + getString(R.string.milibar_pressureUnit),
											R.drawable.ic_pressure);
								}
								int uvIndex = finalHourForecastsList.get(1).getUvIndex().intValue();
								String uvIndexText = "";
								if (uvIndex < 3) {
									uvIndexText = finalHourForecastsList.get(1).getUvIndex().toString().substring(0, 1) + ", " + getString(R.string.low);
								} else if (uvIndex < 6) {
									uvIndexText = finalHourForecastsList.get(1).getUvIndex().toString().substring(0, 1) + ", " + getString(R.string.mid);
								} else if (uvIndex < 8) {
									uvIndexText = finalHourForecastsList.get(1).getUvIndex().toString().substring(0, 1) + ", " + getString(R.string.high);
								} else if (uvIndex < 11) {
									uvIndexText = finalHourForecastsList.get(1).getUvIndex().toString().substring(0, 1) + ", " + getString(R.string.veryHigh);
								} else {
									uvIndexText = finalHourForecastsList.get(1).getUvIndex().toString().substring(0, 1) + ", " + getString(R.string.extreme);
								}
								MoreInfo moreInfoUVIndex = new MoreInfo(getString(R.string.UVIndex), uvIndexText, R.drawable.ic_uv_index);
								ZoneId zoneId = ZoneId.of(currentPlace.getTimeZoneId());
								Date sunrise = finalDayForecastsList.get(0).getSunrise();
								Date sunset = finalDayForecastsList.get(0).getSunset();
								LocalDateTime localDateTimeSunrise = LocalDateTime.ofInstant(sunrise.toInstant(), zoneId);
								LocalDateTime localDateTimeSunset = LocalDateTime.ofInstant(sunset.toInstant(), zoneId);
								MoreInfo moreInfoSunrise = new MoreInfo(getString(R.string.sunrise),
										localDateTimeSunrise.toString().substring(11, 16),
										R.drawable.ic_sunrise);
								MoreInfo moreInfoSunset = new MoreInfo(getString(R.string.sunset),
										localDateTimeSunset.toString().substring(11, 16),
										R.drawable.ic_sunset);
								list.add(moreInfoFeelsLike);
								if (moreInfoPressure != null) {
									list.add(moreInfoPressure);
								}
								list.add(moreInfoUVIndex);
								list.add(moreInfoSunrise);
								list.add(moreInfoSunset);
							}
								CustomRecyclerAdapterMoreInfo adapter = new CustomRecyclerAdapterMoreInfo(list);
								rvMoreInfo.setAdapter(adapter);

						}
					});

					LineChartView chartView = getActivity().findViewById(R.id.chart);
					bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
						@Override
						public void onStateChanged(@NonNull View bottomSheet, int newState) {
							if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
								chartView.setVisibility(View.INVISIBLE);
								tvToday.setText(R.string.tvToday);
								List<HourDayForecast> hoursList = new ArrayList<>();
								if(finalHourForecastsList.size() > 0) {
									HourDayForecast hourForecast1 = new HourDayForecast(
											finalHourForecastsList.get(2).getDate().toString().substring(11, 16),
											finalHourForecastsList.get(2).getAvgTemperature(metric).toString().
													substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
											finalHourForecastsList.get(2).getWeatherCondition().getIconAddress());
									HourDayForecast hourForecast2 = new HourDayForecast(
											finalHourForecastsList.get(3).getDate().toString().substring(11, 16),
											finalHourForecastsList.get(3).getAvgTemperature(metric).toString().
													substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
											finalHourForecastsList.get(3).getWeatherCondition().getIconAddress());
									HourDayForecast hourForecast3 = new HourDayForecast(
											finalHourForecastsList.get(4).getDate().toString().substring(11, 16),
											finalHourForecastsList.get(4).getAvgTemperature(metric).toString().
													substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
											finalHourForecastsList.get(4).getWeatherCondition().getIconAddress());
									HourDayForecast hourForecast4 = new HourDayForecast(
											finalHourForecastsList.get(5).getDate().toString().substring(11, 16),
											finalHourForecastsList.get(5).getAvgTemperature(metric).toString().
													substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
											finalHourForecastsList.get(5).getWeatherCondition().getIconAddress());
									HourDayForecast hourForecast5 = new HourDayForecast(
											finalHourForecastsList.get(6).getDate().toString().substring(11, 16),
											finalHourForecastsList.get(6).getAvgTemperature(metric).toString().
													substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
											finalHourForecastsList.get(6).getWeatherCondition().getIconAddress());
									HourDayForecast hourForecast6 = new HourDayForecast(
											finalHourForecastsList.get(7).getDate().toString().substring(11, 16),
											finalHourForecastsList.get(7).getAvgTemperature(metric).toString().
													substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
											finalHourForecastsList.get(7).getWeatherCondition().getIconAddress());
									hoursList.add(hourForecast1);
									hoursList.add(hourForecast2);
									hoursList.add(hourForecast3);
									hoursList.add(hourForecast4);
									hoursList.add(hourForecast5);
									hoursList.add(hourForecast6);
								}
								CustomRecyclerAdapterHourDayForecast adapterHourDay = new CustomRecyclerAdapterHourDayForecast(
										hoursList);
								rvHourDayPrediction.setAdapter(adapterHourDay);
							} else if (BottomSheetBehavior.STATE_EXPANDED == newState) {
								tvToday.setText(R.string.tvToday_daily);
								List<HourDayForecast> daysList = new ArrayList<>();
								HourDayForecast dayForecast1 = new HourDayForecast(finalDayForecastsList.get(0)
										.getDate()
										.toString()
										.substring(0, 4),
										"",
										finalDayForecastsList.get(0).getWeatherCondition().getIconAddress());
								HourDayForecast dayForecast2 = new HourDayForecast(finalDayForecastsList.get(1)
										.getDate()
										.toString()
										.substring(0, 4),
										"",
										finalDayForecastsList.get(1).getWeatherCondition().getIconAddress());
								HourDayForecast dayForecast3 = new HourDayForecast(finalDayForecastsList.get(2)
										.getDate()
										.toString()
										.substring(0, 4),
										"",
										finalDayForecastsList.get(2).getWeatherCondition().getIconAddress());
								HourDayForecast dayForecast4 = new HourDayForecast(finalDayForecastsList.get(3)
										.getDate()
										.toString()
										.substring(0, 4),
										"",
										finalDayForecastsList.get(3).getWeatherCondition().getIconAddress());
								daysList.add(dayForecast1);
								daysList.add(dayForecast2);
								daysList.add(dayForecast3);
								daysList.add(dayForecast4);
								CustomRecyclerAdapterHourDayForecast adapterHourDay = new CustomRecyclerAdapterHourDayForecast(
										daysList);
								rvHourDayPrediction.setAdapter(adapterHourDay);
							} else if (BottomSheetBehavior.STATE_DRAGGING == newState) {
								chartView.setVisibility(View.VISIBLE);
								if (!chartConfigured) {
									List<AxisValue> axisXValues = null;
									List<PointValue> pointValuesAverage = null;
									List<PointValue> pointValuesOpenWeather = null;
									List<PointValue> pointValuesAccuWeather = null;
                                            /* The function to obtain the HourForecast of
                                            WeatherBit is now premium, so we can not use it */
									/* List<PointValue> pointValuesWeatherBit = null; */
									if (finalDayForecastsList.size() != 0) {
										axisXValues = getAxisXLables(finalHourForecastsList);
										pointValuesAverage = getAxisPoints(finalHourForecastsList);
									}
									if (finalHourForecastsListOpenWeather.size() != 0) {
										pointValuesOpenWeather = getAxisPoints(finalHourForecastsListOpenWeather);
									}
									if (finalHourForecastsAccuWeather.size() != 0) {
										pointValuesAccuWeather = getAxisPoints(finalHourForecastsAccuWeather);
									}
                                            /* The function to obtain the HourForecast of
                                            WeatherBit is now premium, so we can not use it */
                                    /* if(finalHourForecastsWeatherBit.size() != 0) {
                                        pointValuesWeatherBit = getAxisPoints
                                        (hourForecastsWeatherBit);
                                    } */
									initLineChart(chartView,
											axisXValues,
											pointValuesAverage,
											pointValuesOpenWeather,
											pointValuesAccuWeather/*,
                                                    pointValuesWeatherBit*/);
									chartConfigured = true;
								}
							}
						}

						@Override
						public void onSlide(@NonNull View bottomSheet, float slideOffset) {

						}
					});
				} catch (Exception e) {
					e.printStackTrace();
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

	private List<AxisValue> getAxisXLables(List<HourForecast> hourForecastList) {
		List<AxisValue> axisXValues = new ArrayList<AxisValue>();
		for (int i = 0; i < hourForecastList.size(); i++) {
			axisXValues.add(new AxisValue(i).setLabel(hourForecastList.get(i).getDate().
					toString().substring(11, 16)));
		}
		return axisXValues;
	}

	private List<PointValue> getAxisPoints(List<HourForecast> hourForecastList) {
		List<PointValue> pointValues = new ArrayList<PointValue>();
		for (int i = 0; i < 12; i++) {
			double temp = hourForecastList.get(i).getAvgTemperature_celsius();
			pointValues.add(new PointValue(i, (float) temp));
		}
		return pointValues;
	}

	private void initLineChart(LineChartView chartView, List<AxisValue> axisXValues, List<PointValue> pointValuesAverage, List<PointValue> pointValuesOpenWeather, List<PointValue> pointValuesAccuWeather
			/*, List<PointValue> pointValuesWeatherBit*/) {
		Line lineAverage = new Line(pointValuesAverage).setColor(Color.parseColor("#1B1B1B"));
		Line lineOpenWeather = new Line(pointValuesOpenWeather).setColor(Color.parseColor("#E59866"));
		Line lineAccuWeather = new Line(pointValuesAccuWeather).setColor(Color.parseColor("#CD6155"));
		/* The function to obtain the HourForecast of WeatherBit is now premium, so we can not use it */
		//Line lineWeatherBit = new Line(pointValuesWeatherBit).setColor(Color.parseColor("#82E0AA"));
		List<Line> lines = new ArrayList<>();
		lineAverage.setShape(ValueShape.CIRCLE);//The shape of each data point on a broken line chart is circular here (there are three kinds: ValueShape. SQUARE ValueShape. CIRCLE ValueShape. DIAMOND)
		lineAverage.setCubic(false);//Whether the curve is smooth, that is, whether it is a curve or a broken line
		lineAverage.setFilled(true);//Whether or not to fill the area of the curve
		lineAverage.setHasLabels(true);//Whether to add notes to the data coordinates of curves
		lineAverage.setHasLines(true);//Whether to display with line or not. If it is false, there is no curve but point display
		lineAverage.setHasPoints(false);//Whether to display a dot if it is false, there is no origin but only a dot (each data point is a large dot)
		lineOpenWeather.setShape(ValueShape.CIRCLE);
		lineOpenWeather.setCubic(false);
		lineOpenWeather.setFilled(false);
		lineOpenWeather.setHasLabels(true);
		lineOpenWeather.setHasLines(true);
		lineOpenWeather.setHasPoints(false);
		lineAccuWeather.setShape(ValueShape.CIRCLE);
		lineAccuWeather.setCubic(false);
		lineAccuWeather.setFilled(false);
		lineAccuWeather.setHasLabels(true);
		lineAccuWeather.setHasLines(true);
		lineAccuWeather.setHasPoints(false);
		/* The function to obtain the HourForecast of WeatherBit is now premium, so we can not use it */
        /*lineWeatherBit.setShape(ValueShape.CIRCLE);
        lineWeatherBit.setCubic(false);
        lineWeatherBit.setFilled(false);
        lineWeatherBit.setHasLabels(true);
        lineWeatherBit.setHasLines(true);
        lineWeatherBit.setHasPoints(false);*/

		lines.add(lineAverage);
		lines.add(lineOpenWeather);
		lines.add(lineAccuWeather);
		/* lines.add(lineWeatherBit); */
		LineChartData data = new LineChartData();
		data.setLines(lines);

		//Axis of coordinates
		Axis axisX = new Axis(); //X axis
		axisX.setHasTiltedLabels(true);  //Is the font on the X axis oblique or straight, and true oblique?
		axisX.setTextColor(Color.BLACK);  //Setting font color
		axisX.setName("hour"); // table name
		axisX.setTextSize(10);//Set font size
		axisX.setMaxLabelChars(8); //Up to a few X-axis coordinates, which means that your scaling allows the number of data on the X-axis to be 7<=x<=mAxisXValues.length.
		axisX.setValues(axisXValues);  //Fill in the coordinate name of the X-axis
		data.setAxisXBottom(axisX); //The x-axis is at the bottom.
		axisX.setHasLines(true); //x-axis dividing line

		// The Y-axis automatically sets the Y-axis limit according to the size of the data (below I will give a solution for fixing the number of Y-axis data)
		Axis axisY = new Axis();  //Y axis
		axisY.setName("temperature");//y axis annotation
		axisY.setTextColor(Color.BLACK);
		axisY.setTextSize(10);//Set font size
		data.setAxisYLeft(axisY);  //The Y-axis is set on the left

		//Setting behavioral properties to support zooming, sliding, and Translation
		chartView.setInteractive(true);
		chartView.setZoomType(ZoomType.HORIZONTAL);
		chartView.setMaxZoom((float) 2);//Maximum method ratio
		chartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
		chartView.setLineChartData(data);

		chartView.setVisibility(View.VISIBLE);

		Viewport v = new Viewport(chartView.getMaximumViewport());
		v.left = 0;
		v.right = 7;
		chartView.setCurrentViewport(v);
	}

	private enum WeatherProvider {
		Average,
		AccuWeather,
		OpenWeather,
		WeatherBit
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if (grantResults.length > 0 &&
				grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			onLocationPermissionGranted();
		}
	}

}
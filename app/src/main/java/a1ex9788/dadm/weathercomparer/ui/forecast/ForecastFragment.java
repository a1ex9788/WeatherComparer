package a1ex9788.dadm.weathercomparer.ui.forecast;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import a1ex9788.dadm.weathercomparer.MainActivity;
import a1ex9788.dadm.weathercomparer.R;
import a1ex9788.dadm.weathercomparer.adapters.CustomRecyclerAdapter;
import a1ex9788.dadm.weathercomparer.adapters.MoreInfo;
import a1ex9788.dadm.weathercomparer.bindings.CurrentWeather;
import a1ex9788.dadm.weathercomparer.databinding.FragmentForecastBinding;
import a1ex9788.dadm.weathercomparer.model.DayForecast;
import a1ex9788.dadm.weathercomparer.model.HourForecast;
import a1ex9788.dadm.weathercomparer.model.MapPlace;
import a1ex9788.dadm.weathercomparer.model.WeatherCondition;
import a1ex9788.dadm.weathercomparer.webServices.LocationService;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.accuWeather.AccuWeatherDailyForecast;
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
    private SharedPreferences prefs ;

    private LottieAnimationView animationView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        binding = FragmentForecastBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        forecastViewModel = new ViewModelProvider(this).get(ForecastViewModel.class);

        animationView = root.findViewById(R.id.animationViewWeather);

        setNavigationDrawerButtonOnClickListener(root);

        setNavigationDrawerCheckedItem();

        recoverMapPlace(bundle);

        return root;
    }

    private void setNavigationDrawerButtonOnClickListener(View root) {
        ImageButton ibNavigationDrawer = root.findViewById(R.id.ibNavigationDrawer);
        ibNavigationDrawer.setOnClickListener(v -> ((MainActivity) requireActivity()).openNavigationDrawer());
    }

    private void setNavigationDrawerCheckedItem() {
        for (int i = 0; i < 4; i++) {
            MenuItem item = ((MainActivity) requireActivity()).getNavigationDrawer().getMenu().getItem(i);
            if (i == 0) {
                item.setChecked(true);
            } else {
                item.setChecked(false);
            }
        }
    }
    private String getSpeedUnits() {
        final String metric =  prefs.getString("units",getString(R.string.valueUnits0));
        if (metric.equals(getString(R.string.valueUnits0))) {
            return getString(R.string.speed_metricUnits);
        }
        else if (metric.equals(getString(R.string.valueUnits1))) {
            return getString(R.string.speed_imperialUnits);
        }
        else if (metric.equals(getString(R.string.valueUnits2))) {
            return getString(R.string.speed_scientificUnits);
        }
        else {
            return getString(R.string.speed_metricUnits);
        }
    }
    private String getTemperatureUnits() {
        final String metric =  prefs.getString("units",getString(R.string.valueUnits0));
        if (metric.equals(getString(R.string.valueUnits0))) {
            return getString(R.string.temperature_metricUnits);
        }
        else if (metric.equals(getString(R.string.valueUnits1))) {
            return getString(R.string.temperature_imperialUnits);
        }
        else if (metric.equals(getString(R.string.valueUnits2))) {
            return getString(R.string.temperature_scientificUnits);
        }
        else {
            return getString(R.string.temperature_metricUnits);
        }
    }

    private void recoverMapPlace(Bundle bundle) {
        if (bundle != null) {

        } else {
            getLocationPermission();

            if (binding.getLocationPermissionGranted()) {
                LocationService locationService = new LocationService(getContext());

                new Thread(() -> {
                    locationService.getLocation(getContext(), locationResponse -> {
                        Location location = (Location) locationResponse;

                        setCurrentForecastData(location.getLatitude(), location.getLongitude());

                        configureBottomSheet(location.getLatitude(), location.getLongitude());

                        new Thread(() -> {
                            try {
                                forecastViewModel.getPlace(getContext(), location.getLatitude(), location.getLongitude(), detailsResponse -> {
                                    Place placeFounded = ((FetchPlaceResponse) detailsResponse).getPlace();
                                    MapPlace place = new MapPlace(placeFounded);

                                    getActivity().runOnUiThread(() -> {
                                        binding.setPlace(place);

                                        if (place.getPhoto() != null) {
                                            Picasso.get()
                                                    .load(place.getPhoto())
                                                    .into(binding.ivForecastPlace);
                                        }
                                    });


                                });
                            } catch (Exception e) {

                            }
                        }).start();




                    });
                }).start();


            }
        }
        /* To provide parameters to the ForecastFragment the following code is needed:
            Bundle result = new Bundle();
            result.putString(String.valueOf(R.string.nameFragmentParameter_key), "Valencia");
            getParentFragmentManager().setFragmentResult(String.valueOf(R.string.listenerRequest_key), result);
         */
    }

    private void setCurrentForecastData(double latitude, double longitude) {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();

                try {
                    HourForecast hourForecast = forecastViewModel.getCurrentWeather(latitude, longitude);


                    CurrentWeather currentWeather = new CurrentWeather(
                            hourForecast.getWeatherCondition().getText(),
                            roundToOneDecimal(hourForecast.getWindSpeed_kilometersPerHour()) + "",
                            getSpeedUnits(),
                            roundToOneDecimal(hourForecast.getAvgTemperature_celsius()) + "",
                            getTemperatureUnits(),
                            roundToOneDecimal(hourForecast.getPrecipitationProbability()) + "",
                            "%");
                    binding.setCurrentWeather(currentWeather);

                    setWeatherConditionAnimation(hourForecast.getWeatherCondition());
                } catch (Exception e) {
                    Toast.makeText(requireActivity().getBaseContext(), R.string.toast_forecastError, Toast.LENGTH_LONG).show();
                }
            }
        }.start();
    }

    private void configureBottomSheet(double latitude, double longitude) {
        new Thread() {
            @Override
            public void run() {

                try {
                    ConstraintLayout clBottomSheet = getActivity().findViewById(R.id.clBottomSheet);
                    BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(clBottomSheet);

                    DisplayMetrics metrics = new DisplayMetrics();
                    ((MainActivity) getActivity()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    int height = metrics.heightPixels;

                    TextView tvToday = getActivity().findViewById(R.id.tvToday);

                    ConstraintLayout hourPrediction1 = getActivity().findViewById(R.id.hourPrediction1);
                    TextView tvHour1 = hourPrediction1.findViewById(R.id.tvHour);
                    TextView tvTemp1 = hourPrediction1.findViewById(R.id.tvTemp);
                    LottieAnimationView lavWeatherIcon1 = hourPrediction1.findViewById(R.id.lavWeatherIcon);
                    ConstraintLayout hourPrediction2 = getActivity().findViewById(R.id.hourPrediction2);
                    TextView tvHour2 = hourPrediction2.findViewById(R.id.tvHour);
                    TextView tvTemp2 = hourPrediction2.findViewById(R.id.tvTemp);
                    LottieAnimationView lavWeatherIcon2 = hourPrediction2.findViewById(R.id.lavWeatherIcon);
                    ConstraintLayout hourPrediction3 = getActivity().findViewById(R.id.hourPrediction3);
                    TextView tvHour3 = hourPrediction3.findViewById(R.id.tvHour);
                    TextView tvTemp3 = hourPrediction3.findViewById(R.id.tvTemp);
                    LottieAnimationView lavWeatherIcon3 = hourPrediction3.findViewById(R.id.lavWeatherIcon);
                    ConstraintLayout hourPrediction4 = getActivity().findViewById(R.id.hourPrediction4);
                    TextView tvHour4 = hourPrediction4.findViewById(R.id.tvHour);
                    TextView tvTemp4 = hourPrediction4.findViewById(R.id.tvTemp);
                    LottieAnimationView lavWeatherIcon4 = hourPrediction4.findViewById(R.id.lavWeatherIcon);
                    ConstraintLayout hourPrediction5 = getActivity().findViewById(R.id.hourPrediction5);
                    TextView tvHour5 = hourPrediction5.findViewById(R.id.tvHour);
                    TextView tvTemp5 = hourPrediction5.findViewById(R.id.tvTemp);
                    LottieAnimationView lavWeatherIcon5 = hourPrediction5.findViewById(R.id.lavWeatherIcon);
                    ConstraintLayout hourPrediction6 = getActivity().findViewById(R.id.hourPrediction6);
                    TextView tvHour6 = hourPrediction6.findViewById(R.id.tvHour);
                    TextView tvTemp6 = hourPrediction6.findViewById(R.id.tvTemp);

                    RecyclerView recyclerView = getActivity().findViewById(R.id.rvMoreInfo);
                    RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                    SnapHelper snapHelper = new LinearSnapHelper();

                    LottieAnimationView lavWeatherIcon6 = hourPrediction6.findViewById(R.id.lavWeatherIcon);
                    /* The function to obtain the HourForecast of WeatherBit is now premium, so we can not use it */
                    List<HourForecast> hourForecastsList, hourForecastsAccuWeather, hourForecastsOpenWeather
                    /*, hourForecastsWeatherBit*/;
                    List<DayForecast> dayForecastsList;
                    try{
                        hourForecastsList = forecastViewModel.getAverageHourlyForecast(latitude, longitude);
                    }catch (Exception e){
                        hourForecastsList = new ArrayList<>();
                    }
                    try{
                        dayForecastsList = forecastViewModel.getAverageDailyForecast(latitude, longitude);
                    }catch (Exception e){
                        dayForecastsList = new ArrayList<>();
                    }
                    try{
                        hourForecastsAccuWeather = forecastViewModel.getAccuWeatherHourlyForecast(latitude, longitude);
                    }catch (Exception e){
                        hourForecastsAccuWeather = new ArrayList<>();
                    }
                    try{
                        hourForecastsOpenWeather = forecastViewModel.getOpenWeatherHourlyForecast(latitude, longitude);
                    }catch (Exception e){
                        hourForecastsOpenWeather = new ArrayList<>();
                    }
                    /* The function to obtain the HourForecast of WeatherBit is now premium, so we can not use it */
                    /* List<HourForecast> hourForecastsWeatherBit = forecastViewModel.getWeatherBitHourlyForecast(latitude, longitude); */
                    List<HourForecast> finalHourForecastsList = hourForecastsList;
                    List<DayForecast> finalDayForecastsList = dayForecastsList;
                    List<HourForecast> finalHourForecastsAccuWeather = hourForecastsAccuWeather;
                    List<HourForecast> finalHourForecastsListOpenWeather = hourForecastsOpenWeather;
                    /* The function to obtain the HourForecast of WeatherBit is now premium, so we can not use it */
                    /* List<HourForecast> finalHourForecastsWeatherBit = hourForecastsOpenWeather; */
                    ((MainActivity) getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bottomSheetBehavior.setPeekHeight(height / 4);
                            clBottomSheet.setMaxHeight(1784);
                            clBottomSheet.getLayoutParams().height = (3 * height) / 4;
                            tvHour1.setText(finalHourForecastsList.get(2).getDate().toString().substring(11, 16));
                            tvTemp1.setText(finalHourForecastsList.get(2).getAvgTemperature_celsius().toString().
                                    substring(0, 4) + getString(R.string.temperature_metricUnits));
                            lavWeatherIcon1.setAnimationFromUrl(finalHourForecastsList.get(2).getWeatherCondition().getIconAddress());
                            tvHour2.setText(finalHourForecastsList.get(3).getDate().toString().substring(11, 16));
                            tvTemp2.setText(finalHourForecastsList.get(3).getAvgTemperature_celsius().toString().
                                    substring(0, 4) + getString(R.string.temperature_metricUnits));
                            lavWeatherIcon2.setAnimationFromUrl(finalHourForecastsList.get(3).getWeatherCondition().getIconAddress());
                            tvHour3.setText(finalHourForecastsList.get(4).getDate().toString().substring(11, 16));
                            tvTemp3.setText(finalHourForecastsList.get(4).getAvgTemperature_celsius().toString().
                                    substring(0, 4) + getString(R.string.temperature_metricUnits));
                            lavWeatherIcon3.setAnimationFromUrl(finalHourForecastsList.get(4).getWeatherCondition().getIconAddress());
                            tvHour4.setText(finalHourForecastsList.get(5).getDate().toString().substring(11, 16));
                            tvTemp4.setText(finalHourForecastsList.get(5).getAvgTemperature_celsius().toString().
                                    substring(0, 4) + getString(R.string.temperature_metricUnits));
                            lavWeatherIcon4.setAnimationFromUrl(finalHourForecastsList.get(5).getWeatherCondition().getIconAddress());
                            tvHour5.setText(finalHourForecastsList.get(6).getDate().toString().substring(11, 16));
                            tvTemp5.setText(finalHourForecastsList.get(6).getAvgTemperature_celsius().toString().
                                    substring(0, 4) + getString(R.string.temperature_metricUnits));
                            lavWeatherIcon5.setAnimationFromUrl(finalHourForecastsList.get(6).getWeatherCondition().getIconAddress());
                            tvHour6.setText(finalHourForecastsList.get(7).getDate().toString().substring(11, 16));
                            tvTemp6.setText(finalHourForecastsList.get(7).getAvgTemperature_celsius().toString().
                                    substring(0, 4) + getString(R.string.temperature_metricUnits));
                            lavWeatherIcon6.setAnimationFromUrl(finalHourForecastsList.get(8).getWeatherCondition().getIconAddress());
                            snapHelper.attachToRecyclerView(recyclerView);
                            recyclerView.setLayoutManager(manager);
                            List<MoreInfo> list = new ArrayList<>();
                            MoreInfo moreInfoFeelsLike = new MoreInfo(getString(R.string.feels_like), finalHourForecastsList.get(1).getRealFeel_celsius().toString().
                                    substring(0, 4) + getString(R.string.temperature_metricUnits), R.drawable.temperature );
                            MoreInfo moreInfoPressure = new MoreInfo(getString(R.string.pressure), finalHourForecastsList.get(1).getPressure_millibars().toString().
                                    substring(0, 4) + getString(R.string.milibar_pressureUnit), R.drawable.ic_pressure );
                            MoreInfo moreInfoUVIndex = new MoreInfo(getString(R.string.UVIndex), finalHourForecastsList.get(1).getUvIndex().toString().substring(0, 1), R.drawable.ic_uv_index );
                            MoreInfo moreInfoSunrise = new MoreInfo(getString(R.string.sunrise), finalDayForecastsList.get(0).getSunrise().toString().substring(11, 16), R.drawable.ic_sunrise );
                            MoreInfo moreInfoSunset = new MoreInfo(getString(R.string.sunset), finalDayForecastsList.get(0).getSunset().toString().substring(11, 16), R.drawable.ic_sunset );
                            list.add(moreInfoFeelsLike);
                            list.add(moreInfoPressure);
                            list.add(moreInfoUVIndex);
                            list.add(moreInfoSunrise);
                            list.add(moreInfoSunset);
                            CustomRecyclerAdapter adapter = new CustomRecyclerAdapter(list);
                            recyclerView.setAdapter(adapter);
                        }
                    });

                    LineChartView chartView = getActivity().findViewById(R.id.chart);
                    bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                        @Override
                        public void onStateChanged(@NonNull View bottomSheet, int newState) {
                            if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                                chartView.setVisibility(View.INVISIBLE);
                                tvToday.setText(R.string.tvToday);
                                tvHour1.setText(finalHourForecastsList.get(2).getDate().toString().substring(11, 16));
                                tvTemp1.setText(finalHourForecastsList.get(2).getAvgTemperature_celsius().toString().
                                        substring(0, 4) + getString(R.string.temperature_metricUnits));
                                lavWeatherIcon1.setAnimationFromUrl(finalHourForecastsList.get(2).getWeatherCondition().getIconAddress());
                                tvHour2.setText(finalHourForecastsList.get(3).getDate().toString().substring(11, 16));
                                tvTemp2.setText(finalHourForecastsList.get(3).getAvgTemperature_celsius().toString().
                                        substring(0, 4) + getString(R.string.temperature_metricUnits));
                                lavWeatherIcon2.setAnimationFromUrl(finalHourForecastsList.get(3).getWeatherCondition().getIconAddress());
                                tvHour3.setText(finalHourForecastsList.get(4).getDate().toString().substring(11, 16));
                                tvTemp3.setText(finalHourForecastsList.get(4).getAvgTemperature_celsius().toString().
                                        substring(0, 4) + getString(R.string.temperature_metricUnits));
                                lavWeatherIcon3.setAnimationFromUrl(finalHourForecastsList.get(4).getWeatherCondition().getIconAddress());
                                tvHour4.setText(finalHourForecastsList.get(5).getDate().toString().substring(11, 16));
                                tvTemp4.setText(finalHourForecastsList.get(5).getAvgTemperature_celsius().toString().
                                        substring(0, 4) + getString(R.string.temperature_metricUnits));
                                lavWeatherIcon4.setAnimationFromUrl(finalHourForecastsList.get(5).getWeatherCondition().getIconAddress());
                                tvHour5.setText(finalHourForecastsList.get(6).getDate().toString().substring(11, 16));
                                tvTemp5.setText(finalHourForecastsList.get(6).getAvgTemperature_celsius().toString().
                                        substring(0, 4) + getString(R.string.temperature_metricUnits));
                                lavWeatherIcon5.setAnimationFromUrl(finalHourForecastsList.get(6).getWeatherCondition().getIconAddress());
                                tvHour6.setText(finalHourForecastsList.get(7).getDate().toString().substring(11, 16));
                                tvTemp6.setText(finalHourForecastsList.get(7).getAvgTemperature_celsius().toString().
                                        substring(0, 4) + getString(R.string.temperature_metricUnits));
                                lavWeatherIcon6.setAnimationFromUrl(finalHourForecastsList.get(8).getWeatherCondition().getIconAddress());
                                hourPrediction6.setVisibility(View.VISIBLE);
                                hourPrediction5.setVisibility(View.VISIBLE);
                            } else if (BottomSheetBehavior.STATE_EXPANDED == newState) {
                                tvToday.setText(R.string.tvToday_daily);
                                tvHour1.setText(finalDayForecastsList.get(0).getDate().toString().substring(0, 4));
                                tvTemp1.setText("");
                                lavWeatherIcon1.setAnimationFromUrl(finalDayForecastsList.get(0).getWeatherCondition().getIconAddress());
                                tvHour2.setText(finalDayForecastsList.get(1).getDate().toString().substring(0, 4));
                                tvTemp2.setText("");
                                lavWeatherIcon2.setAnimationFromUrl(finalDayForecastsList.get(1).getWeatherCondition().getIconAddress());
                                tvHour3.setText(finalDayForecastsList.get(2).getDate().toString().substring(0, 4));
                                tvTemp3.setText("");
                                lavWeatherIcon3.setAnimationFromUrl(finalDayForecastsList.get(2).getWeatherCondition().getIconAddress());
                                tvHour4.setText(finalDayForecastsList.get(3).getDate().toString().substring(0, 4));
                                tvTemp4.setText("");
                                lavWeatherIcon4.setAnimationFromUrl(finalDayForecastsList.get(3).getWeatherCondition().getIconAddress());
                                hourPrediction5.setVisibility(View.INVISIBLE);
                                hourPrediction6.setVisibility(View.INVISIBLE);
                            } else if (BottomSheetBehavior.STATE_DRAGGING == newState) {
                                chartView.setVisibility(View.VISIBLE);
                                if (!chartConfigured) {
                                    List<AxisValue> axisXValues = null;
                                    List<PointValue> pointValuesAverage = null;
                                    List<PointValue> pointValuesOpenWeather = null;
                                    List<PointValue> pointValuesAccuWeather = null;
                                    /* The function to obtain the HourForecast of WeatherBit is now premium, so we can not use it */
                                    /* List<PointValue> pointValuesWeatherBit = null; */
                                    if(finalDayForecastsList.size() != 0) {
                                        axisXValues = getAxisXLables(finalHourForecastsList);
                                        pointValuesAverage = getAxisPoints(finalHourForecastsList);
                                    }
                                    if(finalHourForecastsListOpenWeather.size() != 0) {
                                        pointValuesOpenWeather = getAxisPoints(finalHourForecastsListOpenWeather);
                                    }
                                    if(finalHourForecastsAccuWeather.size() != 0) {
                                        pointValuesAccuWeather = getAxisPoints(finalHourForecastsAccuWeather);
                                    }
                                    /* The function to obtain the HourForecast of WeatherBit is now premium, so we can not use it */
                                    /* if(finalHourForecastsWeatherBit.size() != 0) {
                                        pointValuesWeatherBit = getAxisPoints(hourForecastsWeatherBit);
                                    } */
                                    initLineChart(chartView, axisXValues, pointValuesAverage, pointValuesOpenWeather,
                                            pointValuesAccuWeather/*,pointValuesWeatherBit*/);
                                    chartConfigured = true;
                                }
                            }
                        }

                        @Override
                        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                        }
                    });
                }catch (Exception e) {
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
        List<AxisValue> axisXValues =  new ArrayList<AxisValue>();
        for(int i = 0; i < hourForecastList.size(); i++){
            axisXValues.add(new AxisValue(i).setLabel(hourForecastList.get(i).getDate().
                    toString().substring(11, 16)));
        }
        return axisXValues;
    }

    private List<PointValue> getAxisPoints(List<HourForecast> hourForecastList) {
        List<PointValue> pointValues = new ArrayList<PointValue>();
        for(int i = 0; i < 12; i++){
            double temp = hourForecastList.get(i).getAvgTemperature_celsius();
            pointValues.add(new PointValue(i, (float) temp));
        }
        return pointValues;
    }

    private void initLineChart(LineChartView chartView, List<AxisValue> axisXValues, List<PointValue> pointValuesAverage,
                               List<PointValue> pointValuesOpenWeather,List<PointValue> pointValuesAccuWeather
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

    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            binding.setLocationPermissionGranted(true);
        } else {
            ActivityCompat
                    .requestPermissions(
                            getActivity(),
                            new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                            1);
        }
    }

}
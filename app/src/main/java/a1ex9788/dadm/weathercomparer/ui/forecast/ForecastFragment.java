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

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import a1ex9788.dadm.weathercomparer.MainActivity;
import a1ex9788.dadm.weathercomparer.R;
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

        setDefaultForecastData();

        configureBottomSheet(root);

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
    private void setDefaultForecastData() {
        // Set default data. It will be seen before the real one is loaded and in case of error.
        setWeatherConditionAnimation(WeatherCondition.UnknownPrecipitation);

        MapPlace mapPlace = new MapPlace();
        mapPlace.setName("Valencia");
        mapPlace.setTimeZone("16:14 Mar 16");
        binding.setPlace(mapPlace);

        CurrentWeather currentWeather = new CurrentWeather(
                "No data",
                "-",
                getSpeedUnits(),
                "-",
                getTemperatureUnits(),
                "-",
                "%");
        binding.setCurrentWeather(currentWeather);
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

                        Log.d("current",location.toString());
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

                    MapPlace mapPlace = new MapPlace();
                    mapPlace.setName("Valencia");
                    mapPlace.setTimeZone("16:14 Mar 16");
                    binding.setPlace(mapPlace);

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

    private void configureBottomSheet(View root) {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    ConstraintLayout clBottomSheet = root.findViewById(R.id.clBottomSheet);
                    BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(clBottomSheet);
                    DisplayMetrics metrics = new DisplayMetrics();

                    ((MainActivity) getActivity()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    int height = metrics.heightPixels;
                    int width = metrics.heightPixels;
                    bottomSheetBehavior.setPeekHeight(height / 4);
                    clBottomSheet.getLayoutParams().height = (3 * height) / 4;
                    TextView tvToday = root.findViewById(R.id.tvToday);
                    TextView tvFeelsLike = clBottomSheet.findViewById(R.id.tvFeelsLikeValue);
                    TextView tvPressure = clBottomSheet.findViewById(R.id.tvPressureValue);
                    TextView tvUVIndex = clBottomSheet.findViewById(R.id.tvUVIndexValue);
                    TextView tvProbabilityOfRain = clBottomSheet.findViewById(R.id.tvProbabilityOfRainValue);
                    TextView tvSunrise = clBottomSheet.findViewById(R.id.tvSunriseValue);
                    TextView tvSunset = clBottomSheet.findViewById(R.id.tvSunsetValue);

                    ConstraintLayout hourPrediction1 = root.findViewById(R.id.hourPrediction1);
                    TextView tvHour1 = hourPrediction1.findViewById(R.id.tvHour);
                    TextView tvTemp1 = hourPrediction1.findViewById(R.id.tvTemp);
                    LottieAnimationView lavWeatherIcon1 = hourPrediction1.findViewById(R.id.lavWeatherIcon);
                    ConstraintLayout hourPrediction2 = root.findViewById(R.id.hourPrediction2);
                    TextView tvHour2 = hourPrediction2.findViewById(R.id.tvHour);
                    TextView tvTemp2 = hourPrediction2.findViewById(R.id.tvTemp);
                    LottieAnimationView lavWeatherIcon2 = hourPrediction2.findViewById(R.id.lavWeatherIcon);
                    ConstraintLayout hourPrediction3 = root.findViewById(R.id.hourPrediction3);
                    TextView tvHour3 = hourPrediction3.findViewById(R.id.tvHour);
                    TextView tvTemp3 = hourPrediction3.findViewById(R.id.tvTemp);
                    LottieAnimationView lavWeatherIcon3 = hourPrediction3.findViewById(R.id.lavWeatherIcon);
                    ConstraintLayout hourPrediction4 = root.findViewById(R.id.hourPrediction4);
                    TextView tvHour4 = hourPrediction4.findViewById(R.id.tvHour);
                    TextView tvTemp4 = hourPrediction4.findViewById(R.id.tvTemp);
                    LottieAnimationView lavWeatherIcon4 = hourPrediction4.findViewById(R.id.lavWeatherIcon);
                    ConstraintLayout hourPrediction5 = root.findViewById(R.id.hourPrediction5);
                    TextView tvHour5 = hourPrediction5.findViewById(R.id.tvHour);
                    TextView tvTemp5 = hourPrediction5.findViewById(R.id.tvTemp);
                    LottieAnimationView lavWeatherIcon5 = hourPrediction5.findViewById(R.id.lavWeatherIcon);
                    ConstraintLayout hourPrediction6 = root.findViewById(R.id.hourPrediction6);
                    TextView tvHour6 = hourPrediction6.findViewById(R.id.tvHour);
                    TextView tvTemp6 = hourPrediction6.findViewById(R.id.tvTemp);
                    LottieAnimationView lavWeatherIcon6 = hourPrediction6.findViewById(R.id.lavWeatherIcon);
                    double latitude = 39.289, longitude = -0.799;


                    List<HourForecast> hourForecastsList = forecastViewModel.getAverageHourlyForecast(latitude, longitude);
                    List<DayForecast> dayForecastsList = forecastViewModel.getAverageDailyForecast(latitude, longitude);
                    //List<HourForecast> hourForecastsAccuWeather = forecastViewModel.getAccuWeatherHourlyForecast(latitude, longitude);
                    List<HourForecast> hourForecastsOpenWeather = forecastViewModel.getOpenWeatherHourlyForecast(latitude, longitude);
                    List<HourForecast> hourForecastsWeatherBit = forecastViewModel.getWeatherBitHourlyForecast(latitude, longitude);

                    ((MainActivity) getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvHour1.setText(hourForecastsList.get(2).getDate().toString().substring(11, 16));
                            tvTemp1.setText(hourForecastsList.get(2).getAvgTemperature_celsius().toString().
                                    substring(0, 4) + getString(R.string.temperature_metricUnits));
                            lavWeatherIcon1.setAnimationFromUrl(hourForecastsList.get(2).getWeatherCondition().getIconAddress());
                            tvHour2.setText(hourForecastsList.get(3).getDate().toString().substring(11, 16));
                            tvTemp2.setText(hourForecastsList.get(3).getAvgTemperature_celsius().toString().
                                    substring(0, 4) + getString(R.string.temperature_metricUnits));
                            lavWeatherIcon2.setAnimationFromUrl(hourForecastsList.get(3).getWeatherCondition().getIconAddress());
                            tvHour3.setText(hourForecastsList.get(4).getDate().toString().substring(11, 16));
                            tvTemp3.setText(hourForecastsList.get(4).getAvgTemperature_celsius().toString().
                                    substring(0, 4) + getString(R.string.temperature_metricUnits));
                            lavWeatherIcon3.setAnimationFromUrl(hourForecastsList.get(4).getWeatherCondition().getIconAddress());
                            tvHour4.setText(hourForecastsList.get(5).getDate().toString().substring(11, 16));
                            tvTemp4.setText(hourForecastsList.get(5).getAvgTemperature_celsius().toString().
                                    substring(0, 4) + getString(R.string.temperature_metricUnits));
                            lavWeatherIcon4.setAnimationFromUrl(hourForecastsList.get(5).getWeatherCondition().getIconAddress());
                            tvHour5.setText(hourForecastsList.get(6).getDate().toString().substring(11, 16));
                            tvTemp5.setText(hourForecastsList.get(6).getAvgTemperature_celsius().toString().
                                    substring(0, 4) + getString(R.string.temperature_metricUnits));
                            lavWeatherIcon5.setAnimationFromUrl(hourForecastsList.get(6).getWeatherCondition().getIconAddress());
                            tvHour6.setText(hourForecastsList.get(7).getDate().toString().substring(11, 16));
                            tvTemp6.setText(hourForecastsList.get(7).getAvgTemperature_celsius().toString().
                                    substring(0, 4) + getString(R.string.temperature_metricUnits));
                            lavWeatherIcon6.setAnimationFromUrl(hourForecastsList.get(8).getWeatherCondition().getIconAddress());
                            tvFeelsLike.setText(hourForecastsList.get(1).getRealFeel_celsius().toString().
                                    substring(0, 4) + getString(R.string.temperature_metricUnits));
                            tvPressure.setText(hourForecastsList.get(1).getPressure_millibars().toString().
                                    substring(0, 7) + getString(R.string.milibar_pressureUnit));
                            tvUVIndex.setText(hourForecastsList.get(1).getUvIndex().toString().substring(0, 1));
                            tvProbabilityOfRain.setText(hourForecastsList.get(1).getPrecipitationProbability().
                                    toString()+ getString(R.string.probability_sign));
                            tvSunrise.setText(dayForecastsList.get(0).getSunrise().toString().substring(11, 16));
                            tvSunset.setText(dayForecastsList.get(0).getSunset().toString().substring(11, 16));
                        }
                    });

                    LineChartView chartView = root.findViewById(R.id.chart);
                    bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                        @Override
                        public void onStateChanged(@NonNull View bottomSheet, int newState) {
                            if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                                chartView.setVisibility(View.INVISIBLE);
                                tvToday.setText(R.string.tvToday);
                                tvHour1.setText(hourForecastsList.get(2).getDate().toString().substring(11, 16));
                                tvTemp1.setText(hourForecastsList.get(2).getAvgTemperature_celsius().toString().
                                        substring(0, 4) + getString(R.string.temperature_metricUnits));
                                lavWeatherIcon1.setAnimationFromUrl(hourForecastsList.get(2).getWeatherCondition().getIconAddress());
                                tvHour2.setText(hourForecastsList.get(3).getDate().toString().substring(11, 16));
                                tvTemp2.setText(hourForecastsList.get(3).getAvgTemperature_celsius().toString().
                                        substring(0, 4) + getString(R.string.temperature_metricUnits));
                                lavWeatherIcon2.setAnimationFromUrl(hourForecastsList.get(3).getWeatherCondition().getIconAddress());
                                tvHour3.setText(hourForecastsList.get(4).getDate().toString().substring(11, 16));
                                tvTemp3.setText(hourForecastsList.get(4).getAvgTemperature_celsius().toString().
                                        substring(0, 4) + getString(R.string.temperature_metricUnits));
                                lavWeatherIcon3.setAnimationFromUrl(hourForecastsList.get(4).getWeatherCondition().getIconAddress());
                                tvHour4.setText(hourForecastsList.get(5).getDate().toString().substring(11, 16));
                                tvTemp4.setText(hourForecastsList.get(5).getAvgTemperature_celsius().toString().
                                        substring(0, 4) + getString(R.string.temperature_metricUnits));
                                lavWeatherIcon4.setAnimationFromUrl(hourForecastsList.get(5).getWeatherCondition().getIconAddress());
                                tvHour5.setText(hourForecastsList.get(6).getDate().toString().substring(11, 16));
                                tvTemp5.setText(hourForecastsList.get(6).getAvgTemperature_celsius().toString().
                                        substring(0, 4) + getString(R.string.temperature_metricUnits));
                                lavWeatherIcon5.setAnimationFromUrl(hourForecastsList.get(6).getWeatherCondition().getIconAddress());
                                tvHour6.setText(hourForecastsList.get(7).getDate().toString().substring(11, 16));
                                tvTemp6.setText(hourForecastsList.get(7).getAvgTemperature_celsius().toString().
                                        substring(0, 4) + getString(R.string.temperature_metricUnits));
                                lavWeatherIcon6.setAnimationFromUrl(hourForecastsList.get(8).getWeatherCondition().getIconAddress());
                                hourPrediction6.setVisibility(View.VISIBLE);
                                hourPrediction5.setVisibility(View.VISIBLE);
                            } else if (BottomSheetBehavior.STATE_EXPANDED == newState) {
                                tvToday.setText(R.string.tvToday_daily);
                                tvHour1.setText(dayForecastsList.get(0).getDate().toString().substring(0, 4));
                                tvTemp1.setText("");
                                lavWeatherIcon1.setAnimationFromUrl(dayForecastsList.get(0).getWeatherCondition().getIconAddress());
                                tvHour2.setText(dayForecastsList.get(1).getDate().toString().substring(0, 4));
                                tvTemp2.setText("");
                                lavWeatherIcon2.setAnimationFromUrl(dayForecastsList.get(1).getWeatherCondition().getIconAddress());
                                tvHour3.setText(dayForecastsList.get(2).getDate().toString().substring(0, 4));
                                tvTemp3.setText("");
                                lavWeatherIcon3.setAnimationFromUrl(dayForecastsList.get(2).getWeatherCondition().getIconAddress());
                                tvHour4.setText(dayForecastsList.get(3).getDate().toString().substring(0, 4));
                                tvTemp4.setText("");
                                lavWeatherIcon4.setAnimationFromUrl(dayForecastsList.get(3).getWeatherCondition().getIconAddress());
                                hourPrediction5.setVisibility(View.INVISIBLE);
                                hourPrediction6.setVisibility(View.INVISIBLE);
                            } else if (BottomSheetBehavior.STATE_DRAGGING == newState) {
                                chartView.setVisibility(View.VISIBLE);
                                if (!chartConfigured) {
                                    List<AxisValue> axisXValues = getAxisXLables(hourForecastsList);
                                    List<PointValue> pointValuesAverage = getAxisPoints(hourForecastsList);
                                    List<PointValue> pointValuesOpenWeather = getAxisPoints(hourForecastsOpenWeather);
                                    List<PointValue> pointValuesWeatherBit = getAxisPoints(hourForecastsWeatherBit);
                                    //List<PointValue> pointValuesAccuWeather = getAxisPoints(hourForecastsAccuWeather);
                                    initLineChart(chartView, axisXValues, pointValuesAverage, pointValuesOpenWeather,
                                            pointValuesWeatherBit/*, pointValuesAccuWeather*/);
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
                               List<PointValue> pointValuesOpenWeather, List<PointValue> pointValuesWeatherBit/*,
                               List<PointValue> pointValuesAccuWeather*/) {
        Line lineAverage = new Line(pointValuesAverage).setColor(Color.parseColor("#1B1B1B"));
        Line lineOpenWeather = new Line(pointValuesOpenWeather).setColor(Color.parseColor("#E59866"));
        Line lineWeatherBit = new Line(pointValuesWeatherBit).setColor(Color.parseColor("#82E0AA"));
        //Line lineAccuWeather = new Line(pointValuesAccuWeather).setColor(Color.parseColor("#CD6155"));
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
        lineWeatherBit.setShape(ValueShape.CIRCLE);
        lineWeatherBit.setCubic(false);
        lineWeatherBit.setFilled(false);
        lineWeatherBit.setHasLabels(true);
        lineWeatherBit.setHasLines(true);
        lineWeatherBit.setHasPoints(false);
        /*lineAccuWeather.setShape(ValueShape.CIRCLE);//The shape of each data point on a broken line chart is circular here (there are three kinds: ValueShape. SQUARE ValueShape. CIRCLE ValueShape. DIAMOND)
        lineAccuWeather.setCubic(false);//Whether the curve is smooth, that is, whether it is a curve or a broken line
        lineAccuWeather.setFilled(false);//Whether or not to fill the area of the curve
        lineAccuWeather.setHasLabels(true);//Whether to add notes to the data coordinates of curves
        //      Line. setHasLabels OnlyForSelected (true); // Click on the data coordinates to prompt the data (set this line.setHasLabels(true); invalid)
        lineAccuWeather.setHasLines(true);//Whether to display with line or not. If it is false, there is no curve but point display
        lineAccuWeather.setHasPoints(false);*/
        lines.add(lineAverage);
        lines.add(lineOpenWeather);
        lines.add(lineWeatherBit);
        //lines.add(lineAccuWeather);
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
        /**Note: The following 7, 10 just represent a number to analogize.
         * At that time, it was to solve the fixed number of X-axis data. See (http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2);
         */
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
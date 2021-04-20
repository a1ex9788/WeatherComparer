package a1ex9788.dadm.weathercomparer.ui.forecast;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.util.DisplayMetrics;
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

    List<PointValue> mPointValues = new ArrayList<PointValue>();
    List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    String[] date = {"10-22", "11-22", "12-22", "1-22", "6-22", "5-23", "5-22", "6-22", "5-23", "5-22"};//Marking of X-axis
    int[] score = {50, 42, 90, 33, 10, 74, 22, 18, 79, 20};//Data Points of Charts
    LineChartView chartView;
    private ForecastViewModel forecastViewModel;
    private FragmentForecastBinding binding;
    private boolean chartConfigured;

    private LottieAnimationView animationView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentForecastBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        forecastViewModel = new ViewModelProvider(this).get(ForecastViewModel.class);

        animationView = root.findViewById(R.id.animationViewWeather);

        setNavigationDrawerButtonOnClickListener(root);

        setNavigationDrawerCheckedItem();

        setDefaultForecastData();

        recoverMapPlace();

        setCurrentForecastData();

        configureBottomSheet(root);

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
                getString(R.string.speed_metricUnits),
                "-",
                getString(R.string.temperature_metricUnits),
                "-",
                "%");
        binding.setCurrentWeather(currentWeather);
    }

    private void recoverMapPlace() {
        getParentFragmentManager().setFragmentResultListener(String.valueOf(R.string.listenerRequest_key), this, (key, bundle) -> {
            String name = bundle.getString(String.valueOf(R.string.nameFragmentParameter_key));
            String photo = bundle.getString(String.valueOf(R.string.photoFragmentParameter_key));
            String timeZone = bundle.getString(String.valueOf(R.string.timeZoneFragmentParameter_key));

            if (name == null || photo == null || timeZone == null) {
                // TODO: Show the current location forecast.
            } else {
                // TODO: Show the recovered info.
            }
        });

        /* To provide parameters to the ForecastFragment the following code is needed:
            Bundle result = new Bundle();
            result.putString(String.valueOf(R.string.nameFragmentParameter_key), "Valencia");
            getParentFragmentManager().setFragmentResult(String.valueOf(R.string.listenerRequest_key), result);
         */
    }

    private void setCurrentForecastData() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();

                try {
                    double latitude = 39.289, longitude = -0.799;
                    HourForecast hourForecast = forecastViewModel.getCurrentWeather(latitude, longitude);

                    MapPlace mapPlace = new MapPlace();
                    mapPlace.setName("Valencia");
                    mapPlace.setTimeZone("16:14 Mar 16");
                    binding.setPlace(mapPlace);

                    CurrentWeather currentWeather = new CurrentWeather(
                            hourForecast.getWeatherCondition().getText(),
                            roundToOneDecimal(hourForecast.getWindSpeed_kilometersPerHour()) + "",
                            getString(R.string.speed_metricUnits),
                            roundToOneDecimal(hourForecast.getAvgTemperature_celsius()) + "",
                            getString(R.string.temperature_metricUnits),
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

                    ((MainActivity) getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvHour1.setText(hourForecastsList.get(3).getDate().toString().substring(11, 16));
                            tvTemp1.setText(hourForecastsList.get(3).getAvgTemperature_celsius().toString().
                                    substring(0, 4) + getString(R.string.temperature_metricUnits));
                            lavWeatherIcon1.setAnimationFromUrl(hourForecastsList.get(3).getWeatherCondition().getIconAddress());
                            tvHour2.setText(hourForecastsList.get(4).getDate().toString().substring(11, 16));
                            tvTemp2.setText(hourForecastsList.get(4).getAvgTemperature_celsius().toString().
                                    substring(0, 4) + getString(R.string.temperature_metricUnits));
                            lavWeatherIcon2.setAnimationFromUrl(hourForecastsList.get(4).getWeatherCondition().getIconAddress());
                            tvHour3.setText(hourForecastsList.get(5).getDate().toString().substring(11, 16));
                            tvTemp3.setText(hourForecastsList.get(5).getAvgTemperature_celsius().toString().
                                    substring(0, 4) + getString(R.string.temperature_metricUnits));
                            lavWeatherIcon3.setAnimationFromUrl(hourForecastsList.get(5).getWeatherCondition().getIconAddress());
                            tvHour4.setText(hourForecastsList.get(6).getDate().toString().substring(11, 16));
                            tvTemp4.setText(hourForecastsList.get(6).getAvgTemperature_celsius().toString().
                                    substring(0, 4) + getString(R.string.temperature_metricUnits));
                            lavWeatherIcon4.setAnimationFromUrl(hourForecastsList.get(6).getWeatherCondition().getIconAddress());
                            tvHour5.setText(hourForecastsList.get(7).getDate().toString().substring(11, 16));
                            tvTemp5.setText(hourForecastsList.get(7).getAvgTemperature_celsius().toString().
                                    substring(0, 4) + getString(R.string.temperature_metricUnits));
                            lavWeatherIcon5.setAnimationFromUrl(hourForecastsList.get(7).getWeatherCondition().getIconAddress());
                            tvHour6.setText(hourForecastsList.get(8).getDate().toString().substring(11, 16));
                            tvTemp6.setText(hourForecastsList.get(8).getAvgTemperature_celsius().toString().
                                    substring(0, 4) + getString(R.string.temperature_metricUnits));
                            lavWeatherIcon6.setAnimationFromUrl(hourForecastsList.get(8).getWeatherCondition().getIconAddress());
                            tvFeelsLike.setText(dayForecastsList.get(0).getRealFeel_celsius().toString().
                                    substring(0, 4) + getString(R.string.temperature_metricUnits));
                            tvPressure.setText(dayForecastsList.get(0).getPressure_millibars().toString() +
                                    getString(R.string.milibar_pressureUnit));
                            tvUVIndex.setText(dayForecastsList.get(0).getUvIndex().toString().substring(0, 1));
                            tvProbabilityOfRain.setText(dayForecastsList.get(0).getPrecipitationProbability().
                                    toString().substring(0, 5) + getString(R.string.probability_sign));
                            tvSunrise.setText(dayForecastsList.get(0).getSunrise().toString().substring(11, 16));
                            tvSunset.setText(dayForecastsList.get(0).getSunset().toString().substring(11, 16));
                        }
                    });

                    chartView = root.findViewById(R.id.chart);
                    bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                        @Override
                        public void onStateChanged(@NonNull View bottomSheet, int newState) {
                            if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                                chartView.setVisibility(View.INVISIBLE);
                                tvToday.setText(R.string.tvToday);
                                tvHour1.setText(hourForecastsList.get(3).getDate().toString().substring(11, 16));
                                tvTemp1.setText(hourForecastsList.get(3).getAvgTemperature_celsius().toString().
                                        substring(0, 4) + getString(R.string.temperature_metricUnits));
                                lavWeatherIcon1.setAnimationFromUrl(hourForecastsList.get(3).getWeatherCondition().getIconAddress());
                                tvHour2.setText(hourForecastsList.get(4).getDate().toString().substring(11, 16));
                                tvTemp2.setText(hourForecastsList.get(4).getAvgTemperature_celsius().toString().
                                        substring(0, 4) + getString(R.string.temperature_metricUnits));
                                lavWeatherIcon2.setAnimationFromUrl(hourForecastsList.get(4).getWeatherCondition().getIconAddress());
                                tvHour3.setText(hourForecastsList.get(5).getDate().toString().substring(11, 16));
                                tvTemp3.setText(hourForecastsList.get(5).getAvgTemperature_celsius().toString().
                                        substring(0, 4) + getString(R.string.temperature_metricUnits));
                                lavWeatherIcon3.setAnimationFromUrl(hourForecastsList.get(5).getWeatherCondition().getIconAddress());
                                tvHour4.setText(hourForecastsList.get(6).getDate().toString().substring(11, 16));
                                tvTemp4.setText(hourForecastsList.get(6).getAvgTemperature_celsius().toString().
                                        substring(0, 4) + getString(R.string.temperature_metricUnits));
                                lavWeatherIcon4.setAnimationFromUrl(hourForecastsList.get(6).getWeatherCondition().getIconAddress());
                                tvHour5.setText(hourForecastsList.get(7).getDate().toString().substring(11, 16));
                                tvTemp5.setText(hourForecastsList.get(7).getAvgTemperature_celsius().toString().
                                        substring(0, 4) + getString(R.string.temperature_metricUnits));
                                lavWeatherIcon5.setAnimationFromUrl(hourForecastsList.get(7).getWeatherCondition().getIconAddress());
                                tvHour6.setText(hourForecastsList.get(8).getDate().toString().substring(11, 16));
                                tvTemp6.setText(hourForecastsList.get(8).getAvgTemperature_celsius().toString().
                                        substring(0, 4) + getString(R.string.temperature_metricUnits));
                                lavWeatherIcon6.setAnimationFromUrl(hourForecastsList.get(8).getWeatherCondition().getIconAddress());
                                hourPrediction6.setVisibility(View.VISIBLE);
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
                                tvHour5.setText(dayForecastsList.get(4).getDate().toString().substring(0, 4));
                                tvTemp5.setText("");
                                lavWeatherIcon5.setAnimationFromUrl(dayForecastsList.get(4).getWeatherCondition().getIconAddress());
                                hourPrediction6.setVisibility(View.INVISIBLE);
                            } else if (BottomSheetBehavior.STATE_DRAGGING == newState) {
                                chartView.setVisibility(View.VISIBLE);
                                if (!chartConfigured) {
                                    getAxisXLables();
                                    getAxisPoints();
                                    initLineChart();
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

    private void getAxisXLables() {
        for (int i = 0; i < date.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }
    }

    private void getAxisPoints() {
        for (int i = 0; i < score.length; i++) {
            mPointValues.add(new PointValue(i, score[i]));
        }
    }

    private void initLineChart() {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //The color of the broken line (orange)
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//The shape of each data point on a broken line chart is circular here (there are three kinds: ValueShape. SQUARE ValueShape. CIRCLE ValueShape. DIAMOND)
        line.setCubic(false);//Whether the curve is smooth, that is, whether it is a curve or a broken line
        line.setFilled(false);//Whether or not to fill the area of the curve
        line.setHasLabels(true);//Whether to add notes to the data coordinates of curves
        //      Line. setHasLabels OnlyForSelected (true); // Click on the data coordinates to prompt the data (set this line.setHasLabels(true); invalid)
        line.setHasLines(true);//Whether to display with line or not. If it is false, there is no curve but point display
        line.setHasPoints(false);//Whether to display a dot if it is false, there is no origin but only a dot (each data point is a large dot)
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //Axis of coordinates
        Axis axisX = new Axis(); //X axis
        axisX.setHasTiltedLabels(true);  //Is the font on the X axis oblique or straight, and true oblique?
        axisX.setTextColor(Color.GRAY);  //Setting font color
        //axisX.setName("date"); // table name
        axisX.setTextSize(10);//Set font size
        axisX.setMaxLabelChars(8); //Up to a few X-axis coordinates, which means that your scaling allows the number of data on the X-axis to be 7<=x<=mAxisXValues.length.
        axisX.setValues(mAxisXValues);  //Fill in the coordinate name of the X-axis
        data.setAxisXBottom(axisX); //The x-axis is at the bottom.
        //Data. setAxisXTop (axisX); //x axis at top
        axisX.setHasLines(true); //x-axis dividing line

        // The Y-axis automatically sets the Y-axis limit according to the size of the data (below I will give a solution for fixing the number of Y-axis data)
        Axis axisY = new Axis();  //Y axis
        axisY.setName("");//y axis annotation
        axisY.setTextSize(10);//Set font size
        data.setAxisYLeft(axisY);  //The Y-axis is set on the left
        //Data. setAxisYRight (axisY); the // Y axis is set to the right

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

}
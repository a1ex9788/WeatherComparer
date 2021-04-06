package a1ex9788.dadm.weathercomparer.ui.forecast;

import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

import a1ex9788.dadm.weathercomparer.MainActivity;
import a1ex9788.dadm.weathercomparer.R;
import a1ex9788.dadm.weathercomparer.databinding.FragmentForecastBinding;
import a1ex9788.dadm.weathercomparer.model.DayForecast;
import a1ex9788.dadm.weathercomparer.model.WeatherCondition;

public class ForecastFragment extends Fragment {

    private ForecastViewModel forecastViewModel;
    private FragmentForecastBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentForecastBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (root == null) {
            return root;
        }

        forecastViewModel = new ViewModelProvider(this).get(ForecastViewModel.class);

        setNavigationDrawerButtonOnClickListener(root);

        setDefaultForecastData(root);

        setDailyForecastData(root);

        configureBottomSheet(root);

        return root;
    }

    private void setDefaultForecastData(View root) {
        // Set default data. It will be seen before the real one is loaded and in case of error.
        setWeatherConditionAnimation(root, WeatherCondition.UnknownPrecipitation);
        binding.setWeatherConditionText("No data");
        binding.setWindSpeed("-");
        binding.setAverageTemperature("-");
        binding.setRainProbability("-");
    }

    private void setDailyForecastData(View root) {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();

                try {
                    List<DayForecast> dailyForecast = forecastViewModel.getAverageDailyForecast();
                    DayForecast dayForecast = dailyForecast.get(0);

                    binding.setWeatherConditionText(dayForecast.getWeatherCondition().getText());
                    binding.setWindSpeed(roundToOneDecimal(dayForecast.getWindSpeed_kilometersPerHour()) + " " + R.string.speed_metricUnits);
                    binding.setAverageTemperature(roundToOneDecimal(dayForecast.getAvgTemperature_celsius()) + " " + R.string.temperature_metricUnits);
                    binding.setRainProbability(roundToOneDecimal(dayForecast.getPrecipitationProbability()) + " " + R.string.probability_sign);

                    setWeatherConditionAnimation(root, dayForecast.getWeatherCondition());
                } catch (Exception e) {
                    Toast.makeText(getActivity().getBaseContext(), R.string.toast_forecastError, Toast.LENGTH_LONG).show();
                }
            }
        }.start();
    }

    private void setNavigationDrawerButtonOnClickListener(View root) {
        ImageButton ibNavigationDrawer = root.findViewById(R.id.ibNavigationDrawer);
        ibNavigationDrawer.setOnClickListener(v -> ((MainActivity) getActivity()).openNavigationDrawer());
    }

    private void configureBottomSheet(View root) {
        ConstraintLayout clBottomSheet = root.findViewById(R.id.clBottomSheet);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(clBottomSheet);
        TextView tvToday = root.findViewById(R.id.tvToday);
        ConstraintLayout hourPrediction1 = root.findViewById(R.id.hourPrediction1);
        ConstraintLayout hourPrediction2 = root.findViewById(R.id.hourPrediction2);
        ConstraintLayout hourPrediction3 = root.findViewById(R.id.hourPrediction3);
        ConstraintLayout hourPrediction4 = root.findViewById(R.id.hourPrediction4);
        BarChart bcBottomSheet = root.findViewById(R.id.bcBottomSheet);

        ArrayList<BarEntry> lineEntries = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            float y = (int) (Math.random() * 8) + 1;
            lineEntries.add(new BarEntry((float) i, (float) y));
        }

        BarDataSet barDataSet = new BarDataSet(lineEntries, "Chart");
        BarData barData = new BarData();
        barData.addDataSet(barDataSet);
        bcBottomSheet.setData(barData);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                    hourPrediction1.setVisibility(View.VISIBLE);
                    hourPrediction2.setVisibility(View.VISIBLE);
                    hourPrediction3.setVisibility(View.VISIBLE);
                    hourPrediction4.setVisibility(View.VISIBLE);
                    bcBottomSheet.setVisibility(View.INVISIBLE);
                    tvToday.setVisibility(View.VISIBLE);
                } else if (BottomSheetBehavior.STATE_EXPANDED == newState) {
                    hourPrediction1.setVisibility(View.INVISIBLE);
                    hourPrediction2.setVisibility(View.INVISIBLE);
                    hourPrediction3.setVisibility(View.INVISIBLE);
                    hourPrediction4.setVisibility(View.INVISIBLE);
                    bcBottomSheet.setVisibility(View.VISIBLE);
                    tvToday.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private double roundToOneDecimal(double d) {
        return Math.round(d * 10.0) / 10.0;
    }

    private void setWeatherConditionAnimation(View root, WeatherCondition weatherCondition) {
        LottieAnimationView animationView = root.findViewById(R.id.animationViewWeather);
        animationView.setAnimationFromUrl(weatherCondition.getIconAddress());
    }

}
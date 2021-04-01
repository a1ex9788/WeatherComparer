package a1ex9788.dadm.weathercomparer.ui.forecast;

import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

import a1ex9788.dadm.weathercomparer.MainActivity;
import a1ex9788.dadm.weathercomparer.R;
import a1ex9788.dadm.weathercomparer.databinding.FragmentForecastBinding;
import a1ex9788.dadm.weathercomparer.model.DayForecast;
import a1ex9788.dadm.weathercomparer.model.WeatherCondition;

public class ForecastFragment extends Fragment {

    private final String SPEED_UNIT = "km/h", TEMPERATURE_UNIT = "°C", PROBABILITY_SIGN = "%";

    private ForecastViewModel forecastViewModel;
    private ImageButton ibNavigationDrawer;
    private FragmentForecastBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentForecastBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set default data. It will be seen before the real one is loaded and in case of error.
        setWeatherConditionAnimation(root, WeatherCondition.UnknownPrecipitation);
        binding.setWeatherConditionText("No data");
        binding.setWindSpeed("-");
        binding.setAverageTemperature("-");
        binding.setRainProbability("-");

        forecastViewModel = new ViewModelProvider(this).get(ForecastViewModel.class);

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();

                try {
                    List<DayForecast> dailyForecast = forecastViewModel.getAverageDailyForecast();
                    setDailyForecastData(root, dailyForecast.get(0));
                } catch (Exception e) {
                    Toast.makeText(getActivity().getBaseContext(), R.string.toast_forecastError, Toast.LENGTH_LONG).show();
                }
            }
        }.start();

        this.ibNavigationDrawer = root.findViewById(R.id.ibNavigationDrawer);
        this.ibNavigationDrawer.setOnClickListener(v -> ((MainActivity) getActivity()).openNavigationDrawer());

        return root;
    }

    private void setDailyForecastData(View root, DayForecast dayForecast) {
        binding.setWeatherConditionText(dayForecast.getWeatherCondition().getText());
        binding.setWindSpeed(roundToOneDecimal(dayForecast.getWindSpeed_kilometersPerHour()) + " " + SPEED_UNIT);
        binding.setAverageTemperature(roundToOneDecimal(dayForecast.getAvgTemperature_celsius()) + " " + TEMPERATURE_UNIT);
        binding.setRainProbability(roundToOneDecimal(dayForecast.getPrecipitationProbability()) + " " + PROBABILITY_SIGN);

        setWeatherConditionAnimation(root, dayForecast.getWeatherCondition());
    }

    private double roundToOneDecimal(double d) {
        return Math.round(d * 10.0) / 10.0;
    }

    private void setWeatherConditionAnimation(View root, WeatherCondition weatherCondition) {
        LottieAnimationView animationView = root.findViewById(R.id.animationViewWeather);
        animationView.setAnimationFromUrl(weatherCondition.getIconAddress());
    }

}
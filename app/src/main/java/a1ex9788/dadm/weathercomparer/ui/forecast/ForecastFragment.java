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

    private ForecastViewModel forecastViewModel;
    private ImageButton ibNavigationDrawer;
    private FragmentForecastBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentForecastBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set default data
        setWeatherConditionAnimation(root, WeatherCondition.UnknownPrecipitation);
        binding.setWeatherConditionText("No data");
        binding.setWindSpeed("0 km/h");
        binding.setAverageTemperature("0 ºC");
        binding.setRainProbability("0 %");

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
        binding.setWindSpeed(roundToTwoDecimals(dayForecast.getWindSpeed_kilometersPerHour()) + " km/ h");
        binding.setAverageTemperature(roundToTwoDecimals(dayForecast.getAvgTemperature_celsius()) + " ºC");
        binding.setRainProbability(roundToTwoDecimals(dayForecast.getPrecipitationProbability()) + " %");

        setWeatherConditionAnimation(root, dayForecast.getWeatherCondition());
    }

    private double roundToTwoDecimals(double d) {
        return Math.round(d * 100 / 100);
    }

    private void setWeatherConditionAnimation(View root, WeatherCondition weatherCondition) {
        LottieAnimationView animationView = root.findViewById(R.id.animationViewWeather);
        animationView.setAnimationFromUrl(weatherCondition.getIconAddress());
    }

}
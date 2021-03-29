package a1ex9788.dadm.weathercomparer.ui.forecast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_forecast, container, false);

        forecastViewModel = new ViewModelProvider(this).get(ForecastViewModel.class);

        // Set default data
        binding = FragmentForecastBinding.inflate(inflater, container, false);
        binding.setWeatherConditionText(WeatherCondition.Clear.getText());
        binding.setWindSpeed("0 km/h");
        binding.setAverageTemperature("0 ºC");
        binding.setRainProbability("0%");

        new Thread() {
            @Override
            public void run() {
                try {
                    List<DayForecast> dailyForecast = forecastViewModel.getAverageDailyForecast();

                    setDailyForecastData(root, dailyForecast.get(0));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        this.ibNavigationDrawer = root.findViewById(R.id.ibNavigationDrawer);
        this.ibNavigationDrawer.setOnClickListener(v -> ((MainActivity) getActivity()).openNavigationDrawer());

        return root;
    }

    private void setDailyForecastData(View root, DayForecast dayForecast) {
        binding.setWeatherConditionText(dayForecast.getWeatherCondition().getText());
        binding.setWindSpeed(dayForecast.getWindSpeed_kilometersPerHour() + " km/ h");
        binding.setAverageTemperature(dayForecast.getAvgTemperature_celsius() + " ºC");
        binding.setRainProbability(dayForecast.getPrecipitationProbability() + " 0%");

        setWeatherConditionAnimation(root, dayForecast.getWeatherCondition());
    }

    private void setWeatherConditionAnimation(View root, WeatherCondition weatherCondition) {
        final LottieAnimationView animationView = root.findViewById(R.id.animationViewWeather);
        animationView.setAnimationFromUrl(weatherCondition.getIconAddress());
    }

}
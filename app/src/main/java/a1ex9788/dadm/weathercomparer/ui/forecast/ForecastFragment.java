package a1ex9788.dadm.weathercomparer.ui.forecast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;

import a1ex9788.dadm.weathercomparer.MainActivity;
import a1ex9788.dadm.weathercomparer.R;
import a1ex9788.dadm.weathercomparer.model.WeatherCondition;

public class ForecastFragment extends Fragment {

    private ForecastViewModel forecastViewModel;
    private ImageButton ibNavigationDrawer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_forecast, container, false);

        forecastViewModel = new ViewModelProvider(this).get(ForecastViewModel.class);

        new GetDayForecastThread().start();
        new GetHourForecastThread().start();

        WeatherCondition weatherCondition = WeatherCondition.Fog;
        final LottieAnimationView animationView = root.findViewById(R.id.animationViewWeather);
        final TextView mWeatherTextView = root.findViewById(R.id.weatherTextView);
        mWeatherTextView.setText(weatherCondition.getText());
        animationView.setAnimationFromUrl(weatherCondition.getIconAddress());

        this.ibNavigationDrawer = root.findViewById(R.id.ibNavigationDrawer);
        this.ibNavigationDrawer.setOnClickListener(v -> ((MainActivity) getActivity()).openNavigationDrawer());

        return root;
    }

    private class GetDayForecastThread extends Thread {

        @Override
        public void run() {
            try {
                forecastViewModel.getAccuWeatherDailyForecast();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private class GetHourForecastThread extends Thread {

        @Override
        public void run() {
            try {
                forecastViewModel.getAccuWeatherHourlyForecast();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
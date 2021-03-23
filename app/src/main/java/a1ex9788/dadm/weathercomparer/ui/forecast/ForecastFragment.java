package a1ex9788.dadm.weathercomparer.ui.forecast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import a1ex9788.dadm.weathercomparer.R;
import a1ex9788.dadm.weathercomparer.model.DayForecast;
import a1ex9788.dadm.weathercomparer.model.HourForecast;
import a1ex9788.dadm.weathercomparer.webServices.WeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.accuWeather.AccuWeatherForecast;

public class ForecastFragment extends Fragment {

    private ForecastViewModel forecastViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        forecastViewModel =
                new ViewModelProvider(this).get(ForecastViewModel.class);
        View root = inflater.inflate(R.layout.fragment_forecast, container, false);
        final TextView textView = root.findViewById(R.id.text_forecast);
        forecastViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new MyThread().start();
    }

    private class MyThread extends Thread {

        @Override
        public void run() {
            try {
                WeatherForecast weatherForecast = new AccuWeatherForecast(39.289, -0.799);
                //WeatherForecast weatherForecast = new OpenWeatherForecast(39.289, -0.799);
                //WeatherForecast weatherForecast = new WeatherBitForecast(39.289, -0.799);
                List<DayForecast> dailyForecast = weatherForecast.getDailyForecast();
                List<HourForecast> hourlyForecast = weatherForecast.getHourlyForecast();
                int nothing = 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
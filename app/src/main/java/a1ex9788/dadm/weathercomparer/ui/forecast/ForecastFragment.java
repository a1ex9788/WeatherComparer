package a1ex9788.dadm.weathercomparer.ui.forecast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;

import java.util.HashMap;

import a1ex9788.dadm.weathercomparer.MainActivity;
import a1ex9788.dadm.weathercomparer.R;
import a1ex9788.dadm.weathercomparer.model.WeatherCondition;

public class ForecastFragment extends Fragment {

    private ForecastViewModel forecastViewModel;
    private ConstraintLayout clBottomSheet;
    private ImageButton ibNavigationDrawer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        forecastViewModel =
                new ViewModelProvider(this).get(ForecastViewModel.class);

        new GetDayForecastThread().start();
        new GetHourForecastThread().start();

        // Dictionary for catching the value of WeatherCondition
        HashMap<WeatherCondition, String> dicWeatherCondition = new HashMap();
        dicWeatherCondition.put(WeatherCondition.Clear, "https://assets7.lottiefiles.com/temp/lf20_Stdaec.json");
        dicWeatherCondition.put(WeatherCondition.Clouds, "https://assets7.lottiefiles.com/temp/lf20_VAmWRg.json");
        dicWeatherCondition.put(WeatherCondition.BrokenClouds, "https://assets5.lottiefiles.com/temp/lf20_dgjK9i.json");
        dicWeatherCondition.put(WeatherCondition.Rain, "https://assets6.lottiefiles.com/temp/lf20_rpC1Rd.json");
        dicWeatherCondition.put(WeatherCondition.Snow, "https://assets7.lottiefiles.com/temp/lf20_WtPCZs.json");
        dicWeatherCondition.put(WeatherCondition.Fog, "https://assets7.lottiefiles.com/temp/lf20_kOfPKE.json");
        dicWeatherCondition.put(WeatherCondition.Flurries, "https://assets9.lottiefiles.com/packages/lf20_J8EPQ1.json");
        dicWeatherCondition.put(WeatherCondition.Thunderstorm, "https://assets7.lottiefiles.com/temp/lf20_Kuot2e.json");
        dicWeatherCondition.put(WeatherCondition.ThunderstormWithRain, "https://assets7.lottiefiles.com/temp/lf20_XkF78Y.json");
        dicWeatherCondition.put(WeatherCondition.UnknownPrecipitation, "https://assets10.lottiefiles.com/temp/lf20_9gY9Yf.json");

        View root = inflater.inflate(R.layout.fragment_forecast, container, false);

        // All the different cases of WeatherCondition
        final LottieAnimationView animationView = root.findViewById(R.id.animationViewWeather);
        final TextView mWeatherTextView = root.findViewById(R.id.weatherTextView);
        WeatherCondition weatherCondition = WeatherCondition.Clear;
        // TODO: Substitute the strings of the weather conditions for a resource in order to enable the language change.
        switch (weatherCondition) {
            case Clear:
                mWeatherTextView.setText("Sunny");
                break;
            case Clouds:
                mWeatherTextView.setText("Cloudy");
                break;
            case BrokenClouds:
                mWeatherTextView.setText("Broken Clouds");
                break;
            case Rain:
                mWeatherTextView.setText("Rainy");
                break;
            case Snow:
                animationView.setSpeed((float) 1.8);
                mWeatherTextView.setText("Snowy");
                break;
            case Fog:
                mWeatherTextView.setText("Foggy");
                break;
            case Flurries:
                mWeatherTextView.setText("Flurries");
                break;
            case Thunderstorm:
                mWeatherTextView.setText("Stormy");
                break;
            case ThunderstormWithRain:
                mWeatherTextView.setText("Stormy and Rainy");
                break;
            case UnknownPrecipitation:
                mWeatherTextView.setText("Unknown Precipitation");
                break;

            default:
                weatherCondition = WeatherCondition.UnknownPrecipitation;
                mWeatherTextView.setText("Unknown");
                break;
        }

        animationView.setAnimationFromUrl(dicWeatherCondition.get(weatherCondition));

        this.ibNavigationDrawer = root.findViewById(R.id.ibNavigationDrawer);
        this.ibNavigationDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).openNavigationDrawer();
            }
        });

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
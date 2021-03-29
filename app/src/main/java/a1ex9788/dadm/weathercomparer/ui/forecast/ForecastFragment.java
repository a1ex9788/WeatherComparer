package a1ex9788.dadm.weathercomparer.ui.forecast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.HashMap;

import a1ex9788.dadm.weathercomparer.R;

public class ForecastFragment extends Fragment {

    private ForecastViewModel forecastViewModel;
    private ConstraintLayout clBottomSheet;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        new GetDayForecastThread().start();
        new GetHourForecastThread().start();

        // Dictionary for catching the value of WeatherCondition
        HashMap<String, String> dicWeatherCondition = new HashMap<String, String>();
        dicWeatherCondition.put("ThunderstormWithRain", "https://assets7.lottiefiles.com/temp/lf20_XkF78Y.json");
        dicWeatherCondition.put("Thunderstorm", "https://assets7.lottiefiles.com/temp/lf20_Kuot2e.json");
        dicWeatherCondition.put("Fog", "https://assets7.lottiefiles.com/temp/lf20_kOfPKE.json");
        dicWeatherCondition.put("Rain", "https://assets6.lottiefiles.com/temp/lf20_rpC1Rd.json");
        dicWeatherCondition.put("HeavyRain", "https://assets4.lottiefiles.com/packages/lf20_bgmkqg6b.json");
        dicWeatherCondition.put("SnowRain", "https://assets4.lottiefiles.com/packages/lf20_uhdaqfkg.json");
        dicWeatherCondition.put("LightSnow", "https://assets7.lottiefiles.com/temp/lf20_BSVgyt.json");
        dicWeatherCondition.put("Snow", "https://assets7.lottiefiles.com/temp/lf20_WtPCZs.json");
        dicWeatherCondition.put("Flurries", "https://assets9.lottiefiles.com/packages/lf20_J8EPQ1.json");
        dicWeatherCondition.put("Clouds", "https://assets7.lottiefiles.com/temp/lf20_VAmWRg.json");
        dicWeatherCondition.put("BrokenClouds", "https://assets5.lottiefiles.com/temp/lf20_dgjK9i.json");
        dicWeatherCondition.put("UnknownPrecipitation", "https://assets10.lottiefiles.com/temp/lf20_9gY9Yf.json");
        dicWeatherCondition.put("Sunny", "https://assets7.lottiefiles.com/temp/lf20_Stdaec.json");

        forecastViewModel =
                new ViewModelProvider(this).get(ForecastViewModel.class);
        View root = inflater.inflate(R.layout.fragment_forecast, container, false);

        //All the different cases of WeatherCondition
        final LottieAnimationView animationView = root.findViewById(R.id.animationViewWeather);
        final TextView mWeatherTextView = root.findViewById(R.id.weatherTextView);
        String conditionWeather = "BrokenClouds";
        switch (conditionWeather) {
            case "Sunny":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("Sunny"));
                mWeatherTextView.setText("Sunny");
                break;
            case "UnknownPrecipitation":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("UnknownPrecipitation"));
                mWeatherTextView.setText("Unknown Precipitation");
                break;
            case "Clouds":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("Clouds"));
                mWeatherTextView.setText("Cloudy");
                break;
            case "BrokenClouds":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("BrokenClouds"));
                mWeatherTextView.setText("Broken Clouds");
                break;
            case "Flurries":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("Flurries"));
                mWeatherTextView.setText("Flurries");
                break;
            case "Snow":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("Snow"));
                animationView.setSpeed((float) 1.8);
                mWeatherTextView.setText("Snowy");
                break;
            case "LightSnow":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("LightSnow"));
                mWeatherTextView.setText("Light Snow");
                break;
            case "SnowRain":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("SnowRain"));
                mWeatherTextView.setText("Snowy and Rainy");
                break;
            case "HeavyRain":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("HeavyRain"));
                mWeatherTextView.setText("Very Rainy");
                break;
            case "Rain":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("Rain"));
                mWeatherTextView.setText("Rainy");
                break;
            case "Fog":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("Fog"));
                mWeatherTextView.setText("Foggy");
                break;
            case "Thunderstorm":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("Thunderstorm"));
                mWeatherTextView.setText("Stormy");
                break;
            case "ThunderstormWithRain":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("ThunderstormWithRain"));
                mWeatherTextView.setText("Stormy and Rainy");

                break;
        }

        this.clBottomSheet = root.findViewById(R.id.clBottomSheet);
        final BottomSheetBehavior clBottomSheetBehaviour = BottomSheetBehavior.from(this.clBottomSheet);
        clBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_DRAGGING);
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
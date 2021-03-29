package a1ex9788.dadm.weathercomparer.ui.forecast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.HashMap;
import java.util.List;

import a1ex9788.dadm.weathercomparer.R;
import a1ex9788.dadm.weathercomparer.model.DayForecast;
import a1ex9788.dadm.weathercomparer.model.HourForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.WeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.accuWeather.AccuWeatherForecast;

public class ForecastFragment extends Fragment {

    private ForecastViewModel forecastViewModel;
    private ConstraintLayout clBottomSheet;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Dictionary for catching the value of WeatherCondition
        HashMap<String, String> dicWeatherCondition = new HashMap<String, String>();
        dicWeatherCondition.put("ThunderstormWithRain","https://assets7.lottiefiles.com/temp/lf20_XkF78Y.json");
        dicWeatherCondition.put("Thunderstorm","https://assets7.lottiefiles.com/temp/lf20_Kuot2e.json");
        dicWeatherCondition.put("Fog","https://assets7.lottiefiles.com/temp/lf20_kOfPKE.json");
        dicWeatherCondition.put("Rain","https://assets6.lottiefiles.com/temp/lf20_rpC1Rd.json");
        dicWeatherCondition.put("HeavyRain","https://assets4.lottiefiles.com/packages/lf20_bgmkqg6b.json");
        dicWeatherCondition.put("SnowRain","https://assets4.lottiefiles.com/packages/lf20_uhdaqfkg.json");
        dicWeatherCondition.put("LightSnow","https://assets7.lottiefiles.com/temp/lf20_BSVgyt.json");
        dicWeatherCondition.put("Snow","https://assets7.lottiefiles.com/temp/lf20_WtPCZs.json");
        dicWeatherCondition.put("Flurries","https://assets9.lottiefiles.com/packages/lf20_J8EPQ1.json");
        dicWeatherCondition.put("Clouds","https://assets7.lottiefiles.com/temp/lf20_VAmWRg.json");
        dicWeatherCondition.put("UnknownPrecipitation","https://assets10.lottiefiles.com/temp/lf20_9gY9Yf.json");
        dicWeatherCondition.put("Sunny","https://assets7.lottiefiles.com/temp/lf20_Stdaec.json");

        forecastViewModel =
                new ViewModelProvider(this).get(ForecastViewModel.class);
        View root = inflater.inflate(R.layout.fragment_forecast, container, false);

        //All the different cases of WeatherCondition
        final LottieAnimationView animationView = root.findViewById(R.id.animationViewWeather);
        String conditionWeather ="ThunderstormWithRain";
        switch (conditionWeather) {
            case "Sunny":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("Sunny"));
                break;
            case "UnknownPrecipitation":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("UnknownPrecipitation"));
                break;
            case "Clouds":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("Clouds"));
                break;
            case "Flurries":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("Flurries"));
                break;
            case "Snow":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("Snow"));
                break;
            case "LightSnow":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("LightSnow"));
                break;
            case "SnowRain":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("SnowRain"));
                break;
            case "HeavyRain":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("HeavyRain"));
                break;
            case "Rain":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("Rain"));
                break;
            case "Fog":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("Fog"));
                break;
            case "Thunderstorm":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("Thunderstorm"));
                break;
            case "ThunderstormWithRain":
                animationView.setAnimationFromUrl(dicWeatherCondition.get("ThunderstormWithRain"));

                break;
        }

        this.clBottomSheet = root.findViewById(R.id.clBottomSheet);
        final BottomSheetBehavior clBottomSheetBehaviour = BottomSheetBehavior.from(this.clBottomSheet);
        clBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_DRAGGING);
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
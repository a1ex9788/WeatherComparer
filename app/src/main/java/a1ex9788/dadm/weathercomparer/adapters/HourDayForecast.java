package a1ex9788.dadm.weathercomparer.adapters;

import com.airbnb.lottie.LottieAnimationView;

public class HourDayForecast {

    private String hour, temperature;
    private String lavWeatherIcon;

    public HourDayForecast() { }

    public HourDayForecast(String hour, String temperature, String lavWeatherIcon){
        this.hour = hour;
        this.temperature = temperature;
        this.lavWeatherIcon = lavWeatherIcon;
    }

    public String getMoreInfoTitle() { return this.hour; }

    public String getMoreInfoValue() { return this.temperature; }

    public String getLottieAnimationView() { return this.lavWeatherIcon; }

}

package a1ex9788.dadm.weathercomparer.model;

import java.util.List;

public class HourlyForecast {

    private List<HourForecast> hourForecasts;

    public HourlyForecast(List<HourForecast> hourForecasts) {
        this.hourForecasts = hourForecasts;
    }

    public List<HourForecast> getHourForecasts() {
        return hourForecasts;
    }

}

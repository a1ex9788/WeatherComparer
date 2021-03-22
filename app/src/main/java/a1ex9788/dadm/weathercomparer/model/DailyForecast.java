package a1ex9788.dadm.weathercomparer.model;

import java.util.List;

public class DailyForecast {

    private List<DayForecast> dayForecasts;

    public DailyForecast(List<DayForecast> dayForecasts) {
        this.dayForecasts = dayForecasts;
    }

    public List<DayForecast> getDayForecasts() {
        return dayForecasts;
    }

}

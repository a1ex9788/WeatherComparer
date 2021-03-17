package a1ex9788.dadm.weathercomparer.model;

import java.util.Date;

// 'Double' class is used instead of primitive type 'double' in order to allow null values.
public class DayForecast extends HourForecast {

    private Double minTemperature_celsius;
    private Double maxTemperature_celsius;
    private Date sunrise;
    private Date sunset;

    public DayForecast(Date date, WeatherCondition weatherCondition, Double avgTemperature_celsius, Double minTemperature_celsius, Double maxTemperature_celsius, Double realFeel_celsius, Double precipitationProbability, Double humidityProbability, Double cloudinessProbability, Double windSpeed_metersPerSecond, Double pressure_pascals, UvIndex uvIndex, Date sunrise, Date sunset) {
        super(date, weatherCondition, avgTemperature_celsius, realFeel_celsius, precipitationProbability, humidityProbability, cloudinessProbability, windSpeed_metersPerSecond, pressure_pascals, uvIndex);
        this.minTemperature_celsius = minTemperature_celsius;
        this.maxTemperature_celsius = maxTemperature_celsius;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public Double getMinTemperature_celsius() {
        return minTemperature_celsius;
    }

    public Double getMaxTemperature_celsius() {
        return maxTemperature_celsius;
    }

    public Date getSunrise() {
        return sunrise;
    }

    public Date getSunset() {
        return sunset;
    }

}

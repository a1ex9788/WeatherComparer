package a1ex9788.dadm.weathercomparer.model;

import java.util.Date;

// 'Integer' class is used instead of primitive type 'int' in order to allow null values.
public class DailyForecast extends HourlyForecast {

    private Integer minTemperature_celsius;
    private Integer maxTemperature_celsius;
    private Date sunrise;
    private Date sunset;

    public DailyForecast(WeatherCondition weatherCondition, Integer avgTemperature_celsius, Integer realFeel_celsius, Integer precipitationProbability, Integer humidityProbability, Integer cloudinessProbability, Integer windSpeed_metersPerSecond, Integer pressure_pascals, UvIndex uvIndex, Integer minTemperature_celsius, Integer maxTemperature_celsius, Date sunrise, Date sunset) {
        super(weatherCondition, avgTemperature_celsius, realFeel_celsius, precipitationProbability, humidityProbability, cloudinessProbability, windSpeed_metersPerSecond, pressure_pascals, uvIndex);
        this.minTemperature_celsius = minTemperature_celsius;
        this.maxTemperature_celsius = maxTemperature_celsius;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public Integer getMinTemperature_celsius() {
        return minTemperature_celsius;
    }

    public Integer getMaxTemperature_celsius() {
        return maxTemperature_celsius;
    }

    public Date getSunrise() { return sunrise; }

    public Date getSunset() {  return sunset; }

}

package a1ex9788.dadm.weathercomparer.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Date;

import a1ex9788.dadm.weathercomparer.webServices.forecasts.UnitsConverter;

// 'Double' class is used instead of primitive type 'double' in order to allow null values.
public class HourForecast  {

    protected Date date;
    protected WeatherCondition weatherCondition;
    protected Double avgTemperature_celsius;
    protected Double realFeel_celsius;
    protected Double precipitationProbability;
    protected Double humidityProbability;
    protected Double cloudinessProbability;
    protected Double windSpeed_kilometersPerHour;
    protected Double pressure_millibars;
    protected Double uvIndex;

    public HourForecast(Date date, WeatherCondition weatherCondition, Double avgTemperature_celsius, Double realFeel_celsius, Double precipitationProbability, Double humidityProbability, Double cloudinessProbability, Double windSpeed_kilometersPerHour, Double pressure_millibars, Double uvIndex) {
        this.date = date;
        this.weatherCondition = weatherCondition;
        this.avgTemperature_celsius = avgTemperature_celsius;
        this.realFeel_celsius = realFeel_celsius;
        this.precipitationProbability = precipitationProbability;
        this.humidityProbability = humidityProbability;
        this.cloudinessProbability = cloudinessProbability;
        this.windSpeed_kilometersPerHour = windSpeed_kilometersPerHour;
        this.pressure_millibars = pressure_millibars;
        this.uvIndex = uvIndex;
    }

    public Date getDate() {
        return date;
    }

    public WeatherCondition getWeatherCondition() {
        return weatherCondition;
    }

    public Double getAvgTemperature_celsius() {
        return avgTemperature_celsius;
    }

    private Double getAvgTemperature_fahrenheit() {
        return UnitsConverter.celsiusToFahrenheit(avgTemperature_celsius);
    }

    private Double getAvgTemperature_kelvin() {
        return UnitsConverter.celsiusToKelvin(avgTemperature_celsius);
    }
    public Double getAvgTemperature(String valueUnit) {
        switch (valueUnit) {
            case "1":
                return getAvgTemperature_fahrenheit();
            case "2":
                return getAvgTemperature_kelvin();
            case "0":
            default:
                return getAvgTemperature_celsius();
        }
    }
    public Double getRealFeel_celsius() {
        return realFeel_celsius;
    }

    public Double getRealFee_fahrenheit() {
        return UnitsConverter.celsiusToFahrenheit(realFeel_celsius);
    }

    public Double getRealFee_kelvin() {
        return UnitsConverter.celsiusToKelvin(realFeel_celsius);
    }

    public Double getPrecipitationProbability() {
        return precipitationProbability;
    }

    public Double getHumidityProbability() {
        return humidityProbability;
    }

    public Double getCloudinessProbability() {
        return cloudinessProbability;
    }

    public Double getWindSpeed_kilometersPerHour() {
        return windSpeed_kilometersPerHour;
    }

    public Double getWindSpeed_milesPerHour() {
        return UnitsConverter.kilometersPerHourToMilesPerHour(windSpeed_kilometersPerHour);
    }

    public Double getWindSpeed_metersPerSecond() {
        return UnitsConverter.kilometersPerHourToMetersPerSecond(windSpeed_kilometersPerHour);
    }
    public Double getWindSpeed(String valueUnit) {
        switch (valueUnit) {
            case "1":
                return getWindSpeed_milesPerHour();
            case "2":
                return getWindSpeed_metersPerSecond();
            case "0":
            default:
                return getWindSpeed_kilometersPerHour();
        }
    }

    public Double getPressure_millibars() {
        return pressure_millibars;
    }

    public Double getUvIndex() {
        return uvIndex;
    }

}
package a1ex9788.dadm.weathercomparer.model;

import java.util.Date;

// 'Double' class is used instead of primitive type 'double' in order to allow null values.
public class HourForecast {

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

    public Double getRealFeel_celsius() {
        return realFeel_celsius;
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

    public Double getPressure_millibars() {
        return pressure_millibars;
    }

    public Double getUvIndex() {
        return uvIndex;
    }

}
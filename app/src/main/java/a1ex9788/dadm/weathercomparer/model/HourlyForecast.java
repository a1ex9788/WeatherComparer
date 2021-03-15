package a1ex9788.dadm.weathercomparer.model;

// 'Integer' class is used instead of primitive type 'int' in order to allow null values.
public class HourlyForecast {

    protected WeatherCondition weatherCondition;
    protected Integer avgTemperature_celsius;
    protected Integer realFeel_celsius;
    protected Integer precipitationProbability;
    protected Integer humidityProbability;
    protected Integer cloudinessProbability;
    protected Integer windSpeed_kilometersPerHour;
    protected Integer pressure_pascals;
    protected UvIndex uvIndex;

    public HourlyForecast(WeatherCondition weatherCondition, Integer avgTemperature_celsius, Integer realFeel_celsius, Integer precipitationProbability, Integer humidityProbability, Integer cloudinessProbability, Integer windSpeed_kilometersPerHour, Integer pressure_pascals, UvIndex uvIndex) {
        this.weatherCondition = weatherCondition;
        this.avgTemperature_celsius = avgTemperature_celsius;
        this.realFeel_celsius = realFeel_celsius;
        this.precipitationProbability = precipitationProbability;
        this.humidityProbability = humidityProbability;
        this.cloudinessProbability = cloudinessProbability;
        this.windSpeed_kilometersPerHour = windSpeed_kilometersPerHour;
        this.pressure_pascals = pressure_pascals;
        this.uvIndex = uvIndex;
    }

    public WeatherCondition getWeatherCondition() {
        return weatherCondition;
    }

    public Integer getAvgTemperature_celsius() {
        return avgTemperature_celsius;
    }

    public Integer getRealFeel_celsius() {
        return realFeel_celsius;
    }

    public Integer getPrecipitationProbability() {
        return precipitationProbability;
    }

    public Integer getHumidityProbability() {
        return humidityProbability;
    }

    public Integer getCloudinessProbability() {
        return cloudinessProbability;
    }

    public Integer getWindSpeed_kilometersPerHour() {
        return windSpeed_kilometersPerHour;
    }

    public Integer getPressure_pascals() {
        return pressure_pascals;
    }

    public UvIndex getUvIndex() {
        return uvIndex;
    }

}

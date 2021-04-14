package a1ex9788.dadm.weathercomparer.bindings;

public class CurrentWeather {

    private String weatherConditionText;
    private String windSpeed;
    private String windSpeedUnits;
    private String averageTemperature;
    private String averageTemperatureUnits;
    private String rainProbability;
    private String rainProbabilityUnits;

    public CurrentWeather(String weatherConditionText, String windSpeed, String windSpeedUnits, String averageTemperature, String averageTemperatureUnits, String rainProbability, String rainProbabilityUnits) {
        this.weatherConditionText = weatherConditionText;
        this.windSpeed = windSpeed;
        this.windSpeedUnits = windSpeedUnits;
        this.averageTemperature = averageTemperature;
        this.averageTemperatureUnits = averageTemperatureUnits;
        this.rainProbability = rainProbability;
        this.rainProbabilityUnits = rainProbabilityUnits;
    }

    public String getWeatherConditionText() {
        return weatherConditionText;
    }

    public void setWeatherConditionText(String weatherConditionText) {
        this.weatherConditionText = weatherConditionText;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindSpeedUnits() {
        return windSpeedUnits;
    }

    public void setWindSpeedUnits(String windSpeedUnits) {
        this.windSpeedUnits = windSpeedUnits;
    }

    public String getAverageTemperature() {
        return averageTemperature;
    }

    public void setAverageTemperature(String averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    public String getAverageTemperatureUnits() {
        return averageTemperatureUnits;
    }

    public void setAverageTemperatureUnits(String averageTemperatureUnits) {
        this.averageTemperatureUnits = averageTemperatureUnits;
    }

    public String getRainProbability() {
        return rainProbability;
    }

    public void setRainProbability(String rainProbability) {
        this.rainProbability = rainProbability;
    }

    public String getRainProbabilityUnits() {
        return rainProbabilityUnits;
    }

    public void setRainProbabilityUnits(String rainProbabilityUnits) {
        this.rainProbabilityUnits = rainProbabilityUnits;
    }

}

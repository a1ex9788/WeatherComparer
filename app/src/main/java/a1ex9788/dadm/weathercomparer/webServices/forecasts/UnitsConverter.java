package a1ex9788.dadm.weathercomparer.webServices.forecasts;

import java.util.Date;

import a1ex9788.dadm.weathercomparer.model.UvIndex;
import a1ex9788.dadm.weathercomparer.model.WeatherCondition;

public class UnitsConverter {

    public static Date unixUtcToDate(long unixUtcTimestamp) {
        return new Date((long) unixUtcTimestamp * 1000);
    }

    public static WeatherCondition accuWeatherConditionToStandard(int icon) {
        // TODO: Implement this.
        return WeatherCondition.Clear;
    }

    public static WeatherCondition openWeatherConditionToStandard(int id) {
        // TODO: Implement this.
        return WeatherCondition.Clear;
    }

    public static WeatherCondition weatherBitConditionToStandard(int code) {
        // TODO: Implement this.
        return WeatherCondition.Clear;
    }

    public static double metersPerSecondToKilometersPerHour(double metersPerSecondValue) {
        return (metersPerSecondValue / 1000.0) * 3600.0;
    }

    public static UvIndex uvIndexValueToUvIndex(double uvIndexValue) {
        if (uvIndexValue <= 2) {
            return UvIndex.Low;
        } else if (uvIndexValue <= 5) {
            return UvIndex.Moderate;
        } else if (uvIndexValue <= 7) {
            return UvIndex.High;
        } else {
            return UvIndex.VeryHigh;
        }
    }

}

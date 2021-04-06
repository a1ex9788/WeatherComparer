package a1ex9788.dadm.weathercomparer.webServices.forecasts;

import java.util.Date;

import a1ex9788.dadm.weathercomparer.model.UvIndex;
import a1ex9788.dadm.weathercomparer.model.WeatherCondition;

public class UnitsConverter {

    public static Date unixUtcToDate(long unixUtcTimestamp) {
        return new Date((long) unixUtcTimestamp * 1000);
    }

    public static long dateToUnixUTC(Date date) {
        return date.getTime() / 1000;
    }

    public static WeatherCondition accuWeatherConditionToStandard(int icon) {
        switch (icon) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 30:
            case 33:
            case 34:
                return WeatherCondition.Clear;
            case 6:
            case 7:
            case 8:
            case 35:
            case 36:
            case 37:
            case 38:
            case 43:
                return WeatherCondition.Clouds;
            case 11:
                return WeatherCondition.Fog;
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 39:
            case 40:
                return WeatherCondition.Rain;
            case 19:
            case 20:
            case 21:
            case 32:
                return WeatherCondition.Flurries;
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 29:
            case 44:
                return WeatherCondition.Snow;
            case 31:
                return WeatherCondition.UnknownPrecipitation;
            case 41:
            case 42:
                return WeatherCondition.ThunderstormWithRain;
            default:
                return WeatherCondition.Unknown;
        }
    }

    public static WeatherCondition openWeatherConditionToStandard(int id) {
        switch (id) {
            case 200:
            case 201:
            case 202:
            case 300:
            case 301:
            case 302:
            case 310:
            case 311:
            case 312:
            case 313:
            case 314:
            case 321:
                return WeatherCondition.ThunderstormWithRain;
            case 210:
            case 211:
            case 212:
            case 221:
            case 230:
            case 231:
            case 232:
                return WeatherCondition.Thunderstorm;
            case 500:
            case 501:
            case 502:
            case 503:
            case 504:
            case 511:
            case 520:
            case 521:
            case 522:
            case 531:
                return WeatherCondition.Rain;
            case 600:
            case 601:
            case 602:
            case 611:
            case 612:
            case 613:
            case 615:
            case 616:
            case 620:
            case 621:
            case 622:
                return WeatherCondition.Snow;
            case 701:
            case 711:
            case 721:
            case 731:
            case 741:
            case 751:
            case 761:
            case 762:
                return WeatherCondition.Fog;
            case 771:
            case 781:
                return WeatherCondition.Flurries;
            case 800:
            case 801:
            case 802:
            case 804:
                return WeatherCondition.Clouds;
            case 803:
                return WeatherCondition.BrokenClouds;
            default:
                return WeatherCondition.Unknown;
        }
    }

    public static WeatherCondition weatherBitConditionToStandard(int code) {
        switch (code) {
            case 200:
            case 201:
            case 202:
            case 231:
            case 232:
            case 233:
            case 300:
            case 301:
            case 302:
                return WeatherCondition.ThunderstormWithRain;
            case 230:
                return WeatherCondition.Thunderstorm;
            case 500:
            case 501:
            case 502:
            case 511:
            case 520:
            case 521:
            case 522:
                return WeatherCondition.Rain;
            case 600:
            case 601:
            case 602:
            case 610:
            case 611:
            case 612:
            case 621:
            case 622:
                return WeatherCondition.Snow;
            case 623:
                return WeatherCondition.Flurries;
            case 700:
            case 711:
            case 721:
            case 731:
            case 741:
            case 751:
                return WeatherCondition.Fog;
            case 800:
                return WeatherCondition.Clear;
            case 801:
            case 802:
            case 804:
                return WeatherCondition.Clouds;
            case 803:
                return WeatherCondition.BrokenClouds;
            case 900:
                return WeatherCondition.UnknownPrecipitation;
            default:
                return WeatherCondition.Unknown;
        }
    }

    public static WeatherCondition getWeatherConditionFromValue(int value) {
        for (WeatherCondition weatherCondition : WeatherCondition.values()) {
            if (weatherCondition.getValue() == value) {
                return weatherCondition;
            }
        }

        return WeatherCondition.Unknown;
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

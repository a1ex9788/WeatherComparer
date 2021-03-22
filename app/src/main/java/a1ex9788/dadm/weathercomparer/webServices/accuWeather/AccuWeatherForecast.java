package a1ex9788.dadm.weathercomparer.webServices.accuWeather;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import a1ex9788.dadm.weathercomparer.model.DailyForecast;
import a1ex9788.dadm.weathercomparer.model.DayForecast;
import a1ex9788.dadm.weathercomparer.model.HourForecast;
import a1ex9788.dadm.weathercomparer.model.HourlyForecast;
import a1ex9788.dadm.weathercomparer.model.UnitsConverter;
import a1ex9788.dadm.weathercomparer.webServices.ApiKeys;
import a1ex9788.dadm.weathercomparer.webServices.WeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.WebServicesHelper;

public class AccuWeatherForecast extends WeatherForecast {

    private String locationKey;

    public AccuWeatherForecast(double latitude, double longitude) {
        super(latitude, longitude);
    }

    @Override
    public DailyForecast getDailyForecast() throws Exception {
        if (locationKey == null) {
            getLocationKey();
        }

        Uri.Builder uriBuilder = prepareForecastUriBuilder("daily", "5day");

        AccuWeatherDailyForecast accuWeatherDailyForecast = WebServicesHelper.getWebServiceAnswer(uriBuilder, AccuWeatherDailyForecast.class);

        return convertToStandard(accuWeatherDailyForecast);
    }

    @Override
    public HourlyForecast getHourlyForecast() throws Exception {
        if (locationKey == null) {
            getLocationKey();
        }

        Uri.Builder uriBuilder = prepareForecastUriBuilder("hourly", "12hour");

        AccuWeatherHourlyForecast.AccuWeatherHourForecast[] accuWeatherHourForecasts = WebServicesHelper.getWebServiceAnswer(uriBuilder, (new AccuWeatherHourlyForecast.AccuWeatherHourForecast[1]).getClass());

        return convertToStandard(accuWeatherHourForecasts);
    }

    private void getLocationKey() throws Exception {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https");
        uriBuilder.authority("dataservice.accuweather.com");
        uriBuilder.appendPath("locations");
        uriBuilder.appendPath("v1");
        uriBuilder.appendPath("cities");
        uriBuilder.appendPath("geoposition");
        uriBuilder.appendPath("search");
        uriBuilder.appendQueryParameter("q", latitude + "," + longitude);
        uriBuilder.appendQueryParameter("apikey", ApiKeys.ACCU_WEATHER);

        AccuWeatherLocation accuWeatherLocation = WebServicesHelper.getWebServiceAnswer(uriBuilder, AccuWeatherLocation.class);

        locationKey = accuWeatherLocation.Key;
    }

    private Uri.Builder prepareForecastUriBuilder(String forecastType, String timeQuantity) {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https");
        uriBuilder.authority("dataservice.accuweather.com");
        uriBuilder.appendPath("forecasts");
        uriBuilder.appendPath("v1");
        uriBuilder.appendPath(forecastType);
        uriBuilder.appendPath(timeQuantity);
        uriBuilder.appendPath(locationKey);
        uriBuilder.appendQueryParameter("details", "true");
        uriBuilder.appendQueryParameter("metric", "true");
        uriBuilder.appendQueryParameter("apikey", ApiKeys.ACCU_WEATHER);

        return uriBuilder;
    }

    private DailyForecast convertToStandard(AccuWeatherDailyForecast accuWeatherDailyForecast) {
        List<DayForecast> dayForecasts = new ArrayList();

        for (AccuWeatherDailyForecast.AccuWeatherDayForecast accuWeatherDayForecast : accuWeatherDailyForecast.DailyForecasts) {
            dayForecasts.add(new DayForecast(
                    accuWeatherDayForecast.Date,
                    accuWeatherDayForecast.Day == null || accuWeatherDayForecast.Day.Icon == null
                            ? null : UnitsConverter.accuWeatherConditionToStandard(accuWeatherDayForecast.Day.Icon),
                    accuWeatherDayForecast.Temperature == null
                            || accuWeatherDayForecast.Temperature.Maximum == null || accuWeatherDayForecast.Temperature.Maximum.Value == null
                            || accuWeatherDayForecast.Temperature.Minimum == null || accuWeatherDayForecast.Temperature.Minimum.Value == null
                            ? null : (accuWeatherDayForecast.Temperature.Maximum.Value + accuWeatherDayForecast.Temperature.Minimum.Value) / 2.0,
                    accuWeatherDayForecast.Temperature == null || accuWeatherDayForecast.Temperature.Maximum == null
                            ? null : accuWeatherDayForecast.Temperature.Maximum.Value,
                    accuWeatherDayForecast.Temperature == null || accuWeatherDayForecast.Temperature.Minimum == null
                            ? null : accuWeatherDayForecast.Temperature.Minimum.Value,
                    accuWeatherDayForecast.RealFeelTemperature == null
                            || accuWeatherDayForecast.RealFeelTemperature.Maximum == null || accuWeatherDayForecast.RealFeelTemperature.Maximum.Value == null
                            || accuWeatherDayForecast.RealFeelTemperature.Minimum == null || accuWeatherDayForecast.RealFeelTemperature.Minimum.Value == null
                            ? null : (accuWeatherDayForecast.RealFeelTemperature.Maximum.Value + accuWeatherDayForecast.RealFeelTemperature.Minimum.Value) / 2.0,
                    accuWeatherDayForecast.Day == null
                            || accuWeatherDayForecast.Day.PrecipitationProbability == null || accuWeatherDayForecast.Night.PrecipitationProbability == null
                            ? null : (accuWeatherDayForecast.Day.PrecipitationProbability + accuWeatherDayForecast.Night.PrecipitationProbability) / 2.0,
                    null,
                    accuWeatherDayForecast.Day == null
                            || accuWeatherDayForecast.Day.CloudCover == null || accuWeatherDayForecast.Night.CloudCover == null
                            ? null : (accuWeatherDayForecast.Day.CloudCover + accuWeatherDayForecast.Night.CloudCover) / 2.0,
                    accuWeatherDayForecast.Day == null || accuWeatherDayForecast.Day.Wind == null || accuWeatherDayForecast.Day.Wind.Speed == null
                            || accuWeatherDayForecast.Night == null || accuWeatherDayForecast.Night.Wind == null || accuWeatherDayForecast.Night.Wind.Speed == null
                            || accuWeatherDayForecast.Day.Wind.Speed.Value == null || accuWeatherDayForecast.Night.Wind.Speed.Value == null
                            ? null : (accuWeatherDayForecast.Day.Wind.Speed.Value + accuWeatherDayForecast.Night.Wind.Speed.Value) / 2.0,
                    null,
                    null,
                    accuWeatherDayForecast.Sun == null ? null : accuWeatherDayForecast.Sun.Rise,
                    accuWeatherDayForecast.Sun == null ? null : accuWeatherDayForecast.Sun.Set
            ));
        }

        return new DailyForecast(dayForecasts);
    }

    private HourlyForecast convertToStandard(AccuWeatherHourlyForecast.AccuWeatherHourForecast[] accuWeatherHourForecasts) {
        List<HourForecast> hourForecasts = new ArrayList();

        for (AccuWeatherHourlyForecast.AccuWeatherHourForecast accuWeatherHourForecast : accuWeatherHourForecasts) {
            hourForecasts.add(new HourForecast(
                    accuWeatherHourForecast.DateTime,
                    accuWeatherHourForecast.WeatherIcon == null ? null : UnitsConverter.accuWeatherConditionToStandard(accuWeatherHourForecast.WeatherIcon),
                    accuWeatherHourForecast.Temperature == null ? null : accuWeatherHourForecast.Temperature.Value,
                    accuWeatherHourForecast.RealFeelTemperature == null ? null : accuWeatherHourForecast.RealFeelTemperature.Value,
                    accuWeatherHourForecast.PrecipitationProbability,
                    accuWeatherHourForecast.RelativeHumidity,
                    accuWeatherHourForecast.CloudCover,
                    accuWeatherHourForecast.Wind == null || accuWeatherHourForecast.Wind.Speed == null ? null : accuWeatherHourForecast.Wind.Speed.Value,
                    null,
                    accuWeatherHourForecast.UVIndex == null ? null : UnitsConverter.uvIndexValueToUvIndex(accuWeatherHourForecast.UVIndex)
            ));
        }

        return new HourlyForecast(hourForecasts);
    }

}

package a1ex9788.dadm.weathercomparer.webServices.forecasts.openWeather;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import a1ex9788.dadm.weathercomparer.model.DayForecast;
import a1ex9788.dadm.weathercomparer.model.HourForecast;
import a1ex9788.dadm.weathercomparer.webServices.ApiKeys;
import a1ex9788.dadm.weathercomparer.webServices.WebServicesHelper;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.UnitsConverter;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.WeatherForecast;

public class OpenWeatherForecast extends WeatherForecast {

    private OpenWeatherCompleteForecast openWeatherCompleteForecast;

    public OpenWeatherForecast(double latitude, double longitude) {
        super(latitude, longitude);
    }

    @Override
    public List<DayForecast> getDailyForecast() throws Exception {
        if (openWeatherCompleteForecast == null) {
            GetOpenWeatherCompleteForecast();
        }

        return convertToDailyStandard(openWeatherCompleteForecast);
    }

    @Override
    public List<HourForecast> getHourlyForecast() throws Exception {
        if (openWeatherCompleteForecast == null) {
            GetOpenWeatherCompleteForecast();
        }

        return convertToHourlyStandard(openWeatherCompleteForecast);
    }

    private void GetOpenWeatherCompleteForecast() throws Exception {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https");
        uriBuilder.authority("api.openweathermap.org");
        uriBuilder.appendPath("data");
        uriBuilder.appendPath("2.5");
        uriBuilder.appendPath("onecall");
        uriBuilder.appendQueryParameter("lat", latitude + "");
        uriBuilder.appendQueryParameter("lon", longitude + "");
        uriBuilder.appendQueryParameter("units", "metric");
        uriBuilder.appendQueryParameter("appid", ApiKeys.getOpenWeatherApiKey());

        openWeatherCompleteForecast = WebServicesHelper.getWebServiceAnswer(uriBuilder, OpenWeatherCompleteForecast.class);
    }

    private List<DayForecast> convertToDailyStandard(OpenWeatherCompleteForecast openWeatherCompleteForecast) {
        List<DayForecast> dailyForecast = new ArrayList();

        for (OpenWeatherCompleteForecast.OpenWeatherDayForecast openWeatherDayForecast : openWeatherCompleteForecast.daily) {
            dailyForecast.add(new DayForecast(
                    openWeatherDayForecast.dt == null ? null : UnitsConverter.unixUtcToDate(openWeatherDayForecast.dt),
                    openWeatherDayForecast.weather == null || openWeatherDayForecast.weather.get(0) == null || openWeatherDayForecast.weather.get(0).id == null
                            ? null : UnitsConverter.openWeatherConditionToStandard(openWeatherDayForecast.weather.get(0).id),
                    openWeatherDayForecast.temp == null ? null : (openWeatherDayForecast.temp.max + openWeatherDayForecast.temp.min) / 2.0,
                    openWeatherDayForecast.temp == null ? null : openWeatherDayForecast.temp.max,
                    openWeatherDayForecast.temp == null ? null : openWeatherDayForecast.temp.min,
                    openWeatherDayForecast.feels_like == null ? null : openWeatherDayForecast.feels_like.day,
                    openWeatherDayForecast.pop,
                    openWeatherDayForecast.humidity,
                    openWeatherDayForecast.clouds,
                    openWeatherDayForecast.wind_speed == null ? null : UnitsConverter.metersPerSecondToKilometersPerHour(openWeatherDayForecast.wind_speed),
                    openWeatherDayForecast.pressure,
                    openWeatherDayForecast.uvi,
                    openWeatherDayForecast.sunrise == null ? null : UnitsConverter.unixUtcToDate(openWeatherDayForecast.sunrise),
                    openWeatherDayForecast.sunset == null ? null : UnitsConverter.unixUtcToDate(openWeatherDayForecast.sunset)
            ));
        }

        return dailyForecast;
    }

    private List<HourForecast> convertToHourlyStandard(OpenWeatherCompleteForecast openWeatherCompleteForecast) {
        List<HourForecast> hourlyForecast = new ArrayList();

        for (OpenWeatherCompleteForecast.OpenWeatherHourForecast openWeatherHourForecast : openWeatherCompleteForecast.hourly) {
            hourlyForecast.add(new HourForecast(
                    openWeatherHourForecast.dt == null ? null : UnitsConverter.unixUtcToDate(openWeatherHourForecast.dt),
                    openWeatherHourForecast.weather == null || openWeatherHourForecast.weather.get(0) == null || openWeatherHourForecast.weather.get(0).id == null
                            ? null : UnitsConverter.openWeatherConditionToStandard(openWeatherHourForecast.weather.get(0).id),
                    openWeatherHourForecast.temp,
                    openWeatherHourForecast.feels_like,
                    openWeatherHourForecast.pop,
                    openWeatherHourForecast.humidity,
                    openWeatherHourForecast.clouds,
                    openWeatherHourForecast.wind_speed == null ? null : UnitsConverter.metersPerSecondToKilometersPerHour(openWeatherHourForecast.wind_speed),
                    openWeatherHourForecast.pressure,
                    openWeatherHourForecast.uvi
            ));
        }

        return hourlyForecast;
    }

}

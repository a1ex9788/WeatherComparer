package a1ex9788.dadm.weathercomparer.webServices.forecasts.weatherBit;

import android.net.Uri;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import a1ex9788.dadm.weathercomparer.model.DayForecast;
import a1ex9788.dadm.weathercomparer.model.HourForecast;
import a1ex9788.dadm.weathercomparer.utils.UnitsConverter;
import a1ex9788.dadm.weathercomparer.webServices.ApiKeys;
import a1ex9788.dadm.weathercomparer.webServices.WebServicesHelper;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.WeatherForecast;

public class WeatherBitForecast extends WeatherForecast {

	public WeatherBitForecast(double latitude, double longitude) {
		super(latitude, longitude);
	}

	@Override
	public List<DayForecast> getDailyForecast() throws Exception {
		WeatherBitDailyForecast weatherBitDailyForecast = getWeatherBitDailyForecast();

		return convertToStandard(weatherBitDailyForecast);
	}

	@Override
	public List<HourForecast> getHourlyForecast() throws Exception {
		WeatherBitHourlyForecast weatherBitHourlyForecast = getWeatherBitHourlyForecast();

		return convertToStandard(weatherBitHourlyForecast);
	}

	private Uri.Builder prepareUriBuilder(String forecastType) {
		Uri.Builder uriBuilder = new Uri.Builder();
		uriBuilder.scheme("https");
		uriBuilder.authority("api.weatherbit.io");
		uriBuilder.appendPath("v2.0");
		uriBuilder.appendPath("forecast");
		uriBuilder.appendPath(forecastType);
		uriBuilder.appendQueryParameter("lat", latitude + "");
		uriBuilder.appendQueryParameter("lon", longitude + "");
		uriBuilder.appendQueryParameter("key", ApiKeys.getWeatherBitApiKey());

		return uriBuilder;
	}

	protected WeatherBitDailyForecast getWeatherBitDailyForecast() throws Exception {
		Uri.Builder uriBuilder = prepareUriBuilder("daily");

		return WebServicesHelper.getWebServiceAnswer(uriBuilder, WeatherBitDailyForecast.class);
	}

	protected WeatherBitHourlyForecast getWeatherBitHourlyForecast() throws Exception {
		throw new Exception("The pricing categories changed and we do not have more access to this feature.");

        /*Uri.Builder uriBuilder = prepareUriBuilder("hourly");

        return WebServicesHelper.getWebServiceAnswer(uriBuilder, WeatherBitHourlyForecast.class);*/
	}

	private List<DayForecast> convertToStandard(WeatherBitDailyForecast weatherBitDailyForecast) {
		List<DayForecast> dailyForecast = new ArrayList();

		// The first element is today's forecast so it has to be skipped.
		for (WeatherBitDailyForecast.WeatherBitDayForecast weatherBitDayForecast : weatherBitDailyForecast.data.subList(
				1,
				weatherBitDailyForecast.data.size())) {
			dailyForecast.add(new DayForecast(weatherBitDayForecast.datetime,
					weatherBitDayForecast.weather == null || weatherBitDayForecast.weather.code == null
							? null
							: UnitsConverter.weatherBitConditionToStandard(weatherBitDayForecast.weather.code),
					weatherBitDayForecast.temp,
					weatherBitDayForecast.max_temp,
					weatherBitDayForecast.min_temp,
					weatherBitDayForecast.app_max_temp == null || weatherBitDayForecast.app_min_temp == null
							? null
							: (weatherBitDayForecast.app_max_temp + weatherBitDayForecast.app_min_temp) / 2.0,
					weatherBitDayForecast.pop,
					weatherBitDayForecast.rh,
					weatherBitDayForecast.clouds,
					weatherBitDayForecast.wind_spd == null
							? null
							: UnitsConverter.metersPerSecondToKilometersPerHour(weatherBitDayForecast.wind_spd),
					weatherBitDayForecast.pres,
					weatherBitDayForecast.uv,
					weatherBitDayForecast.sunrise_ts == null
							? null
							: UnitsConverter.unixUtcToDate(weatherBitDayForecast.sunrise_ts),
					weatherBitDayForecast.sunset_ts == null
							? null
							: UnitsConverter.unixUtcToDate(weatherBitDayForecast.sunset_ts)));
		}

		return dailyForecast;
	}

	private List<HourForecast> convertToStandard(WeatherBitHourlyForecast weatherBitHourlyForecast) throws ParseException {
		List<HourForecast> hourlyForecast = new ArrayList();

		for (WeatherBitHourlyForecast.WeatherBitHourForecast weatherBitHourForecast : weatherBitHourlyForecast.data) {
			hourlyForecast.add(new HourForecast(weatherBitHourForecast.datetime == null
					? null
					: new SimpleDateFormat("yyyy-MM-dd:HH").parse(weatherBitHourForecast.datetime),
					weatherBitHourForecast.weather == null || weatherBitHourForecast.weather.code == null
							? null
							: UnitsConverter.weatherBitConditionToStandard(weatherBitHourForecast.weather.code),
					weatherBitHourForecast.temp,
					weatherBitHourForecast.app_temp,
					weatherBitHourForecast.pop,
					weatherBitHourForecast.rh,
					weatherBitHourForecast.clouds,
					weatherBitHourForecast.wind_spd == null
							? null
							: UnitsConverter.metersPerSecondToKilometersPerHour(weatherBitHourForecast.wind_spd),
					weatherBitHourForecast.pres,
					weatherBitHourForecast.uv));
		}

		return hourlyForecast;
	}

}

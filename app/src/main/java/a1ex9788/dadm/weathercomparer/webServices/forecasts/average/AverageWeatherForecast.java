package a1ex9788.dadm.weathercomparer.webServices.forecasts.average;

import java.util.Arrays;
import java.util.List;

import a1ex9788.dadm.weathercomparer.model.DayForecast;
import a1ex9788.dadm.weathercomparer.model.HourForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.WeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.accuWeather.AccuWeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.openWeather.OpenWeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.weatherBit.WeatherBitForecast;

public class AverageWeatherForecast extends WeatherForecast {

	private final int DAYS_IN_DAILY_FORECAST = 4;
	private final int HOURS_IN_HOURLY_FORECAST = 12;
	private final AverageWeatherForecastCalculator averageWeatherForecastCalculator;
	protected List<WeatherForecast> weatherForecasts = Arrays.asList(new AccuWeatherForecast(latitude, longitude),
			new OpenWeatherForecast(latitude, longitude),
			new WeatherBitForecast(latitude, longitude));

	public AverageWeatherForecast(double latitude, double longitude) {
		super(latitude, longitude);

		averageWeatherForecastCalculator = new AverageWeatherForecastCalculator(getWeatherForecasts(),
				DAYS_IN_DAILY_FORECAST,
				HOURS_IN_HOURLY_FORECAST);
	}

	@Override
	public List<DayForecast> getDailyForecast() {
		return averageWeatherForecastCalculator.getAverageDailyForecast();
	}

	@Override
	public List<HourForecast> getHourlyForecast() {
		return averageWeatherForecastCalculator.getAverageHourlyForecast();
	}

	protected List<WeatherForecast> getWeatherForecasts() {
		return weatherForecasts;
	}

}

package a1ex9788.dadm.weathercomparer.webServices.forecasts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import a1ex9788.dadm.weathercomparer.model.DayForecast;
import a1ex9788.dadm.weathercomparer.model.HourForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.accuWeather.AccuWeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.openWeather.OpenWeatherForecast;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.weatherBit.WeatherBitForecast;

public class AverageForecastCalculator {

    private final int DAYS_IN_DAILY_FORECAST = 5;
    private final int HOURS_IN_HOURLY_FORECAST = 12;

    private double lat, lon;

    private WeatherForecast accuWeatherForecast;
    private WeatherForecast openWeatherForecast;
    private WeatherForecast weatherBitForecast;

    private List<WeatherForecast> weatherForecasts;

    public AverageForecastCalculator(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;

        accuWeatherForecast = new AccuWeatherForecast(lat, lon);
        openWeatherForecast = new OpenWeatherForecast(lat, lon);
        weatherBitForecast = new WeatherBitForecast(lat, lon);

        weatherForecasts = Arrays.asList(
                accuWeatherForecast,
                openWeatherForecast,
                weatherBitForecast
        );
    }

    public List<DayForecast> getAverageDailyForecast() throws Exception {
        List<List<DayForecast>> dailyForecasts = new ArrayList();
        for (WeatherForecast weatherForecast : weatherForecasts) {
            try {
                dailyForecasts.add(weatherForecast.getDailyForecast());
            } catch (Exception e) {
                // An exception can be occurred so the implicated weather forecast will not be used.
            }
        }

        List<DayForecast> averageDailyForecast = new ArrayList<>();
        for (int i = 0; i < DAYS_IN_DAILY_FORECAST; i++) {
            List<Long> weatherConditions = new ArrayList();
            List<Double> averageTemperatures = new ArrayList();
            List<Double> minTemperatures = new ArrayList();
            List<Double> maxTemperatures = new ArrayList();
            List<Double> realFeelTemperatures = new ArrayList();
            List<Double> precipitationProbabilities = new ArrayList();
            List<Double> humidityProbabilities = new ArrayList();
            List<Double> cloudinessProbabilities = new ArrayList();
            List<Double> windSpeeds = new ArrayList();
            List<Double> pressures = new ArrayList();
            List<Double> uvIndexes = new ArrayList();
            List<Long> sunrises = new ArrayList();
            List<Long> sunsets = new ArrayList();

            for (int j = 0; j < dailyForecasts.size(); j++) {
                DayForecast dayForecast = dailyForecasts.get(j).get(i);

                weatherConditions.add(Long.valueOf(dayForecast.getWeatherCondition().getValue()));
                averageTemperatures.add(dayForecast.getAvgTemperature_celsius());
                minTemperatures.add(dayForecast.getMinTemperature_celsius());
                maxTemperatures.add(dayForecast.getMaxTemperature_celsius());
                realFeelTemperatures.add(dayForecast.getRealFeel_celsius());
                precipitationProbabilities.add(dayForecast.getPrecipitationProbability());
                humidityProbabilities.add(dayForecast.getHumidityProbability());
                cloudinessProbabilities.add(dayForecast.getCloudinessProbability());
                windSpeeds.add(dayForecast.getWindSpeed_kilometersPerHour());
                pressures.add(dayForecast.getPressure_millibars());
                uvIndexes.add(dayForecast.getUvIndex());
                sunrises.add(UnitsConverter.dateToUnixUTC(dayForecast.getSunrise()));
                sunsets.add(UnitsConverter.dateToUnixUTC(dayForecast.getSunset()));
            }

            averageDailyForecast.add(new DayForecast(
                    dailyForecasts.get(0).get(i).getDate(),
                    UnitsConverter.getWeatherConditionFromValue(calculateLongAverage(weatherConditions).intValue()),
                    calculateAverageDoubles(averageTemperatures),
                    calculateAverageDoubles(minTemperatures),
                    calculateAverageDoubles(maxTemperatures),
                    calculateAverageDoubles(realFeelTemperatures),
                    calculateAverageDoubles(precipitationProbabilities),
                    calculateAverageDoubles(humidityProbabilities),
                    calculateAverageDoubles(cloudinessProbabilities),
                    calculateAverageDoubles(windSpeeds),
                    calculateAverageDoubles(pressures),
                    calculateAverageDoubles(uvIndexes),
                    UnitsConverter.unixUtcToDate(calculateLongAverage(sunrises)),
                    UnitsConverter.unixUtcToDate(calculateLongAverage(sunsets))
            ));
        }

        return averageDailyForecast;
    }

    public List<HourForecast> getAverageHourlyForecast() throws Exception {
        List<List<HourForecast>> hourlyForecasts = new ArrayList();
        for (WeatherForecast weatherForecast : weatherForecasts) {
            try {
                hourlyForecasts.add(weatherForecast.getHourlyForecast());
            } catch (Exception e) {
                // An exception can be occurred so the implicated weather forecast will not be used.
            }
        }

        List<HourForecast> averageHourlyForecast = new ArrayList<>();
        for (int i = 0; i < HOURS_IN_HOURLY_FORECAST; i++) {
            List<Long> weatherConditions = new ArrayList();
            List<Double> averageTemperatures = new ArrayList();
            List<Double> realFeelTemperatures = new ArrayList();
            List<Double> precipitationProbabilities = new ArrayList();
            List<Double> humidityProbabilities = new ArrayList();
            List<Double> cloudinessProbabilities = new ArrayList();
            List<Double> windSpeeds = new ArrayList();
            List<Double> pressures = new ArrayList();
            List<Double> uvIndexes = new ArrayList();

            for (int j = 0; j < hourlyForecasts.size(); j++) {
                HourForecast hourForecast = hourlyForecasts.get(j).get(i);

                weatherConditions.add(Long.valueOf(hourForecast.getWeatherCondition().getValue()));
                averageTemperatures.add(hourForecast.getAvgTemperature_celsius());
                realFeelTemperatures.add(hourForecast.getRealFeel_celsius());
                precipitationProbabilities.add(hourForecast.getPrecipitationProbability());
                humidityProbabilities.add(hourForecast.getHumidityProbability());
                cloudinessProbabilities.add(hourForecast.getCloudinessProbability());
                windSpeeds.add(hourForecast.getWindSpeed_kilometersPerHour());
                pressures.add(hourForecast.getPressure_millibars());
                uvIndexes.add(hourForecast.getUvIndex());
            }

            averageHourlyForecast.add(new HourForecast(
                    hourlyForecasts.get(0).get(i).getDate(),
                    UnitsConverter.getWeatherConditionFromValue(calculateLongAverage(weatherConditions).intValue()),
                    calculateAverageDoubles(averageTemperatures),
                    calculateAverageDoubles(realFeelTemperatures),
                    calculateAverageDoubles(precipitationProbabilities),
                    calculateAverageDoubles(humidityProbabilities),
                    calculateAverageDoubles(cloudinessProbabilities),
                    calculateAverageDoubles(windSpeeds),
                    calculateAverageDoubles(pressures),
                    calculateAverageDoubles(uvIndexes)
            ));
        }

        return averageHourlyForecast;
    }

    private Double calculateAverageDoubles(List<Double> doubles) {
        int numberOfNotNulls = 0;
        double value = 0;

        for (int i = 0; i < doubles.size(); i++) {
            Double d = doubles.get(i);

            if (d != null) {
                value += d;
                numberOfNotNulls++;
            }
        }

        if (numberOfNotNulls == 0) {
            return null;
        }

        return value / numberOfNotNulls;
    }

    private Long calculateLongAverage(List<Long> longs) {
        int numberOfNotNulls = 0;
        long value = 0;

        for (int i = 0; i < longs.size(); i++) {
            Long l = longs.get(i);

            if (l != null) {
                value += l;
                numberOfNotNulls++;
            }
        }

        if (numberOfNotNulls == 0) {
            return null;
        }

        return Math.round((double) (value / numberOfNotNulls));
    }

}

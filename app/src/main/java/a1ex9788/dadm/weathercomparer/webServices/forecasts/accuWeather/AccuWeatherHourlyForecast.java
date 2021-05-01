package a1ex9788.dadm.weathercomparer.webServices.forecasts.accuWeather;

import java.util.Date;

// Attributes should be private and be accessed via getters but they are not in order to simplify
// the class and its access.
// The name of the attributes begin with upper case because in the answer of the web service they
// do too.
public class AccuWeatherHourlyForecast {

	// 'Integer' and 'Double' classes are used instead of primitive types 'int' and 'double' in
	// order to allow null values.
	public class AccuWeatherHourForecast {

		public Date DateTime;
		public Integer WeatherIcon;
		public Temperature Temperature;
		public RealFeelTemperature RealFeelTemperature;
		public Double PrecipitationProbability;
		public Double RelativeHumidity;
		public Double CloudCover;
		public Wind Wind;
		public Double UVIndex;

		public class Temperature {

			public Double Value;

		}

		public class RealFeelTemperature extends Temperature {

		}

		public class Wind {

			public Speed Speed;

			public class Speed {

				public Double Value;

			}

		}

	}

}

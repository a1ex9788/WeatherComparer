package a1ex9788.dadm.weathercomparer.webServices.forecasts.openWeather;

import java.util.ArrayList;
import java.util.List;

// Attributes should be private and be accessed via getters but they are not in order to simplify the class and its access.
// 'Integer', 'Double' and 'Long' classes are used instead of primitive types 'int', 'double' and 'long' in order to allow null values.
public class OpenWeatherCompleteForecast {

    public List<OpenWeatherDayForecast> daily = new ArrayList();
    public List<OpenWeatherHourForecast> hourly = new ArrayList();

    public class OpenWeatherDayForecast {

        public Long dt;
        public List<Weather> weather;
        public Temperature temp;
        public FeelTemperature feels_like;
        public Double pop;
        public Double humidity;
        public Double clouds;
        public Double wind_speed;
        public Double pressure;
        public Double uvi;
        public Long sunrise;
        public Long sunset;

        public class Weather {

            public Integer id;

        }

        public class Temperature {

            public Double max;
            public Double min;

        }

        public class FeelTemperature {

            public Double day;

        }

    }

    public class OpenWeatherHourForecast {

        public Long dt;
        public List<Weather> weather;
        public Double temp;
        public Double feels_like;
        public Double pop;
        public Double humidity;
        public Double clouds;
        public Double wind_speed;
        public Double pressure;
        public Double uvi;

        public class Weather {

            public Integer id;

        }

    }

}

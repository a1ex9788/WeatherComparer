package a1ex9788.dadm.weathercomparer.webServices.forecasts.weatherBit;

import java.util.ArrayList;
import java.util.List;

// Attributes should be private and be accessed via getters but they are not in order to simplify the class and its access.
public class WeatherBitHourlyForecast {

    public List<WeatherBitHourForecast> data = new ArrayList();

    // 'Integer', 'Double' classes are used instead of primitive types 'int' and 'double' in order to allow null values.
    public class WeatherBitHourForecast {

        public String datetime;
        public Weather weather;
        public Double temp;
        public Double app_temp;
        public Double pop;
        public Double rh;
        public Double clouds;
        public Double wind_spd;
        public Double pres;
        public Double uv;

        public class Weather {

            public Integer code;

        }

    }

}



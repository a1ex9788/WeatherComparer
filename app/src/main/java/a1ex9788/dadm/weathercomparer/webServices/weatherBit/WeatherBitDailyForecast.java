package a1ex9788.dadm.weathercomparer.webServices.weatherBit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Attributes should be private and be accessed via getters but they are not in order to simplify the class and its access.
public class WeatherBitDailyForecast {

    public List<WeatherBitDayForecast> data = new ArrayList();

    // 'Integer', 'Double' and 'Long' classes are used instead of primitive types 'int', 'double' and 'long' in order to allow null values.
    public class WeatherBitDayForecast {

        public Date datetime;
        public Weather weather;
        public Double temp;
        public Double max_temp;
        public Double min_temp;
        public Double app_max_temp;
        public Double app_min_temp;
        public Double pop;
        public Double rh;
        public Double clouds;
        public Double wind_spd;
        public Double pres;
        public Double uv;
        public Long sunrise_ts;
        public Long sunset_ts;

        public class Weather {

            public Integer code;

        }

    }

}

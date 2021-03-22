package a1ex9788.dadm.weathercomparer.webServices.accuWeather;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Attributes should be private and be accessed via getters but they are not in order to simplify the class and its access.
// The name of the attributes begin with upper case because in the answer of the web service they do too.
public class AccuWeatherDailyForecast {

    public List<AccuWeatherDayForecast> DailyForecasts = new ArrayList();

    // 'Integer' and 'Double' classes are used instead of primitive types 'int' and 'double' in order to allow null values.
    public class AccuWeatherDayForecast {

        public Date Date;
        public Day Day;
        public Night Night;
        public Temperature Temperature;
        public RealFeelTemperature RealFeelTemperature;
        public Sun Sun;

        public class Day {

            public Integer Icon;
            public Double PrecipitationProbability;
            public Double CloudCover;
            public Wind Wind;

            public class Wind {

                public Speed Speed;

                public class Speed {

                    public Double Value;

                }

            }

        }

        public class Night extends Day {

        }

        public class Temperature {

            public Minimum Minimum;
            public Maximum Maximum;

            public class Minimum {

                public Double Value;

            }

            public class Maximum extends Minimum {

            }

        }

        public class RealFeelTemperature extends Temperature {

        }

        public class Sun {

            public Date Rise;
            public Date Set;

        }

    }

}

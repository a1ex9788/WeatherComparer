package a1ex9788.dadm.weathercomparer.webServices.forecasts.openWeather;

import a1ex9788.dadm.weathercomparer.webServices.WebServicesHelper;

public class OpenWeatherMockForecast extends OpenWeatherForecast {

    public OpenWeatherMockForecast(double latitude, double longitude) {
        super(latitude, longitude);
    }

    @Override
    protected OpenWeatherCompleteForecast GetOpenWeatherCompleteForecast() throws Exception {
        String openWeatherCompleteForecastWebServiceAnswer = "{\"lat\":39.289,\"lon\":-0.799,\"timezone\":\"Europe/Madrid\",\"timezone_offset\":7200,\"current\":{\"dt\":1619784716,\"sunrise\":1619759140,\"sunset\":1619808895,\"temp\":21.46,\"feels_like\":21.23,\"pressure\":1008,\"humidity\":60,\"dew_point\":13.38,\"uvi\":5.25,\"clouds\":20,\"visibility\":10000,\"wind_speed\":4.63,\"wind_deg\":120,\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}]},\"minutely\":[{\"dt\":1619784720,\"precipitation\":0},{\"dt\":1619784780,\"precipitation\":0},{\"dt\":1619784840,\"precipitation\":0},{\"dt\":1619784900,\"precipitation\":0},{\"dt\":1619784960,\"precipitation\":0},{\"dt\":1619785020,\"precipitation\":0},{\"dt\":1619785080,\"precipitation\":0},{\"dt\":1619785140,\"precipitation\":0},{\"dt\":1619785200,\"precipitation\":0},{\"dt\":1619785260,\"precipitation\":0},{\"dt\":1619785320,\"precipitation\":0},{\"dt\":1619785380,\"precipitation\":0},{\"dt\":1619785440,\"precipitation\":0},{\"dt\":1619785500,\"precipitation\":0},{\"dt\":1619785560,\"precipitation\":0},{\"dt\":1619785620,\"precipitation\":0},{\"dt\":1619785680,\"precipitation\":0},{\"dt\":1619785740,\"precipitation\":0},{\"dt\":1619785800,\"precipitation\":0},{\"dt\":1619785860,\"precipitation\":0},{\"dt\":1619785920,\"precipitation\":0},{\"dt\":1619785980,\"precipitation\":0},{\"dt\":1619786040,\"precipitation\":0},{\"dt\":1619786100,\"precipitation\":0},{\"dt\":1619786160,\"precipitation\":0},{\"dt\":1619786220,\"precipitation\":0},{\"dt\":1619786280,\"precipitation\":0},{\"dt\":1619786340,\"precipitation\":0},{\"dt\":1619786400,\"precipitation\":0},{\"dt\":1619786460,\"precipitation\":0},{\"dt\":1619786520,\"precipitation\":0},{\"dt\":1619786580,\"precipitation\":0},{\"dt\":1619786640,\"precipitation\":0},{\"dt\":1619786700,\"precipitation\":0},{\"dt\":1619786760,\"precipitation\":0},{\"dt\":1619786820,\"precipitation\":0},{\"dt\":1619786880,\"precipitation\":0},{\"dt\":1619786940,\"precipitation\":0},{\"dt\":1619787000,\"precipitation\":0},{\"dt\":1619787060,\"precipitation\":0},{\"dt\":1619787120,\"precipitation\":0},{\"dt\":1619787180,\"precipitation\":0},{\"dt\":1619787240,\"precipitation\":0},{\"dt\":1619787300,\"precipitation\":0},{\"dt\":1619787360,\"precipitation\":0},{\"dt\":1619787420,\"precipitation\":0},{\"dt\":1619787480,\"precipitation\":0},{\"dt\":1619787540,\"precipitation\":0},{\"dt\":1619787600,\"precipitation\":0},{\"dt\":1619787660,\"precipitation\":0},{\"dt\":1619787720,\"precipitation\":0},{\"dt\":1619787780,\"precipitation\":0},{\"dt\":1619787840,\"precipitation\":0},{\"dt\":1619787900,\"precipitation\":0},{\"dt\":1619787960,\"precipitation\":0},{\"dt\":1619788020,\"precipitation\":0},{\"dt\":1619788080,\"precipitation\":0},{\"dt\":1619788140,\"precipitation\":0},{\"dt\":1619788200,\"precipitation\":0},{\"dt\":1619788260,\"precipitation\":0},{\"dt\":1619788320,\"precipitation\":0}],\"hourly\":[{\"dt\":1619784000,\"temp\":21.46,\"feels_like\":21.23,\"pressure\":1008,\"humidity\":60,\"dew_point\":13.38,\"uvi\":5.25,\"clouds\":20,\"visibility\":10000,\"wind_speed\":0.31,\"wind_deg\":300,\"wind_gust\":2.4,\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"pop\":0},{\"dt\":1619787600,\"temp\":21.2,\"feels_like\":20.84,\"pressure\":1008,\"humidity\":56,\"dew_point\":12.08,\"uvi\":6.24,\"clouds\":31,\"visibility\":10000,\"wind_speed\":1.82,\"wind_deg\":109,\"wind_gust\":3.01,\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"pop\":0.06},{\"dt\":1619791200,\"temp\":20.26,\"feels_like\":19.86,\"pressure\":1008,\"humidity\":58,\"dew_point\":11.74,\"uvi\":5.05,\"clouds\":45,\"visibility\":10000,\"wind_speed\":3.3,\"wind_deg\":107,\"wind_gust\":3.85,\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"pop\":0.15},{\"dt\":1619794800,\"temp\":19.53,\"feels_like\":19.05,\"pressure\":1007,\"humidity\":58,\"dew_point\":11.05,\"uvi\":3.45,\"clouds\":60,\"visibility\":10000,\"wind_speed\":2.8,\"wind_deg\":111,\"wind_gust\":2.49,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"pop\":0.39,\"rain\":{\"1h\":0.65}},{\"dt\":1619798400,\"temp\":18.88,\"feels_like\":18.34,\"pressure\":1007,\"humidity\":58,\"dew_point\":10.45,\"uvi\":0.68,\"clouds\":74,\"visibility\":10000,\"wind_speed\":2.93,\"wind_deg\":107,\"wind_gust\":2.46,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"pop\":0.31,\"rain\":{\"1h\":0.4}},{\"dt\":1619802000,\"temp\":18.06,\"feels_like\":17.49,\"pressure\":1007,\"humidity\":60,\"dew_point\":9.87,\"uvi\":0.28,\"clouds\":87,\"visibility\":10000,\"wind_speed\":3.48,\"wind_deg\":108,\"wind_gust\":3.33,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"pop\":0.5,\"rain\":{\"1h\":0.21}},{\"dt\":1619805600,\"temp\":16.67,\"feels_like\":16.19,\"pressure\":1007,\"humidity\":69,\"dew_point\":10.54,\"uvi\":0.07,\"clouds\":89,\"visibility\":6223,\"wind_speed\":1.23,\"wind_deg\":128,\"wind_gust\":2.59,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"pop\":0.63,\"rain\":{\"1h\":0.98}},{\"dt\":1619809200,\"temp\":15.39,\"feels_like\":14.92,\"pressure\":1008,\"humidity\":74,\"dew_point\":10.32,\"uvi\":0,\"clouds\":100,\"visibility\":10000,\"wind_speed\":1.59,\"wind_deg\":248,\"wind_gust\":2.54,\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10n\"}],\"pop\":0.79,\"rain\":{\"1h\":1.06}},{\"dt\":1619812800,\"temp\":14.4,\"feels_like\":13.98,\"pressure\":1009,\"humidity\":80,\"dew_point\":10.47,\"uvi\":0,\"clouds\":100,\"visibility\":10000,\"wind_speed\":1.31,\"wind_deg\":242,\"wind_gust\":2.45,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"pop\":0.79,\"rain\":{\"1h\":0.86}},{\"dt\":1619816400,\"temp\":13.5,\"feels_like\":13.1,\"pressure\":1010,\"humidity\":84,\"dew_point\":10.26,\"uvi\":0,\"clouds\":100,\"visibility\":10000,\"wind_speed\":1.97,\"wind_deg\":256,\"wind_gust\":3.79,\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10n\"}],\"pop\":0.84,\"rain\":{\"1h\":1.11}},{\"dt\":1619820000,\"temp\":13.09,\"feels_like\":12.6,\"pressure\":1010,\"humidity\":82,\"dew_point\":9.61,\"uvi\":0,\"clouds\":100,\"visibility\":10000,\"wind_speed\":2.44,\"wind_deg\":291,\"wind_gust\":5.09,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"pop\":0.97,\"rain\":{\"1h\":0.66}},{\"dt\":1619823600,\"temp\":12.75,\"feels_like\":12.22,\"pressure\":1010,\"humidity\":82,\"dew_point\":9.27,\"uvi\":0,\"clouds\":100,\"visibility\":10000,\"wind_speed\":2.46,\"wind_deg\":285,\"wind_gust\":4.18,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"pop\":0.97,\"rain\":{\"1h\":0.38}},{\"dt\":1619827200,\"temp\":12.58,\"feels_like\":12.06,\"pressure\":1009,\"humidity\":83,\"dew_point\":9.17,\"uvi\":0,\"clouds\":100,\"visibility\":10000,\"wind_speed\":3.38,\"wind_deg\":287,\"wind_gust\":5.38,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"pop\":0.93,\"rain\":{\"1h\":0.2}},{\"dt\":1619830800,\"temp\":12.43,\"feels_like\":11.87,\"pressure\":1009,\"humidity\":82,\"dew_point\":9.04,\"uvi\":0,\"clouds\":100,\"visibility\":10000,\"wind_speed\":3.49,\"wind_deg\":278,\"wind_gust\":6.75,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"pop\":0.76,\"rain\":{\"1h\":0.23}},{\"dt\":1619834400,\"temp\":11.76,\"feels_like\":11.24,\"pressure\":1009,\"humidity\":86,\"dew_point\":8.99,\"uvi\":0,\"clouds\":100,\"visibility\":10000,\"wind_speed\":3.17,\"wind_deg\":290,\"wind_gust\":6.07,\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10n\"}],\"pop\":0.95,\"rain\":{\"1h\":1.16}},{\"dt\":1619838000,\"temp\":11.87,\"feels_like\":11.36,\"pressure\":1009,\"humidity\":86,\"dew_point\":9.01,\"uvi\":0,\"clouds\":100,\"visibility\":10000,\"wind_speed\":2.39,\"wind_deg\":283,\"wind_gust\":4.59,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"pop\":0.98,\"rain\":{\"1h\":0.4}},{\"dt\":1619841600,\"temp\":11.92,\"feels_like\":11.39,\"pressure\":1008,\"humidity\":85,\"dew_point\":8.91,\"uvi\":0,\"clouds\":100,\"visibility\":10000,\"wind_speed\":1.59,\"wind_deg\":293,\"wind_gust\":2.75,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"pop\":0.97,\"rain\":{\"1h\":0.14}},{\"dt\":1619845200,\"temp\":11.83,\"feels_like\":11.29,\"pressure\":1009,\"humidity\":85,\"dew_point\":8.87,\"uvi\":0,\"clouds\":100,\"visibility\":10000,\"wind_speed\":1.89,\"wind_deg\":262,\"wind_gust\":2.47,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"pop\":0.77},{\"dt\":1619848800,\"temp\":12.11,\"feels_like\":11.54,\"pressure\":1009,\"humidity\":83,\"dew_point\":8.79,\"uvi\":0.1,\"clouds\":100,\"visibility\":10000,\"wind_speed\":2.43,\"wind_deg\":280,\"wind_gust\":4.44,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"pop\":0.77},{\"dt\":1619852400,\"temp\":13.51,\"feels_like\":12.8,\"pressure\":1009,\"humidity\":72,\"dew_point\":8.1,\"uvi\":0.51,\"clouds\":100,\"visibility\":10000,\"wind_speed\":3.14,\"wind_deg\":258,\"wind_gust\":6.67,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"pop\":0.11},{\"dt\":1619856000,\"temp\":15.33,\"feels_like\":14.33,\"pressure\":1009,\"humidity\":54,\"dew_point\":5.79,\"uvi\":1.29,\"clouds\":100,\"visibility\":10000,\"wind_speed\":5.64,\"wind_deg\":280,\"wind_gust\":8.9,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"pop\":0.11},{\"dt\":1619859600,\"temp\":16.27,\"feels_like\":15.21,\"pressure\":1010,\"humidity\":48,\"dew_point\":4.86,\"uvi\":2.41,\"clouds\":100,\"visibility\":10000,\"wind_speed\":7.73,\"wind_deg\":283,\"wind_gust\":10.01,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"pop\":0.08},{\"dt\":1619863200,\"temp\":17.98,\"feels_like\":16.88,\"pressure\":1010,\"humidity\":40,\"dew_point\":3.95,\"uvi\":5.18,\"clouds\":93,\"visibility\":10000,\"wind_speed\":8.9,\"wind_deg\":281,\"wind_gust\":10.32,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"pop\":0.08},{\"dt\":1619866800,\"temp\":19.06,\"feels_like\":17.96,\"pressure\":1010,\"humidity\":36,\"dew_point\":3.41,\"uvi\":6.52,\"clouds\":75,\"visibility\":10000,\"wind_speed\":9.18,\"wind_deg\":278,\"wind_gust\":10.82,\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"pop\":0.08},{\"dt\":1619870400,\"temp\":20.2,\"feels_like\":19.14,\"pressure\":1009,\"humidity\":33,\"dew_point\":2.98,\"uvi\":7.08,\"clouds\":64,\"visibility\":10000,\"wind_speed\":8.87,\"wind_deg\":275,\"wind_gust\":10.42,\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"pop\":0.04},{\"dt\":1619874000,\"temp\":21.02,\"feels_like\":19.96,\"pressure\":1009,\"humidity\":30,\"dew_point\":2.76,\"uvi\":6.31,\"clouds\":15,\"visibility\":10000,\"wind_speed\":8.94,\"wind_deg\":275,\"wind_gust\":10.02,\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"pop\":0.06},{\"dt\":1619877600,\"temp\":21.08,\"feels_like\":20.03,\"pressure\":1009,\"humidity\":30,\"dew_point\":2.65,\"uvi\":5.12,\"clouds\":49,\"visibility\":10000,\"wind_speed\":8.78,\"wind_deg\":283,\"wind_gust\":9.4,\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"pop\":0.1},{\"dt\":1619881200,\"temp\":20.28,\"feels_like\":19.23,\"pressure\":1010,\"humidity\":33,\"dew_point\":3.4,\"uvi\":3.5,\"clouds\":64,\"visibility\":10000,\"wind_speed\":6.76,\"wind_deg\":302,\"wind_gust\":8.08,\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"pop\":0.14},{\"dt\":1619884800,\"temp\":19.19,\"feels_like\":18.13,\"pressure\":1011,\"humidity\":37,\"dew_point\":3.86,\"uvi\":2.08,\"clouds\":72,\"visibility\":10000,\"wind_speed\":5.05,\"wind_deg\":298,\"wind_gust\":7.41,\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"pop\":0.14},{\"dt\":1619888400,\"temp\":19.01,\"feels_like\":17.93,\"pressure\":1011,\"humidity\":37,\"dew_point\":3.58,\"uvi\":0.86,\"clouds\":64,\"visibility\":10000,\"wind_speed\":5.09,\"wind_deg\":306,\"wind_gust\":7.18,\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"pop\":0.14},{\"dt\":1619892000,\"temp\":17.33,\"feels_like\":16.35,\"pressure\":1012,\"humidity\":47,\"dew_point\":5.42,\"uvi\":0.22,\"clouds\":59,\"visibility\":10000,\"wind_speed\":2.44,\"wind_deg\":321,\"wind_gust\":4.9,\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"pop\":0.1},{\"dt\":1619895600,\"temp\":14.15,\"feels_like\":13.21,\"pressure\":1014,\"humidity\":61,\"dew_point\":6.17,\"uvi\":0,\"clouds\":84,\"visibility\":10000,\"wind_speed\":0.18,\"wind_deg\":335,\"wind_gust\":1.75,\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"pop\":0},{\"dt\":1619899200,\"temp\":13.1,\"feels_like\":12.27,\"pressure\":1015,\"humidity\":69,\"dew_point\":6.86,\"uvi\":0,\"clouds\":91,\"visibility\":10000,\"wind_speed\":0.47,\"wind_deg\":39,\"wind_gust\":1.26,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"pop\":0},{\"dt\":1619902800,\"temp\":12.39,\"feels_like\":11.64,\"pressure\":1016,\"humidity\":75,\"dew_point\":7.49,\"uvi\":0,\"clouds\":92,\"visibility\":10000,\"wind_speed\":0.46,\"wind_deg\":69,\"wind_gust\":0.85,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"pop\":0},{\"dt\":1619906400,\"temp\":12.04,\"feels_like\":11.36,\"pressure\":1016,\"humidity\":79,\"dew_point\":7.94,\"uvi\":0,\"clouds\":94,\"visibility\":10000,\"wind_speed\":0.3,\"wind_deg\":55,\"wind_gust\":0.65,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"pop\":0},{\"dt\":1619910000,\"temp\":11.72,\"feels_like\":11.06,\"pressure\":1016,\"humidity\":81,\"dew_point\":8.1,\"uvi\":0,\"clouds\":95,\"visibility\":10000,\"wind_speed\":0.78,\"wind_deg\":353,\"wind_gust\":0.97,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"pop\":0},{\"dt\":1619913600,\"temp\":11.48,\"feels_like\":10.85,\"pressure\":1016,\"humidity\":83,\"dew_point\":8.07,\"uvi\":0,\"clouds\":96,\"visibility\":10000,\"wind_speed\":1.27,\"wind_deg\":345,\"wind_gust\":1.39,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"pop\":0},{\"dt\":1619917200,\"temp\":11.3,\"feels_like\":10.63,\"pressure\":1016,\"humidity\":82,\"dew_point\":7.89,\"uvi\":0,\"clouds\":100,\"visibility\":10000,\"wind_speed\":1.45,\"wind_deg\":328,\"wind_gust\":1.44,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"pop\":0.01},{\"dt\":1619920800,\"temp\":11.32,\"feels_like\":10.62,\"pressure\":1016,\"humidity\":81,\"dew_point\":7.5,\"uvi\":0,\"clouds\":99,\"visibility\":10000,\"wind_speed\":1.74,\"wind_deg\":321,\"wind_gust\":1.71,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"pop\":0.05},{\"dt\":1619924400,\"temp\":11.06,\"feels_like\":10.31,\"pressure\":1016,\"humidity\":80,\"dew_point\":7.2,\"uvi\":0,\"clouds\":73,\"visibility\":10000,\"wind_speed\":1.68,\"wind_deg\":328,\"wind_gust\":1.7,\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"pop\":0.05},{\"dt\":1619928000,\"temp\":10.65,\"feels_like\":9.86,\"pressure\":1016,\"humidity\":80,\"dew_point\":6.86,\"uvi\":0,\"clouds\":60,\"visibility\":10000,\"wind_speed\":1.2,\"wind_deg\":320,\"wind_gust\":1.17,\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"pop\":0.01},{\"dt\":1619931600,\"temp\":10.87,\"feels_like\":10.08,\"pressure\":1016,\"humidity\":79,\"dew_point\":6.84,\"uvi\":0,\"clouds\":68,\"visibility\":10000,\"wind_speed\":0.8,\"wind_deg\":317,\"wind_gust\":0.78,\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"pop\":0.01},{\"dt\":1619935200,\"temp\":12.18,\"feels_like\":11.41,\"pressure\":1017,\"humidity\":75,\"dew_point\":7.41,\"uvi\":0.19,\"clouds\":73,\"visibility\":10000,\"wind_speed\":0.58,\"wind_deg\":179,\"wind_gust\":0.67,\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"pop\":0},{\"dt\":1619938800,\"temp\":13.42,\"feels_like\":12.65,\"pressure\":1017,\"humidity\":70,\"dew_point\":7.56,\"uvi\":0.76,\"clouds\":97,\"visibility\":10000,\"wind_speed\":1.01,\"wind_deg\":140,\"wind_gust\":1.08,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"pop\":0},{\"dt\":1619942400,\"temp\":14.58,\"feels_like\":13.84,\"pressure\":1017,\"humidity\":67,\"dew_point\":8.11,\"uvi\":1.9,\"clouds\":96,\"visibility\":10000,\"wind_speed\":1.7,\"wind_deg\":115,\"wind_gust\":1.49,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"pop\":0.03},{\"dt\":1619946000,\"temp\":15.52,\"feels_like\":14.82,\"pressure\":1017,\"humidity\":65,\"dew_point\":8.5,\"uvi\":3.53,\"clouds\":92,\"visibility\":10000,\"wind_speed\":2.56,\"wind_deg\":106,\"wind_gust\":2.27,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"pop\":0.09},{\"dt\":1619949600,\"temp\":16.46,\"feels_like\":15.75,\"pressure\":1017,\"humidity\":61,\"dew_point\":8.62,\"uvi\":5.42,\"clouds\":88,\"visibility\":10000,\"wind_speed\":2.87,\"wind_deg\":101,\"wind_gust\":2.47,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"pop\":0.1},{\"dt\":1619953200,\"temp\":17.6,\"feels_like\":16.88,\"pressure\":1017,\"humidity\":56,\"dew_point\":8.2,\"uvi\":6.83,\"clouds\":76,\"visibility\":10000,\"wind_speed\":3.21,\"wind_deg\":100,\"wind_gust\":2.75,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"pop\":0.34,\"rain\":{\"1h\":0.33}}],\"daily\":[{\"dt\":1619784000,\"sunrise\":1619759140,\"sunset\":1619808895,\"moonrise\":1619734020,\"moonset\":1619768280,\"moon_phase\":0.63,\"temp\":{\"day\":21.46,\"min\":12.91,\"max\":21.46,\"night\":13.5,\"eve\":16.67,\"morn\":13.76},\"feels_like\":{\"day\":21.23,\"night\":12.86,\"eve\":16.19,\"morn\":12.86},\"pressure\":1008,\"humidity\":60,\"dew_point\":13.38,\"wind_speed\":3.48,\"wind_deg\":108,\"wind_gust\":5.62,\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10d\"}],\"clouds\":20,\"pop\":0.84,\"rain\":5.27,\"uvi\":6.24},{\"dt\":1619870400,\"sunrise\":1619845467,\"sunset\":1619895355,\"moonrise\":1619824680,\"moonset\":1619858100,\"moon_phase\":0.66,\"temp\":{\"day\":20.2,\"min\":11.76,\"max\":21.08,\"night\":12.39,\"eve\":17.33,\"morn\":12.11},\"feels_like\":{\"day\":19.14,\"night\":11.54,\"eve\":16.35,\"morn\":11.54},\"pressure\":1009,\"humidity\":33,\"dew_point\":2.98,\"wind_speed\":9.18,\"wind_deg\":278,\"wind_gust\":10.82,\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10d\"}],\"clouds\":64,\"pop\":0.98,\"rain\":3.17,\"uvi\":7.08},{\"dt\":1619956800,\"sunrise\":1619931796,\"sunset\":1619981814,\"moonrise\":1619914680,\"moonset\":1619948400,\"moon_phase\":0.7,\"temp\":{\"day\":18.33,\"min\":10.65,\"max\":18.48,\"night\":12.67,\"eve\":13.96,\"morn\":12.18},\"feels_like\":{\"day\":17.58,\"night\":11.41,\"eve\":13.32,\"morn\":11.41},\"pressure\":1017,\"humidity\":52,\"dew_point\":7.97,\"wind_speed\":6.38,\"wind_deg\":98,\"wind_gust\":5.26,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":66,\"pop\":0.34,\"rain\":1.02,\"uvi\":7.4},{\"dt\":1620039600,\"sunrise\":1620018125,\"sunset\":1620068274,\"moonrise\":1620004140,\"moonset\":1620038760,\"moon_phase\":0.75,\"temp\":{\"day\":14.33,\"min\":10.16,\"max\":14.33,\"night\":12.29,\"eve\":13.24,\"morn\":11.25},\"feels_like\":{\"day\":13.91,\"night\":10.39,\"eve\":12.87,\"morn\":10.39},\"pressure\":1021,\"humidity\":80,\"dew_point\":10.47,\"wind_speed\":5.26,\"wind_deg\":76,\"wind_gust\":5.81,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":94,\"pop\":0.4,\"rain\":1.16,\"uvi\":5.21},{\"dt\":1620126000,\"sunrise\":1620104456,\"sunset\":1620154733,\"moonrise\":1620092940,\"moonset\":1620129180,\"moon_phase\":0.77,\"temp\":{\"day\":22.47,\"min\":11.91,\"max\":23.29,\"night\":14.32,\"eve\":18.83,\"morn\":11.91},\"feels_like\":{\"day\":21.82,\"night\":11.3,\"eve\":18.34,\"morn\":11.3},\"pressure\":1017,\"humidity\":40,\"dew_point\":7.91,\"wind_speed\":4.55,\"wind_deg\":98,\"wind_gust\":4.01,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":0,\"pop\":0,\"uvi\":7.89},{\"dt\":1620212400,\"sunrise\":1620190789,\"sunset\":1620241193,\"moonrise\":1620181320,\"moonset\":1620219480,\"moon_phase\":0.8,\"temp\":{\"day\":24.26,\"min\":13.02,\"max\":24.26,\"night\":15.19,\"eve\":19.4,\"morn\":15.32},\"feels_like\":{\"day\":23.81,\"night\":14.74,\"eve\":18.94,\"morn\":14.74},\"pressure\":1012,\"humidity\":41,\"dew_point\":9.85,\"wind_speed\":4.68,\"wind_deg\":91,\"wind_gust\":3.7,\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"clouds\":32,\"pop\":0.03,\"uvi\":8},{\"dt\":1620298800,\"sunrise\":1620277122,\"sunset\":1620327652,\"moonrise\":1620269460,\"moonset\":1620309600,\"moon_phase\":0.83,\"temp\":{\"day\":27.34,\"min\":14.96,\"max\":27.84,\"night\":19.67,\"eve\":24.88,\"morn\":16.65},\"feels_like\":{\"day\":26.45,\"night\":16.12,\"eve\":24.1,\"morn\":16.12},\"pressure\":1009,\"humidity\":25,\"dew_point\":5.32,\"wind_speed\":9.51,\"wind_deg\":276,\"wind_gust\":12.51,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":5,\"pop\":0,\"uvi\":8},{\"dt\":1620385200,\"sunrise\":1620363457,\"sunset\":1620414110,\"moonrise\":1620357360,\"moonset\":1620399660,\"moon_phase\":0.87,\"temp\":{\"day\":23.43,\"min\":13.38,\"max\":23.43,\"night\":13.38,\"eve\":18.04,\"morn\":14.29},\"feels_like\":{\"day\":22.4,\"night\":13.11,\"eve\":17.28,\"morn\":13.11},\"pressure\":1017,\"humidity\":22,\"dew_point\":0.77,\"wind_speed\":3.69,\"wind_deg\":39,\"wind_gust\":4.97,\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"clouds\":43,\"pop\":0,\"uvi\":8}]}";

        return WebServicesHelper.convertWebServiceAnswer(openWeatherCompleteForecastWebServiceAnswer, OpenWeatherCompleteForecast.class);
    }

}

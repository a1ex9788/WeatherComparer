package a1ex9788.dadm.weathercomparer.webServices.forecasts.weatherBit;

import a1ex9788.dadm.weathercomparer.webServices.WebServicesHelper;

public class WeatherBitMockForecast extends WeatherBitForecast {

    public WeatherBitMockForecast(double latitude, double longitude) {
        super(latitude, longitude);
    }

    @Override
    protected WeatherBitDailyForecast getWeatherBitDailyForecast() throws Exception {
        String weatherBitDailyForecastWebServiceAnswer = "{\"data\":[{\"moonrise_ts\":1619734135,\"wind_cdir\":\"SSW\",\"rh\":62,\"pres\":954.378,\"high_temp\":19.5,\"sunset_ts\":1619809027,\"ozone\":349.8,\"moon_phase\":0.856346,\"wind_gust_spd\":11.0632,\"snow_depth\":0,\"clouds\":91,\"ts\":1619733660,\"sunrise_ts\":1619759230,\"app_min_temp\":12.1,\"wind_spd\":2.72353,\"pop\":80,\"wind_cdir_full\":\"south-southwest\",\"slp\":1008.64,\"moon_phase_lunation\":0.6,\"valid_date\":\"2021-04-30\",\"app_max_temp\":18.5,\"vis\":0,\"dewpt\":8.6,\"snow\":0,\"uv\":4.21097,\"weather\":{\"icon\":\"r01d\",\"code\":500,\"description\":\"Light rain\"},\"wind_dir\":202,\"max_dhi\":null,\"clouds_hi\":53,\"precip\":4.4928,\"low_temp\":11.9,\"max_temp\":19.5,\"moonset_ts\":1619768270,\"datetime\":\"2021-04-30\",\"temp\":16.1,\"min_temp\":12.1,\"clouds_mid\":77,\"clouds_low\":47},{\"moonrise_ts\":1619824756,\"wind_cdir\":\"WSW\",\"rh\":60,\"pres\":955.952,\"high_temp\":19.3,\"sunset_ts\":1619895486,\"ozone\":350.719,\"moon_phase\":0.761009,\"wind_gust_spd\":16.0686,\"snow_depth\":0,\"clouds\":75,\"ts\":1619820060,\"sunrise_ts\":1619845554,\"app_min_temp\":11.6,\"wind_spd\":4.64438,\"pop\":65,\"wind_cdir_full\":\"west-southwest\",\"slp\":1010.37,\"moon_phase_lunation\":0.64,\"valid_date\":\"2021-05-01\",\"app_max_temp\":18.2,\"vis\":0,\"dewpt\":6.6,\"snow\":0,\"uv\":8.12565,\"weather\":{\"icon\":\"c04d\",\"code\":804,\"description\":\"Overcast clouds\"},\"wind_dir\":255,\"max_dhi\":null,\"clouds_hi\":57,\"precip\":2.05859,\"low_temp\":10.5,\"max_temp\":19.3,\"moonset_ts\":1619858142,\"datetime\":\"2021-05-01\",\"temp\":15.2,\"min_temp\":11.6,\"clouds_mid\":68,\"clouds_low\":20},{\"moonrise_ts\":1619911156,\"wind_cdir\":\"SSE\",\"rh\":73,\"pres\":961.746,\"high_temp\":16.6,\"sunset_ts\":1619981945,\"ozone\":350.969,\"moon_phase\":0.653432,\"wind_gust_spd\":10.3944,\"snow_depth\":0,\"clouds\":71,\"ts\":1619906460,\"sunrise_ts\":1619931880,\"app_min_temp\":9.8,\"wind_spd\":2.13306,\"pop\":75,\"wind_cdir_full\":\"south-southeast\",\"slp\":1016.96,\"moon_phase_lunation\":0.67,\"valid_date\":\"2021-05-02\",\"app_max_temp\":16.6,\"vis\":0,\"dewpt\":8.6,\"snow\":0,\"uv\":5.00634,\"weather\":{\"icon\":\"r04d\",\"code\":520,\"description\":\"Light shower rain\"},\"wind_dir\":156,\"max_dhi\":null,\"clouds_hi\":11,\"precip\":3.24609,\"low_temp\":9.8,\"max_temp\":16.6,\"moonset_ts\":1619948390,\"datetime\":\"2021-05-02\",\"temp\":13,\"min_temp\":9.8,\"clouds_mid\":47,\"clouds_low\":37},{\"moonrise_ts\":1620001194,\"wind_cdir\":\"E\",\"rh\":81,\"pres\":969.833,\"high_temp\":14.1,\"sunset_ts\":1620068404,\"ozone\":357.875,\"moon_phase\":0.541173,\"wind_gust_spd\":6.5,\"snow_depth\":0,\"clouds\":73,\"ts\":1619992860,\"sunrise_ts\":1620018207,\"app_min_temp\":9.1,\"wind_spd\":2.36718,\"pop\":60,\"wind_cdir_full\":\"east\",\"slp\":1020.21,\"moon_phase_lunation\":0.7,\"valid_date\":\"2021-05-03\",\"app_max_temp\":14.1,\"vis\":22.8493,\"dewpt\":8.2,\"snow\":0,\"uv\":2.95256,\"weather\":{\"icon\":\"c04d\",\"code\":804,\"description\":\"Overcast clouds\"},\"wind_dir\":88,\"max_dhi\":null,\"clouds_hi\":1,\"precip\":1.625,\"low_temp\":8.9,\"max_temp\":14.3,\"moonset_ts\":1620038817,\"datetime\":\"2021-05-03\",\"temp\":11.5,\"min_temp\":9,\"clouds_mid\":1,\"clouds_low\":73},{\"moonrise_ts\":1620090590,\"wind_cdir\":\"SSE\",\"rh\":68,\"pres\":968.25,\"high_temp\":23.7,\"sunset_ts\":1620154863,\"ozone\":347.229,\"moon_phase\":0.430689,\"wind_gust_spd\":3.90039,\"snow_depth\":0,\"clouds\":8,\"ts\":1620079260,\"sunrise_ts\":1620104536,\"app_min_temp\":8.8,\"wind_spd\":1.92097,\"pop\":0,\"wind_cdir_full\":\"south-southeast\",\"slp\":1018.19,\"moon_phase_lunation\":0.74,\"valid_date\":\"2021-05-04\",\"app_max_temp\":23,\"vis\":24.128,\"dewpt\":8.5,\"snow\":0,\"uv\":9.44929,\"weather\":{\"icon\":\"c02d\",\"code\":801,\"description\":\"Few clouds\"},\"wind_dir\":166,\"max_dhi\":null,\"clouds_hi\":0,\"precip\":0,\"low_temp\":8.8,\"max_temp\":23.9,\"moonset_ts\":1620129236,\"datetime\":\"2021-05-04\",\"temp\":15.1,\"min_temp\":8.7,\"clouds_mid\":0,\"clouds_low\":8},{\"moonrise_ts\":1620179419,\"wind_cdir\":\"SW\",\"rh\":68,\"pres\":964.321,\"high_temp\":26.7,\"sunset_ts\":1620241322,\"ozone\":346.821,\"moon_phase\":0.326927,\"wind_gust_spd\":3.20508,\"snow_depth\":0,\"clouds\":6,\"ts\":1620165660,\"sunrise_ts\":1620190865,\"app_min_temp\":12.2,\"wind_spd\":1.57425,\"pop\":0,\"wind_cdir_full\":\"southwest\",\"slp\":1013.86,\"moon_phase_lunation\":0.77,\"valid_date\":\"2021-05-05\",\"app_max_temp\":22.9,\"vis\":24.128,\"dewpt\":9.1,\"snow\":0,\"uv\":9.40933,\"weather\":{\"icon\":\"c02d\",\"code\":801,\"description\":\"Few clouds\"},\"wind_dir\":233,\"max_dhi\":null,\"clouds_hi\":5,\"precip\":0,\"low_temp\":12.2,\"max_temp\":24,\"moonset_ts\":1620219537,\"datetime\":\"2021-05-05\",\"temp\":15.3,\"min_temp\":12.1,\"clouds_mid\":2,\"clouds_low\":0},{\"moonrise_ts\":1620267813,\"wind_cdir\":\"W\",\"rh\":47,\"pres\":959.438,\"high_temp\":22.6,\"sunset_ts\":1620327781,\"ozone\":337.031,\"moon_phase\":0.233514,\"wind_gust_spd\":12.1172,\"snow_depth\":0,\"clouds\":22,\"ts\":1620252060,\"sunrise_ts\":1620277196,\"app_min_temp\":13.7,\"wind_spd\":5.32774,\"pop\":0,\"wind_cdir_full\":\"west\",\"slp\":1008.31,\"moon_phase_lunation\":0.8,\"valid_date\":\"2021-05-06\",\"app_max_temp\":26,\"vis\":24.128,\"dewpt\":7.5,\"snow\":0,\"uv\":9.54388,\"weather\":{\"icon\":\"c02d\",\"code\":802,\"description\":\"Scattered clouds\"},\"wind_dir\":267,\"max_dhi\":null,\"clouds_hi\":22,\"precip\":0,\"low_temp\":12.4,\"max_temp\":26.9,\"moonset_ts\":1620309691,\"datetime\":\"2021-05-06\",\"temp\":20.2,\"min_temp\":13.6,\"clouds_mid\":0,\"clouds_low\":1},{\"moonrise_ts\":1620355904,\"wind_cdir\":\"S\",\"rh\":54,\"pres\":966.812,\"high_temp\":22.6,\"sunset_ts\":1620414239,\"ozone\":337.844,\"moon_phase\":0.15317,\"wind_gust_spd\":5.60938,\"snow_depth\":0,\"clouds\":47,\"ts\":1620338460,\"sunrise_ts\":1620363528,\"app_min_temp\":12.4,\"wind_spd\":2.48857,\"pop\":0,\"wind_cdir_full\":\"south\",\"slp\":1016.44,\"moon_phase_lunation\":0.84,\"valid_date\":\"2021-05-07\",\"app_max_temp\":21.7,\"vis\":24.128,\"dewpt\":6.1,\"snow\":0,\"uv\":9.25793,\"weather\":{\"icon\":\"c03d\",\"code\":803,\"description\":\"Broken clouds\"},\"wind_dir\":171,\"max_dhi\":null,\"clouds_hi\":47,\"precip\":0,\"low_temp\":11.9,\"max_temp\":24.4,\"moonset_ts\":1620399721,\"datetime\":\"2021-05-07\",\"temp\":16.6,\"min_temp\":11.3,\"clouds_mid\":0,\"clouds_low\":1},{\"moonrise_ts\":1620443801,\"wind_cdir\":\"SSE\",\"rh\":68,\"pres\":968.812,\"high_temp\":20.7,\"sunset_ts\":1620500697,\"ozone\":332.75,\"moon_phase\":0.0880708,\"wind_gust_spd\":3.70898,\"snow_depth\":0,\"clouds\":11,\"ts\":1620424860,\"sunrise_ts\":1620449861,\"app_min_temp\":11.9,\"wind_spd\":2.49571,\"pop\":0,\"wind_cdir_full\":\"south-southeast\",\"slp\":1018.62,\"moon_phase_lunation\":0.87,\"valid_date\":\"2021-05-08\",\"app_max_temp\":22.1,\"vis\":24.128,\"dewpt\":10.2,\"snow\":0,\"uv\":7.27189,\"weather\":{\"icon\":\"c02d\",\"code\":801,\"description\":\"Few clouds\"},\"wind_dir\":148,\"max_dhi\":null,\"clouds_hi\":11,\"precip\":0,\"low_temp\":11.5,\"max_temp\":23.6,\"moonset_ts\":1620489671,\"datetime\":\"2021-05-08\",\"temp\":16.6,\"min_temp\":11.7,\"clouds_mid\":1,\"clouds_low\":0},{\"moonrise_ts\":1620531592,\"wind_cdir\":\"E\",\"rh\":74,\"pres\":965.125,\"high_temp\":17.4,\"sunset_ts\":1620587155,\"ozone\":324.875,\"moon_phase\":0.0400172,\"wind_gust_spd\":3.50586,\"snow_depth\":0,\"clouds\":70,\"ts\":1620511260,\"sunrise_ts\":1620536196,\"app_min_temp\":11.5,\"wind_spd\":2.48526,\"pop\":0,\"wind_cdir_full\":\"east\",\"slp\":1014.81,\"moon_phase_lunation\":0.91,\"valid_date\":\"2021-05-09\",\"app_max_temp\":20.2,\"vis\":24.128,\"dewpt\":10.4,\"snow\":0,\"uv\":5.72937,\"weather\":{\"icon\":\"c04d\",\"code\":804,\"description\":\"Overcast clouds\"},\"wind_dir\":92,\"max_dhi\":null,\"clouds_hi\":68,\"precip\":0,\"low_temp\":10.8,\"max_temp\":21.9,\"moonset_ts\":1620579591,\"datetime\":\"2021-05-09\",\"temp\":15.4,\"min_temp\":11.1,\"clouds_mid\":25,\"clouds_low\":6},{\"moonrise_ts\":1620619352,\"wind_cdir\":\"S\",\"rh\":85,\"pres\":960,\"high_temp\":20.7,\"sunset_ts\":1620673613,\"ozone\":323.375,\"moon_phase\":0.0104289,\"wind_gust_spd\":9.00781,\"snow_depth\":0,\"clouds\":36,\"ts\":1620597660,\"sunrise_ts\":1620622532,\"app_min_temp\":12.3,\"wind_spd\":1.2507,\"pop\":45,\"wind_cdir_full\":\"south\",\"slp\":1009.62,\"moon_phase_lunation\":0.94,\"valid_date\":\"2021-05-10\",\"app_max_temp\":17,\"vis\":24.128,\"dewpt\":11.1,\"snow\":0,\"uv\":1.52229,\"weather\":{\"icon\":\"c02d\",\"code\":802,\"description\":\"Scattered clouds\"},\"wind_dir\":185,\"max_dhi\":null,\"clouds_hi\":0,\"precip\":2.4375,\"low_temp\":15,\"max_temp\":26,\"moonset_ts\":1620669523,\"datetime\":\"2021-05-10\",\"temp\":13.7,\"min_temp\":11.8,\"clouds_mid\":24,\"clouds_low\":36},{\"moonrise_ts\":1620707146,\"wind_cdir\":\"W\",\"rh\":53,\"pres\":963.25,\"high_temp\":23.1,\"sunset_ts\":1620760070,\"ozone\":346.25,\"moon_phase\":0.000229449,\"wind_gust_spd\":16.8906,\"snow_depth\":0,\"clouds\":0,\"ts\":1620684060,\"sunrise_ts\":1620708870,\"app_min_temp\":12.3,\"wind_spd\":9.14921,\"pop\":0,\"wind_cdir_full\":\"west\",\"slp\":1013,\"moon_phase_lunation\":0.97,\"valid_date\":\"2021-05-11\",\"app_max_temp\":17.4,\"vis\":24.128,\"dewpt\":4.7,\"snow\":0,\"uv\":1.5151,\"weather\":{\"icon\":\"c01d\",\"code\":800,\"description\":\"Clear Sky\"},\"wind_dir\":280,\"max_dhi\":null,\"clouds_hi\":0,\"precip\":0,\"low_temp\":18.9,\"max_temp\":20,\"moonset_ts\":1620759488,\"datetime\":\"2021-05-11\",\"temp\":14.9,\"min_temp\":9.5,\"clouds_mid\":0,\"clouds_low\":0},{\"moonrise_ts\":1620795042,\"wind_cdir\":\"S\",\"rh\":58,\"pres\":971.5,\"high_temp\":25.6,\"sunset_ts\":1620846527,\"ozone\":313.875,\"moon_phase\":0.00972637,\"wind_gust_spd\":4.69922,\"snow_depth\":0,\"clouds\":50,\"ts\":1620770460,\"sunrise_ts\":1620795209,\"app_min_temp\":10.8,\"wind_spd\":2.81027,\"pop\":0,\"wind_cdir_full\":\"south\",\"slp\":1021.5,\"moon_phase_lunation\":0.01,\"valid_date\":\"2021-05-12\",\"app_max_temp\":18.1,\"vis\":24.128,\"dewpt\":6.4,\"snow\":0,\"uv\":1.56838,\"weather\":{\"icon\":\"c03d\",\"code\":803,\"description\":\"Broken clouds\"},\"wind_dir\":169,\"max_dhi\":null,\"clouds_hi\":50,\"precip\":0,\"low_temp\":20.6,\"max_temp\":23.5,\"moonset_ts\":1620849478,\"datetime\":\"2021-05-12\",\"temp\":14.8,\"min_temp\":10.8,\"clouds_mid\":0,\"clouds_low\":0},{\"moonrise_ts\":1620883110,\"wind_cdir\":\"SSE\",\"rh\":48,\"pres\":972.5,\"high_temp\":25,\"sunset_ts\":1620932984,\"ozone\":310,\"moon_phase\":0.0385532,\"wind_gust_spd\":2.5,\"snow_depth\":0,\"clouds\":5,\"ts\":1620856860,\"sunrise_ts\":1620881550,\"app_min_temp\":15,\"wind_spd\":1.97803,\"pop\":0,\"wind_cdir_full\":\"south-southeast\",\"slp\":1022.25,\"moon_phase_lunation\":0.04,\"valid_date\":\"2021-05-13\",\"app_max_temp\":19.9,\"vis\":24.128,\"dewpt\":6.4,\"snow\":0,\"uv\":1.53412,\"weather\":{\"icon\":\"c02d\",\"code\":801,\"description\":\"Few clouds\"},\"wind_dir\":165,\"max_dhi\":null,\"clouds_hi\":5,\"precip\":0,\"low_temp\":14.6,\"max_temp\":25,\"moonset_ts\":1620939444,\"datetime\":\"2021-05-13\",\"temp\":17.8,\"min_temp\":14.6,\"clouds_mid\":0,\"clouds_low\":0},{\"moonrise_ts\":1620971427,\"wind_cdir\":\"S\",\"rh\":42,\"pres\":973,\"high_temp\":26.9,\"sunset_ts\":1621019440,\"ozone\":310.875,\"moon_phase\":0.0857053,\"wind_gust_spd\":2.41406,\"snow_depth\":0,\"clouds\":26,\"ts\":1620943260,\"sunrise_ts\":1620967892,\"app_min_temp\":16.6,\"wind_spd\":1.60578,\"pop\":0,\"wind_cdir_full\":\"south\",\"slp\":1022.75,\"moon_phase_lunation\":0.07,\"valid_date\":\"2021-05-14\",\"app_max_temp\":22.4,\"vis\":24.128,\"dewpt\":6.7,\"snow\":0,\"uv\":1.56215,\"weather\":{\"icon\":\"c02d\",\"code\":802,\"description\":\"Scattered clouds\"},\"wind_dir\":171,\"max_dhi\":null,\"clouds_hi\":26,\"precip\":0,\"low_temp\":16.5,\"max_temp\":26.9,\"moonset_ts\":1621029296,\"datetime\":\"2021-05-14\",\"temp\":19.9,\"min_temp\":16.5,\"clouds_mid\":0,\"clouds_low\":0},{\"moonrise_ts\":1621060070,\"wind_cdir\":\"S\",\"rh\":43,\"pres\":972.25,\"high_temp\":29,\"sunset_ts\":1621105896,\"ozone\":320.25,\"moon_phase\":0.14965,\"wind_gust_spd\":3.40625,\"snow_depth\":0,\"clouds\":1,\"ts\":1621029660,\"sunrise_ts\":1621054235,\"app_min_temp\":18.1,\"wind_spd\":2.26268,\"pop\":0,\"wind_cdir_full\":\"south\",\"slp\":1021.75,\"moon_phase_lunation\":0.11,\"valid_date\":\"2021-05-15\",\"app_max_temp\":25.1,\"vis\":24.128,\"dewpt\":8.8,\"snow\":0,\"uv\":1.57683,\"weather\":{\"icon\":\"c02d\",\"code\":801,\"description\":\"Few clouds\"},\"wind_dir\":172,\"max_dhi\":null,\"clouds_hi\":1,\"precip\":0,\"low_temp\":18.8,\"max_temp\":29,\"moonset_ts\":1621032525,\"datetime\":\"2021-05-15\",\"temp\":22.2,\"min_temp\":18.8,\"clouds_mid\":0,\"clouds_low\":0}],\"city_name\":\"Dos Aguas\",\"lon\":-0.8,\"timezone\":\"Europe\\/Madrid\",\"lat\":39.29,\"country_code\":\"ES\",\"state_code\":\"60\"}";

        return WebServicesHelper.convertWebServiceAnswer(weatherBitDailyForecastWebServiceAnswer, WeatherBitDailyForecast.class);
    }

    @Override
    protected WeatherBitHourlyForecast getWeatherBitHourlyForecast() throws Exception {
        // The web service answer could not be obtained because the pricing categories changed and we do not have more access.
        return new WeatherBitHourlyForecast();
    }

}

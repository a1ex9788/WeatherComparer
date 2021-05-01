package a1ex9788.dadm.weathercomparer.webServices;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ApiKeys {

    private static final List<String> accuWeatherApiKeys = Arrays.asList(
            "yOLINkCqLbJQEg8YcjA1juHumFGgpIp0", "weGTdLNUGLnzcqp1XZdgmXgiyHqWkmZj",
            "AwPKW43ddOLFEGChdK5SLzAYhAJAxzGd");

    public static String getAccuWeatherApiKey() {
        Random random = new Random();

        return accuWeatherApiKeys.get(random.nextInt(accuWeatherApiKeys.size()));
    }

    public static String getOpenWeatherApiKey() {
        return "29351929fe3ab873d5ba07eeab75f5d8";
    }

    public static String getWeatherBitApiKey() {
        return "65e807b828e44105b3f8a64cb5e82604";
    }

    public static String getGoogleApiKey() {
        return "AIzaSyAiKQz6mYGVdAYfIWDxkTiOIa0x86e2ntA";
    }

}

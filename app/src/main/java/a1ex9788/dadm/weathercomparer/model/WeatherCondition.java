package a1ex9788.dadm.weathercomparer.model;

public enum WeatherCondition {
    // TODO: Substitute the strings of the weather conditions for a resource in order to enable the language change.
    Clear("Clear", "https://assets7.lottiefiles.com/temp/lf20_Stdaec.json", 1),
    BrokenClouds("Broken clouds", "https://assets5.lottiefiles.com/temp/lf20_dgjK9i.json", 2),
    Clouds("Clouds", "https://assets7.lottiefiles.com/temp/lf20_VAmWRg.json", 3),
    Rain("Rain", "https://assets6.lottiefiles.com/temp/lf20_rpC1Rd.json", 4),
    Snow("Snow", "https://assets7.lottiefiles.com/temp/lf20_WtPCZs.json", 5),
    Fog("Fog", "https://assets7.lottiefiles.com/temp/lf20_kOfPKE.json", 6),
    Flurries("Flurries", "https://assets9.lottiefiles.com/packages/lf20_J8EPQ1.json", 7),
    Thunderstorm("Thunderstorm", "https://assets7.lottiefiles.com/temp/lf20_Kuot2e.json", 8),
    ThunderstormWithRain("Thunderstorm with rain", "https://assets7.lottiefiles.com/temp/lf20_XkF78Y.json", 9),
    UnknownPrecipitation("Unknown precipitation", "https://assets10.lottiefiles.com/temp/lf20_9gY9Yf.json", 10),
    Unknown("Unknown", "https://assets10.lottiefiles.com/temp/lf20_9gY9Yf.json", 11);

    private String text, iconAddress;
    // Integer value for calculating the average of different weather conditions.
    private int value;

    WeatherCondition(String text, String iconAddress, int value) {
        this.text = text;
        this.iconAddress = iconAddress;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public String getIconAddress() {
        return iconAddress;
    }

    public int getValue() {
        return value;
    }
}

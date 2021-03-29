package a1ex9788.dadm.weathercomparer.model;

public enum WeatherCondition {
    // TODO: Substitute the strings of the weather conditions for a resource in order to enable the language change.
    Clear("Clear", "https://assets7.lottiefiles.com/temp/lf20_Stdaec.json"),
    Clouds("Clouds", "https://assets7.lottiefiles.com/temp/lf20_VAmWRg.json"),
    BrokenClouds("Broken clouds", "https://assets5.lottiefiles.com/temp/lf20_dgjK9i.json"),
    Rain("Rain", "https://assets6.lottiefiles.com/temp/lf20_rpC1Rd.json"),
    Snow("Snow", "https://assets7.lottiefiles.com/temp/lf20_WtPCZs.json"),
    Fog("Fog", "https://assets7.lottiefiles.com/temp/lf20_kOfPKE.json"),
    Flurries("Flurries", "https://assets9.lottiefiles.com/packages/lf20_J8EPQ1.json"),
    Thunderstorm("Thunderstorm", "https://assets7.lottiefiles.com/temp/lf20_Kuot2e.json"),
    ThunderstormWithRain("Thunderstorm with rain", "https://assets7.lottiefiles.com/temp/lf20_XkF78Y.json"),
    UnknownPrecipitation("Unknown precipitation", "https://assets10.lottiefiles.com/temp/lf20_9gY9Yf.json"),
    Unknown("Unknown", "https://assets10.lottiefiles.com/temp/lf20_9gY9Yf.json");

    private String text, iconAddress;

    WeatherCondition(String text, String iconAddress) {
        this.text = text;
        this.iconAddress = iconAddress;
    }

    public String getText() {
        return text;
    }

    public String getIconAddress() {
        return iconAddress;
    }
}

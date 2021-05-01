package a1ex9788.dadm.weathercomparer.model;

import a1ex9788.dadm.weathercomparer.R;

public enum WeatherCondition {
	Sunny(R.string.sunny, "https://assets7.lottiefiles.com/temp/lf20_Stdaec.json", 1),
	Cloudy(R.string.cloudy, "https://assets5.lottiefiles.com/temp/lf20_dgjK9i.json", 2),
	Overcast(R.string.overcast, "https://assets7.lottiefiles.com/temp/lf20_VAmWRg.json", 3),
	Rainy(R.string.rainy, "https://assets6.lottiefiles.com/temp/lf20_rpC1Rd.json", 4),
	Snow(R.string.snow, "https://assets7.lottiefiles.com/temp/lf20_WtPCZs.json", 5),
	Foggy(R.string.foggy, "https://assets7.lottiefiles.com/temp/lf20_kOfPKE.json", 6),
	Flurries(R.string.flurries, "https://assets9.lottiefiles.com/packages/lf20_J8EPQ1.json", 7),
	Thunderstorm(R.string.thunderstorm, "https://assets7.lottiefiles.com/temp/lf20_Kuot2e.json", 8),
	ThunderstormWithRain(R.string.thunderstormWithRain, "https://assets7.lottiefiles.com/temp/lf20_XkF78Y.json", 9),
	UnknownPrecipitation(R.string.unknownPrecipitation, "https://assets10.lottiefiles.com/temp/lf20_9gY9Yf.json", 10),
	Unknown(R.string.unknown, "https://assets10.lottiefiles.com/temp/lf20_9gY9Yf.json", 11);

	private final int textResourceIdentifier;
	private final String iconAddress;
	// Integer value for calculating the average of different weather conditions.
	private final int value;

	WeatherCondition(int textResourceIdentifier, String iconAddress, int value) {
		this.textResourceIdentifier = textResourceIdentifier;
		this.iconAddress = iconAddress;
		this.value = value;
	}

	public int getTextResourceIdentifier() {
		return textResourceIdentifier;
	}

	public String getIconAddress() {
		return iconAddress;
	}

	public int getValue() {
		return value;
	}
}

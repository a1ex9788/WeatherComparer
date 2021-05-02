package a1ex9788.dadm.weathercomparer.utils;

public class UnitsGetter {

	public static String getSpeedUnits(String valueUnit) {

		switch (valueUnit) {
			case "0":
				return "km/h";
			case "1":
				return "mile/h";
			case "2":
				return "m/s";
			default:
				return "km/h";
		}
	}

	public static String getTemperatureUnits(String valueUnit) {

		switch (valueUnit) {
			case "0":
				return "°C";
			case "1":
				return "°F";
			case "2":
				return "K";
			default:
				return "°C";
		}
	}

}

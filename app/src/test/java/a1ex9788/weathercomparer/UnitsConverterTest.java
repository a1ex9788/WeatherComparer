package a1ex9788.weathercomparer;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import a1ex9788.dadm.weathercomparer.model.UvIndex;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.UnitsConverter;

import static org.junit.Assert.assertEquals;

public class UnitsConverterTest {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void unixUtcToDate() throws ParseException {
        Map<Integer, Date> parameters = new HashMap();
        parameters.put(1618589545, simpleDateFormat.parse("2021-04-16 18:12:25"));
        parameters.put(1018895456, simpleDateFormat.parse("2002-04-15 20:30:56"));
        parameters.put(2018855456, simpleDateFormat.parse("2033-12-22 10:10:56"));

        for (Map.Entry<Integer, Date> pair : parameters.entrySet()) {
            Assert.assertEquals(pair.getValue(), UnitsConverter.unixUtcToDate(pair.getKey()));
        }
    }

    @Test
    public void dateToUnixUTC() throws ParseException {
        Map<Date, Integer> parameters = new HashMap();
        parameters.put(simpleDateFormat.parse("2021-04-16 18:12:25"), 1618589545);
        parameters.put(simpleDateFormat.parse("2002-04-15 20:30:56"), 1018895456);
        parameters.put(simpleDateFormat.parse("2033-12-22 10:10:56"), 2018855456);

        for (Map.Entry<Date, Integer> pair : parameters.entrySet()) {
            assertEquals((long) pair.getValue(), UnitsConverter.dateToUnixUTC(pair.getKey()));
        }
    }

    @Test
    public void celsiusToFahrenheit() {
        Map<Double, Double> parameters = new HashMap();
        parameters.put(-8.7, 16.34);
        parameters.put(15.5, 59.9);
        parameters.put(38.9, 102.02);

        for (Map.Entry<Double, Double> pair : parameters.entrySet()) {
            assertEquals(pair.getValue(), UnitsConverter.roundToTwoDecimals(UnitsConverter.celsiusToFahrenheit(pair.getKey())), 0);
        }
    }

    @Test
    public void celsiusToKelvin() {
        Map<Double, Double> parameters = new HashMap();
        parameters.put(-8.7, 264.45);
        parameters.put(15.5, 288.65);
        parameters.put(38.9, 312.05);

        for (Map.Entry<Double, Double> pair : parameters.entrySet()) {
            assertEquals(pair.getValue(), UnitsConverter.roundToTwoDecimals(UnitsConverter.celsiusToKelvin(pair.getKey())), 0);
        }
    }

    @Test
    public void kilometersPerHourToMilesPerHour() {
        Map<Double, Double> parameters = new HashMap();
        parameters.put(-8.7, -5.41);
        parameters.put(15.5, 9.63);
        parameters.put(38.9, 24.17);

        for (Map.Entry<Double, Double> pair : parameters.entrySet()) {
            assertEquals(pair.getValue(), UnitsConverter.roundToTwoDecimals(UnitsConverter.kilometersPerHourToMilesPerHour(pair.getKey())), 0);
        }
    }

    @Test
    public void kilometersPerHourToMetersPerSecond() {
        Map<Double, Double> parameters = new HashMap();
        parameters.put(-8.71, -2.42);
        parameters.put(15.52, 4.31);
        parameters.put(38.92, 10.81);

        for (Map.Entry<Double, Double> pair : parameters.entrySet()) {
            assertEquals(pair.getValue(), UnitsConverter.roundToTwoDecimals(UnitsConverter.kilometersPerHourToMetersPerSecond(pair.getKey())), 0);
        }
    }

    @Test
    public void metersPerSecondToKilometersPerHour() {
        Map<Double, Double> parameters = new HashMap();
        parameters.put(-2.42, -8.71);
        parameters.put(4.31, 15.52);
        parameters.put(10.81, 38.92);

        for (Map.Entry<Double, Double> pair : parameters.entrySet()) {
            assertEquals(pair.getValue(), UnitsConverter.roundToTwoDecimals(UnitsConverter.metersPerSecondToKilometersPerHour(pair.getKey())), 0);
        }
    }

    @Test
    public void uvIndexValueToUvIndex() {
        Map<Double, UvIndex> parameters = new HashMap();
        parameters.put(1.1, UvIndex.Low);
        parameters.put(2.99, UvIndex.Low);
        parameters.put(3.0, UvIndex.Moderate);
        parameters.put(5.99, UvIndex.Moderate);
        parameters.put(6.111, UvIndex.High);
        parameters.put(7.99, UvIndex.High);
        parameters.put(8.552, UvIndex.VeryHigh);
        parameters.put(10.99, UvIndex.VeryHigh);

        for (Map.Entry<Double, UvIndex> pair : parameters.entrySet()) {
            assertEquals(pair.getValue(), UnitsConverter.uvIndexValueToUvIndex(pair.getKey()));
        }
    }

    @Test
    public void roundToOneDecimal() {
        Map<Double, Double> parameters = new HashMap();
        parameters.put(-8.78679, -8.8);
        parameters.put(15.5, 15.5);
        parameters.put(38.911, 38.9);

        for (Map.Entry<Double, Double> pair : parameters.entrySet()) {
            assertEquals(pair.getValue(), UnitsConverter.roundToOneDecimal(pair.getKey()), 0);
        }
    }

    @Test
    public void roundToTwoDecimals() {
        Map<Double, Double> parameters = new HashMap();
        parameters.put(-8.78679, -8.79);
        parameters.put(15.5, 15.5);
        parameters.put(38.9111, 38.91);

        for (Map.Entry<Double, Double> pair : parameters.entrySet()) {
            assertEquals(pair.getValue(), UnitsConverter.roundToTwoDecimals(pair.getKey()), 0);
        }
    }

}

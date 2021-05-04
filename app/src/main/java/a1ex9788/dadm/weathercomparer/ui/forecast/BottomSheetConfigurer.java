package a1ex9788.dadm.weathercomparer.ui.forecast;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import a1ex9788.dadm.weathercomparer.R;
import a1ex9788.dadm.weathercomparer.adapters.CustomRecyclerAdapterHourDayForecast;
import a1ex9788.dadm.weathercomparer.adapters.CustomRecyclerAdapterMoreInfo;
import a1ex9788.dadm.weathercomparer.adapters.HourDayForecast;
import a1ex9788.dadm.weathercomparer.adapters.MoreInfo;
import a1ex9788.dadm.weathercomparer.model.DayForecast;
import a1ex9788.dadm.weathercomparer.model.HourForecast;
import a1ex9788.dadm.weathercomparer.model.MapPlace;
import a1ex9788.dadm.weathercomparer.utils.UnitsGetter;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class BottomSheetConfigurer {

	private ForecastViewModel forecastViewModel;
	private Context context;
	private String metric;

	private boolean chartConfigured = false;

	public BottomSheetConfigurer(ForecastViewModel forecastViewModel, Context context, String metric) {
		this.forecastViewModel = forecastViewModel;
		this.context = context;
		this.metric = metric;
	}

	private static List<AxisValue> getAxisXLables(List<HourForecast> hourForecastList) {
		List<AxisValue> axisXValues = new ArrayList<AxisValue>();
		for (int i = 0; i < hourForecastList.size(); i++) {
			axisXValues.add(new AxisValue(i).setLabel(hourForecastList.get(i).getDate().
					toString().substring(11, 16)));
		}
		return axisXValues;
	}

	private static List<PointValue> getAxisPoints(List<HourForecast> hourForecastList) {
		List<PointValue> pointValues = new ArrayList<PointValue>();
		for (int i = 0; i < 12; i++) {
			double temp = hourForecastList.get(i).getAvgTemperature_celsius();
			pointValues.add(new PointValue(i, (float) temp));
		}
		return pointValues;
	}

	private static void initLineChart(LineChartView chartView, List<AxisValue> axisXValues, List<PointValue> pointValuesAverage, List<PointValue> pointValuesOpenWeather, List<PointValue> pointValuesAccuWeather
			/*, List<PointValue> pointValuesWeatherBit*/) {
		Line lineAverage = new Line(pointValuesAverage).setColor(Color.parseColor("#1B1B1B"));
		Line lineOpenWeather = new Line(pointValuesOpenWeather).setColor(Color.parseColor("#E59866"));
		Line lineAccuWeather = new Line(pointValuesAccuWeather).setColor(Color.parseColor("#CD6155"));
		/* The function to obtain the HourForecast of WeatherBit is now premium, so we can not use it */
		//Line lineWeatherBit = new Line(pointValuesWeatherBit).setColor(Color.parseColor("#82E0AA"));
		List<Line> lines = new ArrayList<>();
		lineAverage.setShape(ValueShape.CIRCLE);//The shape of each data point on a broken line chart is circular here (there are three kinds: ValueShape. SQUARE ValueShape. CIRCLE ValueShape. DIAMOND)
		lineAverage.setCubic(false);//Whether the curve is smooth, that is, whether it is a curve or a broken line
		lineAverage.setFilled(true);//Whether or not to fill the area of the curve
		lineAverage.setHasLabels(true);//Whether to add notes to the data coordinates of curves
		lineAverage.setHasLines(true);//Whether to display with line or not. If it is false, there is no curve but point display
		lineAverage.setHasPoints(false);//Whether to display a dot if it is false, there is no origin but only a dot (each data point is a large dot)
		lineOpenWeather.setShape(ValueShape.CIRCLE);
		lineOpenWeather.setCubic(false);
		lineOpenWeather.setFilled(false);
		lineOpenWeather.setHasLabels(true);
		lineOpenWeather.setHasLines(true);
		lineOpenWeather.setHasPoints(false);
		lineAccuWeather.setShape(ValueShape.CIRCLE);
		lineAccuWeather.setCubic(false);
		lineAccuWeather.setFilled(false);
		lineAccuWeather.setHasLabels(true);
		lineAccuWeather.setHasLines(true);
		lineAccuWeather.setHasPoints(false);
		/* The function to obtain the HourForecast of WeatherBit is now premium, so we can not use it */
        /*lineWeatherBit.setShape(ValueShape.CIRCLE);
        lineWeatherBit.setCubic(false);
        lineWeatherBit.setFilled(false);
        lineWeatherBit.setHasLabels(true);
        lineWeatherBit.setHasLines(true);
        lineWeatherBit.setHasPoints(false);*/

		lines.add(lineAverage);
		lines.add(lineOpenWeather);
		lines.add(lineAccuWeather);
		/* lines.add(lineWeatherBit); */
		LineChartData data = new LineChartData();
		data.setLines(lines);

		//Axis of coordinates
		Axis axisX = new Axis(); //X axis
		axisX.setHasTiltedLabels(true);  //Is the font on the X axis oblique or straight, and true oblique?
		axisX.setTextColor(Color.BLACK);  //Setting font color
		axisX.setName("hour"); // table name
		axisX.setTextSize(10);//Set font size
		axisX.setMaxLabelChars(8); //Up to a few X-axis coordinates, which means that your scaling allows the number of data on the X-axis to be 7<=x<=mAxisXValues.length.
		axisX.setValues(axisXValues);  //Fill in the coordinate name of the X-axis
		data.setAxisXBottom(axisX); //The x-axis is at the bottom.
		axisX.setHasLines(true); //x-axis dividing line

		// The Y-axis automatically sets the Y-axis limit according to the size of the data (below I will give a solution for fixing the number of Y-axis data)
		Axis axisY = new Axis();  //Y axis
		axisY.setName("temperature");//y axis annotation
		axisY.setTextColor(Color.BLACK);
		axisY.setTextSize(10);//Set font size
		data.setAxisYLeft(axisY);  //The Y-axis is set on the left

		//Setting behavioral properties to support zooming, sliding, and Translation
		chartView.setInteractive(true);
		chartView.setZoomType(ZoomType.HORIZONTAL);
		chartView.setMaxZoom((float) 2);//Maximum method ratio
		chartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
		chartView.setLineChartData(data);

		chartView.setVisibility(View.VISIBLE);

		Viewport v = new Viewport(chartView.getMaximumViewport());
		v.left = 0;
		v.right = 7;
		chartView.setCurrentViewport(v);
	}

	public void configureBottomSheet(Activity activity, MapPlace currentPlace,
								ForecastFragment.WeatherProvider weatherProvider, double latitude, double longitude, boolean firstTime) {
		try {
			if (activity == null) {
				return;
			}

			ConstraintLayout clBottomSheetFake = activity.findViewById(R.id.clBottomSheet);
			while (clBottomSheetFake == null) {
				clBottomSheetFake = activity.findViewById(R.id.clBottomSheet);
			}

			ConstraintLayout clBottomSheet = clBottomSheetFake;

			BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(clBottomSheet);
			DisplayMetrics metrics = new DisplayMetrics();

			activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
			int height = metrics.heightPixels;

			TextView tvToday = activity.findViewById(R.id.tvToday);

			RecyclerView rvMoreInfo = activity.findViewById(R.id.rvMoreInfo);
			RecyclerView rvHourDayPrediction = activity.findViewById(R.id.rvHourDayPrediction);
			RecyclerView.LayoutManager managerMoreInfo = new LinearLayoutManager(context,
					RecyclerView.HORIZONTAL,
					false);
			RecyclerView.LayoutManager managerHourDay = new LinearLayoutManager(context,
					RecyclerView.HORIZONTAL,
					false);
			SnapHelper snapHelper = new LinearSnapHelper();

			List<HourForecast> hourForecastsList, hourForecastsAccuWeather, hourForecastsOpenWeather
					/*, hourForecastsWeatherBit*/;
			List<DayForecast> dayForecastsList;
			try {
				if (weatherProvider == ForecastFragment.WeatherProvider.Average) {
					hourForecastsList = forecastViewModel.getAverageHourlyForecast(latitude, longitude);
				} else if (weatherProvider == ForecastFragment.WeatherProvider.AccuWeather) {
					hourForecastsList = forecastViewModel.getAccuWeatherHourlyForecast(latitude, longitude);
				} else if (weatherProvider == ForecastFragment.WeatherProvider.OpenWeather) {
					hourForecastsList = forecastViewModel.getOpenWeatherHourlyForecast(latitude, longitude);
				} else {
					hourForecastsList = forecastViewModel.getWeatherBitHourlyForecast(latitude, longitude);
				}
			} catch (Exception e) {
				hourForecastsList = new ArrayList<>();
			}
			try {
				if (weatherProvider == ForecastFragment.WeatherProvider.Average) {
					dayForecastsList = forecastViewModel.getAverageDailyForecast(latitude, longitude);
				} else if (weatherProvider == ForecastFragment.WeatherProvider.AccuWeather) {
					dayForecastsList = forecastViewModel.getAccuWeatherDailyForecast(latitude, longitude);
				} else if (weatherProvider == ForecastFragment.WeatherProvider.OpenWeather) {
					dayForecastsList = forecastViewModel.getOpenWeatherDailyForecast(latitude, longitude);
				} else {
					dayForecastsList = forecastViewModel.getWeatherBitDailyForecast(latitude, longitude);
				}
			} catch (Exception e) {
				dayForecastsList = new ArrayList<>();
			}
			try {
				hourForecastsAccuWeather = forecastViewModel.getAccuWeatherHourlyForecast(latitude, longitude);
			} catch (Exception e) {
				hourForecastsAccuWeather = new ArrayList<>();
			}
			try {
				hourForecastsOpenWeather = forecastViewModel.getOpenWeatherHourlyForecast(latitude, longitude);
			} catch (Exception e) {
				hourForecastsOpenWeather = new ArrayList<>();
			}
                    /* La función se ha convertido de pago, por lo que ya no funciona
                    List<HourForecast> hourForecastsWeatherBit = forecastViewModel
                    .getWeatherBitHourlyForecast(latitude, longitude);
                    */
			List<HourForecast> finalHourForecastsList = hourForecastsList;
			List<DayForecast> finalDayForecastsList = dayForecastsList;
			List<HourForecast> finalHourForecastsAccuWeather = hourForecastsAccuWeather;
			List<HourForecast> finalHourForecastsListOpenWeather = hourForecastsOpenWeather;

			if (activity == null) {
				return;
			}

			while (currentPlace == null) {
			}

			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					bottomSheetBehavior.setPeekHeight(484);
					clBottomSheet.setMaxHeight(1784);
					clBottomSheet.getLayoutParams().height = (3 * height) / 4;
					if (firstTime) {
						snapHelper.attachToRecyclerView(rvMoreInfo);
					}
					rvMoreInfo.setLayoutManager(managerMoreInfo);
					rvHourDayPrediction.setLayoutManager(managerHourDay);

					List<HourDayForecast> hoursList = new ArrayList<>();
					List<MoreInfo> list = new ArrayList<>();
					if (finalHourForecastsList.size() > 0) {
						HourDayForecast hourForecast1 = new HourDayForecast(
								finalHourForecastsList.get(2).getDate().toString().substring(11, 16),
								finalHourForecastsList.get(2).getAvgTemperature(metric).toString().
										substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
								finalHourForecastsList.get(2).getWeatherCondition().getIconAddress());
						HourDayForecast hourForecast2 = new HourDayForecast(
								finalHourForecastsList.get(3).getDate().toString().substring(11, 16),
								finalHourForecastsList.get(3).getAvgTemperature(metric).toString().
										substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
								finalHourForecastsList.get(3).getWeatherCondition().getIconAddress());
						HourDayForecast hourForecast3 = new HourDayForecast(
								finalHourForecastsList.get(4).getDate().toString().substring(11, 16),
								finalHourForecastsList.get(4).getAvgTemperature(metric).toString().
										substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
								finalHourForecastsList.get(4).getWeatherCondition().getIconAddress());
						HourDayForecast hourForecast4 = new HourDayForecast(
								finalHourForecastsList.get(5).getDate().toString().substring(11, 16),
								finalHourForecastsList.get(5).getAvgTemperature(metric).toString().
										substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
								finalHourForecastsList.get(5).getWeatherCondition().getIconAddress());
						HourDayForecast hourForecast5 = new HourDayForecast(
								finalHourForecastsList.get(6).getDate().toString().substring(11, 16),
								finalHourForecastsList.get(6).getAvgTemperature(metric).toString().
										substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
								finalHourForecastsList.get(6).getWeatherCondition().getIconAddress());
						HourDayForecast hourForecast6 = new HourDayForecast(
								finalHourForecastsList.get(7).getDate().toString().substring(11, 16),
								finalHourForecastsList.get(7).getAvgTemperature(metric).toString().
										substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
								finalHourForecastsList.get(7).getWeatherCondition().getIconAddress());
						hoursList.add(hourForecast1);
						hoursList.add(hourForecast2);
						hoursList.add(hourForecast3);
						hoursList.add(hourForecast4);
						hoursList.add(hourForecast5);
						hoursList.add(hourForecast6);

						CustomRecyclerAdapterHourDayForecast adapterHourDay = new CustomRecyclerAdapterHourDayForecast(
								hoursList);
						rvHourDayPrediction.setAdapter(adapterHourDay);
						MoreInfo moreInfoFeelsLike = new MoreInfo(context.getString(R.string.feels_like),
								finalHourForecastsList.get(1).getRealFeel(metric).toString().
										substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
								R.drawable.temperature);
						MoreInfo moreInfoPressure = null;
						if (finalDayForecastsList.get(1).getPressure_millibars() != null) {
							moreInfoPressure = new MoreInfo(context.getString(R.string.pressure),
									finalHourForecastsList.get(1).getPressure_millibars().toString().
											substring(0, 4) + context.getString(R.string.milibar_pressureUnit),
									R.drawable.ic_pressure);
						}
						int uvIndex = finalHourForecastsList.get(1).getUvIndex().intValue();
						String uvIndexText = "";
						if (uvIndex < 3) {
							uvIndexText = finalHourForecastsList.get(1)
									.getUvIndex()
									.toString()
									.substring(0, 1) + ", " + context.getString(R.string.low);
						} else if (uvIndex < 6) {
							uvIndexText = finalHourForecastsList.get(1)
									.getUvIndex()
									.toString()
									.substring(0, 1) + ", " + context.getString(R.string.mid);
						} else if (uvIndex < 8) {
							uvIndexText = finalHourForecastsList.get(1)
									.getUvIndex()
									.toString()
									.substring(0, 1) + ", " + context.getString(R.string.high);
						} else if (uvIndex < 11) {
							uvIndexText = finalHourForecastsList.get(1)
									.getUvIndex()
									.toString()
									.substring(0, 1) + ", " + context.getString(R.string.veryHigh);
						} else {
							uvIndexText = finalHourForecastsList.get(1)
									.getUvIndex()
									.toString()
									.substring(0, 1) + ", " + context.getString(R.string.extreme);
						}
						MoreInfo moreInfoUVIndex = new MoreInfo(context.getString(R.string.UVIndex),
								uvIndexText,
								R.drawable.ic_uv_index);

						ZoneId zoneId = ZoneId.of(currentPlace.getTimeZoneId());
						Date sunrise = finalDayForecastsList.get(0).getSunrise();
						Date sunset = finalDayForecastsList.get(0).getSunset();
						LocalDateTime localDateTimeSunrise = LocalDateTime.ofInstant(sunrise.toInstant(), zoneId);
						LocalDateTime localDateTimeSunset = LocalDateTime.ofInstant(sunset.toInstant(), zoneId);
						MoreInfo moreInfoSunrise = new MoreInfo(context.getString(R.string.sunrise),
								localDateTimeSunrise.toString().substring(11, 16),
								R.drawable.ic_sunrise);
						MoreInfo moreInfoSunset = new MoreInfo(context.getString(R.string.sunset),
								localDateTimeSunset.toString().substring(11, 16),
								R.drawable.ic_sunset);
						list.add(moreInfoFeelsLike);
						if (moreInfoPressure != null) {
							list.add(moreInfoPressure);
						}
						list.add(moreInfoUVIndex);
						list.add(moreInfoSunrise);
						list.add(moreInfoSunset);
					}
					CustomRecyclerAdapterMoreInfo adapter = new CustomRecyclerAdapterMoreInfo(list);
					rvMoreInfo.setAdapter(adapter);
				}
			});

			if (activity == null) {
				return;
			}

			LineChartView chartView = activity.findViewById(R.id.chart);
			bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
				@Override
				public void onStateChanged(@NonNull View bottomSheet, int newState) {
					if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
						chartView.setVisibility(View.INVISIBLE);
						tvToday.setText(R.string.tvToday);
						List<HourDayForecast> hoursList = new ArrayList<>();
						if (finalHourForecastsList.size() > 0) {
							HourDayForecast hourForecast1 = new HourDayForecast(
									finalHourForecastsList.get(2).getDate().toString().substring(11, 16),
									finalHourForecastsList.get(2).getAvgTemperature(metric).toString().
											substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
									finalHourForecastsList.get(2).getWeatherCondition().getIconAddress());
							HourDayForecast hourForecast2 = new HourDayForecast(
									finalHourForecastsList.get(3).getDate().toString().substring(11, 16),
									finalHourForecastsList.get(3).getAvgTemperature(metric).toString().
											substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
									finalHourForecastsList.get(3).getWeatherCondition().getIconAddress());
							HourDayForecast hourForecast3 = new HourDayForecast(
									finalHourForecastsList.get(4).getDate().toString().substring(11, 16),
									finalHourForecastsList.get(4).getAvgTemperature(metric).toString().
											substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
									finalHourForecastsList.get(4).getWeatherCondition().getIconAddress());
							HourDayForecast hourForecast4 = new HourDayForecast(
									finalHourForecastsList.get(5).getDate().toString().substring(11, 16),
									finalHourForecastsList.get(5).getAvgTemperature(metric).toString().
											substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
									finalHourForecastsList.get(5).getWeatherCondition().getIconAddress());
							HourDayForecast hourForecast5 = new HourDayForecast(
									finalHourForecastsList.get(6).getDate().toString().substring(11, 16),
									finalHourForecastsList.get(6).getAvgTemperature(metric).toString().
											substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
									finalHourForecastsList.get(6).getWeatherCondition().getIconAddress());
							HourDayForecast hourForecast6 = new HourDayForecast(
									finalHourForecastsList.get(7).getDate().toString().substring(11, 16),
									finalHourForecastsList.get(7).getAvgTemperature(metric).toString().
											substring(0, 4) + UnitsGetter.getTemperatureUnits(metric),
									finalHourForecastsList.get(7).getWeatherCondition().getIconAddress());
							hoursList.add(hourForecast1);
							hoursList.add(hourForecast2);
							hoursList.add(hourForecast3);
							hoursList.add(hourForecast4);
							hoursList.add(hourForecast5);
							hoursList.add(hourForecast6);
						}
						CustomRecyclerAdapterHourDayForecast adapterHourDay = new CustomRecyclerAdapterHourDayForecast(
								hoursList);
						rvHourDayPrediction.setAdapter(adapterHourDay);
					} else if (BottomSheetBehavior.STATE_EXPANDED == newState) {
						tvToday.setText(R.string.tvToday_daily);
						List<HourDayForecast> daysList = new ArrayList<>();
						HourDayForecast dayForecast1 = new HourDayForecast(finalDayForecastsList.get(0)
								.getDate()
								.toString()
								.substring(0, 4),
								"",
								finalDayForecastsList.get(0).getWeatherCondition().getIconAddress());
						HourDayForecast dayForecast2 = new HourDayForecast(finalDayForecastsList.get(1)
								.getDate()
								.toString()
								.substring(0, 4),
								"",
								finalDayForecastsList.get(1).getWeatherCondition().getIconAddress());
						HourDayForecast dayForecast3 = new HourDayForecast(finalDayForecastsList.get(2)
								.getDate()
								.toString()
								.substring(0, 4),
								"",
								finalDayForecastsList.get(2).getWeatherCondition().getIconAddress());
						HourDayForecast dayForecast4 = new HourDayForecast(finalDayForecastsList.get(3)
								.getDate()
								.toString()
								.substring(0, 4),
								"",
								finalDayForecastsList.get(3).getWeatherCondition().getIconAddress());
						daysList.add(dayForecast1);
						daysList.add(dayForecast2);
						daysList.add(dayForecast3);
						daysList.add(dayForecast4);
						CustomRecyclerAdapterHourDayForecast adapterHourDay = new CustomRecyclerAdapterHourDayForecast(
								daysList);
						rvHourDayPrediction.setAdapter(adapterHourDay);
					} else if (BottomSheetBehavior.STATE_DRAGGING == newState) {
						chartView.setVisibility(View.VISIBLE);
						if (!chartConfigured) {
							List<AxisValue> axisXValues = null;
							List<PointValue> pointValuesAverage = null;
							List<PointValue> pointValuesOpenWeather = null;
							List<PointValue> pointValuesAccuWeather = null;
                                            /* The function to obtain the HourForecast of
                                            WeatherBit is now premium, so we can not use it */
							/* List<PointValue> pointValuesWeatherBit = null; */
							if (finalDayForecastsList.size() != 0) {
								axisXValues = getAxisXLables(finalHourForecastsList);
								pointValuesAverage = getAxisPoints(finalHourForecastsList);
							}
							if (finalHourForecastsListOpenWeather.size() != 0) {
								pointValuesOpenWeather = getAxisPoints(finalHourForecastsListOpenWeather);
							}
							if (finalHourForecastsAccuWeather.size() != 0) {
								pointValuesAccuWeather = getAxisPoints(finalHourForecastsAccuWeather);
							}
                                            /* The function to obtain the HourForecast of
                                            WeatherBit is now premium, so we can not use it */
                                    /* if(finalHourForecastsWeatherBit.size() != 0) {
                                        pointValuesWeatherBit = getAxisPoints
                                        (hourForecastsWeatherBit);
                                    } */
							initLineChart(chartView,
									axisXValues,
									pointValuesAverage,
									pointValuesOpenWeather,
									pointValuesAccuWeather/*,
                                                    pointValuesWeatherBit*/);
							chartConfigured = true;
						}
					}
				}

				@Override
				public void onSlide(@NonNull View bottomSheet, float slideOffset) {

				}
			});
		} catch (Exception e) {
			Log.e("", e.toString());
		}
	}

}

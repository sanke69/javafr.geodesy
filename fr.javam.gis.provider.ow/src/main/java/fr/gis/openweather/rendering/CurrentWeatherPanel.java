package fr.gis.openweather.rendering;

import javafx.scene.layout.StackPane;

public class CurrentWeatherPanel extends StackPane {
/*
	static final HashMap<String, String> msgMap = new HashMap<String, String>() {
		{
			put("forecast_button_title", "5 days forecast (3 hours)");
			put("actual_temp", "Actual temp: %.1f\u00b0C min: %.1f\u00b0C max: %.1f\u00b0C");
			put("sea_level_pressure", "Sea level pressure: %d hPa");
			put("humidity", "Humidity: %d");
			put("graph_temperature", "Temperature");
			put("graph_temperature_minimum", "Temperature minimum");
			put("graph_temperature_maximum", "Temperature maximum");
			put("graph_humidity", "Humidity");
			put("graph_pressure", "Pressure");
			put("graph_precipitation", "Precipitation");
			put("graph_rain", "Rain");
			put("graph_snow", "Snow");
			put("graph_wind_speed", "Wind speed");
			put("graph_snow", "Snow");
			put("graph_wind_speed", "Wind speed");
			put("title_axis_temperature_minimum", "Temperature minimum");
			put("title_axis_temperature_maximum", "Temperature maximum");
			put("title_axis_rain", "Rain");
			put("title_axis_snow", "Snow");
			put("axis_date", "Date");
			put("axis_title_temperature", "Temperature [\u00b0C]");
			put("axis_title_humidity", "Humidity [%]");
			put("axis_title_pressure", "Pressure [hPa]");
			put("axis_title_precipitation", "Rain");
			put("title_axis_rain", "Precipitation [mm]");
			put("axis_title_wind_speed", "Wind speed [m/s]");
			put("weather_forecast_window_title", "Weather forecast window");
			put("close_button_text", "Close");
		}
	};

	private CurrentWeatherData				weatherData;
	private OWIconRepository				iconRepository;
	private Label							cityLabel;
	private Label							iconLabel;
	private Label							tempLabel;
	private Label							pressureLabel;
	private Label							humidityLabel;
	private Label							windLabel;
	private TextArea						descriptionTextArea;
	private Button							forecast5Button;
	private WeatherFactory					weatherfactory;
	private static final SimpleDateFormat	AXIS_DATE_FORMAT	= new SimpleDateFormat("EEE. d kk:00");

	public CurrentWeatherPanel() {
		super();
		initComponents();
		iconRepository = new OWIconRepository();
		weatherfactory = new WeatherFactory();
	}

	private void initComponents() {
		cityLabel = new Label();
		iconLabel = new Label();
		tempLabel = new Label();
		pressureLabel = new Label();
		humidityLabel = new Label();
		windLabel = new Label();
		descriptionTextArea = new TextArea();
		forecast5Button = new Button(msgMap.get("forecast_button_title"));

		getChildren().add(cityLabel);
		getChildren().add(iconLabel);
		getChildren().add(tempLabel);
		getChildren().add(pressureLabel);
		getChildren().add(humidityLabel);
		getChildren().add(windLabel);
		getChildren().add(descriptionTextArea);
		getChildren().add(forecast5Button);

		forecast5Button.addActionListener((e) -> {
			showDetailForecast(getCityId());
		});
	}

	private long getCityId() {
		return weatherData.getCityId();
	}

	private Icon getIconForState(String state) {
		return iconRepository.loadIcon(state);
	}

	public void setWeatherData(CurrentWeatherData weatherData) {
		this.weatherData = weatherData;

		cityLabel.setText(weatherData.getCityName());
		iconLabel.setIcon(getIconForState(weatherData.getWeatherIcon()));
		String text = String.format(msgMap.get("actual_temp"), weatherData.getTemp(), weatherData.getTemp_min(), weatherData.getTemp_max());
		tempLabel.setText(text);
		text = String.format(msgMap.get("sea_level_pressure"), weatherData.getPressure());
		pressureLabel.setText(text);
		text = String.format(msgMap.get("humidity"), weatherData.getHumidity());
		humidityLabel.setText(text);
		descriptionTextArea.setText(weatherData.getWeatherDescription());
	}

	public List<ForecastWeatherData> loadForecastData(long cityId) {
		return weatherfactory.getDetailForecast(cityId);
	}

	private void showDetailForecast(long cityId) {
		List<ForecastWeatherData> data = loadForecastData(cityId);

		if(data == null) {
			return;
		}

		XYSeries seriesTemp = new XYSeries(msgMap.get("graph_temperature"));
		XYSeries seriesTempMax = new XYSeries(msgMap.get("graph_temperature_maximum"));
		XYSeries seriesTempMin = new XYSeries(msgMap.get("graph_temperature_minimum"));
		XYSeries seriesHumidity = new XYSeries(msgMap.get("graph_humidity"));
		XYSeries seriesRain = new XYSeries(msgMap.get("graph_rain"));
		XYSeries seriesSnow = new XYSeries(msgMap.get("graph_snow"));
		XYSeries seriesPressure = new XYSeries(msgMap.get("graph_pressure"));
		XYSeries seriesWind = new XYSeries(msgMap.get("graph_wind_speed"));

		for(ForecastWeatherData forecast : data) {
			seriesTemp.add(forecast.getDateTime() * 1000, forecast.getTempDay());
			seriesTempMax.add(forecast.getDateTime() * 1000, forecast.getTempMax());
			seriesTempMin.add(forecast.getDateTime() * 1000, forecast.getTempMin());
			seriesHumidity.add(forecast.getDateTime() * 1000, forecast.getHumidity());
			seriesRain.add(forecast.getDateTime() * 1000, forecast.getRain());
			seriesSnow.add(forecast.getDateTime() * 1000, forecast.getSnow());
			seriesPressure.add(forecast.getDateTime() * 1000, forecast.getPressure());
			seriesWind.add(forecast.getDateTime() * 1000, forecast.getWind());
		}

		XYSeriesCollection temperatureDataset = new XYSeriesCollection();
		temperatureDataset.addSeries(seriesTemp);
		temperatureDataset.addSeries(seriesTempMax);
		temperatureDataset.addSeries(seriesTempMin);

		Dimension graphSize = new java.awt.Dimension(500, 270);

		String dateText = NbBundle.getMessage(CurrentWeatherPanel.class, "axis_date");
		JFreeChart temperatureChart = createXYLineChart(msgMap.get("graph_temperature"), dateText,
				msgMap.get("axis_title_temperature"), temperatureDataset, true, true);
		ChartPanel temperatureChartPanel = new ChartPanel(temperatureChart);
		temperatureChartPanel.setPreferredSize(graphSize);

		XYSeriesCollection humidityDataset = new XYSeriesCollection();
		humidityDataset.addSeries(seriesHumidity);

		JFreeChart humidityChart = createXYLineChart(msgMap.get("graph_humidity"), dateText,
				msgMap.get("axis_title_humidity"), humidityDataset, false, false);
		ChartPanel humidityChartPanel = new ChartPanel(humidityChart);
		humidityChartPanel.setPreferredSize(graphSize);

		XYSeriesCollection precipitationDataset = new XYSeriesCollection();
		precipitationDataset.addSeries(seriesRain);
		precipitationDataset.addSeries(seriesSnow);

		JFreeChart precipitationChart = createBarChart(NbBundle.getMessage(CurrentWeatherPanel.class, "graph_precipitation"), dateText,
				msgMap.get("axis_title_precipitation"), precipitationDataset, true);
		ChartPanel precipitationChartPanel = new ChartPanel(precipitationChart);
		humidityChartPanel.setPreferredSize(graphSize);

		XYSeriesCollection pressureDataset = new XYSeriesCollection();
		pressureDataset.addSeries(seriesPressure);

		JFreeChart pressureChart = createXYLineChart(NbBundle.getMessage(CurrentWeatherPanel.class, "graph_pressure"), dateText,
				msgMap.get("axis_title_pressure"), pressureDataset, false, false);
		ChartPanel pressureChartPanel = new ChartPanel(pressureChart);
		humidityChartPanel.setPreferredSize(graphSize);

		XYSeriesCollection windDataset = new XYSeriesCollection();
		windDataset.addSeries(seriesWind);

		JFreeChart windChart = createXYLineChart(NbBundle.getMessage(CurrentWeatherPanel.class, "graph_wind_speed"), dateText,
				msgMap.get("axis_title_wind_speed"), windDataset, false, true);
		ChartPanel windChartPanel = new ChartPanel(windChart);
		humidityChartPanel.setPreferredSize(graphSize);

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab(msgMap.get("graph_temperature"), temperatureChartPanel);
		tabbedPane.addTab(msgMap.get("graph_humidity"), humidityChartPanel);
		tabbedPane.addTab(msgMap.get("graph_precipitation"), precipitationChartPanel);
		tabbedPane.addTab(msgMap.get("graph_pressure"), pressureChartPanel);
		tabbedPane.addTab(msgMap.get("graph_wind_speed"), windChartPanel);

		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(tabbedPane, BorderLayout.CENTER);
		/ *
		DialogDescriptor dd = new DialogDescriptor(p, NbBundle.getMessage(CurrentWeatherPanel.class, "weather_forecast_window_title"));
		dd.setOptions(new Object[]{NbBundle.getMessage(CurrentWeatherPanel.class, "close_button_text")});
		dd.setClosingOptions(new Object[]{NbBundle.getMessage(CurrentWeatherPanel.class, "close_button_text")});
		DialogDisplayer.getDefault().createDialog(dd).setVisible(true);
		* /
	}

	private JFreeChart createXYLineChart(String title, String xAxisLabel,
			String yAxisLabel, XYDataset dataset, boolean legend, boolean includeZero) {

		DateAxis xAxis = new DateAxis(xAxisLabel);
		xAxis.setDateFormatOverride(AXIS_DATE_FORMAT);
		NumberAxis yAxis = new NumberAxis(yAxisLabel);
		yAxis.setAutoRangeIncludesZero(includeZero);
		XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);
		XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
		plot.setOrientation(PlotOrientation.VERTICAL);
		return new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend);
	}

	private JFreeChart createBarChart(String title, String xAxisLabel,
			String yAxisLabel, XYDataset dataset, boolean legend) {

		DateAxis xAxis = new DateAxis(xAxisLabel);
		xAxis.setDateFormatOverride(AXIS_DATE_FORMAT);
		NumberAxis yAxis = new NumberAxis(yAxisLabel);

		XYBarRenderer renderer = new XYBarRenderer();
		renderer.setShadowVisible(false);
		XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
		plot.setOrientation(PlotOrientation.VERTICAL);
		return new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend);
	}
*/
}

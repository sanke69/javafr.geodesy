package fr.gis.sdk.objects.weather;

public class CurrentWeatherData {

	private String	cityName;
	private long	cityId;
	private double	longitude;
	private double	latitude;
	private long	timestamp;
	private float	temp;
	private float	temp_min;
	private float	temp_max;
	private int		pressure;
	private int		humidity;
	private float	wind_speed;
	private float	wind_dir;
	private long	weatherId;
	private String	weatherDescription;
	private String	weatherIcon;
	private long	time;

	public CurrentWeatherData() {
		time = System.currentTimeMillis();
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public float getTemp() {
		return temp;
	}

	public void setTemp(float temp) {
		this.temp = temp;
	}

	public float getTemp_min() {
		return temp_min;
	}

	public void setTemp_min(float temp_min) {
		this.temp_min = temp_min;
	}

	public float getTemp_max() {
		return temp_max;
	}

	public void setTemp_max(float temp_max) {
		this.temp_max = temp_max;
	}

	public int getPressure() {
		return pressure;
	}

	public void setPressure(int pressure) {
		this.pressure = pressure;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public float getWind_speed() {
		return wind_speed;
	}

	public void setWind_speed(float wind_speed) {
		this.wind_speed = wind_speed;
	}

	public float getWind_dir() {
		return wind_dir;
	}

	public void setWind_dir(float wind_dir) {
		this.wind_dir = wind_dir;
	}

	public long getWeatherId() {
		return weatherId;
	}

	public void setWeatherId(long weatherId) {
		this.weatherId = weatherId;
	}

	public String getWeatherDescription() {
		return weatherDescription;
	}

	public void setWeatherDescription(String weatherDescription) {
		this.weatherDescription = weatherDescription;
	}

	public String getWeatherIcon() {
		return weatherIcon;
	}

	public void setWeatherIcon(String weatherIcon) {
		this.weatherIcon = weatherIcon;
	}

	@Override
	public String toString() {
		String t = cityName + " actual temp: " + temp + ", min temp: " + temp_min + ", max temp: " + temp_max;
		return t;
	}

	public boolean isOutdated() {
		long timeDelay = System.currentTimeMillis() - time;
		return timeDelay >= CurrentWeatherDataObject.OUTDATED_TRESHOLD;
	}
}

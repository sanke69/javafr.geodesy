package fr.gis.openweather;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import fr.gis.openweather.parser.WeatherJSONParser;
import fr.gis.sdk.objects.weather.CurrentWeatherData;
import fr.gis.sdk.objects.weather.ForecastWeatherData;

public class WeatherFactory {

    public static final String API_CODE = "644a83e8df9d998ec8035c9930ddd911";
    
    private static final int NUMBER_OF_ATTEMPTS = 3;
    private static final String serviceUrlForLocation    = "http://api.openweathermap.org/data/2.5/weather?lat=%.10f&lon=%.10f&APPID=%s";
    private static final String serviceUrlForArea        = "http://api.openweathermap.org/data/2.5/box/city?bbox=%.10f,%.10f,%.10f,%.10f,%d&cluster=yes&APPID=%s";
    private static final String serviceUrlForId          = "http://api.openweathermap.org/data/2.5/weather?id=%d&APPID=%s";
    private static final String serviceUrlForGroupId     = "http://api.openweathermap.org/data/2.5/group?id=%s1&unitsq=metric&APPID=%s";
    private static final String serviceUrlForecastDetail = "http://api.openweathermap.org/data/2.5/forecast?id=%d&APPID=%s";
    
    private WeatherJSONParser jsonParser;

    public WeatherFactory() {
        jsonParser = new WeatherJSONParser();
    }

    public List<CurrentWeatherData> getCurrentWeatherData(double startLongitude, double startLatitude) {
        return readCitiesData(startLongitude, startLatitude);
    }

    public List<CurrentWeatherData> getCurrentWeatherDataForArea(double startLongitude, double startLatitude, double endLongitude, double endLatitude, int zoom) {
        return readCitiesData(startLongitude, startLatitude, endLongitude, endLatitude, 19 - zoom);
    }

    public List<CurrentWeatherData> readCitiesData(double startLongitude, double startLatitude) {
        String url = String.format(Locale.ENGLISH, serviceUrlForLocation, startLatitude, startLongitude, API_CODE);

        List<CurrentWeatherData> data = null;

        for (int i = 0; i < NUMBER_OF_ATTEMPTS; i++) {
            try {
                data = jsonParser.parseCurrentWeatherData(url, true);
                break;
            } catch (IOException ex) {
                //another attempt
            }
        }

        return data;
    }

    public List<CurrentWeatherData> readCitiesData(double startLongitude, double startLatitude, double endLongitude, double endLatitude, int zoom) {
        String url = String.format(Locale.ENGLISH, serviceUrlForArea, startLatitude, startLongitude, endLatitude, endLongitude, zoom, API_CODE);
        List<CurrentWeatherData> data = null;

        System.out.println(url);

        for (int i = 0; i < NUMBER_OF_ATTEMPTS; i++) {
            try {
                data = jsonParser.parseCurrentWeatherData(url);
                break;
            } catch (IOException ex) {
                //another try
            }
        }

        return data;
    }

    public CurrentWeatherData getCurrentWeatherData(long id) {
        String url = String.format(Locale.ENGLISH, serviceUrlForId, id);
        List<CurrentWeatherData> data = null;

        for (int i = 0; i < NUMBER_OF_ATTEMPTS; i++) {
            try {
                data = jsonParser.parseCurrentWeatherData(url, true);
                break;
            } catch (IOException ex) {
                //another try
            }
        }

        return data != null && !data.isEmpty() ? data.get(0) : null;
    }

    public List<CurrentWeatherData> getCurrentWeatherDataForCities(long... ids) {

        String text = "";
        for (long id : ids) {
            text += id + ",";
        }

        String url = String.format(Locale.ENGLISH, serviceUrlForGroupId, text);
        List<CurrentWeatherData> data = null;

        for (int i = 0; i < NUMBER_OF_ATTEMPTS; i++) {
            try {
                data = jsonParser.parseCurrentWeatherData(url, true);
                break;
            } catch (IOException ex) {
                //another try
            }
        }

        return data;
    }

    public List<ForecastWeatherData> getDetailForecast(long cityId) {
        String url = String.format(Locale.ENGLISH, serviceUrlForecastDetail, cityId);
        List<ForecastWeatherData> data = null;

        for (int i = 0; i < NUMBER_OF_ATTEMPTS; i++) {
            try {
                data = jsonParser.parseDetailForecast(url, true);
                break;
            } catch (IOException ex) {
                //another try
            }
        }

        return data;
    }
}

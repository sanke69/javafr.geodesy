package fr.gis.openweather.parser;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonStructure;

import fr.gis.openweather.WeatherFactory;
import fr.gis.sdk.objects.weather.CurrentWeatherData;
import fr.gis.sdk.objects.weather.ForecastWeatherData;

public class WeatherJSONParser {

    public List<CurrentWeatherData> parseCurrentWeatherData(String jsonUrl) throws IOException {
        return parseCurrentWeatherData(jsonUrl, false);
    }

    public List<ForecastWeatherData> parseForecast(String jsonUrl, boolean translateFromKelvin) throws IOException {
    	JsonObject json = readJsonFromUrl(jsonUrl);
        List<ForecastWeatherData> data = new ArrayList<>();
        if (json.containsKey("list")) {
            JsonArray dayArray = json.getJsonArray("list");
            for (int i = 0; i < dayArray.size(); i++) {
            	JsonObject day = dayArray.getJsonObject(i);
                ForecastWeatherData weatherData = getForecastData(day, translateFromKelvin);
                data.add(weatherData);
            }
        } else {
            ForecastWeatherData weatherData = getForecastData(json, translateFromKelvin);
            data.add(weatherData);
        }
        return data;
    }

    public List<ForecastWeatherData> parseDetailForecast(String jsonUrl, boolean translateFromKelvin) throws IOException {
    	JsonObject json = readJsonFromUrl(jsonUrl);
        List<ForecastWeatherData> data = new ArrayList<>();
        if (json.containsKey("list")) {
        	JsonArray dayArray = json.getJsonArray("list");
            for (int i = 0; i < dayArray.size(); i++) {
            	JsonObject day = dayArray.getJsonObject(i);
                ForecastWeatherData weatherData = getDetailForecastData(day, translateFromKelvin);
                data.add(weatherData);
            }
        } else {
            ForecastWeatherData weatherData = getDetailForecastData(json, translateFromKelvin);
            data.add(weatherData);
        }
        return data;
    }

    public List<CurrentWeatherData> parseCurrentWeatherData(String jsonUrl, boolean translateFromKelvin) throws IOException {
    	JsonObject json = readJsonFromUrl(jsonUrl);
        List<CurrentWeatherData> data = new ArrayList<>();
        if (json.containsKey("list")) {
        	JsonArray cityArray = json.getJsonArray("list");
            for (int i = 0; i < cityArray.size(); i++) {
            	JsonObject city = cityArray.getJsonObject(i);
                CurrentWeatherData weatherData = getWeatherData(city, translateFromKelvin);
                data.add(weatherData);
            }
        } else {
            CurrentWeatherData weatherData = getWeatherData(json, translateFromKelvin);
            data.add(weatherData);
        }
        return data;
    }

    private ForecastWeatherData getForecastData(JsonObject jsonObject, boolean translateFromKelvin) {
        ForecastWeatherData weatherData = new ForecastWeatherData();
        weatherData.setDateTime(jsonObject.getJsonNumber("dt").longValue());
        weatherData.setClouds(jsonObject.getInt("clouds"));
        weatherData.setHumidity(jsonObject.getJsonNumber("humidity").doubleValue());
        weatherData.setPressure(jsonObject.getJsonNumber("pressure").doubleValue());
        weatherData.setWind(jsonObject.getJsonNumber("speed").doubleValue());
        weatherData.setDescription(jsonObject.getJsonArray("weather").getJsonObject(0).getString("main"));

        JsonObject tempObject = jsonObject.getJsonObject("temp");
        weatherData.setTempMin(translateTemperature((float) tempObject.getJsonNumber("min").doubleValue(), translateFromKelvin));
        weatherData.setTempMax(translateTemperature((float) tempObject.getJsonNumber("max").doubleValue(), translateFromKelvin));
        weatherData.setTempNight(translateTemperature((float) tempObject.getJsonNumber("night").doubleValue(), translateFromKelvin));
        weatherData.setTempDay(translateTemperature((float) tempObject.getJsonNumber("day").doubleValue(), translateFromKelvin));
        weatherData.setTempEvening(translateTemperature((float) tempObject.getJsonNumber("eve").doubleValue(), translateFromKelvin));
        weatherData.setTempMorning(translateTemperature((float) tempObject.getJsonNumber("morn").doubleValue(), translateFromKelvin));
        if (jsonObject.containsKey("snow")) {
            weatherData.setSnow(jsonObject.getJsonNumber("snow").doubleValue());
        }
        if (jsonObject.containsKey("rain")) {
            weatherData.setRain(jsonObject.getJsonNumber("rain").doubleValue());
        }
        return weatherData;
    }

    private ForecastWeatherData getDetailForecastData(JsonObject jsonObject, boolean translateFromKelvin) {
        ForecastWeatherData weatherData = new ForecastWeatherData();
        weatherData.setDateTime(jsonObject.getJsonNumber("dt").longValue());
        weatherData.setWind(jsonObject.getJsonObject("wind").getJsonNumber("speed").longValue());
        JsonObject mainObject = jsonObject.getJsonObject("main");
        weatherData.setHumidity(mainObject.getJsonNumber("humidity").doubleValue());
        weatherData.setPressure(mainObject.getJsonNumber("pressure").doubleValue());
        weatherData.setTempDay(translateTemperature((float) mainObject.getJsonNumber("temp").doubleValue(), translateFromKelvin));
        weatherData.setTempMin(translateTemperature((float) mainObject.getJsonNumber("temp_min").doubleValue(), translateFromKelvin));
        weatherData.setTempMax(translateTemperature((float) mainObject.getJsonNumber("temp_max").doubleValue(), translateFromKelvin));
        weatherData.setIcon(jsonObject.getJsonArray("weather").getJsonObject(0).getString("icon"));

        if (jsonObject.containsKey("rain")) {
            weatherData.setRain(jsonObject.getJsonObject("rain").getJsonNumber("3h").doubleValue());
        }

        if (jsonObject.containsKey("snow")) {
            weatherData.setSnow(jsonObject.getJsonObject("snow").getJsonNumber("3h").doubleValue());
        }

        return weatherData;
    }

    private CurrentWeatherData getWeatherData(JsonObject jsonObject, boolean translateFromKelvin) {
        CurrentWeatherData weatherData = new CurrentWeatherData();
        weatherData.setCityName(jsonObject.getString("name"));
        weatherData.setCityId(jsonObject.getJsonNumber("id").longValue());
        JsonObject coord = jsonObject.getJsonObject("coord");
        weatherData.setLongitude(coord.getJsonNumber("lon").doubleValue());
        weatherData.setLatitude(coord.getJsonNumber("lat").doubleValue());
        JsonObject main = jsonObject.getJsonObject("main");
        weatherData.setTemp(translateTemperature((float) main.getJsonNumber("temp").doubleValue(), translateFromKelvin));
        weatherData.setTemp_min(translateTemperature((float) main.getJsonNumber("temp_min").doubleValue(), translateFromKelvin));
        weatherData.setTemp_max(translateTemperature((float) main.getJsonNumber("temp_max").doubleValue(), translateFromKelvin));

        if (main.containsKey("humidity")) {
            weatherData.setHumidity(main.getInt("humidity"));
        }
        if (main.containsKey("pressure")) {
            weatherData.setPressure(main.getInt("pressure"));
        }

        JsonArray weather = jsonObject.getJsonArray("weather");
        if (weather.size() > 0) {
        	JsonObject weatherObject = weather.getJsonObject(0);
            weatherData.setWeatherIcon(weatherObject.getString("icon"));
        }
        return weatherData;
    }

    private static float translateTemperature(float temp, boolean translate) {
        return translate ? temp - 274.15f : temp;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JsonObject readJsonFromUrl(String url) throws IOException {
        url = url.concat("&APPID=" + WeatherFactory.API_CODE);
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd       = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String         jsonText = readAll(rd);

    		InputStream    jsonis   = new ByteArrayInputStream(jsonText.toString().getBytes());
    		JsonReader     reader   = Json.createReader(jsonis);
    		JsonStructure  jsonst   = reader.read();

            return jsonst.asJsonObject();
        }
    }
}

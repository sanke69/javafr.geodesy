package fr.gis.sdk.objects.weather;

import java.util.Date;

public class ForecastWeatherData {
    
    long dateTime;
    int clouds;
    double humidity;
    double pressure;
    double wind;
    double tempMin;
    double tempMax;
    double tempNight;
    double tempDay;
    double tempEvening;
    double tempMorning;
    String description;
    double snow;
    double rain;
    String icon;

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public double getTempNight() {
        return tempNight;
    }

    public void setTempNight(double tempNight) {
        this.tempNight = tempNight;
    }

    public double getTempDay() {
        return tempDay;
    }

    public void setTempDay(double tempDay) {
        this.tempDay = tempDay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "forcast for " + new Date(dateTime*1000) + ":  min " + tempMin + ", max: " + tempMax + "  " + description + " morning:  " + tempMorning + " day: " + tempDay + " evening: " + tempEvening + " snow " + snow + "mm, rain: " + rain + "mm";
    }

    public void setTempEvening(float temEvening) {
        this.tempEvening = temEvening;
    }

    public void setTempMorning(float tempMorning) {
        this.tempMorning = tempMorning;
    }

    public double getTemEvening() {
        return tempEvening;
    }

    public double getTempMorning() {
        return tempMorning;
    }

    public double getSnow() {
        return snow;
    }

    public void setSnow(double snow) {
        this.snow = snow;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }
    
    
    
    
    
    
    
    
    
}

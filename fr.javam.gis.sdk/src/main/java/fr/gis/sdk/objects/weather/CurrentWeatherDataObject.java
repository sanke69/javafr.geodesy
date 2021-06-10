package fr.gis.sdk.objects.weather;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class CurrentWeatherDataObject {
    
    public static final long OUTDATED_TRESHOLD = 1800000l; //30 minutes old are outdated
    
    private List<CurrentWeatherData> data;
    PropertyChangeSupport propertyChangeSupport;
    public static final String LOADED = "loaded";
    private boolean loaded = false;
    private long time;
    
    public CurrentWeatherDataObject() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }
    
    public void setData(List<CurrentWeatherData> data) {
        this.data = data;
        setLoaded();
        propertyChangeSupport.firePropertyChange(LOADED, null, data);
    }
    
    public List<CurrentWeatherData> getData() {
        return data;
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void setLoaded() {
        this.loaded = true;
        time = System.currentTimeMillis();
    }
    
    public boolean isLoaded() {
        return loaded;
    }
    
    public boolean isOutdated() {
        long timeDelay = System.currentTimeMillis() - time;
        return timeDelay >= OUTDATED_TRESHOLD;
    }
}

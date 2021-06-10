package fr.gis.graphics.core.hud;

import fr.gis.graphics.core.controller.DefaultGisServiceController;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class MapInfoControl extends Control {

	private DoubleProperty longitude, latitude;
	private StringProperty roadName;
	
	private DefaultGisServiceController map;

	public MapInfoControl(DefaultGisServiceController _mapDisplay) {
		longitude = new SimpleDoubleProperty(-1);
		latitude  = new SimpleDoubleProperty(-1);
		
		roadName  = new SimpleStringProperty("Unknown");
		
		map       = _mapDisplay;
	}

	@Override
	protected Skin<?> createDefaultSkin() {
		return new MapInfoControlVisual(this);
	}

	public DefaultGisServiceController getMapDisplay() {
		return map;
	}
	
	public DoubleProperty longitudeProperty() {
		return longitude;
	}
	public void setLongitude(double _lg) {
		longitude.set(_lg);
	}
	public double getLongitude() {
		return longitude.get();
	}

	public DoubleProperty latitudeProperty() {
		return latitude;
	}
	public void setLatitude(double _lt) {
		latitude.set(_lt);
	}
	public double getLatitude() {
		return latitude.get();
	}

	public StringProperty roadNameProperty() {
		return roadName;
	}
	public void setRoadName(String _label) {
		roadName.set(_label);
	}
	public String getRoadName() {
		return roadName.get();
	}

}

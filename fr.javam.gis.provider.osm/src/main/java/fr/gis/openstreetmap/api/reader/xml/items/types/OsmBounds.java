package fr.gis.openstreetmap.api.reader.xml.items.types;

import fr.gis.openstreetmap.api.reader.xml.items.OsmItem;

public class OsmBounds extends OsmItem {

	private double minlat, minlon, maxlat, maxlon;

	public OsmBounds() { 
		super();

		minlat = 0.0f; 
		minlon = 0.0f; 
		maxlat = 0.0f; 
		maxlon = 0.0f;
	}
	public OsmBounds(double _minlat, double _minlon, double _maxlat, double _maxlon) { 
		super();

		minlat = _minlat; 
		minlon = _minlon; 
		maxlat = _maxlat; 
		maxlon = _maxlon; 
	}
	
	public double getMinLat() { return minlat; }
	public double getMinLon() { return minlon; }
	public double getMaxLat() { return maxlat; }
	public double getMaxLon() { return maxlon; }
	
	public void setMinLat(double _minlat) { minlat = _minlat; }
	public void setMinLon(double _minlon) { minlon = _minlon; }
	public void setMaxLat(double _maxlat) { maxlat = _maxlat; }
	public void setMaxLon(double _maxlon) { maxlon = _maxlon; }

	public String toString(){
		return new String("[Bounds] ")
				+ "(minlat= " + minlat
				+ ",minlon= " + minlon
				+ ",maxlat= " + maxlat
				+ ",maxlon= " + maxlon + ")";
	}

}

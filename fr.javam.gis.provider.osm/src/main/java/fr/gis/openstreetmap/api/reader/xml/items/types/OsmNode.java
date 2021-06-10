package fr.gis.openstreetmap.api.reader.xml.items.types;

import fr.gis.openstreetmap.api.reader.xml.items.OsmItem;

public class OsmNode extends OsmItem {

	private double 	  longitude, 
					  latitude;

	public OsmNode() {
		super();
		longitude	= 0.0f;
		latitude	= 0.0f;
	}

	public double 		getLongitude() 				{ return this.longitude; }
	public double 		getLatitude()  				{ return this.latitude; }

	public void 		setLongitude(double _long) 	{ this.longitude 	= _long; }
	public void 		setLatitude(double _lat)  	{ this.latitude 	= _lat; }

	public String toString(){
		return new String("[Node] ")
				+ "(id= " + getId()
				+ ",longitude= " + longitude
				+ ",latitude= " + latitude
				+ ")";
	}

}

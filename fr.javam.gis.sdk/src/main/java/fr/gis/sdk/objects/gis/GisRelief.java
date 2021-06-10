package fr.gis.sdk.objects.gis;

import java.util.List;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.Gis;
import fr.gis.sdk.objects.core.GisArea;
import fr.java.lang.properties.ID;

public class GisRelief extends GisArea implements Gis.Relief {

	public GisRelief(ID _id, List<GeoCoordinate> _coords) {
		super(_id, _coords);
	}

	public Gis.Relief.Type getType() {
		return (Gis.Relief.Type) getProperty("type");
	}
	public void       setType(Gis.Relief.Type _type) {
		addProperty("type", _type);
	}

	public String     getName() {
		return (String) getProperty("name");
	}
	public void       setName(String _name) {
		addProperty("name", _name);
	}

}

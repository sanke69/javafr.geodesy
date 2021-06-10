package fr.gis.sdk.objects.gis;

import java.util.Arrays;
import java.util.List;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.referential.Datum;
import fr.gis.api.Gis;
import fr.gis.sdk.objects.GisObject;
import fr.java.lang.properties.ID;

public class GisStream extends GisObject implements Gis.Stream {
	protected List<GeoCoordinate>		geometry;
	protected Datum 					datum;
	
	protected String description;

	public GisStream(ID _id, Datum _datum, GeoCoordinate... _nodes) {
		super(_id);
		datum    = _datum;
		geometry = Arrays.asList(_nodes);
	}
	public GisStream(ID _id, Datum _crs, List<GeoCoordinate> _nodes) {
		super(_id);
		datum    = _crs;
		geometry = _nodes;
	}

	public Datum 				getDatum() {
		return datum;
	}

	public List<GeoCoordinate> 	getTops() {
		return geometry;
	}

}

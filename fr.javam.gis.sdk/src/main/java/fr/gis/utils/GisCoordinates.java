package fr.gis.utils;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.utils.GeoCoordinates;
import fr.gis.api.Gis;
import fr.gis.api.road.Road;
import fr.gis.api.road.RoadCoordinate;
import fr.gis.utils.objects.AdapterMapSpheric2D;

public class GisCoordinates extends GeoCoordinates {

	public static RoadCoordinate.Editable 				newMapCoordinate(GeoCoordinate _raw, Road.Element _elt, Gis.Direction _dir, double _abs_curv) {
		return new AdapterMapSpheric2D(_raw.asWGS84().getLongitude(), _raw.asWGS84().getLatitude(), _elt, _dir, -1, -1, _abs_curv);
	}
	public static RoadCoordinate.Editable 				newMapCoordinate(GeoCoordinate _raw, double _heading, Road.Element _elt, Gis.Direction _dir, double _abs_curv) {
		return new AdapterMapSpheric2D(_raw.asWGS84().getLongitude(), _raw.asWGS84().getLatitude(), _heading, _elt, _dir, -1, -1, _abs_curv);
	}

}

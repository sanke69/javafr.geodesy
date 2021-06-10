package fr.gms.utils;

import java.util.List;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.referential.Datum;
import fr.gis.api.Gis;
import fr.gis.api.road.Road;
import fr.gis.sdk.objects.gis.GisPath;
import fr.gis.sdk.objects.road.RoadTraficSign;
import fr.java.lang.properties.ID;

public class GmsObjects {

	public static Gis.Path	createPath(GeoCoordinate... _waypoints) {
		return new GisPath(_waypoints);
	}
	public static Gis.Path	createPath(ID _id, Datum _datum, GeoCoordinate... _waypoints) {
		GisPath path = new GisPath(_id, _datum, _waypoints);
		return  path;
	}
	public static Gis.Path	createPath(ID _id, Datum _datum, List<GeoCoordinate> _waypoints) {
		GisPath path = new GisPath(_id, _datum, _waypoints);
		return  path;
	}

	public static RoadTraficSign createMSL(ID _id, GeoCoordinate _coord, int _msl) {
		RoadTraficSign elt = new RoadTraficSign(_id, _coord, Road.TraficSign.Type.MANDATORY_SPEED_LIMIT);
		elt.getProperties().put(Road.TraficSign.MSL_VALUE, _msl);
		return elt;
	}
	public static RoadTraficSign createCrossWalk(ID _id, GeoCoordinate _coord) {
		RoadTraficSign elt = new RoadTraficSign(_id, _coord, Road.TraficSign.Type.CROSS_WALK);

		return elt;
	}
	public static RoadTraficSign createTraficLight(ID _id, GeoCoordinate _coord, int _idLight) {
		RoadTraficSign elt = new RoadTraficSign(_id, _coord, Road.TraficSign.Type.TRAFIC_LIGHT);
		elt.getProperties().put(Road.TraficSign.TRAFICLIGHT_ID, _idLight);

		return elt;
	}
	public static RoadTraficSign createStop(ID _id, GeoCoordinate _coord) {
		RoadTraficSign elt = new RoadTraficSign(_id, _coord, Road.TraficSign.Type.STOP);

		return elt;
	}
	public static RoadTraficSign createClP(ID _id, GeoCoordinate _coord) {
		RoadTraficSign elt = new RoadTraficSign(_id, _coord, Road.TraficSign.Type.YIELD);

		return elt;
	}

}

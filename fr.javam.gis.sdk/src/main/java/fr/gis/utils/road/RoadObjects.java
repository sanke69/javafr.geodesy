package fr.gis.utils.road;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.road.Road;
import fr.gis.sdk.objects.road.RoadElement;
import fr.gis.sdk.objects.road.RoadTraficSign;
import fr.java.lang.properties.ID;

public final class RoadObjects {

	public static Road.Element createRoadElement(ID _id, GeoCoordinate... _nodes) {
		return new RoadElement(_id, _nodes);
	}

	public static Road.TraficSign createMSL(ID _id, GeoCoordinate _coord, int _msl) {
		RoadTraficSign elt = new RoadTraficSign(_id, _coord, RoadTraficSign.Type.MANDATORY_SPEED_LIMIT);
		elt.getProperties().put(RoadTraficSign.MSL_VALUE, _msl);
		return elt;
	}
	public static Road.TraficSign createCrossWalk(ID _id, GeoCoordinate _coord) {
		Road.TraficSign elt = new RoadTraficSign(_id, _coord, RoadTraficSign.Type.CROSS_WALK);

		return elt;
	}
	public static Road.TraficSign createTraficLight(ID _id, GeoCoordinate _coord, int _idLight) {
		RoadTraficSign elt = new RoadTraficSign(_id, _coord, RoadTraficSign.Type.TRAFIC_LIGHT);
		elt.getProperties().put(RoadTraficSign.TRAFICLIGHT_ID, _idLight);

		return elt;
	}
	public static Road.TraficSign createStop(ID _id, GeoCoordinate _coord) {
		RoadTraficSign elt = new RoadTraficSign(_id, _coord, RoadTraficSign.Type.STOP);

		return elt;
	}
	public static Road.TraficSign createClP(ID _id, GeoCoordinate _coord) {
		RoadTraficSign elt = new RoadTraficSign(_id, _coord, RoadTraficSign.Type.YIELD);

		return elt;
	}

}

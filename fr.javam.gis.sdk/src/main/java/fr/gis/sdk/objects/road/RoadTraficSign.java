package fr.gis.sdk.objects.road;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.Gis;
import fr.gis.api.road.Road;
import fr.gis.api.road.RoadCoordinate;
import fr.gis.sdk.objects.core.GisNode;
import fr.java.lang.properties.ID;

public class RoadTraficSign extends GisNode implements Road.TraficSign {
	private Road.TraficSign.Type	type;

	private RoadCoordinate 			position;
	private Map<String, Object> 	properties;

	public RoadTraficSign(Gis.Node _node) {
		super(_node.getId(), _node.getPosition());
	}
	public RoadTraficSign(ID _id, GeoCoordinate _coord, Road.TraficSign.Type _type) {
		super(_id, _coord);
		id = _id;
		type = _type;

		properties = new HashMap<String, Object>();
	}
	public RoadTraficSign(Road.TraficSign _sign, RoadCoordinate _mapPosition) {
		super(_sign.getId(), _sign.getPosition());
		position = _mapPosition;
		System.err.println("TBC");

		properties = new HashMap<String, Object>();
	}

	public Optional<RoadCoordinate> getMapInfos() {
		return Optional.ofNullable(position);
	}
	public void setMapInfos(RoadCoordinate _position) {
		position = _position;
	}

	public Road.TraficSign.Type getType() {
		return type;
	}
	public Object getProperty(String _property) {
		return properties.get(_property);
	}
	
	@Deprecated
	public Map<String, Object> getProperties() {
		return properties;
	}

	public String toString() {
		switch (type) {
		case YIELD:
			return "CDP" + "\t" + id + "\t" + type.toString() + "\t" + getPosition();
		case CROSS_WALK:
			return "CROSS" + "\t" + id + "\t" + type.toString() + "\t" + getPosition();
		case MANDATORY_SPEED_LIMIT:
			return "MSL" + "\t" + id + "\t" + type.toString() + "\t" + getPosition();
		case STOP:
			return "STOP" + "\t" + id + "\t" + type.toString() + "\t" + getPosition();
		case TRAFIC_LIGHT:
			return "TRAFL" + "\t" + id + "\t" + type.toString() + "\t" + getPosition();
		default:
			return "UNDEF";
		}

	}

}

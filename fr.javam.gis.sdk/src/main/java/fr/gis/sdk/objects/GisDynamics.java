package fr.gis.sdk.objects;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.GeoDynamics;
import fr.gis.api.Gis;
import fr.java.lang.collections.RingList;
import fr.java.lang.properties.ID;
import fr.java.lang.properties.Timestamp;
import fr.java.math.algebra.vector.generic.Vector3D;

public class GisDynamics extends GisObject implements Gis.Dynamics {
	final Gis.User	nature;

	Timestamp 		timestamp;
	GeoCoordinate 	position;
	Vector3D		velocity;
	Vector3D		acceleration;
	double			heading;

	public GisDynamics(ID _id, Gis.User _nature) {
		this(_id, _nature, null);
	}
	public GisDynamics(ID _id, Gis.User _nature, Timestamp _timestamp, GeoCoordinate _coords) {
		this(_id, _nature, _timestamp, _coords, null, null, 0d);
	}
	public GisDynamics(ID _id, Gis.User _nature, Timestamp _timestamp, GeoCoordinate _position, Vector3D _velocity, Vector3D _acceleration, double _heading) {
		super(_id);
		nature = _nature;
		
		timestamp    = _timestamp;
		position     = _position;
		velocity     = _velocity;
		acceleration = _acceleration;
		heading      = _heading;
	}
	public GisDynamics(ID _id, Gis.User _nature, GeoDynamics _coords) {
		this(_id, _nature, _coords != null ? _coords.getTimestamp() : null, _coords, _coords != null ? _coords.getVelocity() : null, _coords != null ? _coords.getAcceleration() : null, _coords != null ? _coords.getHeading() : Double.NaN);
	}

	@Override
	public final Gis.User 				getNature() {
		return nature;
	}

	public final void					setTimestamp(Timestamp _timestamp) {
		timestamp = _timestamp;
	}
	@Override
	public final Timestamp				getTimestamp() {
		return timestamp;
	}

	public final void 					setPosition(GeoCoordinate _position) {
		position = _position;
	}
	@Override
	public final GeoCoordinate 			getPosition() {
		return position;
	}

	public final void 					setHeading(double _heading) {
		heading = _heading;
	}
	public final double 				getHeading() {
		return heading;
	}

	public final void 					setVelocity(Vector3D _velocity) {
		velocity = _velocity;
	}
	@Override
	public final Vector3D 				getVelocity() {
		return velocity;
	}

	public final void	 				setAcceleration(Vector3D _acceleration) {
		acceleration = _acceleration;
	}
	@Override
	public final Vector3D 				getAcceleration() {
		return acceleration;
	}

	@Override
	public final RingList<GeoDynamics> 	getHistory() {
		return null;
	}

	@Override
	public String 						toString() {
		StringBuilder sb = new StringBuilder();

//		sb.append("MOBILE: " + id + "\n");
//		sb.append(" - TIM: " + getTimestamp() + " ms" + "\n");
//		sb.append(" - POS: " + getPosition() + "\n");
//		sb.append(" - HEA: " + getHeading() + "°" + "\n");
//		sb.append(" - VEL: " + getVelocity() + "km/h" + "\n");
//		sb.append(" - ACC: " + getAcceleration() + "km/h2" + "\n");
		sb.append("GisDyn: " + id + "\n");

		return sb.toString();
	}

}

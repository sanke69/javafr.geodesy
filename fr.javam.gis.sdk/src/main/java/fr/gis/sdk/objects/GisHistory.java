package fr.gis.sdk.objects;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.GeoDynamics;
import fr.geodesic.utils.GeoCoordinates;
import fr.gis.api.Gis;
import fr.java.lang.collections.RingList;
import fr.java.lang.properties.ID;
import fr.java.lang.properties.Timestamp;
import fr.java.math.algebra.NumberVector.Norm;
import fr.java.math.geometry.space.Vector3D;
import fr.java.maths.algebra.Vectors;
import fr.java.sdk.patterns.timeable.Timestamps;

public class GisHistory extends GisObject implements Gis.Dynamics {
	Gis.User				nature;
	RingList<GeoDynamics> 	history;

	public GisHistory(ID _id) {
		this(_id, Gis.User.UNKNOWN, null, null);
	}
	public GisHistory(ID _id, Gis.User _nature) {
		this(_id, _nature, null, null);
	}
	public GisHistory(ID _id, Gis.User _nature, Timestamp _timestamp, GeoCoordinate _coords) {
		super(_id);
		nature = _nature;
		
		history = new RingList<GeoDynamics>(10);
		if(_timestamp != null && _coords != null)
			history.insert(GeoCoordinates.newDynamics(_coords, _timestamp, Double.NaN, Vectors.of(0d, 0d, 0d), Vectors.of(0d, 0d, 0d)));
	}
	public GisHistory(ID _id, Gis.User _nature, GeoDynamics _coords) {
		super(_id);
		nature = _nature;
		
		history = new RingList<GeoDynamics>(10);
		if(_coords != null)
			history.insert(_coords);
	}

	public GisHistory(ID _id, GeoDynamics _coords) {
		this(_id, Gis.User.UNKNOWN, _coords);
	}

	@Override
	public final Timestamp				getTimestamp() {
		return history.get(0).getTimestamp();
	}
	@Override
	public final Gis.User 				getNature() {
		return nature;
	}

	@Override
	public final GeoDynamics 			getPosition() {
		return history.get(0);
	}
	public final double 				getHeading() {
		return history.get(0).getHeading();
	}
	@Override
	public final Vector3D 				getVelocity() {
		return history.get(0).getVelocity();
	}
	@Override
	public final Vector3D 				getAcceleration() {
		return history.get(0).getAcceleration();
	}

	@Override
	public final RingList<GeoDynamics> 	getHistory() {
		return history;
	}
	public final GeoCoordinate 			getHistory(int _index) {
		return history.get(_index);
	}

	public void 						update(long _timestamp, GeoCoordinate _pos, double _heading, Vector3D _vel, Vector3D _acc) {
		Timestamp T = Timestamps.of(_timestamp);

		if(history.size() == 0)
			history.insert(GeoCoordinates.newDynamics(_pos, T, _heading, _vel, _acc));
		else {
			GeoDynamics prevP = getHistory().get(0);
			Timestamp   prevT = prevP.getTimestamp();
			Vector3D    prevV = prevP.getVelocity();
	
			double   heading      = !Double.isNaN(_heading) ? _heading : GeoCoordinates.computeHeading(prevP, _pos);
			Vector3D velocity     = _vel     != null        ? _vel     : GeoCoordinates.computeVelocity(prevT, prevP, T, _pos);
			Vector3D acceleration = _acc     != null        ? _acc     : GeoCoordinates.computeAcceleration(prevT, prevP, prevV, T, _pos, velocity);
	
			if(velocity.norm(Norm.Euclidian) < 3 && getHistory().size() > 2)
				heading = prevP.getHeading();
	
			history.insert(GeoCoordinates.newDynamics(_pos, T, heading, velocity, acceleration));
		}

		postUpdate();
	}
	public void 						postUpdate() {}

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

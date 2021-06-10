package fr.gms.map.objects.ego;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.Gis;
import fr.gis.sdk.objects.GisHistory;
import fr.gms.api.ego.EgoVehicle;
import fr.gms.api.sensor.EHorizon;
import fr.gms.map.objects.sensor.EHorizonOrientedAdapter;
import fr.java.lang.properties.ID;
import fr.java.sdk.patterns.timeable.Timestamps;

public class EgoVehicleAdapter extends GisHistory implements EgoVehicle {
	private EHorizon.Oriented	 eHorizon;

	public EgoVehicleAdapter(ID _id) {
		super(_id, Gis.User.EgoVehicle, Timestamps.epoch(), null);

		eHorizon = new EHorizonOrientedAdapter(getPosition()) {
			@Override public void update(boolean _withDynamics) { }};
	}
	public EgoVehicleAdapter(ID _id, GeoCoordinate _position) {
		super(_id, Gis.User.EgoVehicle, Timestamps.epoch(), _position);

		eHorizon = new EHorizonOrientedAdapter(_position) {
			@Override public void update(boolean _withDynamics) { }};
	}
	public EgoVehicleAdapter(ID _id, GeoCoordinate _position, double _radius) {
		super(_id, Gis.User.EgoVehicle, Timestamps.epoch(), _position);

		eHorizon = new EHorizonOrientedAdapter(_position, _radius) {
			@Override public void update(boolean _withDynamics) { }};
	}

	protected final void			setEHorizon(EHorizon.Oriented _eHorizon) {
		eHorizon = _eHorizon;
	}
	public final EHorizon.Oriented	getEHorizon() {
		return eHorizon;
	}

	public String 					toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("EGO-VEHICLE: " + id + "\n");
		sb.append(" - TIM: " + getTimestamp() + " ms" + "\n");
		sb.append(" - POS: " + getPosition() + "\n");
		sb.append(" - HEA: " + getHeading() + "°" + "\n");
		sb.append(" - VEL: " + getVelocity() + "km/h" + "\n");
		sb.append(" - ACC: " + getAcceleration() + "km/h2" + "\n");

		return sb.toString();
	}

}

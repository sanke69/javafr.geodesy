package fr.utils.geodesic.adapters;

import java.text.DecimalFormat;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.GeoDynamics;
import fr.geodesic.api.referential.Datum;
import fr.java.lang.properties.Timestamp;
import fr.java.math.geometry.space.Vector3D;

public class AdapterDynamicCartesian2D extends AdapterGeoCartesian2D implements GeoDynamics.Cartesian2D.Editable {
	Timestamp timestamp;
	Double    heading;
	Vector3D  velocity, acceleration;

	public AdapterDynamicCartesian2D(double _x, String _zone_x, double _y, String _zone_y) {
		this(Datum.UTM, _x, _zone_x, _y, _zone_y, null, null, null, null);
	}
	public AdapterDynamicCartesian2D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y) {
		this(_datum, _x, _zone_x, _y, _zone_y, null, null, null, null);
	}
	public AdapterDynamicCartesian2D(double _x, String _zone_x, double _y, String _zone_y, Timestamp _timestamp) {
		this(Datum.UTM, _x, _zone_x, _y, _zone_y, _timestamp, null, null, null);
	}
	public AdapterDynamicCartesian2D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y, Timestamp _timestamp) {
		this(_datum, _x, _zone_x, _y, _zone_y, _timestamp, null, null, null);
	}
	public AdapterDynamicCartesian2D(double _x, String _zone_x, double _y, String _zone_y, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
		this(Datum.UTM, _x, _zone_x, _y, _zone_y, null, _heading, _velocity, _acceleration);
	}
	public AdapterDynamicCartesian2D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
		this(_datum, _x, _zone_x, _y, _zone_y, null, _heading, _velocity, _acceleration);
	}
	public AdapterDynamicCartesian2D(double _x, String _zone_x, double _y, String _zone_y, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
		this(Datum.UTM, _x, _zone_x, _y, _zone_y, _timestamp, _heading, _velocity, _acceleration);
	}
	public AdapterDynamicCartesian2D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
		super(_datum, _x, _zone_x, _y, _zone_y);
		timestamp    = _timestamp;
		heading      = _heading;
		velocity     = _velocity;
		acceleration = _acceleration;
	}
	public AdapterDynamicCartesian2D(GeoCoordinate.Cartesian2D _coord, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
		super(_coord.getDatum(), _coord.getX(), _coord.getZoneX(), _coord.getY(), _coord.getZoneY());
		timestamp    = _timestamp;
		heading      = _heading;
		velocity     = _velocity;
		acceleration = _acceleration;
	}

	@Override public Timestamp 							getTimestamp() 											{ return timestamp; }

	@Override public void 								setHeading(double _heading) 							{ heading = _heading; }
	@Override public double 							getHeading() 											{ return heading; }

	@Override public void 								setVelocity(Vector3D _velocity) 						{ velocity = _velocity; }
	@Override public Vector3D 							getVelocity() 											{ return velocity; }

	@Override public void 								setAcceleration(Vector3D _acceleration) 				{ acceleration = _acceleration;  }
	@Override public Vector3D 							getAcceleration() 										{ return acceleration; }

	@Override public String 							toString() 												{ return toString(new DecimalFormat()); }
	@Override public String 							toString(DecimalFormat _df) 							{ return "(" + getX() + (getZoneX() != null ? " " + getZoneX() : "") + ", " + getY() + (getZoneY() != null ? " " + getZoneY() : "") + ")"; }

	@Override public GeoCoordinate.Cartesian2D.Editable	clone() 												{ return new AdapterDynamicCartesian2D(getDatum(), getX(), getZoneX(), getY(), getZoneY(), getTimestamp(), getHeading(), getVelocity(), getAcceleration()); }

}
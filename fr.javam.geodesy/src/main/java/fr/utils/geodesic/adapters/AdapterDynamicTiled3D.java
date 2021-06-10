package fr.utils.geodesic.adapters;

import java.text.DecimalFormat;

import fr.geodesic.api.GeoDynamics;
import fr.geodesic.api.referential.Datum;
import fr.java.lang.properties.Timestamp;
import fr.java.math.geometry.space.Vector3D;

public class AdapterDynamicTiled3D extends AdapterGeoTiled3D implements GeoDynamics.Tiled3D.Editable {
	Timestamp timestamp;
	Double    heading;
	Vector3D  velocity, acceleration;

	public AdapterDynamicTiled3D(int _lvl, int _i, int _j, double _x, double _y, double _alt) {
		this(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y, _alt, null, null, null, null);
	}
	public AdapterDynamicTiled3D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y, double _alt) {
		this(_datum, _lvl, _i, _j, _x, _y, _alt, null, null, null, null);
	}
	public AdapterDynamicTiled3D(int _lvl, int _i, int _j, double _x, double _y, double _alt, Timestamp _timestamp) {
		this(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y, _alt, _timestamp, null, null, null);
	}
	public AdapterDynamicTiled3D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y, double _alt, Timestamp _timestamp) {
		this(_datum, _lvl, _i, _j, _x, _y, _alt, _timestamp, null, null, null);
	}
	public AdapterDynamicTiled3D(int _lvl, int _i, int _j, double _x, double _y, double _alt, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
		this(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y, _alt, null, _heading, _velocity, _acceleration);
	}
	public AdapterDynamicTiled3D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y, double _alt, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
		this(_datum, _lvl, _i, _j, _x, _y, _alt, null, _heading, _velocity, _acceleration);
	}
	public AdapterDynamicTiled3D(int _lvl, int _i, int _j, double _x, double _y, double _alt, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
		this(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y, _alt, _timestamp, _heading, _velocity, _acceleration);
	}
	public AdapterDynamicTiled3D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y, double _alt, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
		super(_datum, _lvl, _i, _j, _x, _y, _alt);
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
	@Override public String 							toString(DecimalFormat _df) 							{ return "(" + getX() + ", " + getY() + ", " + getZ() + " @ " + getI() + ", " + getJ() + ", " + getLevel() + ")"; }

	@Override public GeoDynamics.Tiled3D.Editable		clone() 												{ return new AdapterDynamicTiled3D(getDatum(), getLevel(), getI(), getJ(), getX(), getY(), getZ(), getTimestamp(), getHeading(), getVelocity(), getAcceleration()); }

}

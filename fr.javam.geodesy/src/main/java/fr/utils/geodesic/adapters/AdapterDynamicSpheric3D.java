package fr.utils.geodesic.adapters;

import java.text.DecimalFormat;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.GeoDynamics;
import fr.geodesic.api.referential.Datum;
import fr.java.lang.properties.Timestamp;
import fr.java.math.geometry.space.Vector3D;
import fr.utils.geodesic.Angles;

public class AdapterDynamicSpheric3D extends AdapterGeoSpheric3D implements GeoDynamics.Spheric3D.Editable {
		Timestamp timestamp;
		Double    heading;
		Vector3D  velocity, acceleration;

		public AdapterDynamicSpheric3D(double _longitude, double _latitude, double _altitude) {
			this(Datum.WGS84, _longitude, _latitude, _altitude, null, null, null, null);
		}
		public AdapterDynamicSpheric3D(Datum _datum, double _longitude, double _latitude, double _altitude) {
			this(_datum, _longitude, _latitude, _altitude, null, null, null, null);
		}
		public AdapterDynamicSpheric3D(double _longitude, double _latitude, double _altitude, Timestamp _timestamp) {
			this(Datum.WGS84, _longitude, _latitude, _altitude, _timestamp, null, null, null);
		}
		public AdapterDynamicSpheric3D(Datum _datum, double _longitude, double _latitude, double _altitude, Timestamp _timestamp) {
			this(_datum, _longitude, _latitude, _altitude, _timestamp, null, null, null);
		}
		public AdapterDynamicSpheric3D(double _longitude, double _latitude, double _altitude, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(Datum.WGS84, _longitude, _latitude, _altitude, null, _heading, _velocity, _acceleration);
		}
		public AdapterDynamicSpheric3D(Datum _datum, double _longitude, double _latitude, double _altitude, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(_datum, _longitude, _latitude, _altitude, null, _heading, _velocity, _acceleration);
		}
		public AdapterDynamicSpheric3D(double _longitude, double _latitude, double _altitude, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(Datum.WGS84, _longitude, _latitude, _altitude, _timestamp, _heading, _velocity, _acceleration);
		}
		public AdapterDynamicSpheric3D(Datum _datum, double _longitude, double _latitude, double _altitude, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			super(_datum, _longitude, _latitude, _altitude);
			timestamp    = _timestamp;
			heading      = _heading;
			velocity     = _velocity;
			acceleration = _acceleration;
		}
		public AdapterDynamicSpheric3D(GeoCoordinate.Spheric3D _coord, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			super(_coord.getDatum(), _coord.getLongitude(), _coord.getLatitude(), _coord.getAltitude());
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
		@Override public String 							toString(DecimalFormat _df) 							{
			/**/
			StringBuilder sb = new StringBuilder();
			double[] dmsLg = Angles.Degree2DMS(getLongitude());
			double[] dmsLt = Angles.Degree2DMS(getLatitude());

			sb.append((int) dmsLg[0] + "°" + (int) dmsLg[1] + "'" + _df.format(dmsLg[2]) + "''");
			sb.append(", ");
			sb.append((int) dmsLt[0] + "°" + (int) dmsLt[1] + "'" + _df.format(dmsLt[2]) + "''"); 
			
			if(getAltitude() > 0)
				sb.append(" " + _df.format(getAltitude()) + " m");
			return sb.toString();
			/*/
			return "(" + _df.format(getLongitude()) + ", " + _df.format(getLatitude()) + ", " + getAltitude() + ")";
			/**/
		}

		@Override public GeoCoordinate.Spheric3D.Editable	clone() { return new AdapterDynamicSpheric3D(getDatum(), getLongitude(), getLatitude(), getAltitude(), getTimestamp(), getHeading(), getVelocity(), getAcceleration()); }

	};

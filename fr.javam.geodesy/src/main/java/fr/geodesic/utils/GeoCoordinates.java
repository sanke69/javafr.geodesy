package fr.geodesic.utils;

import java.text.DecimalFormat;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.GeoDynamics;
import fr.geodesic.api.referential.Datum;
import fr.geodesic.utils.converters.Lambert93Converter;
import fr.geodesic.utils.converters.MGRSConverter;
import fr.geodesic.utils.converters.UTMConverter;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.lang.properties.Timestamp;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.space.Point3D;
import fr.java.math.geometry.space.Vector3D;
import fr.java.sdk.math.Angles;
import fr.java.sdk.math.algebra.Vectors;
import fr.java.sdk.math.algebra.vectors.DoubleVector3D;
import fr.java.sdk.patterns.timeable.Timestamps;

public class GeoCoordinates {
	private final static GeoCoordinate.Converter utmConverter  = new UTMConverter();
	private final static GeoCoordinate.Converter mgrsConverter = new MGRSConverter();
	private final static GeoCoordinate.Converter l93Converter  = new Lambert93Converter();

	protected static class EditableCartesian2D implements GeoCoordinate.Cartesian2D.Editable {
		Datum  datum;
		double x, y;
		String zoneX, zoneY;

		public EditableCartesian2D(double _x, String _zone_x, double _y, String _zone_y) {
			this(Datum.UTM, _x, _zone_x, _y, _zone_y);
		}
		public EditableCartesian2D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y) {
			super();
			datum = _datum;
			x     = _x;
			zoneX = _zone_x;
			y     = _y;
			zoneY = _zone_y;
		}

		@Override public Datum 								getDatum() 												{ return datum; }

		@Override public void 								setX(double _x) 										{ x = _x; }
		@Override public double 							getX() 													{ return x; }
		@Override public void 								setZoneX(String _zone) 									{ zoneX = _zone; }
		@Override public String 							getZoneX() 												{ return zoneX; }
		@Override public void 								setY(double _y) 										{ y = _y; }
		@Override public double 							getY() 													{ return y; }
		@Override public void 								setZoneY(String _zone) 									{ zoneY = _zone; }
		@Override public String 							getZoneY() 												{ return zoneY; }

		@Override public String 							toString() 												{ return toString(new DecimalFormat()); }
		@Override public String 							toString(DecimalFormat _df) 							{ return "(" + getX() + (getZoneX() != null ? " " + getZoneX() : "") + ", " + getY() + (getZoneY() != null ? " " + getZoneY() : "") + ")"; }

		@Override public GeoCoordinate.Cartesian2D.Editable	clone() 												{ return new EditableCartesian2D(getDatum(), getX(), getZoneX(), getY(), getZoneY()); }

		@Override public boolean							equals(GeoCoordinate _other) 							{
			if(_other instanceof GeoCoordinate.Cartesian2D) {
				GeoCoordinate.Cartesian2D c2d = (GeoCoordinate.Cartesian2D) _other;
				return getX() == c2d.getX() && getY() == c2d.getY();
			} else
				return GeoDistances.getDistanceBetween(this, _other) < 1e-2; // TODO:: 
		}

	}
	protected static class EditableCartesian3D extends EditableCartesian2D implements GeoCoordinate.Cartesian3D.Editable {
		double altitude;

		public EditableCartesian3D(double _x, String _zone_x, double _y, String _zone_y, double _altitude) {
			super(_x, _zone_x, _y, _zone_y);
			altitude = _altitude;
		}
		public EditableCartesian3D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y, double _altitude) {
			super(_datum, _x, _zone_x, _y, _zone_y);
			altitude = _altitude;
		}

		@Override public void 								setAltitude(double _altitude) 		{ altitude = _altitude; }
		@Override public double 							getAltitude() 						{ return altitude; }

		@Override public String 							toString() 							{ return toString(new DecimalFormat()); }
		@Override public String 							toString(DecimalFormat _df) 		{ return "(" + getX() + (getZoneX() != null ? " " + getZoneX() : "") + ", " + getY() + (getZoneY() != null ? " " + getZoneY() : "") + ", " + getZ() + ")"; }

		@Override public GeoCoordinate.Cartesian3D.Editable	clone() 							{ return new EditableCartesian3D(getX(), getZoneX(), getY(), getZoneY(), getAltitude()); }

		@Override public boolean							equals(GeoCoordinate _other) 		{
			if(_other instanceof GeoCoordinate.Cartesian3D) {
				GeoCoordinate.Cartesian3D c3d = (GeoCoordinate.Cartesian3D) _other;
				return getX() == c3d.getX() && getY() == c3d.getY() && getZ() == c3d.getZ();
		} else
				return GeoDistances.getDistanceBetween(this, _other) < 1e-2; // TODO:: 
		}
	};
	
	protected static class EditableSpheric2D implements GeoCoordinate.Spheric2D.Editable {
		Datum  datum;
		double lg, lt;

		public EditableSpheric2D(double _longitude, double _latitude) {
			this(Datum.WGS84, _longitude, _latitude);
		}
		public EditableSpheric2D(Datum _datum, double _longitude, double _latitude) {
			super();
			datum = _datum;
			lg    = _longitude;
			lt    = _latitude;
		}

		@Override public Datum 								getDatum() 							{ return datum; }

		@Override public void 								setLongitude(double _lg) 			{ lg = _lg; }
		@Override public double 							getLongitude() 						{ return lg; }
		@Override public void 								setLatitude(double _lt) 			{ lt = _lt; }
		@Override public double 							getLatitude() 						{ return lt; }

		@Override public String 							toString() 							{ return toString(new DecimalFormat()); }
		@Override public String 							toString(DecimalFormat _df) 		{
			StringBuilder sb = new StringBuilder();
			double[] dmsLg = Angles.Degree2DMS(getLongitude());
			double[] dmsLt = Angles.Degree2DMS(getLatitude());

			sb.append((int) dmsLg[0] + "°" + (int) dmsLg[1] + "'" + _df.format(dmsLg[2]) + "''");
			sb.append(", ");
			sb.append((int) dmsLt[0] + "°" + (int) dmsLt[1] + "'" + _df.format(dmsLt[2]) + "''"); 

			return sb.toString();
		}

		@Override public GeoCoordinate.Spheric2D.Editable	clone() 							{ return new EditableSpheric2D(getLongitude(), getLatitude()); }

		@Override public boolean							equals(GeoCoordinate _other) 		{
			if(_other instanceof GeoCoordinate.Spheric2D) {
				GeoCoordinate.Spheric2D s2d = (GeoCoordinate.Spheric2D) _other;
				return getLongitude() == s2d.getLongitude() && getLatitude() == s2d.getLatitude();
			} else
				return GeoDistances.getDistanceBetween(this, _other) < 1e-2; // TODO:: 
		}
	}
	protected static class EditableSpheric3D extends EditableSpheric2D implements GeoCoordinate.Spheric3D.Editable {
		double altitude;

		public EditableSpheric3D(double _longitude, double _latitude, double _altitude) {
			this(Datum.WGS84, _longitude, _latitude, _altitude);
		}
		public EditableSpheric3D(Datum _datum, double _longitude, double _latitude, double _altitude) {
			super(_datum, _longitude, _latitude);
			altitude = _altitude;
		}

		@Override public void 								setAltitude(double _altitude) 		{ altitude = _altitude; }
		@Override public double 							getAltitude() 						{ return altitude; }

		@Override public String 							toString() 							{ return toString(new DecimalFormat()); }
		@Override public String 							toString(DecimalFormat _df) 		{
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

		@Override public GeoCoordinate.Spheric3D.Editable	clone() { return new EditableSpheric3D(getDatum(), getLongitude(), getLatitude(), getAltitude()); }

		@Override public boolean							equals(GeoCoordinate _other) 		{
			if(_other instanceof GeoCoordinate.Spheric3D) {
				GeoCoordinate.Spheric3D s3d = (GeoCoordinate.Spheric3D) _other;
				return getLongitude() == s3d.getLongitude() && getLatitude() == s3d.getLatitude() && getAltitude() == s3d.getAltitude();
			} else
				return GeoDistances.getDistanceBetween(this, _other) < 1e-2; // TODO:: 
		}
	};

	protected static class EditableTiled2D implements GeoCoordinate.Tiled2D.Editable {
		Datum  datum;
		double x, y;
		int    i, j, lvl;

		public EditableTiled2D(int _lvl, int _i, int _j, double _x, double _y) {
			this(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y);
		}
		public EditableTiled2D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y) {
			super();
			datum = _datum;
			x = _x;
			y = _y;
			i = _i;
			j = _j;
			lvl = _lvl;
		}

		@Override public Datum 								getDatum() 							{ return datum; }

		@Override public void 								setX(double _x) 					{ x = _x; }
		@Override public double 							getX() 								{ return x; }
		@Override public void 								setY(double _y) 					{ y = _y; }
		@Override public double 							getY() 								{ return y; }

		@Override public void 								setLevel(int _lvl) 					{ lvl = _lvl; }
		@Override public int 								getLevel() 							{ return lvl; }
		@Override public void 								setI(int _i) 						{ i = _i; }
		@Override public int 								getI() 								{ return i; }
		@Override public void 								setJ(int _j) 						{ j = _j; }
		@Override public int 								getJ() 								{ return j; }

		@Override public String 							toString() 							{ return toString(new DecimalFormat()); }
		@Override public String 							toString(DecimalFormat _df) 		{ return "(" + getX() + ", " + getY() + " @ " + getI() + ", " + getJ() + ", " + getLevel() + ")"; }

		@Override public GeoCoordinate.Tiled2D.Editable		clone() 							{ return new EditableTiled2D(getDatum(), getLevel(), getI(), getJ(), getX(), getY()); }

		@Override public boolean							equals(GeoCoordinate _other) 		{
			if(_other instanceof GeoCoordinate.Tiled2D) {
				GeoCoordinate.Tiled2D t2d = (GeoCoordinate.Tiled2D) _other;
				return getX() == t2d.getX() && getY() == t2d.getY() && getI() == t2d.getI() && getJ() == t2d.getJ() && getLevel() == t2d.getLevel();
			} else
				return GeoDistances.getDistanceBetween(this, _other) < 1e-2; // TODO:: 
		}
	}
	protected static class EditableTiled3D extends EditableTiled2D implements GeoCoordinate.Tiled3D.Editable {
		double z;

		public EditableTiled3D(int _lvl, int _i, int _j, double _x, double _y, double _alt) {
			this(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y, _alt);
		}
		public EditableTiled3D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y, double _alt) {
			super(_datum, _lvl, _i, _j, _x, _y);
			z = _alt;
		}

		@Override public void 								setAltitude(double _altitude) 		{ z = _altitude; }
		@Override public double 							getAltitude() 						{ return z; }

		@Override public String 							toString() 							{ return toString(new DecimalFormat()); }
		@Override public String 							toString(DecimalFormat _df) 		{ return "(" + getX() + ", " + getY() + ", " + getZ() + " @ " + getI() + ", " + getJ() + ", " + getLevel() + ")"; }

		@Override public GeoCoordinate.Tiled3D.Editable	clone() { return new EditableTiled3D(getDatum(), getLevel(), getI(), getJ(), getX(), getY(), getAltitude()); }

		@Override public boolean							equals(GeoCoordinate _other) 		{
			if(_other instanceof GeoCoordinate.Tiled3D) {
				GeoCoordinate.Tiled3D t3d = (GeoCoordinate.Tiled3D) _other;
				return getX() == t3d.getX() && getY() == t3d.getY() && getAltitude() == t3d.getAltitude() && getI() == t3d.getI() && getJ() == t3d.getJ() && getLevel() == t3d.getLevel();
			} else
				return GeoDistances.getDistanceBetween(this, _other) < 1e-2; // TODO:: 
		}
	}



	protected static class DynamicCartesian2D extends EditableCartesian2D implements GeoDynamics.Cartesian2D.Editable {
		Timestamp timestamp;
		Double    heading;
		Vector3D  velocity, acceleration;

		public DynamicCartesian2D(double _x, String _zone_x, double _y, String _zone_y) {
			this(Datum.UTM, _x, _zone_x, _y, _zone_y, null, null, null, null);
		}
		public DynamicCartesian2D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y) {
			this(_datum, _x, _zone_x, _y, _zone_y, null, null, null, null);
		}
		public DynamicCartesian2D(double _x, String _zone_x, double _y, String _zone_y, Timestamp _timestamp) {
			this(Datum.UTM, _x, _zone_x, _y, _zone_y, _timestamp, null, null, null);
		}
		public DynamicCartesian2D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y, Timestamp _timestamp) {
			this(_datum, _x, _zone_x, _y, _zone_y, _timestamp, null, null, null);
		}
		public DynamicCartesian2D(double _x, String _zone_x, double _y, String _zone_y, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(Datum.UTM, _x, _zone_x, _y, _zone_y, null, _heading, _velocity, _acceleration);
		}
		public DynamicCartesian2D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(_datum, _x, _zone_x, _y, _zone_y, null, _heading, _velocity, _acceleration);
		}
		public DynamicCartesian2D(double _x, String _zone_x, double _y, String _zone_y, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(Datum.UTM, _x, _zone_x, _y, _zone_y, _timestamp, _heading, _velocity, _acceleration);
		}
		public DynamicCartesian2D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			super(_datum, _x, _zone_x, _y, _zone_y);
			timestamp    = _timestamp;
			heading      = _heading;
			velocity     = _velocity;
			acceleration = _acceleration;
		}
		public DynamicCartesian2D(GeoCoordinate.Cartesian2D _coord, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
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

		@Override public GeoCoordinate.Cartesian2D.Editable	clone() 												{ return new DynamicCartesian2D(getDatum(), getX(), getZoneX(), getY(), getZoneY(), getTimestamp(), getHeading(), getVelocity(), getAcceleration()); }

	}
	protected static class DynamicCartesian3D extends EditableCartesian3D implements GeoDynamics.Cartesian3D.Editable {
		Timestamp timestamp;
		Double    heading;
		Vector3D  velocity, acceleration;

		public DynamicCartesian3D(double _x, String _zone_x, double _y, String _zone_y, double _alt) {
			this(Datum.UTM, _x, _zone_x, _y, _zone_y, _alt, null, null, null, null);
		}
		public DynamicCartesian3D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y, double _alt) {
			this(_datum, _x, _zone_x, _y, _zone_y, _alt, null, null, null, null);
		}
		public DynamicCartesian3D(double _x, String _zone_x, double _y, String _zone_y, double _alt, Timestamp _timestamp) {
			this(Datum.UTM, _x, _zone_x, _y, _zone_y, _alt, _timestamp, null, null, null);
		}
		public DynamicCartesian3D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y, double _alt, Timestamp _timestamp) {
			this(_datum, _x, _zone_x, _y, _zone_y, _alt, _timestamp, null, null, null);
		}
		public DynamicCartesian3D(double _x, String _zone_x, double _y, String _zone_y, double _alt, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(Datum.UTM, _x, _zone_x, _y, _zone_y, _alt, null, _heading, _velocity, _acceleration);
		}
		public DynamicCartesian3D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y, double _alt, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(_datum, _x, _zone_x, _y, _zone_y, _alt, null, _heading, _velocity, _acceleration);
		}
		public DynamicCartesian3D(double _x, String _zone_x, double _y, String _zone_y, double _alt, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(Datum.UTM, _x, _zone_x, _y, _zone_y, _alt, _timestamp, _heading, _velocity, _acceleration);
		}
		public DynamicCartesian3D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y, double _alt, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			super(_datum, _x, _zone_x, _y, _zone_y, _alt);
			timestamp    = _timestamp;
			heading      = _heading;
			velocity     = _velocity;
			acceleration = _acceleration;
		}
		public DynamicCartesian3D(GeoCoordinate.Cartesian3D _coord, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			super(_coord.getDatum(), _coord.getX(), _coord.getZoneX(), _coord.getY(), _coord.getZoneY(), _coord.getZ());
			timestamp    = _timestamp;
			heading      = _heading;
			velocity     = _velocity;
			acceleration = _acceleration;
		}

		@Override public void 								setHeading(double _heading) 							{ heading = _heading; }
		@Override public double 							getHeading() 											{ return heading; }


		@Override public void 								setVelocity(Vector3D _velocity) 						{ velocity = _velocity; }
		@Override public Vector3D 							getVelocity() 											{ return velocity; }

		@Override public void 								setAcceleration(Vector3D _acceleration) 				{ acceleration = _acceleration;  }
		@Override public Vector3D 							getAcceleration() 										{ return acceleration; }

		@Override public Timestamp 							getTimestamp() 											{ return timestamp; }

		@Override public String 							toString() 												{ return toString(new DecimalFormat()); }
		@Override public String 							toString(DecimalFormat _df) 							{ return "(" + getX() + (getZoneX() != null ? " " + getZoneX() : "") + ", " + getY() + (getZoneY() != null ? " " + getZoneY() : "") + ")"; }

		@Override public GeoCoordinate.Cartesian3D.Editable	clone() 												{ return new DynamicCartesian3D(getDatum(), getX(), getZoneX(), getY(), getZoneY(), getAltitude(), getTimestamp(), getHeading(), getVelocity(), getAcceleration()); }

	}

	protected static class DynamicSpheric2D extends EditableSpheric2D implements GeoDynamics.Spheric2D.Editable {
		Timestamp timestamp;
		Double    heading;
		Vector3D  velocity, acceleration;

		public DynamicSpheric2D(double _longitude, double _latitude) {
			this(Datum.WGS84, _longitude, _latitude);
		}
		public DynamicSpheric2D(Datum _datum, double _longitude, double _latitude) {
			super(_datum, _longitude, _latitude);
		}
		public DynamicSpheric2D(double _longitude, double _latitude, Timestamp _timestamp) {
			this(Datum.WGS84, _longitude, _latitude, _timestamp, null, null, null);
		}
		public DynamicSpheric2D(Datum _datum, double _longitude, double _latitude, Timestamp _timestamp) {
			this(_datum, _longitude, _latitude, _timestamp, null, null, null);
		}
		public DynamicSpheric2D(double _longitude, double _latitude, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(Datum.WGS84, _longitude, _latitude, null, _heading, _velocity, _acceleration);
		}
		public DynamicSpheric2D(Datum _datum, double _longitude, double _latitude, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(_datum, _longitude, _latitude, null, _heading, _velocity, _acceleration);
		}
		public DynamicSpheric2D(double _longitude, double _latitude, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(Datum.WGS84, _longitude, _latitude, _timestamp, _heading, _velocity, _acceleration);
		}
		public DynamicSpheric2D(Datum _datum, double _longitude, double _latitude, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			super(_datum, _longitude, _latitude);
			timestamp    = _timestamp;
			heading      = _heading;
			velocity     = _velocity;
			acceleration = _acceleration;
		}
		public DynamicSpheric2D(GeoCoordinate.Spheric2D _coord, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			super(_coord.getDatum(), _coord.getLongitude(), _coord.getLatitude());
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
			StringBuilder sb = new StringBuilder();
			double[] dmsLg = Angles.Degree2DMS(getLongitude());
			double[] dmsLt = Angles.Degree2DMS(getLatitude());

			sb.append((int) dmsLg[0] + "°" + (int) dmsLg[1] + "'" + _df.format(dmsLg[2]) + "''");
			sb.append(", ");
			sb.append((int) dmsLt[0] + "°" + (int) dmsLt[1] + "'" + _df.format(dmsLt[2]) + "''"); 

			return sb.toString();
		}

		@Override public GeoCoordinate.Spheric2D.Editable	clone() 												{ return new DynamicSpheric2D(getDatum(), getLongitude(), getLatitude(), getTimestamp(), getHeading(), getVelocity(), getAcceleration()); }

	}
	protected static class DynamicSpheric3D extends EditableSpheric3D implements GeoDynamics.Spheric3D.Editable {
		Timestamp timestamp;
		Double    heading;
		Vector3D  velocity, acceleration;

		public DynamicSpheric3D(double _longitude, double _latitude, double _altitude) {
			this(Datum.WGS84, _longitude, _latitude, _altitude, null, null, null, null);
		}
		public DynamicSpheric3D(Datum _datum, double _longitude, double _latitude, double _altitude) {
			this(_datum, _longitude, _latitude, _altitude, null, null, null, null);
		}
		public DynamicSpheric3D(double _longitude, double _latitude, double _altitude, Timestamp _timestamp) {
			this(Datum.WGS84, _longitude, _latitude, _altitude, _timestamp, null, null, null);
		}
		public DynamicSpheric3D(Datum _datum, double _longitude, double _latitude, double _altitude, Timestamp _timestamp) {
			this(_datum, _longitude, _latitude, _altitude, _timestamp, null, null, null);
		}
		public DynamicSpheric3D(double _longitude, double _latitude, double _altitude, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(Datum.WGS84, _longitude, _latitude, _altitude, null, _heading, _velocity, _acceleration);
		}
		public DynamicSpheric3D(Datum _datum, double _longitude, double _latitude, double _altitude, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(_datum, _longitude, _latitude, _altitude, null, _heading, _velocity, _acceleration);
		}
		public DynamicSpheric3D(double _longitude, double _latitude, double _altitude, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(Datum.WGS84, _longitude, _latitude, _altitude, _timestamp, _heading, _velocity, _acceleration);
		}
		public DynamicSpheric3D(Datum _datum, double _longitude, double _latitude, double _altitude, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			super(_datum, _longitude, _latitude, _altitude);
			timestamp    = _timestamp;
			heading      = _heading;
			velocity     = _velocity;
			acceleration = _acceleration;
		}
		public DynamicSpheric3D(GeoCoordinate.Spheric3D _coord, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
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

		@Override public GeoCoordinate.Spheric3D.Editable	clone() { return new DynamicSpheric3D(getDatum(), getLongitude(), getLatitude(), getAltitude(), getTimestamp(), getHeading(), getVelocity(), getAcceleration()); }

	};

	@Deprecated
	protected static class DynamicTiled2D extends EditableTiled2D implements GeoDynamics.Tiled2D.Editable {
		Timestamp timestamp;
		Double    heading;
		Vector3D  velocity, acceleration;

		public DynamicTiled2D(int _lvl, int _i, int _j, double _x, double _y) {
			this(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y);
		}
		public DynamicTiled2D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y) {
			super(_datum, _lvl, _i, _j, _x, _y);
		}
		public DynamicTiled2D(int _lvl, int _i, int _j, double _x, double _y, Timestamp _timestamp) {
			this(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y, _timestamp, null, null, null);
		}
		public DynamicTiled2D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y, Timestamp _timestamp) {
			this(_datum, _lvl, _i, _j, _x, _y, _timestamp, null, null, null);
		}
		public DynamicTiled2D(int _lvl, int _i, int _j, double _x, double _y, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y, null, _heading, _velocity, _acceleration);
		}
		public DynamicTiled2D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(_datum, _lvl, _i, _j, _x, _y, null, _heading, _velocity, _acceleration);
		}
		public DynamicTiled2D(int _lvl, int _i, int _j, double _x, double _y, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y, _timestamp, _heading, _velocity, _acceleration);
		}
		public DynamicTiled2D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			super(_datum, _lvl, _i, _j, _x, _y);
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
		@Override public String 							toString(DecimalFormat _df) 							{ return "(" + getX() + ", " + getY() + " @ " + getI() + ", " + getJ() + ", " + getLevel() + ")"; }

		@Override public GeoCoordinate.Tiled2D.Editable		clone() 												{ return new DynamicTiled2D(getDatum(), getLevel(), getI(), getJ(), getX(), getY(), getTimestamp(), getHeading(), getVelocity(), getAcceleration()); }

	}
	@Deprecated
	protected static class DynamicTiled3D extends EditableTiled3D implements GeoDynamics.Tiled3D.Editable {
		Timestamp timestamp;
		Double    heading;
		Vector3D  velocity, acceleration;

		public DynamicTiled3D(int _lvl, int _i, int _j, double _x, double _y, double _alt) {
			this(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y, _alt, null, null, null, null);
		}
		public DynamicTiled3D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y, double _alt) {
			this(_datum, _lvl, _i, _j, _x, _y, _alt, null, null, null, null);
		}
		public DynamicTiled3D(int _lvl, int _i, int _j, double _x, double _y, double _alt, Timestamp _timestamp) {
			this(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y, _alt, _timestamp, null, null, null);
		}
		public DynamicTiled3D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y, double _alt, Timestamp _timestamp) {
			this(_datum, _lvl, _i, _j, _x, _y, _alt, _timestamp, null, null, null);
		}
		public DynamicTiled3D(int _lvl, int _i, int _j, double _x, double _y, double _alt, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y, _alt, null, _heading, _velocity, _acceleration);
		}
		public DynamicTiled3D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y, double _alt, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(_datum, _lvl, _i, _j, _x, _y, _alt, null, _heading, _velocity, _acceleration);
		}
		public DynamicTiled3D(int _lvl, int _i, int _j, double _x, double _y, double _alt, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y, _alt, _timestamp, _heading, _velocity, _acceleration);
		}
		public DynamicTiled3D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y, double _alt, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
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

		@Override public GeoDynamics.Tiled3D.Editable		clone() 												{ return new DynamicTiled3D(getDatum(), getLevel(), getI(), getJ(), getX(), getY(), getZ(), getTimestamp(), getHeading(), getVelocity(), getAcceleration()); }

	};


	public static GeoCoordinate.Cartesian2D.Editable	newGeoCartesian2D(double _x, String _zone_x, double _y, String _zone_y) {
		return new EditableCartesian2D(_x, _zone_x, _y, _zone_y);
	}
	public static GeoCoordinate.Cartesian2D.Editable	newGeoCartesian2D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y) {
		return new EditableCartesian2D(_datum, _x, _zone_x, _y, _zone_y);
	}
	public static GeoDynamics.Cartesian2D.Editable		newGeoCartesian2D(double _x, String _zone_x, double _y, String _zone_y, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new DynamicCartesian2D(_x, _zone_x, _y, _zone_y, _timestamp, _heading, _velocity, _acceleration);
	}
	public static GeoDynamics.Cartesian2D.Editable		newGeoCartesian2D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new DynamicCartesian2D(_datum, _x, _zone_x, _y, _zone_y, _timestamp, _heading, _velocity, _acceleration);
	}
	public static GeoDynamics.Cartesian2D.Editable		newGeoCartesian2D(GeoCoordinate.Cartesian2D _coords, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new DynamicCartesian2D(_coords.getDatum(), _coords.getX(), _coords.getZoneX(), _coords.getY(), _coords.getZoneY(), _timestamp, _heading, _velocity, _acceleration);
	}


	public static GeoCoordinate.Cartesian3D.Editable	newGeoCartesian3D(double _x, String _zone_x, double _y, String _zone_y, double _alt) {
		return new EditableCartesian3D(_x, _zone_x, _y, _zone_y, _alt);
	}
	public static GeoCoordinate.Cartesian3D.Editable	newGeoCartesian3D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y, double _alt) {
		return new EditableCartesian3D(_datum, _x, _zone_x, _y, _zone_y, _alt);
	}
	public static GeoDynamics.Cartesian3D.Editable		newGeoCartesian3D(double _x, String _zone_x, double _y, double _alt, String _zone_y, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new DynamicCartesian3D(_x, _zone_x, _y, _zone_y, _alt, _timestamp, _heading, _velocity, _acceleration);
	}
	public static GeoDynamics.Cartesian3D.Editable		newGeoCartesian3D(Datum _datum, double _x, String _zone_x, double _y, double _alt, String _zone_y, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new DynamicCartesian3D(_datum, _x, _zone_x, _y, _zone_y, _alt, _timestamp, _heading, _velocity, _acceleration);
	}
	public static GeoDynamics.Cartesian3D.Editable		newGeoCartesian3D(GeoCoordinate.Cartesian3D _coords, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new DynamicCartesian3D(_coords.getDatum(), _coords.getX(), _coords.getZoneX(), _coords.getY(), _coords.getZoneY(), _coords.getZ(), _timestamp, _heading, _velocity, _acceleration);
	}



	public static GeoCoordinate.Spheric2D.Editable		newGeoSpheric2D(double _lg, double _lt) {
		return new EditableSpheric2D(Datum.WGS84, _lg, _lt);
	}
	public static GeoCoordinate.Spheric2D.Editable		newGeoSpheric2D(Datum _datum, double _lg, double _lt) {
		return new EditableSpheric2D(_datum, _lg, _lt);
	}
	public static GeoDynamics.Spheric2D.Editable		newGeoSpheric2D(double _lg, double _lt, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new DynamicSpheric2D(_lg, _lt, _timestamp, _heading, _velocity, _acceleration);
	}
	public static GeoDynamics.Spheric2D.Editable		newGeoSpheric2D(Datum _datum, double _lg, double _lt, String _zone_y, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new DynamicSpheric2D(_datum, _lg, _lt, _timestamp, _heading, _velocity, _acceleration);
	}
	public static GeoDynamics.Spheric2D.Editable		newGeoSpheric2D(GeoCoordinate.Spheric2D _coords, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new DynamicSpheric2D(_coords.getDatum(), _coords.getLongitude(), _coords.getLatitude(), _timestamp, _heading, _velocity, _acceleration);
	}


	public static GeoCoordinate.Spheric3D.Editable		newGeoSpheric3D(double _lg, double _lt, double _alt) {
		return new EditableSpheric3D(Datum.WGS84, _lg, _lt, _alt);
	}
	public static GeoCoordinate.Spheric3D.Editable		newGeoSpheric3D(Datum _datum, double _lg, double _lt, double _alt) {
		return new EditableSpheric3D(_datum, _lg, _lt, _alt);
	}
	public static GeoDynamics.Spheric3D.Editable		newGeoSpheric3D(double _lg, double _lt, double _alt, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new DynamicSpheric3D(_lg, _lt, _alt, _timestamp, _heading, _velocity, _acceleration);
	}
	public static GeoDynamics.Spheric3D.Editable		newGeoSpheric3D(Datum _datum, double _lg, double _lt, double _alt, String _zone_y, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new DynamicSpheric3D(_datum, _lg, _lt, _alt, _timestamp, _heading, _velocity, _acceleration);
	}
	public static GeoDynamics.Spheric3D.Editable		newGeoSpheric3D(GeoCoordinate.Spheric3D _coords, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new DynamicSpheric3D(_coords.getDatum(), _coords.getLongitude(), _coords.getLatitude(), _coords.getAltitude(), _timestamp, _heading, _velocity, _acceleration);
	}


	public static GeoCoordinate.Tiled2D.Editable		newGeoTiled2D(int _lvl, int _i, int _j, double _x, double _y) {
		return new EditableTiled2D(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y);
	}
	public static GeoCoordinate.Tiled2D.Editable		newGeoTiled2D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y) {
		return new EditableTiled2D(_datum, _lvl, _i, _j, _x, _y);
	}
	public static GeoDynamics.Tiled2D.Editable			newGeoTiled2D(int _lvl, int _i, int _j, double _x, double _y, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new DynamicTiled2D(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y, _timestamp, _heading, _velocity, _acceleration);
	}
	public static GeoDynamics.Tiled2D.Editable			newGeoTiled2D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new DynamicTiled2D(_datum, _lvl, _i, _j, _x, _y, _timestamp, _heading, _velocity, _acceleration);
	}
	public static GeoDynamics.Tiled2D.Editable			newGeoTiled2D(GeoCoordinate.Tiled2D _coord, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new DynamicTiled2D(_coord.getDatum(), _coord.getLevel(), _coord.getI(), _coord.getJ(), _coord.getX(), _coord.getY(), _timestamp, _heading, _velocity, _acceleration);
	}


	public static GeoCoordinate.Tiled3D.Editable		newGeoTiled3D(int _lvl, int _i, int _j, double _x, double _y, double _alt) {
		return new EditableTiled3D(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y, _alt);
	}
	public static GeoCoordinate.Tiled3D.Editable		newGeoTiled3D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y, double _alt) {
		return new EditableTiled3D(_datum, _lvl, _i, _j, _x, _y, _alt);
	}
	public static GeoDynamics.Tiled3D.Editable			newGeoTiled3D(int _lvl, int _i, int _j, double _x, double _y, double _alt, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new DynamicTiled3D(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y, _alt, _timestamp, _heading, _velocity, _acceleration);
	}
	public static GeoDynamics.Tiled3D.Editable			newGeoTiled3D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y, double _alt, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new DynamicTiled3D(_datum, _lvl, _i, _j, _x, _y, _alt, _timestamp, _heading, _velocity, _acceleration);
	}
	public static GeoDynamics.Tiled3D.Editable			newGeoTiled3D(GeoCoordinate.Tiled3D _coord, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new DynamicTiled3D(_coord.getDatum(), _coord.getLevel(), _coord.getI(), _coord.getJ(), _coord.getX(), _coord.getY(), _coord.getZ(), _timestamp, _heading, _velocity, _acceleration);
	}




	public static GeoCoordinate.Spheric3D.Editable 		newWGS84() {
		return newWGS84(Double.NaN, Double.NaN, Double.NaN);
	}
	public static GeoCoordinate.Spheric3D.Editable		newWGS84(final double _lg, final double _lt) {
		return newWGS84(_lg, _lt, 0.0);
	}
	public static GeoCoordinate.Spheric3D.Editable		newWGS84(final double _lg, final double _lt, final double _alt) {
		return newGeoSpheric3D(_lg, _lt, _alt);
	}
	public static GeoCoordinate.Spheric3D.Editable 		newWGS84(final Point2D _pt) {
		return newWGS84(_pt.getX(), _pt.getY());
	}
	public static GeoCoordinate.Spheric3D.Editable 		newWGS84(final Point3D _pt) {
		return newWGS84(_pt.getX(), _pt.getY(), _pt.getZ());
	}


	public static GeoCoordinate.Cartesian2D.Editable 	newLambert93(double _x, double _y) {
		return newGeoCartesian2D(Datum.Lambert93, _x, null, _y, null);
	}
	public static GeoCoordinate.Cartesian3D.Editable 	newLambert93(double _x, double _y, double _alt) {
		return newGeoCartesian3D(Datum.Lambert93, _x, null, _y, null, _alt);
	}


	
	public static GeoCoordinate.Cartesian3D.Editable 	newUTM() {
		return newUTM(Double.NaN, null, Double.NaN, null, Double.NaN);
	}
	public static GeoCoordinate.Cartesian3D.Editable 	newUTM(final double _x, final double _y) {
		return newUTM(_x, "31", _y, "U", 0.0);
	}
	public static GeoCoordinate.Cartesian3D.Editable 	newUTM(final double _x, final double _y, final double _alt) {
		return newUTM(_x, "31", _y, "U", _alt);
	}
	public static GeoCoordinate.Cartesian3D.Editable 	newUTM(final double _x, final String _zone_x, final double _y, final String _zone_y) {
		return newUTM(_x, _zone_x, _y, _zone_y, 0.0);
	}
	public static GeoCoordinate.Cartesian3D.Editable  	newUTM(final double _x, final String _zone_x, final double _y, final String _zone_y, double _alt) {
		return newGeoCartesian3D(Datum.UTM, _x, _zone_x, _y, _zone_y, _alt);
	}
	public static GeoCoordinate.Cartesian3D.Editable 	newUTM(final Point2D _pt) {
		return newUTM(_pt.getX(), "31", _pt.getY(), "U", 0.0);
	}
	public static GeoCoordinate.Cartesian3D.Editable 	newUTM(final Point2D _pt, final String _zone_x, final String _zone_y) {
		return newUTM(_pt.getX(), _zone_x, _pt.getY(), _zone_y, 0.0);
	}
	public static GeoCoordinate.Cartesian3D.Editable 	newUTM(final Point3D _pt) {
		return newUTM(_pt.getX(), "31", _pt.getY(), "U", _pt.getZ());
	}
	public static GeoCoordinate.Cartesian3D.Editable 	newUTM(final Point3D _pt, final String _zone_x, final String _zone_y) {
		return newUTM(_pt.getX(), _zone_x, _pt.getY(), _zone_y, _pt.getZ());
	}



	public static GeoCoordinate.Cartesian2D 			newMGRS(double _x, String _zone_x, double _y, String _zone_y) {
		return newGeoCartesian3D(Datum.MGRS, _x, _zone_x, _y, _zone_y, 0.0);
	}
	public static GeoCoordinate.Cartesian3D				newMGRS(double _x, String _zone_x, double _y, String _zone_y, double _alt) {
		return newGeoCartesian3D(Datum.MGRS, _x, _zone_x, _y, _zone_y, _alt);
	}



	public static GeoCoordinate.Tiled3D.Editable 		newTiled(int _lvl, int _i, int _j, double _x, double _y) {
		return newTiled(_lvl, _i, _j, _x, _y, 0.0);
	}
	public static GeoCoordinate.Tiled3D.Editable 		newTiled(int _lvl, int _i, int _j, double _x, double _y, double _alt) {
		return newGeoTiled3D(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y, _alt);
	}

	public static GeoDynamics 							newDynamics(GeoCoordinate _pos) {
		return new DynamicSpheric2D(_pos.asWGS84(), Timestamps.epoch(), 0d, Vectors.zero3(), Vectors.zero3());
	}
	public static GeoDynamics 							newDynamics(GeoCoordinate _pos, Timestamp _of, double _heading, Vector3D _vel, Vector3D _acc) {
		return new DynamicSpheric2D(_pos.asWGS84(), _of, _heading, _vel, _acc);
	}


	public static GeoDynamics							computeDynamics(Timestamp _oldTime, GeoCoordinate _oldPos, Timestamp _newTime, GeoCoordinate _newPos) {
		if(_oldPos == null || _newPos == null)
			return null;

		double   h = GeoCoordinates.computeHeading(_oldPos, _newPos);
		Vector3D v = GeoCoordinates.computeVelocity(_oldTime, _oldPos, _newTime, _newPos);

		return new DynamicSpheric2D(_newPos.asWGS84(), _newTime, h, v, null);
	}
	public static GeoDynamics							computeDynamics(Timestamp _oldTime, GeoCoordinate _oldPos, Vector3D _oldVel, Timestamp _newTime, GeoCoordinate _newPos, Vector3D _newVel) {
		if(_oldPos == null || _newPos == null)
			return null;

		double   h = GeoCoordinates.computeHeading(_oldPos, _newPos);
		Vector3D v = GeoCoordinates.computeVelocity(_oldTime, _oldPos, _newTime, _newPos);
		Vector3D a = GeoCoordinates.computeAcceleration(_oldTime, _oldPos, _oldVel, _newTime, _newPos, _newVel);

		return new DynamicSpheric2D(_newPos.asWGS84(), _newTime, h, v, a);
	}


	
	


	public static GeoCoordinate 						interpolate(GeoCoordinate _p0, GeoCoordinate _p1, double _t) {
		if(_p0.getDatum() != _p1.getDatum())
			throw new RuntimeException("Can't operate interpolation between different CRS");
		if(_p0.getDatum() != Datum.UTM && _p0.getDatum() != Datum.WGS84)
			throw new NotYetImplementedException("Datum is " + _p0.getDatum());

		double x = (1.0 - _t) * _p0.asUTM().getX() + _t * _p1.asUTM().getX();
		double y = (1.0 - _t) * _p0.asUTM().getY() + _t * _p1.asUTM().getY();
//		double z = (1.0 - _t) * _p0.asUTM().getZ() + _t * _p1.asUTM().getZ();

//		return newUTM(x, _p0.asUTM().getZoneX(), y, _p0.asUTM().getZoneY(), z);
		return newUTM(x, _p0.asUTM().getZoneX(), y, _p0.asUTM().getZoneY());
	}


	/**
	 * CONVERSION / REFERENTIAL
	 */
	public static GeoCoordinate.Spheric2D   			asWGS84(GeoCoordinate _c) {
		switch(_c.getDatum()) {
		case WGS84:		return (GeoCoordinate.Spheric2D) _c;
		case UTM:		return utmConverter.toLatLong(_c);
		case Lambert93:	return l93Converter.toLatLong(_c);
		case MGRS:		return mgrsConverter.toLatLong(_c);
		default:		throw new IllegalArgumentException();		
		}
	}
	public static GeoCoordinate.Cartesian2D 			asUTM(GeoCoordinate _c) {
		switch(_c.getDatum()) {
		case WGS84:		return utmConverter.fromLatLong(_c);
		case UTM:		return (GeoCoordinate.Cartesian2D) _c;
		case Lambert93:	return utmConverter.fromLatLong(l93Converter.toLatLong(_c));
		case MGRS:		return utmConverter.fromLatLong(mgrsConverter.toLatLong(_c));
		default:		throw new IllegalArgumentException();		
		}
	}
	public static GeoCoordinate 						asLambert93(GeoCoordinate _c) {
		switch(_c.getDatum()) {
		case WGS84:		return l93Converter.fromLatLong(_c);
		case UTM:		return l93Converter.fromLatLong(utmConverter.toLatLong(_c));
		case Lambert93:	return _c;
		case MGRS:		return l93Converter.fromLatLong(mgrsConverter.toLatLong(_c));
		default:		throw new IllegalArgumentException();		
		}
	}


	/**
	 * DYNAMICS
	 */
	public static double 								computeDistance(GeoCoordinate _from, GeoCoordinate _to) {
		return computeDistance(_from, _to);
	}
	public static double 								computeDistance(GeoCoordinate _from, GeoCoordinate _to, boolean _useEllipsoid) {
		return 0;
	}

	public static double   								computeHeading(GeoCoordinate _from, GeoCoordinate _to) {
		double A = _to.asUTM().getY() - _from.asUTM().getY();
		double O = _to.asUTM().getX() - _from.asUTM().getX();
		
		double H = A == 0 && O == 0 ? 0 : (double) (90 - Angles.Radian2Degree(Math.atan2(A, O)));

		return H;
	}
	public static Vector3D 								computeVelocity(Timestamp _oldTime, GeoCoordinate _from, Timestamp _newTime, GeoCoordinate _to) {
		if(_oldTime == null || _newTime == null || _from == null || _to == null)
			throw new IllegalArgumentException();

		GeoCoordinate.Cartesian2D p0 = _from.asUTM();
		GeoCoordinate.Cartesian2D p1 = _to.asUTM();

		double ds = Math.sqrt( (p1.getX()-p0.getX())*(p1.getX()-p0.getX()) + (p1.getY()-p0.getY())*(p1.getY()-p0.getY()) );
		double dt = _newTime.delta(_oldTime);// ms
		double v  = ( ds / dt ) * 1e3;

		return new DoubleVector3D(v, 0.0, 0.0);
	}
	public static Vector3D 								computeAcceleration(Timestamp _oldTime, GeoCoordinate _from, Vector3D _oldVel, Timestamp _newTime, GeoCoordinate _to, Vector3D _newVel) {
		if(_oldTime == null || _newTime == null || _from == null || _to == null || _oldVel == null || _newVel == null)
			throw new IllegalArgumentException("No timestamp for given Coordinate");

		Vector3D v0 = _oldVel, v1 = _newVel;

		double ds = Math.sqrt( (v1.getX()-v0.getX())*(v1.getX()-v0.getX()) + (v1.getY()-v0.getY())*(v1.getY()-v0.getY()) );
		double dt = _newTime.delta(_oldTime);// / 1000.0;
		double a  = (ds / dt) * 3600;

		return new DoubleVector3D(a, 0.0, 0.0);
	}

	public static GeoCoordinate plusUTM(GeoCoordinate _p, double _dx, double _dy) {
		Point2D P = _p.asUTM().as2D();
		return newUTM(P.plus(_dx, _dy));
	}


}

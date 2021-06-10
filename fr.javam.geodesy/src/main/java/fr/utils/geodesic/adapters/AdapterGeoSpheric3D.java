package fr.utils.geodesic.adapters;

import java.text.DecimalFormat;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.referential.Datum;
import fr.geodesic.utils.GeoCoordinates;
import fr.utils.geodesic.Angles;

public class AdapterGeoSpheric3D extends AdapterGeoSpheric2D implements GeoCoordinate.Spheric3D.Editable {
	double altitude;

	public AdapterGeoSpheric3D(double _longitude, double _latitude, double _altitude) {
		this(Datum.WGS84, _longitude, _latitude, _altitude);
	}
	public AdapterGeoSpheric3D(Datum _datum, double _longitude, double _latitude, double _altitude) {
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

	@Override public GeoCoordinate.Spheric3D.Editable	clone() { return new AdapterGeoSpheric3D(getDatum(), getLongitude(), getLatitude(), getAltitude()); }

	@Override public boolean							equals(GeoCoordinate _other) 		{
		if(_other instanceof GeoCoordinate.Spheric3D) {
			GeoCoordinate.Spheric3D s3d = (GeoCoordinate.Spheric3D) _other;
			return getLongitude() == s3d.getLongitude() && getLatitude() == s3d.getLatitude() && getAltitude() == s3d.getAltitude();
		} else
			return GeoCoordinates.computeDistance(this, _other) < 1e-2; // TODO:: 
	}

}

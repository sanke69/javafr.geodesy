package fr.utils.geodesic.adapters;

import java.text.DecimalFormat;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.referential.Datum;
import fr.geodesic.utils.GeoCoordinates;
import fr.utils.geodesic.Angles;

public class AdapterGeoSpheric2D implements GeoCoordinate.Spheric2D.Editable {
	Datum  datum;
	double lg, lt;

	public AdapterGeoSpheric2D(double _longitude, double _latitude) {
		this(Datum.WGS84, _longitude, _latitude);
	}
	public AdapterGeoSpheric2D(Datum _datum, double _longitude, double _latitude) {
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

	@Override public GeoCoordinate.Spheric2D.Editable	clone() 							{ return new AdapterGeoSpheric2D(getLongitude(), getLatitude()); }

	@Override public boolean							equals(GeoCoordinate _other) 		{
		if(_other instanceof GeoCoordinate.Spheric2D) {
			GeoCoordinate.Spheric2D s2d = (GeoCoordinate.Spheric2D) _other;
			return getLongitude() == s2d.getLongitude() && getLatitude() == s2d.getLatitude();
		} else
			return GeoCoordinates.computeDistance(this, _other) < 1e-2; // TODO:: 
	}

}

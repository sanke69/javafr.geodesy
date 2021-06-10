package fr.utils.geodesic.adapters;

import java.text.DecimalFormat;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.referential.Datum;
import fr.geodesic.utils.GeoCoordinates;

public class AdapterGeoCartesian3D extends AdapterGeoCartesian2D implements GeoCoordinate.Cartesian3D.Editable {
	double altitude;

	public AdapterGeoCartesian3D(double _x, String _zone_x, double _y, String _zone_y, double _altitude) {
		super(_x, _zone_x, _y, _zone_y);
		altitude = _altitude;
	}
	public AdapterGeoCartesian3D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y, double _altitude) {
		super(_datum, _x, _zone_x, _y, _zone_y);
		altitude = _altitude;
	}

	@Override public void 								setAltitude(double _altitude) 		{ altitude = _altitude; }
	@Override public double 							getAltitude() 						{ return altitude; }

	@Override public String 							toString() 							{ return toString(new DecimalFormat()); }
	@Override public String 							toString(DecimalFormat _df) 		{ return "(" + getX() + (getZoneX() != null ? " " + getZoneX() : "") + ", " + getY() + (getZoneY() != null ? " " + getZoneY() : "") + ", " + getZ() + ")"; }

	@Override public GeoCoordinate.Cartesian3D.Editable	clone() 							{ return new AdapterGeoCartesian3D(getX(), getZoneX(), getY(), getZoneY(), getAltitude()); }

	@Override public boolean							equals(GeoCoordinate _other) 		{
		if(_other instanceof GeoCoordinate.Cartesian3D) {
			GeoCoordinate.Cartesian3D c3d = (GeoCoordinate.Cartesian3D) _other;
			return getX() == c3d.getX() && getY() == c3d.getY() && getZ() == c3d.getZ();
		} else
			return GeoCoordinates.computeDistance(this, _other) < 1e-2; // TODO:: 
	}
}
	
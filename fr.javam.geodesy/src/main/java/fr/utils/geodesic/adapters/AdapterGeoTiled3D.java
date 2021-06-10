package fr.utils.geodesic.adapters;

import java.text.DecimalFormat;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.referential.Datum;
import fr.geodesic.utils.GeoCoordinates;

public class AdapterGeoTiled3D extends AdapterGeoTiled2D implements GeoCoordinate.Tiled3D.Editable {
	double z;

	public AdapterGeoTiled3D(int _lvl, int _i, int _j, double _x, double _y, double _alt) {
		this(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y, _alt);
	}
	public AdapterGeoTiled3D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y, double _alt) {
		super(_datum, _lvl, _i, _j, _x, _y);
		z = _alt;
	}

	@Override public void 								setAltitude(double _altitude) 		{ z = _altitude; }
	@Override public double 							getAltitude() 						{ return z; }

	@Override public String 							toString() 							{ return toString(new DecimalFormat()); }
	@Override public String 							toString(DecimalFormat _df) 		{ return "(" + getX() + ", " + getY() + ", " + getZ() + " @ " + getI() + ", " + getJ() + ", " + getLevel() + ")"; }

	@Override public GeoCoordinate.Tiled3D.Editable		clone() 							{ return new AdapterGeoTiled3D(getDatum(), getLevel(), getI(), getJ(), getX(), getY(), getAltitude()); }

	@Override public boolean							equals(GeoCoordinate _other) 		{
		if(_other instanceof GeoCoordinate.Tiled3D) {
			GeoCoordinate.Tiled3D t3d = (GeoCoordinate.Tiled3D) _other;
			return getX() == t3d.getX() && getY() == t3d.getY() && getAltitude() == t3d.getAltitude() && getI() == t3d.getI() && getJ() == t3d.getJ() && getLevel() == t3d.getLevel();
		} else
			return GeoCoordinates.computeDistance(this, _other) < 1e-2; // TODO:: 
	}

}

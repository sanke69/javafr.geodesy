package fr.utils.geodesic.adapters;

import java.text.DecimalFormat;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.referential.Datum;
import fr.geodesic.utils.GeoCoordinates;

public class AdapterGeoCartesian2D implements GeoCoordinate.Cartesian2D.Editable {
	Datum  datum;
	double x, y;
	String zoneX, zoneY;

	public AdapterGeoCartesian2D(double _x, String _zone_x, double _y, String _zone_y) {
		this(Datum.UTM, _x, _zone_x, _y, _zone_y);
	}
	public AdapterGeoCartesian2D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y) {
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

	@Override public GeoCoordinate.Cartesian2D.Editable	clone() 												{ return new AdapterGeoCartesian2D(getDatum(), getX(), getZoneX(), getY(), getZoneY()); }

	@Override public boolean							equals(GeoCoordinate _other) 							{
		if(_other instanceof GeoCoordinate.Cartesian2D) {
			GeoCoordinate.Cartesian2D c2d = (GeoCoordinate.Cartesian2D) _other;
			return getX() == c2d.getX() && getY() == c2d.getY();
		} else
			return GeoCoordinates.computeDistance(this, _other) < 1e-2; // TODO:: 
	}

}
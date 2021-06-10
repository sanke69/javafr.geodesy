package fr.utils.geodesic.adapters;

import java.text.DecimalFormat;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.referential.Datum;
import fr.geodesic.utils.GeoCoordinates;

public class AdapterGeoTiled2D implements GeoCoordinate.Tiled2D.Editable {
	Datum  datum;
	double x, y;
	int    i, j, lvl;

	public AdapterGeoTiled2D(int _lvl, int _i, int _j, double _x, double _y) {
		this(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y);
	}
	public AdapterGeoTiled2D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y) {
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

	@Override public GeoCoordinate.Tiled2D.Editable		clone() 							{ return new AdapterGeoTiled2D(getDatum(), getLevel(), getI(), getJ(), getX(), getY()); }

	@Override public boolean							equals(GeoCoordinate _other) 		{
		if(_other instanceof GeoCoordinate.Tiled2D) {
			GeoCoordinate.Tiled2D t2d = (GeoCoordinate.Tiled2D) _other;
			return getX() == t2d.getX() && getY() == t2d.getY() && getI() == t2d.getI() && getJ() == t2d.getJ() && getLevel() == t2d.getLevel();
		} else
			return GeoCoordinates.computeDistance(this, _other) < 1e-2; // TODO:: 
	}

}

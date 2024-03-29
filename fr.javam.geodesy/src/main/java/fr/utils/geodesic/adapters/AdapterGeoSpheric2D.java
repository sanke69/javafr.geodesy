/**
 * Copyright (C) 2007-?XYZ Steve PECHBERTI <steve.pechberti@laposte.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
**/
package fr.utils.geodesic.adapters;

import java.text.DecimalFormat;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.referential.Datum;
import fr.geodesic.utils.GeoCoordinates;
import fr.java.maths.Angles;

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

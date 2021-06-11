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
import fr.geodesic.api.GeoDynamics;
import fr.geodesic.api.referential.Datum;
import fr.java.lang.properties.Timestamp;
import fr.java.math.algebra.vector.generic.Vector3D;
import fr.utils.geodesic.Angles;

public class AdapterDynamicSpheric2D extends AdapterGeoSpheric2D implements GeoDynamics.Spheric2D.Editable {
		Timestamp timestamp;
		Double    heading;
		Vector3D  velocity, acceleration;

		public AdapterDynamicSpheric2D(double _longitude, double _latitude) {
			this(Datum.WGS84, _longitude, _latitude);
		}
		public AdapterDynamicSpheric2D(Datum _datum, double _longitude, double _latitude) {
			super(_datum, _longitude, _latitude);
		}
		public AdapterDynamicSpheric2D(double _longitude, double _latitude, Timestamp _timestamp) {
			this(Datum.WGS84, _longitude, _latitude, _timestamp, null, null, null);
		}
		public AdapterDynamicSpheric2D(Datum _datum, double _longitude, double _latitude, Timestamp _timestamp) {
			this(_datum, _longitude, _latitude, _timestamp, null, null, null);
		}
		public AdapterDynamicSpheric2D(double _longitude, double _latitude, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(Datum.WGS84, _longitude, _latitude, null, _heading, _velocity, _acceleration);
		}
		public AdapterDynamicSpheric2D(Datum _datum, double _longitude, double _latitude, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(_datum, _longitude, _latitude, null, _heading, _velocity, _acceleration);
		}
		public AdapterDynamicSpheric2D(double _longitude, double _latitude, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(Datum.WGS84, _longitude, _latitude, _timestamp, _heading, _velocity, _acceleration);
		}
		public AdapterDynamicSpheric2D(Datum _datum, double _longitude, double _latitude, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			super(_datum, _longitude, _latitude);
			timestamp    = _timestamp;
			heading      = _heading;
			velocity     = _velocity;
			acceleration = _acceleration;
		}
		public AdapterDynamicSpheric2D(GeoCoordinate.Spheric2D _coord, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
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

		@Override public GeoCoordinate.Spheric2D.Editable	clone() 												{ return new AdapterDynamicSpheric2D(getDatum(), getLongitude(), getLatitude(), getTimestamp(), getHeading(), getVelocity(), getAcceleration()); }

	}
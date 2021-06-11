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

public class AdapterDynamicTiled2D extends AdapterGeoTiled2D implements GeoDynamics.Tiled2D.Editable {
		Timestamp timestamp;
		Double    heading;
		Vector3D  velocity, acceleration;

		public AdapterDynamicTiled2D(int _lvl, int _i, int _j, double _x, double _y) {
			this(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y);
		}
		public AdapterDynamicTiled2D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y) {
			super(_datum, _lvl, _i, _j, _x, _y);
		}
		public AdapterDynamicTiled2D(int _lvl, int _i, int _j, double _x, double _y, Timestamp _timestamp) {
			this(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y, _timestamp, null, null, null);
		}
		public AdapterDynamicTiled2D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y, Timestamp _timestamp) {
			this(_datum, _lvl, _i, _j, _x, _y, _timestamp, null, null, null);
		}
		public AdapterDynamicTiled2D(int _lvl, int _i, int _j, double _x, double _y, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y, null, _heading, _velocity, _acceleration);
		}
		public AdapterDynamicTiled2D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(_datum, _lvl, _i, _j, _x, _y, null, _heading, _velocity, _acceleration);
		}
		public AdapterDynamicTiled2D(int _lvl, int _i, int _j, double _x, double _y, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
			this(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y, _timestamp, _heading, _velocity, _acceleration);
		}
		public AdapterDynamicTiled2D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y, Timestamp _timestamp, Double _heading, Vector3D _velocity, Vector3D _acceleration) {
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

		@Override public GeoCoordinate.Tiled2D.Editable		clone() 												{ return new AdapterDynamicTiled2D(getDatum(), getLevel(), getI(), getJ(), getX(), getY(), getTimestamp(), getHeading(), getVelocity(), getAcceleration()); }

	}
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
package fr.geodesic.api;

import fr.geodesic.api.referential.Datum;
import fr.geodesic.utils.GeoCoordinates;
import fr.java.lang.exceptions.InvalidArgumentException;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.topology.Coordinate;
import fr.java.math.topology.CoordinateSystem;
import fr.java.patterns.tiled.TileCoordinate;
import fr.utils.geodesic.Points;

/**
 * Available and defined for Earth (Planet 0)
 * 
 * @author sanke
 *
 */
public interface GeoCoordinate extends Coordinate {
	public static final GeoCoordinate NaN = null;

	// Default Converter: from & to WGS84
	public interface Converter {
	
		public GeoCoordinate.Cartesian2D 	fromLatLong(GeoCoordinate _coord);
		public GeoCoordinate.Spheric2D 		toLatLong(GeoCoordinate _coord);
	
	}

	public interface Cartesian2D extends GeoCoordinate, 			Coordinate.Cartesian2D {
		@Override default CoordinateSystem 		getCoordinateSystem() 		{ return Coordinate.Cartesian2D.super.getCoordinateSystem(); }

		public interface Editable extends GeoCoordinate.Cartesian2D, Coordinate.Cartesian2D.Editable {

			public void							setZoneX(String _zone);
			public void							setZoneY(String _zone);
		}

		public String 							getZoneX();
		public String 							getZoneY();

	}
	public interface Cartesian3D extends GeoCoordinate.Cartesian2D, Coordinate.Cartesian3D {
			@Override default CoordinateSystem 	getCoordinateSystem() 		{ return Coordinate.Cartesian3D.super.getCoordinateSystem(); }

		public interface Editable extends GeoCoordinate.Cartesian3D, GeoCoordinate.Cartesian2D.Editable, Coordinate.Cartesian3D.Editable {
			@Override default CoordinateSystem 	getCoordinateSystem() { return GeoCoordinate.Cartesian3D.super.getCoordinateSystem(); }

			@Override public default void		setZ(double _altitude) 		{ setAltitude(_altitude); }
			public void							setAltitude(double _altitude);
			
		}

		@Override public default double			getZ() 						{ return getAltitude(); }
		public double							getAltitude();

	}
	public interface Spheric2D   extends GeoCoordinate, 			Coordinate.Spheric2D {
		@Override default CoordinateSystem 		getCoordinateSystem() 		{ return Coordinate.Spheric2D.super.getCoordinateSystem(); }
	
		public interface Editable extends GeoCoordinate.Spheric2D, Coordinate.Spheric2D.Editable {
			@Override public default void 		setRho(double _rho) 		{ setLongitude(_rho); }
			@Override public default void 		setTheta(double _theta) 	{ setLatitude(_theta); }
			public void 						setLongitude(double _lg);
			public void 						setLatitude(double _lt);
	
		}

		@Override public default double 		getRho() 					{ return getLongitude(); }
		@Override public default double 		getTheta() 					{ return getLatitude(); }
		public double 							getLongitude();
		public double 							getLatitude();

	}
	public interface Spheric3D   extends GeoCoordinate.Spheric2D, 	Coordinate.Spheric3D {
		@Override default CoordinateSystem 		getCoordinateSystem() 		{ return Coordinate.Spheric3D.super.getCoordinateSystem(); }

		public interface Editable extends GeoCoordinate.Spheric3D, GeoCoordinate.Spheric2D.Editable, Coordinate.Spheric3D.Editable {
			@Override public default void 		setRadius(double _radius) 	{ setAltitude(_radius); }
			public void 						setAltitude(double _alt);
		}

		@Override public default double 		getRadius() 				{ return getAltitude(); }
		public double 							getAltitude();
	

	}
	public interface Tiled2D     extends GeoCoordinate, 			TileCoordinate {
		@Override default CoordinateSystem getCoordinateSystem() { return TileCoordinate.super.getCoordinateSystem(); }
	
		public interface Editable extends GeoCoordinate.Tiled2D, TileCoordinate.Editable { }

	}
	public interface Tiled3D     extends GeoCoordinate.Tiled2D, 	Coordinate.Cartesian3D {
		@Override default CoordinateSystem getCoordinateSystem() { return GeoCoordinate.Tiled2D.super.getCoordinateSystem(); }
	
		public interface Editable extends GeoCoordinate.Tiled3D, GeoCoordinate.Tiled2D.Editable, Coordinate.Cartesian3D.Editable {

			@Override public default void		setZ(double _altitude) { setAltitude(_altitude); }
			public void 						setAltitude(double _altitude);

		}

		@Override public default double			getZ() { return getAltitude(); }
		public double 							getAltitude();

	}

	public Datum 									getDatum();
	@Override
	public default CoordinateSystem					getCoordinateSystem() 	{ return getDatum().getCoordinateSystem(); }

	public default GeoCoordinate.Spheric2D			asWGS84() {
		return GeoCoordinates.asWGS84(this);
	}
	public default GeoCoordinate.Cartesian2D 		asUTM() {
		return GeoCoordinates.asUTM(this);
	}

	@Deprecated
	public default Point2D 							as2D() {
		if(this instanceof Cartesian2D) {
			return Points.of(((Cartesian2D) this).getX(), ((Cartesian2D) this).getY());
		} else if(this instanceof Angular2D) {
			return Points.of(((Angular2D) this).getRho(), ((Angular2D) this).getTheta());
		} else if(this instanceof Spheric2D) {
			return Points.of(((Spheric2D) this).getLongitude(), ((Spheric2D) this).getLatitude());
		} else if(this instanceof Tiled2D) {
			return Points.of(((Tiled2D) this).getX(), ((Tiled2D) this).getY());
		} else
			throw new InvalidArgumentException("");
	}

	public boolean		 							equals(GeoCoordinate _other);
	public GeoCoordinate 							clone();

}

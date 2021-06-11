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
package fr.geodesic.utils;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.GeoDynamics;
import fr.geodesic.api.referential.Datum;
import fr.geodesic.api.referential.properties.Ellipsoid;
import fr.geodesic.utils.converters.GeoConverterLambert93;
import fr.geodesic.utils.converters.GeoConverterMGRS;
import fr.geodesic.utils.converters.GeoConverterUTM;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.lang.properties.Timestamp;
import fr.java.math.algebra.vector.generic.Vector2D;
import fr.java.math.algebra.vector.generic.Vector3D;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.space.Point3D;
import fr.utils.geodesic.Angles;
import fr.utils.geodesic.Points;
import fr.utils.geodesic.Timestamps;
import fr.utils.geodesic.Vectors;
import fr.utils.geodesic.adapters.AdapterDynamicCartesian2D;
import fr.utils.geodesic.adapters.AdapterDynamicCartesian3D;
import fr.utils.geodesic.adapters.AdapterDynamicSpheric2D;
import fr.utils.geodesic.adapters.AdapterDynamicSpheric3D;
import fr.utils.geodesic.adapters.AdapterDynamicTiled2D;
import fr.utils.geodesic.adapters.AdapterDynamicTiled3D;
import fr.utils.geodesic.adapters.AdapterGeoCartesian2D;
import fr.utils.geodesic.adapters.AdapterGeoCartesian3D;
import fr.utils.geodesic.adapters.AdapterGeoSpheric2D;
import fr.utils.geodesic.adapters.AdapterGeoSpheric3D;
import fr.utils.geodesic.adapters.AdapterGeoTiled2D;
import fr.utils.geodesic.adapters.AdapterGeoTiled3D;
import fr.utils.geodesic.adapters.AdapterVector3D;

public class GeoCoordinates {
	private final static GeoCoordinate.Converter converterUTM       = new GeoConverterUTM();
	private final static GeoCoordinate.Converter converterMGRS      = new GeoConverterMGRS();
	private final static GeoCoordinate.Converter converterLambert93 = new GeoConverterLambert93();

	public static GeoCoordinate.Spheric3D.Editable 		newWGS84() {
		return newWGS84(Double.NaN, Double.NaN, Double.NaN);
	}
	public static GeoCoordinate.Spheric3D.Editable		newWGS84(final double _lg, final double _lt) {
		return newWGS84(_lg, _lt, 0.0);
	}
	public static GeoCoordinate.Spheric3D.Editable		newWGS84(final double _lg, final double _lt, final double _alt) {
		return newGeoSpheric3D(_lg, _lt, _alt);
	}
	public static GeoCoordinate.Spheric3D.Editable 		newWGS84(final Point2D _pt) {
		return newWGS84(_pt.getX(), _pt.getY());
	}
	public static GeoCoordinate.Spheric3D.Editable 		newWGS84(final Point3D _pt) {
		return newWGS84(_pt.getX(), _pt.getY(), _pt.getZ());
	}
	public static GeoCoordinate.Spheric3D.Editable 		newWGS84(final Vector2D _pt) {
		return newWGS84(_pt.getX(), _pt.getY());
	}
	public static GeoCoordinate.Spheric3D.Editable 		newWGS84(final Vector3D _pt) {
		return newWGS84(_pt.getX(), _pt.getY(), _pt.getZ());
	}

	public static GeoCoordinate.Cartesian3D.Editable 	newUTM() {
		return newUTM(Double.NaN, null, Double.NaN, null, Double.NaN);
	}
	public static GeoCoordinate.Cartesian3D.Editable 	newUTM(final double _x, final double _y) {
		return newUTM(_x, "31", _y, "U", 0.0);
	}
	public static GeoCoordinate.Cartesian3D.Editable 	newUTM(final double _x, final double _y, final double _alt) {
		return newUTM(_x, "31", _y, "U", _alt);
	}
	public static GeoCoordinate.Cartesian3D.Editable 	newUTM(final double _x, final String _zone_x, final double _y, final String _zone_y) {
		return newUTM(_x, _zone_x, _y, _zone_y, 0.0);
	}
	public static GeoCoordinate.Cartesian3D.Editable  	newUTM(final double _x, final String _zone_x, final double _y, final String _zone_y, double _alt) {
		return newGeoCartesian3D(Datum.UTM, _x, _zone_x, _y, _zone_y, _alt);
	}
	public static GeoCoordinate.Cartesian3D.Editable 	newUTM(final Point2D _pt) {
		return newUTM(_pt.getX(), "31", _pt.getY(), "U", 0.0);
	}
	public static GeoCoordinate.Cartesian3D.Editable 	newUTM(final Point2D _pt, final String _zone_x, final String _zone_y) {
		return newUTM(_pt.getX(), _zone_x, _pt.getY(), _zone_y, 0.0);
	}
	public static GeoCoordinate.Cartesian3D.Editable 	newUTM(final Point3D _pt) {
		return newUTM(_pt.getX(), "31", _pt.getY(), "U", _pt.getZ());
	}
	public static GeoCoordinate.Cartesian3D.Editable 	newUTM(final Point3D _pt, final String _zone_x, final String _zone_y) {
		return newUTM(_pt.getX(), _zone_x, _pt.getY(), _zone_y, _pt.getZ());
	}

	public static GeoCoordinate.Cartesian2D 			newMGRS(double _x, String _zone_x, double _y, String _zone_y) {
		return newGeoCartesian3D(Datum.MGRS, _x, _zone_x, _y, _zone_y, 0.0);
	}
	public static GeoCoordinate.Cartesian3D				newMGRS(double _x, String _zone_x, double _y, String _zone_y, double _alt) {
		return newGeoCartesian3D(Datum.MGRS, _x, _zone_x, _y, _zone_y, _alt);
	}

	public static GeoCoordinate.Cartesian2D.Editable 	newLambert93(double _x, double _y) {
		return newGeoCartesian2D(Datum.Lambert93, _x, null, _y, null);
	}
	public static GeoCoordinate.Cartesian3D.Editable 	newLambert93(double _x, double _y, double _alt) {
		return newGeoCartesian3D(Datum.Lambert93, _x, null, _y, null, _alt);
	}

	public static GeoCoordinate.Cartesian2D.Editable	newGeoCartesian2D(double _x, String _zone_x, double _y, String _zone_y) {
		return new AdapterGeoCartesian2D(_x, _zone_x, _y, _zone_y);
	}
	public static GeoCoordinate.Cartesian2D.Editable	newGeoCartesian2D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y) {
		return new AdapterGeoCartesian2D(_datum, _x, _zone_x, _y, _zone_y);
	}
	public static GeoDynamics.Cartesian2D.Editable		newGeoCartesian2D(double _x, String _zone_x, double _y, String _zone_y, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new AdapterDynamicCartesian2D(_x, _zone_x, _y, _zone_y, _timestamp, _heading, _velocity, _acceleration);
	}
	public static GeoDynamics.Cartesian2D.Editable		newGeoCartesian2D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new AdapterDynamicCartesian2D(_datum, _x, _zone_x, _y, _zone_y, _timestamp, _heading, _velocity, _acceleration);
	}
	public static GeoDynamics.Cartesian2D.Editable		newGeoCartesian2D(GeoCoordinate.Cartesian2D _coords, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new AdapterDynamicCartesian2D(_coords.getDatum(), _coords.getX(), _coords.getZoneX(), _coords.getY(), _coords.getZoneY(), _timestamp, _heading, _velocity, _acceleration);
	}

	public static GeoCoordinate.Cartesian3D.Editable	newGeoCartesian3D(double _x, String _zone_x, double _y, String _zone_y, double _alt) {
		return new AdapterGeoCartesian3D(_x, _zone_x, _y, _zone_y, _alt);
	}
	public static GeoCoordinate.Cartesian3D.Editable	newGeoCartesian3D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y, double _alt) {
		return new AdapterGeoCartesian3D(_datum, _x, _zone_x, _y, _zone_y, _alt);
	}
	public static GeoDynamics.Cartesian3D.Editable		newGeoCartesian3D(double _x, String _zone_x, double _y, double _alt, String _zone_y, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new AdapterDynamicCartesian3D(_x, _zone_x, _y, _zone_y, _alt, _timestamp, _heading, _velocity, _acceleration);
	}
	public static GeoDynamics.Cartesian3D.Editable		newGeoCartesian3D(Datum _datum, double _x, String _zone_x, double _y, double _alt, String _zone_y, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new AdapterDynamicCartesian3D(_datum, _x, _zone_x, _y, _zone_y, _alt, _timestamp, _heading, _velocity, _acceleration);
	}
	public static GeoDynamics.Cartesian3D.Editable		newGeoCartesian3D(GeoCoordinate.Cartesian3D _coords, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new AdapterDynamicCartesian3D(_coords.getDatum(), _coords.getX(), _coords.getZoneX(), _coords.getY(), _coords.getZoneY(), _coords.getZ(), _timestamp, _heading, _velocity, _acceleration);
	}

	public static GeoCoordinate.Spheric2D.Editable		newGeoSpheric2D(double _lg, double _lt) {
		return new AdapterGeoSpheric2D(Datum.WGS84, _lg, _lt);
	}
	public static GeoCoordinate.Spheric2D.Editable		newGeoSpheric2D(Datum _datum, double _lg, double _lt) {
		return new AdapterGeoSpheric2D(_datum, _lg, _lt);
	}
	public static GeoDynamics.Spheric2D.Editable		newGeoSpheric2D(double _lg, double _lt, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new AdapterDynamicSpheric2D(_lg, _lt, _timestamp, _heading, _velocity, _acceleration);
	}
	public static GeoDynamics.Spheric2D.Editable		newGeoSpheric2D(Datum _datum, double _lg, double _lt, String _zone_y, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new AdapterDynamicSpheric2D(_datum, _lg, _lt, _timestamp, _heading, _velocity, _acceleration);
	}
	public static GeoDynamics.Spheric2D.Editable		newGeoSpheric2D(GeoCoordinate.Spheric2D _coords, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new AdapterDynamicSpheric2D(_coords.getDatum(), _coords.getLongitude(), _coords.getLatitude(), _timestamp, _heading, _velocity, _acceleration);
	}

	public static GeoCoordinate.Spheric3D.Editable		newGeoSpheric3D(double _lg, double _lt, double _alt) {
		return new AdapterGeoSpheric3D(Datum.WGS84, _lg, _lt, _alt);
	}
	public static GeoCoordinate.Spheric3D.Editable		newGeoSpheric3D(Datum _datum, double _lg, double _lt, double _alt) {
		return new AdapterGeoSpheric3D(_datum, _lg, _lt, _alt);
	}
	public static GeoDynamics.Spheric3D.Editable		newGeoSpheric3D(double _lg, double _lt, double _alt, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new AdapterDynamicSpheric3D(_lg, _lt, _alt, _timestamp, _heading, _velocity, _acceleration);
	}
	public static GeoDynamics.Spheric3D.Editable		newGeoSpheric3D(Datum _datum, double _lg, double _lt, double _alt, String _zone_y, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new AdapterDynamicSpheric3D(_datum, _lg, _lt, _alt, _timestamp, _heading, _velocity, _acceleration);
	}
	public static GeoDynamics.Spheric3D.Editable		newGeoSpheric3D(GeoCoordinate.Spheric3D _coords, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new AdapterDynamicSpheric3D(_coords.getDatum(), _coords.getLongitude(), _coords.getLatitude(), _coords.getAltitude(), _timestamp, _heading, _velocity, _acceleration);
	}

	public static GeoCoordinate.Tiled2D.Editable		newGeoTiled2D(int _lvl, int _i, int _j, double _x, double _y) {
		return new AdapterGeoTiled2D(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y);
	}
	public static GeoCoordinate.Tiled2D.Editable		newGeoTiled2D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y) {
		return new AdapterGeoTiled2D(_datum, _lvl, _i, _j, _x, _y);
	}
	public static GeoDynamics.Tiled2D.Editable			newGeoTiled2D(int _lvl, int _i, int _j, double _x, double _y, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new AdapterDynamicTiled2D(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y, _timestamp, _heading, _velocity, _acceleration);
	}
	public static GeoDynamics.Tiled2D.Editable			newGeoTiled2D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new AdapterDynamicTiled2D(_datum, _lvl, _i, _j, _x, _y, _timestamp, _heading, _velocity, _acceleration);
	}
	public static GeoDynamics.Tiled2D.Editable			newGeoTiled2D(GeoCoordinate.Tiled2D _coord, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new AdapterDynamicTiled2D(_coord.getDatum(), _coord.getLevel(), _coord.getI(), _coord.getJ(), _coord.getX(), _coord.getY(), _timestamp, _heading, _velocity, _acceleration);
	}

	public static GeoCoordinate.Tiled3D.Editable		newGeoTiled3D(int _lvl, int _i, int _j, double _x, double _y, double _alt) {
		return new AdapterGeoTiled3D(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y, _alt);
	}
	public static GeoCoordinate.Tiled3D.Editable		newGeoTiled3D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y, double _alt) {
		return new AdapterGeoTiled3D(_datum, _lvl, _i, _j, _x, _y, _alt);
	}
	public static GeoDynamics.Tiled3D.Editable			newGeoTiled3D(int _lvl, int _i, int _j, double _x, double _y, double _alt, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new AdapterDynamicTiled3D(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y, _alt, _timestamp, _heading, _velocity, _acceleration);
	}
	public static GeoDynamics.Tiled3D.Editable			newGeoTiled3D(Datum _datum, int _lvl, int _i, int _j, double _x, double _y, double _alt, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new AdapterDynamicTiled3D(_datum, _lvl, _i, _j, _x, _y, _alt, _timestamp, _heading, _velocity, _acceleration);
	}
	public static GeoDynamics.Tiled3D.Editable			newGeoTiled3D(GeoCoordinate.Tiled3D _coord, Timestamp _timestamp, double _heading, Vector3D _velocity, Vector3D _acceleration) {
		return new AdapterDynamicTiled3D(_coord.getDatum(), _coord.getLevel(), _coord.getI(), _coord.getJ(), _coord.getX(), _coord.getY(), _coord.getZ(), _timestamp, _heading, _velocity, _acceleration);
	}

	public static GeoCoordinate.Tiled3D.Editable 		newTiled(int _lvl, int _i, int _j, double _x, double _y) {
		return newGeoTiled3D(_lvl, _i, _j, _x, _y, 0d);
	}
	public static GeoCoordinate.Tiled3D.Editable 		newTiled(int _lvl, int _i, int _j, double _x, double _y, double _alt) {
		return newGeoTiled3D(Datum.WEB_MERCATOR, _lvl, _i, _j, _x, _y, _alt);
	}

	/**
	 * CONVERSION / REFERENTIAL
	 */
	public static GeoCoordinate.Spheric2D   			asWGS84(GeoCoordinate _c) {
		switch(_c.getDatum()) {
		case WGS84:		return (GeoCoordinate.Spheric2D) _c;
		case UTM:		return converterUTM.toLatLong(_c);
		case Lambert93:	return converterLambert93.toLatLong(_c);
		case MGRS:		return converterMGRS.toLatLong(_c);
		default:		throw new IllegalArgumentException();		
		}
	}
	public static GeoCoordinate.Cartesian2D 			asUTM(GeoCoordinate _c) {
		switch(_c.getDatum()) {
		case WGS84:		return converterUTM.fromLatLong(_c);
		case UTM:		return (GeoCoordinate.Cartesian2D) _c;
		case Lambert93:	return converterUTM.fromLatLong(converterLambert93.toLatLong(_c));
		case MGRS:		return converterUTM.fromLatLong(converterMGRS.toLatLong(_c));
		default:		throw new IllegalArgumentException();		
		}
	}
	public static GeoCoordinate 						asLambert93(GeoCoordinate _c) {
		switch(_c.getDatum()) {
		case WGS84:		return converterLambert93.fromLatLong(_c);
		case UTM:		return converterLambert93.fromLatLong(converterUTM.toLatLong(_c));
		case Lambert93:	return _c;
		case MGRS:		return converterLambert93.fromLatLong(converterMGRS.toLatLong(_c));
		default:		throw new IllegalArgumentException();		
		}
	}

	/**
	 * OPERATIONS
	 */
	public static GeoCoordinate 						interpolate(GeoCoordinate _p0, GeoCoordinate _p1, double _t) {
		if(_p0.getDatum() != _p1.getDatum())
			throw new RuntimeException("Can't operate interpolation between different CRS");
		if(_p0.getDatum() != Datum.UTM && _p0.getDatum() != Datum.WGS84)
			throw new NotYetImplementedException("Datum is " + _p0.getDatum());

		double x = (1.0 - _t) * _p0.asUTM().getX() + _t * _p1.asUTM().getX();
		double y = (1.0 - _t) * _p0.asUTM().getY() + _t * _p1.asUTM().getY();
//		double z = (1.0 - _t) * _p0.asUTM().getZ() + _t * _p1.asUTM().getZ();

//		return newUTM(x, _p0.asUTM().getZoneX(), y, _p0.asUTM().getZoneY(), z);
		return newUTM(x, _p0.asUTM().getZoneX(), y, _p0.asUTM().getZoneY());
	}

	/// Distances
	public static enum DistanceMethod { Haversine, Iterative };

	public static double 								computeDistance(GeoCoordinate _from, GeoCoordinate _to) {
		return computeDistance(_from, _to, DistanceMethod.Haversine);
	}
	public static double 								computeDistance(GeoCoordinate _from, GeoCoordinate _to, double _radius_m) {
		return distanceWithRadius(_from.asWGS84().getLatitude(), _from.asWGS84().getLongitude(), _to.asWGS84().getLatitude(), _to.asWGS84().getLongitude(), _radius_m);
	}
	public static double 								computeDistance(GeoCoordinate _from, GeoCoordinate _to, Ellipsoid _ellipsoid, int _maxIter) {
		return distanceWithEllipsoid(_from.asWGS84().getLatitude(), _from.asWGS84().getLongitude(), _to.asWGS84().getLatitude(), _to.asWGS84().getLongitude(), _ellipsoid, _maxIter);
	}
	public static double 								computeDistance(GeoCoordinate _from, GeoCoordinate _to, DistanceMethod _method) {
		switch(_method) {
		default:
		case Haversine: return distanceWithRadius(_from.asWGS84().getLatitude(), _from.asWGS84().getLongitude(), _to.asWGS84().getLatitude(), _to.asWGS84().getLongitude(), 6_371_000);
		case Iterative: return distanceWithEllipsoid(_from.asWGS84().getLatitude(), _from.asWGS84().getLongitude(), _to.asWGS84().getLatitude(), _to.asWGS84().getLongitude(), Ellipsoid.WGS84, 10);
		}
	}

	public static double 								computeDistanceUTM(GeoCoordinate _from, GeoCoordinate _to) {
		return computeDistanceUTM(_from.asUTM(), _to.asUTM());
	}
	public static double 								computeDistanceUTM(GeoCoordinate.Cartesian2D _from, GeoCoordinate.Cartesian2D _to) {
		double deltaX = _to.getX() - _from.getX();
		double deltaY = _to.getY() - _from.getY();

		return Math.sqrt( deltaX*deltaX + deltaY*deltaY);
	}
	public static double 								computeDistanceUTM(GeoCoordinate.Cartesian3D _from, GeoCoordinate.Cartesian3D _to) {
		double deltaX = _to.getX() - _from.getX();
		double deltaY = _to.getY() - _from.getY();
		double deltaZ = _to.getZ() - _from.getZ();

		return Math.sqrt( deltaX*deltaX + deltaY*deltaY + deltaZ*deltaZ);
	}

	/**
	 * Haversine formulae, implementation based on example at 
	 * <a href="http://www.codecodex.com/wiki/Calculate_Distance_Between_Two_Points_on_a_Globe">codecodex</a>.
	 * <br/>
	 * Mean earth radius (IUGG) = 6371.009 km
	 * Meridional earth radius  = 6367.4491 km
	 * Earth radius by assumption that 1 degree equals exactly 60 NM: 1.852 * 60 * 360 / (2 * Pi) = 6366.7 km
	 * 
	 * @return Distance in meters
	 */
    static double 										distanceWithRadius(final double _lat1, final double _long1, final double _lat2, final double _long2, final double _radius_m) {
        final double LAT_1_RADIANS     = Math.toRadians(_lat1);
        final double LAT_2_RADIANS     = Math.toRadians(_lat2);
        final double DELTA_LAT_RADIANS = Math.toRadians(_lat2-_lat1);
        final double DELTA_LON_RADIANS = Math.toRadians(_long2-_long1);

        final double A = Math.sin(DELTA_LAT_RADIANS * 0.5) * Math.sin(DELTA_LAT_RADIANS * 0.5) + Math.cos(LAT_1_RADIANS) * Math.cos(LAT_2_RADIANS) * Math.sin(DELTA_LON_RADIANS * 0.5) * Math.sin(DELTA_LON_RADIANS * 0.5);
        final double C = 2 * Math.atan2(Math.sqrt(A), Math.sqrt(1-A));

        final double DISTANCE = _radius_m * C;

        return DISTANCE;
    }
	static double 										distanceWithEllipsoid(final double _lat1, final double _long1, final double _lat2, final double _long2, Ellipsoid _ellipsoid, int _maxIter) {
        // Based on android.location.Location.java
    	// Based on http://www.ngs.noaa.gov/PUBS_LIB/inverse.pdf
        // using the "Inverse Formula" (section 4)

		double aSqMinusBSqOverBSq = (_ellipsoid.equatorialRadius() * _ellipsoid.equatorialRadius() - _ellipsoid.polarRadius() * _ellipsoid.polarRadius()) / (_ellipsoid.polarRadius() * _ellipsoid.polarRadius());

        // Convert lat/long to radians
        double lat1 = Math.toRadians(_lat1); 
        double lon1 = Math.toRadians(_long1);
        
        double lat2 = Math.toRadians(_lat2);
        double lon2 = Math.toRadians(_long2);

        double L = lon2 - lon1;
        double A = 0.0;
        double U1 = Math.atan((1.0 - _ellipsoid.excentricity()) * Math.tan(lat1));
        double U2 = Math.atan((1.0 - _ellipsoid.excentricity()) * Math.tan(lat2));

        double cosU1 = Math.cos(U1);
        double cosU2 = Math.cos(U2);
        double sinU1 = Math.sin(U1);
        double sinU2 = Math.sin(U2);
        double cosU1cosU2 = cosU1 * cosU2;
        double sinU1sinU2 = sinU1 * sinU2;

        double sigma = 0.0;
        double deltaSigma = 0.0;
        double cosSqAlpha;
        double cos2SM;
        double cosSigma;
        double sinSigma;
        double cosLambda;
        double sinLambda;

        double lambda = L; // initial guess
        for(int iter = 0; iter < _maxIter; iter++) {
            double lambdaOrig = lambda;
            cosLambda = Math.cos(lambda);
            sinLambda = Math.sin(lambda);
            double t1 = cosU2 * sinLambda;
            double t2 = cosU1 * sinU2 - sinU1 * cosU2 * cosLambda;
            double sinSqSigma = t1 * t1 + t2 * t2; // (14)
            sinSigma = Math.sqrt(sinSqSigma);
            cosSigma = sinU1sinU2 + cosU1cosU2 * cosLambda; // (15)
            sigma = Math.atan2(sinSigma, cosSigma); // (16)
            double sinAlpha = (sinSigma == 0) ? 0.0 :
                cosU1cosU2 * sinLambda / sinSigma; // (17)
            cosSqAlpha = 1.0 - sinAlpha * sinAlpha;
            cos2SM = (cosSqAlpha == 0) ? 0.0 :
                cosSigma - 2.0 * sinU1sinU2 / cosSqAlpha; // (18)

            double uSquared = cosSqAlpha * aSqMinusBSqOverBSq; // defn
            A = 1 + (uSquared / 16384.0) * // (3)
                (4096.0 + uSquared *
                 (-768 + uSquared * (320.0 - 175.0 * uSquared)));
            double B = (uSquared / 1024.0) * // (4)
                (256.0 + uSquared *
                 (-128.0 + uSquared * (74.0 - 47.0 * uSquared)));
            double C = (_ellipsoid.excentricity() / 16.0) *
                cosSqAlpha *
                (4.0 + _ellipsoid.excentricity() * (4.0 - 3.0 * cosSqAlpha)); // (10)
            double cos2SMSq = cos2SM * cos2SM;
            deltaSigma = B * sinSigma * // (6)
                (cos2SM + (B / 4.0) *
                 (cosSigma * (-1.0 + 2.0 * cos2SMSq) -
                  (B / 6.0) * cos2SM *
                  (-3.0 + 4.0 * sinSigma * sinSigma) *
                  (-3.0 + 4.0 * cos2SMSq)));

            lambda = L +
                (1.0 - C) * _ellipsoid.excentricity() * sinAlpha *
                (sigma + C * sinSigma *
                 (cos2SM + C * cosSigma *
                  (-1.0 + 2.0 * cos2SM * cos2SM))); // (11)

            double delta = (lambda - lambdaOrig) / lambda;
            if (Math.abs(delta) < 1.0e-12) {
                break;
            }
        }

        float distance = (float) (_ellipsoid.polarRadius() * A * (sigma - deltaSigma));
        return distance;
    }

	/**
	 * DYNAMICS
	 */
	public static GeoDynamics 							newDynamics(GeoCoordinate _pos) {
		return new AdapterDynamicSpheric2D(_pos.asWGS84(), Timestamps.epoch(), 0d, Vectors.zero3(), Vectors.zero3());
	}
	public static GeoDynamics 							newDynamics(GeoCoordinate _pos, Timestamp _of, double _heading, Vector3D _vel, Vector3D _acc) {
		return new AdapterDynamicSpheric2D(_pos.asWGS84(), _of, _heading, _vel, _acc);
	}

	public static GeoDynamics							computeDynamics(Timestamp _oldTime, GeoCoordinate _oldPos, Timestamp _newTime, GeoCoordinate _newPos) {
		if(_oldPos == null || _newPos == null)
			return null;

		double   h = computeHeading(_oldPos, _newPos);
		Vector3D v = computeVelocity(_oldTime, _oldPos, _newTime, _newPos);

		return new AdapterDynamicSpheric2D(_newPos.asWGS84(), _newTime, h, v, null);
	}
	public static GeoDynamics							computeDynamics(Timestamp _oldTime, GeoCoordinate _oldPos, Vector3D _oldVel, Timestamp _newTime, GeoCoordinate _newPos, Vector3D _newVel) {
		if(_oldPos == null || _newPos == null)
			return null;

		double   h = computeHeading(_oldPos, _newPos);
		Vector3D v = computeVelocity(_oldTime, _oldPos, _newTime, _newPos);
		Vector3D a = computeAcceleration(_oldTime, _oldPos, _oldVel, _newTime, _newPos, _newVel);

		return new AdapterDynamicSpheric2D(_newPos.asWGS84(), _newTime, h, v, a);
	}

	public static double   								computeHeading(GeoCoordinate _from, GeoCoordinate _to) {
		double A = _to.asUTM().getY() - _from.asUTM().getY();
		double O = _to.asUTM().getX() - _from.asUTM().getX();
		
		double H = A == 0 && O == 0 ? 0 : (double) (90 - Angles.Radian2Degree(Math.atan2(A, O)));

		return H;
	}
	public static Vector3D 								computeVelocity(Timestamp _oldTime, GeoCoordinate _from, Timestamp _newTime, GeoCoordinate _to) {
		if(_oldTime == null || _newTime == null || _from == null || _to == null)
			throw new IllegalArgumentException();

		GeoCoordinate.Cartesian2D p0 = _from.asUTM();
		GeoCoordinate.Cartesian2D p1 = _to.asUTM();

		double ds = Math.sqrt( (p1.getX()-p0.getX())*(p1.getX()-p0.getX()) + (p1.getY()-p0.getY())*(p1.getY()-p0.getY()) );
		double dt = _newTime.delta(_oldTime);// ms
		double v  = ( ds / dt ) * 1e3;

		return Vectors.of(v, 0.0, 0.0);
	}
	public static Vector3D 								computeAcceleration(Timestamp _oldTime, GeoCoordinate _from, Vector3D _oldVel, Timestamp _newTime, GeoCoordinate _to, Vector3D _newVel) {
		if(_oldTime == null || _newTime == null || _from == null || _to == null || _oldVel == null || _newVel == null)
			throw new IllegalArgumentException("No timestamp for given Coordinate");

		Vector3D v0 = _oldVel, v1 = _newVel;

		double ds = Math.sqrt( (v1.getX()-v0.getX())*(v1.getX()-v0.getX()) + (v1.getY()-v0.getY())*(v1.getY()-v0.getY()) );
		double dt = _newTime.delta(_oldTime);// / 1000.0;
		double a  = (ds / dt) * 3600;

		return new AdapterVector3D(a, 0.0, 0.0);
	}

	public static GeoCoordinate 						plusUTM(GeoCoordinate _p, double _dx, double _dy) {
		Point2D P = Points.of(_p.asUTM());
		return newUTM(P.plus(_dx, _dy));
	}


}

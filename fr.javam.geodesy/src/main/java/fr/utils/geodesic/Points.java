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
package fr.utils.geodesic;

import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.vector.generic.Vector1D;
import fr.java.math.algebra.vector.generic.Vector2D;
import fr.java.math.algebra.vector.generic.Vector3D;
import fr.java.math.geometry.Geometry;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.topology.Coordinate;
import fr.utils.geodesic.adapters.AdapterPoint1D;
import fr.utils.geodesic.adapters.AdapterPoint2D;
import fr.utils.geodesic.adapters.AdapterPoint3D;

public class Points {

	// One Dimension -----------------------------------------------------------
	public static final AdapterPoint1D NaN1  = new AdapterPoint1D(Double.NaN);

	public static AdapterPoint1D	O1() 										{ return new AdapterPoint1D(); }

	public static AdapterPoint1D 	zero1() 									{ return new AdapterPoint1D( 0); }
	public static AdapterPoint1D 	unit1() 									{ return new AdapterPoint1D( 1); }

	public static AdapterPoint1D 	of(double _x) 								{ return new AdapterPoint1D(_x); }
	public static AdapterPoint1D 	of(Coordinate.OneDim _coords) 				{ return new AdapterPoint1D(_coords.getFirst()); }
	public static AdapterPoint1D 	of(Vector1D _v) 							{ return new AdapterPoint1D(_v.getX()); }
	public static AdapterPoint1D 	of1(NumberVector _v) 						{
		if(_v == null || _v.size() < 0)
			throw new IllegalAccessError("Why ???");

		return new AdapterPoint1D(_v.getNumber(0).doubleValue());
	}

	public static AdapterPoint1D 	ofEditable(double _x) 						{ return new AdapterPoint1D(_x); }
	public static AdapterPoint1D 	ofEditable(Coordinate.OneDim _coords) 		{ return new AdapterPoint1D(_coords.getFirst()); }
	public static AdapterPoint1D 	ofEditable(Vector1D _v) 					{ return new AdapterPoint1D(_v.getX()); }
	public static AdapterPoint1D	ofEditable1(NumberVector _v) 				{
		if(_v == null || _v.size() < 1)
			throw new IllegalAccessError("Why ???");

		return new AdapterPoint1D(_v.getNumber(0).doubleValue());
	}

	// Two Dimensions ----------------------------------------------------------
	public static final AdapterPoint2D  NaN2  = new AdapterPoint2D(Double.NaN, Double.NaN);

	public static AdapterPoint2D	O2() 										{ return new AdapterPoint2D(); }

	public static AdapterPoint2D 	zero2() 									{ return new AdapterPoint2D( 0,  0); }
	public static AdapterPoint2D 	unit2() 									{ return new AdapterPoint2D( 1,  1); }

	public static AdapterPoint2D 	of(double _x, double _y) 					{ return new AdapterPoint2D(_x, _y); }
	public static AdapterPoint2D 	of(Coordinate.TwoDims _coords) 				{ return new AdapterPoint2D(_coords.getFirst(), _coords.getSecond()); }
	public static AdapterPoint2D 	of(Vector2D _v) 							{ return new AdapterPoint2D(_v.getX(), _v.getY()); }
	public static AdapterPoint2D 	of2(NumberVector _v) 						{
		if(_v == null || _v.size() < 2)
			throw new IllegalAccessError("Why ???");

		return new AdapterPoint2D(_v.getNumber(0).doubleValue(), _v.getNumber(1).doubleValue());
	}

	public static AdapterPoint2D 	ofEditable(double _x, double _y) 			{ return new AdapterPoint2D(_x, _y); }
	public static AdapterPoint2D 	ofEditable(Coordinate.TwoDims _coords) 		{ return new AdapterPoint2D(_coords.getFirst(), _coords.getSecond()); }
	public static AdapterPoint2D 	ofEditable(Vector2D _v) 					{ return new AdapterPoint2D(_v.getX(), _v.getY()); }
	public static AdapterPoint2D 	ofEditable2(NumberVector _v) 				{
		if(_v == null || _v.size() < 2)
			throw new IllegalAccessError("Why ???");

		return new AdapterPoint2D(_v.getNumber(0).doubleValue(), _v.getNumber(1).doubleValue());
	}

	// Three Dimensions --------------------------------------------------------
	public static final AdapterPoint3D NaN3 = new AdapterPoint3D(Double.NaN, Double.NaN, Double.NaN);

	public static AdapterPoint3D 	O3() 										{ return new AdapterPoint3D(); }

	public static AdapterPoint3D 	zero3() 									{ return new AdapterPoint3D( 0,  0,  0); }
	public static AdapterPoint3D 	unit3() 									{ return new AdapterPoint3D( 1,  1,  1); }

	public static AdapterPoint3D 	of(double _x, double _y, double _z) 		{ return new AdapterPoint3D(_x, _y, _z); }
	public static AdapterPoint3D 	of(Coordinate.ThreeDims _coords) 			{ return new AdapterPoint3D(_coords.getFirst(), _coords.getSecond(), _coords.getThird()); }
	public static AdapterPoint3D 	of(Vector3D _v) 							{ return new AdapterPoint3D(_v.getX(), _v.getY(), _v.getZ()); }
	public static AdapterPoint3D 	of3(NumberVector _v) 						{
		if(_v == null || _v.size() < 3)
			throw new IllegalAccessError("Why ???");

		return new AdapterPoint3D(_v.getNumber(0).doubleValue(), _v.getNumber(1).doubleValue(), _v.getNumber(2).doubleValue());
	}

	public static AdapterPoint3D 	ofEditable(double _x, double _y, double _z) { return new AdapterPoint3D(_x, _y, _z); }
	public static AdapterPoint3D 	ofEditable(Coordinate.ThreeDims _coords) 	{ return new AdapterPoint3D(_coords.getFirst(), _coords.getSecond(), _coords.getThird()); }
	public static AdapterPoint3D 	ofEditable(Vector3D _v) 					{ return new AdapterPoint3D(_v.getX(), _v.getY(), _v.getZ()); }
	public static AdapterPoint3D 	ofEditable3(NumberVector _v) 				{
		if(_v == null || _v.size() < 3)
			throw new IllegalAccessError("Why ???");

		return new AdapterPoint3D(_v.getNumber(0).doubleValue(), _v.getNumber(1).doubleValue(), _v.getNumber(2).doubleValue());
	}

	// Two Dimension Operators -------------------------------------------------
	public static double 			distance(Point2D A, Point2D B, Geometry.Distance _distance) {
		switch(_distance) {
		case EUCLIDIAN: int    dx = (int) (A.getX() - B.getX());
						int    dy = (int) (A.getY() - B.getY());
						double d2 = dx * dx + dy * dy;
						return Math.sqrt(d2);
		default: 		throw new RuntimeException();
		}
	}

	public static AdapterPoint2D 	plus(Coordinate.TwoDims _pt, double _dx, double _dy) {
		return Points.of(_pt.getFirst() + _dx, _pt.getSecond() + _dy);
	}
	public static AdapterPoint2D 	minus(Coordinate.TwoDims _pt, double _dx, double _dy) {
		return Points.of(_pt.getFirst() - _dx, _pt.getSecond() - _dy);
	}
	public static AdapterPoint2D 	times(Coordinate.TwoDims _pt, double _t) {
		return Points.of(_pt.getFirst() * _t, _pt.getSecond() * _t);
	}
	public static AdapterPoint2D 	divides(Coordinate.TwoDims _pt, double _t) {
		return Points.of(_pt.getFirst() * _t, _pt.getSecond() * _t);
	}

}

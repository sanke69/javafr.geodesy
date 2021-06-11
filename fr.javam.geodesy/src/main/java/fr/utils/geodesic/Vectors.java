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
import fr.java.math.algebra.vector.generic.Vector4D;
import fr.java.math.geometry.linear.Point1D;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.space.Point3D;
import fr.java.math.topology.Coordinate;
import fr.utils.geodesic.adapters.AdapterVector1D;
import fr.utils.geodesic.adapters.AdapterVector2D;
import fr.utils.geodesic.adapters.AdapterVector3D;
import fr.utils.geodesic.adapters.AdapterVector4D;

public class Vectors {

	// One Dimension -----------------------------------------------------------
	public static Vector1D.Editable zero1() 											{ return new AdapterVector1D(); }
	public static Vector1D.Editable unit1() 											{ return new AdapterVector1D(1); }

	public static Vector1D.Editable of(double _x) 										{ return new AdapterVector1D(_x); }
	public static Vector1D.Editable of(Point1D _pt) 									{ return new AdapterVector1D(_pt.getX()); }
	public static Vector1D.Editable of(Point1D _b, Point1D _a) 							{ return new AdapterVector1D(_b.getX() - _a.getX()); }

	public static Vector1D.Editable delta(Point1D _b, Point1D _a) 						{ return new AdapterVector1D(_b.getFirst() - _a.getFirst()); }
	public static Vector1D.Editable delta(Coordinate.OneDim _b, Coordinate.OneDim _a) 	{ return new AdapterVector1D(_b.getFirst() - _a.getFirst()); }

	// Two Dimensions ----------------------------------------------------------
	public static Vector2D.Editable zero2() 											{ return new AdapterVector2D(); }
	public static Vector2D.Editable unit2() 											{ return new AdapterVector2D(1, 1); }

	public static Vector2D.Editable of(double _x, double _y) 							{ return new AdapterVector2D(_x, _y); }
	public static Vector2D.Editable of(Point2D _pt) 									{ return new AdapterVector2D(_pt.getX(), _pt.getY()); }
	public static Vector2D.Editable of(Point2D _a, Point2D _b) 							{ return new AdapterVector2D(_b.getX() - _a.getX(), _b.getY() - _a.getY()); }

	public static Vector2D.Editable delta(Point2D _b, Point2D _a) 						{ return new AdapterVector2D(_b.getX() - _a.getX(), _b.getY() - _a.getY()); }
	public static Vector2D.Editable delta(Coordinate.TwoDims _b, Coordinate.TwoDims _a) { return new AdapterVector2D(_b.getFirst() - _a.getFirst(), _b.getSecond() - _a.getSecond()); }

	public static double 			dotProduct(final Vector2D _a, final NumberVector _b) {
		assert(_b.size() == 2);
		return _a.getX() * _b.getNumber(0).doubleValue() + _a.getY() * _b.getNumber(1).doubleValue();
	}
	public static double 			dotProduct(final NumberVector _a, final Vector2D _b) {
		assert(_a.size() == 2);
		return _a.getNumber(0).doubleValue() * _b.getX() + _a.getNumber(1).doubleValue() * _b.getY();
	}
	public static double 			dotProduct(final Vector2D _a, final Vector2D _b) {
		return _a.getX() * _b.getX() + _a.getY() * _b.getY();
	}

	public static Vector2D 			crossProduct(final Vector2D _a, final NumberVector _b) {
		assert(_b.size() == 2);
		return new AdapterVector2D( _a.getY()*_b.getNumber(0).doubleValue() - _a.getX()*_b.getNumber(1).doubleValue(), _a.getX()*_b.getNumber(1).doubleValue() - _a.getY()*_b.getNumber(0).doubleValue()  );
	}
	public static Vector2D 			crossProduct(final NumberVector _a, final Vector2D _b) {
		assert(_a.size() == 2);
		return new AdapterVector2D( _a.getNumber(1).doubleValue()*_b.getX() - _a.getNumber(0).doubleValue()*_b.getY(), _a.getNumber(0).doubleValue()*_b.getY() - _a.getNumber(1).doubleValue()*_b.getX()  );
	}
	public static Vector2D 			crossProduct(final Vector2D _a, final Vector2D _b) {
		return new AdapterVector2D( _a.getY()*_b.getX() - _a.getX()*_b.getY(), _a.getX()*_b.getY() - _a.getY()*_b.getX()  );
	}

	// Three Dimensions --------------------------------------------------------
	public static Vector3D.Editable zero3() 											{ return new AdapterVector3D(); }
	public static Vector3D.Editable unit3() 											{ return new AdapterVector3D(1, 1, 1); }

	public static Vector3D.Editable of(double _x, double _y, double _z) 				{ return new AdapterVector3D(_x, _y, _z); }
	public static Vector3D.Editable of(Point3D _pt) 									{ return new AdapterVector3D(_pt.getX(), _pt.getY(), _pt.getZ()); }
	public static Vector3D.Editable of(Point3D _b, Point3D _a) 							{ return new AdapterVector3D(_b.getX() - _a.getX(), _b.getY() - _a.getY(), _b.getZ() - _a.getZ()); }

	public static Vector3D.Editable delta(Point3D _b, Point3D _a)	 					{ return new AdapterVector3D(_b.getX() - _a.getX(), _b.getY() - _a.getY(), _b.getZ() - _a.getZ()); }

    public static double 			dotProduct(final Vector3D _a, final NumberVector _b) {
    	assert(_b.size() == 2);
        return _a.getX() * _b.getNumber(0).doubleValue() + _a.getY() * _b.getNumber(1).doubleValue() + _a.getZ() * _b.getNumber(2).doubleValue();
    }
    public static double 			dotProduct(final Vector3D _a, final Vector3D _b) {
        return _a.getX() * _b.getX() + _a.getY() * _b.getY() + _a.getZ() * _b.getZ();
    }

	public static Vector3D 			crossProduct(final Vector3D _a, final NumberVector _b) {
		assert(_b.size() == 2);
		return new AdapterVector3D( _a.getY() * _b.getNumber(2).doubleValue() - _a.getZ() * _b.getNumber(1).doubleValue(),
							 		_a.getZ() * _b.getNumber(0).doubleValue() - _a.getX() * _b.getNumber(2).doubleValue(),
							 		_a.getX() * _b.getNumber(1).doubleValue() - _a.getY() * _b.getNumber(0).doubleValue()  );
	}
	public static Vector3D 			crossProduct(final NumberVector _a, final Vector3D _b) {
		assert(_a.size() == 2);
		return new AdapterVector3D(	_a.getNumber(1).doubleValue() * _b.getZ() - _a.getNumber(2).doubleValue() * _b.getY(),
									_a.getNumber(2).doubleValue() * _b.getX() - _a.getNumber(0).doubleValue() * _b.getZ(),
									_a.getNumber(0).doubleValue() * _b.getY() - _a.getNumber(1).doubleValue() * _b.getX()  );
	}
	public static Vector3D 			crossProduct(final Vector3D _a, final Vector3D _b) {
		return new AdapterVector3D( _a.getY() * _b.getZ() - _a.getZ() * _b.getY(),
								    _a.getZ() * _b.getX() - _a.getX() * _b.getZ(),
								    _a.getX() * _b.getY() - _a.getY() * _b.getX()  );
	}

	// Four Dimensions --------------------------------------------------------
	public static Vector4D.Editable zero4() 											{ return new AdapterVector4D(); }
	public static Vector4D.Editable unit4() 											{ return new AdapterVector4D(1, 1, 1, 1); }

	public static Vector4D.Editable of(double _x, double _y, double _z, double _w) 		{ return new AdapterVector4D(_x, _y, _z, _w); }

	// Operations Diverses ----------------------------------------------------
    public static double 			dotProduct(final NumberVector _a, final NumberVector _b) {
    	assert(_a.size() == _b.size());
    	
    	if(_a.size() == 2)
    		return _a.getNumber(0).doubleValue() * _b.getNumber(0).doubleValue() + _a.getNumber(1).doubleValue() * _b.getNumber(1).doubleValue();
    	if(_a.size() == 3)
    		return _a.getNumber(0).doubleValue() * _b.getNumber(0).doubleValue() + _a.getNumber(1).doubleValue() * _b.getNumber(1).doubleValue() + _a.getNumber(2).doubleValue() * _b.getNumber(2).doubleValue();
    	
    	return -1;
    }

	public static Vector2D 			crossProduct(final NumberVector _a, final NumberVector _b) {
		assert(_a.size() == _b.size() && _a.size() == 2);
		return new AdapterVector2D( _a.getNumber(1).doubleValue()*_b.getNumber(0).doubleValue() - _a.getNumber(0).doubleValue()*_b.getNumber(1).doubleValue(), _a.getNumber(0).doubleValue()*_b.getNumber(1).doubleValue() - _a.getNumber(1).doubleValue()*_b.getNumber(0).doubleValue()  );
	}

}

/** ************************************************************************ **\
 * Copyright (c) 2007-?XYZ Steve PECHBERTI                                    *
 *                                                                            *
 * @author <a href='mailto:steve.pechberti@gmail.com'> Steve PECHBERTI </a>   *
 *                                                                            *
 * @section license License                                                   *
 *    [EN] This program is free software:                                     *
 *         you can redistribute it and/or modify it under the terms of        * 
 *         the GNU General Public License as published by                     *
 *         the Free Software Foundation, either version 3 of the License, or  *
 *         (at your option) any later version.                                *
 *         You should have received a copy of the GNU General Public License  *
 *         along with this program. If not, see                               *
 *            <http://www.gnu.org/licenses/gpl.html>                          *
 *    [FR] Ce programme est un logiciel libre ; vous pouvez le redistribuer   * 
 *         ou le modifier suivant les termes de la GNU General Public License *
 *         telle que publiée par la Free Software Foundation ;                *
 *         soit la version 3 de la licence, soit (à votre gré) toute version  *
 *         ultérieure.                                                        *
 *         Vous devez avoir reçu une copie de la GNU General Public License   *
 *         en même temps que ce programme ; si ce n'est pas le cas, consultez *
 *            <http://www.gnu.org/licenses/gpl.html>                          *
 *                                                                            *
 * @section disclaimer Disclaimer                                             *
 *    [EN] This program is distributed in the hope that it will be useful,    *
 *         but WITHOUT ANY WARRANTY; without even the implied warranty of     *
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.               *
 *    [FR] Ce programme est distribué dans l'espoir qu'il sera utile,         *
 *         mais SANS AUCUNE GARANTIE, sans même la garantie implicite de      *
 *         VALEUR MARCHANDE ou FONCTIONNALITE POUR UN BUT PARTICULIER.        *
 *                                                                            *
\** ************************************************************************ **/
package fr.utils.geodesic.adapters;

import java.text.NumberFormat;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.vector.DoubleVector;
import fr.java.math.geometry.Point;
import fr.java.math.geometry.space.Vector3D;
import fr.java.math.geometry.space.Vector4D;

public class AdapterVector4D extends AdapterVectorDouble.Editable implements DoubleVector.Editable, Vector4D.Editable {
	private static final long serialVersionUID = 1L;

	private double x, y, z, w;

	public AdapterVector4D() {
	    this(0, 0, 0, 0);
	}
	public AdapterVector4D(final double _x, final double _y, final double _z, final double _w) {
		super(4);
		x = _x;
		y = _y;
		z = _z;
		w = _w;
	}
	public AdapterVector4D(final AdapterVector3D _d, final double _w) {
		this(_d.getX(), _d.getY(), _d.getZ(), _w);
	}
	public AdapterVector4D(final AdapterVector4D _d) {
		this(_d.x, _d.y, _d.z, _d.w);
	}

	@Override
	public final void 				set(final double _value) {
		x = _value;
		y = _value;
		z = _value;
		w = 1.0f;
	}
	@Override
	public final void 				set(final double _x, final double _y, final double _z, final double _w) {
		this.x = _x;
		this.y = _y;
		this.z = _z;
		this.w = _w;
	}
	@Override
	public final void 				set(final double[] _value) {
		assert(_value.length >= 4);
		x = _value[0];
		y = _value[1];
		z = _value[2];
		w = _value[3];
	}
	@Override
	public final void 				set(final Number _value) {
		x = _value.doubleValue();
		y = _value.doubleValue();
		z = _value.doubleValue();
		w = 1.0f;
	}
	@Override
	public final void 				set(final Number _x, final Number _y, final Number _z, final Number _w) {
		this.x = _x.doubleValue();
		this.y = _y.doubleValue();
		this.z = _z.doubleValue();
		this.w = _w.doubleValue();
	}
	@Override
	public final void 				set(final Number[] _value) {
		assert(_value.length >= 4);
		x = _value[0].doubleValue();
		y = _value[1].doubleValue();
		z = _value[2].doubleValue();
		w = _value[3].doubleValue();
	}
	@Override
	public final void 				set(final Vector4D _d) {
		x = _d.getX();
		y = _d.getY();
		z = _d.getZ();
		w = _d.getW();
	}

	@Override
	public final void 				setX(final double _x) {
		this.x = _x;
	}
	@Override
	public final double 			getX() {
		return x;
	}

	@Override
	public final void 				setY(final double _y) {
		this.y = _y;
	}
	@Override
	public final double 			getY() {
		return y;
	}

	@Override
	public final void 				setZ(final double _z) {
		this.z = _z;
	}
	@Override
	public final double 			getZ() {
		return z;
	}

	@Override
	public final void 				setW(final double _w) {
		this.w = _w;
	}
	@Override
	public final double 			getW() {
		return w;
	}

	@Override
	public void 					setMagnitude(double _mag) {
	        double old_mag = Math.sqrt((x*x + y*y + z*z + w*w));
	        
	        x = x * _mag / old_mag;
	        y = y * _mag / old_mag;
	        z = z * _mag / old_mag;
	        w = w * _mag / old_mag;
	}
	@Override
	public double 					getMagnitude() {
		return Math.sqrt((x*x + y*y + z*z + w*w));
	}

	@Override
	public final AdapterVector4D 	plus(final double _t) {
		return new AdapterVector4D(x+_t, y+_t, z+_t, w+_t);
	}
	@Override
	public final AdapterVector4D 	plus(final double _t, final double _u, final double _v, final double _w) {
		return new AdapterVector4D(x+_t, y+_u, z+_v, w+_w);
	}
	@Override
	public final AdapterVector4D 	plus(final double[] _v) {
		assert(_v.length >= 4);
		return new AdapterVector4D(x+_v[0], y+_v[1], z+_v[2], w+_v[3]);
	}
	@Override
	public final AdapterVector4D 	plus(final Number _t) {
		return new AdapterVector4D(x+_t.doubleValue(), y+_t.doubleValue(), z+_t.doubleValue(), w+_t.doubleValue());
	}
	@Override
	public final AdapterVector4D 	plus(final Number _x, final Number _y, final Number _z, final Number _w) {
		return new AdapterVector4D(x+_x.doubleValue(), y+_y.doubleValue(), z+_z.doubleValue(), w+_w.doubleValue());
	}
	@Override
	public final AdapterVector4D 	plus(final Number[] _v) {
		assert(_v.length >= 4);
		return new AdapterVector4D(x+_v[0].doubleValue(), y+_v[1].doubleValue(), z+_v[2].doubleValue(), w+_v[3].doubleValue());
	}
	@Override
	public final AdapterVector4D 	plus(final Vector4D _d) {
		return new AdapterVector4D(x+_d.getX(), y+_d.getY(), z+_d.getZ(), w+_d.getW());
	}

	@Override
	public final AdapterVector4D 	plusEquals(final double _t) {
		x += _t;
		y += _t;
		z += _t;
		return this;
	}
	@Override
	public final AdapterVector4D 	plusEquals(final double _t, final double _u, final double _v, final double _w) {
		x += _t;
		y += _u;
		z += _v;
		w += _w;
		return this;
	}
	@Override
	public final AdapterVector4D 	plusEquals(final double[] _v) {
		assert(_v.length >= 4);
		x += _v[0];
		y += _v[1];
		z += _v[2];
		w += _v[3];
		return this;
	}
	@Override
	public final AdapterVector4D 	plusEquals(final Number _t) {
		x += _t.doubleValue();
		y += _t.doubleValue();
		z += _t.doubleValue();
		return this;
	}
	@Override
	public final AdapterVector4D 	plusEquals(final Number _x, final Number _y, final Number _z, final Number _w) {
		x += _x.doubleValue();
		y += _y.doubleValue();
		z += _z.doubleValue();
		z += _w.doubleValue();
		return this;
	}
	@Override
	public final AdapterVector4D 	plusEquals(final Number[] _v) {
		assert(_v.length >= 4);
		x += _v[0].doubleValue();
		y += _v[1].doubleValue();
		z += _v[2].doubleValue();
		w += _v[3].doubleValue();
		return this;
	}
	@Override
	public final AdapterVector4D 	plusEquals(final Vector4D _d) {
		x += _d.getX();
		y += _d.getY();
		z += _d.getZ();
		w += _d.getW();
		return this;
	}

	@Override
	public final AdapterVector4D 	minus(final double _t) {
		return new AdapterVector4D(x-_t, y-_t, z-_t, w-_t);
	}
	@Override
	public final AdapterVector4D 	minus(final double _t, final double _u, final double _v, final double _w) {
		return new AdapterVector4D(x-_t, y-_u, z-_v, w-_w);
	}
	@Override
	public final AdapterVector4D 	minus(final double[] _v) {
		assert(_v.length >= 4);
		return new AdapterVector4D(x-_v[0], y-_v[1], z-_v[2], w-_v[3]);
	}
	@Override
	public final AdapterVector4D 	minus(final Number _t) {
		return new AdapterVector4D(x-_t.doubleValue(), y-_t.doubleValue(), z-_t.doubleValue(), w-_t.doubleValue());
	}
	@Override
	public final AdapterVector4D 	minus(final Number _x, final Number _y, final Number _z, final Number _w) {
		return new AdapterVector4D(x-_x.doubleValue(), y-_y.doubleValue(), z-_z.doubleValue(), w-_w.doubleValue());
	}
	@Override
	public final AdapterVector4D 	minus(final Number[] _v) {
		assert(_v.length >= 4);
		return new AdapterVector4D(x-_v[0].doubleValue(), y-_v[1].doubleValue(), z-_v[2].doubleValue(), w-_v[3].doubleValue());
	}
	@Override
	public final AdapterVector4D 	minus(final Vector4D _d) {
		return new AdapterVector4D(x-_d.getX(), y-_d.getY(), z-_d.getZ(), w-_d.getW());
	}

	@Override
	public final AdapterVector4D 	minusEquals(final double _t) {
		x -= _t;
		y -= _t;
		z -= _t;
		w -= _t;
		return this;
	}
	@Override
	public final AdapterVector4D 	minusEquals(final double _t, final double _u, final double _v, final double _w) {
		x -= _t;
		y -= _u;
		z -= _v;
		w -= _w;
		return this;
	}
	@Override
	public final AdapterVector4D 	minusEquals(final double[] _v) {
		assert(_v.length >= 4);
		x -= _v[0];
		y -= _v[1];
		z -= _v[2];
		w -= _v[3];
		return this;
	}
	@Override
	public final AdapterVector4D 	minusEquals(final Number _t) {
		x -= _t.doubleValue();
		y -= _t.doubleValue();
		z -= _t.doubleValue();
		w -= _t.doubleValue();
		return this;
	}
	@Override
	public final AdapterVector4D 	minusEquals(final Number _x, final Number _y, final Number _z, final Number _w) {
		x -= _x.doubleValue();
		y -= _y.doubleValue();
		z -= _z.doubleValue();
		w -= _w.doubleValue();
		return this;
	}
	@Override
	public final AdapterVector4D 	minusEquals(final Number[] _v) {
		assert(_v.length >= 4);
		x -= _v[0].doubleValue();
		y -= _v[1].doubleValue();
		z -= _v[2].doubleValue();
		w -= _v[3].doubleValue();
		return this;
	}
	@Override
	public final AdapterVector4D 	minusEquals(final Vector4D _v) {
		assert(_v.size() >= 4);
		x -= _v.getX();
		y -= _v.getY();
		z -= _v.getZ();
		w -= _v.getW();
		return this;
	}

	@Override
	public final AdapterVector4D 	times(final double _t) {
		return new AdapterVector4D(x*_t, y*_t, z*_t, w*_t);
	}
	@Override
	public final AdapterVector4D 	times(final double _t, final double _u, final double _v, final double _w) {
		return new AdapterVector4D(x*_t, y*_u, z*_v, w*_w);
	}
	@Override
	public final AdapterVector4D 	times(final double[] _v) {
		assert(_v.length >= 4);
		return new AdapterVector4D(x*_v[0], y*_v[1], z*_v[2], w*_v[3]);
	}
	@Override
	public final AdapterVector4D 	times(final Number _t) {
		return new AdapterVector4D(x*_t.doubleValue(), y*_t.doubleValue(), z*_t.doubleValue(), w*_t.doubleValue());
	}
	@Override
	public final AdapterVector4D 	times(final Number _x, final Number _y, final Number _z, final Number _w) {
		return new AdapterVector4D(x*_x.doubleValue(), y*_y.doubleValue(), z*_z.doubleValue(), w*_w.doubleValue());
	}
	@Override
	public final AdapterVector4D 	times(final Number[] _v) {
		assert(_v.length >= 4);
		return new AdapterVector4D(x*_v[0].doubleValue(), y*_v[1].doubleValue(), z*_v[2].doubleValue(), w*_v[3].doubleValue());
	}
	@Override
	public final AdapterVector4D 	times(final Vector4D _d) {
		return new AdapterVector4D(x*_d.getX(), y*_d.getY(), z*_d.getZ(), w*_d.getW());
	}

	@Override
	public final AdapterVector4D 	timesEquals(final double _t) {
		x *= _t;
		y *= _t;
		z *= _t;
		w *= _t;
		return this;
	}
	@Override
	public final AdapterVector4D 	timesEquals(final double _t, final double _u, final double _v, final double _w) {
		x *= _t;
		y *= _u;
		z *= _v;
		w *= _w;
		return this;
	}
	@Override
	public final AdapterVector4D 	timesEquals(final double[] _v) {
		assert(_v.length >= 4);
		x *= _v[0];
		y *= _v[1];
		z *= _v[2];
		w *= _v[3];
		return this;
	}
	@Override
	public final AdapterVector4D 	timesEquals(final Number _t) {
		x *= _t.doubleValue();
		y *= _t.doubleValue();
		z *= _t.doubleValue();
		w *= _t.doubleValue();
		return this;
	}
	@Override
	public final AdapterVector4D 	timesEquals(final Number _x, final Number _y, final Number _z, final Number _w) {
		x *= _x.doubleValue();
		y *= _y.doubleValue();
		z *= _z.doubleValue();
		w *= _w.doubleValue();
		return this;
	}
	@Override
	public final AdapterVector4D 	timesEquals(final Number[] _v) {
		assert(_v.length >= 4);
		x *= _v[0].doubleValue();
		y *= _v[1].doubleValue();
		z *= _v[2].doubleValue();
		w *= _v[3].doubleValue();
		return this;
	}
	@Override
	public final AdapterVector4D 	timesEquals(final Vector4D _d) {
		x *= _d.getX();
		y *= _d.getY();
		z *= _d.getZ();
		w *= _d.getW();
		return this;
	}

	@Override
	public final AdapterVector4D 	divides(final double _t) {
		if(_t == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector4D(x/_t, y/_t, z/_t, w/_t);
	}
	@Override
	public final AdapterVector4D 	divides(final double _t, final double _u, final double _v, final double _w) {
		if(_t == 0 || _u == 0 || _v == 0 || _w == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector4D(x/_t, y/_u, z/_v, w/_w);
	}
	@Override
	public final AdapterVector4D 	divides(final double[] _v) {
		assert(_v.length >= 4);
		if(_v[0] == 0 || _v[1] == 0 || _v[2] == 0 || _v[3] == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector4D(x/_v[0], y/_v[1], z/_v[2], w/_v[3]);
	}
	@Override
	public final AdapterVector4D 	divides(final Number _t) {
		if(_t.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector4D(x/_t.doubleValue(), y/_t.doubleValue(), z/_t.doubleValue(), w/_t.doubleValue());
	}
	@Override
	public final AdapterVector4D 	divides(final Number _x, final Number _y, final Number _z, final Number _w) {
		if(_x.doubleValue() == 0 || _y.doubleValue() == 0 || _z.doubleValue() == 0 || _w.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector4D(x/_x.doubleValue(), y/_y.doubleValue(), z/_z.doubleValue(), w/_w.doubleValue());
	}
	@Override
	public final AdapterVector4D 	divides(final Number[] _v) {
		assert(_v.length >= 4);
		if(_v[0].doubleValue() == 0 || _v[1].doubleValue() == 0 || _v[2].doubleValue() == 0 || _v[3].doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector4D(x/_v[0].doubleValue(), y/_v[1].doubleValue(), z/_v[2].doubleValue(), w/_v[3].doubleValue());
	}
	@Override
	public final AdapterVector4D 	divides(final Vector4D _d) {
		if(_d.getX() == 0 || _d.getY() == 0 || _d.getZ() == 0 || _d.getW() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector4D(x/_d.getX(), y/_d.getY(), z/_d.getZ(), w/_d.getW());
	}

	@Override
	public final AdapterVector4D 	dividesEquals(final double _t) {
		if(_t == 0) throw new RuntimeException("Divide by 0");
		x /= _t;
		y /= _t;
		z /= _t;
		w /= _t;	
		return this;
	}
	@Override
	public final AdapterVector4D 	dividesEquals(final double _t, final double _u, final double _v, final double _w) {
		if(_t == 0 || _u == 0 || _v == 0 || _w == 0) throw new RuntimeException("Divide by 0");
		x /= _t;
		y /= _u;
		z /= _v;
		w /= _w;
		return this;
	}
	@Override
	public final AdapterVector4D 	dividesEquals(final double[] _v) {
		assert(_v.length >= 4);
		if(_v[0] == 0 || _v[1] == 0 || _v[2] == 0 || _v[3] == 0) throw new RuntimeException("Divide by 0");
		x /= _v[0];
		y /= _v[1];
		z /= _v[2];
		w /= _v[3];
		return this;
	}
	@Override
	public final AdapterVector4D 	dividesEquals(final Number _t) {
		if(_t.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _t.doubleValue();
		y /= _t.doubleValue();
		z /= _t.doubleValue();
		w /= _t.doubleValue();	
		return this;
	}
	@Override
	public final AdapterVector4D 	dividesEquals(final Number _x, final Number _y, final Number _z, final Number _w) {
		if(_x.doubleValue() == 0 || _y.doubleValue() == 0 || _z.doubleValue() == 0 || _w.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _x.doubleValue();
		y /= _y.doubleValue();
		z /= _z.doubleValue();
		w /= _w.doubleValue();	
		return this;
	}
	@Override
	public final AdapterVector4D 	dividesEquals(final Number[] _v) {
		assert(_v.length >= 4);
		if(_v[0].doubleValue() == 0 || _v[1].doubleValue() == 0 || _v[2].doubleValue() == 0 || _v[3].doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _v[0].doubleValue();
		y /= _v[1].doubleValue();
		z /= _v[2].doubleValue();
		w /= _v[3].doubleValue();
		return this;
	}
	@Override
	public final AdapterVector4D 	dividesEquals(final Vector4D _d) {
		if(_d.getX() == 0 || _d.getY() == 0 || _d.getZ() == 0 || _d.getW() == 0) throw new RuntimeException("Divide by 0");
		x /= _d.getX();
		y /= _d.getY();
		z /= _d.getZ();
		w /= _d.getW();
		return this;
	}

	@Override
	public Vector3D downgrade() {
		return new AdapterVector3D(getW() * getX(), getW() * getY(), getW() * getZ());
	}

	@Override
	public final boolean 		isEqual(final Number _t) {
		return (x == _t.doubleValue() && y == _t.doubleValue() && z == _t.doubleValue() && w == _t.doubleValue()) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final Number[] _v) {
		assert(_v.length == 4);
		return (x == _v[0].doubleValue() && y == _v[1].doubleValue() && z == _v[2].doubleValue() && w == _v[3].doubleValue()) ? true : false;
	}

	@Override
	public final boolean 		isEqual(final double _t) {
		return (x == _t && y == _t && z == _t && w == _t) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final double[] _v) {
		assert(_v.length == 4);
		return (x == _v[0] && y == _v[1] && z == _v[2] && w == _v[3]) ? true : false;
	}
	public final boolean 		isEqual(final double _u, final double _v, final double _w, final double _z) {
		return (x == _u && y == _v && z == _w && w == _z) ? true : false;
	}
	public final boolean 		isEqual(final Vector4D _other) {
		return (x == _other.getX() && y == _other.getY() && z == _other.getZ() && w == _other.getW()) ? true : false;
	}

	@Override
	public final boolean 		isDifferent(final Number _t) {
		return (x != _t.doubleValue() || y != _t.doubleValue() || z != _t.doubleValue()) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final Number[] _v) {
		assert(_v.length == 4);
		return (x != _v[0].doubleValue() || y != _v[1].doubleValue() || z != _v[2].doubleValue() || w != _v[3].doubleValue()) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final NumberVector _v) {
		assert(_v.size() == 4);
		return (x != _v.getNumber(0).doubleValue() || y != _v.getNumber(1).doubleValue() || z != _v.getNumber(2).doubleValue() || w != _v.getNumber(3).doubleValue()) ? true : false;
	}

	@Override
	public final boolean 		isDifferent(final double _t) {
		return (x != _t || y != _t || z != _t) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final double[] _v) {
		assert(_v.length == 4);
		return (x != _v[0] || y != _v[1] || z != _v[2] || w != _v[3]) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final DoubleVector _v) {
		assert(_v.size() == 4);
		return (x != _v.get(0) || y != _v.get(1) || z != _v.get(2) || w != _v.get(3)) ? true : false;
	}

	public final boolean 		isDifferent(final double _u, final double _v, final double _w, final double _z) {
		return (x != _u || y != _v || z != _w || w != _z) ? true : false;
	}
	public final boolean 		isDifferent(final Vector4D _other) {
		return (x != _other.getX() || y != _other.getY() || z != _other.getZ() || w != _other.getW()) ? true : false;
	}
	

	@Override
	public final boolean 		isColinear(final NumberVector _other) {
		return false;
	}
	@Override
	public final boolean 		isColinear(final DoubleVector _vector) {
		return false;
	}
	public final boolean 		isColinear(final Vector4D _other) {
		return false;
	}

	public final boolean 		isColinearToSegment(final Vector4D _A, final Vector4D _B) {
		return false;
	}
	public final boolean 		isColinearToLine(final Vector4D _P, final Vector4D _N) {
		return false;
	}


	@Override
	public final boolean 		isOrthogonal(final NumberVector _vec) {
		return false;
	}
	@Override
	public final boolean 		isOrthogonal(final DoubleVector _vector) {
		return false;
	}
	public final boolean 		isOrthogonal(final Vector4D _other) {
		return false;
	}

	public final boolean 		isOrthogonalToSegment(final Vector4D _A, final Vector4D _B) {
		return false;
	}
	public final boolean 		isOrthogonalToLine(final Vector4D _P, final Vector4D _N) {
		return false;
	}

	@Override
	public final AdapterVector4D 		clone() {
		return new AdapterVector4D(x, y, z, w);
	}
	@Override
	public final AdapterVector4D cloneEditable() {
		return new AdapterVector4D(x, y, z, w);
	}

	@Override
	public final AdapterVector4D 		abs() {
		return new AdapterVector4D(Math.abs(x), Math.abs(y), Math.abs(z), Math.abs(w));
	}
	@Override
	public final AdapterVector4D 		negate() {
		return new AdapterVector4D(-x, -y, -z, -w);
	}
	@Override
	public final AdapterVector4D 		normalized() {
		double length = norm();
		if(length < Point.EPSILON)
			return this;
		double invLength = 1.0f / length;
		return new AdapterVector4D(x * invLength, y * invLength, z * invLength, w * invLength);
	}


	@Override
	public final double 		norm(Norm _norm/*, double _p*/) {
		double p = 1;
		switch(_norm) {
		case EuclidianSquare:	return x*x+y*y+z*z;

		case Euclidian:			return Math.sqrt(x*x+y*y+z*z);

		case Manhattan:			return Math.abs( x ) + Math.abs( y ) + Math.abs( z );

		case P:					return Math.pow(  Math.pow( Math.abs( x ), p) +  Math.pow( Math.abs( y ), p) +  Math.pow( Math.abs( z ), p), 1d/p);

		case Maximum:			return Math.abs( x ) > Math.abs( y ) ? 
											( Math.abs( x ) > Math.abs( z ) ? 
													( Math.abs( x ) > Math.abs( w ) ? Math.abs( x ) : Math.abs( w ) )
													:
													( Math.abs( z ) > Math.abs( w ) ? Math.abs( z ) : Math.abs( w ) )
											)
											:
											( Math.abs( y ) > Math.abs( z ) ? 
													( Math.abs( y ) > Math.abs( w ) ? Math.abs( y ) : Math.abs( w ) )
													:
													( Math.abs( z ) > Math.abs( w ) ? Math.abs( z ) : Math.abs( w ) ) )
											;
								
		default:				throw new NotYetImplementedException();
		}
	}
	@Override
	public final double 		magnitude() {
		return norm();
	}
	@Override
	public final double 		norm() {
		return Math.sqrt(norm2());
	}
	@Override
	public final double 		norm2() {
		return x*x + y*y + z*z + w*w;
	}

	@Override
	public final Number 		dotProduct(NumberVector _v) {
		return 0;
	}
	public final static double 	dotProduct(final Vector4D _a, final Vector4D _b) {
		return -1;//a.x * b.x + a.y * b.y;
	}

	@Override
	public final NumberVector 		crossProduct(NumberVector _v) {
		throw new NotYetImplementedException();
	}
	public final static AdapterVector4D crossProduct(final AdapterVector4D _a, final AdapterVector4D _b) {
		return new AdapterVector4D(  _a.y*_b.x - _a.x*_b.y, 
							_a.x*_b.y - _a.y*_b.x, 0, 0  );
	}


	public final double euclydianDistance(final Vector4D _v) {
		return (double) Math.sqrt((x-_v.getX())*(x-_v.getX()) + (y-_v.getY())*(y-_v.getY()) + (z-_v.getZ())*(z-_v.getZ()) + (w-_v.getW())*(w-_v.getW()));
	}
	public final double euclydianDistance2(final Vector4D _v) {
		return (double) (x-_v.getX())*(x-_v.getX()) + (y-_v.getY())*(y-_v.getY()) + (z-_v.getZ())*(z-_v.getZ()) + (w-_v.getW())*(w-_v.getW());
	}


	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		AdapterVector4D other = (AdapterVector4D) obj;
		if(Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if(Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if(Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		if(Double.doubleToLongBits(w) != Double.doubleToLongBits(other.w))
			return false;
		return true;
	}


	@Override
	public int compareTo(Object obj) {
		if(this == obj)
			return 0;
		if(obj == null)
			return 1;
		if(getClass() != obj.getClass())
			return 1;
		AdapterVector4D other = (AdapterVector4D) obj;
		if(Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return Double.doubleToLongBits(x) > Double.doubleToLongBits(other.x) ? 1 : -1;
		if(Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return Double.doubleToLongBits(y) > Double.doubleToLongBits(other.y) ? 1 : -1;
		if(Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return Double.doubleToLongBits(z) > Double.doubleToLongBits(other.z) ? 1 : -1;
		if(Double.doubleToLongBits(w) != Double.doubleToLongBits(other.w))
			return Double.doubleToLongBits(w) > Double.doubleToLongBits(other.w) ? 1 : -1;
		return -1;
	}


	@Override
	public int hashCode() {
		final long prime = 31;
		long result = 1;
		result = prime * result + Double.doubleToLongBits(x);
		result = prime * result + Double.doubleToLongBits(y);
		result = prime * result + Double.doubleToLongBits(z);
		result = prime * result + Double.doubleToLongBits(w);
		return (int) result;
	}


	@Override
	public final String toString() {
		return "(" + x + "," + y + "," + z + "," + w + ")";
	}
	@Override
	public final String toString(NumberFormat _nf) {
		return "(" + _nf.format(x) + "," + _nf.format(y) + "," + _nf.format(z) + "," + _nf.format(w) + ")";
	}

}

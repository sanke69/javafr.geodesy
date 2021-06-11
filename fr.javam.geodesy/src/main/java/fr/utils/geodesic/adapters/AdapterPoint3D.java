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
import java.text.NumberFormat;

import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.vector.DoubleVector;
import fr.java.math.algebra.vector.generic.Vector3D;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.space.Dimension3D;
import fr.java.math.geometry.space.Point3D;
import fr.java.math.topology.Coordinate;
import fr.java.math.topology.CoordinateSystem;

public class AdapterPoint3D implements Point3D.Editable {
	private static final long serialVersionUID = 1369L;

	private double x, y, z;

	public AdapterPoint3D() {
	    this(0, 0, 0);
	}
	public AdapterPoint3D(final double _x, final double _y, final double _z) {
		x = _x;
		y = _y;
		z = _z;
	}
	public AdapterPoint3D(final AdapterPoint3D _copy) {
	    this(_copy.x, _copy.y, _copy.z);
	}

	@Override public CoordinateSystem getCoordinateSystem() { return CoordinateSystem.Cartesian2D; }

	@Override
	public final void 			set(final double _value) {
		this.x = _value;
		this.y = _value;
		this.z = _value;
	}
	@Override
	public final void 			set(final double[] _values) {
		assert (_values.length >= 3);
		set(_values[0], _values[1], _values[2]);
	}
	@Override
	public final void 			set(final double _x, final double _y, final double _z) {
		this.x = _x;
		this.y = _y;
		this.z = _z;
	}
	@Override
	public final void 			set(final Number _value) {
		x = _value.doubleValue();
		y = _value.doubleValue();
		z = _value.doubleValue();
	}
	@Override
	public final void 			set(final Number[] _values) {
		assert (_values.length >= 3);
		set(_values[0].doubleValue(), _values[1].doubleValue(), _values[2].doubleValue());
	}
	@Override
	public void 				set(final Number _x, final Number _y, final Number _z) {
		x = _x.doubleValue();
		y = _y.doubleValue();
		z = _z.doubleValue();
	}
	@Override
	public final void 			set(final Point3D _pt) {
		x = _pt.getX();
		y = _pt.getY();
		z = _pt.getZ();
	}
	@Override
	public final void 			set(final Vector3D _vec) {
		x = _vec.getX();
		y = _vec.getY();
		z = _vec.getZ();
	}

	@Override
	public final void 			setX(final double _x) {
		x = _x;
	}
	@Override
	public final double 		getX() {
		return x;
	}

	@Override
	public final void 			setY(final double _y) {
		y = _y;
	}
	@Override
	public final double 		getY() {
		return y;
	}

	@Override
	public final void 			setZ(final double _z) {
		z = _z;
	}
	@Override
	public final double 		getZ() {
		return z;
	}

	@Override
	public final AdapterPoint3D plus(final double _t) {
		return new AdapterPoint3D(x + _t, y + _t, z + _t);
	}
	@Override
	public final AdapterPoint3D plus(final double _u, final double _v, final double _w) {
		return new AdapterPoint3D(x + _u, y + _v, z + _w);
	}
	@Override
	public final AdapterPoint3D plus(final double[] _v) {
		assert(_v.length >= 3);
		return new AdapterPoint3D(x + _v[0], y + _v[1], z + _v[2]);
	}
	@Override
	public final AdapterPoint3D plus(final Number _t) {
		return new AdapterPoint3D(x + _t.doubleValue(), y + _t.doubleValue(), z + _t.doubleValue());
	}
	@Override
	public final AdapterPoint3D plus(final Number _x, final Number _y, final Number _z) {
		return new AdapterPoint3D(x + _x.doubleValue(), y + _y.doubleValue(), z + _z.doubleValue());
	}
	@Override
	public final AdapterPoint3D plus(final Number[] _v) {
		assert(_v.length >= 3);
		return new AdapterPoint3D(x + _v[0].doubleValue(), y + _v[1].doubleValue(), z + _v[2].doubleValue());
	}
	@Override
	public final AdapterPoint3D plus(final Point3D _pt) {
		return new AdapterPoint3D(x + _pt.getX(), y + _pt.getY(), z + _pt.getZ());
	}
	@Override
	public final AdapterPoint3D plus(final Vector3D _vec) {
		return new AdapterPoint3D(x + _vec.getX(), y + _vec.getY(), z + _vec.getZ());
	}
	@Override
	public final AdapterPoint3D plus(final Dimension3D _d) {
		return new AdapterPoint3D(x + _d.getWidth(), y + _d.getHeight(), z + _d.getDepth());
	}
	@Override
	public final AdapterPoint3D plus(final Coordinate.ThreeDims _c) {
		return new AdapterPoint3D(x + _c.getFirst(), y + _c.getSecond(), z + _c.getThird());
	}
	@Override
	public final AdapterPoint3D plus(final Dimension.ThreeDims _d) {
		return new AdapterPoint3D(x + _d.getWidth(), y + _d.getHeight(), z + _d.getDepth());
	}
//	@Override
	public final AdapterPoint3D plus(final DoubleVector _v) {
		assert(_v.size() >= 3);
		return new AdapterPoint3D(x + _v.get(0), y + _v.get(1), z + _v.get(2));
	}

	@Override
	public final AdapterPoint3D plusEquals(final double _t) {
		x += _t;
		y += _t;
		z += _t;
		return this;
	}
	@Override
	public final AdapterPoint3D plusEquals(final double _u, final double _v, final double _w) {
		x += _u;
		y += _v;
		z += _w;
		return this;
	}
	@Override
	public final AdapterPoint3D plusEquals(final double[] _v) {
		assert(_v.length >= 3);
		x += _v[0];
		y += _v[1];
		z += _v[2];
		return this;
	}
	@Override
	public final AdapterPoint3D plusEquals(final Number _t) {
		x += _t.doubleValue();
		y += _t.doubleValue();
		z += _t.doubleValue();
		return this;
	}
	@Override
	public final AdapterPoint3D plusEquals(final Number _u, final Number _v, final Number _w) {
		x += _u.doubleValue();
		y += _v.doubleValue();
		z += _w.doubleValue();
		return this;
	}
	@Override
	public final AdapterPoint3D plusEquals(final Number[] _v) {
		assert(_v.length >= 3);
		x += _v[0].doubleValue();
		y += _v[1].doubleValue();
		z += _v[2].doubleValue();
		return this;
	}
	@Override
	public final AdapterPoint3D plusEquals(final Point3D _d) {
		x += _d.getX();
		y += _d.getY();
		z += _d.getZ();
		return this;
	}
	@Override
	public final AdapterPoint3D plusEquals(final Vector3D _d) {
		x += _d.getX();
		y += _d.getY();
		z += _d.getZ();
		return this;
	}
	@Override
	public final AdapterPoint3D plusEquals(final Dimension3D _dim) {
		x += _dim.getWidth();
		y += _dim.getHeight();
		z += _dim.getDepth();
		return this;
	}
	@Override
	public final AdapterPoint3D plusEquals(final Coordinate.ThreeDims _dim) {
		x += _dim.getFirst();
		y += _dim.getSecond();
		z += _dim.getThird();
		return this;
	}
	@Override
	public final AdapterPoint3D plusEquals(final Dimension.ThreeDims _dim) {
		x += _dim.getWidth();
		y += _dim.getHeight();
		z += _dim.getDepth();
		return this;
	}
//	@Override
	public final AdapterPoint3D plusEquals(final NumberVector _v) {
		assert(_v.size() >= 3);
		x += _v.getNumber(0).doubleValue();
		y += _v.getNumber(1).doubleValue();
		z += _v.getNumber(2).doubleValue();
		return this;
	}

	@Override
	public final AdapterPoint3D minus(final double _t) {
		return new AdapterPoint3D(x - _t, y - _t, z - _t);
	}
	@Override
	public final AdapterPoint3D minus(final double _u, final double _v, final double _w) {
		return new AdapterPoint3D(x - _u, y - _v, z - _w);
	}
	@Override
	public final AdapterPoint3D minus(final double[] _v) {
		assert(_v.length >= 3);
		return new AdapterPoint3D(x - _v[0], y - _v[1], z - _v[2]);
	}
	@Override
	public final AdapterPoint3D minus(final Number _t) {
		return new AdapterPoint3D(x - _t.doubleValue(), y - _t.doubleValue(), z - _t.doubleValue());
	}
	@Override
	public final AdapterPoint3D minus(final Number _x, final Number _y, final Number _z) {
		return new AdapterPoint3D(x - _x.doubleValue(), y - _y.doubleValue(), z - _z.doubleValue());
	}
	@Override
	public final AdapterPoint3D minus(final Number[] _v) {
		assert(_v.length >= 3);
		return new AdapterPoint3D(x - _v[0].doubleValue(), y - _v[1].doubleValue(), z - _v[2].doubleValue());
	}
	@Override
	public final AdapterPoint3D minus(final Point3D _d) {
		return new AdapterPoint3D(x - _d.getX(), y - _d.getY(), z - _d.getZ());
	}
	@Override
	public final AdapterPoint3D minus(final Vector3D _d) {
		return new AdapterPoint3D(x - _d.getX(), y - _d.getY(), z - _d.getZ());
	}
	@Override
	public final AdapterPoint3D minus(final Dimension3D _d) {
		return new AdapterPoint3D(x - _d.getWidth(), y - _d.getHeight(), z - _d.getDepth());
	}
	@Override
	public final AdapterPoint3D minus(final Coordinate.ThreeDims _c) {
		return new AdapterPoint3D(x - _c.getFirst(), y - _c.getSecond(), z - _c.getThird());
	}
	@Override
	public final AdapterPoint3D minus(final Dimension.ThreeDims _d) {
		return new AdapterPoint3D(x - _d.getWidth(), y - _d.getHeight(), z - _d.getDepth());
	}
//	@Override
	public final AdapterPoint3D minus(final NumberVector _v) {
		assert(_v.size() >= 3);
		return new AdapterPoint3D(x - _v.getNumber(0).doubleValue(), y - _v.getNumber(1).doubleValue(), z - _v.getNumber(2).doubleValue());
	}
//	@Override
	public final AdapterPoint3D minus(final DoubleVector _v) {
		assert(_v.size() >= 3);
		return new AdapterPoint3D(x - _v.get(0), y - _v.get(1), z - _v.get(2));
	}

	@Override
	public final AdapterPoint3D minusEquals(final double _t) {
		x -= _t;
		y -= _t;
		z -= _t;
		return this;
	}
	@Override
	public final AdapterPoint3D minusEquals(final double _u, final double _v, final double _w) {
		x -= _u;
		y -= _v;
		z -= _w;
		return this;
	}
	@Override
	public final AdapterPoint3D minusEquals(final double[] _v) {
		assert(_v.length >= 3);
		x -= _v[0];
		y -= _v[1];
		z -= _v[2];
		return this;
	}
	@Override
	public final AdapterPoint3D minusEquals(final Number _t) {
		x -= _t.doubleValue();
		y -= _t.doubleValue();
		z -= _t.doubleValue();
		return this;
	}
	@Override
	public final AdapterPoint3D minusEquals(final Number _u, Number _v, Number _w) {
		x -= _u.doubleValue();
		y -= _v.doubleValue();
		z -= _w.doubleValue();
		return this;
	}
	@Override
	public final AdapterPoint3D minusEquals(final Number[] _v) {
		assert(_v.length >= 3);
		x -= _v[0].doubleValue();
		y -= _v[1].doubleValue();
		z -= _v[1].doubleValue();
		return this;
	}
	@Override
	public final AdapterPoint3D minusEquals(final Point3D _d) {
		x -= _d.getX();
		y -= _d.getY();
		z -= _d.getZ();
		return this;
	}
	@Override
	public final AdapterPoint3D minusEquals(final Vector3D _d) {
		x -= _d.getX();
		y -= _d.getY();
		z -= _d.getZ();
		return this;
	}
	@Override
	public final AdapterPoint3D minusEquals(final Dimension3D _dim) {
		x -= _dim.getWidth();
		y -= _dim.getHeight();
		z -= _dim.getDepth();
		return this;
	}
	@Override
	public final AdapterPoint3D minusEquals(final Coordinate.ThreeDims _pt) {
		x -= _pt.getFirst();
		y -= _pt.getSecond();
		z -= _pt.getThird();
		return this;
	}
	@Override
	public final AdapterPoint3D minusEquals(final Dimension.ThreeDims _dim) {
		x -= _dim.getWidth();
		y -= _dim.getHeight();
		z -= _dim.getDepth();
		return this;
	}
//	@Override
	public final AdapterPoint3D minusEquals(final NumberVector _v) {
		assert(_v.size() >= 3);
		x -= _v.getNumber(0).doubleValue();
		y -= _v.getNumber(1).doubleValue();
		z -= _v.getNumber(2).doubleValue();
		return this;
	}
//	@Override
	public final AdapterPoint3D minusEquals(final DoubleVector _v) {
		assert(_v.size() >= 3);
		x -= _v.get(0);
		y -= _v.get(1);
		z -= _v.get(2);
		return this;
	}

	@Override
	public final AdapterPoint3D times(final double _t) {
		return new AdapterPoint3D(x * _t, y * _t, z * _t);
	}
	@Override
	public final AdapterPoint3D times(final double _u, final double _v, final double _w) {
		return new AdapterPoint3D(x * _u, y * _v, z * _w);
	}
	@Override
	public final AdapterPoint3D times(final double[] _v) {
		assert(_v.length >= 3);
		return new AdapterPoint3D(x * _v[0], y * _v[1], z * _v[2]);
	}
	@Override
	public final AdapterPoint3D times(final Number _t) {
		return new AdapterPoint3D(x * _t.doubleValue(), y * _t.doubleValue(), z * _t.doubleValue());
	}
	@Override
	public final AdapterPoint3D times(final Number _x, final Number _y, final Number _z) {
		return new AdapterPoint3D(x * _x.doubleValue(), y * _y.doubleValue(), z * _z.doubleValue());
	}
	@Override
	public final AdapterPoint3D times(final Number[] _v) {
		assert(_v.length >= 3);
		return new AdapterPoint3D(x * _v[0].doubleValue(), y * _v[1].doubleValue(), z * _v[2].doubleValue());
	}
	@Override
	public final AdapterPoint3D times(final Vector3D _d) {
		return new AdapterPoint3D(x * _d.getX(), y * _d.getY(), z * _d.getZ());
	}
	@Override
	public final AdapterPoint3D times(final Dimension3D _dim) {
		return new AdapterPoint3D(x * _dim.getWidth(), y * _dim.getHeight(), z * _dim.getDepth());
	}
	@Override
	public final AdapterPoint3D times(final Dimension.ThreeDims _dim) {
		return new AdapterPoint3D(x * _dim.getWidth(), y * _dim.getHeight(), z * _dim.getDepth());
	}
//	@Override
	public final AdapterPoint3D times(final NumberVector _v) {
		assert(_v.size() >= 3);
		return new AdapterPoint3D(x * _v.getNumber(0).doubleValue(), y * _v.getNumber(1).doubleValue(), z * _v.getNumber(2).doubleValue());
	}
//	@Override
	public final AdapterPoint3D times(final DoubleVector _v) {
		assert(_v.size() >= 2);
		return new AdapterPoint3D(x * _v.get(0), y * _v.get(1), z * _v.get(2));
	}

	@Override
	public final AdapterPoint3D timesEquals(final double _t) {
		x *= _t;
		y *= _t;
		z *= _t;
		return this;
	}
	@Override
	public final AdapterPoint3D timesEquals(final double _u, final double _v, final double _w) {
		x *= _u;
		y *= _v;
		z *= _w;
		return this;
	}
	@Override
	public final AdapterPoint3D timesEquals(final double[] _v) {
		assert(_v.length >= 3);
		x *= _v[0];
		y *= _v[1];
		z *= _v[2];
		return this;
	}
	@Override
	public final AdapterPoint3D timesEquals(final Number _t) {
		x *= _t.doubleValue();
		y *= _t.doubleValue();
		z *= _t.doubleValue();
		return this;
	}
	@Override
	public final AdapterPoint3D timesEquals(final Number _u, final Number _v, final Number _w) {
		x *= _u.doubleValue();
		y *= _v.doubleValue();
		z *= _w.doubleValue();
		return this;
	}
	@Override
	public final AdapterPoint3D timesEquals(final Number[] _v) {
		assert(_v.length >= 3);
		x *= _v[0].doubleValue();
		y *= _v[1].doubleValue();
		z *= _v[2].doubleValue();
		return this;
	}
	@Override
	public final AdapterPoint3D timesEquals(final Vector3D _d) {
		x *= _d.getX();
		y *= _d.getY();
		z *= _d.getZ();
		return this;
	}
	@Override
	public final AdapterPoint3D timesEquals(final Dimension3D _d) {
		x *= _d.getWidth();
		y *= _d.getHeight();
		z *= _d.getDepth();
		return this;
	}
	@Override
	public final AdapterPoint3D timesEquals(final Dimension.ThreeDims _dim) {
		x *= _dim.getWidth();
		y *= _dim.getHeight();
		z *= _dim.getDepth();
		return this;
	}
//	@Override
	public final AdapterPoint3D timesEquals(final NumberVector _v) {
		assert(_v.size() >= 3);
		x *= _v.getNumber(0).doubleValue();
		y *= _v.getNumber(1).doubleValue();
		z *= _v.getNumber(2).doubleValue();
		return this;
	}
//	@Override
	public final AdapterPoint3D timesEquals(final DoubleVector _v) {
		assert(_v.size() >= 3);
		x *= _v.get(0);
		y *= _v.get(1);
		z *= _v.get(2);
		return this;
	}

	@Override
	public final AdapterPoint3D divides(final double _t) {
		if(_t == 0) throw new RuntimeException("Divide by 0");
		return new AdapterPoint3D(x / _t, y / _t, z / _t);
	}
	public final AdapterPoint3D divides(final double _u, final double _v, final double _w) {
		if(_u == 0 || _v == 0 || _w == 0) throw new RuntimeException("Divide by 0");
		return new AdapterPoint3D(x / _u, y / _v, z / _w);
	}
	@Override
	public final AdapterPoint3D divides(final double[] _v) {
		assert(_v.length >= 3);
		if(_v[0] == 0 || _v[1] == 0 || _v[2] == 0) throw new RuntimeException("Divide by 0");
		return new AdapterPoint3D(x / _v[0], y / _v[1], z / _v[2]);
	}
	@Override
	public final AdapterPoint3D divides(final Number _t) {
		if(_t.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterPoint3D(x / _t.doubleValue(), y / _t.doubleValue(), z / _t.doubleValue());
	}
	@Override
	public final AdapterPoint3D divides(final Number _u, final Number _v, final Number _w) {
		if(_u.doubleValue() == 0 || _v.doubleValue() == 0 || _w.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterPoint3D(x / _u.doubleValue(), y / _v.doubleValue(), z / _w.doubleValue());
	}
	@Override
	public final AdapterPoint3D divides(final Number[] _v) {
		assert(_v.length >= 3);
		if(_v[0].doubleValue() == 0 || _v[1].doubleValue() == 0 || _v[2].doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterPoint3D(x / _v[0].doubleValue(), y / _v[1].doubleValue(), z / _v[2].doubleValue());
	}
	@Override
	public final AdapterPoint3D divides(final Vector3D _d) {
		if(_d.getX() == 0 || _d.getY() == 0 || _d.getZ() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterPoint3D(x / _d.getX(), y / _d.getY(), z / _d.getZ());
	}
	@Override
	public final AdapterPoint3D divides(final Dimension3D _d) {
		if(_d.getWidth() == 0 || _d.getHeight() == 0 || _d.getDepth() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterPoint3D(x / _d.getWidth(), y / _d.getHeight(), z / _d.getDepth());
	}
	@Override
	public final AdapterPoint3D divides(Dimension.ThreeDims _dim) {
		if(_dim.getWidth() == 0 || _dim.getHeight() == 0 || _dim.getDepth() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterPoint3D(x / _dim.getWidth(), y / _dim.getHeight(), z / _dim.getDepth());
	}
//	@Override
	public final AdapterPoint3D divides(final NumberVector _v) {
		assert(_v.size() >= 3);
		if(_v.getNumber(0).doubleValue() == 0 || _v.getNumber(1).doubleValue() == 0 || _v.getNumber(2).doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterPoint3D(x / _v.getNumber(0).doubleValue(), y / _v.getNumber(1).doubleValue(), z / _v.getNumber(2).doubleValue());
	}
//	@Override
	public final AdapterPoint3D divides(final DoubleVector _v) {
		assert(_v.size() >= 3);
		if(_v.get(0) == 0 || _v.get(1) == 0 || _v.get(2) == 0) throw new RuntimeException("Divide by 0");
		return new AdapterPoint3D(x / _v.get(0), y / _v.get(1), z / _v.get(2));
	}

	@Override
	public final AdapterPoint3D dividesEquals(final double _t) {
		if(_t == 0) throw new RuntimeException("Divide by 0");
		x /= _t;
		y /= _t;
		z /= _t;
		return this;
	}
	public final AdapterPoint3D dividesEquals(final double _u, final double _v, final double _w) {
		if(_u == 0 || _v == 0 || _w == 0) throw new RuntimeException("Divide by 0");
		x /= _u;
		y /= _v;
		z /= _w;
		return this;
	}
	@Override
	public final AdapterPoint3D dividesEquals(final double[] _v) {
		assert(_v.length >= 3);
		if(_v[0] == 0 || _v[1] == 0 || _v[2] == 0) throw new RuntimeException("Divide by 0");
		x /= _v[0];
		y /= _v[1];
		z /= _v[2];
		return this;
	}
	@Override
	public final AdapterPoint3D dividesEquals(final Number _t) {
		if(_t.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _t.doubleValue();
		y /= _t.doubleValue();
		z /= _t.doubleValue();
		return this;
	}
	@Override
	public final AdapterPoint3D dividesEquals(final Number _u, final Number _v, final Number _w) {
		if(_u.doubleValue() == 0 || _v.doubleValue() == 0 || _w.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _u.doubleValue();
		y /= _v.doubleValue();
		z /= _w.doubleValue();
		return this;
	}
	@Override
	public final AdapterPoint3D dividesEquals(final Number[] _v) {
		assert(_v.length >= 3);
		if(_v[0].doubleValue() == 0 || _v[1].doubleValue() == 0 || _v[2].doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _v[0].doubleValue();
		y /= _v[1].doubleValue();
		z /= _v[2].doubleValue();
		return this;
	}
	@Override
	public final AdapterPoint3D dividesEquals(final Vector3D _d) {
		if(_d.getX() == 0 || _d.getY() == 0 || _d.getZ() == 0) throw new RuntimeException("Divide by 0");
		x /= _d.getX();
		y /= _d.getY();
		z /= _d.getZ();
		return this;
	}
	@Override
	public final AdapterPoint3D dividesEquals(final Dimension3D _d) {
		if(_d.getWidth() == 0 || _d.getHeight() == 0) throw new RuntimeException("Divide by 0");
		x /= _d.getWidth();
		y /= _d.getHeight();
		z /= _d.getDepth();
		return this;
	}
	@Override
	public final AdapterPoint3D dividesEquals(final Dimension.ThreeDims _dim) {
		x *= _dim.getWidth();
		y *= _dim.getHeight();
		z *= _dim.getDepth();
		return this;
	}
//	@Override
	public final AdapterPoint3D dividesEquals(final NumberVector _v) {
		if(_v.getNumber(0).doubleValue() == 0 || _v.getNumber(1).doubleValue() == 0 || _v.getNumber(2).doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _v.getNumber(0).doubleValue();
		y /= _v.getNumber(1).doubleValue();
		z /= _v.getNumber(2).doubleValue();
		return this;
	}
//	@Override
	public final AdapterPoint3D dividesEquals(final DoubleVector _v) {
		if(_v.get(0) == 0 || _v.get(1) == 0 || _v.get(2) == 0) throw new RuntimeException("Divide by 0");
		x /= _v.get(0);
		y /= _v.get(1);
		z /= _v.get(2);
		return this;
	}

	@Override
	public final boolean 		isEqual(final double _u, final double _v, final double _w) {
		if(Double.isNaN(x) || Double.isNaN(y))
			return Double.isNaN(_u) || Double.isNaN(_v) ? true : false;
		return (x == _u && y == _v && z == _w) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final Point3D _other) {
		if(Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(z))
			return Double.isNaN(_other.getX()) || Double.isNaN(_other.getY()) ? true : false;
		return (x == _other.getX() && y == _other.getY() && z == _other.getZ()) ? true : false;
	}

	@Override
	public final boolean 		isDifferent(final double _u, final double _v, final double _w) {
		return (x != _u || y != _v || z != _w) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final Point3D _d) {
		return (x != _d.getX() || y != _d.getY() || z != _d.getZ()) ? true : false;
	}

	@Override
	public double 				norm() {
		return Math.sqrt(x*x+y*y+z*z);
	}

	@Override
	public final AdapterPoint3D clone() {
		return new AdapterPoint3D(x, y, z);
	}
	@Override
	public final AdapterPoint3D cloneEditable() {
		return new AdapterPoint3D(x, y, z);
	}
	@Override
	public final AdapterPoint3D abs() {
		return new AdapterPoint3D(Math.abs(x), Math.abs(y), Math.abs(z));
	}
	@Override
	public final AdapterPoint3D negate() {
		return new AdapterPoint3D(-x, -y, -z);
	}
	@Override
	public final AdapterPoint3D normalized() {
		double length = Math.sqrt(x*x+y*y+z*z);
		if(length < 1e-6)
			return this;
		double invLength = 1.0f / length;
		return new AdapterPoint3D(x * invLength, y * invLength, z * invLength);
	}

	@Override
	public int 					hashCode() {
		final long prime = 31;
		long result = 1;
		result = prime * result + java.lang.Double.doubleToLongBits(x);
		result = prime * result + java.lang.Double.doubleToLongBits(y);
		return (int) result;
	}

	@Override
	public boolean 				equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		AdapterPoint3D other = (AdapterPoint3D) obj;
		if(java.lang.Double.doubleToLongBits(x) != java.lang.Double.doubleToLongBits(other.x))
			return false;
		if(java.lang.Double.doubleToLongBits(y) != java.lang.Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	@Override
	public int 					compareTo(Object obj) {
		if(this == obj)
			return 0;
		if(obj == null)
			return 1;
		if(getClass() != obj.getClass())
			return 1;
		AdapterPoint3D other = (AdapterPoint3D) obj;
		if(java.lang.Double.doubleToLongBits(x) != java.lang.Double.doubleToLongBits(other.x))
			return java.lang.Double.doubleToLongBits(x) > java.lang.Double.doubleToLongBits(other.x) ? 1 : -1;
		if(java.lang.Double.doubleToLongBits(y) != java.lang.Double.doubleToLongBits(other.y))
			return java.lang.Double.doubleToLongBits(y) > java.lang.Double.doubleToLongBits(other.y) ? 1 : -1;
		return -1;
	}

	@Override
	public String 				toString() {
		return "(" + x + "," + y + ")";
	}
	@Override
	public String 				toString(NumberFormat _nf) {
		return "( " + _nf.format(x) + ";" + _nf.format(y) + " )";
	}
	@Override
	public String 				toString(DecimalFormat _df) {
		return "( " + _df.format(x) + ";" + _df.format(y) + " )";
	}

}

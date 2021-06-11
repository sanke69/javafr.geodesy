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

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.vector.DoubleVector;
import fr.java.math.algebra.vector.generic.Vector1D;
import fr.java.math.algebra.vector.generic.Vector2D;
import fr.java.math.algebra.vector.generic.Vector3D;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.Point;
import fr.java.math.geometry.plane.Dimension2D;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.topology.Coordinate;
import fr.java.math.topology.CoordinateSystem;
import fr.utils.geodesic.Vectors;

public class AdapterVector2D extends AdapterVectorDouble.Editable implements Point2D.Editable, Vector2D.Editable {
	private static final long serialVersionUID = 1369L;

	private double x, y;

	public AdapterVector2D() {
	    this(0, 0);
	}
	public AdapterVector2D(final double _x, final double _y) {
		super(2);
		x = _x;
		y = _y;
	}
	public AdapterVector2D(final AdapterVector2D _copy) {
	    this(_copy.x, _copy.y);
	}

	@Override public CoordinateSystem getCoordinateSystem() { return CoordinateSystem.Cartesian2D; }

	@Override
	public final void 			set(final double _value) {
		this.x = _value;
		this.y = _value;
	}
	@Override
	public final void 			set(final double[] _values) {
		assert (_values.length >= 2);
		set(_values[0], _values[1]);
	}
	@Override
	public final void 			set(final double _x, final double _y) {
		this.x = _x;
		this.y = _y;
	}
	@Override
	public final void 			set(final Number _value) {
		x = _value.doubleValue();
		y = _value.doubleValue();
	}
	@Override
	public final void 			set(final Number[] _values) {
		assert (_values.length >= 2);
		set(_values[0].doubleValue(), _values[1].doubleValue());
	}
	@Override
	public void 				set(final Number _x, final Number _y) {
		x = _x.doubleValue();
		y = _y.doubleValue();
	}
	@Override
	public final void 			set(final Point2D _pt) {
		x = _pt.getX();
		y = _pt.getY();
	}
	@Override
	public final void 			set(final Vector2D _vec) {
		x = _vec.getX();
		y = _vec.getY();
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
	public void 				setMagnitude(double _mag) {
	        double old_mag = Math.sqrt((x*x + y*y));
	        
	        x = x * _mag / old_mag;
	        y = y * _mag / old_mag;
	}
	@Override
	public double 				getMagnitude() {
		return Math.sqrt((x*x + y*y));
	}

	@Override
	public final AdapterVector2D plus(final double _t) {
		return new AdapterVector2D(x + _t, y + _t);
	}
	@Override
	public final AdapterVector2D plus(final double _u, final double _v) {
		return new AdapterVector2D(x + _u, y + _v);
	}
	@Override
	public final AdapterVector2D plus(final double[] _v) {
		assert(_v.length >= 2);
		return new AdapterVector2D(x + _v[0], y + _v[1]);
	}
	@Override
	public final AdapterVector2D plus(final Number _t) {
		return new AdapterVector2D(x + _t.doubleValue(), y + _t.doubleValue());
	}
	@Override
	public final AdapterVector2D plus(final Number _x, final Number _y) {
		return new AdapterVector2D(x + _x.doubleValue(), y + _y.doubleValue());
	}
	@Override
	public final AdapterVector2D plus(final Number[] _v) {
		assert(_v.length >= 2);
		return new AdapterVector2D(x + _v[0].doubleValue(), y + _v[1].doubleValue());
	}
	@Override
	public final AdapterVector2D plus(final Point2D _pt) {
		return new AdapterVector2D(x + _pt.getX(), y + _pt.getY());
	}
	@Override
	public final AdapterVector2D plus(final Vector2D _vec) {
		return new AdapterVector2D(x + _vec.getX(), y + _vec.getY());
	}
	@Override
	public final AdapterVector2D plus(final Dimension2D _d) {
		return new AdapterVector2D(x + _d.getWidth(), y + _d.getHeight());
	}
	@Override
	public final AdapterVector2D plus(final Coordinate.TwoDims _c) {
		return new AdapterVector2D(x + _c.getFirst(), y + _c.getSecond());
	}
	@Override
	public final AdapterVector2D plus(final Dimension.TwoDims _d) {
		return new AdapterVector2D(x + _d.getWidth(), y + _d.getHeight());
	}

	@Override
	public final AdapterVector2D plusEquals(final double _t) {
		x += _t;
		y += _t;
		return this;
	}
	@Override
	public final AdapterVector2D plusEquals(final double _u, final double _v) {
		x += _u;
		y += _v;
		return this;
	}
	@Override
	public final AdapterVector2D plusEquals(final double[] _v) {
		assert(_v.length >= 2);
		x += _v[0];
		y += _v[1];
		return this;
	}
	@Override
	public final AdapterVector2D plusEquals(final Number _t) {
		x += _t.doubleValue();
		y += _t.doubleValue();
		return this;
	}
	@Override
	public final AdapterVector2D plusEquals(final Number _u, final Number _v) {
		x += _u.doubleValue();
		y += _v.doubleValue();
		return this;
	}
	@Override
	public final AdapterVector2D plusEquals(final Number[] _v) {
		assert(_v.length >= 2);
		x += _v[0].doubleValue();
		y += _v[1].doubleValue();
		return this;
	}
	@Override
	public final AdapterVector2D plusEquals(final Point2D _d) {
		x += _d.getX();
		y += _d.getY();
		return this;
	}
	@Override
	public final AdapterVector2D plusEquals(final Vector2D _d) {
		x += _d.getX();
		y += _d.getY();
		return this;
	}
	@Override
	public final AdapterVector2D plusEquals(final Dimension2D _dim) {
		x += _dim.getWidth();
		y += _dim.getHeight();
		return this;
	}
	@Override
	public final AdapterVector2D plusEquals(final Coordinate.TwoDims _dim) {
		x += _dim.getFirst();
		y += _dim.getSecond();
		return this;
	}
	@Override
	public final AdapterVector2D plusEquals(final Dimension.TwoDims _dim) {
		x += _dim.getWidth();
		y += _dim.getHeight();
		return this;
	}
//	@Override
	public final AdapterVector2D plusEquals(final NumberVector _v) {
		assert(_v.size() >= 2);
		x += _v.getNumber(0).doubleValue();
		y += _v.getNumber(1).doubleValue();
		return this;
	}

	@Override
	public final AdapterVector2D minus(final double _t) {
		return new AdapterVector2D(x - _t, y - _t);
	}
	@Override
	public final AdapterVector2D minus(final double _u, final double _v) {
		return new AdapterVector2D(x - _u, y - _v);
	}
	@Override
	public final AdapterVector2D minus(final double[] _v) {
		assert(_v.length >= 2);
		return new AdapterVector2D(x - _v[0], y - _v[1]);
	}
	@Override
	public final AdapterVector2D minus(final Number _t) {
		return new AdapterVector2D(x - _t.doubleValue(), y - _t.doubleValue());
	}
	@Override
	public final AdapterVector2D minus(final Number _x, final Number _y) {
		return new AdapterVector2D(x - _x.doubleValue(), y - _y.doubleValue());
	}
	@Override
	public final AdapterVector2D minus(final Number[] _v) {
		assert(_v.length >= 2);
		return new AdapterVector2D(x - _v[0].doubleValue(), y - _v[1].doubleValue());
	}
	@Override
	public final AdapterVector2D minus(final Point2D _d) {
		return new AdapterVector2D(x - _d.getX(), y - _d.getY());
	}
	@Override
	public final AdapterVector2D minus(final Vector2D _d) {
		return new AdapterVector2D(x - _d.getX(), y - _d.getY());
	}
	@Override
	public final AdapterVector2D minus(final Dimension2D _d) {
		return new AdapterVector2D(x - _d.getWidth(), y - _d.getHeight());
	}
	@Override
	public final AdapterVector2D minus(final Coordinate.TwoDims _c) {
		return new AdapterVector2D(x - _c.getFirst(), y - _c.getSecond());
	}
	@Override
	public final AdapterVector2D minus(final Dimension.TwoDims _d) {
		return new AdapterVector2D(x - _d.getWidth(), y - _d.getHeight());
	}
//	@Override
	public final AdapterVector2D minus(final NumberVector _v) {
		assert(_v.size() >= 2);
		return new AdapterVector2D(x - _v.getNumber(0).doubleValue(), y - _v.getNumber(1).doubleValue());
	}
//	@Override
	public final AdapterVector2D minus(final DoubleVector _v) {
		assert(_v.size() >= 2);
		return new AdapterVector2D(x - _v.get(0), y - _v.get(1));
	}

	@Override
	public final AdapterVector2D minusEquals(final double _t) {
		x -= _t;
		y -= _t;
		return this;
	}
	@Override
	public final AdapterVector2D minusEquals(final double _u, final double _v) {
		x -= _u;
		y -= _v;
		return this;
	}
	@Override
	public final AdapterVector2D minusEquals(final double[] _v) {
		assert(_v.length >= 2);
		x -= _v[0];
		y -= _v[1];
		return this;
	}
	@Override
	public final AdapterVector2D minusEquals(final Number _t) {
		x -= _t.doubleValue();
		y -= _t.doubleValue();
		return this;
	}
	@Override
	public final AdapterVector2D minusEquals(final Number _u, final Number _v) {
		x -= _u.doubleValue();
		y -= _v.doubleValue();
		return this;
	}
	@Override
	public final AdapterVector2D minusEquals(final Number[] _v) {
		assert(_v.length >= 2);
		x -= _v[0].doubleValue();
		y -= _v[1].doubleValue();
		return this;
	}
	@Override
	public final AdapterVector2D minusEquals(final Point2D _d) {
		x -= _d.getX();
		y -= _d.getY();
		return this;
	}
	@Override
	public final AdapterVector2D minusEquals(final Vector2D _d) {
		x -= _d.getX();
		y -= _d.getY();
		return this;
	}
	@Override
	public final AdapterVector2D minusEquals(final Dimension2D _dim) {
		x -= _dim.getWidth();
		y -= _dim.getHeight();
		return this;
	}
	@Override
	public final AdapterVector2D minusEquals(final Coordinate.TwoDims _pt) {
		x -= _pt.getFirst();
		y -= _pt.getSecond();
		return this;
	}
	@Override
	public final AdapterVector2D minusEquals(final Dimension.TwoDims _dim) {
		x -= _dim.getWidth();
		y -= _dim.getHeight();
		return this;
	}
//	@Override
	public final AdapterVector2D minusEquals(final NumberVector _v) {
		assert(_v.size() >= 2);
		x -= _v.getNumber(0).doubleValue();
		y -= _v.getNumber(1).doubleValue();
		return this;
	}
//	@Override
	public final AdapterVector2D minusEquals(final DoubleVector _v) {
		assert(_v.size() >= 2);
		x -= _v.get(0);
		y -= _v.get(1);
		return this;
	}

	@Override
	public final AdapterVector2D times(final double _t) {
		return new AdapterVector2D(x * _t, y * _t);
	}
	public final AdapterVector2D times(final double _u, final double _v) {
		return new AdapterVector2D(x * _u, y * _v);
	}
	@Override
	public final AdapterVector2D times(final double[] _v) {
		assert(_v.length >= 2);
		return new AdapterVector2D(x * _v[0], y * _v[1]);
	}
	@Override
	public final AdapterVector2D times(final Number _t) {
		return new AdapterVector2D(x * _t.doubleValue(), y * _t.doubleValue());
	}
	@Override
	public final AdapterVector2D times(final Number _x, final Number _y) {
		return new AdapterVector2D(x * _x.doubleValue(), y * _y.doubleValue());
	}
	@Override
	public final AdapterVector2D times(final Number[] _v) {
		assert(_v.length >= 2);
		return new AdapterVector2D(x * _v[0].doubleValue(), y * _v[1].doubleValue());
	}
	@Override
	public final AdapterVector2D times(final Vector2D _d) {
		return new AdapterVector2D(x * _d.getX(), y * _d.getY());
	}
	@Override
	public final AdapterVector2D times(final Dimension2D _dim) {
		return new AdapterVector2D(x * _dim.getWidth(), y * _dim.getHeight());
	}
	@Override
	public final AdapterVector2D times(final Dimension.TwoDims _dim) {
		return new AdapterVector2D(x * _dim.getWidth(), y * _dim.getHeight());
	}
//	@Override
	public final AdapterVector2D times(final NumberVector _v) {
		assert(_v.size() >= 2);
		return new AdapterVector2D(x * _v.getNumber(0).doubleValue(), y * _v.getNumber(1).doubleValue());
	}
//	@Override
	public final AdapterVector2D times(final DoubleVector _v) {
		assert(_v.size() >= 2);
		return new AdapterVector2D(x * _v.get(0), y * _v.get(1));
	}

	@Override
	public final AdapterVector2D timesEquals(final double _t) {
		x *= _t;
		y *= _t;
		return this;
	}
	@Override
	public final AdapterVector2D timesEquals(final double _u, final double _v) {
		x *= _u;
		y *= _v;
		return this;
	}
	@Override
	public final AdapterVector2D timesEquals(final double[] _v) {
		assert(_v.length >= 2);
		x *= _v[0];
		y *= _v[1];
		return this;
	}
	@Override
	public final AdapterVector2D timesEquals(final Number _t) {
		x *= _t.doubleValue();
		y *= _t.doubleValue();
		return this;
	}
	@Override
	public final AdapterVector2D timesEquals(final Number _u, final Number _v) {
		x *= _u.doubleValue();
		y *= _v.doubleValue();
		return this;
	}
	@Override
	public final AdapterVector2D timesEquals(final Number[] _v) {
		assert(_v.length >= 2);
		x *= _v[0].doubleValue();
		y *= _v[1].doubleValue();
		return this;
	}
	@Override
	public final AdapterVector2D timesEquals(final Vector2D _d) {
		x *= _d.getX();
		y *= _d.getY();
		return this;
	}
	@Override
	public final AdapterVector2D timesEquals(final Dimension2D _d) {
		x *= _d.getWidth();
		y *= _d.getHeight();
		return this;
	}
	@Override
	public final AdapterVector2D timesEquals(final Dimension.TwoDims _dim) {
		x *= _dim.getWidth();
		y *= _dim.getHeight();
		return this;
	}
//	@Override
	public final AdapterVector2D timesEquals(final NumberVector _v) {
		assert(_v.size() >= 2);
		x *= _v.getNumber(0).doubleValue();
		y *= _v.getNumber(1).doubleValue();
		return this;
	}
//	@Override
	public final AdapterVector2D timesEquals(final DoubleVector _v) {
		assert(_v.size() >= 2);
		x *= _v.get(0);
		y *= _v.get(1);
		return this;
	}

	@Override
	public final AdapterVector2D divides(final double _t) {
		if(_t == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector2D(x / _t, y / _t);
	}
	public final AdapterVector2D divides(final double _u, final double _v) {
		if(_u == 0 || _v == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector2D(x / _u, y / _v);
	}
	@Override
	public final AdapterVector2D divides(final double[] _v) {
		assert(_v.length >= 2);
		if(_v[0] == 0 || _v[1] == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector2D(x / _v[0], y / _v[1]);
	}
	@Override
	public final AdapterVector2D divides(final Number _t) {
		if(_t.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector2D(x / _t.doubleValue(), y / _t.doubleValue());
	}
	@Override
	public final AdapterVector2D divides(final Number _u, final Number _v) {
		if(_u.doubleValue() == 0 || _v.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector2D(x / _u.doubleValue(), y / _v.doubleValue());
	}
	@Override
	public final AdapterVector2D divides(final Number[] _v) {
		assert(_v.length >= 2);
		if(_v[0].doubleValue() == 0 || _v[1].doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector2D(x / _v[0].doubleValue(), y / _v[1].doubleValue());
	}
	@Override
	public final AdapterVector2D divides(final Vector2D _d) {
		if(_d.getX() == 0 || _d.getY() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector2D(x / _d.getX(), y / _d.getY());
	}
	@Override
	public final AdapterVector2D divides(final Dimension2D _d) {
		if(_d.getWidth() == 0 || _d.getHeight() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector2D(x / _d.getWidth(), y / _d.getHeight());
	}
	@Override
	public final AdapterVector2D divides(Dimension.TwoDims _dim) {
		if(_dim.getWidth() == 0 || _dim.getHeight() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector2D(x / _dim.getWidth(), y / _dim.getHeight());
	}
//	@Override
	public final AdapterVector2D divides(final NumberVector _v) {
		assert(_v.size() >= 2);
		if(_v.getNumber(0).doubleValue() == 0 || _v.getNumber(1).doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector2D(x / _v.getNumber(0).doubleValue(), y / _v.getNumber(1).doubleValue());
	}
//	@Override
	public final AdapterVector2D divides(final DoubleVector _v) {
		assert(_v.size() >= 2);
		if(_v.get(0) == 0 || _v.get(1) == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector2D(x / _v.get(0), y / _v.get(1));
	}

	@Override
	public final AdapterVector2D dividesEquals(final double _t) {
		if(_t == 0) throw new RuntimeException("Divide by 0");
		x /= _t;
		y /= _t;
		return this;
	}
	public final AdapterVector2D dividesEquals(final double _u, final double _v) {
		if(_u == 0 || _v == 0) throw new RuntimeException("Divide by 0");
		x /= _u;
		y /= _v;
		return this;
	}
	@Override
	public final AdapterVector2D dividesEquals(final double[] _v) {
		assert(_v.length >= 2);
		if(_v[0] == 0 || _v[1] == 0) throw new RuntimeException("Divide by 0");
		x /= _v[0];
		y /= _v[1];
		return this;
	}
	@Override
	public final AdapterVector2D dividesEquals(final Number _t) {
		if(_t.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _t.doubleValue();
		y /= _t.doubleValue();
		return this;
	}
	@Override
	public final AdapterVector2D dividesEquals(final Number _u, final Number _v) {
		if(_u.doubleValue() == 0 || _v.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _u.doubleValue();
		y /= _v.doubleValue();
		return this;
	}
	@Override
	public final AdapterVector2D dividesEquals(final Number[] _v) {
		assert(_v.length >= 2);
		if(_v[0].doubleValue() == 0 || _v[1].doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _v[0].doubleValue();
		y /= _v[1].doubleValue();
		return this;
	}
	@Override
	public final AdapterVector2D dividesEquals(final Vector2D _d) {
		if(_d.getX() == 0 || _d.getY() == 0) throw new RuntimeException("Divide by 0");
		x /= _d.getX();
		y /= _d.getY();
		return this;
	}
	@Override
	public final AdapterVector2D dividesEquals(final Dimension2D _d) {
		if(_d.getWidth() == 0 || _d.getHeight() == 0) throw new RuntimeException("Divide by 0");
		x /= _d.getWidth();
		y /= _d.getHeight();
		return this;
	}
	@Override
	public final AdapterVector2D dividesEquals(final Dimension.TwoDims _dim) {
		x *= _dim.getWidth();
		y *= _dim.getHeight();
		return this;
	}
//	@Override
	public final AdapterVector2D dividesEquals(final NumberVector _v) {
		if(_v.getNumber(0).doubleValue() == 0 || _v.getNumber(1).doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _v.getNumber(0).doubleValue();
		y /= _v.getNumber(1).doubleValue();
		return this;
	}
//	@Override
	public final AdapterVector2D dividesEquals(final DoubleVector _v) {
		if(_v.get(0) == 0 || _v.get(1) == 0) throw new RuntimeException("Divide by 0");
		x /= _v.get(0);
		y /= _v.get(1);
		return this;
	}

	@Override
	public final AdapterVector2D	normalize(double _norm) {
		double length = Math.sqrt(x*x + y*y), Q = _norm / length;
		return new AdapterVector2D(x * Q, y * Q);
	}

	@Override
	public final boolean 		isEqual(final double _t) {
		if(Double.isNaN(x) || Double.isNaN(y))
			return Double.isNaN(_t) || Double.isNaN(_t) ? true : false;
		return (x == _t && y == _t) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final double _u, final double _v) {
		if(Double.isNaN(x) || Double.isNaN(y))
			return Double.isNaN(_u) || Double.isNaN(_v) ? true : false;
		return (x == _u && y == _v) ? true : false;
	}
	@Override
	public boolean 				isEqual(double[] _v) {
		if(Double.isNaN(x) || Double.isNaN(y))
			return Double.isNaN(_v[0]) || Double.isNaN(_v[1]) ? true : false;
		return _v.length == 2 && _v[0] == x && _v[1] == y;
	}
	@Override
	public final boolean 		isEqual(final Point2D _other) {
		if(Double.isNaN(x) || Double.isNaN(y))
			return Double.isNaN(_other.getX()) || Double.isNaN(_other.getY()) ? true : false;
		return (x == _other.getX() && y == _other.getY()) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final Vector2D _other) {
		if(Double.isNaN(x) || Double.isNaN(y))
			return Double.isNaN(_other.getX()) || Double.isNaN(_other.getY()) ? true : false;
		return (x == _other.getX() && y == _other.getY()) ? true : false;
	}

	@Override
	public final boolean 		isDifferent(final double _t) {
		return (x != _t || y != _t) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final double _u, final double _v) {
		return (x != _u || y != _v) ? true : false;
	}
	@Override
	public boolean 				isDifferent(double[] _v) {
		if(Double.isNaN(x) || Double.isNaN(y))
			return Double.isNaN(_v[0]) || Double.isNaN(_v[1]) ? true : false;
		return _v.length == 2 && (_v[0] != x && _v[1] != y);
	}
	@Override
	public final boolean 		isDifferent(final Point2D _d) {
		return (x != _d.getX() || y != _d.getY()) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final Vector2D _d) {
		return (x != _d.getX() || y != _d.getY()) ? true : false;
	}

	@Override
	public final AdapterVector2D clone() {
		return new AdapterVector2D(x, y);
	}
	@Override
	public final AdapterVector2D cloneEditable() {
		return new AdapterVector2D(x, y);
	}
	@Override
	public final AdapterVector2D abs() {
		return new AdapterVector2D(Math.abs(x), Math.abs(y));
	}
	@Override
	public final AdapterVector2D negate() {
		return new AdapterVector2D(-x, -y);
	}
	@Override
	public final AdapterVector2D normalized() {
		double length = getMagnitude();
		if(length < 1e-6)
			return this;
		double invLength = 1.0f / length;
		return new AdapterVector2D(x * invLength, y * invLength);
	}

	@Override
	public Vector1D 			downgrade() {
		return new AdapterVector1D(getY() * getX());
	}
	@Override
	public Vector3D 			uniform() {
		return new AdapterVector3D(getX(), getY(), 0d);
	}
	@Override
	public Vector3D 			uniform(double _w) {
		return new AdapterVector3D(getX(), getY(), _w);
	}

	@Override
	public final boolean 		isColinear(final Vector2D _vec) {
		return (x * _vec.getY()) == (y * _vec.getX());
	}
//	@Override
	public final boolean 		isColinear(final NumberVector _vec) {
		assert(_vec.size() >= 2);
		return (x * _vec.getNumber(1).doubleValue()) == (y * _vec.getNumber(0).doubleValue());
	}
//	@Override
	public final boolean 		isColinear(final DoubleVector _vec) {
		assert(_vec.size() >= 2);
		return (x * _vec.get(1)) == (y * _vec.get(0));
	}

	public final boolean 		isColinearToSegment(final Point2D _A, final Point2D _B) {
		return (x * (_B.getY() - _A.getY())) == (y * (_B.getX() - _A.getX()));
	}
	public final boolean 		isColinearToLine(final Point2D _P, final Vector2D _N) {
		return (x * _N.getY()) == (y * _N.getY());
	}

	@Override
	public final boolean 		isOrthogonal(final Vector2D _vec) {
		return (Vectors.dotProduct((Vector2D) this, _vec) < Point.EPSILON) ? true : false;
	}
//	@Override
	public final boolean 		isOrthogonal(final NumberVector _vec) {
		return (Vectors.dotProduct((Vector2D) this, _vec) < Point.EPSILON) ? true : false;
	}
//	@Override
	public final boolean 		isOrthogonal(final DoubleVector _vec) {
		return (Vectors.dotProduct((Vector2D) this, _vec) < Point.EPSILON) ? true : false;
	}
	
	public final boolean 		isOrthogonalToSegment(final Point2D _A, final Point2D _B) {
		return (Vectors.dotProduct((Vector2D) this, Vectors.delta(_B, _A)) < Point.EPSILON) ? true : false;
	}
	public final boolean 		isOrthogonalToLine(final Point2D _P, final Vector2D _N) {
		return (Vectors.dotProduct((Vector2D) this, _N) < Point.EPSILON) ? true : false;
	}

    @Override
    public final double 		dotProduct(final double _x, final double _y) {
        return x * _x + y * _y;
    }
    @Override
    public final double 		dotProduct(final Vector2D _b) {
        return x * _b.getX() + y * _b.getY();
    }
//	@Override
	public final Number 		dotProduct(final NumberVector _b) {
        return x * _b.getNumber(1).doubleValue() + y * _b.getNumber(1).doubleValue();
	}

    @Override
	public final Vector2D 		crossProduct(final double _x, final double _y) {
		throw new NotYetImplementedException();
	}
    @Override
	public final Vector2D 		crossProduct(final Vector2D _b) {
		throw new NotYetImplementedException();
	}
//	@Override
	public final NumberVector 	crossProduct(NumberVector _b) {
		throw new NotYetImplementedException();
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
		AdapterVector2D other = (AdapterVector2D) obj;
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
		AdapterVector2D other = (AdapterVector2D) obj;
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

	public double 				euclydianDistance(final Vector2D _v) {
		return (double) Math.sqrt((x-_v.getX())*(x-_v.getX()) + (y-_v.getY())*(y-_v.getY()));
	}
	public double 				euclydianDistance2(final Vector2D _v) {
		return (double) (x-_v.getX())*(x-_v.getX()) + (y-_v.getY())*(y-_v.getY());
	}

}

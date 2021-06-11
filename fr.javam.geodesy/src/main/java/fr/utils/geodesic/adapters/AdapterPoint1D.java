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

import fr.java.math.algebra.vector.generic.Vector1D;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.linear.Dimension1D;
import fr.java.math.geometry.linear.Point1D;
import fr.java.math.topology.Coordinate;
import fr.java.math.topology.CoordinateSystem;

public class AdapterPoint1D implements Point1D.Editable {
	private static final long serialVersionUID = 1369L;

	private double x;

	public AdapterPoint1D() {
	    this(0d);
	}
	public AdapterPoint1D(final double _x) {
		x = _x;
	}
	public AdapterPoint1D(final AdapterPoint1D _copy) {
	    this(_copy.x);
	}

	@Override public CoordinateSystem getCoordinateSystem() { return CoordinateSystem.Cartesian2D; }

	@Override
	public final void 			set(final double _value) {
		this.x = _value;
	}
	@Override
	public final void 			set(final double[] _values) {
		assert (_values.length > 0);
		set(_values[0]);
	}
	@Override
	public final void 			set(final Number _value) {
		this.x = _value.doubleValue();
	}
	@Override
	public final void 			set(final Number[] _values) {
		assert (_values.length > 0);
		set(_values[0]);
	}
	@Override
	public void 				set(final Point1D _pt) {
		x = _pt.getX();
	}
	@Override
	public final void 			set(final Vector1D _vec) {
		x = _vec.getX();
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
	public final AdapterPoint1D plus(final double _t) {
		return new AdapterPoint1D(x + _t);
	}
	@Override
	public final AdapterPoint1D plus(final double[] _v) {
		assert(_v.length > 0);
		return new AdapterPoint1D(x + _v[0]);
	}
	@Override
	public final AdapterPoint1D plus(final Number _t) {
		return new AdapterPoint1D(x + _t.doubleValue());
	}
	@Override
	public final AdapterPoint1D plus(final Number[] _v) {
		assert(_v.length > 0);
		return new AdapterPoint1D(x + _v[0].doubleValue());
	}
	@Override
	public final AdapterPoint1D plus(final Point1D _pt) {
		return new AdapterPoint1D(x + _pt.getX());
	}
	@Override
	public final AdapterPoint1D plus(final Vector1D _vec) {
		return new AdapterPoint1D(x + _vec.getX());
	}
	@Override
	public final AdapterPoint1D plus(final Dimension1D _d) {
		return new AdapterPoint1D(x + _d.getWidth());
	}
	@Override
	public final AdapterPoint1D plus(final Coordinate.OneDim _c) {
		return new AdapterPoint1D(x + _c.getFirst());
	}
	@Override
	public final AdapterPoint1D plus(final Dimension.OneDim _d) {
		return new AdapterPoint1D(x + _d.getWidth());
	}

	@Override
	public final AdapterPoint1D plusEquals(final Number _t) {
		x += _t.doubleValue();
		return this;
	}
	@Override
	public final AdapterPoint1D plusEquals(final Number[] _v) {
		assert(_v.length > 0);
		x += _v[0].doubleValue();
		return this;
	}	
	@Override
	public final AdapterPoint1D plusEquals(final double _t) {
		x += _t;
		return this;
	}
	@Override
	public final AdapterPoint1D plusEquals(final double[] _v) {
		assert(_v.length > 0);
		x += _v[0];
		return this;
	}
	@Override
	public final AdapterPoint1D plusEquals(final Point1D _d) {
		x += _d.getX();
		return this;
	}
	@Override
	public final AdapterPoint1D plusEquals(final Vector1D _d) {
		x += _d.getX();
		return this;
	}
	@Override
	public final AdapterPoint1D plusEquals(final Dimension1D _dim) {
		x += _dim.getWidth();
		return this;
	}
	@Override
	public final AdapterPoint1D plusEquals(final Coordinate.OneDim _c) {
		return new AdapterPoint1D(x + _c.getFirst());
	}
	@Override
	public final AdapterPoint1D plusEquals(final Dimension.OneDim _dim) {
		x += _dim.getWidth();
		return this;
	}

	@Override
	public final AdapterPoint1D minus(final double _t) {
		return new AdapterPoint1D(x - _t);
	}
	@Override
	public final AdapterPoint1D minus(final double[] _v) {
		assert(_v.length > 0);
		return new AdapterPoint1D(x - _v[0]);
	}
	@Override
	public final AdapterPoint1D minus(final Number _t) {
		return new AdapterPoint1D(x - _t.doubleValue());
	}
	@Override
	public final AdapterPoint1D minus(final Number[] _v) {
		assert(_v.length > 0);
		return new AdapterPoint1D(x - _v[0].doubleValue());
	}
	@Override
	public final AdapterPoint1D minus(final Point1D _d) {
		return new AdapterPoint1D(x - _d.getX());
	}
	@Override
	public final AdapterPoint1D minus(final Vector1D _d) {
		return new AdapterPoint1D(x - _d.getX());
	}
	@Override
	public final AdapterPoint1D minus(final Dimension1D _d) {
		return new AdapterPoint1D(x - _d.getWidth());
	}
	@Override
	public final AdapterPoint1D minus(final Coordinate.OneDim _c) {
		return new AdapterPoint1D(x - _c.getFirst());
	}
	@Override
	public final AdapterPoint1D minus(final Dimension.OneDim _d) {
		return new AdapterPoint1D(x - _d.getWidth());
	}

	@Override
	public final AdapterPoint1D minusEquals(final double _t) {
		x -= _t;
		return this;
	}
	@Override
	public final AdapterPoint1D minusEquals(final double[] _v) {
		assert(_v.length > 0);
		x -= _v[0];
		return this;
	}
	@Override
	public final AdapterPoint1D minusEquals(final Number _t) {
		x -= _t.doubleValue();
		return this;
	}
	@Override
	public final AdapterPoint1D minusEquals(final Number[] _v) {
		assert(_v.length > 0);
		x -= _v[0].doubleValue();
		return this;
	}
	@Override
	public final AdapterPoint1D minusEquals(final Point1D _d) {
		x -= _d.getX();
		return this;
	}
	@Override
	public final AdapterPoint1D minusEquals(final Vector1D _d) {
		x -= _d.getX();
		return this;
	}
	@Override
	public final AdapterPoint1D minusEquals(final Dimension1D _dim) {
		x -= _dim.getWidth();
		return this;
	}
	@Override
	public final AdapterPoint1D minusEquals(final Coordinate.OneDim _dim) {
		x -= _dim.getFirst();
		return this;
	}
	@Override
	public final AdapterPoint1D minusEquals(final Dimension.OneDim _dim) {
		x -= _dim.getWidth();
		return this;
	}

	@Override
	public final AdapterPoint1D times(final double _t) {
		return new AdapterPoint1D(x * _t);
	}
	@Override
	public final AdapterPoint1D times(final double[] _v) {
		assert(_v.length > 0);
		return new AdapterPoint1D(x * _v[0]);
	}
	@Override
	public final AdapterPoint1D times(final Number _t) {
		return new AdapterPoint1D(x * _t.doubleValue());
	}
	@Override
	public final AdapterPoint1D times(final Number[] _v) {
		assert(_v.length > 0);
		return new AdapterPoint1D(x * _v[0].doubleValue());
	}
	@Override
	public final AdapterPoint1D times(final Vector1D _d) {
		return new AdapterPoint1D(x * _d.getX());
	}
	@Override
	public final AdapterPoint1D times(final Dimension1D _d) {
		return new AdapterPoint1D(x * _d.getWidth());
	}
	@Override
	public final AdapterPoint1D times(final Dimension.OneDim _dim) {
		return new AdapterPoint1D(x * _dim.getWidth());
	}

	@Override
	public final AdapterPoint1D timesEquals(final double _t) {
		x *= _t;
		return this;
	}
	@Override
	public final AdapterPoint1D timesEquals(final double[] _v) {
		assert(_v.length > 0);
		x *= _v[0];
		return this;
	}
	@Override
	public final AdapterPoint1D timesEquals(final Number _t) {
		x *= _t.doubleValue();
		return this;
	}
	@Override
	public final AdapterPoint1D timesEquals(final Number[] _v) {
		assert(_v.length > 0);
		x *= _v[0].doubleValue();
		return this;
	}
	@Override
	public final AdapterPoint1D timesEquals(final Vector1D _d) {
		x *= _d.getX();
		return this;
	}
	@Override
	public final AdapterPoint1D timesEquals(final Dimension1D _dim) {
		x *= _dim.getWidth();
		return this;
	}
	@Override
	public final AdapterPoint1D timesEquals(final Dimension.OneDim _dim) {
		x *= _dim.getWidth();
		return this;
	}

	@Override
	public final AdapterPoint1D divides(final double _t) {
		if(_t == 0) throw new RuntimeException("Divide by 0");
		return new AdapterPoint1D(x / _t);
	}
	@Override
	public final AdapterPoint1D divides(final double[] _v) {
		assert(_v.length > 0);
		if(_v[0] == 0) throw new RuntimeException("Divide by 0");
		return new AdapterPoint1D(x / _v[0]);
	}
	@Override
	public final AdapterPoint1D divides(final Number _t) {
		if(_t.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterPoint1D(x / _t.doubleValue());
	}
	@Override
	public final AdapterPoint1D divides(final Number[] _v) {
		assert(_v.length >= 2);
		if(_v[0].doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterPoint1D(x / _v[0].doubleValue());
	}
	@Override
	public final AdapterPoint1D divides(final Vector1D _d) {
		if(_d.getX() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterPoint1D(x / _d.getX());
	}
	@Override
	public final AdapterPoint1D divides(final Dimension1D _d) {
		if(_d.getWidth() == 0) throw new RuntimeException("Divide by 0");
		x /= _d.getWidth();
		return this;
	}
	@Override
	public final AdapterPoint1D divides(final Dimension.OneDim _dim) {
		if(_dim.getWidth() == 0) throw new RuntimeException("Divide by 0");
		x /= _dim.getWidth();
		return this;
	}

	@Override
	public final AdapterPoint1D dividesEquals(final double _t) {
		if(_t == 0) throw new RuntimeException("Divide by 0");
		x /= _t;
		return this;
	}
	@Override
	public final AdapterPoint1D dividesEquals(final double[] _v) {
		assert(_v.length >= 1);
		if(_v[0] == 0) throw new RuntimeException("Divide by 0");
		x /= _v[0];
		return this;
	}
	@Override
	public final AdapterPoint1D dividesEquals(final Number _t) {
		if(_t.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _t.doubleValue();
		return this;
	}
	@Override
	public final AdapterPoint1D dividesEquals(final Number[] _v) {
		assert(_v.length > 0);
		if(_v[0].doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _v[0].doubleValue();
		return this;
	}
	@Override
	public final AdapterPoint1D dividesEquals(final Vector1D _d) {
		if(_d.getX() == 0) throw new RuntimeException("Divide by 0");
		x /= _d.getX();
		return this;
	}
	@Override
	public final AdapterPoint1D dividesEquals(final Dimension1D _dim) {
		if(_dim.getWidth() == 0) throw new RuntimeException("Divide by 0");
		x /= _dim.getWidth();
		return this;
	}
	@Override
	public final AdapterPoint1D dividesEquals(Dimension.OneDim _dim) {
		if(_dim.getWidth() == 0) throw new RuntimeException("Divide by 0");
		x /= _dim.getWidth();
		return this;
	}

	@Override
	public final boolean 		isEqual(final double _t) {
		return (x == _t) ? true : false;
	}
	@Override
	public boolean 				isEqual(Point1D _pt) {
		return x == _pt.getX();
	}

	@Override
	public final boolean 		isDifferent(final double _t) {
		return (x != _t) ? true : false;
	}
	@Override
	public boolean 				isDifferent(Point1D _pt) {
		return x != _pt.getX();
	}

	@Override
	public final AdapterPoint1D clone() {
		return new AdapterPoint1D(x);
	}
	@Override
	public final AdapterPoint1D cloneEditable() {
		return new AdapterPoint1D(x);
	}
	@Override
	public final AdapterPoint1D abs() {
		return new AdapterPoint1D(Math.abs(x));
	}
	@Override
	public final AdapterPoint1D negate() {
		return new AdapterPoint1D(-x);
	}
	@Override
	public final AdapterPoint1D normalized() {
		return new AdapterPoint1D(x);
	}

	@Override
	public int 					hashCode() {
		final long prime = 31;
		long result = 1;
		result = prime * result + java.lang.Double.doubleToLongBits(x);
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
		AdapterPoint1D other = (AdapterPoint1D) obj;

		if(java.lang.Double.doubleToLongBits(x) != java.lang.Double.doubleToLongBits(other.x))
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
		AdapterPoint1D other = (AdapterPoint1D) obj;
		if(java.lang.Double.doubleToLongBits(x) != java.lang.Double.doubleToLongBits(other.x))
			return java.lang.Double.doubleToLongBits(x) > java.lang.Double.doubleToLongBits(other.x) ? 1 : -1;
		return -1;
	}

	@Override
	public String 				toString() {
		return "(" + x + ")";
	}
	@Override
	public String 				toString(NumberFormat _nf) {
		return "( " + _nf.format(x) + " )";
	}
	@Override
	public String 				toString(DecimalFormat _df) {
		return "( " + _df.format(x) + " )";
	}

}

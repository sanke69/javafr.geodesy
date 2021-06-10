package fr.utils.geodesic.adapters;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.vector.DoubleVector;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Vector2D;
import fr.java.math.geometry.space.Dimension3D;
import fr.java.math.geometry.space.Point3D;
import fr.java.math.geometry.space.Vector3D;
import fr.java.math.geometry.space.Vector4D;
import fr.java.math.topology.Coordinate;
import fr.java.math.topology.CoordinateSystem;

public class AdapterVector3D extends AdapterVectorDouble.Editable implements Point3D.Editable, Vector3D.Editable {
	private static final long serialVersionUID = 1369L;

	private double x, y, z;

	public AdapterVector3D() {
	    this(0, 0, 0);
	}
	public AdapterVector3D(final double _x, final double _y, final double _z) {
		super(3);
		x = _x;
		y = _y;
		z = _z;
	}
	public AdapterVector3D(final AdapterVector3D _copy) {
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
	public void 				setMagnitude(double _mag) {
	        double old_mag = Math.sqrt((x*x + y*y + z*z));
	        
	        x = x * _mag / old_mag;
	        y = y * _mag / old_mag;
	        z = z * _mag / old_mag;
	}
	@Override
	public double 				getMagnitude() {
		return Math.sqrt((x*x + y*y + z*z));
	}

	@Override
	public final AdapterVector3D plus(final double _t) {
		return new AdapterVector3D(x + _t, y + _t, z + _t);
	}
	@Override
	public final AdapterVector3D plus(final double _u, final double _v, final double _w) {
		return new AdapterVector3D(x + _u, y + _v, z + _w);
	}
	@Override
	public final AdapterVector3D plus(final double[] _v) {
		assert(_v.length >= 3);
		return new AdapterVector3D(x + _v[0], y + _v[1], z + _v[2]);
	}
	@Override
	public final AdapterVector3D plus(final Number _t) {
		return new AdapterVector3D(x + _t.doubleValue(), y + _t.doubleValue(), z + _t.doubleValue());
	}
	@Override
	public final AdapterVector3D plus(final Number _x, final Number _y, final Number _z) {
		return new AdapterVector3D(x + _x.doubleValue(), y + _y.doubleValue(), z + _z.doubleValue());
	}
	@Override
	public final AdapterVector3D plus(final Number[] _v) {
		assert(_v.length >= 3);
		return new AdapterVector3D(x + _v[0].doubleValue(), y + _v[1].doubleValue(), z + _v[2].doubleValue());
	}
	@Override
	public final AdapterVector3D plus(final Point3D _pt) {
		return new AdapterVector3D(x + _pt.getX(), y + _pt.getY(), z + _pt.getZ());
	}
	@Override
	public final AdapterVector3D plus(final Vector3D _vec) {
		return new AdapterVector3D(x + _vec.getX(), y + _vec.getY(), z + _vec.getZ());
	}
	@Override
	public final AdapterVector3D plus(final Dimension3D _d) {
		return new AdapterVector3D(x + _d.getWidth(), y + _d.getHeight(), z + _d.getDepth());
	}
	@Override
	public final AdapterVector3D plus(final Coordinate.ThreeDims _c) {
		return new AdapterVector3D(x + _c.getFirst(), y + _c.getSecond(), z + _c.getThird());
	}
	@Override
	public final AdapterVector3D plus(final Dimension.ThreeDims _d) {
		return new AdapterVector3D(x + _d.getWidth(), y + _d.getHeight(), z + _d.getDepth());
	}

	@Override
	public final AdapterVector3D plusEquals(final double _t) {
		x += _t;
		y += _t;
		z += _t;
		return this;
	}
	@Override
	public final AdapterVector3D plusEquals(final double _u, final double _v, final double _w) {
		x += _u;
		y += _v;
		z += _w;
		return this;
	}
	@Override
	public final AdapterVector3D plusEquals(final double[] _v) {
		assert(_v.length >= 3);
		x += _v[0];
		y += _v[1];
		z += _v[2];
		return this;
	}
	@Override
	public final AdapterVector3D plusEquals(final Number _t) {
		x += _t.doubleValue();
		y += _t.doubleValue();
		z += _t.doubleValue();
		return this;
	}
	@Override
	public final AdapterVector3D plusEquals(final Number _u, final Number _v, final Number _w) {
		x += _u.doubleValue();
		y += _v.doubleValue();
		z += _w.doubleValue();
		return this;
	}
	@Override
	public final AdapterVector3D plusEquals(final Number[] _v) {
		assert(_v.length >= 3);
		x += _v[0].doubleValue();
		y += _v[1].doubleValue();
		z += _v[2].doubleValue();
		return this;
	}
	@Override
	public final AdapterVector3D plusEquals(final Point3D _d) {
		x += _d.getX();
		y += _d.getY();
		z += _d.getZ();
		return this;
	}
	@Override
	public final AdapterVector3D plusEquals(final Vector3D _d) {
		x += _d.getX();
		y += _d.getY();
		z += _d.getZ();
		return this;
	}
	@Override
	public final AdapterVector3D plusEquals(final Dimension3D _dim) {
		x += _dim.getWidth();
		y += _dim.getHeight();
		z += _dim.getDepth();
		return this;
	}
	@Override
	public final AdapterVector3D plusEquals(final Coordinate.ThreeDims _dim) {
		x += _dim.getFirst();
		y += _dim.getSecond();
		z += _dim.getThird();
		return this;
	}
	@Override
	public final AdapterVector3D plusEquals(final Dimension.ThreeDims _dim) {
		x += _dim.getWidth();
		y += _dim.getHeight();
		z += _dim.getDepth();
		return this;
	}

	@Override
	public final AdapterVector3D minus(final double _t) {
		return new AdapterVector3D(x - _t, y - _t, z - _t);
	}
	@Override
	public final AdapterVector3D minus(final double _u, final double _v, final double _w) {
		return new AdapterVector3D(x - _u, y - _v, z - _w);
	}
	@Override
	public final AdapterVector3D minus(final double[] _v) {
		assert(_v.length >= 3);
		return new AdapterVector3D(x - _v[0], y - _v[1], z - _v[2]);
	}
	@Override
	public final AdapterVector3D minus(final Number _t) {
		return new AdapterVector3D(x - _t.doubleValue(), y - _t.doubleValue(), z - _t.doubleValue());
	}
	@Override
	public final AdapterVector3D minus(final Number _x, final Number _y, final Number _z) {
		return new AdapterVector3D(x - _x.doubleValue(), y - _y.doubleValue(), z - _z.doubleValue());
	}
	@Override
	public final AdapterVector3D minus(final Number[] _v) {
		assert(_v.length >= 3);
		return new AdapterVector3D(x - _v[0].doubleValue(), y - _v[1].doubleValue(), z - _v[2].doubleValue());
	}
	@Override
	public final AdapterVector3D minus(final Point3D _d) {
		return new AdapterVector3D(x - _d.getX(), y - _d.getY(), z - _d.getZ());
	}
	@Override
	public final AdapterVector3D minus(final Vector3D _d) {
		return new AdapterVector3D(x - _d.getX(), y - _d.getY(), z - _d.getZ());
	}
	@Override
	public final AdapterVector3D minus(final Dimension3D _d) {
		return new AdapterVector3D(x - _d.getWidth(), y - _d.getHeight(), z - _d.getDepth());
	}
	@Override
	public final AdapterVector3D minus(final Coordinate.ThreeDims _c) {
		return new AdapterVector3D(x - _c.getFirst(), y - _c.getSecond(), z - _c.getThird());
	}
	@Override
	public final AdapterVector3D minus(final Dimension.ThreeDims _d) {
		return new AdapterVector3D(x - _d.getWidth(), y - _d.getHeight(), z - _d.getDepth());
	}

	@Override
	public final AdapterVector3D minusEquals(final double _t) {
		x -= _t;
		y -= _t;
		z -= _t;
		return this;
	}
	@Override
	public final AdapterVector3D minusEquals(final double _u, final double _v, final double _w) {
		x -= _u;
		y -= _v;
		z -= _w;
		return this;
	}
	@Override
	public final AdapterVector3D minusEquals(final double[] _v) {
		assert(_v.length >= 3);
		x -= _v[0];
		y -= _v[1];
		z -= _v[2];
		return this;
	}
	@Override
	public final AdapterVector3D minusEquals(final Number _t) {
		x -= _t.doubleValue();
		y -= _t.doubleValue();
		z -= _t.doubleValue();
		return this;
	}
	@Override
	public final AdapterVector3D minusEquals(final Number _u, Number _v, Number _w) {
		x -= _u.doubleValue();
		y -= _v.doubleValue();
		z -= _w.doubleValue();
		return this;
	}
	@Override
	public final AdapterVector3D minusEquals(final Number[] _v) {
		assert(_v.length >= 3);
		x -= _v[0].doubleValue();
		y -= _v[1].doubleValue();
		z -= _v[1].doubleValue();
		return this;
	}
	@Override
	public final AdapterVector3D minusEquals(final Point3D _d) {
		x -= _d.getX();
		y -= _d.getY();
		z -= _d.getZ();
		return this;
	}
	@Override
	public final AdapterVector3D minusEquals(final Vector3D _d) {
		x -= _d.getX();
		y -= _d.getY();
		z -= _d.getZ();
		return this;
	}
	@Override
	public final AdapterVector3D minusEquals(final Dimension3D _dim) {
		x -= _dim.getWidth();
		y -= _dim.getHeight();
		z -= _dim.getDepth();
		return this;
	}
	@Override
	public final AdapterVector3D minusEquals(final Coordinate.ThreeDims _pt) {
		x -= _pt.getFirst();
		y -= _pt.getSecond();
		z -= _pt.getThird();
		return this;
	}
	@Override
	public final AdapterVector3D minusEquals(final Dimension.ThreeDims _dim) {
		x -= _dim.getWidth();
		y -= _dim.getHeight();
		z -= _dim.getDepth();
		return this;
	}

	@Override
	public final AdapterVector3D times(final double _t) {
		return new AdapterVector3D(x * _t, y * _t, z * _t);
	}
	public final AdapterVector3D times(final double _u, final double _v, final double _w) {
		return new AdapterVector3D(x * _u, y * _v, z * _w);
	}
	@Override
	public final AdapterVector3D times(final double[] _v) {
		assert(_v.length >= 3);
		return new AdapterVector3D(x * _v[0], y * _v[1], z * _v[2]);
	}
	@Override
	public final AdapterVector3D times(final Number _t) {
		return new AdapterVector3D(x * _t.doubleValue(), y * _t.doubleValue(), z * _t.doubleValue());
	}
	@Override
	public final AdapterVector3D times(final Number _x, final Number _y, final Number _z) {
		return new AdapterVector3D(x * _x.doubleValue(), y * _y.doubleValue(), z * _z.doubleValue());
	}
	@Override
	public final AdapterVector3D times(final Number[] _v) {
		assert(_v.length >= 3);
		return new AdapterVector3D(x * _v[0].doubleValue(), y * _v[1].doubleValue(), z * _v[2].doubleValue());
	}
	@Override
	public final AdapterVector3D times(final Vector3D _d) {
		return new AdapterVector3D(x * _d.getX(), y * _d.getY(), z * _d.getZ());
	}
	@Override
	public final AdapterVector3D times(final Dimension3D _dim) {
		return new AdapterVector3D(x * _dim.getWidth(), y * _dim.getHeight(), z * _dim.getDepth());
	}
	@Override
	public final AdapterVector3D times(final Dimension.ThreeDims _dim) {
		return new AdapterVector3D(x * _dim.getWidth(), y * _dim.getHeight(), z * _dim.getDepth());
	}


	@Override
	public final AdapterVector3D timesEquals(final double _t) {
		x *= _t;
		y *= _t;
		z *= _t;
		return this;
	}
	@Override
	public final AdapterVector3D timesEquals(final double _u, final double _v, final double _w) {
		x *= _u;
		y *= _v;
		z *= _w;
		return this;
	}
	@Override
	public final AdapterVector3D timesEquals(final double[] _v) {
		assert(_v.length >= 3);
		x *= _v[0];
		y *= _v[1];
		z *= _v[2];
		return this;
	}
	@Override
	public final AdapterVector3D timesEquals(final Number _t) {
		x *= _t.doubleValue();
		y *= _t.doubleValue();
		z *= _t.doubleValue();
		return this;
	}
	@Override
	public final AdapterVector3D timesEquals(final Number _u, final Number _v, final Number _w) {
		x *= _u.doubleValue();
		y *= _v.doubleValue();
		z *= _w.doubleValue();
		return this;
	}
	@Override
	public final AdapterVector3D timesEquals(final Number[] _v) {
		assert(_v.length >= 3);
		x *= _v[0].doubleValue();
		y *= _v[1].doubleValue();
		z *= _v[2].doubleValue();
		return this;
	}
	@Override
	public final AdapterVector3D timesEquals(final Vector3D _d) {
		x *= _d.getX();
		y *= _d.getY();
		z *= _d.getZ();
		return this;
	}
	@Override
	public final AdapterVector3D timesEquals(final Dimension3D _d) {
		x *= _d.getWidth();
		y *= _d.getHeight();
		z *= _d.getDepth();
		return this;
	}
	@Override
	public final AdapterVector3D timesEquals(final Dimension.ThreeDims _dim) {
		x *= _dim.getWidth();
		y *= _dim.getHeight();
		z *= _dim.getDepth();
		return this;
	}

	@Override
	public final AdapterVector3D divides(final double _t) {
		if(_t == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector3D(x / _t, y / _t, z / _t);
	}
	public final AdapterVector3D divides(final double _u, final double _v, final double _w) {
		if(_u == 0 || _v == 0 || _w == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector3D(x / _u, y / _v, z / _w);
	}
	@Override
	public final AdapterVector3D divides(final double[] _v) {
		assert(_v.length >= 3);
		if(_v[0] == 0 || _v[1] == 0 || _v[2] == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector3D(x / _v[0], y / _v[1], z / _v[2]);
	}
	@Override
	public final AdapterVector3D divides(final Number _t) {
		if(_t.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector3D(x / _t.doubleValue(), y / _t.doubleValue(), z / _t.doubleValue());
	}
	@Override
	public final AdapterVector3D divides(final Number _u, final Number _v, final Number _w) {
		if(_u.doubleValue() == 0 || _v.doubleValue() == 0 || _w.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector3D(x / _u.doubleValue(), y / _v.doubleValue(), z / _w.doubleValue());
	}
	@Override
	public final AdapterVector3D divides(final Number[] _v) {
		assert(_v.length >= 3);
		if(_v[0].doubleValue() == 0 || _v[1].doubleValue() == 0 || _v[2].doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector3D(x / _v[0].doubleValue(), y / _v[1].doubleValue(), z / _v[2].doubleValue());
	}
	@Override
	public final AdapterVector3D divides(final Vector3D _d) {
		if(_d.getX() == 0 || _d.getY() == 0 || _d.getZ() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector3D(x / _d.getX(), y / _d.getY(), z / _d.getZ());
	}
	@Override
	public final AdapterVector3D divides(final Dimension3D _d) {
		if(_d.getWidth() == 0 || _d.getHeight() == 0 || _d.getDepth() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector3D(x / _d.getWidth(), y / _d.getHeight(), z / _d.getDepth());
	}
	@Override
	public final AdapterVector3D divides(Dimension.ThreeDims _dim) {
		if(_dim.getWidth() == 0 || _dim.getHeight() == 0 || _dim.getDepth() == 0) throw new RuntimeException("Divide by 0");
		return new AdapterVector3D(x / _dim.getWidth(), y / _dim.getHeight(), z / _dim.getDepth());
	}

	@Override
	public final AdapterVector3D dividesEquals(final double _t) {
		if(_t == 0) throw new RuntimeException("Divide by 0");
		x /= _t;
		y /= _t;
		z /= _t;
		return this;
	}
	public final AdapterVector3D dividesEquals(final double _u, final double _v, final double _w) {
		if(_u == 0 || _v == 0 || _w == 0) throw new RuntimeException("Divide by 0");
		x /= _u;
		y /= _v;
		z /= _w;
		return this;
	}
	@Override
	public final AdapterVector3D dividesEquals(final double[] _v) {
		assert(_v.length >= 3);
		if(_v[0] == 0 || _v[1] == 0 || _v[2] == 0) throw new RuntimeException("Divide by 0");
		x /= _v[0];
		y /= _v[1];
		z /= _v[2];
		return this;
	}
	@Override
	public final AdapterVector3D dividesEquals(final Number _t) {
		if(_t.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _t.doubleValue();
		y /= _t.doubleValue();
		z /= _t.doubleValue();
		return this;
	}
	@Override
	public final AdapterVector3D dividesEquals(final Number _u, final Number _v, final Number _w) {
		if(_u.doubleValue() == 0 || _v.doubleValue() == 0 || _w.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _u.doubleValue();
		y /= _v.doubleValue();
		z /= _w.doubleValue();
		return this;
	}
	@Override
	public final AdapterVector3D dividesEquals(final Number[] _v) {
		assert(_v.length >= 3);
		if(_v[0].doubleValue() == 0 || _v[1].doubleValue() == 0 || _v[2].doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _v[0].doubleValue();
		y /= _v[1].doubleValue();
		z /= _v[2].doubleValue();
		return this;
	}
	@Override
	public final AdapterVector3D dividesEquals(final Vector3D _d) {
		if(_d.getX() == 0 || _d.getY() == 0 || _d.getZ() == 0) throw new RuntimeException("Divide by 0");
		x /= _d.getX();
		y /= _d.getY();
		z /= _d.getZ();
		return this;
	}
	@Override
	public final AdapterVector3D dividesEquals(final Dimension3D _d) {
		if(_d.getWidth() == 0 || _d.getHeight() == 0) throw new RuntimeException("Divide by 0");
		x /= _d.getWidth();
		y /= _d.getHeight();
		z /= _d.getDepth();
		return this;
	}
	@Override
	public final AdapterVector3D dividesEquals(final Dimension.ThreeDims _dim) {
		x *= _dim.getWidth();
		y *= _dim.getHeight();
		z *= _dim.getDepth();
		return this;
	}

	@Override
	public final AdapterVector3D	normalize(double _norm) {
		double length = Math.sqrt(x*x + y*y + z*z), Q = _norm / length;
		return new AdapterVector3D(x * Q, y * Q, z * Q);
	}

	@Override
	public final boolean 		isEqual(final double _t) {
		if(Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(z))
			return Double.isNaN(_t) ? true : false;
		return (x == _t && y == _t && z == _t) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final double _u, final double _v, final double _w) {
		if(Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(z))
			return Double.isNaN(_u) || Double.isNaN(_v)  || Double.isNaN(_w) ? true : false;
		return (x == _u && y == _v && z == _w) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final double[] _vec) {
		if(_vec.length == 3  & (Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(z)))
			return Double.isNaN(_vec[0]) || Double.isNaN(_vec[1]) || Double.isNaN(_vec[2]) ? true : false;
		return (x == _vec[0] && y == _vec[1] && z == _vec[2]) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final Point3D _other) {
		if(Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(z))
			return Double.isNaN(_other.getX()) || Double.isNaN(_other.getY()) ? true : false;
		return (x == _other.getX() && y == _other.getY() && z == _other.getZ()) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final Vector3D _other) {
		if(Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(z))
			return Double.isNaN(_other.getX()) || Double.isNaN(_other.getY()) ? true : false;
		return (x == _other.getX() && y == _other.getY() && z == _other.getZ()) ? true : false;
	}

	@Override
	public final boolean 		isDifferent(final double _t) {
		return (x != _t || y != _t || z != _t) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final double _u, final double _v, final double _w) {
		return (x != _u || y != _v || z != _w) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final double[] _vec) {
		if(_vec.length < 3)
			return false;
		return (x != _vec[0] || y != _vec[1] || z != _vec[2]) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final Point3D _d) {
		return (x != _d.getX() || y != _d.getY() || z != _d.getZ()) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final Vector3D _d) {
		return (x != _d.getX() || y != _d.getY() || z != _d.getZ()) ? true : false;
	}

	@Override
	public final AdapterVector3D clone() {
		return new AdapterVector3D(x, y, z);
	}
	@Override
	public final AdapterVector3D cloneEditable() {
		return new AdapterVector3D(x, y, z);
	}
	@Override
	public final AdapterVector3D abs() {
		return new AdapterVector3D(Math.abs(x), Math.abs(y), Math.abs(z));
	}
	@Override
	public final AdapterVector3D negate() {
		return new AdapterVector3D(-x, -y, -z);
	}
	@Override
	public final AdapterVector3D normalized() {
		double length = getMagnitude();
		if(length < 1e-6)
			return this;
		double invLength = 1.0f / length;
		return new AdapterVector3D(x * invLength, y * invLength, z * invLength);
	}

	@Override
	public Vector2D 			downgrade() {
		return new AdapterVector2D(getZ() * getX(), getZ() * getY());
	}
	@Override
	public Vector4D uniform() {
		return new AdapterVector4D(getX(), getY(), getZ(), 0d);
	}
	@Override
	public Vector4D uniform(double _w) {
		return new AdapterVector4D(getX(), getY(), getZ(), _w);
	}

	@Override
	public final boolean 		isColinear(final Vector3D _vec) {
		throw new NotYetImplementedException();
//		return (x * _vec.getY()) == (y * _vec.getX());
	}
//	@Override
	public final boolean 		isColinear(final NumberVector _vec) {
		assert(_vec.size() >= 3);
		throw new NotYetImplementedException();
//		return (x * _vec.getNumber(1).doubleValue()) == (y * _vec.getNumber(0).doubleValue());
	}
//	@Override
	public final boolean 		isColinear(final DoubleVector _vec) {
		assert(_vec.size() >= 3);
		throw new NotYetImplementedException();
//		return (x * _vec.get(1)) == (y * _vec.get(0));
	}

	public final boolean 		isColinearToSegment(final Point2D _A, final Point2D _B) {
		throw new NotYetImplementedException();
//		return (x * (_B.getY() - _A.getY())) == (y * (_B.getX() - _A.getX()));
	}
	public final boolean 		isColinearToLine(final Point2D _P, final Vector2D _N) {
		throw new NotYetImplementedException();
//		return (x * _N.getY()) == (y * _N.getY());
	}

	@Override
	public final boolean 		isOrthogonal(final Vector3D _vec) {
		throw new NotYetImplementedException();
	}
//	@Override
	public final boolean 		isOrthogonal(final NumberVector _vec) {
		throw new NotYetImplementedException();
	}
//	@Override
	public final boolean 		isOrthogonal(final DoubleVector _vec) {
		throw new NotYetImplementedException();
	}
	
	public final boolean 		isOrthogonalToSegment(final Point2D _A, final Point2D _B) {
		throw new NotYetImplementedException();
//		return (Vectors.dotProduct((Vector2D) this, Vectors.delta(_B, _A)) < Point.EPSILON) ? true : false;
	}
	public final boolean 		isOrthogonalToLine(final Point2D _P, final Vector2D _N) {
		throw new NotYetImplementedException();
//		return (Vectors.dotProduct((Vector2D) this, _N) < Point.EPSILON) ? true : false;
	}

    @Override
    public final double 		dotProduct(final double _x, final double _y, final double _z) {
		throw new NotYetImplementedException();
//        return x * _x + y * _y;
    }
    @Override
    public final double 		dotProduct(final Vector3D _b) {
		throw new NotYetImplementedException();
//        return x * _b.getX() + y * _b.getY();
    }
//	@Override
	public final Number 		dotProduct(final NumberVector _b) {
		throw new NotYetImplementedException();
//        return x * _b.getNumber(1).doubleValue() + y * _b.getNumber(1).doubleValue();
	}

    @Override
	public final Vector3D 		crossProduct(final double _x, final double _y, final double _z) {
		throw new NotYetImplementedException();
	}
    @Override
	public final Vector3D 		crossProduct(final Vector3D _b) {
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
		AdapterVector3D other = (AdapterVector3D) obj;
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
		AdapterVector3D other = (AdapterVector3D) obj;
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

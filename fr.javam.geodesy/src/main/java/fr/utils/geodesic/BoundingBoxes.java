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

import java.text.NumberFormat;
import java.util.Collection;
import java.util.Set;

import fr.java.lang.exceptions.IllegalAccessArgument;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.space.Dimension3D;
import fr.java.math.geometry.space.Point3D;
import fr.java.math.topology.Coordinate;

public final class BoundingBoxes {

	static class AdapterBoundingBox1D implements BoundingBox.OneDim {
		private static final long serialVersionUID = 1L;

		static class Editable extends AdapterBoundingBox1D implements BoundingBox.OneDim.Editable {
			private static final long serialVersionUID = 1L;
		
			public Editable() {
				super();
			}
			public Editable(final double _x, final double _width) {
				super();
			}
		
			@Override public void 							set(double _x, double _width) {
				x = _x; width = _width;
			}
		
			@Override public void 							setX(double _x) 			{ x = _x; }
			@Override public void 							setMinX(double _x) 			{ setX(_x); }
			@Override public void 							setMaxX(double _x) 			{ x = _x - width; }
			@Override public void 							setCenterX(double _x) 		{ x = _x - width / 2d; }
			@Override public void 							setWidth(double _width) 	{ width = _width; }
		
			@Override public void 							setLeft(double _left) 		{ width += x - _left; x = _left; }
			@Override public void 							setRight(double _right) 	{ width  = _right - x; }
		
		}

		double x, width;
		
		public AdapterBoundingBox1D() {
			super();
			x     = 0;
			width = 0;
		}
		public AdapterBoundingBox1D(final double _x, final double _width) {
			super();
			x     = _x;
			width = _width;
		}
	
		@Override
		public double 	getX() 			 { return x; }
	
		@Override
		public double 	getWidth() 		 { return width; }
	
		@Override
		public boolean 	isEmpty()        { return getWidth() == 0; }
	
		@Override
		public double  	getMinX()        { return getX(); }
		@Override
	    public double  	getMaxX()        { return getMinX() + getWidth(); }
		@Override
	    public double  	getCenterX()     { return (getMinX() + getMaxX()) / 2; }
	
		@Override
		public double  	getLeft()        { return getMinX(); }
		@Override
	    public double  	getRight()       { return getMaxX(); }
	
		@Override
		public double 	getPerimeter()   { return getWidth(); }
	
		@Override
		public boolean 	contains(double _x) {
			if(isEmpty())
				return false;
			return _x >= getMinX() && _x <= getMaxX();
		}
		@Override
		public boolean 	contains(double _x, double _w) {
			return contains(_x) && contains(_x + _w);
		}
		@Override
		public boolean 	contains(Coordinate _pt) {
			if(_pt instanceof Coordinate.OneDim)
				return contains(((Coordinate.OneDim) _pt).getFirst());
			throw new IllegalAccessArgument();
		}
		@Override
		public boolean 	contains(Coordinate.OneDim _pt) {
			return contains(_pt.getFirst());
		}
		@Override
		public boolean 	contains(BoundingBox _pt) {
			if(_pt instanceof BoundingBox.OneDim)
				return contains(((BoundingBox.OneDim) _pt));
			throw new IllegalAccessArgument();
		}
		@Override
		public boolean 	contains(BoundingBox.OneDim _bb) {
			return _bb.getMinX() > getMinX() && _bb.getMaxX() < getMaxX();
		}
	
		@Override
		public boolean 	intersects(double _x, double _w) {
			if (isEmpty() || _w <= 0)
				return false;
	
			return (getMinX() < _x + _w && getMaxX() > _x);
		}
		@Override
		public boolean 	intersects(BoundingBox _pt) {
			if(_pt instanceof BoundingBox.OneDim)
				return contains(((BoundingBox.OneDim) _pt));
			throw new IllegalAccessArgument();
		}
		@Override
		public boolean 	intersects(BoundingBox.OneDim _bb) {
			if(isEmpty() || _bb == null || _bb.isEmpty())
				return false;
	
			boolean inside = false, outside = false;
	
			if(_bb.getMinX() > getMinX() && _bb.getMinX() < getMaxX()) inside = true; else outside = true;
			if(_bb.getMaxX() > getMinX() && _bb.getMaxX() < getMaxX()) inside = true; else outside = true;
	
			return inside && outside;
			
		}
	
		@Override
		public String				toString()					{ return "(" + getX() + ") [" + getWidth() + "]"; }
	//	@Override
	//	public String				toString()					{ return toString(new DecimalFormat("#.#")); }
	
		@Override
		public String 				toString(NumberFormat _nf) 	{ return "(" + _nf.format(getMinX()) + "), [" + _nf.format(getWidth()) + "]"; }
	
		@Override
		public BoundingBox.OneDim 	clone() 					{ throw new NotYetImplementedException(); }
	
	}

	static class AdapterBoundingBox2D implements BoundingBox.TwoDims {
		private static final long serialVersionUID = 1L;

		static class Editable extends AdapterBoundingBox2D implements BoundingBox.TwoDims.Editable {
			private static final long serialVersionUID = 1L;
		
			public Editable() {
				super();
			}
			public Editable(final double _x, final double _width, final double _y, final double _height) {
				super(_x, _y, _width, _height);
			}
		
			@Override public void 							set(double _x, double _y, double _width, double _height) {
				x = _x; y = _y; width = _width; height = _height;
			}
		
			@Override public void 							setX(double _x) 			{ x = _x; }
			@Override public void 							setMinX(double _x) 			{ setX(_x); }
			@Override public void 							setMaxX(double _x) 			{ x = _x - width; }
			@Override public void 							setCenterX(double _x) 		{ x = _x - width / 2d; }
			@Override public void 							setWidth(double _width) 	{ width = _width; }
		
			@Override public void 							setLeft(double _left) 		{ width += x - _left; x = _left; }
			@Override public void 							setRight(double _right) 	{ width  = _right - x; }
		
			@Override public void 							setY(double _y) 			{ setMinY(_y); }
			@Override public void 							setMinY(double _y) 			{ y = _y; }
			@Override public void 							setMaxY(double _y) 			{ y = _y - height; }
			@Override public void 							setCenterY(double _y) 		{ y = _y - height / 2d; }
			@Override public void 							setHeight(double _height) 	{ height = _height; }
		
			@Override public void 							setTop(double _top) 		{ height += y - _top; y = _top; }
			@Override public void 							setBottom(double _bottom) 	{ height  = _bottom - y; }
		
		}

		double x, width;
		double y, height;
	
		public AdapterBoundingBox2D() {
			super();
			x      = 0;
			width  = 0;
			y      = 0;
			height = 0;
		}
		public AdapterBoundingBox2D(final double _x, final double _y, final double _width, final double _height) {
			super();
			x     = _x;
			width = _width;
			y      = _y;
			height = _height;
		}
	
		@Override
		public double 	getX() 			 { return x; }
		@Override
		public double 	getY() 			 { return y; }
	
		@Override
		public double 	getWidth() 		 { return width; }
		@Override
		public double 	getHeight() 	 { return height; }
	
		@Override
		public boolean 	isEmpty()        { return getWidth() == 0 || getHeight() == 0; }
	
		@Override
		public double  	getMinY()        { return getY(); }
		@Override
	    public double  	getMaxY()        { return getMinY() + getHeight(); }
		@Override
	    public double  	getCenterY()     { return (getMinY() + getMaxY()) / 2; }
	
		@Override
	    public double  	getTop()         { return getMaxY(); }
		@Override
	    public double  	getBottom()      { return getMinY(); }
	
		@Override
		public double 	getPerimeter()   { return 2 * (getWidth() + getHeight()); }
		@Override
		public double 	getArea() 		 { return getWidth() * getHeight(); }
	
		@Override
		public boolean 	contains(double _x, double _y) {
			if(isEmpty() || Double.isNaN(_x) || Double.isNaN(_y))
				return false;
			return _x >= getMinX() && _x <= getMaxX()
				&& _y >= getMinY() && _y <= getMaxY();
		}
		@Override
		public boolean 	contains(double _x, double _y, double _w, double _h) {
			return contains(_x, _y)
				&& contains(_x + _w, _y + _h);
		}
		@Override
		public boolean 	contains(Coordinate _pt) {
			if(_pt instanceof Coordinate.TwoDims)
				return contains((Coordinate.TwoDims) _pt);
			throw new IllegalAccessArgument();
		}
		@Override
		public boolean 	contains(Coordinate.TwoDims _pt) {
			return contains( _pt.getFirst(), _pt.getSecond());
		}
		@Override
		public boolean 	contains(BoundingBox _pt) {
			if(_pt instanceof BoundingBox.TwoDims)
				return contains(((BoundingBox.TwoDims) _pt));
			throw new IllegalAccessArgument();
		}
		@Override
		public boolean 	contains(BoundingBox.TwoDims _bbox) {
			return contains(_bbox.getMinX(), _bbox.getMinY())
				&& contains(_bbox.getMaxX(), _bbox.getMaxY());
		}
	
		@Override
		public boolean 	intersects(double _x, double _y, double _w, double _h) {
			if (isEmpty() || _w < 0 || _h < 0)
				return false;
	
			return getMinX() < _x + _w && getMaxX() > _x 
				&& getMaxY() > _y      && getMinY() < _y + _h;
		}
		@Override
		public boolean 	intersects(BoundingBox _pt) {
			if(_pt instanceof BoundingBox.TwoDims)
				return contains(((BoundingBox.TwoDims) _pt));
			throw new IllegalAccessArgument();
		}
		@Override
		public boolean 	intersects(BoundingBox.TwoDims _bbox) {
	//		if ((_bbox == null) || _bbox.isEmpty())
			if (_bbox == null)
				return false;
			return intersects(_bbox.getMinX(), _bbox.getMinY(), _bbox.getWidth(), _bbox.getHeight());
		}
	
		@Override
		public double 	getIntersectionArea(BoundingBox.TwoDims _bbox) {
			throw new NotYetImplementedException();
		}
		@Override
		public double 	getUnionArea(BoundingBox.TwoDims _bbox) {
			throw new NotYetImplementedException();
		}
		@Override
		public double 	getIOU(BoundingBox.TwoDims _bbox) {
			return getIntersectionArea(_bbox) / getUnionArea(_bbox);
		}
	
		@Override
		public String				toString()					{ return "(" + getMinX() + ", " + getMinY() + "), [" + getWidth() + "x" + getHeight() + "]"; }
	//	@Override
	//	public String				toString()					{ return toString(new DecimalFormat("#.#")); }
	
		@Override
		public String 				toString(NumberFormat _nf) 	{ return "(" + _nf.format(getMinX()) + ", " + _nf.format(getMinY()) + "), [" + _nf.format(getWidth()) + "x" + _nf.format(getHeight()) + "]"; }
	
		@Override
		public BoundingBox.TwoDims 	clone() 					{ throw new NotYetImplementedException(); }
	
	}

	static class AdapterBoundingBox3D implements BoundingBox.ThreeDims {
		private static final long serialVersionUID = 1L;

		static class Editable extends AdapterBoundingBox3D implements BoundingBox.ThreeDims.Editable {
			private static final long serialVersionUID = 1L;

			static double[] position_and_dimension(Set<Point3D> _points) {
				if(_points == null || _points.size() < 2)
					throw new IllegalArgumentException(
							"The parameter '_bounds' cannot be null and must "
									+ "have 2 or more elements.");

				double minX = Integer.MAX_VALUE;
				double minY = Integer.MAX_VALUE;
				double minZ = Integer.MAX_VALUE;
				double maxX = Integer.MIN_VALUE;
				double maxY = Integer.MIN_VALUE;
				double maxZ = Integer.MIN_VALUE;
				for(Point3D pt : _points) {
					minX = Math.min(minX, pt.getX());
					minY = Math.min(minY, pt.getY());
					minZ = Math.min(minZ, pt.getX());
					maxX = Math.max(maxX, pt.getX());
					maxY = Math.max(maxY, pt.getY());
					maxZ = Math.max(maxZ, pt.getY());
				}

				double width  = maxX - minX;
				double height = maxY - minY;
				double depth  = maxZ - minZ;
				
				return new double[] { minX, minY, minZ, width, height, depth };
			}

			public Editable() {
				super();
			}
			public Editable(final double _x, final double _y, final double _z, final double _width, final double _height, final double _depth) {
				super(_x, _y, _z, _width, _height, _depth);
			}
			public Editable(Point3D _position, Dimension3D _dimensions) {
				this(_position.getX(), _position.getY(), _position.getZ(), _dimensions.getWidth(), _dimensions.getHeight(), _dimensions.getDepth());
			}
			public Editable(Set<Point3D> _points) {
				this(position_and_dimension(_points));
			}
			private Editable(final double[] _xyzwhd) {
				super(_xyzwhd[0], _xyzwhd[1], _xyzwhd[2], _xyzwhd[3], _xyzwhd[4], _xyzwhd[5]);
			}
		
			@Override public double 						getX() 						{ return x; }
			@Override public double 						getY() 						{ return y; }
			@Override public double 						getZ() 						{ return z; }
		
			@Override public double 						getWidth() 					{ return width; }
			@Override public double 						getHeight() 				{ return height; }
			@Override public double 						getDepth() 					{ return depth; }
		
			@Override public void 							set(final double _x, final double _y, final double _z, final double _width, final double _height, final double _depth) {
				x = _x; y = _y; z = _z; width = _width; height = _height; depth = _depth;
			}
		
			@Override public void 							setX(double _x) 			{ x = _x; }
			@Override public void 							setMinX(double _x) 			{ setX(_x); }
			@Override public void 							setMaxX(double _x) 			{ x = _x - width; }
			@Override public void 							setCenterX(double _x) 		{ x = _x - width / 2d; }
			@Override public void 							setWidth(double _width) 		{ width = _width; }
		
			@Override public void 							setLeft(double _left) 		{ width += x - _left; x = _left; }
			@Override public void 							setRight(double _right) 	{ width  = _right - x; }
		
			@Override public void 							setY(double _y) 			{ setMinY(_y); }
			@Override public void 							setMinY(double _y) 			{ y = _y; }
			@Override public void 							setMaxY(double _y) 			{ y = _y - height; }
			@Override public void 							setCenterY(double _y) 		{ y = _y - height / 2d; }
			@Override public void 							setHeight(double _height) 	{ height = _height; }
		
			@Override public void 							setTop(double _top) 		{ height += y - _top; y = _top; }
			@Override public void 							setBottom(double _bottom) 	{ height  = _bottom - y; }
		
			@Override public void 							setZ(double _z) 			{ setMinZ(_z); }
			@Override public void 							setMinZ(double _z) 			{ z = _z; }
			@Override public void 							setMaxZ(double _z) 			{ z = _z - depth; }
			@Override public void 							setCenterZ(double _z) 		{ z = _z - depth / 2d; }
			@Override public void 							setDepth(double _depth) 	{ depth = _depth; }
		
			@Override public void 							setFront(double _front) 	{ depth += z - _front; z = _front; }
			@Override public void 							setBack(double _back) 		{ depth  = _back - z; }
		
		}

		double x, width;
		double y, height;
		double z, depth;
	
		public AdapterBoundingBox3D() {
			super();
			x      = 0;
			width  = 0;
			y      = 0;
			height = 0;
		}
		public AdapterBoundingBox3D(final double _x, final double _y, final double _z, final double _width, final double _height, final double _depth) {
			super();
			x     = _x;
			width = _width;
			y      = _y;
			height = _height;
			z      = _z;
			depth  = _depth;
		}
	
		@Override
		public double 	getX() 			{ return x; }
		@Override
		public double 	getY() 			{ return y; }
		@Override
		public double 	getZ() 			{ return z; }
	
		@Override
		public double 	getWidth() 		{ return width; }
		@Override
		public double 	getHeight() 	{ return height; }
		@Override
		public double 	getDepth() 		{ return depth; }
	
		@Override
		public boolean 	isEmpty()        { return getWidth() == 0 || getHeight() == 0 || getDepth() == 0; }
	
		public double  	getMinZ()        { return getZ(); }
	    public double  	getMaxZ()        { return getMinZ() + getDepth(); }
	    public double  	getCenterZ()     { return (getMinZ() + getMaxZ()) / 2; }
	
	    public double  	getFront()       { return getMaxZ(); }
	    public double  	getBack()        { return getMinZ(); }
	
		public double 	getPerimeter()   { return 2 * (getWidth() + getHeight()); }
		public double 	getArea() 		 { return 2 * getWidth() * getHeight() + 2 * getWidth() * getDepth() + 2 * getHeight() * getDepth(); }
		public double 	getVolume()      { return getWidth() * getHeight() * getDepth(); }
	
		public boolean 	contains(double _x, double _y, double _z) {
			if(isEmpty())
				return false;
			return _x >= getMinX() && _x <= getMaxX()
				&& _y >= getMinY() && _y <= getMaxY()
				&& _z >= getMinZ() && _z <= getMaxZ();
		}
		public boolean 	contains(double _x, double _y, double _z, double _w, double _h, double _d) {
			return contains(_x, _y, _z) && contains(_x + _w, _y + _h, _z + _d);
		}
		public boolean 	contains(Coordinate _pt) {
			if(_pt instanceof Coordinate.ThreeDims)
				return contains((Coordinate.ThreeDims) _pt);
			throw new IllegalAccessArgument();
		}
		public boolean 	contains(Coordinate.ThreeDims _pt) {
			if (_pt == null)
				return false;
			return _pt.getFirst()  >= getMinX() && _pt.getFirst()  <= getMaxX()  
				&& _pt.getSecond() >= getMinY() && _pt.getSecond() <= getMaxY() 
				&& _pt.getThird()  >= getMinZ() && _pt.getThird()  <= getMaxZ();
		}
		public boolean 	contains(BoundingBox _pt) {
			if(_pt instanceof BoundingBox.ThreeDims)
				return contains(((BoundingBox.ThreeDims) _pt));
			throw new IllegalAccessArgument();
		}
		public boolean 	contains(BoundingBox.ThreeDims _bbox) {
			return contains(_bbox.getMinX(), _bbox.getMinY(), _bbox.getMinZ())
				&& contains(_bbox.getMaxX(), _bbox.getMaxY(), _bbox.getMaxZ());
		}
	
		public boolean 	intersects(double _x, double _y, double _z, double _w, double _h, double _d) {
			if (isEmpty() || _w < 0 || _h < 0)
				return false;
	
			return getMinX() < _x + _w && getMaxX() > _x
				&& getMinY() < _y + _h && getMaxY() > _y
				&& getMinZ() < _z + _d && getMaxY() < _z;
		}
		public boolean 	intersects(BoundingBox _pt) {
			if(_pt instanceof BoundingBox.ThreeDims)
				return contains(((BoundingBox.ThreeDims) _pt));
			throw new IllegalAccessArgument();
		}
		public boolean 	intersects(BoundingBox.ThreeDims _bbox) {
			if ((_bbox == null) || _bbox.isEmpty())
				return false;
			return intersects(_bbox.getMinX(), _bbox.getMinY(), _bbox.getMinZ(), _bbox.getWidth(), _bbox.getHeight(), _bbox.getDepth());
		}
	
		@Override
		public String					toString()					{ return "(" + getMinX() + ", " + getMinY() + "), [" + getWidth() + "x" + getHeight() + "]"; }
	//	@Override
	//	public String					toString()					{ return toString(new DecimalFormat("#.#")); }
	
		@Override 
		public String 					toString(NumberFormat _nf) 	{ return "(" + _nf.format(getX()) + ", " + _nf.format(getY()) + ", " + _nf.format(getZ()) + ") [" + _nf.format(getWidth()) + "x" + _nf.format(getHeight()) + "x" + _nf.format(getDepth()) + "]"; }
	
		@Override
		public BoundingBox.ThreeDims 	clone() 					{ throw new NotYetImplementedException(); }
	
	}

	// Implementations
	public static final BoundingBox.OneDim.Editable 	empty1() {
		return ofEditable(0, 0);
	}
	public static final BoundingBox.TwoDims.Editable 	empty2() {
		return ofEditable(0, 0, 0, 0);
	}
	public static final BoundingBox.ThreeDims.Editable 	empty3() {
		return ofEditable(0, 0, 0, 0, 0, 0);
	}

	public static final BoundingBox.OneDim.Editable 	unit1() {
		return ofEditable(0, 1);
	}
	public static final BoundingBox.TwoDims.Editable 	unit2() {
		return ofEditable(0, 0, 1, 1);
	}
	public static final BoundingBox.ThreeDims.Editable 	unit3() {
		return ofEditable(0, 0, 0, 1, 1, 1);
	}

	public static final BoundingBox.OneDim 				of(final double _x, final double _width) {
		return new AdapterBoundingBox1D(_x, _width);
	}
	public static final BoundingBox.OneDim.Editable 	ofEditable(final double _x, final double _width) {
		return new AdapterBoundingBox1D.Editable(_x, _width);
	}

	public static final BoundingBox.TwoDims 			of(final double _x, final double _y, final double _width, final double _height) {
		return new AdapterBoundingBox2D(_x, _y, _width, _height);
	}
	public static final BoundingBox.TwoDims.Editable 	ofEditable(final double _x, final double _y, final double _width, final double _height) {
		return new AdapterBoundingBox2D.Editable(_x, _y, _width, _height);
	}

	public static final BoundingBox.ThreeDims 			of(final double _x, final double _y, final double _z, final double _width, final double _height, final double _depth) {
		return new AdapterBoundingBox3D(_x, _y, _z, _width, _height, _depth);
	}
	public static final BoundingBox.ThreeDims.Editable 	ofEditable(final double _x, final double _y, final double _z, final double _width, final double _height, final double _depth) {
		return new AdapterBoundingBox3D.Editable(_x, _y, _z, _width, _height, _depth);
	}

	// 2D Specialization
	public static BoundingBox.TwoDims 					of2D(final double _size) {
		return of(0, 0, _size, _size);
	}
	public static BoundingBox.TwoDims 					of2D(final double _width, final double _height) {
		return of(0, 0, _width, _height);
	}

	public static final BoundingBox.TwoDims 			of(final Dimension.TwoDims _dimension) {
		return of(0, 0, _dimension.getWidth(), _dimension.getHeight());
	}
	public static final BoundingBox.TwoDims 			of(final Coordinate.TwoDims _topLeft, final Coordinate.TwoDims _bottomRight) {
		return of(_topLeft.getFirst(), _topLeft.getSecond(), _bottomRight.getFirst() - _topLeft.getFirst(), _bottomRight.getSecond() - _topLeft.getSecond());
	}
	public static final BoundingBox.TwoDims 			of(final Coordinate.TwoDims _position, final Dimension.TwoDims _dimension) {
		return of(_position.getFirst(), _position.getSecond(), _dimension.getWidth(), _dimension.getHeight());
	}
	public static final BoundingBox.TwoDims.Editable 	ofEditable(final Dimension.TwoDims _dimension) {
		return ofEditable(0, 0, _dimension.getWidth(), _dimension.getHeight());
	}
	public static final BoundingBox.TwoDims.Editable 	ofEditable(final Coordinate.TwoDims _topLeft, final Coordinate.TwoDims _bottomRight) {
		return ofEditable(_topLeft.getFirst(), _topLeft.getSecond(), _bottomRight.getFirst() - _topLeft.getFirst(), _bottomRight.getSecond() - _topLeft.getSecond());
	}
	public static final BoundingBox.TwoDims.Editable 	ofEditable(final Coordinate.TwoDims _position, final Dimension.TwoDims _dimension) {
		return ofEditable(_position.getFirst(), _position.getSecond(), _dimension.getWidth(), _dimension.getHeight());
	}
	public static final BoundingBox.TwoDims.Editable 	ofEditable(final BoundingBox.TwoDims _bbox) {
		return ofEditable(_bbox.getX(), _bbox.getY(), _bbox.getWidth(), _bbox.getHeight());
	}

	public static BoundingBox.TwoDims 					of(Coordinate.TwoDims... _points) {
		double minY = Integer.MAX_VALUE;
		double minX = Integer.MAX_VALUE;
		double maxY = Integer.MIN_VALUE;
		double maxX = Integer.MIN_VALUE;

		for(Coordinate.TwoDims position : _points) {
			minY = Math.min(minY, position.getSecond());
			minX = Math.min(minX, position.getFirst());
			maxY = Math.max(maxY, position.getSecond());
			maxX = Math.max(maxX, position.getFirst());
		}
        
		return fromBounds(minX, minY, maxX, maxY);
	}
	public static BoundingBox.TwoDims 					of(Collection<Point2D> _points) {
		double minY = Integer.MAX_VALUE;
		double minX = Integer.MAX_VALUE;
		double maxY = Integer.MIN_VALUE;
		double maxX = Integer.MIN_VALUE;

		for(Coordinate.TwoDims position : _points) {
			minY = Math.min(minY, position.getSecond());
			minX = Math.min(minX, position.getFirst());
			maxY = Math.max(maxY, position.getSecond());
			maxX = Math.max(maxX, position.getFirst());
		}
        
		return fromBounds(minX, minY, maxX, maxY);
	}

	public static BoundingBox.TwoDims 					fromBounds(double _minX, double _minY, double _maxX, double _maxY) {
		if(_maxX < _minX) {
			double tmp = _minX;
			_minX = _maxX;
			_maxX = tmp;
		}
		if(_maxY < _minY) {
			double tmp = _minY;
			_minY = _maxY;
			_maxY = tmp;
		}
		return of(_minX, _minY, _maxX - _minX, _maxY - _minY);
	}
	public static BoundingBox.TwoDims.Editable 			fromBoundsEditable(double _minX, double _minY, double _maxX, double _maxY) {
		if(_maxX < _minX) {
			double tmp = _minX;
			_minX = _maxX;
			_maxX = tmp;
		}
		if(_maxY < _minY) {
			double tmp = _minY;
			_minY = _maxY;
			_maxY = tmp;
		}
		return ofEditable(_minX, _minY, _maxX - _minX, _maxY - _minY);
	}

	public static final BoundingBox.TwoDims				scaled(BoundingBox.TwoDims _bbox, double _scale) {
		return of(_bbox.getX(), _bbox.getY(), _scale * _bbox.getWidth(), _scale * _bbox.getHeight());
	}
	public static final BoundingBox.TwoDims				scaled(BoundingBox.TwoDims _bbox, double _scalex, double _scaley) {
		return of(_bbox.getX(), _bbox.getY(), _scalex * _bbox.getWidth(), _scaley * _bbox.getHeight());
	}

	public static final BoundingBox.TwoDims				scaled(BoundingBox.TwoDims _bbox, double _scale, boolean _scaledPosition) {
		return _scaledPosition ? 
					of(_scale * _bbox.getX(), _scale * _bbox.getY(), _scale * _bbox.getWidth(), _scale * _bbox.getHeight())
					:
					of(_scale * _bbox.getX(), _scale * _bbox.getY(), _scale * _bbox.getWidth(), _scale * _bbox.getHeight());
	}
	public static final BoundingBox.TwoDims				scaled(BoundingBox.TwoDims _bbox, double _scalex, double _scaley, boolean _scaledPosition) {
		return _scaledPosition ? 
					of(_bbox.getX(), _bbox.getY(), _scalex * _bbox.getWidth(), _scaley * _bbox.getHeight())
					:
					of(_scalex * _bbox.getX(), _scaley * _bbox.getY(), _scalex * _bbox.getWidth(), _scaley * _bbox.getHeight());
	}

	// 3D Specialization
	public static BoundingBox.ThreeDims 				of3D(final double _size) {
		return of(0, 0, 0, _size, _size, _size);
	}
	public static BoundingBox.ThreeDims 				of3D(final double _width, final double _height, final double _depth) {
		return of(0, 0, 0, _width, _height, _depth);
	}

	public static BoundingBox.ThreeDims.Editable 		of(double _width, double _height, double _depth) {
		return new AdapterBoundingBox3D.Editable(0, 0, 0, _width, _height, _depth);
	}
	public static BoundingBox.ThreeDims.Editable 		of(Dimension3D _dimension) {
		return new AdapterBoundingBox3D.Editable(0, 0, 0, _dimension.getWidth(), _dimension.getHeight(), _dimension.getDepth());
	}
	
	public static BoundingBox.ThreeDims.Editable 		of(Point3D _position, Dimension3D _dimension) {
		return new AdapterBoundingBox3D.Editable(_position, _dimension);
	}
	public static BoundingBox.ThreeDims.Editable 		of(Set<Point3D> _points) {
		return new AdapterBoundingBox3D.Editable(_points);
	}

	public static BoundingBox.TwoDims.Editable 			newRectangular() {
		return new AdapterBoundingBox2D.Editable(0, 0, 1, 1);
	}

	public static BoundingBox.ThreeDims.Editable		fromBounds(double _minX, double _minY, double _minZ, double _maxX, double _maxY, double _maxZ) {
		if(_maxX < _minX) {
			double tmp = _minX;
			_minX = _maxX;
			_maxX = tmp;
		}
		if(_maxY < _minY) {
			double tmp = _minY;
			_minY = _maxY;
			_maxY = tmp;
		}
		if(_maxZ < _minZ) {
			double tmp = _minZ;
			_minZ = _maxZ;
			_maxZ = tmp;
		}
		return new AdapterBoundingBox3D.Editable(_minX, _minY, _minZ, _maxX - _minX, _maxY - _minY, _maxZ - _minZ);
	}

}
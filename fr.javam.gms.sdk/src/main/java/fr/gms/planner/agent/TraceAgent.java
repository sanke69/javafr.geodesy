package fr.gms.planner.agent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import fr.gms.navigation.waypath.Segment;
import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.Angles;
import fr.java.maths.Points;

public abstract class TraceAgent {

	public static class  Path {
		List<Point2D>   waypoints;
		boolean 	    loop;

		public Path(Point2D[] _waypoints, boolean _loop) {
			waypoints = Arrays.asList(_waypoints);
			loop      = _loop;
		}
		public Path(List<Point2D> _waypoints, boolean _loop) {
			waypoints = _waypoints;
			loop      = _loop;
		}

		public int					nextIndex(int _index) {
			return index(_index+1);
		}
		public int					index(int _index) {
			return loop ? _index % waypoints.size() : _index < 0 ? -1 : _index > waypoints.size() - 1 ? - 1 : _index;
		}
		public Point2D 				waypoint(int _index) {
			int index = index(_index);
		
			return index < 0 ? null : waypoints.get(index);
		}
		public Segment				segment(int _index) {
			if(_index < 0 || _index > waypoints.size() - 1)
				return null;

			int next = index(_index+1);

			return next < 0 ? null : new Segment(waypoints.get(_index), waypoints.get(next));
		}

		public List<Point2D> 		waypoints() { return waypoints; }
		public boolean       		loop() 		{ return loop; }
		public int 					size() 		{ return waypoints.size(); }

		public final 	int 		getClosestIndex		(Point2D _pos) {
			double dCandidate = Double.MAX_VALUE;
			int    iCandidate = -1;

			for(int i = 0; i < size(); ++i) {
				Point2D candidate = waypoint(i);
				double  distance  = getDistance(_pos, candidate);

				if(distance < dCandidate) {
					dCandidate = distance;
					iCandidate = i;
				}
			}

			return iCandidate;
		}
		public final 	Point2D		getClosestWaypoint	(Point2D _pos) {
			return waypoint( getClosestIndex(_pos) );
		}
		public final 	Segment		getClosestSegment	(Point2D _pos) {
			double dCandidate = Double.MAX_VALUE;
			int    iCandidate = -1;

			for(int i = 0; i < size(); ++i) {
				Segment seg = segment(i);
				
				if(!Segment.isValid(seg))
					break;
				
				double  distance  = getOrthoProjectionDistance(_pos, seg);

				if(distance < dCandidate) {
					dCandidate = distance;
					iCandidate = i;
				}
			}

			return segment(iCandidate);
		}

	};

	Point2D[] path;
	int       last;
	double    step;

	protected TraceAgent(int _nbDots, double _interDots) {
		super();
		path = new Point2D[_nbDots];
		last = 0;
		step = _interDots;
	}

	public abstract Point2D[] 			compute(Point2D _pos, Path _path);
	public final void					reset(Point2D _pos) { 
		last = 0;
		path[last++] = _pos;
	}

	public final Point2D 				moveTo(Point2D _point) {
		if( ! (last < path.length - 1) || _point == Points.NaN2)
			return null;
		path[last++] = _point;
		return _point;
	}

	public final boolean 				done() {
		return size() == capacity() - 1;
	}

	public final int 					capacity() {
		return path.length;
	}
	public final int 					size() {
		return last;
	}
	public final double 				step() {
		return step;
	}

	public final Point2D 				last() {
		if(last == 0)
			return null;

		return path[last - 1];
	}
	public final Point2D 				last(int _prev) {
		if(last - _prev <= 0)
			return null;

		return path[last - _prev - 1];
	}

	public final Point2D[] 				trace() {
		return trim();
	}
	public final Point2D[] 				trim() {
		return Arrays.stream(path, 0, last)
					 .collect(Collectors.toList())
					 .toArray(new Point2D[0]);
		/*
		return Arrays.asList(path)
				     .stream()
				     .filter(p -> p != null)
				     .collect(Collectors.toList())
				     .toArray(new Point2D[0]);
		*/
	}

	public static final Point2D 		linearInterpolationWithRatio(Point2D _from, Point2D _to, double _alpha) {
		double x = _alpha * _to.getX() + (1 - _alpha) * _from.getX();
		double y = _alpha * _to.getY() + (1 - _alpha) * _from.getY();

		return Points.of(x, y);
	}
	public static final Point2D 		linearInterpolationWithDistance(Point2D _from, Point2D to, double _distanceFrom) {
		double alpha  = Math.sqrt( _distanceFrom / getDistance(to, _from) );

		return linearInterpolationWithRatio(_from, to, alpha);
	}

	public static final Point2D 		linearInterpolationWithDistance(Point2D _from, Point2D _to_a, Point2D _to_b, double _distanceFrom) {
		List<Double> K = getLinearInterpolationFactor(_from, _to_a, _to_b, _distanceFrom);

		double k = K.size() == 2 ? K.get(1) : K.size() == 1 ? K.get(0) : Double.NaN;
		
		if(Double.isNaN(k))
			return Points.NaN2;

		double x = k * _to_b.getX() + (1d - k) * _to_a.getX();
		double y = k * _to_b.getY() + (1d - k) * _to_a.getY();

		return Points.of(x, y);
	}

	public static final List<Double> 	getLinearInterpolationFactor(Point2D _from, Point2D _to_a, Point2D _to_b, double _distanceFrom) {
		//  AH  = k AB
		// |PH| = d
		double xp = _from.getX(),
			   xa = _to_a.getX(),
			   xb = _to_b.getX(),
			   yp = _from.getY(),
			   ya = _to_a.getY(),
			   yb = _to_b.getY(),
			   d  = _distanceFrom;
		double Xa = xb - xa,
			   Xb = xa - xp,
			   Ya = yb - ya,
			   Yb = ya - yp;
		double A  = Xa*Xa+Ya*Ya,
			   B  = 2*Xa*Xb+2*Ya*Yb,
			   C  = Xb*Xb+Yb*Yb-d*d;
		// tq: A² . k + B . k + C = 0

		return solve2ndDegree(A, B, C);
	}
	public static final List<Double> 	solve2ndDegree(double A, double B, double C) {
		double delta = B*B - 4 * A * C;

		if(delta > 0) {
			double kA = - (B + Math.sqrt(delta)) / (2 * A);
			double kB = - (B - Math.sqrt(delta)) / (2 * A);

			double k1 = kA <  kB ? kA : kB;
			double k2 = k1 == kB ? kA : kB;

			return Arrays.asList(k1, k2);
		}

		if(delta == 0)
			return Arrays.asList(- B / (2 * A));

		if(delta < 0)
			return Collections.emptyList();

		return null;
	}

	public static final double 			getDistance(Point2D _a, Point2D _b) {
		return Math.sqrt( (_b.getX() - _a.getX()) * (_b.getX() - _a.getX()) + (_b.getY() - _a.getY()) * (_b.getY() - _a.getY()));
	}

	public final static double 			getHeading(Point2D _a, Point2D _b) {
		double A = _b.getY() - _a.getY();
		double O = _b.getX() - _a.getX();
		
		double H = A == 0 && O == 0 ? 0 : (double) (90 - Angles.Radian2Degree(Math.atan2(A, O)));

		return H;
	}

	public static final double 			getOrthoProjectionFactor(Point2D _pt, Point2D _a, Point2D _b) {
		double xa =  _a.getX();
		double ya =  _a.getY();
		double xb =  _b.getX();
		double yb =  _b.getY();
		double x  = _pt.getX();
		double y  = _pt.getY();

		double k  = ((yb-ya) * (x-xa) - (xb-xa) * (y-ya)) / ((yb-ya)*(yb-ya) + (xb-xa)*(xb-xa));
		
		return k;
	}
	public static final double 			getOrthoProjectionDistance(Point2D _pt, Point2D _a, Point2D _b) {
		return getDistance(_pt, getOrthoProjection(_pt, _a, _b));
	}
	public static final Point2D			getOrthoProjection(Point2D _pt, Point2D _a, Point2D _b) {
		double k = getOrthoProjectionFactor(_pt, _a, _b);

		double x = _pt.getX() - k * (_b.getX() - _a.getX());
		double y = _pt.getY() + k * (_b.getY() - _a.getY());

		return Points.of(x, y);
	}

	public static final double 			getOrthoProjectionFactor(Point2D _pt, Segment _seg) {
		double xa =  _seg.a().getX();
		double ya =  _seg.a().getY();
		double xb =  _seg.b().getX();
		double yb =  _seg.b().getY();
		double x  = _pt.getX();
		double y  = _pt.getY();

		double k  = ((yb-ya) * (x-xa) - (xb-xa) * (y-ya)) / ((yb-ya)*(yb-ya) + (xb-xa)*(xb-xa));
		
		return k;
	}
	public static final double 			getOrthoProjectionDistance(Point2D _pt, Segment _seg) {
		return getDistance(_pt, getOrthoProjection(_pt, _seg));
	}
	public static final Point2D			getOrthoProjection(Point2D _pt, Segment _seg) {
		double k = getOrthoProjectionFactor(_pt, _seg);

		double x = _pt.getX() - k * (_seg.b().getX() - _seg.a().getX());
		double y = _pt.getY() + k * (_seg.b().getY() - _seg.a().getY());

		return Points.of(x, y);
	}

}

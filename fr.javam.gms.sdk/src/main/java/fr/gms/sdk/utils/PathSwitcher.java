package fr.gms.sdk.utils;

import java.util.ArrayList;
import java.util.List;

import fr.gms.navigation.waypath.WayPath;
import fr.gms.sdk.navigation.waypath.WayPathDouble;
import fr.gms.sdk.navigation.waypath.WayPaths;
import fr.java.math.geometry.plane.Point2D;

public class PathSwitcher {
	
	public PathSwitcher() {
		super();
	}

	public WayPath.Single 	buildSwitchTrajectory(WayPathDouble _path, Point2D _from, boolean _rightToLeft, int _previous, int _offset, int _count, int _next) {
		List<Point2D> 	trace = new ArrayList<Point2D>();

		WayPath.Single  from     = _path.getLane(_rightToLeft ? WayPathDouble.RIGHT : WayPathDouble.LEFT),
						to       = _path.getLane(_rightToLeft ? WayPathDouble.LEFT  : WayPathDouble.RIGHT);

		int     		index    = from.getClosestIndex(_from);
		int     		previous = _previous;
		int     		offset   = _offset;
		int     		count    = _count;
		int     		next     = _next;

		for(int i = previous; i > 0; --i)
			trace.add(from.getWayPoint(index - i));
		for(int i =  0; i < offset; ++i)
			trace.add(from.getWayPoint(index + i));
		

		double lambda =  .5d;
		double x_min  = - 10d;
		double x_max  = + 10d;
		
		for(int i = 0; i < count; ++i) {
			double X = x_min + (i / (count - 1d)) * (x_max - x_min);
			double T = sig(X, lambda);

			Point2D l = to.getWayPoint(index + offset + i);
			Point2D r = from.getWayPoint(index + offset + i);
			Point2D S = r.times(1-T).plus(l.times(T));

			trace.add(S);
		}

		for(int i =  0; i < next; ++i)
			trace.add(to.getWayPoint(index + offset + count + i));

		return WayPaths.newSimpleTrip(trace);
	}
	
	
	public static double sig(double _x) {
		return 1d / ( 1d + Math.exp(- _x) );
	}
	public static double sig(double _x, double _lambda) {
		return 1d / ( 1d + Math.exp(- _lambda * _x) );
	}
	public static double sigp(double _x, double _n, double _theta) {
		return Math.pow(_x, _n) / ( Math.pow(_x, _n) + Math.pow(_theta, _n) );
	}
	// sigm(x) = 1 - sigp(x)
	public static double sigm(double _x, double _n, double _theta) {
		return Math.pow(_theta, _n) / ( Math.pow(_x, _n) + Math.pow(_theta, _n) );
	}

}

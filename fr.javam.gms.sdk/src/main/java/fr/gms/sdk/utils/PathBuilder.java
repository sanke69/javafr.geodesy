package fr.gms.sdk.utils;

import java.util.ArrayList;
import java.util.List;

import fr.gms.navigation.waypath.Segment;
import fr.gms.navigation.waypath.WayPath;
import fr.gms.sdk.navigation.waypath.WayPathDouble;
import fr.gms.sdk.navigation.waypath.WayPathSimple;
import fr.gms.sdk.navigation.waypath.WayPaths;
import fr.java.math.algebra.vector.generic.Vector2D;
import fr.java.math.geometry.plane.Point2D;
import fr.java.utils.jMath;

public class PathBuilder { double epsilon = 1e-6;

	public PathBuilder() {
		super();
	}

	public WayPath.Single 	buildMonoTrack(WayPath.Single _path, double _step) {
		List<Point2D> 	trace = new ArrayList<Point2D>();

		boolean 		loop     = false;
		int     		index    = 0;
		Point2D 		position = _path.getWayPoint(0), next;
		Segment 		segment  = _path.getSegment(0);
		double 			d_step   = 0d;

		while(position != null) {
			trace.add(position);

			// Find next point in record
			next = jMath.linearInterpolationWithDistance(position, segment.a(), segment.b(), _step);
			if(next != null && !jMath.isBetween(segment.a(), segment.b(), next)) {
				d_step  = _step;
				d_step -= jMath.length(position, segment.b());

				do {
					if((segment = _path.getSegment(++index)) != null) {
						next    = jMath.linearInterpolationWithDistance(segment.a(), segment.a(), segment.b(), d_step);
						d_step -= jMath.length(segment.a(), segment.b());
					} else
						next = null;
				} while(next != null && !jMath.isBetween(segment.a(), segment.b(), next));
			}

			// If we're back on initial positions, assuming it's a loop...
			if(trace.size() > 2 && next != null)
				if(jMath.liesBetween(trace.get(0), trace.get(1), next, 1e-1)) {
					loop = true;
					next = null;
				}

			position = next;
		}

		return loop ? WayPaths.newSimpleLoop(trace) : WayPaths.newSimpleTrip(trace);
	}
	public WayPath.Single 	buildMonoTrackWithFixedStep(WayPath.Single _path, double _step) {
		return buildMonoTrack( _path, _step );
	}
	public WayPath.Single 	buildMonoTrackWithPointNumber(WayPath.Single _path, int _nbPoints) {
		return buildMonoTrack( _path, _path.getLength() / (double) _nbPoints );
	}

	public WayPath.Single[] buildMultiTrack(WayPath.Single _ref, double _step, WayPath.Single... _pathes) {
		List<Point2D> 	 trace        = new ArrayList<Point2D>();

		int     		 index        =   0;
		double			 d_step       = _step;
		Point2D 		 position     = _ref.getWayPoint(index), next;
		Segment 		 reference    = _ref.getSegment(index);
		ProjectionResult projection   = null;

		boolean			 compute_left = _pathes.length > 0 ? true : false;
		int     		 offset_left  =   0,
						 count_left   =  -1, default_count = 150;
		WayPath.Single   track_left   = compute_left ? _pathes[0] : null;
		List<Point2D> 	 trace_left   = new ArrayList<Point2D>();

		while(position != null) {
			trace.add(position);

			// Optimization
			int iOffset = count_left == -1 ?  0 : offset_left - count_left/2;
			int iCount  = count_left == -1 ? -1 : count_left;
			if(compute_left && (projection = getProjectionInternal(track_left, iOffset, iCount, position, reference.normal(true))) != null) {
				trace_left.add(projection.result);

				offset_left = projection.index;
				count_left  = default_count;
			}

			// Find next point in record
			next = jMath.linearInterpolationWithDistance(position, reference.a(), reference.b(), _step);
			if(next != null && !jMath.isBetween(reference.a(), reference.b(), next)) {
				d_step  = _step;
				d_step -= jMath.length(position, reference.b());

				do {
					if((reference = _ref.getSegment(++index)) != null) {
						next    = jMath.linearInterpolationWithDistance(reference.a(), reference.a(), reference.b(), d_step);
						d_step -= jMath.length(reference.a(), reference.b());
					} else
						next = null;
				} while(next != null && !jMath.isBetween(reference.a(), reference.b(), next));
			}

			// If we're back on initial positions, assuming it's a loop...
			if(trace.size() > 2 && next != null)
				if(jMath.liesBetween(trace.get(0), trace.get(1), next, 1e-1))
					next = null;

			position = next;
		}

		if(trace_left.size() > 0)
			return new WayPath.Single[] { WayPaths.newSimpleLoop(trace), WayPaths.newSimpleLoop(trace_left) };
		return new WayPath.Single[] { WayPaths.newSimpleLoop(trace) };
	}
	public WayPath.Single[] buildMultiTrackWithFixedStep(WayPath.Single _path, double _step, WayPath.Single... _pathes) {
		return buildMultiTrack( _path, _step, _pathes );
	}
	public WayPath.Single[] buildMultiTrackWithPointNumber(WayPath.Single _path, int _nbPoints, WayPath.Single... _pathes) {
		return buildMultiTrack( _path, _path.getLength() / (double) _nbPoints, _pathes );
	}

	public WayPath 			buildDualTrack(WayPath.Single _right, WayPath.Single _left, double _step) {
		return buildDualTrack(_right, _left, _step, _step);
	}
	public WayPath 			buildDualTrack(WayPath.Single _right, WayPath.Single _left, double _step, double _epsilon) {
		List<Point2D> 	 trace        = new ArrayList<Point2D>();

		int     		 index        =   0;
		double			 d_step       = _step;
		Point2D 		 position     = _right.getWayPoint(index), next;
		Segment 		 reference    = _right.getSegment(index);
		ProjectionResult projection   = null;

		boolean			 compute_left = _left != null;
		int     		 offset_left  =   0,
						 count_left   =  -1, default_count = 150;
		WayPath.Single   track_left   = _left;
		List<Point2D> 	 trace_left   = new ArrayList<Point2D>();

		while(position != null) {
			trace.add(position);

			// Optimization
			int iOffset = count_left == -1 ?  0 : offset_left - count_left/2;
			int iCount  = count_left == -1 ? -1 : count_left;
			if(compute_left && (projection = getProjectionInternal(track_left, iOffset, iCount, position, reference.normal(true))) != null) {
				trace_left.add(projection.result);

				offset_left = projection.index;
				count_left  = default_count;
			}

			// Find next point in record
			next = jMath.linearInterpolationWithDistance(position, reference.a(), reference.b(), _step);
			if(next != null && !jMath.isBetween(reference.a(), reference.b(), next)) {
				d_step  = _step;
				d_step -= jMath.length(position, reference.b());

				do {
					if((reference = _right.getSegment(++index)) != null) {
						next    = jMath.linearInterpolationWithDistance(reference.a(), reference.a(), reference.b(), d_step);
						d_step -= jMath.length(reference.a(), reference.b());
					} else
						next = null;
				} while(next != null && !jMath.isBetween(reference.a(), reference.b(), next));
			}

			// If we're back on initial positions, assuming it's a loop...
			if(trace.size() > 2 && next != null)
				if(jMath.liesBetween(trace.get(0), trace.get(1), next, _epsilon))
					next = null;

			position = next;
		}

		if(trace_left.size() == trace.size())
			return new WayPathDouble( trace, trace_left, true);
		return new WayPathSimple( trace, true);
	}
	public WayPath 			buildDualTrackWithFixedStep(WayPath.Single _right, WayPath.Single _left, double _step) {
		return buildDualTrack( _right, _left, _step );
	}
	public WayPath			buildDualTrackWithPointNumber(WayPath.Single _right, WayPath.Single _left, int _nbPoints) {
		return buildDualTrack( _right, _left, _right.getLength() / (double) _nbPoints );
	}

	public WayPath    		buildDualTrack(WayPath.Multi _pathes, double _step) {
		if(_pathes.getLanes().size() < 2)
			throw new RuntimeException();

		WayPath.Single   left         = _pathes.getLane(WayPathDouble.LEFT);
		WayPath.Single   right        = _pathes.getLane(WayPathDouble.RIGHT);

		List<Point2D> 	 trace        = new ArrayList<Point2D>();

		int     		 index        =   0;
		double			 d_step       = _step;
		Point2D 		 position     = right.getWayPoint(index), next;
		Segment 		 reference    = right.getSegment(index);
		ProjectionResult projection   = null;

		boolean			 compute_left = left != null;
		int     		 offset_left  =   0,
						 count_left   =  -1, default_count = 150;
		WayPath.Single   track_left   = left;
		List<Point2D> 	 trace_left   = new ArrayList<Point2D>();

		while(position != null) {
			trace.add(position);

			// Optimization
			int iOffset = count_left == -1 ?  0 : offset_left - count_left/2;
			int iCount  = count_left == -1 ? -1 : count_left;
			if(compute_left && (projection = getProjectionInternal(track_left, iOffset, iCount, position, reference.normal(true))) != null) {
				trace_left.add(projection.result);

				offset_left = projection.index;
				count_left  = default_count;
			}

			// Find next point in record
			next = jMath.linearInterpolationWithDistance(position, reference.a(), reference.b(), _step);
			if(next != null && !jMath.isBetween(reference.a(), reference.b(), next)) {
				d_step  = _step;
				d_step -= jMath.length(position, reference.b());

				do {
					if((reference = right.getSegment(++index)) != null) {
						next    = jMath.linearInterpolationWithDistance(reference.a(), reference.a(), reference.b(), d_step);
						d_step -= jMath.length(reference.a(), reference.b());
					} else
						next = null;
				} while(next != null && !jMath.isBetween(reference.a(), reference.b(), next));
			}

			// If we're back on initial positions, assuming it's a loop...
			if(trace.size() > 2 && next != null)
				if(jMath.liesBetween(trace.get(0), trace.get(1), next, 1e-1))
					next = null;

			position = next;
		}

		if(trace_left.size() == trace.size())
			return new WayPathDouble( trace, trace_left, true);
		return new WayPathSimple( trace, true);
	}
	public WayPath 			buildDualTrackWithFixedStep(WayPath.Multi _pathes, double _step) {
		return buildDualTrack( _pathes, _step );
	}
	public WayPath 			buildDualTrackWithPointNumber(WayPath.Multi _pathes, int _nbPoints) {
		return buildDualTrack( _pathes, _pathes.getLength(WayPathDouble.RIGHT) / (double) _nbPoints );
	}

	/** Internal implementation **/
//	public static record ProjectionResult(int index, double distance, Point2D result, boolean perfectMatch, double distanceMatch) {
	public static class ProjectionResult {
		int index; double distance; Point2D result; boolean perfectMatch; double distanceMatch;

		ProjectionResult(int _index, double _distance, Point2D _result, boolean _perfectMatch, double _distanceMatch) {
			index         = _index;
			distance      = _distance;
			result        = _result;
			perfectMatch  = _perfectMatch;
			distanceMatch = _distanceMatch;
		}
		ProjectionResult(int index, double distance, Point2D result) {
			this(index, distance, result, false, Double.MAX_VALUE);
		}
		ProjectionResult(int index, double distance, Point2D result, boolean perfectMatch) {
			this(index, distance, result, perfectMatch, perfectMatch ? 0 : Double.MAX_VALUE);
		}
	};

	private static ProjectionResult getOrthoProjectionInternal(WayPath.Single _path, int _offset, int _count, Point2D _pt) {
		_count = _count < 0 ? _path.getSegments().size() - _offset : _count;

		ProjectionResult result = null;
  		for(int i = _offset; i < _offset + _count; ++i) {
			Segment seg = _path.getSegment(i);

			if(seg == null)
				break;

			Point2D proj = jMath.getOrthoProjection(seg.a(), seg.b(), _pt);

			if(proj != null) {
				boolean isBetween = jMath.isBetween(seg.a(), seg.b(), proj);
				double  distance  = jMath.distance(proj, _pt);

				if(isBetween && (result == null || distance < result.distance))
					result = new ProjectionResult(i, distance, proj, true);
			}

		}

		return result;
	}
	private static ProjectionResult getProjectionInternal(WayPath.Single _path, int _offset, int _count, Point2D _pt, Vector2D _dir) {
		_count = _count < 0 ? _path.getSegments().size() - _offset : _count;

		ProjectionResult result = null;
  		for(int i = _offset; i < _offset + _count; ++i) {
			Segment seg = _path.getSegment(i);
			
			if(seg == null)
				break;

			Point2D proj = jMath.getProjection(seg.a(), seg.b(), _pt, _dir.normalized());

			if(proj != null) {
				boolean isBetween = jMath.isBetween(seg.a(), seg.b(), proj);
				double  distance  = jMath.distance(proj, _pt);

				if(isBetween && (result == null || distance < result.distance))
					result = new ProjectionResult(i, distance, proj, true);
			}

		}

		return result;
	}

	public static Point2D 			getOrthoProjection(WayPath.Single _path, int _offset, int _count, Point2D _pt) {
		ProjectionResult candidate = getOrthoProjectionInternal(_path, _offset, _count, _pt);

		return candidate != null ? candidate.result : null;
	}
	public static double 			getOrthoDistance(WayPath.Single _path, int _offset, int _count, Point2D _pt) {
		ProjectionResult candidate = getOrthoProjectionInternal(_path, _offset, _count, _pt);

		return candidate != null ? candidate.distance : Double.MAX_VALUE;
	}
	public  static Point2D 			getProjection(WayPath.Single _path, int _offset, int _count, Point2D _pt, Vector2D _dir) {
		ProjectionResult candidate = getProjectionInternal(_path, _offset, _count, _pt, _dir);

		return candidate != null ? candidate.result : null;
	}

}

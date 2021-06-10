package fr.gms.planner.agent.ant;

import fr.gms.navigation.waypath.Segment;
import fr.java.math.geometry.plane.Point2D;

public class AntAgentSmoothed extends AntAgent {
	public static final int MIN_PATH_SIZE = 1;

	public AntAgentSmoothed(int _nbDots, double _interDots) {
		super(_nbDots, _interDots);
	}

	@Override
	public Point2D[] 	compute(Point2D _pos, Path _path) {
		if(_path.size() < MIN_PATH_SIZE)
			return null;

		int     index = _path.getClosestIndex(_pos);
		Segment seg   = _path.getClosestSegment(_pos), next;
		Point2D from  = _path.getClosestWaypoint(_pos);

		if( ! Segment.isValid( seg ) ) {
/*
			if(from != null) {
				double  heading = getHeading(_pos, from);
				System.out.println("FROM     " + heading);
				System.out.println("REDUCED  " + Angles.reduceDegree(heading));
				System.out.println("POSITIVE " + Angles.positiveDegree(heading));

				reset(_pos);
				goToPoint(from, step());
				return trace();
			}
*/
			return null;
		}

		reset(_pos);
		

		// We now move on splines
		while(index >= 0) {
			seg  = _path.segment(index);
			next = _path.segment(index+1);

			if( !Segment.isValid(seg) )
				break;

			Point2D[]                waypoints = new Point2D[] { last(), seg.a(), seg.b() };
//			CoordinateInterpolator2D spline    = new CoordinateInterpolator2D(waypoints);

			goAlongSegment(seg, step());

			index = !done() ? _path.nextIndex(index) : -1;
		}
		
		
		
		
/*
		double  headingA = getHeading(seg.a(), last());
		double  headingB = getHeading(seg.b(), last());

		if( ! (Math.abs( headingA - headingB ) < 90) )
			from = seg.b();
*/
		return trace();
	}

	Point2D 			goAlongSpline(Segment _target, double _step) {
		while(getDistance(last(), _target.b()) > _step) {
			moveTo (
					linearInterpolationWithDistance(last(), _target.a(), _target.b(), _step)
				   );

			if(done())
				break ;
		}

		return last();
	}

}

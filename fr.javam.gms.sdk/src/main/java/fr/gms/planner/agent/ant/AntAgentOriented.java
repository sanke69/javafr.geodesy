package fr.gms.planner.agent.ant;

import fr.gms.navigation.waypath.Segment;
import fr.java.math.geometry.plane.Point2D;

public class AntAgentOriented extends AntAgent {
	public static final int MIN_PATH_SIZE = 1;

	public AntAgentOriented(int _nbDots, double _interDots) {
		super(_nbDots, _interDots);
	}

	@Override
	public Point2D[] 	compute(Point2D _pos, Path _path) {
		if(_path.size() < MIN_PATH_SIZE)
			return null;

		int     index = _path.getClosestIndex(_pos);
		Segment seg   = _path.getClosestSegment(_pos);
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

		double  headingA = getHeading(seg.a(), last());
		double  headingB = getHeading(seg.b(), last());

		if( ! (Math.abs( headingA - headingB ) < 90) )
			from = seg.b();

		// We try to reach the path if we are too far away
		goToPoint(from, step());
		if(done())
			return trace();
		// But, we're still stupid and don't take into account our direction...

		// Now, let discover and go along the the path until it end
		while(index >= 0) {
			seg = _path.segment(index);

			if( !Segment.isValid(seg) )
				break;

			goAlongSegment(seg, step());

			index = !done() ? _path.nextIndex(index) : -1;
		}

		return trace();
	}

}

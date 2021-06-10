package fr.gms.planner.agent.ant;

import fr.gms.navigation.waypath.Segment;
import fr.gms.planner.agent.TraceAgent;
import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.Points;

public class AntAgent extends TraceAgent {
	public static final int MIN_PATH_SIZE = 1;

	public AntAgent(int _nbDots, double _interDots) {
		super(_nbDots, _interDots);
	}

	@Override
	public Point2D[] 	compute(Point2D _pos, Path _path) {
		if(_path.size() < MIN_PATH_SIZE)
			return null;

		int     index = _path.getClosestIndex(_pos);
		Segment seg   = _path.getClosestSegment(_pos);
		Point2D from  = _path.getClosestWaypoint(_pos);

		reset(_pos);

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

	Point2D 			goToPoint(Point2D _target, double _step) {
		if(getDistance(last(), _target) < _step)
			return last();

		while(getDistance(last(), _target) > _step) {
			Point2D next = linearInterpolationWithDistance(last(), last(), _target, _step);
			
			if(next.isEqual(Points.NaN2))
				break ;

			moveTo( next );

			if(done())
				break ;
		}

		return last();
	}
	Point2D 			goAlongSegment(Segment _target, double _step) {
		if(getDistance(last(), _target.b()) < _step)
			return last();

		while(getDistance(last(), _target.b()) > _step) {
			Point2D next = linearInterpolationWithDistance(last(), _target.a(), _target.b(), _step);
			
			if(next.isEqual(Points.NaN2))
				break ;

			moveTo( next );

			if(done())
				break ;
		}

		return last();
	}

}

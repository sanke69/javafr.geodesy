package fr.gms.sdk.navigation.waypath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fr.gms.navigation.waypath.Segment;
import fr.gms.navigation.waypath.WayPath;
import fr.java.math.geometry.plane.Point2D;
import fr.java.utils.jMath;

public class WayPathSimple implements WayPath.Single {
	List<Point2D>   waypoints;
	double 			length;
	boolean 	    loop;

	public WayPathSimple(Point2D... _waypoints) {
		this(Arrays.asList(_waypoints), false);
	}
	public WayPathSimple(Collection<Point2D> _waypoints) {
		this(new ArrayList<Point2D>(_waypoints), false);
	}
	public WayPathSimple(List<Point2D> _waypoints) {
		this(new ArrayList<Point2D>(_waypoints), false);
	}
	public WayPathSimple(Point2D[] _waypoints, boolean _loop) {
		this(Arrays.asList(_waypoints), _loop);
	}
	public WayPathSimple(Collection<Point2D> _waypoints, boolean _loop) {
		this(new ArrayList<Point2D>(_waypoints), _loop);
	}
	public WayPathSimple(List<Point2D> _waypoints, boolean _loop) {
		super();
		waypoints = _waypoints;
		loop      = _loop;
		length    = computeLength();
	}
	public WayPathSimple(WayPath.Single _path, boolean _loop) {
		super();
		waypoints = _path.getWayPoints();
		length    = _path.getLength();
		loop      = _loop;
	}

	@Override
	public boolean 				isLoop() {
		return loop;
	}
	@Override
	public double 				getLength() {
		return length;
	}
	@Override
	public int	 				getSize() {
		return waypoints.size();
	}

	public final int			index(int _index) {
		if(!loop)
			return _index < 0 ? -1 : _index > waypoints.size() - 1 ? - 1 : _index;

		if(waypoints.size() <= 0)
			System.err.println("WTF");

		if(_index < 0)
			_index += waypoints.size();

		return _index % waypoints.size();
	}
	public final Point2D 		waypoint(int _index) {
		return getWayPoint(_index);
	}
	public final Point2D 		reverseWaypoint(int _index) {
		return getLastWayPoint(_index);
	}
	public final Segment		segment(int _id) {
		return getSegment(_id);
	}

	@Override
	public Point2D 				getWayPoint(int _id) {
		int    index  = index(_id);
		return index == -1 ? null : waypoints.get(index);
	}
	@Override
	public Point2D 				getLastWayPoint(int _id) {
		int    index  = index(size() - 1 - _id);
		return index == -1 ? null : waypoints.get(index);
	}
	@Override
	public Segment 				getSegment(int _id) {
		int id = 0, idp1 = 0;
		if((id = index(_id)) < 0 || (idp1 = index(_id+1)) < 0)
			return null;
		return new Segment(waypoints.get(id), waypoints.get(idp1));
	}
	@Override
	public Segment 				getLastSegment(int _id) {
		int id = 0, idm1 = 0;
		int lastId = size() - 1 - _id;

		if((id = index(lastId)) < 0 || (idm1 = index(lastId - 1)) < 0)
			return null;

		return new Segment(waypoints.get(idm1), waypoints.get(id));
	}

	@Override
	public List<Point2D> 		getWayPoints() {
		return waypoints;
	}
	@Override
	public List<Segment> 		getSegments() {
		List<Segment> segments = IntStream.range(0, waypoints.size()-1)
				.mapToObj(i -> new Segment(waypoints.get(i), waypoints.get(i+1)))
				.collect(Collectors.toList());

		if(loop)
			segments.add( new Segment(waypoint(0), reverseWaypoint(0)) );
		
		return segments;
	}

	public final List<Point2D> 	waypoints() { return waypoints; }
	public final boolean       	loop() 		{ return loop; }
	public final int 			size() 		{ return waypoints.size(); }

	public final WayPath.Single	reverse() 	{
		ArrayList<Point2D> clone = new ArrayList<Point2D>(waypoints);
		Collections.reverse(clone);
		return new WayPathSimple(clone, loop());
	}

	public final 	int 		getClosestIndex		(Point2D _pos) {
		double dCandidate = Double.MAX_VALUE;
		int    iCandidate = -1;

		for(int i = 0; i < size(); ++i) {
			Point2D candidate = waypoint(i);
			double  distance  = jMath.distance(_pos, candidate);

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

			double  distance  = jMath.getOrthoDistance(seg.a(), seg.b(), _pos);

			if(distance < dCandidate) {
				dCandidate = distance;
				iCandidate = i;
			}
		}

		return segment(iCandidate);
	}

	@Override
	public Point2D 				atCurvilinearAbscissa(double _abscissa) {
		if(_abscissa < 0)
			return getWayPoint(0);
		else if(_abscissa > getLength())
			return getLastWayPoint(0);

		for(Segment s : getSegments()) {
			double seg_length = jMath.length(s.a(), s.b());

			if(_abscissa < seg_length)
				return jMath.linearInterpolation(s.a(), s.b(), _abscissa / seg_length);
			else
				_abscissa -= seg_length;
		}

		throw new IllegalAccessError();
	}
	@Override
	public double 				getCurvilinearAbscissa(Point2D _pt) {
		if(!belongsTo(_pt))
			return -1d;

		double abscissa = 0d;

		for(Segment s : getSegments()) {
			if(jMath.isBetween(s.a(), s.b(), _pt))
				return abscissa + jMath.length(s.a(), _pt);
			else
				abscissa += jMath.length(s.a(), s.b());
		}

		throw new IllegalAccessError();
	}

	private double 				computeLength() {
		double length = 0d;
		for(Segment s : getSegments())
			length += jMath.length(s.a(), s.b());
		
		if(loop)
			length += jMath.length(waypoint(0), reverseWaypoint(0));

		return length;
	}

}

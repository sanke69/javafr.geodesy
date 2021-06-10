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

public class WayPathDouble implements WayPath.Multi {
	public static final int LEFT  = 1;
	public static final int RIGHT = 0;

	List<Point2D>   right, left;
	double 			lengthRight, lengthLeft;
	boolean 	    loop;

	public WayPathDouble(Point2D[] _right, Point2D[] _left) {
		this(Arrays.asList(_right), Arrays.asList(_left), false);
	}
	public WayPathDouble(Collection<Point2D> _right, Collection<Point2D> _left) {
		this(new ArrayList<Point2D>(_right), new ArrayList<Point2D>(_left), false);
	}
	public WayPathDouble(List<Point2D> _right, List<Point2D> _left) {
		this(new ArrayList<Point2D>(_right), new ArrayList<Point2D>(_left), false);
	}
	public WayPathDouble(Point2D[] _right, Point2D[] _left, boolean _loop) {
		this(Arrays.asList(_right), Arrays.asList(_left), _loop);
	}
	public WayPathDouble(Collection<Point2D> _right, Collection<Point2D> _left, boolean _loop) {
		this(new ArrayList<Point2D>(_right), new ArrayList<Point2D>(_left), _loop);
	}
	public WayPathDouble(List<Point2D> _right, List<Point2D> _left, boolean _loop) {
		super();
		if(_right.size() != _left.size())
			throw new RuntimeException("All lanes must have same length");

		left        = _left;
		right       = _right;
		loop        = _loop;
		lengthRight = computeLength(RIGHT);
		lengthLeft  = computeLength(LEFT);
	}
	public WayPathDouble(WayPath.Multi _path, boolean _loop) {
		super();
		left        = _path.getWayPoints(LEFT);
		right       = _path.getWayPoints(RIGHT);
		lengthLeft  = _path.getLength(LEFT);
		lengthRight = _path.getLength(RIGHT);
		loop        = _loop;
	}

	@Override
	public boolean 				isLoop() {
		return loop;
	}
	@Override
	public double 				getLength(int _lane) {
		return _lane == LEFT ? lengthLeft : lengthRight;
	}
	@Override
	public int	 				getSize() {
		return right.size();
	}

	public final int			index(int _index) {
		if(!loop)
			return _index < 0 ? -1 : _index > left.size() - 1 ? - 1 : _index;

		if(left.size() <= 0)
			System.err.println("WTF");

		if(_index < 0)
			_index += left.size();

		return _index % left.size();
	}
	public final Point2D 		waypoint(int _lane, int _index) {
		return getWayPoint(_lane, _index);
	}
	public final Point2D 		reverseWaypoint(int _lane, int _index) {
		return getLastWayPoint(_lane, _index);
	}
	public final Segment		segment(int _lane, int _id) {
		return getSegment(_lane, _id);
	}
	
	@Override
	public int[] 				getLaneIds() {
		return new int[] { RIGHT, LEFT };
	}
	@Override
	public List<WayPath.Single> getLanes() {
		return Arrays.asList( new WayPathSimple(right, loop()), new WayPathSimple(left, loop()) );
	}
	@Override
	public WayPath.Single 		getLane(int _lane) {
		// TODO Auto-generated method stub
		return _lane == LEFT ? new WayPathSimple(left, loop()) : new WayPathSimple(right, loop());
	}

	@Override
	public Point2D 				getWayPoint(int _lane, int _id) {
		int    index  = index(_id);
		return index == -1 ? null : left.get(index);
	}
	@Override
	public Point2D 				getLastWayPoint(int _lane, int _id) {
		int    index  = index(size() - 1 - _id);
		return index == -1 ? null : left.get(index);
	}
	@Override
	public Segment 				getSegment(int _lane, int _id) {
		int id = 0, idp1 = 0;
		if((id = index(_id)) < 0 || (idp1 = index(_id+1)) < 0)
			return null;
		return new Segment(left.get(id), left.get(idp1));
	}
	@Override
	public Segment 				getLastSegment(int _lane, int _id) {
		int id = 0, idm1 = 0;
		int lastId = size() - 1 - _id;

		if((id = index(lastId)) < 0 || (idm1 = index(lastId - 1)) < 0)
			return null;

		return new Segment(left.get(idm1), left.get(id));
	}

	@Override
	public List<Point2D> 		getWayPoints(int _lane) {
		return _lane == LEFT ? left : right;
	}
	@Override
	public List<Segment> 		getSegments(int _lane) {
		List<Point2D> lane     = _lane == LEFT ? left : right;
		List<Segment> segments = IntStream.range(0, lane.size()-1)
											.mapToObj(i -> new Segment(lane.get(i), lane.get(i+1)))
											.collect(Collectors.toList());

		if(loop)
			segments.add( new Segment(waypoint(_lane, 0), reverseWaypoint(_lane, 0)) );
		
		return segments;
	}

	public final List<Point2D> 	waypoints() { return left; }
	public final boolean       	loop() 		{ return loop; }
	public final int 			size() 		{ return left.size(); }

	public final WayPathDouble	reverse() 	{
		ArrayList<Point2D> cloneLeft = new ArrayList<Point2D>(left);
		ArrayList<Point2D> cloneRight = new ArrayList<Point2D>(right);
		Collections.reverse(cloneLeft);
		Collections.reverse(cloneRight);
		return new WayPathDouble(cloneLeft, cloneRight, loop());
	}

	public final 	int 		getClosestIndex		(int _laneId, Point2D _pos) {
		double dCandidate = Double.MAX_VALUE;
		int    iCandidate = -1;

		for(int i = 0; i < size(); ++i) {
			Point2D candidate = waypoint(_laneId, i);
			double  distance  = jMath.distance(_pos, candidate);

			if(distance < dCandidate) {
				dCandidate = distance;
				iCandidate = i;
			}
		}

		return iCandidate;
	}
	public final 	Point2D		getClosestWaypoint	(int _laneId, Point2D _pos) {
		return waypoint( _laneId, getClosestIndex(_laneId, _pos) );
	}
	public final 	Segment		getClosestSegment	(int _laneId, Point2D _pos) {
		double dCandidate = Double.MAX_VALUE;
		int    iCandidate = -1;

		for(int i = 0; i < size(); ++i) {
			Segment seg = segment(_laneId, i);

			if(!Segment.isValid(seg))
				break;

			double  distance  = jMath.getOrthoDistance(seg.a(), seg.b(), _pos);

			if(distance < dCandidate) {
				dCandidate = distance;
				iCandidate = i;
			}
		}

		return segment(_laneId, iCandidate);
	}

	private double 				computeLength(int _lane) {
		double length = 0d;
		for(Segment s : getSegments(_lane))
			length += jMath.length(s.a(), s.b());
		
		if(loop)
			length += jMath.length(waypoint(_lane, 0), reverseWaypoint(_lane, 0));

		return length;
	}

}

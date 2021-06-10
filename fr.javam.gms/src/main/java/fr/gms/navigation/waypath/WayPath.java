package fr.gms.navigation.waypath;

import java.util.List;

import fr.java.math.geometry.plane.Point2D;
import fr.java.utils.jMath;

public interface WayPath {

	public boolean isLoop();

	public WayPath reverse();

	public interface Single extends WayPath {

		public double 				getLength();
		public default double 		length() { return getLength(); }

		public int  				getSize();
		public default int 			size() { return getSize(); }

		public List<Point2D> 		getWayPoints();
		public Point2D 				getWayPoint(int _id);
		public Point2D 				getLastWayPoint(int _id);

		public List<Segment> 		getSegments();
		public Segment 				getSegment(int _id);
		public Segment 				getLastSegment(int _id);

		public int 					getClosestIndex		(Point2D _pos);
		public Point2D				getClosestWaypoint	(Point2D _pos);
		public Segment				getClosestSegment	(Point2D _pos);

		public WayPath.Single		reverse();

		public default boolean 		belongsTo(Point2D _pt) {
			boolean isOnPath = false;
			
			for(Segment seg : getSegments())
				if(jMath.isBetween(seg.a(), seg.b(), _pt))
					isOnPath = true;

		    return isOnPath;
		}

		public Point2D				atCurvilinearAbscissa(double _abscissa);
		public double				getCurvilinearAbscissa(Point2D _pt);

	}

	public interface Multi extends WayPath {

		public int  				getSize				();
		public default int 			size				() { return getSize(); }

		public int[]  				getLaneIds			();
		public default int[] 		laneIds				() { return getLaneIds(); }

		public double 				getLength			(int _lane);
		public default double 		length				(int _lane) { return getLength(_lane); }

		public List<WayPath.Single> getLanes			();
		public WayPath.Single		getLane				(int _lane);

		public List<Point2D> 		getWayPoints		(int _lane);
		public Point2D 				getWayPoint			(int _lane, int _id);
		public Point2D 				getLastWayPoint		(int _lane, int _id);

		public List<Segment> 		getSegments			(int _lane);
		public Segment 				getSegment			(int _lane, int _id);
		public Segment 				getLastSegment		(int _lane, int _id);

		public int 					getClosestIndex		(int _lane, Point2D _pos);
		public Point2D				getClosestWaypoint	(int _lane, Point2D _pos);
		public Segment				getClosestSegment	(int _lane, Point2D _pos);

		public WayPath.Multi		reverse				();

		public default boolean 		belongsTo			(Point2D _pt) {
			boolean isOnPath = false;
			
			for(int i : laneIds())
				for(Segment seg : getSegments(i))
					if(jMath.isBetween(seg.a(), seg.b(), _pt))
						isOnPath = true;

		    return isOnPath;
		}

	}

}

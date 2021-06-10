package fr.gis.utils.objects;

import java.text.DecimalFormat;

import fr.geodesic.api.referential.Datum;
import fr.gis.api.Gis;
import fr.gis.api.road.Road;
import fr.gis.api.road.RoadCoordinate;
import fr.java.math.topology.Coordinate;
import fr.utils.geodesic.adapters.AdapterGeoCartesian2D;

public class AdapterMapCartesian2D extends AdapterGeoCartesian2D implements RoadCoordinate.Cartesian2D.Editable {
	Road.Element  element;
	Gis.Direction direction;
	int           segmentId, laneId;
	double        heading, s;

	Coordinate    projection;
	double        error;

	public AdapterMapCartesian2D(double _x, String _zone_x, double _y, String _zone_y) {
		this(Datum.UTM, _x, _zone_x, _y, _zone_y, null, null, -1, -1, Double.NaN);
	}
	public AdapterMapCartesian2D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y) {
		this(_datum, _x, _zone_x, _y, _zone_y, null, null, -1, -1, Double.NaN);
	}
	public AdapterMapCartesian2D(double _x, String _zone_x, double _y, String _zone_y, Road.Element _element, Gis.Direction _direction) {
		this(Datum.UTM, _x, _zone_x, _y, _zone_y, _element, _direction, -1, -1, Double.NaN);
	}
	public AdapterMapCartesian2D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y, Road.Element _element, Gis.Direction _direction) {
		this(_datum, _x, _zone_x, _y, _zone_y, _element, _direction, -1, -1, Double.NaN);
	}
	public AdapterMapCartesian2D(double _x, String _zone_x, double _y, String _zone_y, Road.Element _element, Gis.Direction _direction, int _segmentId, int _laneId, double _abscissaCurvilinear) {
		this(Datum.UTM, _x, _zone_x, _y, _zone_y, _element, _direction, _segmentId, _laneId, _abscissaCurvilinear);
	}
	public AdapterMapCartesian2D(Datum _datum, double _x, String _zone_x, double _y, String _zone_y, Road.Element _element, Gis.Direction _direction, int _segmentId, int _laneId, double _abscissaCurvilinear) {
		super(_datum, _x, _zone_x, _y, _zone_y);
		element   = _element;
		direction = _direction;
		segmentId = _segmentId;
		laneId    = _laneId;

		s = _abscissaCurvilinear;
	}

	@Override public void 			setHeading(double _heading) 							{ heading = _heading; }
	@Override public double 		getHeading() 											{ return heading; }

	@Override public void 			setRoadElement(Road.Element _roadElt) 					{ element =_roadElt; }
	@Override public Road.Element 	getRoadElement() 										{ return element; }
	@Override public void 			setRoadElementSegmentId(int _id) 						{ segmentId = _id; }
	@Override public int 			getRoadElementSegmentId() 								{ return segmentId; }
	@Override public void 			setRoadElementLaneId(int _n) 							{ laneId = _n; }
	@Override public int 			getRoadElementLaneId() 									{ return laneId; }
	@Override public void 			setRoadElementDirection(Gis.Direction _direction) 		{ direction = _direction; }
	@Override public Gis.Direction 	getRoadElementDirection() 								{ return direction; }
	@Override public void 			setCurvilinearAbscissa(double _s, boolean _fromEnd) 	{ s = _fromEnd ? getRoadElement().getLength() - _s : _s;}
	@Override public double 		getCurvilinearAbscissa(boolean _fromEnd) 				{ return s; }

	@Override public void 			setProjection(Coordinate _proj) 						{ projection = _proj; }
	@Override public Coordinate 	getProjection() 										{ return projection; }
	@Override public void 			setProjectionError(double _e) 							{ error = _e; }
	@Override public double 		getProjectionError() 									{ return error; }

	@Override public String 		toString() 												{ return toString(new DecimalFormat()); }
	@Override public String 		toString(DecimalFormat _df) 							{ return "(" + getX() + (getZoneX() != null ? " " + getZoneX() : "") + ", " + getY() + (getZoneY() != null ? " " + getZoneY() : "") + ")"; }

	@Override public RoadCoordinate.Cartesian2D.Editable	clone() 												{ return new AdapterMapCartesian2D(getDatum(), getX(), getZoneX(), getY(), getZoneY(), getRoadElement(), getRoadElementDirection(), getRoadElementSegmentId(), getRoadElementLaneId(), getCurvilinearAbscissa()); }

}

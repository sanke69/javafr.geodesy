package fr.gis.utils.objects;

import java.text.DecimalFormat;

import fr.geodesic.api.referential.Datum;
import fr.gis.api.Gis;
import fr.gis.api.road.Road;
import fr.gis.api.road.RoadCoordinate;
import fr.java.math.topology.Coordinate;
import fr.utils.geodesic.adapters.AdapterGeoSpheric2D;

public class AdapterMapSpheric2D extends AdapterGeoSpheric2D implements RoadCoordinate.Spheric2D.Editable {
	Road.Element  element;
	Gis.Direction direction;
	int           segmentId, laneId;
	double        heading, s;

	Coordinate    projection;
	double        error;

	public AdapterMapSpheric2D(double _longitude, double _latitude) {
		this(Datum.WGS84, _longitude, _latitude, null, null, -1, -1, Double.NaN);
	}
	public AdapterMapSpheric2D(Datum _datum, double _longitude, double _latitude) {
		this(_datum, _longitude, _latitude, null, null, -1, -1, Double.NaN);
	}
	public AdapterMapSpheric2D(double _longitude, double _latitude, Road.Element _element, Gis.Direction _direction) {
		this(Datum.WGS84, _longitude, _latitude, _element, _direction, -1, -1, Double.NaN);
	}
	public AdapterMapSpheric2D(Datum _datum, double _longitude, double _latitude, Road.Element _element, Gis.Direction _direction) {
		this(_datum, _longitude, _latitude, _element, _direction, -1, -1, Double.NaN);
	}
	public AdapterMapSpheric2D(double _longitude, double _latitude, Road.Element _element, Gis.Direction _direction, int _segmentId, int _laneId, double _abscissaCurvilinear) {
		this(Datum.WGS84, _longitude, _latitude, _element, _direction, _segmentId, _laneId, _abscissaCurvilinear);
	}
	public AdapterMapSpheric2D(Datum _datum, double _longitude, double _latitude, Road.Element _element, Gis.Direction _direction, int _segmentId, int _laneId, double _abscissaCurvilinear) {
		super(_datum, _longitude, _latitude);
		element   = _element;
		direction = _direction;
		segmentId = _segmentId;
		laneId    = _laneId;

		s = _abscissaCurvilinear;
	}
	public AdapterMapSpheric2D(double _longitude, double _latitude, double _heading, Road.Element _element, Gis.Direction _direction, int _segmentId, int _laneId, double _abscissaCurvilinear) {
		this(Datum.WGS84, _longitude, _latitude, _heading, _element, _direction, _segmentId, _laneId, _abscissaCurvilinear);
	}
	public AdapterMapSpheric2D(Datum _datum, double _longitude, double _latitude, double _heading, Road.Element _element, Gis.Direction _direction, int _segmentId, int _laneId, double _abscissaCurvilinear) {
		super(_datum, _longitude, _latitude);
		heading   = _heading;

		element   = _element;
		direction = _direction;
		segmentId = _segmentId;
		laneId    = _laneId;

		s = _abscissaCurvilinear;
	}

	@Override public void 								setHeading(double _heading) 							{ heading = _heading; }
	@Override public double 							getHeading() 											{ return heading; }

	@Override public void 								setRoadElement(Road.Element _roadElt) 					{ element =_roadElt; }
	@Override public Road.Element 						getRoadElement() 										{ return element; }
	@Override public void 								setRoadElementSegmentId(int _id) 						{ segmentId = _id; }
	@Override public int 								getRoadElementSegmentId() 								{ return segmentId; }
	@Override public void 								setRoadElementLaneId(int _n) 							{ laneId = _n; }
	@Override public int 								getRoadElementLaneId() 									{ return laneId; }
	@Override public void 								setRoadElementDirection(Gis.Direction _direction) 		{ direction = _direction; }
	@Override public Gis.Direction 						getRoadElementDirection() 								{ return direction; }
	@Override public void 								setCurvilinearAbscissa(double _s, boolean _fromEnd) 	{ s = _fromEnd ? getRoadElement().getLength() - _s : _s;}
	@Override public double 							getCurvilinearAbscissa(boolean _fromEnd) 				{ return s; }

	@Override public void 								setProjection(Coordinate _proj) 						{ projection = _proj; }
	@Override public Coordinate 						getProjection() 										{ return projection; }
	@Override public void 								setProjectionError(double _e) 							{ error = _e; }
	@Override public double 							getProjectionError() 									{ return error; }

	@Override public String 							toString() 												{ return toString(new DecimalFormat()); }
	@Override public String 							toString(DecimalFormat _df) 							{ return "(" + getLongitude() + ", " + getLatitude() + ")"; }

	@Override public RoadCoordinate.Spheric2D.Editable	clone() 												{ return new AdapterMapSpheric2D(getDatum(), getLongitude(), getLatitude(), getRoadElement(), getRoadElementDirection(), getRoadElementSegmentId(), getRoadElementLaneId(), getCurvilinearAbscissa()); }

}

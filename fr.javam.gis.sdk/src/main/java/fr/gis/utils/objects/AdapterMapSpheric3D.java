package fr.gis.utils.objects;

import java.text.DecimalFormat;

import fr.geodesic.api.referential.Datum;
import fr.gis.api.Gis;
import fr.gis.api.road.Road;
import fr.gis.api.road.RoadCoordinate;
import fr.java.math.topology.Coordinate;
import fr.utils.geodesic.adapters.AdapterGeoSpheric3D;

public class AdapterMapSpheric3D extends AdapterGeoSpheric3D implements RoadCoordinate.Spheric3D.Editable {
	Road.Element  element;
	Gis.Direction direction;
	int           segmentId, laneId;
	double        heading, s;

	Coordinate    projection;
	double        error;

	public AdapterMapSpheric3D(double _longitude, double _latitude, double _alt) {
		this(Datum.WGS84, _longitude, _latitude, _alt, null, null, -1, -1, Double.NaN);
	}
	public AdapterMapSpheric3D(Datum _datum, double _longitude, double _latitude, double _alt) {
		this(_datum, _longitude, _latitude, _alt, null, null, -1, -1, Double.NaN);
	}
	public AdapterMapSpheric3D(double _longitude, double _latitude, double _alt, Road.Element _element, Gis.Direction _direction) {
		this(Datum.WGS84, _longitude, _latitude, _alt, _element, _direction, -1, -1, Double.NaN);
	}
	public AdapterMapSpheric3D(Datum _datum, double _longitude, double _latitude, double _alt, Road.Element _element, Gis.Direction _direction) {
		this(_datum, _longitude, _latitude, _alt, _element, _direction, -1, -1, Double.NaN);
	}
	public AdapterMapSpheric3D(double _longitude, double _latitude, double _alt, Road.Element _element, Gis.Direction _direction, int _segmentId, int _laneId, double _abscissaCurvilinear) {
		this(Datum.WGS84, _longitude, _latitude, _alt, _element, _direction, _segmentId, _laneId, _abscissaCurvilinear);
	}
	public AdapterMapSpheric3D(Datum _datum, double _longitude, double _latitude, double _alt, Road.Element _element, Gis.Direction _direction, int _segmentId, int _laneId, double _abscissaCurvilinear) {
		super(_datum, _longitude, _latitude, _alt);
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
	@Override public String 							toString(DecimalFormat _df) 							{ return "(" + getLongitude() + ", " + getLatitude() + " : " + getAltitude() + " m" + ")"; }

	@Override public RoadCoordinate.Spheric3D.Editable	clone() 												{ return new AdapterMapSpheric3D(getDatum(), getLongitude(), getLatitude(), getAltitude(), getRoadElement(), getRoadElementDirection(), getRoadElementSegmentId(), getRoadElementLaneId(), getCurvilinearAbscissa()); }

}

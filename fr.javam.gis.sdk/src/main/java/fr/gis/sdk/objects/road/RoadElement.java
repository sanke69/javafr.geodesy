package fr.gis.sdk.objects.road;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.utils.GeoCoordinates;
import fr.gis.api.Gis;
import fr.gis.api.Gis.User;
import fr.gis.api.road.Road;
import fr.gis.sdk.objects.core.GisCurve;
import fr.java.lang.properties.ID;

public class RoadElement extends GisCurve implements Road.Element.Linkable {

	private double				length;
	private Road.Category		category;
	private Road.SubCategory 	nature;
	private Gis.Direction 		drivingWay;
	private int					laneCount;

	// Road Graph Properties
	private Set<Road.Element> previous;
	private Set<Road.Element> next;

	public RoadElement(GisCurve _s) {
		super(_s.getId(), _s.getTops().toArray(new GeoCoordinate[0]));
		length     = super.getLength();
		category   = Road.Category.Unknown;
		nature     = Road.SubCategory.NotApplicable;
		drivingWay = Gis.Direction.UNKNOWN;
		laneCount  = -1;
	}
	public RoadElement(ID _id, GeoCoordinate... _nodes) {
		super(_id, _nodes);
		length     = super.getLength();
		category   = Road.Category.Unknown;
		nature     = Road.SubCategory.NotApplicable;
		drivingWay = Gis.Direction.UNKNOWN;
		laneCount  = -1;
	}
	public RoadElement(ID _id, List<GeoCoordinate> _nodes) {
		super(_id, _nodes);
		length     = super.getLength();
		category   = Road.Category.Unknown;
		nature     = Road.SubCategory.NotApplicable;
		drivingWay = Gis.Direction.UNKNOWN;
		laneCount  = -1;
	}
	public RoadElement(ID _id, Gis.Direction _drivingWay, GeoCoordinate... _nodes) {
		super(_id, _nodes);
		length     = super.getLength();
		category   = Road.Category.Unknown;
		nature     = Road.SubCategory.NotApplicable;
		drivingWay = _drivingWay;
		laneCount  = -1;
	}
	public RoadElement(ID _id, Road.SubCategory _nature, Gis.Direction _drivingWay, GeoCoordinate... _nodes) {
		super(_id, _nodes);
		length     = super.getLength();
		nature     = _nature;
		drivingWay = _drivingWay;
		laneCount  = -1;
	}
	public RoadElement(ID _id, Gis.Direction _drivingWay, List<GeoCoordinate> _geometry, Set<Road.Element> _previous, Set<Road.Element> _next) {
		super(_id, _geometry);
		length     = super.getLength();
		nature     = Road.SubCategory.NotApplicable;
		drivingWay = _drivingWay;
		previous   = _previous;
		next       = _next;
	}
	public RoadElement(ID _id, Road.SubCategory _nature, Gis.Direction _drivingWay, List<GeoCoordinate> _geometry, Set<Road.Element> _previous, Set<Road.Element> _next) {
		super(_id, _geometry);
		length     = super.getLength();
		nature     = _nature;
		drivingWay = _drivingWay;
		previous   = _previous;
		next       = _next;
	}

	@Override
	public double 				getLength() {
		return length;
	}

	@Override
	public Road.Category 		getCategory() {
		return category;
	}
	@Override
	public Road.SubCategory 	getNature() {
		return nature;
	}
	@Override
	public Gis.Direction 		getDrivingWay() {
		return drivingWay;
	}
	@Override
	public int 					getLaneCount() {
		return laneCount;
	}

	@Override
	public double 				getHeading() { // From North
		return GeoCoordinates.computeHeading(getStart().asUTM(), getEnd().asUTM());
	}
	@Override
	public double 				getHeading(int _idSeg) { // From North
		return GeoCoordinates.computeHeading(getTops().get(_idSeg - 1).asUTM(), getTops().get(_idSeg).asUTM());
	}

	@Override
	public GeoCoordinate 		getStart() {
		return geometry.get(0);
	}
	@Override
	public GeoCoordinate 		getEnd() {
		return geometry.get(geometry.size() - 1);
	}

	@Deprecated
	public void 				forceLength(double _forcedLength) {
		length = _forcedLength;
	}

	// Road Graph Properties
	@Override
	public Set<Road.Element> 	getPreviousElements() {
		return previous != null ? previous : Collections.emptySet();
	}

	@Override
	public void 				addPreviousElement(Road.Element _elt) {
		if(previous == null)
			previous = new HashSet<Road.Element>();
		previous.add(_elt);
	}
	@Override
	public void 				addPreviousElements(Road.Element... _elts) {
		if(previous == null)
			previous = new HashSet<Road.Element>();
		previous.addAll(Arrays.asList(_elts));
	}

	@Override
	public Set<Road.Element> 	getNextElements() {
		return next != null ? next : Collections.emptySet();
	}

	@Override
	public void 				addNextElement(Road.Element _elt) {
		if(next == null)
			next = new HashSet<Road.Element>();
		next.add(_elt);
	}
	@Override
	public void 				addNextElements(Road.Element... _elts) {
		if(next == null)
			next = new HashSet<Road.Element>();
		next.addAll(Arrays.asList(_elts));
	}

	@Override
	public String 				toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("ROAD ELEMENT: " + getId() + "\n");
		sb.append(" - GEOM.:   " + getTops().stream().map((a) -> "" + a).reduce((a, b) -> a + ", " + b) + "\n");
		sb.append(" - BOUNDS.: " + getTops().get(0) + " - " + getTops().get(getTops().size() - 1));
		sb.append(" - NB LANE: " + getLaneCount() + "\n");
		sb.append(" - NATURE:  " + getNature() + "\n");
		sb.append(" - WAY:     " + getDrivingWay() + "\n");
		if(previous != null)
			sb.append(" - PREV.:   " + getPreviousElements().stream().map((a) -> "" + a.getId()).reduce((a, b) -> a + ", " + b) + "\n");
		if(next != null)
			sb.append(" - NEXT.:   " + getNextElements().stream().map((a) -> "" + a.getId()).reduce((a, b) -> a + ", " + b) + "\n");

		return sb.toString();
	}
	
	public static class Builder {
		ID 					id;
		List<GeoCoordinate> geometry;
		int					laneCount;
		Road.SubCategory 		nature;
		Gis.Direction 		direction;

		public Builder(ID _id) {
			super();
			id = _id;
		}

		public Builder setGeometry(List<GeoCoordinate> _geometry) {
			geometry = _geometry;
			return this;
		}
		public Builder setLaneCount(int _nbLane) {
			laneCount = _nbLane;
			return this;
		}
		public Builder setRoadNature(Road.SubCategory _nature) {
			nature = _nature;
			return this;
		}
		public Builder setDirection(Gis.Direction _direction) {
			direction = _direction;
			return this;
		}

		public RoadElement build() {
			RoadElement elt = new RoadElement(id, geometry);
			elt.laneCount = laneCount;
			elt.nature = nature;
			elt.drivingWay = direction;
			
			return elt;
		}

	}

	@Override
	public EnumSet<User> getAllowedUsers() {
		return EnumSet.noneOf(User.class);
	}

}

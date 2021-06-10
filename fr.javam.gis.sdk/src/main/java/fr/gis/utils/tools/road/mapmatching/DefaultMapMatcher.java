package fr.gis.utils.tools.road.mapmatching;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.utils.GeoCoordinates;
import fr.gis.api.Gis;
import fr.gis.api.road.Road;
import fr.gis.api.road.RoadCoordinate;
import fr.gis.api.road.tools.RoadToolBox;
import fr.gis.api.road.tools.engines.MapMatchEngine;
import fr.gis.utils.GisCoordinates;
import fr.java.maths.Angles;

public class DefaultMapMatcher implements MapMatchEngine {
	private static final double EPSILON = 20.0;

	RoadToolBox  mapToolBox;

	public DefaultMapMatcher(RoadToolBox  _mapToolBox) {
		super();
		mapToolBox = _mapToolBox;
	}

	protected RoadToolBox  			getRoadToolBox() {
		return mapToolBox;
	}

	public Optional<RoadCoordinate> getMapMatchedPosition(GeoCoordinate _position, double _heading) {
		return getMapMatchedPosition(	_position, 
										_heading, 
										mapToolBox.getSurroundingRoadElements(_position, 100d)   );
	}
	public Optional<RoadCoordinate> getMapMatchedPosition(GeoCoordinate _position, double _heading, Collection<Road.Element> _elts) {
		if(_elts == null || _elts.isEmpty())
			return Optional.empty();

		List<MapMatchedResult> results = new ArrayList<MapMatchedResult>();
		for(Road.Element elt : _elts) {
			MapMatchedResult mmr = getTheProjectionOnElement(_position, _heading, elt);
			if(mmr != null)
				results.add(mmr);
		}

		RoadCoordinate.Editable mmp = null;
		if(results.size() > 0) {
			Collections.sort(results);
			for(MapMatchedResult candidate : results) {
				boolean validCandidate = true;

				// Check Heading & Direction
				double      	headSeg = candidate.elt.getHeading(candidate.idSeg);
				Gis.Direction	mmpDir  = Gis.Direction.UNKNOWN;

				if(!Double.isNaN(_heading)) {
					if(Angles.colinear(_heading, headSeg, 45) == 0)
						validCandidate = false;

					if(validCandidate
					&& mapToolBox.isValidDrivingWay(_heading, candidate.elt, candidate.idSeg))
						validCandidate = false;

					mmpDir = Angles.colinear(_heading, headSeg, 45) > 0 ? Gis.Direction.DIRECT : Gis.Direction.INDIRECT;
				}

				if(true/*validCandidate*/) {
					mmp = GisCoordinates.newMapCoordinate(_position, candidate.heading, candidate.elt, mmpDir, candidate.s);
					mmp.setRoadElementSegmentId	(candidate.idSeg);
					mmp.setRoadElementLaneId	(0);

					mmp.setProjection			(candidate.projection);
					mmp.setProjectionError		(candidate.distance);

					mmp.setCurvilinearAbscissa	(candidate.s_inv, true);
					break;
				}
			}
		}

		return Optional.ofNullable(mmp);
	}

	@Override
	public List<RoadCoordinate> 	getMapMatchedPositions(List<GeoCoordinate> rawCoords, Collection<Road.Element> _elts) {
		List<RoadCoordinate> mmps = new ArrayList<RoadCoordinate>();
		
		Double         			 heading  = Double.NaN;
		Collection<Road.Element> proxyMap = _elts;

		GeoCoordinate prev = null;
		for(GeoCoordinate raw : rawCoords) {			
			heading = prev == null ? Double.NaN : GeoCoordinates.computeHeading(prev.asUTM(), raw.asUTM());

			if(prev == null || (prev != null && GeoCoordinates.computeDistanceUTM(prev.asUTM(), raw.asUTM()) > 0)) {

				getMapMatchedPosition(raw, heading, proxyMap).ifPresent(
						mmp -> {
							mmps.add(mmp);

							proxyMap.clear();
							proxyMap.addAll( mapToolBox.getReachableElements(mmp, 150) );
						}
				);

			}
			prev = raw;

		}
		
		return mmps;
	}
	public Set<Road.Element> 		getMapMatchedRoadElements(List<GeoCoordinate> rawCoords, Collection<Road.Element> _elts) {
		List<RoadCoordinate> mmps = getMapMatchedPositions(rawCoords, _elts);
		Set<Road.Element>             elts = new LinkedHashSet<Road.Element>();
		for(RoadCoordinate mmp : mmps) {
			Road.Element mmpSeg = mmp.getRoadElement();
			elts.add(mmpSeg);
		}

		return elts;
	}

	protected MapMatchedResult 		getTheProjectionOnElement(GeoCoordinate _p, double _heading, Road.Element _elt) {
		if(_p == null || _elt == null) return null;

		List<GeoCoordinate> 		eltNodes 	= _elt.getTops();
		GeoCoordinate.Cartesian2D  	cA, cB, cP 	= _p.asUTM();
		double   		 			eps 		= EPSILON,
						 			lAB			= 0.0,
						 			S			= 0.0;
		MapMatchedResult 			R			= new MapMatchedResult();

		if(eltNodes != null) {
			int segId = eltNodes.size();

			while(segId-- > 1) {
				cA  = eltNodes.get(segId - 1).asUTM();
				cB  = eltNodes.get(segId).asUTM();
				lAB = Math.sqrt( Math.pow(cA.getX() - cB.getX(), 2.0) + Math.pow(cA.getY() - cB.getY(), 2.0) );

				MapMatchedResult test = getTheProjectionOnSegment(cP, _heading, cA, cB, segId, lAB, S, eps);
				if(test != null) {
					R   = test;
					eps = R.distance;
				}

	    		S += lAB;
			}

		}

		R.elt = _elt;
		R.s     = S - R.s_inv;

		return eps != EPSILON ? R : null;
	}
	protected MapMatchedResult 		getTheProjectionOnSegment(GeoCoordinate.Cartesian2D _P, double _heading, GeoCoordinate.Cartesian2D _A, GeoCoordinate.Cartesian2D _B, int _segId, double _segLength, double _curAbs, double _epsilon) {
		double Hx     = 0.0;
		double Hy     = 0.0;
		double Hz     = 0.0;
		double headAB = 0.0;

		double kA     = 0.0;
		double kB     = 0.0;

		if(_B.getX() == _A.getX()) {
			Hx = _A.getX();
			Hy = _P.getY();

			double yAB = _B.getY() - _A.getY();		
			double yAH = Hy - _A.getY();
			double yBH = Hy - _B.getY();

			kA  = yAH/yAB;
			kB  = yBH/yAB;
		} else if(_B.getY() == _A.getY()) {
			Hx = _P.getX();
			Hy = _A.getY();

			double xAB = _B.getX() - _A.getX();		
			double xAH = Hx - _A.getX();
			double xBH = Hx - _B.getX();

			kA  = xAH/xAB;
			kB  = xBH/xAB;
		} else {
			double a  = (_B.getY() - _A.getY()) / (_B.getX() - _A.getX());
			double b  = _A.getY() - a * _A.getX();
			
			headAB = (double) (90.0 - Angles.Radian2Degree(Math.atan2(_B.getY() - _A.getY(), _B.getX() - _A.getX())));

			Hx = (1.0 / (a*a + 1.0)) * (_P.getX() + a * _P.getY() - a * b);
			Hy = a * Hx + b;

			double _AB = _B.getX() - _A.getX();		
			double _AH = Hx - _A.getX();
			double _BH = Hx - _B.getX();

			kA  = _AH/_AB;
			kB  = _BH/_AB;
		}

		double dPH = Math.sqrt( Math.pow(_P.getX() - Hx, 2.0) + Math.pow(_P.getY() - Hy, 2.0) );
		double dAH = Math.sqrt( Math.pow(_A.getX() - Hx, 2.0) + Math.pow(_A.getY() - Hy, 2.0) );
//		double dBH = Math.sqrt( Math.pow(_B.getX() - Hx, 2.0) + Math.pow(_B.getY() - Hy, 2.0) );

		boolean headingTest = (Double.isNaN(_heading) || (!Double.isNaN(_heading) && Angles.colinear(headAB, _heading, 45.0) != 0));
		boolean projTest    = (Math.signum(kA) == 1 && Math.signum(kA) != Math.signum(kB));

		if(headingTest && projTest)	
    		if(dPH < _epsilon) {
    			MapMatchedResult R = new MapMatchedResult();

    			R.projection = GeoCoordinates.newUTM(Hx, "31", Hy, "U", Hz);
    			R.heading    = headAB;

    			R.distance   = dPH;
    			R.idSeg      = _segId;
    			R.s_inv 	 = _curAbs + _segLength - dAH; // S + dBH

    			return R;
    		}

		return null;
	}

}

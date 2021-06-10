package fr.gis.utils.tools.road.mapmatching;

import java.util.Collection;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.road.Road;
import fr.gis.api.road.RoadCoordinate;
import fr.gis.api.road.tools.RoadToolBox;
import fr.java.lang.collections.RingList;

public class DynamicMapMatcher extends DefaultMapMatcher { 
	// MapMatching
	protected RingList<GeoCoordinate> 	history;
	protected boolean 					mmInitialized;
	protected int						mmAttempt     = 0, 
										mmMaxAttempt  = 10;

	GeoCoordinate						coordinate;
	RoadCoordinate						position;

	public DynamicMapMatcher(RoadToolBox _mapToolBox) {
		super(_mapToolBox);
	}

	public RoadCoordinate compute(GeoCoordinate _coords, double _heading, Collection<Road.Element> _elts) {
		RoadCoordinate mapInfos = null;

		if(!mmInitialized) {
			if(_coords != null)
				mapInfos = super.getMapMatchedPosition(_coords, Double.NaN).get();
			else {
				mmInitialized = true;
				mmAttempt = 0;
			}

		} else {
			if(_coords != null) {
				mapInfos = super.getMapMatchedPosition(_coords, _heading, _elts).get();
				mmAttempt = 0;
			} else {
				if(mmAttempt > mmMaxAttempt)
					mmInitialized = false;
				mmAttempt++;
			}

		}

		history.add(_coords);

		return mapInfos;
	}

}

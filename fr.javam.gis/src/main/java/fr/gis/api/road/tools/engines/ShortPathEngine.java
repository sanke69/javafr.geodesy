package fr.gis.api.road.tools.engines;

import java.util.Collection;
import java.util.List;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.road.Road;
import fr.java.lang.properties.ID;

public interface ShortPathEngine {

	public void 	setMapTree(Collection<Road.Element> _elts);
	public void 	clearMapTree();

	public double 	getDistanceBetween(GeoCoordinate _p0, GeoCoordinate _p1);
	public List<ID> getWayBetween(GeoCoordinate _p0, GeoCoordinate _p1);

}

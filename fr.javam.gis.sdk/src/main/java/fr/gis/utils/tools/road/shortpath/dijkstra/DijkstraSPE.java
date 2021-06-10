package fr.gis.utils.tools.road.shortpath.dijkstra;

import java.util.Collection;
import java.util.List;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.road.Road;
import fr.gis.api.road.tools.engines.ShortPathEngine;
import fr.java.lang.properties.ID;

public class DijkstraSPE implements ShortPathEngine {

	public Dijkstra      algo;
	public DijkstraGraph data;

	public DijkstraSPE(Collection<Road.Element> _road) {
		if(_road != null) {
			data = new DijkstraGraph(_road);
			algo = new Dijkstra(data);
		}
	}

	@Override
	public void setMapTree(Collection<Road.Element> _road) {
		data = new DijkstraGraph(_road);
		algo = new Dijkstra(data); 
	}

	@Override
	public void clearMapTree() {
		data = null;
		algo = null; 
	}

	@Override
	public double getDistanceBetween(GeoCoordinate _p0, GeoCoordinate _p1) {
		if(data == null || algo == null)
			return -1.0;

		int i0 = 0; // Convert from Coordinate to Nearest graph node
		int i1 = 7; // Convert from Coordinate to Nearest graph node
		System.err.println("ShortPathEngine:: TBC");

		List<Integer> path = algo.getPath(i0, i1);
		return data.waypoints2distance(path);
	}

	@Override
	public List<ID> getWayBetween(GeoCoordinate _p0, GeoCoordinate _p1) {
		if(data == null || algo == null)
			return null;

		int i0 = 0; // Convert from Coordinate to Nearest graph node
		int i1 = 7; // Convert from Coordinate to Nearest graph node
		System.err.println("ShortPathEngine:: TBC");

		List<Integer> path = algo.getPath(i0, i1);
		return data.waypoints2ids(path);
	}

}
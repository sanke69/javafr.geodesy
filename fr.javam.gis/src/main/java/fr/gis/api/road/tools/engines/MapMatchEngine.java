package fr.gis.api.road.tools.engines;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.road.Road;
import fr.gis.api.road.RoadCoordinate;

public interface MapMatchEngine {

	public static class MapMatchedResult implements Comparable<MapMatchedResult> {
		public GeoCoordinate 		projection;
		public double    			distance, heading;

		public Road.Element			elt;
		public int					idSeg;
		public double      			s, s_inv;

		public MapMatchedResult() {
			projection = null;
			distance   = 1e6;
		}

		public int compareTo(MapMatchedResult o) {
			MapMatchedResult O = (MapMatchedResult) o;
			return Double.compare(distance, O.distance);
		}

		public String toString() {
			String ret = new String();

			DecimalFormat dist = new DecimalFormat("0.000 m");

			ret = "[d: " + dist.format(distance) + "] " + projection.toString() + " " + elt.getId().toString() + " " + s;

			return ret;
		}

	}

	public Optional<RoadCoordinate> 			getMapMatchedPosition		(GeoCoordinate _position, double _heading);
	public Optional<RoadCoordinate> 			getMapMatchedPosition		(GeoCoordinate _position, double _heading, Collection<Road.Element> _elts);

	public List<RoadCoordinate> 				getMapMatchedPositions		(List<GeoCoordinate> rawCoords, Collection<Road.Element> _elts);
	public Set<Road.Element> 					getMapMatchedRoadElements	(List<GeoCoordinate> rawCoords, Collection<Road.Element> _elts);

}

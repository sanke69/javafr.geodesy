package fr.gis.utils.tools.road.shortpath.dijkstra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.road.Road;
import fr.java.lang.properties.ID;

public class DijkstraGraph {

	public static final double INFINITE = 1e9;

	Collection<Road.Element>	segments;
	Map<GeoCoordinate, Integer>	id2node;
	Map<Integer, GeoCoordinate>	node2id;

	double[][]	mapDistance;
	ID[][]		mapId;

	public DijkstraGraph(Collection<Road.Element> _road) {
		segments = _road;
		id2node = new HashMap<GeoCoordinate, Integer>();
		node2id = new HashMap<Integer, GeoCoordinate>();

		buildGraph(_road);
	}

	public List<ID> waypoints2ids(List<Integer> _nodes) {
		List<ID> ids = new ArrayList<ID>();
		for(int i = 0; i < _nodes.size() - 1; ++i) {
			int k = _nodes.get(i);
			int l = _nodes.get(i + 1);

			GeoCoordinate A = node2id.get(k);
			GeoCoordinate B = node2id.get(l);

			for(Road.Element s : segments) {
				if((s.getStart().equals(A) && s.getEnd().equals(B)) || (s.getStart().equals(B) && s.getEnd().equals(A))) {
					ids.add(s.getId());
					break;
				}
			}
		}
		return ids;
	}
	public double waypoints2distance(List<Integer> _nodes) {
		double length = 0;
		for(int i = 0; i < _nodes.size() - 1; ++i) {
			int k = _nodes.get(i);
			int l = _nodes.get(i + 1);

			GeoCoordinate A = node2id.get(k);
			GeoCoordinate B = node2id.get(l);

			for(Road.Element s : segments) {
				if((s.getStart().equals(A) && s.getEnd().equals(B)) || (s.getStart().equals(B) && s.getEnd().equals(A))) {
					length += s.getLength();
					break;
				}
			}
		}
		return length;
	}

	private void buildGraph(Collection<Road.Element> _road) {
		mapDistance = new double[_road.size()][_road.size()];
		mapId = new ID[_road.size()][_road.size()];

		for(int j = 0; j < mapDistance.length; ++j)
			for(int i = 0; i < mapDistance[j].length; ++i)
				mapDistance[j][i] = INFINITE;

		int index = 0;
		for(Road.Element elt : _road) {
			GeoCoordinate nodeA = elt.getStart();
			Integer idA = id2node.get(nodeA);
			if(idA == null) {
				id2node.put(nodeA, index);
				node2id.put(index, nodeA);
				idA = index; //id2node.get( nodeA );
				index++;
			}

			GeoCoordinate nodeB = elt.getEnd();
			Integer idB = id2node.get(nodeB);
			if(idB == null) {
				id2node.put(nodeB, index);
				node2id.put(index, nodeB);
				idB = index;
				index++;
			}

			switch (elt.getDrivingWay()) {
			case BOTH:
				mapDistance[idA][idB] = elt.getLength();
				mapDistance[idB][idA] = elt.getLength();
				mapId[idA][idB] = elt.getId();
				mapId[idB][idA] = elt.getId();
				break;
			case DIRECT:
				mapDistance[idA][idB] = elt.getLength();
				mapId[idA][idB] = elt.getId();
				break;
			case INDIRECT:
				mapDistance[idB][idA] = elt.getLength();
				mapId[idB][idA] = elt.getId();
				break;
			case NONE:
			case UNKNOWN:
			default:
				break;
			}
		}
	}

}

package fr.gis.utils.tools.road.shortpath.astar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.road.Road;
import fr.java.lang.properties.ID;

public class AStarGraph {

	static class Cell {
		double	heuristicCost	= 0; //Heuristic cost
		double	finalCost		= 0; //G+H
		int		i, j;
		Cell	parent;

		boolean isClosed, isBlocked;

		Cell(int _i, int _j) {
			i             = _i;
			j             = _j;
			heuristicCost = 0.0;
			finalCost     = 0.0;
			isClosed      = false;
			isBlocked     = false;
			parent        = null;
		}

		@Override
		public String toString() {
			return "[" + this.i + ", " + this.j + "]";
		}
	}

	public static final double INFINITE = Double.NaN;

	Collection<Road.Element>	segments;
	Map<GeoCoordinate, Integer>		id2node;
	Map<Integer, GeoCoordinate>		node2id;

	Cell[][]			grid;
	PriorityQueue<Cell>	open;

	ID[][]		mapId;

	public AStarGraph(Collection<Road.Element> _road) {
		segments = _road;
		id2node = new HashMap<GeoCoordinate, Integer>();
		node2id = new HashMap<Integer, GeoCoordinate>();

		buildGraph(_road);
	}

	public void buildGraph(Collection<Road.Element> _road/*, int tCase, int x, int y, int si, int sj, int ei, int ej, int[][] blocked*/) {
		grid = new Cell[_road.size()][_road.size()];
		mapId = new ID[_road.size()][_road.size()];
		open = new PriorityQueue<>((Object o1, Object o2) -> {
			Cell c1 = (Cell) o1;
			Cell c2 = (Cell) o2;

			return c1.finalCost < c2.finalCost ? -1 : c1.finalCost > c2.finalCost ? 1 : 0;
		});

		for(int j = 0; j < grid.length; ++j)
			for(int i = 0; i < grid[j].length; ++i) {
				grid[j][i] = new Cell(j,i);
				grid[j][i].heuristicCost = INFINITE;
				grid[j][i].isBlocked = true;
			}

		int index = 0;
		for(Road.Element elt : _road) { // TODO:: 
			GeoCoordinate nodeA = elt.getStart();
			Integer idA = id2node.get(nodeA);
			if(idA == null) {
				id2node.put(nodeA, index);
				node2id.put(index, nodeA);
//				idA = index; //id2node.get( nodeA );
				idA = id2node.get( nodeA );
				index++;
			}

			GeoCoordinate nodeB = elt.getEnd();
			Integer idB = id2node.get(nodeB);
			if(idB == null) {
				id2node.put(nodeB, index);
				node2id.put(index, nodeB);
//				idB = index; //id2node.get( nodeB );
				idB = id2node.get( nodeB );
				index++;
			}

			switch (elt.getDrivingWay()) {
			case BOTH:
				grid[idA][idB].heuristicCost = elt.getLength();
				grid[idB][idA].heuristicCost = elt.getLength();
				grid[idA][idB].isBlocked = false;
				grid[idB][idA].isBlocked = false;
				mapId[idA][idB] = elt.getId();
				mapId[idB][idA] = elt.getId();
				break;
			case DIRECT:
				grid[idA][idB].heuristicCost = elt.getLength();
				grid[idB][idA].isBlocked = true;
				mapId[idA][idB] = elt.getId();
				break;
			case INDIRECT:
				grid[idB][idA].heuristicCost = elt.getLength();
				grid[idA][idB].isBlocked = true;
				mapId[idB][idA] = elt.getId();
				break;
			case NONE:
			case UNKNOWN:
			default:
				break;
			}
		}

		//Display initial map
		System.out.println("Grid: ");
		for(int i = 0; i < _road.size(); ++i) {
			for(int j = 0; j < _road.size(); ++j) {
				if(i == 0 && j == 0)
					System.out.print("SO  "); //Source
				else if(i == 3 && j == 3)
					System.out.print("DE  ");  //Destination
				else if(grid[i][j].heuristicCost != INFINITE)
					System.out.printf("%.1f ", grid[i][j].heuristicCost);
				else
					System.out.print("-   ");
			}
			System.out.println();
		}
		System.out.println();

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

}

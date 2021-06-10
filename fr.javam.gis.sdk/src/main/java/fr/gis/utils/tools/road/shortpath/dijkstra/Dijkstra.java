package fr.gis.utils.tools.road.shortpath.dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dijkstra {

	private DijkstraGraph data;

	private double[]	distanceFromStart;
	private boolean[]	activesNodes;
	private int			dim;
	private int[]		precedences;

	public Dijkstra(DijkstraGraph _data) {
		super();
		data = _data;
	}

	public List<Integer> getPath(final int start, final int end) {
		return getPath(new int[] { start }, new int[] { end });
	}

	public List<Integer> getPath(final int start, final int[] ends) {
		return getPath(new int[] { start }, ends);
	}

	public List<Integer> getPath(final int[] starts, final int[] ends) {
		Arrays.sort(ends);

		init(starts);
		final int end = processDistances(ends);
		return buildPath(end);
	}

	private void init(final int[] start) {
		dim = data.mapDistance.length;
		activesNodes = new boolean[dim];

		precedences = new int[dim];
		Arrays.fill(precedences, -1);

		distanceFromStart = new double[dim];
		Arrays.fill(distanceFromStart, Integer.MAX_VALUE);

		for(final int value : start)
			activeNode(value, value, 0);
	}

	private List<Integer> buildPath(final int end) {
		final List<Integer> path = new ArrayList<Integer>();
		path.add(end);

		int position = end;
		do {
			path.add(0, precedences[position]);
			position = path.get(0);
		} while(distanceFromStart[position] != 0);

		return path;
	}

	private void activeAdjacents(final int node) {
		double distanceTo;
		for(int to = 0; to < dim; to++)
			if(isAdjacent(node, to) && (distanceTo = distanceFromStart[node] + data.mapDistance[node][to]) < distanceFromStart[to])
				activeNode(node, to, distanceTo);
	}

	private void activeNode(final int from, final int node, final double distance) {
		distanceFromStart[node] = distance;
		precedences[node] = from;
		activesNodes[node] = true;
	}

	private boolean isAdjacent(final int from, final int to) {
		return data.mapDistance[from][to] >= 0;
	}

	private int processDistances(final int[] ends) {
		final int next = selectNextNode();
		if(next == -1)
			return -1;

		if(Arrays.binarySearch(ends, next) >= 0)
			return next;

		activeAdjacents(next);
		activesNodes[next] = false;

		return processDistances(ends);
	}

	private int selectNextNode() {
		int nextNode = -1;
		for(int node = 0; node < dim; node++)
			if(activesNodes[node] && (nextNode == -1 || distanceFromStart[node] < distanceFromStart[nextNode]))
				nextNode = node;

		return nextNode;
	}
}
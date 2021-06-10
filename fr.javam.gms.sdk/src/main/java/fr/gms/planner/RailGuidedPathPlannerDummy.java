package fr.gms.planner;

import java.util.Arrays;
import java.util.List;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.utils.GeoCoordinates;
import fr.gis.api.Gis;
import fr.gis.api.Gis.PathType;
import fr.gis.sdk.objects.gis.GisPath;
import fr.gms.api.ego.EgoVehicle;
import fr.gms.api.service.PathPlanner;
import fr.java.jvm.properties.id.IDs;

public class RailGuidedPathPlannerDummy implements PathPlanner {
	int 		nbWayPoints;
	Gis.Path	reference;

	public RailGuidedPathPlannerDummy() {
		this(null, 100);
	}
	public RailGuidedPathPlannerDummy(int _nbWayPoints) {
		this(null, _nbWayPoints);
	}
	public RailGuidedPathPlannerDummy(Gis.Path _trajectory) {
		this(_trajectory, 100);
	}
	public RailGuidedPathPlannerDummy(Gis.Path _trajectory, int _nbWayPoints) {
		super();
		nbWayPoints = _nbWayPoints;
		reference   = _trajectory;
	}

	public void				setReference(Gis.Path _path) {
		reference = _path;
	}
	public Gis.Path			getReference() {
		return reference;
	}

	public void				setWayPointCount(int _nbWayPoints) {
		nbWayPoints = _nbWayPoints;
	}
	public int				getWayPointCount() {
		return nbWayPoints;
	}

	public Gis.Path			compute(EgoVehicle _ego) {
		if(_ego == null || getReference() == null)
			throw new IllegalArgumentException("PathPlanner not fully initialized !!!");

		List<GeoCoordinate> forwardPath  = generateForwardVectorFromPath(_ego.getPosition(), reference, nbWayPoints);
		List<GeoCoordinate> backwardPath = generateForwardVectorFromPath(_ego.getPosition(), reference, nbWayPoints);

		return new GisPath(IDs.newUTF8(_ego.getId() + "traj"), PathType.GroundTrajectory, forwardPath);
	}

	List<GeoCoordinate> 	generateForwardVectorFromPath(GeoCoordinate _pos, Gis.Path _path, int _nbDots) {
		if(reference.isLoop())
			return generateForwardVectorFromLoop(_pos, _path, _nbDots);

		return generateForwardVectorFromCurve(_pos, _path, _nbDots, true);
	}
	List<GeoCoordinate> 	generateBackwardVectorFromPath(GeoCoordinate _pos, Gis.Path _path, int _nbDots) {
		if(reference.isLoop())
			return generateBackwardVectorFromLoop(_pos, _path, _nbDots);

		return generateBackwardVectorFromCurve(_pos, _path, _nbDots, true);
	}

	List<GeoCoordinate> 	generateForwardVectorFromLoop(GeoCoordinate _pos, Gis.Path _path, int _nbDots) {
		List<GeoCoordinate> path = _path.getGeometry();
		GeoCoordinate[]     traj = new GeoCoordinate[_nbDots];

		int pathBegin    = getClosestIndex(_pos, _path), 
			pathEnd      = path.size();

		int coordCounter = 0;
		int idBeg        = pathBegin;

		while(coordCounter < _nbDots) {
			traj[coordCounter++] = path.get(idBeg).clone();

			idBeg = (idBeg+1) % (pathEnd);
		}

		return Arrays.asList(traj);
	}
	List<GeoCoordinate> 	generateForwardVectorFromCurve(GeoCoordinate _pos, Gis.Path _path, int _nbDots, boolean _fillWithLast) {
		List<GeoCoordinate> path = _path.getGeometry();
		GeoCoordinate[]     traj = new GeoCoordinate[_nbDots];

		int pathBegin    = getClosestIndex(_pos, _path), 
			pathEnd      = path.size();

		if(pathEnd - pathBegin > _nbDots) {
			for(int i = 0; i < _nbDots; ++i)
				traj[i] = path.get(pathBegin + i).clone();

		} else {
			for(int i = 0; i < pathEnd - pathBegin; ++i)
				traj[i] = path.get(pathBegin + i).clone();

			if(_fillWithLast)
				for(int j = pathEnd - pathBegin; j < _nbDots; ++j)
					traj[j] = path.get(pathEnd - 1).clone();
		}

		return Arrays.asList(traj);
	}

	List<GeoCoordinate> 	generateBackwardVectorFromLoop(GeoCoordinate _pos, Gis.Path _path, int _nbDots) {
		List<GeoCoordinate> path = _path.getGeometry();
		GeoCoordinate[]     traj = new GeoCoordinate[_nbDots];

		int pathBegin    = getClosestIndex(_pos, _path), 
			pathEnd      = path.size();

		int coordCounter = 0;
		int idBeg        = pathBegin;

		while(coordCounter < _nbDots) {
			traj[coordCounter++] = path.get(idBeg).clone();

			idBeg = (idBeg-1) >= 0 ? idBeg-1 : pathEnd - 1;
		}

		return Arrays.asList(traj);
	}
	List<GeoCoordinate> 	generateBackwardVectorFromCurve(GeoCoordinate _pos, Gis.Path _path, int _nbDots, boolean _fillWithLast) {
		List<GeoCoordinate> path = _path.getGeometry();
		GeoCoordinate[]     traj = new GeoCoordinate[_nbDots];

		int pathBegin    = getClosestIndex(_pos, _path);

		if(pathBegin > _nbDots) {
			for(int i = 0; i < _nbDots; ++i)
				traj[i] = path.get(pathBegin - i).clone();

		} else {
			for(int i = 0; i < pathBegin; ++i)
				traj[i] = path.get(pathBegin - i).clone();

			if(_fillWithLast)
				for(int j = pathBegin; j < _nbDots; ++j)
					traj[j] = path.get(0).clone();
		}

		return Arrays.asList(traj);
	}

	int 					getClosestIndex(GeoCoordinate _pos, Gis.Path _path) {
		double dCandidate = Double.MAX_VALUE;
		int    iCandidate = -1;

		for(int i = 0; i < _path.getGeometry().size(); ++i) {
			GeoCoordinate candidate = _path.getGeometry().get(i);
			double        distance  = GeoCoordinates.computeDistanceUTM(_pos, candidate);
			if(distance < dCandidate) {
				dCandidate = distance;
				iCandidate = i;
			}
		}

		return iCandidate;
	}

}

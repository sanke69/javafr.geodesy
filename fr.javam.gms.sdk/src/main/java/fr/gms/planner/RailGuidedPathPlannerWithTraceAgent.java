package fr.gms.planner;

import java.util.Arrays;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.utils.GeoCoordinates;
import fr.gis.api.Gis;
import fr.gis.api.Gis.PathType;
import fr.gis.sdk.objects.gis.GisPath;
import fr.gms.api.ego.EgoVehicle;
import fr.gms.api.service.PathPlanner;
import fr.gms.planner.agent.TraceAgent;
import fr.gms.planner.agent.ant.AntAgent;
import fr.java.jvm.properties.id.IDs;
import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.Points;

public class RailGuidedPathPlannerWithTraceAgent implements PathPlanner {
	int 		nbWayPoints;
	double		interWayPoints;
	Gis.Path	reference;
	TraceAgent  agent;

	public RailGuidedPathPlannerWithTraceAgent() {
		this(null, 100, 0.33);
	}
	public RailGuidedPathPlannerWithTraceAgent(int _nbWayPoints, double _interWayPoints) {
		this(null, _nbWayPoints, _interWayPoints);
	}
	public RailGuidedPathPlannerWithTraceAgent(Gis.Path _trajectory) {
		this(_trajectory, 100, 0.33);
	}
	public RailGuidedPathPlannerWithTraceAgent(Gis.Path _trajectory, int _nbWayPoints, double _interWayPoints) {
		super();
		nbWayPoints = _nbWayPoints;
		reference   = _trajectory;
		agent       = new AntAgent(_nbWayPoints, _interWayPoints);
	}

	public void				setReference(Gis.Path _path) {
		reference = _path;
	}
	public Gis.Path			getReference() {
		return reference;
	}

	public Gis.Path			compute(EgoVehicle _ego) {
		if(_ego == null || reference == null)
			throw new IllegalArgumentException("PathPlanner not fully initialized !!!");
		
		TraceAgent.Path path       = new TraceAgent.Path(reference.getGeometry()
											.stream()
											.map(GeoCoordinate::asUTM)
											.map(utm -> Points.of(utm.getX(), utm.getY()))
											.toArray(Point2D[]::new), true);
		
		Point2D[] 		wayPoints  = agent.compute(_ego.getPosition().asUTM().as2D(), path);

		GeoCoordinate[] trajectory = Arrays .stream(wayPoints)
											.map(GeoCoordinates::newUTM)
											.toArray(GeoCoordinate[]::new);
		
		if(trajectory.length == nbWayPoints) {
			return new GisPath(IDs.newUTF8(_ego.getId() + "traj"), PathType.GroundTrajectory, trajectory);

		} else {
			GeoCoordinate[] trajResult = new GeoCoordinate[nbWayPoints];
			for(int i = 0; i < trajectory.length; ++i)
				trajResult[i] = trajectory[i];
			for(int i = trajectory.length - 1; i < nbWayPoints; ++i)
				trajResult[i] = trajectory[trajectory.length - 1];

			return new GisPath(IDs.newUTF8(_ego.getId() + "traj"), PathType.GroundTrajectory, trajectory);
		}
	}

}

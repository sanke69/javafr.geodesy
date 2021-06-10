package fr.gms.simulator;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.Gis;
import fr.gms.api.service.PathPlanner;
import fr.gms.map.objects.ego.EgoVehicleInMap;
import fr.gms.planner.RailGuidedPathPlannerWithTraceAgent;
import fr.java.lang.properties.ID;

public abstract class EgoVehicleModel {

	public static class Default extends EgoVehicleInMap {

		public Default(final ID _id) {
			super(_id);
		}
		public Default(final ID _id, final GeoCoordinate _position) {
			super(_id, _position);
		}

	}

	public static class WithRailGuidedPlanner extends EgoVehicleInMap {
		Gis.Path 	reference;
		PathPlanner planner;
		Gis.Path    trajectory;

		public WithRailGuidedPlanner(final ID _id, final Gis.Path _reference) {
			super(_id, _reference.getGeometry(0));
			setReference(_reference);
		}

		@Override
		public void 					postUpdate() {
			trajectory = planner.compute(this);
		}

		public void 					setReference(Gis.Path _reference) {
			reference = _reference;
			planner   = new RailGuidedPathPlannerWithTraceAgent(reference, 500, 1);
		}
		public Gis.Path 				getReference() {
			return reference;
		}

		public PathPlanner				getPlanner() {
			return planner;
		}

		public Gis.Path 				getTrajectory() {
			return trajectory;
		}

	}

}

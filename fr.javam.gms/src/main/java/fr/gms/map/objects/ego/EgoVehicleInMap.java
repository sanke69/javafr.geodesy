package fr.gms.map.objects.ego;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.GeoDynamics;
import fr.geodesic.utils.GeoCoordinates;
import fr.gis.api.GisProvider;
import fr.gis.api.road.RoadCoordinate;
import fr.gis.api.road.tools.engines.MapMatchEngine;
import fr.gis.utils.GisUtils;
import fr.gis.utils.tools.road.mapmatching.DynamicMapMatcher;
import fr.gms.api.ego.EgoVehicle;
import fr.gms.map.objects.sensor.EHorizonOrientedInMapped;
import fr.java.lang.properties.ID;
import fr.java.maths.algebra.Vectors;

public class EgoVehicleInMap extends EgoVehicleAdapter implements EgoVehicle {
	protected GisProvider 		provider;
	protected MapMatchEngine 	mapMatcher;

	protected boolean			tryDynamics = false;
	protected boolean			tryMapMatch = false;

	protected RoadCoordinate 	roadCoordinate;

	public EgoVehicleInMap(ID _id) {
		super(_id, null);
	}
	public EgoVehicleInMap(ID _id, GeoCoordinate _position) {
		super(_id, _position);
	}
	public EgoVehicleInMap(ID _id, GeoCoordinate _position, double _radius) {
		super(_id, _position, _radius);
	}
	public EgoVehicleInMap(ID _id, GeoCoordinate _position, GisProvider _provider) {
		this(_id, _position, 250d, _provider);
	}
	public EgoVehicleInMap(ID _id, GeoCoordinate _position, double _radius, GisProvider _provider) {
		super(_id, _position, _radius);

		setEHorizon( new EHorizonOrientedInMapped(_position, 250d, _provider) );
		setMapMatcher( new DynamicMapMatcher( GisUtils.createRoadToolBox(_provider) ) );
	}

	public final void				setProvider(GisProvider _provider) {
		provider    = _provider;
		tryDynamics = true;
		tryMapMatch = false;

		setEHorizon( new EHorizonOrientedInMapped(getPosition(), 250d, _provider) );
		setMapMatcher( new DynamicMapMatcher( GisUtils.createRoadToolBox(_provider) ) );
	}
	public final GisProvider		getProvider() {
		return provider;
	}

	public final void 				enableMapMatching(boolean _enable) {
		tryMapMatch = _enable;
	}
	public final void				setMapMatcher(MapMatchEngine _engine) {
		mapMatcher = _engine;
	}
	public final MapMatchEngine		getMapMatcher() {
		return mapMatcher;
	}

	protected final void			setRoadCoordinate(RoadCoordinate _roadCoord) {
		roadCoordinate = _roadCoord;
	}
	public final RoadCoordinate		getRoadCoordinate() {
		return roadCoordinate;
	}

	public void 					postUpdate() {
		super.postUpdate();

		System.out.println("InMappedEgoVehicle::postUpdate()");
		if(tryDynamics) {
			GeoDynamics dynamic = updateDynamics();
			if(dynamic != null)
				getHistory().updateLast(dynamic);
		}

		if(tryMapMatch) {
			RoadCoordinate mapInfo = updateRoadCoordinate();
			if(mapInfo != null)
				roadCoordinate = mapInfo;
		}

		getEHorizon().update(true);
	}

	private RoadCoordinate		 	updateRoadCoordinate() {
		return getMapMatcher().getMapMatchedPosition(getPosition(), getHeading(), getEHorizon().getSurroundingRoadElements()).orElse(null);
	}
	private GeoDynamics			 	updateDynamics() {
		GeoDynamics 			current  = getHistory().get(0);
		GeoDynamics 			previous = getHistory().get(1);
		GeoDynamics			 	prevDyn  = previous;

		if (previous == null || prevDyn == null)
			return GeoCoordinates.newDynamics(current, getTimestamp(), 0, Vectors.zero3(), Vectors.zero3());

		return GeoCoordinates.computeDynamics(prevDyn.getTimestamp(), previous, getTimestamp(), current);
		/*
//		GeoCoordinates.computeDynamics(, _oldPos, _newTime, _newPos)
		
		GeoCoordinate pos = getHistory().get(0).withDynamics(GeoCoordinates.newDynamics(getTimestamp()));
		GeoCoordinate old = getHistory().get(1);

		if(old == null)
			return GeoCoordinates.newDynamics(getTimestamp(), 0, Vectors.zero3(), Vectors.zero3());

//		double   d = Coordinates.getDistanceBetween(old, pos);
		double   h = GeoCoordinates.computeHeading(pos, old);
		Vector3D v = GeoCoordinates.getVelocity(pos, old);
		Vector3D a = GeoCoordinates.computeAcceleration(pos, old);

		if(v != null)
			if(v.norm() < 3 && getHistory().size() > 2)
				h = old.getDynamics().get().getHeading();

		return GeoCoordinates.newDynamics(getTimestamp(), h, v, a);
		*/
	}

}

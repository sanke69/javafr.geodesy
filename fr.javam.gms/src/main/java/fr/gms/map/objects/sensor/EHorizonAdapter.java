package fr.gms.map.objects.sensor;

import java.util.Collection;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.Gis;
import fr.gis.api.road.Road;
import fr.gis.sdk.objects.GisObject;
import fr.gms.api.sensor.EHorizon;
import fr.java.jvm.properties.id.IDs;
import fr.java.lang.properties.ID;

public abstract class EHorizonAdapter extends GisObject implements EHorizon {
	private GeoCoordinate 				center;
	private double 						radius;

	private Collection<Gis.Dynamics>	proxyMobiles;
	private Collection<Gis.Building>	proxyBuildings;
	private Collection<Road.Element>	proxyMap;
	private Collection<Road.TraficSign>	proxySigns;

	public EHorizonAdapter() {
		this(IDs.random(), null, EHorizon.DEFAULT_RANGE);
	}
	public EHorizonAdapter(GeoCoordinate _center) {
		this(IDs.random(), _center, EHorizon.DEFAULT_RANGE);
	}
	public EHorizonAdapter(GeoCoordinate _center, double _radius) {
		this(IDs.random(), _center, _radius);
	}
	public EHorizonAdapter(ID _id) {
		this(_id, null, EHorizon.DEFAULT_RANGE);
	}
	public EHorizonAdapter(ID _id, GeoCoordinate _center) {
		this(_id, _center, EHorizon.DEFAULT_RANGE);
	}
	public EHorizonAdapter(ID _id, GeoCoordinate _center, double _radius) {
		super(_id);
		center   = _center;
		radius   = _radius;
	}

	public final void   						setCenter(GeoCoordinate _center) {
		center = _center;
		update(true);
	}
	@Override
	public final GeoCoordinate 					getCenter() {
		return center;
	}

	public final void   						setRadius(double _radius) {
		radius = _radius;
		update(true);
	}
	@Override
	public final double 						getRadius() {
		return radius;
	}

	protected final void 						setSurroundingMobiles(Collection<Gis.Dynamics> _surroundingMobiles) {
		proxyMobiles.clear();
		proxyMobiles.addAll(_surroundingMobiles);
	}
	@Override
	public final Collection<Gis.Dynamics> 		getSurroundingMobiles() {
		return proxyMobiles;
	}

	protected final void 						setSurroundingBuildings(Collection<Gis.Building> _surroundingBuildings) {
		proxyBuildings.clear();
		proxyBuildings.addAll(_surroundingBuildings);
	}
	@Override
	public final Collection<Gis.Building> 		getSurroundingBuildings() {
		return proxyBuildings;
	}

	protected final void 						setSurroundingRoadElements(Collection<Road.Element> _surroundingRoads) {
		proxyMap.clear();
		proxyMap.addAll(_surroundingRoads);
	}
	@Override
	public final Collection<Road.Element> 		getSurroundingRoadElements() {
		return proxyMap;
	}

	protected final void 						setSurroundingTrafficSigns(Collection<Road.TraficSign> _surroundingSigns) {
		proxySigns.clear();
		proxySigns.addAll(_surroundingSigns);
	}
	@Override
	public final Collection<Road.TraficSign>	getSurroundingTrafficSigns() {
		return proxySigns;
	}

}

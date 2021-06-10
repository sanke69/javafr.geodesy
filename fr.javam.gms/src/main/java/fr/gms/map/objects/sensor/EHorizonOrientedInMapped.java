package fr.gms.map.objects.sensor;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.GisProvider;
import fr.gis.api.road.RoadCoordinate;
import fr.gis.api.road.tools.RoadToolBox;
import fr.gis.utils.GisUtils;
import fr.gms.api.sensor.EHorizon;
import fr.java.jvm.properties.id.IDs;
import fr.java.lang.properties.ID;

public class EHorizonOrientedInMapped extends EHorizonOrientedAdapter implements EHorizon.Oriented {
	protected GisProvider 		provider;
	protected RoadToolBox 		toolbox;
	
	private   RoadCoordinate    roadCoordinate;

	public EHorizonOrientedInMapped(GisProvider _provider) {
		this(IDs.random(), null, EHorizon.DEFAULT_RANGE, _provider);
	}
	public EHorizonOrientedInMapped(GeoCoordinate _center, GisProvider _provider) {
		this(IDs.random(), _center, EHorizon.DEFAULT_RANGE, _provider);
	}
	public EHorizonOrientedInMapped(GeoCoordinate _center, double _radius, GisProvider _provider) {
		this(IDs.random(), _center, _radius, _provider);
	}
	public EHorizonOrientedInMapped(ID _id, GisProvider _provider) {
		this(_id, null, EHorizon.DEFAULT_RANGE, _provider);
	}
	public EHorizonOrientedInMapped(ID _id, GeoCoordinate _center, GisProvider _provider) {
		this(_id, _center, EHorizon.DEFAULT_RANGE, _provider);
	}
	public EHorizonOrientedInMapped(ID _id, GeoCoordinate _center, double _radius, GisProvider _provider) {
		super(_id, _center, _radius);
		setProvider(_provider);
	}

	public void					update(boolean _withDynamics) {
		updateRoadCoordinate();

		updateProxyStatics();
		if(_withDynamics) updateProxyDynamics();

		updateForwardStatics();
		if(_withDynamics) updateForwardDynamics();
	}

	public void					setProvider(GisProvider _provider) {
		provider = _provider;
		toolbox  = GisUtils.createRoadToolBox(provider);

		update(true);
	}
	public final GisProvider	getProvider() {
		return provider;
	}

	protected final void		setRoadCoordinate(RoadCoordinate _roadCoord) {
		roadCoordinate = _roadCoord;
	}
	public final RoadCoordinate	getRoadCoordinate() {
		return roadCoordinate;
	}

	public final void 			updateRoadCoordinate() {
		setRoadCoordinate           ( toolbox.getMapMatchEngine().getMapMatchedPosition(getCenter(), -1).orElse(null) );
	}
	public final void 			updateProxyStatics() {
		setSurroundingBuildings		( toolbox.getSurroundingBuildings    (getCenter(), getRadius()) );
		setSurroundingRoadElements	( toolbox.getSurroundingRoadElements (getCenter(), getRadius()) );
		setSurroundingTrafficSigns	( toolbox.getSurroundingTrafficSigns (getCenter(), getRadius()) );
	}
	public final void 			updateProxyDynamics() {
		setSurroundingMobiles		( toolbox.getSurroundingMobiles      (getCenter(), getRadius()) );
	}
	public void 				updateForwardStatics() {
		setForwardRoadTree			( toolbox.getOrientedRoadTree   (getRoadCoordinate(), getRadius()) );
		setForwardTrafficSigns		( toolbox.getForwardTraficSigns (getRoadCoordinate(), getRadius(), getForwardRoadTree()) );
	}
	public void 				updateForwardDynamics() {
		setForwardMobiles			( toolbox.getForwardMobiles     (getRoadCoordinate(), getRadius(), getForwardRoadTree()) );
	}

}

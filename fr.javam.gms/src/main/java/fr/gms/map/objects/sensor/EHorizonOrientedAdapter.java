package fr.gms.map.objects.sensor;

import java.util.Collection;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.Gis;
import fr.gis.api.road.Road;
import fr.gms.api.sensor.EHorizon;
import fr.java.jvm.properties.id.IDs;
import fr.java.lang.properties.ID;

public abstract class EHorizonOrientedAdapter extends EHorizonAdapter implements EHorizon.Oriented {
	private   Collection<Road.Element>		forwardTree, forwardVector;
	private   Collection<Road.TraficSign>	forwardSigns;
	private   Collection<Gis.Dynamics>	    forwardMobiles;

	public EHorizonOrientedAdapter() {
		super(IDs.random(), null, EHorizon.DEFAULT_RANGE);
	}
	public EHorizonOrientedAdapter(GeoCoordinate _center) {
		super(IDs.random(), _center, EHorizon.DEFAULT_RANGE);
	}
	public EHorizonOrientedAdapter(GeoCoordinate _center, double _radius) {
		super(IDs.random(), _center, _radius);
	}
	public EHorizonOrientedAdapter(ID _id) {
		super(_id, null, EHorizon.DEFAULT_RANGE);
	}
	public EHorizonOrientedAdapter(ID _id, GeoCoordinate _center) {
		super(_id, _center, EHorizon.DEFAULT_RANGE);
	}
	public EHorizonOrientedAdapter(ID _id, GeoCoordinate _center, double _radius) {
		super(_id);
	}

	protected final void		 				setForwardMobiles(Collection<Gis.Dynamics> _forwardMobiles) {
		forwardMobiles.clear();
		forwardMobiles.addAll(_forwardMobiles);
	}
	@Override
	public final Collection<Gis.Dynamics> 		getForwardMobiles() {
		return forwardMobiles;
	}

	protected final void		 				setForwardRoadTree(Collection<Road.Element> _forwardTree) {
		forwardTree.clear();
		forwardTree.addAll(_forwardTree);
	}
	@Override
	public final Collection<Road.Element> 		getForwardRoadTree() {
		return forwardTree;
	}

	protected final void		 				setForwardRoadVector(Collection<Road.Element> _forwardTree) {
		forwardVector.clear();
		forwardVector.addAll(_forwardTree);
	}
	@Override
	public Collection<Road.Element> 			getForwardRoadVector() {
		return forwardVector;
	}

	protected final void		 				setForwardTrafficSigns(Collection<Road.TraficSign> _forwardSigns) {
		forwardSigns.clear();
		forwardSigns.addAll(_forwardSigns);
	}
	@Override
	public final Collection<Road.TraficSign> 	getForwardTrafficSigns() {
		return forwardSigns;
	}

}

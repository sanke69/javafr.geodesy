package fr.gms.api.sensor;

import java.util.Collection;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.Gis;
import fr.gis.api.road.Road;

public interface EHorizon extends Gis.Object {
	public static final double DEFAULT_RANGE = 100d;

	public interface Oriented extends EHorizon {

		public Collection<Gis.Dynamics> 	getForwardMobiles();

		public Collection<Road.Element> 	getForwardRoadTree();
		public Collection<Road.Element> 	getForwardRoadVector();
		public Collection<Road.TraficSign> 	getForwardTrafficSigns();

	}

	public void 							update(boolean _withDynamics);

	public GeoCoordinate 				 	getCenter();
	public double 						 	getRadius();

	public Collection<Gis.Dynamics> 		getSurroundingMobiles();
	public Collection<Gis.Building> 		getSurroundingBuildings();
	public Collection<Road.Element> 		getSurroundingRoadElements();
	public Collection<Road.TraficSign>   	getSurroundingTrafficSigns();

}

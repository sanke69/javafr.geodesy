package fr.gis.api.tools;

import java.util.Collection;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.Gis;

public interface MapToolBox {

	public Collection<Gis.Dynamics> 		getAllMobiles				();
	public Collection<Gis.Building> 	 	getAllBuildings				();

	public Collection<Gis.Dynamics> 		getSurroundingMobiles		(GeoCoordinate _p, double _radius);
	public Collection<Gis.Building> 	 	getSurroundingBuildings		(GeoCoordinate _p, double _radius);

}
